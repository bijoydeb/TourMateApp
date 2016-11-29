package com.summons.tourmateapp.WeatherModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by engrb on 07-Nov-16.
 */

public class Base {
    @SerializedName("query")
    @Expose
    private Query query;
    private String city;

    public Base() {
    }

    public Base(String city) {
        this.city = city;
    }

    public Query getQuery() {
        return query;
    }

    /**
     * @param query The query
     */
    public void setQuery(Query query) {
        this.query = query;
    }

}
