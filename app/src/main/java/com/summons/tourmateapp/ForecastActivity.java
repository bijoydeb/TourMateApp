package com.summons.tourmateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


import com.summons.tourmateapp.Adapter.ForecastAdapter;
import com.summons.tourmateapp.Fragments.WeatherFragment;
import com.summons.tourmateapp.WeatherModel.Forecast;

import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {
    ArrayList<Forecast> forecastArrayList;
    ListView forecastListView;
    ForecastAdapter forecastAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forecast");
        forecastListView = (ListView) findViewById(R.id.forecastListView);
        forecastArrayList = WeatherFragment.forecastArrayList;

        forecastAdapter = new ForecastAdapter(this, forecastArrayList);
        forecastListView.setAdapter(forecastAdapter);

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
