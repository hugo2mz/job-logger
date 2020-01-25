package pe.empresa.joblogger.app;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import pe.empresa.joblogger.dao.JDBCLogHandler;

public class JogLoggerProcess {
	
	private static final Logger LOGGER = Logger.getLogger("pe.empresa.log");
	private JobLogger jobLogger;
	
	JogLoggerProcess(JobLogger jobLogger) {
		this.jobLogger = jobLogger;
	}
	
	public void log(String message, Level level) throws IOException {
		if(levelFilter(level)) {
			if(jobLogger.getLogToFile().booleanValue()) {
				logFile(message, level);
			}
			
			if(jobLogger.getLogToDatabase().booleanValue()) {
				logDatabase(message, level);
			}

			if(jobLogger.getLogToConsole().booleanValue()) {
				logConsole(message, level);
			}
	
		}
	}
	
	private boolean levelFilter(Level level) {
		return ( (level.equals(Level.INFO) && jobLogger.getActivateMessage().booleanValue()) ||
				(level.equals(Level.WARNING) && jobLogger.getActivateWarning().booleanValue()) || 
				(level.equals(Level.SEVERE) && jobLogger.getActivateError().booleanValue()) );
	}

	private void logFile(String message, Level level) throws IOException {
		Handler fileHandler = new FileHandler("./joblogger.log", true);
		SimpleFormatter simpleFormatter = new SimpleFormatter();
		fileHandler.setFormatter(simpleFormatter);
		LOGGER.addHandler(fileHandler);
		LOGGER.log(level, level + " - " + message);
		LOGGER.removeHandler(fileHandler);
	}
	
	private void logDatabase(String message, Level level) {
		JDBCLogHandler dbHandler = new JDBCLogHandler(); 
		LOGGER.addHandler(dbHandler);
		LOGGER.log(level, level + " - " + message);
		LOGGER.removeHandler(dbHandler);
		
	}

	private void logConsole(String message, Level level) {
		Handler consoleHandler = new ConsoleHandler();
		LOGGER.addHandler(consoleHandler);
		LOGGER.log(level, level + " - " + message);
		LOGGER.removeHandler(consoleHandler);
	}

}
