package IoT_Client.sensors;

import IoT_Client.utility.SendAndReciveData;
import IoT_Client.utility.SensorsInfor;
import com.google.gson.Gson;
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
    private SensorsInfor infor;
    private DoorMotorServo moto;
    private SendAndReciveData sendRecive = new SendAndReciveData();
    Gson gson = new Gson();
    SensorsInforR T = new SensorsInforR();

    public DoorRFID()  {
        infor = new SensorsInfor();
        moto= new DoorMotorServo();
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
                    dataReceived();
                    Thread.sleep(2500);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
        }catch (PhidgetException e){
            log.error(e);
        }
    }

    @Override
    public void tagGained( TagGainEvent tagGainEvent ) {

        infor.setRfidValue( tagGainEvent.getValue( ) );
        if (infor.getRfidValue().equals("hi") ){
            log.info(infor.getRfidValue());

            try {
                infor.setIsDooropen(true);
                sendRecive.sendingData(infor.getIsDooropen());
                rfid.setLEDOn(false);
                moto.openlock();
                Thread.sleep(250);
                if (rfid.getLEDOn() == false && infor.getIsDooropen()==true) {
                    infor.setIsDooropen(false);
                    sendRecive.sendingData(infor.getIsDooropen());
                    rfid.setLEDOn(true);
                    moto.closelock();

                }
            } catch (PhidgetException e) {
                log.error(e);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
        infor.setIsDooropen(false);
    }

    @Override
    public void tagLost(TagLossEvent tagLossEvent) {

    }


    private void dataReceived(){

        String json = sendRecive.reciveData();

        System.out.println(json);

    }



}