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

    public void add (String sensorName, String sensorValue){
        try {
            String query="insert into sensorUsage(SensorName, SensorValue, TimeInserted) "+
                    "values('"+sensorName+"','"+sensorValue+"', now());";
            conn=getConnection();
            ptmt=conn.prepareStatement(query);
            sensorInfor infor = new sensorInfor();
            ptmt.setString(1,infor.getSensorName());
            ptmt.setString(2,infor.getSensorValue());
            ptmt.executeUpdate();
            log.info("Data Added Successfully!!!");

        }catch (SQLException e){
            log.error(e);
        }finally {
            destory();
        }
    }

    public ArrayList<sensorInfor> getdata(){
        ArrayList<sensorInfor> sensor = null;
        try {
            String query="Select * from sensor";
            conn=getConnection();
            ptmt=conn.prepareStatement(query);
            output = ptmt.executeQuery();
            sensor=new ArrayList<sensorInfor>();
            while (output.next()){
                sensorInfor infor = new sensorInfor();
                infor.setSensorID(output.getString(""));
                infor.setSensorName(output.getString(""));
                infor.setSensorValue(output.getString(""));
                infor.setTimeInserted(output.getTime(""));
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
