package com.example.myapplication;

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by brant on 18-12-3.
 */

public class TextActivity extends AppCompatActivity {

    private EditText et;
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_activity);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (displayMetrics.heightPixels * 0.7);
        p.width = (int) (displayMetrics.widthPixels * 0.95);
        getWindow().setAttributes(p);
        Log.e("TAG","click");
        et = findViewById(R.id.et);

        tv = findViewById(R.id.tv);
//        String content = "我叫<font color=\"#FF0000\">张小龙</font>";
//        String content2 = "我叫张小龙";
//        Spanned spanned = Html.fromHtml(content);
//        tv.setText(spanned);

        Button btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","click"+Integer.MAX_VALUE+"    "+Integer.MIN_VALUE);
//                int num = Integer.parseInt(et.getText().toString());
//                int reverseNum = reverse2(num);
//                tv.setText(""+reverseNum);
                Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
                TextActivity.this.sendBroadcast(intent);
            }
        });
        
        try {
//            Class<?> aClass = Class.forName("com.example.myapplication.MyInterface");
            Class aClass = MyInterface.class;
            Method test = aClass.getMethod("test");
            Constructor constructor = aClass.getConstructor();
            Object o = constructor.newInstance();
            test.invoke(o);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        Log.e("Text","onCreate()");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG","onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG","onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("TAG","onRestart()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("TAG","onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("TAG","onRestoreInstanceState");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG","onStop()");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("TAG","onConfigurationChanged()");
    }

    public int reverse(int x) {
        if(x/10 == 0){
            return x;
        }

        if(x > 0){
            int reverseNum = 0;
            int lastNum = x%10;
            while(x/10 != 0){
                reverseNum =reverseNum*10 + lastNum;
                x = x/10;
                lastNum = x%10;
            }
            return reverseNum*10 + lastNum;
        }
        else{
            x = -x;
            Log.e("TAG","-x = "+x);
            int reverseNum = 0;
            int lastNum = x%10;
            while(x/10 != 0){
                reverseNum =reverseNum*10 + lastNum;
                x = x/10;
                lastNum = x%10;
            }
            return -(reverseNum*10 + lastNum);
        }
    }

    int MAX = Integer.MAX_VALUE;
    int MIN = Integer.MIN_VALUE;
    public int reverse2(int x) {
        int i = 0;
        if(x/10 == 0){
            return x;
        }

        if(x>0){
            int reverseNum = 0;
            int lastNum = x%10;
            while(x/10 != 0){
                reverseNum =reverseNum*10 + lastNum;
                x = x/10;
                lastNum = x%10;
                i++;
            }
            if(i > 8){
                if(reverseNum > MAX/10){
                    return 0;
                }else if(reverseNum == MAX/10){
                    if(lastNum > 7){
                        return 0;
                    }
                }
            }
            return reverseNum*10 + lastNum;
        }
        else{
            x = -x;
            if(x == -x){
                return 0;
            }
            Log.e("TAG","-x = "+x);
            int reverseNum = 0;
            int lastNum = x%10;
            while(x/10 != 0){
                reverseNum =reverseNum*10 + lastNum;
                x = x/10;
                lastNum = x%10;
                i++;
            }

            if(i > 8){
                Log.e("TAG","reverseNum : "+reverseNum);
                if(reverseNum > MAX/10){
                    return 0;
                }else if(reverseNum == MAX/10){
                    if(lastNum > 7){
                        return 0;
                    }
                }
            }

            return -(reverseNum*10 + lastNum);
        }
    }
}
/*

class Solution {
    static int MAX = 2147483647;
    public static int reverse(int x) {
        int i = 0;
        if(x/10 == 0){
            return x;
        }

        if(x>0){
            int reverseNum = 0;
            int lastNum = x%10;
            while(x/10 != 0){
                reverseNum =reverseNum*10 + lastNum;
                x = x/10;
                lastNum = x%10;
                i++;
            }
            if(i > 8){
                if(reverseNum > MAX/10){
                    return 0;
                }else if(reverseNum == MAX/10){
                    if(lastNum > 7){
                        return 0;
                    }
                }
            }
            return reverseNum*10 + lastNum;
        }
        else{
            x = -x;
            int reverseNum = 0;
            int lastNum = x%10;
            while(x/10 != 0){
                reverseNum =reverseNum*10 + lastNum;
                x = x/10;
                lastNum = x%10;
                i++;
            }

            if(i > 8){
                if(reverseNum > MAX/10){
                    return 0;
                }else if(reverseNum == MAX/10){
                    if(lastNum >= 7){
                        return 0;
                    }
                }
            }

            return -(reverseNum*10 + lastNum);
            }
    }

    public static void main(String[] args){
        int num = reverse(-123);
        System.out.println(num);
    }

}

 */