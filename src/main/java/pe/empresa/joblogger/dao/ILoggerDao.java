package pe.empresa.joblogger.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.LogRecord;

import pe.empresa.joblogger.model.EventLog;

public interface ILoggerDao {
	
	boolean save(LogRecord record) throws ClassNotFoundException, SQLException;
	List<EventLog> list();

}
