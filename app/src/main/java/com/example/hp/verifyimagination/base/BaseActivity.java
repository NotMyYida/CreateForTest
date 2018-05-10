package com.example.hp.verifyimagination.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hp.verifyimagination.R;

/**
 * Created by hp on 18-4-18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

    }


    /**
     * 获取到布局id
     * @return
     */
    protected abstract int getLayoutId();

    protected void setUpToolbar(){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    /**
     * 设置标题
     * @param title
     */
    protected void setUpToolbar(String title){
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
    }

    /**
     * 设置后退按钮是否可见
     * @param displayHome   false 后退按钮不可见
     *                      true 后退按钮可见,点击可以后退
     */
    protected void setToolbarDisplayHomeAsUp(boolean displayHome){
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHome);
        if( displayHome ){
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }
}
