package config;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by hamzaghani on 24/10/2016.
 */
public class connectionFactory {
    private static final Logger log = Logger.getLogger(connectionFactory.class);
    String driverClassName = "com.mysql.jdbc.Driver";
    String dbUser = "root";
    String dbPwd = "ghani";
    String dBase = "";
    String dbLocal = "";
    String connectionUrl = "jdbc:mysql://"+dbLocal+"/" + dBase;

    private static connectionFactory connectionFactory = null;

    private connectionFactory() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            log.error(e);
        }
    }

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
        return conn;
    }

    public static connectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new connectionFactory();
        }
        return connectionFactory;
    }


}
