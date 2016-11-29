package IoT_Client.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hamzaghani on 29/11/2016.
 */
public class SendAndReciveData {
    static String mainURL= "http://localhost:8080/IoT_Server/sensorDB";
    URL url;
    HttpURLConnection conn;
    BufferedReader rd;
    String fullURL ;


    public String sendingData(boolean info) {
        fullURL = mainURL + "?sensorname=DoorRFID&sensorvalue=" + info;
        System.out.println("Sending data to: " + fullURL);
        String result = "";
        connection("POST", fullURL);

    return result;
    }

    public String reciveData(){
        fullURL= mainURL + "?getdata";
        System.out.println("Sending data to: "+fullURL);
        String result = "";
        connection("GET",fullURL);
        return result;
    }

    private String connection (String httpResquest ,String fullURL){
        String temp="";
        String line;
        try {
            url = new URL(fullURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                temp += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temp;
    }

}
