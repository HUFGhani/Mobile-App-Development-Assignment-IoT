package com.iot_mobile.hamzaghani.iot_mobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hamza Ghani 12077582
 */

public class SensorShow extends Activity {

    private ProgressDialog pDialog;


    // URL to get contacts JSON
    String email;
    String baseURL = "http://10.182.26.74:8080/IoT_Server/";
    String url;

    // JSON Node names
    private static final String TAG_SensorName = "sensorName";
    private static final String TAG_SensorValue = "sensorValue";
    private static final String TAG_TimeStamp = "TimeStamp";


    // Hashmap for ListView
    ArrayList<HashMap<String, String>> sensor;


    TextView s;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_show);

        sensor = new ArrayList<HashMap<String, String>>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("email");
        }

        s = (TextView) findViewById(R.id.text);
        // Calling async task to get json
        new GetSensor().execute();


    }

    public void open(View view){
        new opendoor().execute();
        new GetSensor().execute();
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetSensor extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SensorShow.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0 ) {
            url = baseURL+"sensorDB?getdata&email="+email;
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray json = new JSONArray(jsonStr);
                    // Getting JSON Array node
                    // contacts = jsonObj.getJSONArray(TAG_CONTACTS);

                    // looping through All Contacts
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject c = json.getJSONObject(i);

                        String SensorName = c.getString(TAG_SensorName);
                        String SensorValue = c.getString(TAG_SensorValue);
                        String TimeStamp = c.getString(TAG_TimeStamp);



                        // tmp hashmap for single contact
                        HashMap<String, String> temp = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        temp.put(TAG_SensorName, SensorName);
                        temp.put(TAG_SensorValue, SensorValue);
                        temp.put(TAG_TimeStamp, TimeStamp);

                        // adding contact to contact list
                        sensor.add(temp);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */


            if (sensor.size()>0) {
                for (HashMap<String, String> hashMap : sensor) {
                    if (hashMap.get(TAG_SensorValue).equals("true")){
                        s.setText("door open");
                    }else{
                        s.setText("door lock");
                    }
                }

            }

        }

    }

    private class opendoor  extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            url = baseURL +"sensorDB?sensorname=DoorRFID&sensorvalue=true&email="+email;
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            sh.makeServiceCall(url, ServiceHandler.POST);
            return null;
        }
    }
}

