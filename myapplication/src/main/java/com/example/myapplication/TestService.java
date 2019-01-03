package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by brant on 18-12-7.
 */

public class TestService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TestService","onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("TestService","onStart");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TestService","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e("TestService","onLowMemory");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(),TestService.class);
        getApplicationContext().startService(intent);
        Log.e("TestService","onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
