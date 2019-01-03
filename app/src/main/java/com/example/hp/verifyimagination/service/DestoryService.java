package com.example.hp.verifyimagination.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by hp on 18-6-26.
 */

public class DestoryService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String action = intent.getAction();
        Log.e("DestoryService","onBind --> "+action);
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("DestoryService","onCreate --> ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("DestoryService","onStartCommand --> ");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
        Log.e("DestoryService","onDestroy -> ");
    }
}
