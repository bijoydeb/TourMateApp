package com.summons.tourmateapp.DetailsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by engrb on 25-Nov-16.
 */

public class OpeningHours {
    @SerializedName("open_now")
    @Expose
    private Boolean openNow;
    /**
     *
     * @return
     * The openNow
     */
    public Boolean getOpenNow() {
        return openNow;
    }

    /**
     *
     * @param openNow
     * The open_now
     */
    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }
}
