package com.example.hp.verifyimagination.verify.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.hp.verifyimagination.Config;
import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;
import com.example.hp.verifyimagination.util.IntentUtils;

/**
 * Created by hp on 18-4-18.
 */

public class VerifyLayoutActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 从 MainActivity 获取到 xml 布局文件，通过 Intent 传递过来
        int xmlResourceID = IntentUtils.safeGetIntExtra(getIntent(), Config.VERIFY_TYPE_XML, 0);
        if( xmlResourceID == 0 ){
            Toast.makeText(this,"加载错误",Toast.LENGTH_SHORT).show();
            finish();
        }
        setContentView(xmlResourceID);
        setUpToolbar("布局文件");
        setToolbarDisplayHomeAsUp(true);

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
