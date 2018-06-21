package com.example.hp.verifyimagination;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.verifyimagination.adapter.BaseRecyclerAdapter;
import com.example.hp.verifyimagination.adapter.SmartViewHolder;
import com.example.hp.verifyimagination.base.BaseActivity;
import com.example.hp.verifyimagination.util.PermissionUtil;
import com.example.hp.verifyimagination.verify.layout.PopMenuActivity;
import com.example.hp.verifyimagination.verify.layout.VerifyLayoutActivity;
import com.example.hp.verifyimagination.verify.layout.VerifyTabLayoutActivity;
import com.example.hp.verifyimagination.verify.layout.VerifyTextureViewActivity;
import com.example.hp.verifyimagination.verify.layout.funtion.GetBatteryActivity;
import com.example.hp.verifyimagination.verify.layout.funtion.ImageRotationActivity;
import com.example.hp.verifyimagination.verify.layout.funtion.MatrixVerifyActivity;
import com.example.hp.verifyimagination.verify.layout.funtion.NDKTestActivity;
import com.example.hp.verifyimagination.verify.layout.funtion.PreferenceActivity;
import com.example.hp.verifyimagination.verify.layout.funtion.VerifyMatrixView;
import com.example.hp.verifyimagination.verify.layout.funtion.VerifyOpenGLActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Arrays;
import static android.R.layout.simple_list_item_2;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private enum Item {
        验证Layout(R.string.test_type_layout),
        验证Matrix(R.string.test_matrix),
        验证ＮＤＫ(R.string.test_ndk),
        验证TextureView(R.string.test_texture),
        验证TabLayout(R.string.test_tab_layout),
        验证OpenGL(R.string.test_opengl),
        验证Preference(R.string.test_preference),
        获取电量(R.string.get_battery),
        弹出Ｐｏｐ(R.string.test_pop),
        测试Ｒｏｔａｔｉｏｎ(R.string.test_rotation),
        ;
        public int nameId;
        Item(@StringRes int nameId) {
            this.nameId = nameId;
        }
    }

    private RefreshLayout mRefreshLayout;

//    private boolean isFirst = true;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar();
        setToolbarDisplayHomeAsUp(false);

        TextView tvToast = findViewById(R.id.tv_toast);
        tvToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"测试Toast",Toast.LENGTH_SHORT).show();
            }
        });

        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(3000);      // 3s 结束
            }
        });
//        if( isFirst ){
//            isFirst = false;
//            mRefreshLayout.autoRefresh();       // 第一次进入自动刷新
//        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new BaseRecyclerAdapter<Item>(Arrays.asList(Item.values()), simple_list_item_2,this) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Item model, int position) {
                holder.text(android.R.id.text1, model.name());
                holder.text(android.R.id.text2, model.nameId);
                holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position){
            case 0:
                intent = new Intent(this, VerifyLayoutActivity.class);
                intent.putExtra(Config.VERIFY_TYPE_XML,R.layout.activity_scroller_layout);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, MatrixVerifyActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, NDKTestActivity.class);
                startActivity(intent);
                break;
            case 3:
                if( PermissionUtil.checkPermission(this, Manifest.permission.CAMERA) ) {
                    intent = new Intent(this, VerifyTextureViewActivity.class);
                    startActivity(intent);
                }else{
                    PermissionUtil.reqestPermission(this,new String[]{Manifest.permission.CAMERA},0);
                }
                break;
            case 4:
                intent = new Intent(this,VerifyTabLayoutActivity.class);
                startActivity(intent);
                break;

            case 5:
                intent = new Intent(this, VerifyOpenGLActivity.class);
                startActivity(intent);
                break;

            case 6:
                intent = new Intent(this, PreferenceActivity.class);
                startActivity(intent);
                break;
            case 7:
                intent = new Intent(this, GetBatteryActivity.class);
                startActivity(intent);
                break;
            case 8:
                intent = new Intent(this, PopMenuActivity.class);
                startActivity(intent);
                break;
            case 9:
                intent = new Intent(this, ImageRotationActivity.class);
                startActivity(intent);
                break;
        }
//        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( requestCode == 0 ){
            if ( grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                Intent intent = new Intent(this, VerifyTextureViewActivity.class);
                startActivity(intent);
            }
        }
    }
}
