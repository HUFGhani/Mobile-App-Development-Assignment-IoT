package IoT_Client.controller;

import IoT_Client.sensors.DoorRFID;

/**
 * Created by hamzaghani on 28/10/2016.
 */
public class Controller {
    private static DoorRFID doorRFID;

    public static void main(String[] args){
        doorRFID = new DoorRFID();

    }


}
