package com.example.hp.verifyimagination.verify.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;
import com.example.hp.verifyimagination.view.HorizontalSelectedView;
import com.example.hp.verifyimagination.view.VerticalSeekBar;

import java.util.ArrayList;

/**
 * Created by hp on 18-4-23.
 */

public class VerifyTabLayoutActivity extends BaseActivity {

    private VerticalSeekBar verticalSeekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar("验证TabLayout");
        setToolbarDisplayHomeAsUp(true);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorHeight(8);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CharSequence text = tab.getText();
                Toast.makeText(VerifyTabLayoutActivity.this,text,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        verticalSeekBar = findViewById(R.id.vertical_seekBar);
        verticalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(VerifyTabLayoutActivity.this,"progress : "+progress,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                int progress = seekBar.getProgress();
//                Toast.makeText(VerifyTabLayoutActivity.this,"progress : "+progress,Toast.LENGTH_SHORT).show();
            }
        });

        HorizontalSelectedView horizontalselectedView = findViewById(R.id.horizontalSelectedView);
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("慢动作");
        strings.add("视频");
        strings.add("照片");
        strings.add("人像");
        strings.add("全景");
        horizontalselectedView.setData(strings);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify_tablayout;
    }
}
