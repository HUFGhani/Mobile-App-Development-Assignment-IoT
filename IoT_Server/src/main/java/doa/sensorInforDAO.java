package doa;

import config.ConnectionFactory;
import model.sensorInfor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by hamzaghani on 24/10/2016.
 */
public class sensorInforDAO {
    Connection conn;
    PreparedStatement ptmt;
    ResultSet output;
    public sensorInforDAO() {
    }

    private Connection getConnection() throws SQLException{
        Connection connection;
        connection= ConnectionFactory.getInstance().getConnection();
        return connection;
    }

    public void add (sensorInfor infor){

    }

    public ArrayList<sensorInfor> getdata(){

        return null;
    }

}
