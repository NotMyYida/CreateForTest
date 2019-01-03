package com.example.myapplication;

import android.util.Log;

/**
 * Created by brant on 18-12-5.
 */

public class TestInterface implements MyInterface {
    @Override
    public void test() {
        Log.e("TAG","test");
    }
}
