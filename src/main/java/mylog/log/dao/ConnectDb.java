package mylog.log.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectDb {
	
    private static ConnectDb instance;
    private Connection connection;

    private ConnectDb() throws SQLException, ClassNotFoundException {
		Properties proper = new Properties();
		proper.setProperty("user", "sa");
		proper.setProperty("password", "");

		String driver = "org.h2.Driver";
    	String url = "jdbc:h2:~/test";
        
        Class.forName(driver);
        this.connection = DriverManager.getConnection(url, proper);
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConnectDb getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new ConnectDb();
        }
        return instance;
    }	

}
