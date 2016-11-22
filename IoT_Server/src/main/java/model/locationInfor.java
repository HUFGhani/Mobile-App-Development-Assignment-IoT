package model;

import java.util.Date;

/**
 * Created by hamzaghani on 26/10/2016.
 */
public class locationInfor {

    Float  lat,lng;
    String deviceID;
    Date Timestape;

    public locationInfor() {
        super();
    }

    public locationInfor(Float lng, Float lat, String deviceID) {
        this.lng = lng;
        this.lat = lat;
        this.deviceID = deviceID;
    }

    public Float getLat() {
        return lat;
    }

    public locationInfor setLat(float lat) {
        this.lat = lat;
        return this;
    }
    public Float getLng() {
        return lng;
    }

    public locationInfor setLng(float lng) {
        this.lng = lng;
        return this;
    }
    public String getDeviceID() {
        return deviceID;
    }

    public locationInfor setDeviceID(String deviceID) {
        this.deviceID = deviceID;
        return this;
    }

    public Date getTimeInserted() {
        return Timestape;
    }

    public locationInfor setTimeInserted(Date timestape) {
        Timestape = timestape;
        return this;
    }

    @Override
    public String toString() {
        return "[locationInfor{" +
                ", lat=" + lat +
                "lng=" + lng +
                ", deviceID='" + deviceID + '\'' +
                ", Timestape=" + Timestape +
                "}]";
    }
}
