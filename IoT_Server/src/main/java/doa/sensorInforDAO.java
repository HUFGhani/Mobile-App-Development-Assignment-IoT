package doa;

import config.connectionFactory;
import model.sensorInfor;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by hamzaghani on 24/10/2016.
 */
public class sensorInforDAO {

    private static final Logger log = Logger.getLogger(sensorInforDAO.class);
    Connection conn;
    PreparedStatement ptmt;
    ResultSet output;
    public sensorInforDAO() {
    }

    private Connection getConnection() throws SQLException{
        Connection connection;
        connection= connectionFactory.getInstance().getConnection();
        return connection;
    }

    public void add (sensorInfor sensor){
        try {
            String query="insert into Sensers(SensorName, SensorValue,Email, TimeStamp ) "+
                    "values(?,?,?,?);";
            conn=getConnection();
            ptmt=conn.prepareStatement(query);
            ptmt.setString(1,sensor.getSensorName());
            ptmt.setBoolean(2,sensor.getSensorValue());
            ptmt.setString(3,sensor.getEmail());
            ptmt.setTimestamp(4,sensor.getTimeStamp());
            ptmt.executeUpdate();
            log.info("Data Added Successfully!!!");

        }catch (SQLException e){
            log.error(e);
        }finally {
            destory();
        }
    }

    public ArrayList<sensorInfor> getdata(String eamil){
        ArrayList<sensorInfor> sensor = null;
        try {
            String query="Select * from Sensers WHERE Email='"+eamil+"'ORDER BY TimeStamp DESC LIMIT 1;";
            conn=getConnection();
            ptmt=conn.prepareStatement(query);
            output = ptmt.executeQuery();
            sensor=new ArrayList<sensorInfor>();
            while (output.next()){
                sensorInfor infor = new sensorInfor();
                infor.setSensorName(output.getString("SensorName"));
                infor.setSensorValue(output.getBoolean("SensorValue"));
                infor.setTimeStamp(output.getTimestamp("TimeStamp"));
                sensor.add(infor);

            }
        } catch (SQLException e) {
            log.error(e);
        } finally {
            destory();
        }
        return sensor;
    }

    private void destory(){
        try {
            if (ptmt != null) {
                ptmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
    }


}
