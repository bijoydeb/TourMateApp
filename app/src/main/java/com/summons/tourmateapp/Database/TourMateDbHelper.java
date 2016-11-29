package com.summons.tourmateapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by engrb on 14-Nov-16.
 */

public class TourMateDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tourmate.sqlite";
    public static final int DATABASE_VERSION = 1;

    public static final String SIGNUP_TABLE_NAME = "signUp";
    public static final String SIGNUP_ID = "id";
    public static final String SIGNUP_FULL_NAME = "fullName";
    public static final String SIGNUP_USERNAME = "username";
    public static final String SIGNUP_PASSWORD = "password";
    public static final String SIGNUP_E_NUMBER = "eNumber";
    public static final String SIGNUP_ADDRESS = "address";
    public static final String SIGNUP_PROFILE_PIC = "picture";

    public static final String EVENT_TABLE_NAME = "event";
    public static final String EVENT_ID = "id";
    public static final String EVENT_DESTINATION = "destination";
    public static final String EVENT_BUDGET = "budget";
    public static final String EVENT_FROM_DATE = "from_date";
    public static final String EVENT_TO_DATE = "to_date";
    public static final String EVENT_USER_ID = "user_id";

    public static final String EXPENSE_TABLE_NAME = "expense";
    public static final String EXPENSE_ID = "id";
    public static final String EXPENSE_TIME = "time";
    public static final String EXPENSE_DATE = "date";
    public static final String EXPENSE_DETAILS = "details";
    public static final String EXPENSE_AMOUNT = "amount";
    public static final String EXPENSE_USER_ID = "userId";
    public static final String EXPENSE_EVENT_ID = "event_id";

    public static final String MOMENT_TABLE_NAME = "moment";
    public static final String MOMENT_ID = "id";
    public static final String MOMENT_IMAGE_PATH = "image_path";
    public static final String MOMENT_DETAILS = "moment_details";
    public static final String MOMENT_DATE = "moment_date";
    public static final String MOMENT_USER_ID = "moment_user_id";
    public static final String MOMENT_EVENT_ID = "moment_event_id";



    private static final String MOMENT_TABLE = "CREATE TABLE " + MOMENT_TABLE_NAME + "("
            + MOMENT_ID + " integer primary key autoincrement not null,"
            + MOMENT_DETAILS + " varchar,"
            + MOMENT_DATE + " varchar,"
            + MOMENT_IMAGE_PATH + " varchar, "
            + MOMENT_USER_ID + " varchar,"
            + MOMENT_EVENT_ID + " varchar);";

    private static final String SIGNUP_TABLE = "CREATE TABLE " + SIGNUP_TABLE_NAME + "("
            + SIGNUP_ID + " integer primary key autoincrement not null,"
            + SIGNUP_FULL_NAME + " varchar,"
            + SIGNUP_USERNAME + " varchar, "
            + SIGNUP_PASSWORD + " varchar,"
            + SIGNUP_E_NUMBER + " varchar, "
            + SIGNUP_ADDRESS + " varchar,"
            + SIGNUP_PROFILE_PIC + " varchar);";

    private static final String EVENT_TABLE = "CREATE TABLE " + EVENT_TABLE_NAME + "("
            + EVENT_ID + " integer primary key autoincrement not null,"
            + EVENT_DESTINATION + " varchar,"
            + EVENT_BUDGET + " varchar,"
            + EVENT_FROM_DATE + " varchar,"
            + EVENT_TO_DATE + " varchar,"
            + EVENT_USER_ID + " varchar);";

    private static final String EXPENSE_TABLE = "CREATE TABLE " + EXPENSE_TABLE_NAME + "("
            + EXPENSE_ID + " integer primary key autoincrement not null,"
            + EXPENSE_TIME + " varchar,"
            + EXPENSE_DATE + " varchar,"
            + EXPENSE_DETAILS + " varchar,"
            + EXPENSE_AMOUNT + " varchar,"
            + EXPENSE_USER_ID + " varchar,"
            + EXPENSE_EVENT_ID + " varchar);";

    public TourMateDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SIGNUP_TABLE);
        db.execSQL(EVENT_TABLE);
        db.execSQL(EXPENSE_TABLE);
        db.execSQL(MOMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS " + SIGNUP_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS " + EVENT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS " + EXPENSE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS " + MOMENT_TABLE);
        onCreate(sqLiteDatabase);
    }
}
