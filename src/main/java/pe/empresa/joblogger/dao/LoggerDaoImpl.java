package pe.empresa.joblogger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.LogRecord;

import pe.empresa.joblogger.model.EventLog;

public class LoggerDaoImpl implements ILoggerDao {
	
	@Override
	public boolean save(LogRecord record) throws ClassNotFoundException, SQLException {
		final String insertSQL = "insert into LOG_EVENT(message, level) values(?, ?)";
		
		Connection con = ConnectDb.getInstance().getConnection();
		try(PreparedStatement prepInsert = con.prepareStatement(insertSQL);) {
			prepInsert.setString(1,record.getMessage());
			prepInsert.setString(2,record.getLevel().toString());
			int rpta = prepInsert.executeUpdate();
			return rpta > 0;
		}

	}

	@Override
	public List<EventLog> list() {
		return null;
	}

}
