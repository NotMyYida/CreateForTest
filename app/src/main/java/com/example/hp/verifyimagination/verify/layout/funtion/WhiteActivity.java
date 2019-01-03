package com.example.hp.verifyimagination.verify.layout.funtion;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.hp.verifyimagination.R;

/**
 * Created by brant on 18-7-6.
 */

public class WhiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white);
        Log.e("WhiteActivity","onCreate() -> ");
        RelativeLayout rlWhite = findViewById(R.id.rl_white);
        ImageView img = new ImageView(this);
        img.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_focusing_sun));
        DisplayMetrics dm = getResources().getDisplayMetrics();
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(dm.widthPixels,
                        dm.heightPixels);
        rlWhite.addView(img,params);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("WhiteActivity","onDestroy() -> ");
    }
}
