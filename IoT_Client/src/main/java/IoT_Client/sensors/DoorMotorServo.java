package IoT_Client.sensors;

import IoT_Client.utility.sensorInfor;
import com.phidgets.AdvancedServoPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.*;
import org.apache.log4j.Logger;

/**
 * Created by hamzaghani on 31/10/2016.
 */
public class DoorMotorServo {
    private static final Logger log = Logger.getLogger(DoorMotorServo.class);
    private AdvancedServoPhidget moto;
    private sensorInfor infor;

    public DoorMotorServo() {
        try {
            moto = new AdvancedServoPhidget();
            moto.addAttachListener(new AttachListener() {
                public void attached(AttachEvent ae) {
                    log.info(ae);
                }

            });
            moto.addDetachListener(new DetachListener() {
                public void detached(DetachEvent ae) {
                    log.info(ae);
                }
            });
            moto.addErrorListener(new ErrorListener() {
                public void error(ErrorEvent ee) {
                    log.error(ee);
                }
            });
            moto.addServoPositionChangeListener(new ServoPositionChangeListener()
            {
                public void servoPositionChanged(ServoPositionChangeEvent oe)
                {
                   log.info(oe);
                }
            });

            moto.openAny();
            moto.waitForAttachment();

        } catch (PhidgetException e) {
            log.error(e);
        }

    }

    public void openlock(){
        try {
            moto.setEngaged(0, false);
            moto.setSpeedRampingOn(0, false);
            moto.setPosition(0, 180);
            System.out.println("open doors");
            moto.setEngaged(0, true);
            Thread.sleep(5000);
        } catch (PhidgetException e) {
            log.error(e);
        } catch (InterruptedException e) {
            log.error(e);
        }

    }

    public void closelock(){
        try {
            moto.setEngaged(0, false);
            moto.setSpeedRampingOn(0, false);
            moto.setPosition(0, 0);
            System.out.println("locking doors");
            moto.setEngaged(0, true);
            Thread.sleep(2000);
        }catch (PhidgetException e){
            log.error(e);
        }catch (InterruptedException e){
            log.error(e);
        }
    }
}
