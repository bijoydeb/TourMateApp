package com.summons.tourmateapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.summons.tourmateapp.Model.Moment;

import java.util.ArrayList;

/**
 * Created by engrb on 25-Nov-16.
 */

public class MomentManager {

    private TourMateDbHelper tourMateDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private Cursor cursor = null;

    public MomentManager(Context context) {
        this.context = context;
        tourMateDbHelper = new TourMateDbHelper(context);
    }

    public long addMoment(Moment moment) {
        long success = 0;
        ContentValues contentValues = new ContentValues();

        contentValues.put(TourMateDbHelper.MOMENT_IMAGE_PATH, moment.getImagePath());
        contentValues.put(TourMateDbHelper.MOMENT_DETAILS, moment.getMomentDetails());
        contentValues.put(TourMateDbHelper.MOMENT_DATE, moment.getDate());
        contentValues.put(TourMateDbHelper.MOMENT_USER_ID, moment.getUserId());
        contentValues.put(TourMateDbHelper.MOMENT_EVENT_ID, moment.getEventId());

        try {
            sqLiteDatabase = tourMateDbHelper.getWritableDatabase();
            success = sqLiteDatabase.insert(TourMateDbHelper.MOMENT_TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
            sqLiteDatabase.close();
        }
        return success;
    }

    public ArrayList<Moment> getAllMoment(String userId, String eventId) {
        ArrayList<Moment> momentArrayList = null;
        Moment moment;

        try {
            momentArrayList = new ArrayList<>();
            sqLiteDatabase = tourMateDbHelper.getReadableDatabase();
            String Query = "SELECT * from " + TourMateDbHelper.MOMENT_TABLE_NAME + " where " + TourMateDbHelper.MOMENT_USER_ID + " = " + userId + " and " + TourMateDbHelper.MOMENT_EVENT_ID + " = " + eventId;
            cursor = sqLiteDatabase.rawQuery(Query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {

                    String id = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.MOMENT_ID));
                    String details = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.MOMENT_DETAILS));

                    String image_path = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.MOMENT_IMAGE_PATH));
                    String date = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.MOMENT_DATE));
                    String mEventId = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.MOMENT_EVENT_ID));
                    String mUserId = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.MOMENT_USER_ID));

                    moment = new Moment(id, details, image_path, date, mEventId, mUserId);
                    momentArrayList.add(moment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            sqLiteDatabase.close();
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return momentArrayList;
    }

}
