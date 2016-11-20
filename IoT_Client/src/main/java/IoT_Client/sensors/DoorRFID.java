package IoT_Client.sensors;

import IoT_Client.utility.SensorsInfor;
import com.phidgets.PhidgetException;
import com.phidgets.RFIDPhidget;
import com.phidgets.event.TagGainEvent;
import com.phidgets.event.TagGainListener;
import com.phidgets.event.TagLossEvent;
import com.phidgets.event.TagLossListener;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hamzaghani on 31/10/2016.
 */
public class DoorRFID implements TagLossListener, TagGainListener {

    private static final Logger log = Logger.getLogger(DoorRFID.class);
    private RFIDPhidget rfid;
    private SensorsInfor infor;
    private DoorMotorServo moto;

    static String mainURL= "http://localhost:8080/IoT_Server/sensorDB";
    URL url;
    HttpURLConnection conn;
    BufferedReader rd;
    String fullURL ;


    public DoorRFID()  {
        infor = new SensorsInfor();
        moto= new DoorMotorServo();
        infor.setIsDooropen(false);
        try {

            rfid = new RFIDPhidget();
            rfid.addTagGainListener(this);
            rfid.addTagLossListener(this);

            rfid.openAny();
            rfid.waitForAttachment();
            rfid.setAntennaOn(true);
            rfid.setLEDOn(true);

            while (true)
                try {
                    Thread.sleep(5000);
                    getDataFromServer();
                } catch (Throwable t) {
                    log.error(t);
                }
        }catch (PhidgetException e){
            log.error(e);
        }
    }

    @Override
    public void tagGained( TagGainEvent tagGainEvent ) {
        infor.setRfidValue( tagGainEvent.getValue( ) );
        if (infor.getRfidValue().equals("hi")){
            log.info(infor.getRfidValue());

            try {
                rfid.setLEDOn(false);
                moto.openlock();
                infor.setIsDooropen(true);
                Data2Server();
                if (rfid.getLEDOn() == false) {
                    rfid.setLEDOn(true);
                    moto.closelock();
                    infor.setIsDooropen(false);
                    Data2Server();
                }
            } catch (PhidgetException e) {
                log.error(e);
            }
        }
        infor.setIsDooropen(false);
    }

    @Override
    public void tagLost(TagLossEvent tagLossEvent) {

    }

    public String Data2Server() {

        fullURL= mainURL + "?sensorname=DoorRFID&sensorvalue="+infor.getIsDooropen();
        System.out.println("Sending data to: "+fullURL);
        String line;
        String result = "";

        try {
            url = new URL(fullURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getDataFromServer() {

        fullURL= mainURL + "?getdata";
        System.out.println("Sending data to: "+fullURL);
        String line;
        String result = "";

        try {
            url = new URL(fullURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
