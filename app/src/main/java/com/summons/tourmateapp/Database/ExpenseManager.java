package com.summons.tourmateapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.summons.tourmateapp.Model.Expense;

import java.util.ArrayList;

/**
 * Created by ripon on 11/22/2016.
 */

public class ExpenseManager {

    private TourMateDbHelper tourMateDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private Cursor cursor = null;

    public ExpenseManager(Context context) {
        this.context = context;
        tourMateDbHelper = new TourMateDbHelper(context);
    }

    public long addExpense(Expense expense) {
        long success = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(TourMateDbHelper.EXPENSE_USER_ID, expense.getUserId());
        contentValues.put(TourMateDbHelper.EXPENSE_TIME, expense.getTime());
        contentValues.put(TourMateDbHelper.EXPENSE_DATE, expense.getDate());
        contentValues.put(TourMateDbHelper.EXPENSE_DETAILS, expense.getDetails());
        contentValues.put(TourMateDbHelper.EXPENSE_AMOUNT, expense.getAmount());
        contentValues.put(TourMateDbHelper.EXPENSE_EVENT_ID, expense.getEventId());

        try {
            sqLiteDatabase = tourMateDbHelper.getWritableDatabase();
            success = sqLiteDatabase.insert(TourMateDbHelper.EXPENSE_TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
            sqLiteDatabase.close();
        }
        return success;
    }

    public ArrayList<Expense> getAllExpense(String userId, String eventId) {
        ArrayList<Expense> eventArrayList = null;
        Expense expense;

        try {
            eventArrayList = new ArrayList<>();
            sqLiteDatabase = tourMateDbHelper.getReadableDatabase();
            String Query = "SELECT * from " + TourMateDbHelper.EXPENSE_TABLE_NAME + " where " + TourMateDbHelper.EXPENSE_USER_ID + " = " + userId + " and " + TourMateDbHelper.EXPENSE_EVENT_ID + " = " + eventId;
            Log.e("EM", "Query : " + Query);
            cursor = sqLiteDatabase.rawQuery(Query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {

                    String id = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EXPENSE_ID));
                    Log.e("EM", "id : " + id);
                    String details = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EXPENSE_DETAILS));
                    Log.e("EM", "details : " + details);
                    String amount = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EXPENSE_AMOUNT));
                    String date = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EXPENSE_DATE));
                    String time = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EXPENSE_TIME));
                    String event_id = cursor.getString(cursor.getColumnIndex(TourMateDbHelper.EXPENSE_EVENT_ID));

                    expense = new Expense(id, event_id, details, amount, date, time);
                    eventArrayList.add(expense);
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


}
