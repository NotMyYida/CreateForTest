package com.example.hp.verifyimagination.verify.layout.funtion;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;
import com.example.hp.verifyimagination.service.DestoryService;
import com.example.hp.verifyimagination.view.RotateImageView;

/**
 * Created by hp on 18-5-9.
 */

public class ImageRotationActivity extends BaseActivity {

    private RotateImageView imgIcon;
    private int rotateDegree = 0 ;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("ImageRotationActivity","onServiceConnected ->");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("ImageRotationActivity","onServiceDisconnected ->");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar("旋转的图标");
        setToolbarDisplayHomeAsUp(true);
        imgIcon = findViewById(R.id.img_icon);
        Button btnRotate = findViewById(R.id.btn_rotate);
        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateDegree += 90 ;
                rotateDegree = rotateDegree % 360 ;
                imgIcon.setOrientation(rotateDegree, true );
            }
        });

        final Intent intentService = new Intent(this, DestoryService.class);
//        bindService(intentService,conn,Context.BIND_AUTO_CREATE);

        final Button btnBindService = findViewById(R.id.btn_bindService);
        btnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ImageRotation","bind service ~");
                bindService(intentService,conn,Context.BIND_AUTO_CREATE);
//                startService(intentService);
            }

        });

        Button btnChoose = findViewById(R.id.btn_choose);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent send = new Intent(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(send,"Send"),0);
//                bindService(intentService,conn,Context.BIND_AUTO_CREATE);
                Log.e("ImageRotation","stop service ~");
//                stopService(intentService);
                unbindService(conn);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CaptureRequest.Key<Boolean> blackLevelLock = CaptureRequest.BLACK_LEVEL_LOCK;
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                CameraCharacteristics mCameraCharacteristics = cameraManager.getCameraCharacteristics("0");
                Integer integer = mCameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
                Log.e("ImageRotation","0 -> level : "+integer);

                CameraCharacteristics mCameraCharacteristics2 = cameraManager.getCameraCharacteristics("1");
                Integer integer2 = mCameraCharacteristics2.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
                Log.e("ImageRotation","1 -> level : "+integer2);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }


        Spinner spinner = findViewById(R.id.spinner);
        spinner.setPrompt("Prompt");
        String[] spinnerStrs = new String[]{"all","audio","video","zip"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.search_spinner_item,
                spinnerStrs);
        spinner.setAdapter(adapter);

        String manufacture = Build.MANUFACTURER;
        String model = Build.MODEL;
        Log.e("ImageRotation"," onCreate ->  manufacture : "+manufacture+"  model : "+model);

//        ColorUtils.blendARGB();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rotation_image;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if( isFinishing() ){
            Log.e("ImageRotation","isFinishing");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = newConfig.orientation;
        switch (orientation){
            case 0 :

                break;

            case Configuration.ORIENTATION_PORTRAIT :
                imgIcon.setOrientation(0,true);
                break;

            case 2 :
                imgIcon.setOrientation(90,true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        Log.e("ImageRotation","onDestroy -> ");
    }
}
