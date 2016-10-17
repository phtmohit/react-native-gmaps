package com.rota.rngmaps;

import android.graphics.Color;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Created by Mohit Sharma <mohits@swiftsetup.com> on 2016-09-21.
 */
public class RNGMapsSnapshotModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "RNGMapsSnapshotModule";
    private RNGMapsViewManager mView;

    public RNGMapsSnapshotModule(ReactApplicationContext reactContext, RNGMapsViewManager view) {
        super(reactContext);
        mView = view;
    }
    @ReactMethod
    public void snapshot(final ReadableMap points, final Callback cb) {

      UiThreadUtil.runOnUiThread(new Runnable()
         {
          @Override
          public void run()
          {
            mView.autoZoomMap(points);
          }
      });
      SnapshotReadyCallback callback = new SnapshotReadyCallback() {
           Bitmap bitmap;
           @Override
           public void onSnapshotReady(Bitmap snapshot) {
               bitmap = snapshot;
               try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                byte[] bytes = ((ByteArrayOutputStream) out).toByteArray();
                String data = Base64.encodeToString(bytes, Base64.NO_WRAP);
                data = "data:image/png;base64," + data;
                cb.invoke(data);
               } catch (Exception e) {
                   e.printStackTrace();
                   cb.invoke(false);
               }
           }
       };
       mView.getMap().snapshot(callback);
    }
    @Override
    public String getName() {
        return REACT_CLASS;
    }
}
