package com.summons.tourmateapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by engrb on 18-Nov-16.
 */

public class TourMateSharedPreference {

    Context context;
    SharedPreferences sharedPreferences;


    public TourMateSharedPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("tourMate", Context.MODE_PRIVATE);

    }

    public void saveUserId(String id) {
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        editor.putString("userId", id);
        editor.apply();
        editor.commit();
    }

    public void saveLoginInfo(boolean status, String user_name, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("status", status);
        editor.putString("user_name", user_name);
        editor.putString("password", password);
        editor.apply();
        editor.commit();
    }

    public String getUserId() {
        return sharedPreferences.getString("userId", "");
    }

    public boolean getStatus(){
        return sharedPreferences.getBoolean("status",false);
    }

    public String getUserName() {
        return sharedPreferences.getString("user_name", "");
    }

    public String getUserPassWord() {
        return sharedPreferences.getString("password", "");
    }
}
