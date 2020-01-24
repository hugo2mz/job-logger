package mylog.log.app;

import java.io.IOException;
import java.util.logging.Level;

import lombok.Getter;
import lombok.ToString;
import mylog.log.exceptions.JobLoggerException;

@Getter
@ToString
public class JobLogger {
	
	private Boolean logToFile;
	private Boolean logToConsole;
	private Boolean logToDatabase;
	private Boolean activateMessage;
	private Boolean activateWarning;
	private Boolean activateError;
	
	private JobLogger() {
	}

	private JobLogger(Builder builder) {
		this.logToFile = builder.logToFile;
		this.logToConsole = builder.logToConsole;
		this.logToDatabase = builder.logToDatabase;
		this.activateMessage = builder.activateMessage;
		this.activateWarning = builder.activateWarning;
		this.activateError = builder.activateError;
	}
	
	public void log(String message, Level level) throws IOException {
		JogLoggerProcess jobJogLoggerProcess = new JogLoggerProcess(this);	
		jobJogLoggerProcess.log(message, level);
	} 
	
	public static class Builder {
		private Boolean logToFile = false;
		private Boolean logToConsole = false;
		private Boolean logToDatabase = false;
		private Boolean activateMessage = false;
		private Boolean activateWarning = false;
		private Boolean activateError = false;
		
		public Builder logToFile(Boolean logToFile) {
			this.logToFile = logToFile;
			return this;			
		}
		
		public Builder logToConsole(Boolean logToConsole) {
			this.logToConsole = logToConsole;
			return this;			
		}
		
		public Builder logToDatabase(Boolean logToDatabase) {
			this.logToDatabase = logToDatabase;
			return this;			
		}
		
		public Builder activateMessage(Boolean activateMessage) {
			this.activateMessage = activateMessage;
			return this;			
		}
		
		public Builder activateWarning(Boolean activateWarning) {
			this.activateWarning = activateWarning;
			return this;			
		}
		
		public Builder activateError(Boolean activateError) {
			this.activateError = activateError;
			return this;			
		}
		
	    public JobLogger build() {
	    	JobLogger jobLogger = new JobLogger(this);
	    	validateJobLogger(jobLogger);
	    	
    		return jobLogger;
    	}

		private void validateJobLogger(JobLogger jobLogger) {
			if( (!jobLogger.getLogToFile() && !jobLogger.getLogToConsole() && !jobLogger.getLogToDatabase()) || 
				(!jobLogger.getActivateError() && !jobLogger.getActivateMessage() && !jobLogger.getActivateWarning())
					) {
				throw new JobLoggerException("Invalid configuration. Activate at least one destination and one log level");
			}
		}
		
	}

}
