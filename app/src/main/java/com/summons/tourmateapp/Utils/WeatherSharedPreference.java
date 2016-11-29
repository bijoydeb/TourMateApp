package com.summons.tourmateapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by engrb on 10-Nov-16.
 */

public class WeatherSharedPreference {
    private SharedPreferences sharedPreferences;
    private Context context;

    public WeatherSharedPreference(Context context) {
        this.context = context;
        sharedPreferences = (SharedPreferences) context.getSharedPreferences("weatherShared", Context.MODE_PRIVATE);
    }

    public void setStatus(String status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("status", status);
        editor.apply();
        editor.commit();
    }
    public void setUnit(String unit) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("unit", unit);
        editor.apply();
        editor.commit();
    }


    public String getStatus() {
        return sharedPreferences.getString("status", "");
    }

    public String getUnit() {
        return sharedPreferences.getString("unit", "");
    }
}
