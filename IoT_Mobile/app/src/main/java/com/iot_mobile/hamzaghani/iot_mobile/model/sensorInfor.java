package com.iot_mobile.hamzaghani.iot_mobile.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by hamzaghani on 30/11/2016.
 */

public class sensorInfor implements Serializable {
    private String sensorID, sensorName,email;
    private Boolean sensorValue;
    private Timestamp TimeStamp;

    public sensorInfor() {
        super();
    }

    public sensorInfor(String sensorID, String sensorName, String email, Boolean sensorValue, Timestamp timeStamp) {
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.email = email;
        this.sensorValue = sensorValue;
        TimeStamp = timeStamp;
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

    public String getEmail() {
        return email;
    }

    public sensorInfor setEmail(String email) {
        this.email = email;
        return this;
    }

    public Boolean getSensorValue() {
        return sensorValue;
    }

    public sensorInfor setSensorValue(Boolean sensorValue) {
        this.sensorValue = sensorValue;
        return this;
    }

    public Timestamp getTimeStamp() {
        return TimeStamp;
    }

    public sensorInfor setTimeStamp(Timestamp timeStamp) {
        TimeStamp = timeStamp;
        return this;
    }

    @Override
    public String toString() {
        return "sensorInfor{" +
                "sensorID='" + sensorID + '\'' +
                ", sensorName='" + sensorName + '\'' +
                ", email='" + email + '\'' +
                ", sensorValue=" + sensorValue +
                ", TimeStamp=" + TimeStamp +
                '}';
    }
}