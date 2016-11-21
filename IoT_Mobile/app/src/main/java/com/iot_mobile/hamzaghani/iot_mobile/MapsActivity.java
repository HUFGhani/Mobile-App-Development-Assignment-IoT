package com.iot_mobile.hamzaghani.iot_mobile;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ProgressDialog pDialog;
    private static final String TAG_Longitude = "Longitude";
    private static final String TAG_Latitude = "Latitude";
    String url;
    private static final String baseURL="http://10.182.17.145:8080/IoT_Server/locationDB";

    MarkerOptions marker;
    ArrayList<HashMap<String, String>> location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        location = new ArrayList<HashMap<String, String>>();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        new getLocation().execute();

    }

    private class getLocation extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MapsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ServiceHandler sh = new ServiceHandler();
            url = baseURL +"?data";

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


                        String Longitude = c.getString(TAG_Longitude);
                        String Latitude = c.getString(TAG_Latitude);

                        // tmp hashmap for single contact
                        HashMap<String, String> loc = new HashMap<String, String>();

                        // adding each child node to HashMap key => value

                        loc.put(TAG_Longitude, Longitude);
                        loc.put(TAG_Latitude, Latitude);
                        location.add(loc);

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

            if (location.size()>0) {
                if(mMap != null) {
                    for (HashMap<String, String> hashMap : location) {
                        double currentLatitude = Double.parseDouble(hashMap.get(TAG_Latitude));
                        double currentLongitude = Double.parseDouble(hashMap.get(TAG_Longitude));
                        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
                        marker = new MarkerOptions()
                                .position((latLng));
                        mMap.addMarker(marker);
                    }
                }
            }
        }
    }
}
