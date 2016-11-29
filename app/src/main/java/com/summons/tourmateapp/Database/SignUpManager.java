package com.summons.tourmateapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.summons.tourmateapp.Model.SignUp;

/**
 * Created by engrb on 14-Nov-16.
 */

public class SignUpManager {

    private TourMateDbHelper tourMateDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private Cursor cursor = null;

    public SignUpManager(Context context) {
        this.context = context;
        tourMateDbHelper = new TourMateDbHelper(context);
    }

    public long addRegInfo(SignUp signUp) {
        long success = 0;

        ContentValues contentValues = new ContentValues();

        contentValues.put(TourMateDbHelper.SIGNUP_FULL_NAME, signUp.getFullName());
        contentValues.put(TourMateDbHelper.SIGNUP_USERNAME, signUp.getUserName());
        contentValues.put(TourMateDbHelper.SIGNUP_PASSWORD, signUp.getPassword());
        contentValues.put(TourMateDbHelper.SIGNUP_E_NUMBER, signUp.geteNumber());
        contentValues.put(TourMateDbHelper.SIGNUP_ADDRESS, signUp.getAddress());
        contentValues.put(TourMateDbHelper.SIGNUP_PROFILE_PIC, signUp.getProfilePic());

        try {
            sqLiteDatabase = tourMateDbHelper.getWritableDatabase();
            success = sqLiteDatabase.insert(TourMateDbHelper.SIGNUP_TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
            sqLiteDatabase.close();
        }
        return success;
    }

    public String getUserPass(String userName) {
        String userPass = "";

        try {
            sqLiteDatabase = tourMateDbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query(TourMateDbHelper.SIGNUP_TABLE_NAME, null, TourMateDbHelper.SIGNUP_USERNAME + "= '" + userName + "'", null, null, null, null);
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    userPass = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.SIGNUP_PASSWORD));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return userPass;
    }

    public String getUserId(String userName) {
        String userId = "";

        try {
            sqLiteDatabase = tourMateDbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query(TourMateDbHelper.SIGNUP_TABLE_NAME, null, TourMateDbHelper.SIGNUP_USERNAME + "= '" + userName + "'", null, null, null, null);
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    userId = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.SIGNUP_ID));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return userId;
    }
    public String getUserImage(String user_id) {
        String userImage = "";

        try {
            sqLiteDatabase = tourMateDbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query(TourMateDbHelper.SIGNUP_TABLE_NAME, null, TourMateDbHelper.SIGNUP_ID + "= '" + user_id + "'", null, null, null, null);
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    userImage = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.SIGNUP_PROFILE_PIC));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return userImage;
    }


    public String userNameExits(String userName) {
        sqLiteDatabase = tourMateDbHelper.getReadableDatabase();
        int num = 0;

        try {
            cursor = sqLiteDatabase.query(TourMateDbHelper.SIGNUP_TABLE_NAME, null, TourMateDbHelper.SIGNUP_USERNAME + "= '" + userName + "'", null, null, null, null);
            num = cursor.getCount();
            if (num > 0) {
                return "1";
            } else {
                return "0";
            }

        } catch (Exception e) {
            Log.e("error", e + "");
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
            cursor.close();
        }
        return "0";

    }

}
