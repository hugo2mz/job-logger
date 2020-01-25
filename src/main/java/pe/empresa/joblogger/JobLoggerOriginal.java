package pe.empresa.joblogger;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobLoggerOriginal {
	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logMessage;
	private static boolean logWarning;
	private static boolean logError;
	private static boolean logToDatabase;
	private boolean initialized;
	private static Map dbParams;
	private static Logger logger;

	//Mejora: demasiados flags para un constructor
	public JobLoggerOriginal(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map dbParamsMap) {
		/* Error: con el constructor se podrían modificar atributos estáticos, los cuales estarán vigentes para todas las instancias de la clase
		 * causando un comportamiento poco confiable
		 * */
		logger = Logger.getLogger("MyLog");
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		dbParams = dbParamsMap;
	}

	/* Errores:
	 * - Este método crea una conexión a db y a la vez crea handlers de logger, todos a la vez de manera innecesaria, sin importar a donde se quieran orientar los logs, si la DB, a la consola o a un archivo
	 * - Nombre del método LogMessage, por convención debe comenzar en minúscula
	 * - Operaciones diversas colocadas todas juntas sin ningún orden ni relación. Hay lógica, configuración, autenticación, etc 
	 * - Los flags puede ocasionar una configuración ambigua puesto que, por ejemplo, un mensaje puede ser del tipo message y warning a la vez 
	 * */
	public static void LogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception {
		//Error: Posible null pointer Exception y el retorno del trim no se asigna a ningún atributo
		messageText.trim();
		if (messageText == null || messageText.length() == 0) {
			return;
		}
		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new Exception("Invalid configuration");
		}
		if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
			throw new Exception("Error or Warning or Message must be specified");
		}

		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", dbParams.get("userName"));
		connectionProps.put("password", dbParams.get("password"));

		//Mejora: Se podría usar try-with-resources para hacer un autoclose de connection
		connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
				+ ":" + dbParams.get("portNumber") + "/", connectionProps);

		int t = 0;
		if (message && logMessage) {
			t = 1;
		}

		if (error && logError) {
			t = 2;
		}

		if (warning && logWarning) {
			t = 3;
		}

		//Mejora: Se podría usar try-with-resources para hacer un autoclose del statament stmt
		Statement stmt = connection.createStatement();

		String l = null;
		File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		
		FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
		ConsoleHandler ch = new ConsoleHandler();
		
		//Error: uso excesivo de condiciones
		//Error: condición repetida
		if (error && logError) {
			//Error: Asignación insulsa al atributo l, ya que no se usa
			l = l + "error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}

		if (warning && logWarning) {
			//Error: Asignación insulsa al atributo l, ya que no se usa
			l = l + "warning " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}

		if (message && logMessage) {
			//Error: Asignación insulsa al atributo l, ya que no se usa
			l = l + "message " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}
		
		if(logToFile) {
			logger.addHandler(fh);
			logger.log(Level.INFO, messageText);
		}
		
		if(logToConsole) {
			logger.addHandler(ch);
			logger.log(Level.INFO, messageText);
		}
		
		if(logToDatabase) {
			//Error: Posible ataque de Inyección SQL
			//Error: Uso innecesario de String.valueOf, podría concatenarse directamente
			stmt.executeUpdate("insert into Log_Values('" + message + "', " + String.valueOf(t) + ")");
		}
	}
}

