package pe.empresa.joblogger;

import java.util.logging.Level;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pe.empresa.joblogger.app.JobLogger;

@SpringBootApplication
public class JobLoggerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JobLoggerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
    	JobLogger jobLogger = new JobLogger.Builder()
    			.logToFile(false)
    			.logToConsole(true)
    			.logToDatabase(true)
    			.activateError(true)
    			.activateMessage(true)
    			.activateWarning(true)
    			.build();
    	
    	jobLogger.log("Mensaje de pruebas 1", Level.SEVERE);
//    	jobLogger.log("Mensaje de pruebas 2", Level.WARNING);
//    	jobLogger.log("Mensaje de pruebas 3", Level.INFO);
		
	}

}
