package com.example.hp.verifyimagination.verify.layout.funtion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;

/**
 * Created by brant on 18-7-2.
 */

public class TestPerformClickActivity extends BaseActivity implements View.OnClickListener{

    private Button btnShowToast;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_perform_click;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnShowToast = findViewById(R.id.btn_show_toast);
        btnShowToast.setOnClickListener(this);

        Button btnTestPerformClick = findViewById(R.id.btn_test_perform_click);
        btnTestPerformClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnShowToast.performClick();
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_show_toast:
                Toast.makeText(this,"Show a Toast!!!",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
