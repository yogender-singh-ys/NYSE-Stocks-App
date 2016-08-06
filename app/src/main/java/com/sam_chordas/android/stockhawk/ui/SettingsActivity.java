package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.rest.Utils;

/**
 * Created by yogenders on 7/30/16.
 */
public class SettingsActivity extends Utils.AppCompatPreferenceActivity {


    public static Context mContext;
    public  static String KEY = "sync_lang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference);

        mContext = this;

        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                        if(key.equals(KEY)){
                            ListPreference connectionPref = (ListPreference) findPreference(key);
                            //String language = connectionPref.getValue();
                            Intent main = new Intent(mContext, MyStocksActivity.class);
                            startActivity(main);

                        }

                    }
                };
        prefs.registerOnSharedPreferenceChangeListener(listener);
        getSupportActionBar().setTitle(R.string.action_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return true;
        }

    }
}
