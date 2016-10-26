package model;

import java.util.Date;

/**
 * Created by hamzaghani on 26/10/2016.
 */
public class locationInfor {

    Float lon, lat;
    String deviceID;
    Date Timestape;

    public locationInfor() {
        super();
    }

    public locationInfor(Float lon, Float lat, String deviceID) {
        this.lon = lon;
        this.lat = lat;
        this.deviceID = deviceID;
    }

    public Float getLon() {
        return lon;
    }

    public locationInfor setLon(Float lon) {
        this.lon = lon;
        return this;
    }

    public Float getLat() {
        return lat;
    }

    public locationInfor setLat(Float lat) {
        this.lat = lat;
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
        return "locationInfor{" +
                "lon=" + lon +
                ", lat=" + lat +
                ", deviceID='" + deviceID + '\'' +
                ", Timestape=" + Timestape +
                '}';
    }
}
