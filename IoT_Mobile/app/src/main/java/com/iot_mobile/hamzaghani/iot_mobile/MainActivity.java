package com.iot_mobile.hamzaghani.iot_mobile;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.estimote.sdk.SystemRequirementsChecker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.iot_mobile.hamzaghani.iot_mobile.estimote.BeaconID;
import com.iot_mobile.hamzaghani.iot_mobile.estimote.EstimoteCloudBeaconDetails;
import com.iot_mobile.hamzaghani.iot_mobile.estimote.EstimoteCloudBeaconDetailsFactory;
import com.iot_mobile.hamzaghani.iot_mobile.estimote.ProximityContentManager;

import java.util.Arrays;

import static com.google.android.gms.wearable.DataMap.TAG;

public class MainActivity extends Activity implements LocationListener {

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_LOCATION = 2;

    private LocationRequest mLocationRequest;


    private double fusedLatitude = 0.0;
    private  double fusedLongitude = 0.0;


    String url, url2;
    private static final String baseURL="http://10.182.26.74:8080/IoT_Server/";
    private Dialog pDialog;

    EditText emailAddress;

    String email;
    Button login,map,sensor;
    boolean test;
    private ProximityContentManager proximityContentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailAddress =  (EditText) findViewById(R.id.emailAddress);
        login = (Button) findViewById(R.id.login);
        map = (Button) findViewById(R.id.locaiton);
        sensor = (Button) findViewById(R.id.sensor_status);

        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 28322, 44007)),
                new EstimoteCloudBeaconDetailsFactory());
        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {

                Integer backgroundColor;
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    test = true;

                } else {
                    test = false;

                }
            }
        });
    }

    public void map(View view){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        MainActivity.this.startActivity(intent);
    }
    public void login(View view){

        email = emailAddress.getText().toString();
        if (email != null && email!=  "" ) {
            if (checkPlayServices()) {
                startFusedLocation();
                registerRequestUpdate(this);
                emailAddress.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);
                sensor.setVisibility(View.VISIBLE);
                map.setVisibility(View.VISIBLE);
                sendData2Server();
            }

        }
    }

    public void sensorShow(View view){
        Intent intent = new Intent(MainActivity.this,SensorShow.class);
        intent.putExtra("email",email);
        MainActivity.this.startActivity(intent);
    }


    @Override
    protected void onStop() {
        stopFusedLocation();
        super.onStop();
    }


    // check if google play services is installed on the device
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(),
                        "This device is supported. Please download google play services",
                                Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    public void startFusedLocation() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnectionSuspended(int cause) {
                        }

                        @Override
                        public void onConnected(Bundle connectionHint) {

                        }
                    }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {

                        @Override
                        public void onConnectionFailed(ConnectionResult result) {

                        }
                    }).build();
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.connect();
        }
    }

    public void stopFusedLocation() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public void registerRequestUpdate(final LocationListener listener) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED)) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    REQUEST_LOCATION);
            registerRequestUpdate(listener);
        } else {


            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(1000); // every second

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                                mLocationRequest, listener);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!isGoogleApiClientConnected()) {
                            mGoogleApiClient.connect();
                        }
                        registerRequestUpdate(listener);
                    }
                }
            }, 10000);
        }
    }

    public boolean isGoogleApiClientConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    @Override
    public void onLocationChanged(Location location) {
           setFusedLatitude(location.getLatitude());
            setFusedLongitude(location.getLongitude());
        if (!(email.equals(null)) || email.equals("")) {
            sendData2Server();
        }

    }



    public void setFusedLatitude(double lat) {
        fusedLatitude = lat;
    }

    public void setFusedLongitude(double lon) {
        fusedLongitude = lon;
    }

    public double getFusedLatitude() {
        return fusedLatitude;
    }

    public double getFusedLongitude() {
        return fusedLongitude;
    }

    private void sendData2Server() {
        new sendLocation().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        proximityContentManager.stopContentUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proximityContentManager.destroy();
    }

    private class sendLocation extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                send();
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return null;
        }

        public void send() {
            if (!(email.equals(null)) || email.equals("")) {
                url = baseURL + "locationDB?lat=" + getFusedLatitude() + "&lng=" + getFusedLongitude() + "&email=" + email;
                url2 = baseURL +"sensorname=DoorRFID&sensorvalue="+test+"&email="+email;
                // Creating service handler class instance
                ServiceHandler sh = new ServiceHandler();

                // Making a request to url and getting response
                sh.makeServiceCall(url, ServiceHandler.POST);
                sh.makeServiceCall(url2, ServiceHandler.POST);
            }
        }
    }


}
