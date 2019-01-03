package com.example.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by brant on 18-10-27.
 */

public class CountService extends IntentService {

    public final static String EXTRA_NUMBER = "extra_number";
    public String TAG = CountService.class.getSimpleName();

    public CountService() {
        super("CountService");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG,"onStart --> startId : "+startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand -->> startId : "+startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int count = intent.getIntExtra(EXTRA_NUMBER, 1);
        for (int i = 0 ; i < count ; i++ ){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e(TAG,"onHandleIntent --> counting "+i);
        }
        Log.e(TAG,"onHandleIntent count end");
    }


    @Override
    public void onDestroy() {
//        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }
}
