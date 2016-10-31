package IoT_Client.utility;

/**
 * Created by hamzaghani on 31/10/2016.
 */
public class SensorsInfor {

    String rfidValue;
    boolean isDoorOpen;

    public SensorsInfor() {
        super();
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
