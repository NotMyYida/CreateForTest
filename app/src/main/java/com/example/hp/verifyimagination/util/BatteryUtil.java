package com.example.hp.verifyimagination.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by hp on 18-4-28.
 */

public class BatteryUtil {

    /**
     * 电池电量监听接口
     */
    public interface BatteryLevelChangeListener{
        void onBatteryLevelChange(int level);
    }

    public static BatteryLevelChangeListener mBatteryLevelChangeListener ;

    private static int batteryLevel = 100;

    private static BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_BATTERY_CHANGED)){
                batteryLevel = intent.getIntExtra("level", 0);
                mBatteryLevelChangeListener.onBatteryLevelChange(batteryLevel);
            }
        }
    };

    private static IntentFilter getBatteryIntentFilter(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        return filter;
    }

    /**
     * 注册电池广播
     * @param context
     */
    public static void registerBatteryChangedListener(Context context){
        context.registerReceiver(receiver,getBatteryIntentFilter());
    }

    /**
     * 注销电池广播
     * @param context
     */
    public static void unRegisterBatteryChangedListener(Context context){
        context.unregisterReceiver(receiver);
    }

    public static void setBatteryLevelChangeListener(BatteryLevelChangeListener batteryLevelChangeListener){
        mBatteryLevelChangeListener = batteryLevelChangeListener ;
    }

    /**
     * 获取当前电池电量
     * @return
     */
    public static int getCurrentBatteryLevel(){
        return batteryLevel ;
    }
}
