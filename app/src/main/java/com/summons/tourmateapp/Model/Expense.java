package com.summons.tourmateapp.Model;

/**
 * Created by ripon on 11/22/2016.
 */

public class Expense {
    private String userId;
    private String eventId;
    private String details;
    private String amount;
    private String date;
    private String time;

    public Expense(String eventId, String details, String amount, String date, String time) {
        this.eventId = eventId;
        this.details = details;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public Expense(String userId, String eventId, String details, String amount, String date, String time) {
        this.userId = userId;
        this.eventId = eventId;
        this.details = details;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }

    public String getAmount() {
        return amount;
    }


    public String getEventId() {
        return eventId;
    }
}
