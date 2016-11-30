package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by hamzaghani on 26/10/2016.
 */
public class locationInfor implements Serializable{

    private BigDecimal lat,lng;
    private Timestamp timestape;
    private String email;

    public locationInfor() {
        super();
    }

    public locationInfor(BigDecimal lat, BigDecimal lng, Timestamp timestape, String email) {
        this.lat = lat;
        this.lng = lng;
        this.timestape = timestape;
        this.email = email;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public locationInfor setLat(BigDecimal lat) {
        this.lat = lat;
        return this;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public locationInfor setLng(BigDecimal lng) {
        this.lng = lng;
        return this;
    }

    public Date getTimestape() {
        return timestape;
    }

    public locationInfor setTimestape(Timestamp timestape) {
        this.timestape = timestape;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public locationInfor setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "locationInfor{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", timestape=" + timestape +
                ", email='" + email + '\'' +
                '}';
    }
}
