package com.example.hp.verifyimagination.verify.layout.funtion;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;
import com.example.hp.verifyimagination.view.RotateImageView;

/**
 * Created by hp on 18-5-9.
 */

public class ImageRotationActivity extends BaseActivity {

    private RotateImageView imgIcon;
    private int rotateDegree = 0 ;

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
        Intent.ACTION_ATTACH_DATA;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rotation_image;
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
}
