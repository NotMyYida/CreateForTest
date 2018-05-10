package com.example.hp.verifyimagination.util;

/**
 * Created by hp on 18-4-20.
 */

public class CommonUtil {

    public static int parseStringToInteger(String str){
        int num ;
        try {
            num = Integer.valueOf(str);
        }catch (NumberFormatException e){
            num = -1;
        }
        return num ;
    }


    public static float parseStringToFloat(String str){
        float num ;
        try {
            num = Float.valueOf(str);
        }catch (NumberFormatException e){
            num = 0f;
        }
        return num;
    }
}
