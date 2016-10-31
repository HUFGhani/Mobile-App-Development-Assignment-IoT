package IoT_Client.sensors;

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
public class RFID implements TagLossListener, TagGainListener {

    private static final Logger log = Logger.getLogger(RFID.class);
    RFIDPhidget rfid;

    public RFID() throws PhidgetException {
        try {

            rfid = new RFIDPhidget();
            rfid.addTagGainListener(this);
            rfid.addTagLossListener(this);

            rfid.openAny();
            rfid.waitForAttachment();
            rfid.setAntennaOn(true);

            while (true)
                try {
                    Thread.sleep(1000);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
        }catch (PhidgetException e){
            log.error(e);
        }
    }

    @Override
    public void tagGained(TagGainEvent tagGainEvent) {



    }

    @Override
    public void tagLost(TagLossEvent tagLossEvent) {

    }
}
