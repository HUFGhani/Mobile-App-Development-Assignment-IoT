package doa;

import config.connectionFactory;
import model.locationInfor;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by hamzaghani on 26/10/2016.
 */
public class locationInforDAO {

    private static final Logger log = Logger.getLogger(locationInforDAO.class);
    Connection conn;
    PreparedStatement ptmt;
    ResultSet output;
    //Statement statement;

    private Connection getConnection() throws SQLException{
        Connection connection;
        connection= connectionFactory.getInstance().getConnection();
        return connection;
    }

    public void add (locationInfor location){
        try {
            String query="insert into Locations(Latitude, Longitude,Email, TimeStamp ) "+
                    "values(?,?,?,?);";
            conn=getConnection();
            ptmt=conn.prepareStatement(query);
            ptmt.setBigDecimal(1, location.getLat());
            ptmt.setBigDecimal(2, location.getLng());
            ptmt.setString(3, location.getEmail());
            ptmt.setTimestamp(4, (Timestamp) location.getTimestape());
            ptmt.executeUpdate();
            log.info("Data Added Successfully!!!");

        }catch (SQLException e){
            log.error(e);
        }finally {
            destory();
        }
    }

    public ArrayList<locationInfor> getdata(String email){
        ArrayList<locationInfor> location = null;
        try {
            String query="Select * from Locations WHERE Email ='"+email+"';";
            conn=getConnection();
            ptmt=conn.prepareStatement(query);
            output = ptmt.executeQuery();
            location=new ArrayList<locationInfor>();
            while (output.next()){
                locationInfor infor = new locationInfor();

                infor.setLat(output.getBigDecimal("Latitude"));
                infor.setLng(output.getBigDecimal("Longitude"));
                infor.setTimestape(output.getTimestamp("TimeStamp"));
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
