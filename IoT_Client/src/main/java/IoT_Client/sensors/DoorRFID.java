package IoT_Client.sensors;

import IoT_Client.utility.SensorsInfor;
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
                    Thread.sleep(500);
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
        if (infor.getRfidValue().equals("hi")){
            log.info(infor.getRfidValue());

            try {
                rfid.setLEDOn(false);
                moto.openlock();
                infor.setIsDooropen(true);
                Thread.sleep(500);
                if (rfid.getLEDOn() == false) {
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
}