package mylog.log;

import java.io.IOException;
import java.util.logging.Level;

import mylog.log.app.JobLogger;

public class Prueba {

    public static void main(String[] args) throws IOException {
    	
    	JobLogger jobLogger = new JobLogger.Builder()
    			.logToFile(false)
    			.logToConsole(true)
    			.logToDatabase(false)
    			.activateError(true)
    			.activateMessage(true)
    			.build();
    	
    	jobLogger.log("Mensaje de pruebas", Level.SEVERE);

    }
}
