package com.summons.tourmateapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.summons.tourmateapp.Model.Event;

import java.util.ArrayList;

/**
 * Created by engrb on 18-Nov-16.
 */

public class EventManager {
    private TourMateDbHelper tourMateDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private Cursor cursor = null;

    public EventManager(Context context) {
        this.context = context;
        tourMateDbHelper = new TourMateDbHelper(context);
    }

    public long addEvent(Event event) {
        long success = 0;
        ContentValues contentValues = new ContentValues();

        contentValues.put(TourMateDbHelper.EVENT_DESTINATION, event.getDestination());
        contentValues.put(TourMateDbHelper.EVENT_BUDGET, event.getBudget());
        contentValues.put(TourMateDbHelper.EVENT_FROM_DATE, event.getFromDate());
        contentValues.put(TourMateDbHelper.EVENT_TO_DATE, event.getToDate());
        contentValues.put(TourMateDbHelper.EVENT_USER_ID, event.getUserId());

        try {
            sqLiteDatabase = tourMateDbHelper.getWritableDatabase();
            success = sqLiteDatabase.insert(TourMateDbHelper.EVENT_TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
            sqLiteDatabase.close();
        }
        return success;
    }

    public ArrayList<Event> getAllEvent(String userId) {
        ArrayList<Event> eventArrayList = null;
        Event event;
        Log.e("EM g","user id nn: "+userId);

        try {
            eventArrayList = new ArrayList<>();
            sqLiteDatabase = tourMateDbHelper.getReadableDatabase();
            String Query = "SELECT * from " + TourMateDbHelper.EVENT_TABLE_NAME + " where " + TourMateDbHelper.EVENT_USER_ID + " = " + userId;
            Log.e("EM g"," query data: "+Query);
            cursor = sqLiteDatabase.rawQuery(Query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    String eventId= String.valueOf(cursor.getInt(cursor.getColumnIndex(TourMateDbHelper.EVENT_ID)));
                    Log.e("EM g","e id : "+eventId);
                    String destination = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EVENT_DESTINATION));
                    String budget = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EVENT_BUDGET));
                    String fromDate = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EVENT_FROM_DATE));
                    String toDate = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EVENT_TO_DATE));
                    event = new Event(eventId,destination, budget, fromDate, toDate);
                    eventArrayList.add(event);
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

        return eventArrayList;
    }

    public ArrayList<Event> getEventName() {
        ArrayList<Event> allEventNameArrayList = null;
        Event event;

        try {
            allEventNameArrayList = new ArrayList<>();
            sqLiteDatabase = tourMateDbHelper.getReadableDatabase();
            String Query = "SELECT " + TourMateDbHelper.EVENT_DESTINATION + " from " + TourMateDbHelper.EVENT_TABLE_NAME;
            cursor = sqLiteDatabase.rawQuery(Query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    String destination = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EVENT_DESTINATION));
                    event = new Event(destination);
                    allEventNameArrayList.add(event);
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

        return allEventNameArrayList;
    }

    public String getEventId(String destination) {
        String id = "";
        Event event;

        try {
            sqLiteDatabase = tourMateDbHelper.getReadableDatabase();
            String Query = "SELECT " + TourMateDbHelper.EVENT_ID + " from " + TourMateDbHelper.EVENT_TABLE_NAME + " where " + TourMateDbHelper.EVENT_DESTINATION + " = '" + destination + "'";
            cursor = sqLiteDatabase.rawQuery(Query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    id = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EVENT_ID));

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

        return id;
    }

}
