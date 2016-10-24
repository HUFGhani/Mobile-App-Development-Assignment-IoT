package model;

import java.util.Date;

/**
 * Created by hamzaghani on 24/10/2016.
 */
public class sensorInfor {
    String sensorID, sensorName, sensorValue;
    Date timeInserted;

    public sensorInfor() {
        super();
    }

    public sensorInfor(String sensorID, String sensorName, String sensorValue, Date timeInserted) {
        super();
        this.sensorID =sensorID;
        this.sensorName = sensorName;
        this.sensorValue = sensorValue;
        this.timeInserted = timeInserted;
    }

    public String getSensorID() {
        return sensorID;
    }

    public sensorInfor setSensorID(String sensorID) {
        this.sensorID = sensorID;
        return this;
    }

    public String getSensorName() {
        return sensorName;
    }

    public sensorInfor setSensorName(String sensorName) {
        this.sensorName = sensorName;
        return this;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public sensorInfor setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
        return this;
    }

    public Date getTimeInserted() {
        return timeInserted;
    }

    public sensorInfor setTimeInserted(Date timeInserted) {
        this.timeInserted = timeInserted;
        return this;
    }

    @Override
    public String toString() {
        return "sensorInfor{" +
                "sensorID='" + sensorID + '\'' +
                ", sensorName='" + sensorName + '\'' +
                ", sensorValue='" + sensorValue + '\'' +
                ", timeInserted=" + timeInserted +
                '}';
    }
}
