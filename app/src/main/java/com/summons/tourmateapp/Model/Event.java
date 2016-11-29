package com.summons.tourmateapp.Model;

/**
 * Created by engrb on 18-Nov-16.
 */

public class Event {
    private String userId;
    private String destination;
    private String budget;
    private String fromDate;
    private String toDate;

    public Event(String destination) {
        this.destination = destination;
    }

    public Event(String destination, String budget, String fromDate, String toDate) {
        this.destination = destination;
        this.budget = budget;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Event(String userId, String destination, String budget, String fromDate, String toDate) {
        this.userId = userId;
        this.destination = destination;
        this.budget = budget;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getDestination() {
        return destination;
    }

    public String getBudget() {
        return budget;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }
}
