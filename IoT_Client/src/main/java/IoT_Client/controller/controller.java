package IoT_Client.controller;

import IoT_Client.sensors.DoorMotorServo;
import IoT_Client.sensors.DoorRFID;
import IoT_Client.utility.SensorsInfor;

/**
 * Created by hamzaghani on 28/10/2016.
 */
public class Controller {
    private static DoorRFID rfid;
    private static DoorMotorServo moto;
    SensorsInfor infor;

    public static void main(String[] args){
        rfid = new DoorRFID();

    }


}
