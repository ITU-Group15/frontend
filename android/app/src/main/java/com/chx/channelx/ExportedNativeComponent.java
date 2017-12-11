package com.itugroup15.channelx;

import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.Map;

public class ExportedNativeComponent extends SimpleViewManager {

    @Override
    public String getName() {
        return "ExportedNativeComponent";
    }

    @Override
    protected View createViewInstance(ThemedReactContext reactContext) {
        return null;
    }

    @Override
    public @Nullable
    Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder
            .builder()
            .put("topChange", MapBuilder.of(
                 "registrationName", "onChange")
            )
            .build();
    }

}
