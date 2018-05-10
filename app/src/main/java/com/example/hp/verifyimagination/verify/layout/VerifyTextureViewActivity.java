package com.example.hp.verifyimagination.verify.layout;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.TextureView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;

import java.io.IOException;

/**
 * Created by hp on 18-4-21.
 */

public class VerifyTextureViewActivity extends BaseActivity implements TextureView.SurfaceTextureListener{

    private Camera mCamera;
    private TextureView textureView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textureView = findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_textureview;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open();
        Camera.Size size = mCamera.getParameters().getPictureSize();
        textureView.setLayoutParams(new RelativeLayout.LayoutParams(
                size.width, size.height));
        try {
            mCamera.setPreviewTexture(surface);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
        textureView.setAlpha(0.5f);
        textureView.setRotation(90f);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
