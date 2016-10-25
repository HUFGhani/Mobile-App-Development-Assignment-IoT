package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by hamzaghani on 24/10/2016.
 */
public class connectionFactory {
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
            e.printStackTrace();
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
