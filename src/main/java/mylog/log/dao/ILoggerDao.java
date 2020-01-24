package mylog.log.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.LogRecord;

import mylog.log.model.EventLog;

public interface ILoggerDao {
	
	boolean save(LogRecord record) throws ClassNotFoundException, SQLException;
	List<EventLog> list();

}
