package com.example.hp.verifyimagination.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by hp on 18-4-21.
 */

public class PermissionUtil {

    public static boolean checkPermission(Context ctx,String permission){
        int selfPermission = ContextCompat.checkSelfPermission(ctx, permission);
        return selfPermission == PackageManager.PERMISSION_GRANTED ;
    }

    public static void reqestPermission(Activity activity,String[] permissions,int actionCode){
        ActivityCompat.requestPermissions(activity,permissions,actionCode);
    }

}
