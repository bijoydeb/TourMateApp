package com.summons.tourmateapp;

import com.summons.tourmateapp.DetailsModel.DirectionMain;
import com.summons.tourmateapp.Model.PlaceList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by ripon on 11/23/2016.
 */

public interface NearbyPlaceApi {

    @GET()
    Call<PlaceList> getPlaceList(@Url String url);
    @GET()
    Call<DirectionMain> getIDList(@Url String url);

}
