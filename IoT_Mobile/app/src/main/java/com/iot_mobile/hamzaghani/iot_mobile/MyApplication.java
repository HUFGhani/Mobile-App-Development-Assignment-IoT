package com.iot_mobile.hamzaghani.iot_mobile;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;

/**
 * Created by hamzaghani on 08/12/2016.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        EstimoteSDK.initialize(getApplicationContext(), "iot-mobile-6dh", "d0f7fba88fde641d071eb6c89cd69d56");
    }
}
