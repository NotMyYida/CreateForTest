package com.example.hp.verifyimagination.verify.layout.funtion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;
import com.example.hp.verifyimagination.dialog.LowBatteryDialog;
import com.example.hp.verifyimagination.util.BatteryUtil;

/**
 * Created by hp on 18-4-28.
 */

public class GetBatteryActivity extends BaseActivity implements BatteryUtil.BatteryLevelChangeListener {

    private TextView tvBattery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar("获取电量");
        setToolbarDisplayHomeAsUp(true);
        BatteryUtil.registerBatteryChangedListener(this);
        BatteryUtil.setBatteryLevelChangeListener(this);

        tvBattery = findViewById(R.id.tv_battery);
        Button btnGet = findViewById(R.id.btn_get_battery);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBatteryActivity.this,"当前电量为 ; "+BatteryUtil.getCurrentBatteryLevel(),Toast.LENGTH_SHORT).show();
            }
        });
        Button btnShowLowBattery = findViewById(R.id.btn_show_low_battery);
        btnShowLowBattery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LowBatteryDialog dialog = new LowBatteryDialog(GetBatteryActivity.this);
                dialog.show();
            }
        });

        // 获取 dpi 等信息
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Log.e("Dpi","densityDpi : "+displayMetrics.densityDpi+"  density : "+displayMetrics.density);

        TextView tvDensityDpi = findViewById(R.id.tv_densityDpi);
        TextView tvDensity = findViewById(R.id.tv_density);
        tvDensityDpi.setText("densityDpi : "+displayMetrics.densityDpi);
        tvDensity.setText("density : "+displayMetrics.density);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_battery;
    }

    @Override
    public void onBatteryLevelChange(int level) {
        tvBattery.setText("当前电量为 : "+level);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BatteryUtil.unRegisterBatteryChangedListener(this);
    }
}
