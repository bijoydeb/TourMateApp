package com.summons.tourmateapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.summons.tourmateapp.Adapter.NearByPlaceListAdapter;
import com.summons.tourmateapp.Model.PlaceList;
import com.summons.tourmateapp.Model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceListActivity extends AppCompatActivity {
    ListView placeListView;
    NearbyPlaceApi nearbyPlaceApi;
    NearByPlaceListAdapter nearByPlaceListAdapter;
    String type = "";
    String base_url = "https://maps.googleapis.com/maps/api/place/";
    String link;
    List<Result> results;
    double c_lat;
    double c_lon;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        toolbar =(Toolbar)findViewById(R.id.toolbar);
        placeListView = (ListView) findViewById(R.id.placeListView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("NearBy Places List");

        type = getIntent().getStringExtra("type");
        c_lat = getIntent().getDoubleExtra("c_lat", 0.0d);
        c_lon = getIntent().getDoubleExtra("c_lon", 0.0d);

        networkLibraryInitializer();
        getData();

        placeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                double lat = results.get(i).getGeometry().getLocation().getLat();
                double lon = results.get(i).getGeometry().getLocation().getLng();
                String name = results.get(i).getName();
                String placeId = results.get(i).getPlaceId();

                Intent intent = new Intent(PlaceListActivity.this, MapsActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("c_lat", c_lat);
                intent.putExtra("c_lon", c_lon);
                intent.putExtra("name", name);
                intent.putExtra("placeId", placeId);
                startActivity(intent);
            }
        });
    }


    private void networkLibraryInitializer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nearbyPlaceApi = retrofit.create(NearbyPlaceApi.class);

    }

    private void getData() {
//        String aa = "nearbysearch/json?location=23.8103,90.4125&rankby=distance&types=" + type + "&key=%20AIzaSyB5oELzprE-xCyETQrulCdVGJufbot7KMU";
        link = "search/json?location=" + c_lat + "," + c_lon + "&rankby=distance&types=" + type + "&key=%20AIzaSyDr4zLcVFvYWH-JdyxRsULOmP81Dpk_Wbo";
        type = "";
        Log.e("v", "lat & lon : " + c_lat + "&" + c_lon);
        Call<PlaceList> getAllData = nearbyPlaceApi.getPlaceList(link);

        getAllData.enqueue(new Callback<PlaceList>() {
            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {

                PlaceList placeList = response.body();
                String status = placeList.getStatus();
                results = placeList.getResults();
                nearByPlaceListAdapter = new NearByPlaceListAdapter(PlaceListActivity.this, results);
                placeListView.setAdapter(nearByPlaceListAdapter);

            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
