package com.example.hp.verifyimagination.verify.layout.funtion;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


/**
 * Created by hp on 18-6-20.
 */

public class GLSurfaceActivity extends AppCompatActivity {

    GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        Toast.makeText(this,"as wallpaper",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    private void init() {
        mGLSurfaceView = new GLSurfaceView(this);
    }


}
