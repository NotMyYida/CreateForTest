package com.example.myapplication.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.TextActivity;

/**
 * Created by brant on 18-12-29.
 */

public class TestAppWidgetProvider extends AppWidgetProvider {

    public static final String CLICK_ACTION = "CLICK_ACTION";
    boolean reverse = true;
    private CharSequence charSequence;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intent = new Intent(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, TextActivity.class), 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_widget,pendingIntent);
        Log.e("APPWidgetProvider","onUpdate -> ");
            Log.e("APPWidgetProvider","onUpdate -> Hello");
            remoteViews.setTextViewText(R.id.iv_widget, charSequence);
        reverse = !reverse;
        for (int appWidgetId : appWidgetIds){
            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e("APPWidgetProvider","onReceive : "+intent.getAction());
        charSequence = "aaaaaa";
        updateWidget(context);
        if(CLICK_ACTION.equals(intent.getAction())){
            Toast.makeText(context,"Hello Dog",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIDs = appWidgetManager.getAppWidgetIds(new ComponentName(context, TestAppWidgetProvider.class));
        onUpdate(context,appWidgetManager,widgetIDs);

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

}
