package com.itugroup15.channelx.ExportNative;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.itugroup15.MainApplication;
import com.itugroup15.channelx.ChannelListActivity;
import com.itugroup15.channelx.LoginActivity;
import com.itugroup15.channelx.WelcomeActivity;

import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.itugroup15.channelx.ChannelListActivity.PREFS_NAME;

public class ExportNativeModule extends ReactContextBaseJavaModule {

    @Override
    public String getName() {
        return "ExportNative";
    }

    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";

    public ExportNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        return constants;
    }

    @ReactMethod
    public void show(String message, int duration) {
        Toast.makeText(getReactApplicationContext(), message, duration).show();
    }

    @ReactMethod
    public void logout() {
        Intent intent = new Intent(getReactApplicationContext(), ChannelListActivity.class);
        intent.putExtra("flag", "logout");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getReactApplicationContext().startActivity(intent);
    }

    @ReactMethod
    public void go_back_to_channel_list() {
        Intent intent = new Intent(getReactApplicationContext(), ChannelListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getReactApplicationContext().startActivity(intent);
    }

    @ReactMethod
    public void connect() {
        Intent intent = new Intent(getReactApplicationContext(), WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getReactApplicationContext().startActivity(intent);
    }

}
