package com.summons.tourmateapp;


import com.summons.tourmateapp.WeatherModel.Base;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by engrb on 10-Nov-16.
 */

public interface WeatherApi {
    @GET
    Call<Base> getAllQueryData(@Url String url);
}
