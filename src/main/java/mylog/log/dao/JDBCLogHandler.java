package mylog.log.dao;

import java.sql.SQLException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class JDBCLogHandler extends Handler {

	@Override
	public void publish(LogRecord record) {

    	ILoggerDao dao = new LoggerDaoImpl();
    	try {
			dao.save(record);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() {
	}

}
