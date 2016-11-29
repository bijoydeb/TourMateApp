package com.summons.tourmateapp.Model;

/**
 * Created by engrb on 26-Nov-16.
 */

public class ImageGallery {
    private String title;
    private String image;

    public ImageGallery() {
    }

    public ImageGallery(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
