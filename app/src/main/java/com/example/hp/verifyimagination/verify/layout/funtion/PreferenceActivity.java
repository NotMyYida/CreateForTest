package com.example.hp.verifyimagination.verify.layout.funtion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.hp.verifyimagination.R;

/**
 * Created by hp on 18-4-26.
 */

public class PreferenceActivity extends android.preference.PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.test_pref);
        PreferenceScreen preferenceScreen = this.getPreferenceScreen();
        sharedPreferences = preferenceScreen.getSharedPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Toast.makeText(this,key+" is changed",Toast.LENGTH_SHORT).show();
    }
}
