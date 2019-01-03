package com.example.hp.verifyimagination.verify.layout.funtion;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;

/**
 * Created by brant on 18-7-10.
 */

public class OrientationActivity extends BaseActivity {

    private final String TAG = OrientationActivity.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_popmenu;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate() ->");
        PackageManager packageManager = getPackageManager();
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            int orientation = activityInfo.screenOrientation;
            Log.e("OrientationActivity","onCreate() -->>  orientation : "+orientation);// -1
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart() ->");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume() ->");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orien = newConfig.orientation;
        Log.e(TAG,"onConfigurationChanged ->  orientation : "+orien);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onPause() ->");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop() ->");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy() ->");
    }
}
