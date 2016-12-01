package IoT_Client.sensors;

import IoT_Client.utility.SendAndReciveData;
import IoT_Client.utility.sensorInfor;
import com.phidgets.PhidgetException;
import com.phidgets.RFIDPhidget;
import com.phidgets.event.TagGainEvent;
import com.phidgets.event.TagGainListener;
import com.phidgets.event.TagLossEvent;
import com.phidgets.event.TagLossListener;
import org.apache.log4j.Logger;

/**
 * Created by hamzaghani on 31/10/2016.
 */
public class DoorRFID implements TagLossListener, TagGainListener {

    private static final Logger log = Logger.getLogger(DoorRFID.class);
    private RFIDPhidget rfid;
    private sensorInfor infor;
    private DoorMotorServo moto;
    private SendAndReciveData sendRecive = new SendAndReciveData();

    public DoorRFID() {
        infor = new sensorInfor();
        moto = new DoorMotorServo();
        try {

            rfid = new RFIDPhidget();
            rfid.addTagGainListener(this);
            rfid.addTagLossListener(this);

            rfid.openAny();
            rfid.waitForAttachment();
            rfid.setAntennaOn(true);
            rfid.setLEDOn(true);
            infor.setSensorValue(false);

            while (true)
                try {
                    dataReceived();
                    Thread.sleep(5000);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
        } catch (PhidgetException e) {
            log.error(e);
        }

    }

    @Override
    public void tagGained(TagGainEvent tagGainEvent) {

        if (tagGainEvent.getValue().equals("hi")) {

            try {
                infor.setSensorValue(true);
                sendRecive.sendingData(infor.getSensorValue());
                rfid.setLEDOn(false);
                moto.openlock();
                Thread.sleep(250);
                if (rfid.getLEDOn() == false && infor.getSensorValue() == true) {
                    infor.setSensorValue(false);
                    sendRecive.sendingData(infor.getSensorValue());
                    rfid.setLEDOn(true);
                    moto.closelock();

                }
            } catch (PhidgetException e) {
                log.error(e);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
        infor.setSensorValue(false);

    }

    @Override
    public void tagLost(TagLossEvent tagLossEvent) {


    }


    private void dataReceived() {
        boolean t = infor.getSensorValue();


        if ( sendRecive.reciveData().equals("true") && t==false ){
            System.out.println("hamzaghani");
            try {
                infor.setSensorValue(true);
                rfid.setLEDOn(false);
                moto.openlock();
                Thread.sleep(250);
                if (infor.getSensorValue() == true) {
                    infor.setSensorValue(false);
                    sendRecive.sendingData(infor.getSensorValue());
                    rfid.setLEDOn(true);
                    moto.closelock();
                }
            } catch (PhidgetException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}