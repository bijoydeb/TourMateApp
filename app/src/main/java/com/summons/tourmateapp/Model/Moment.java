package com.summons.tourmateapp.Model;

/**
 * Created by engrb on 25-Nov-16.
 */

public class Moment {
   private String id;
    private String momentDetails;
    private String imagePath;
    private String date;
    private String eventId;
    private String userId;


    public Moment(String imagePath, String momentDetails, String userId, String eventId,String date) {
        this.imagePath = imagePath;
        this.momentDetails = momentDetails;
        this.userId = userId;
        this.eventId = eventId;
        this.date = date;
    }

    public Moment(String id, String momentDetails, String imagePath, String date, String eventId, String userId) {
        this.id = id;
        this.momentDetails = momentDetails;
        this.imagePath = imagePath;
        this.date = date;
        this.eventId = eventId;
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getMomentDetails() {
        return momentDetails;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventId() {
        return eventId;
    }
}
