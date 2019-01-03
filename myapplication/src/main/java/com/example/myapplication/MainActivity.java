package com.example.myapplication;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.zackratos.ultimatebar.UltimateBar;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends Activity implements View.OnClickListener{

    private View view;
    private TextView tvHello;
    private View viewShot;
    private ImageView iv;
    private Button btnScale;
    private ImageView ivBlur;

    private int aX;
    private int aY;
    private int aZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.v("msgwsc",getString(android.R.string.phoneTypeCustom));
        int resIdStatusbarHeight = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        int navigationbarHeight = getResources().getDimensionPixelSize(resIdStatusbarHeight);//navigationBar高度
        Log.e("MainActivity","onCreate -> navigation bar height : "+navigationbarHeight);

        tvHello = findViewById(R.id.tv_hello);
        tvHello.setOnClickListener(this);
        viewShot = findViewById(R.id.view_shot);
        viewShot.setOnClickListener(this);
        iv = findViewById(R.id.iv);
        btnScale = findViewById(R.id.btn_scale);
        btnScale.setOnClickListener(this);
        iv.setOnClickListener(this);

        ivBlur = findViewById(R.id.iv_blur);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        view = findViewById(R.id.layout_content);
        view.post(new Runnable() {
            @Override
            public void run() {
                Log.e("Main","view  Width : "+ view.getWidth()+"  Height : "+ view.getHeight());
            }
        });


        String defaultUserAgent = WebSettings.getDefaultUserAgent(this);
        Log.e("Main","UA : "+defaultUserAgent);

        String ip = getIPAddress(this);

        Log.e("Main","ip : "+ip);

        getWindow().getDecorView().setSystemUiVisibility
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS|WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        view.post(new Runnable() {
            @Override
            public void run() {
                Log.e("Main","view  Width : "+ view.getWidth()+"  Height : "+ view.getHeight());
            }
        });

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        getWindow().getDecorView().setSystemUiVisibility
//                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        getWindow().setNavigationBarColor(Color.TRANSPARENT);

//        UltimateBar.newImmersionBuilder()

//                .applyNav(true)         // 是否应用到导航栏
//                .build(this)
//                .apply();

//        UltimateBar.newTransparentBuilder()
//                .applyNav(true)                 // 是否应用到导航栏
//                .navColor(Color.TRANSPARENT)          // 导航栏颜色
//                .navAlpha(100)                  // 导航栏透明度
//                .build(this)
//                .apply();

        ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        mActivityManager.getMemoryInfo(memoryInfo);
        Log.e("tag","系统剩余内存:"+(memoryInfo.availMem >> 10)+"k");
        Log.e("tag","系统是否处于低内存运行："+memoryInfo.lowMemory);
        Log.e("tag","当系统剩余内存低于"+memoryInfo.threshold+"时就看成低内存运行");
    }


    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        mActivityManager.getMemoryInfo(memoryInfo);
        Log.e("tag","系统剩余内存:"+(memoryInfo.availMem/1024/1024)+"M");
        Log.e("tag","系统是否处于低内存运行："+memoryInfo.lowMemory);
        Log.e("tag","当系统剩余内存低于"+(memoryInfo.threshold/1024/1024)+"M 时就看成低内存运行");
    }

    boolean revert = true;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tv_hello:
                if( revert ){
//                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    getWindow().setNavigationBarColor(Color.TRANSPARENT);
                    Log.e("Main","View height : "+view.getHeight());
                } else{
//                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    getWindow().setNavigationBarColor(Color.BLACK);
                    Log.e("Main","2 View height : "+view.getHeight());
                }
                revert = !revert;
                break;

            case R.id.view_shot:
                Toast.makeText(this,"begin to load",Toast.LENGTH_LONG).show();
                tvHello.buildDrawingCache();
//                Bitmap drawingCache = tvHello.getDrawingCache();
//                iv.setImageBitmap(drawingCache);
                iv.setVisibility(View.INVISIBLE);
                break;

            case R.id.btn_scale:
                animateScale();
                iv.setImageResource(R.mipmap.ic_launcher);
                break;

            case R.id.iv:
                iv.buildDrawingCache();
                Bitmap drawingCache1 = iv.getDrawingCache();
                Bitmap fastblur = Blur.fastblur(this, drawingCache1, 30);
                ivBlur.setImageBitmap(fastblur);
                break;
        }
    }

    public void animateScale(){
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(
//                ObjectAnimator.ofFloat(btnScale , "scaleX" , 1f , 5f ),
//                ObjectAnimator.ofFloat(btnScale , "scaleY" , 1f , 5f )
//        );
//
//        set.setDuration(2000).start();
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator = ValueAnimator.ofInt(160,720).setDuration(800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int width = (int) animation.getAnimatedValue();
                Log.e("Main","width : "+width);
                ViewGroup.LayoutParams layoutParams = btnScale.getLayoutParams();
                layoutParams.height = width;
                btnScale.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
    }


    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


}
