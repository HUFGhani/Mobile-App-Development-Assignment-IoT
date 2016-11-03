package doa;

import config.connectionFactory;
import model.locationInfor;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by hamzaghani on 26/10/2016.
 */
public class locationInforDAO {

    private static final Logger log = Logger.getLogger(locationInforDAO.class);
    Connection conn;
    PreparedStatement ptmt;
    ResultSet output;

    private Connection getConnection() throws SQLException{
        Connection connection;
        connection= connectionFactory.getInstance().getConnection();
        return connection;
    }

    public void add (String lan, String lat){
        try {
            String query="insert into sensorUsage(SensorName, SensorValue, TimeInserted) "+
                    "values('"+lan+"','"+lat+"', now());";
            conn=getConnection();
            ptmt=conn.prepareStatement(query);
            locationInfor infor = new locationInfor();
            ptmt.setString(1,infor.getDeviceID());
            ptmt.setFloat(2, infor.getLat());
            ptmt.setFloat(3, infor.getLon());
            ptmt.executeUpdate();
            log.info("Data Added Successfully!!!");

        }catch (SQLException e){
            log.error(e);
        }finally {
            destory();
        }
    }

    public ArrayList<locationInfor> getdata(){
        ArrayList<locationInfor> location = null;
        try {
            String query="Select * from location";
            conn=getConnection();
            ptmt=conn.prepareStatement(query);
            output = ptmt.executeQuery();
            location=new ArrayList<locationInfor>();
            while (output.next()){
                locationInfor infor = new locationInfor();
                infor.setDeviceID(output.getString(""));
                infor.setLat(output.getFloat("s"));
                infor.setLon(output.getFloat("s"));
                infor.setTimeInserted(output.getTime("s"));
                location.add(infor);
            }
        } catch (SQLException e) {
            log.error(e);
        } finally {
            destory();
        }
        return location;
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
