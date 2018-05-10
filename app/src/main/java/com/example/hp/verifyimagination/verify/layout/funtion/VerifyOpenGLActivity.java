package com.example.hp.verifyimagination.verify.layout.funtion;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;
import com.example.hp.verifyimagination.opengl.OpenGLDemo;
import com.example.hp.verifyimagination.opengl.OpenGlRenderer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hp on 18-4-25.
 */

public class VerifyOpenGLActivity extends BaseActivity implements OpenGLDemo{

    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar();
        setToolbarDisplayHomeAsUp(true);

        LinearLayout llParent = findViewById(R.id.ll_parent);
        mGLSurfaceView = new GLSurfaceView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        llParent.addView(mGLSurfaceView, params);
        mGLSurfaceView.setRenderer(new OpenGlRenderer(this));

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_opengl;
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


    @Override
    public void drawScene(GL10 gl10) {
        gl10.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        // Clears the screen and depth buffer.
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);

    }
}
