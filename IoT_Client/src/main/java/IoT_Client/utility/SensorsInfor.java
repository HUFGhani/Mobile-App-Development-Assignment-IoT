package IoT_Client.utility;

import java.io.Serializable;

/**
 * Created by hamzaghani on 31/10/2016.
 */
public class SensorsInfor implements Serializable{

    String rfidValue;
    boolean isDoorOpen;

    public SensorsInfor() {
        super();
    }

    public SensorsInfor(String rfidValue, boolean isDoorOpen) {
        this.rfidValue = rfidValue;
        this.isDoorOpen = isDoorOpen;
    }

    public String getRfidValue() {
        return rfidValue;
    }

    public SensorsInfor setRfidValue(String rfidValue) {
        this.rfidValue = rfidValue;
        return this;
    }

    public boolean getIsDooropen() {
        return isDoorOpen;
    }

    public SensorsInfor setIsDooropen(boolean dooropen) {
        isDoorOpen = dooropen;
        return this;
    }

    @Override
    public String toString() {
        return "SensorsInfor{" +
                "rfidValue='" + rfidValue + '\'' +
                ", isDooropen=" + isDoorOpen +
                '}';
    }
}
