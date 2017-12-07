package com.chx;

import android.content.Intent;

import com.chx.channelx.WelcomeActivity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.chx.MainApplication;

public class ConnectToReactNativeModule extends ReactContextBaseJavaModule {

    public ConnectToReactNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "bridgeBetweenAndroidAndReactNative";
    }

    @ReactMethod
    public void connect() {
        Intent intent = new Intent(getReactApplicationContext(), WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getReactApplicationContext().startActivity(intent);
    }

}
