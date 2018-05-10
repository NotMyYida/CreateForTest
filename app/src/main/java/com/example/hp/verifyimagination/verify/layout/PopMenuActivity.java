package com.example.hp.verifyimagination.verify.layout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;
import com.example.hp.verifyimagination.popmenu.PopMenuMore;
import com.example.hp.verifyimagination.popmenu.PopMenuMoreItem;

import java.util.ArrayList;

/**
 * Created by hp on 18-5-2.
 */

public class PopMenuActivity extends BaseActivity implements View.OnClickListener{

    private PopMenuMore popMenuMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar("弹出ＰＯＰ");
        setToolbarDisplayHomeAsUp(true);
        TextView tvPop = findViewById(R.id.tv_pop);
        initPopMenu();
        tvPop.setOnClickListener(this);
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
        popMenuMore.showAsDropDown(v);
    }
}
