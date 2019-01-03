package com.example.hp.verifyimagination.verify.layout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;
import com.example.hp.verifyimagination.popmenu.PopMenuMore;
import com.example.hp.verifyimagination.popmenu.PopMenuMoreItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by hp on 18-5-2.
 */

public class PopMenuActivity extends BaseActivity implements View.OnClickListener{

    private PopMenuMore popMenuMore;
    private ImageView ivBitmap;
    private View viewTestLayoutParameter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar("弹出ＰＯＰ");
        setToolbarDisplayHomeAsUp(true);
        TextView tvPop = findViewById(R.id.tv_pop);
        initPopMenu();
        tvPop.setOnClickListener(this);
//        tvPop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(PopMenuActivity.this,"Toast Menu",Toast.LENGTH_SHORT).show();
//            }
//        });

        ivBitmap = findViewById(R.id.iv_bitmap);
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if( permission == PackageManager.PERMISSION_GRANTED ){
            loadBitmap();
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

        Button btnSetLayoutParameter = findViewById(R.id.btn_set_layout_parameter);
        btnSetLayoutParameter.setOnClickListener(this);

        viewTestLayoutParameter = findViewById(R.id.view_test_layoutParameter);

    }

    public void loadBitmap(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.matrix);
//        ivBitmap.setImageBitmap(bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();
        String file =Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/"+"test.jpeg";
        try {
            FileOutputStream fos = new FileOutputStream(new File(file));
            fos.write(data,0,data.length);
            fos.flush();
            fos.close();
            Toast.makeText(this,"已保存",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
        }
    }


    private void initPopMenu() {
        popMenuMore = new PopMenuMore(this);
        popMenuMore.setBackgroundColor(Color.parseColor("#FFD700"));
        ArrayList<PopMenuMoreItem> popMenuItems = new ArrayList<PopMenuMoreItem>();
        popMenuItems.add(new PopMenuMoreItem(1,"葛平"));
        popMenuItems.add(new PopMenuMoreItem(2,"张召忠"));
        popMenuItems.add(new PopMenuMoreItem(3,"金坷垃"));
        popMenuItems.add(new PopMenuMoreItem(4,"洛天依"));
        popMenuItems.add(new PopMenuMoreItem(5,"面筋哥"));
        popMenuItems.add(new PopMenuMoreItem(6,"雷总"));
        popMenuMore.addItems(popMenuItems);
        popMenuMore.setOnItemSelectedListener(new PopMenuMore.OnItemSelectedListener() {
            @Override
            public void selected(View view, PopMenuMoreItem item, int position) {
                switch(item.getId()){
                    case 1:
                        Toast.makeText(PopMenuActivity.this,"小朋友们,有谁想和我学蓝猫的配音啊",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(PopMenuActivity.this,"雾霾能够防导弹",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(PopMenuActivity.this,"非洲农业不发达,必须要有金坷垃",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(PopMenuActivity.this,"后面的朋友们让我看到你们的双手",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(PopMenuActivity.this,"烤面筋烤面筋,我的烤面筋",Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(PopMenuActivity.this,"Are you OK",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_popmenu;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch( id ){
            case R.id.tv_pop:
                popMenuMore.showAsDropDown(v);
                break;

            case R.id.btn_set_layout_parameter:
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 80);
                layoutParams.setMargins(0,100,0,100);
                viewTestLayoutParameter.setLayoutParams(layoutParams);
                viewTestLayoutParameter.requestLayout();
                break;
        }

    }
}
