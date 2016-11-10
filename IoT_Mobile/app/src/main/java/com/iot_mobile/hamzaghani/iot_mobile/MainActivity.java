package com.iot_mobile.hamzaghani.iot_mobile;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends Activity implements LocationListener {

    private static final int REQUEST_LOCATION = 2;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    private TextView latitude, longitude;

    private double fusedLatitude = 0.0;
    private  double fusedLongitude = 0.0;


    private static int UPDATE_INTERVAL = 1000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 1; // 1 meters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        if (checkPlayServices()) {
            startFusedLocation();
            registerRequestUpdate(this);
        }

    }

    private void initializeViews() {
        latitude = (TextView) findViewById(R.id.textview_latitude);
        longitude = (TextView) findViewById(R.id.textview_longitude);
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
                        "This device is supported. Please download google play services", Toast.LENGTH_LONG)
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
                != PackageManager.PERMISSION_GRANTED)){
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
            // permission has been granted, continue as usual
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setFastestInterval(FATEST_INTERVAL);
            mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
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
            }, 1000);
        }
    }

    public boolean isGoogleApiClientConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    @Override
    public void onLocationChanged(Location location) {
        setFusedLatitude(location.getLatitude());
        setFusedLongitude(location.getLongitude());

        Toast.makeText(getApplicationContext(), "NEW LOCATION RECEIVED", Toast.LENGTH_LONG).show();

        latitude.setText(getString(R.string.latitude_string) +" "+ getFusedLatitude());
        longitude.setText(getString(R.string.longitude_string) +" "+ getFusedLongitude());
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
}
