package com.example.hp.verifyimagination.verify.layout.funtion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;

/**
 * Created by hp on 18-4-20.
 */

public class NDKTestActivity extends BaseActivity {

    static {
        System.loadLibrary("hello");
    }

    private TextView tvNdk;

    public native String sayHello();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar("NDK 测试");
        setToolbarDisplayHomeAsUp(true);

        tvNdk = findViewById(R.id.tv_ndk);
        Button btnCallNdk = findViewById(R.id.btn_call_ndk);
        btnCallNdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ndkStr = sayHello();
                tvNdk.setText(ndkStr);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ndk_test;
    }
}
