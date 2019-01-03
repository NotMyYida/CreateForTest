package com.example.myapplication;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by brant on 18-11-28.
 */

public class FlashActivity extends AppCompatActivity {

    private boolean isopen = false; //记录手电筒状态
    private Camera camera; //
    private Button btn_flash;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        btn_flash = (Button) findViewById(R.id.btn_flash);
        btn_flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isopen) { //如果手电筒已经开启
                    Toast.makeText(getApplicationContext(), "手电筒已开启", 0).show();
                    camera = Camera.open();
                    Camera.Parameters params = camera.getParameters();
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(params);
                    camera.startPreview(); // 开始亮灯
                    isopen = true;
                    btn_flash.setText("手电筒已开启！");
                } else {
                    Toast.makeText(getApplicationContext(), "关闭了手电筒",
                            Toast.LENGTH_SHORT).show();
                    camera.stopPreview(); // 关掉亮灯
                    camera.release(); // 关掉照相机
                    isopen = false;
                    btn_flash.setText("手电筒已关闭！");
                }
            }
        });
    }
}
