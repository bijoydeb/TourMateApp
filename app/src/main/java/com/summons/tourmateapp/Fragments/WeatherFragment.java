package com.summons.tourmateapp.Fragments;


import android.app.FragmentManager;import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.summons.tourmateapp.DialogFragments.ConnectionAlertDialog;
import com.summons.tourmateapp.ForecastActivity;
import com.summons.tourmateapp.R;
import com.summons.tourmateapp.Utils.ConnectionDetector;
import com.summons.tourmateapp.Utils.GPSTracker;
import com.summons.tourmateapp.Utils.WeatherSharedPreference;
import com.summons.tourmateapp.WeatherApi;
import com.summons.tourmateapp.WeatherModel.Base;
import com.summons.tourmateapp.WeatherModel.Forecast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private EditText searchEditText;
    private Button goButton;
    TextView cityTextView;
    TextView tempTextView;
    TextView humidityTextView;
    TextView conditionTextView;
    TextView windTextView;
    TextView sunriseTextView;
    TextView sunsetTextView;
    ImageView cConditionImageView;
    Button forecastButton;
    ConnectionDetector detector;
    Boolean isInternetConnection = false;
    WeatherApi weatherApi;
    String a = "";
    String url = "https://query.yahooapis.com/";
    String link = "";
    public static ArrayList<Forecast> forecastArrayList;
    GPSTracker gpsTracker;
    double lat;
    double lon;
    String city;
    boolean isGPSEnabled = false;
    protected LocationManager locationManager;
    WeatherSharedPreference sharedPreference;

    ProgressBar loading_progressBar;
    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_weather, container, false);
        searchEditText = (EditText) view.findViewById(R.id.searchEditText);
        goButton = (Button)view. findViewById(R.id.goButton);
        forecastButton = (Button)view. findViewById(R.id.forecastButton);
        cityTextView = (TextView)view. findViewById(R.id.cityTextView);
        cConditionImageView = (ImageView)view. findViewById(R.id.cConditionImageView);
        tempTextView = (TextView) view.findViewById(R.id.tempTextView);

        loading_progressBar = (ProgressBar)view. findViewById(R.id.loading_progressBar);
        conditionTextView = (TextView) view.findViewById(R.id.conditionTextView);

        forecastArrayList = new ArrayList<Forecast>();
        detector = new ConnectionDetector(getActivity());
        gpsTracker = new GPSTracker(getActivity());
        isInternetConnection = detector.isConnectingToInternet();

        locationManager = (LocationManager) getActivity()
                .getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);



        if (isGPSEnabled) {
            lat = gpsTracker.getLatitude();
            lon = gpsTracker.getLongitude();

            getCityName(lat, lon);
            a = city;
            searchEditText.setText(a);
            networkLibraryInitializer();
            try {
                getData();
            } catch (Exception e) {
                e.printStackTrace();
            }

//                loading_progressBar.setVisibility(View.GONE);
        }else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("GPS settings");
            alertDialog.setMessage("GPS is not enabled. Do you want to Enable Gps?");

            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
//                        loading_progressBar.setVisibility(View.VISIBLE);
                    networkLibraryInitializer();
                    getData();
//                        loading_progressBar.setVisibility(View.GONE);
                }
            });
            alertDialog.show();
        }

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = searchEditText.getText().toString().trim();
//                    loading_progressBar.setVisibility(View.VISIBLE);
                networkLibraryInitializer();
                try {
                    getData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        forecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForecastActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return view;

 }

    private void getCityName(double lat, double lon) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            city = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void networkLibraryInitializer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApi = retrofit.create(WeatherApi.class);

    }
    private void getData() {
        loading_progressBar.setVisibility(View.VISIBLE);
        if (a.equals("")) {
            a = "comilla";
        }
        link = "v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + a + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        Call<Base> getAllData = weatherApi.getAllQueryData(link);
        getAllData.enqueue(new Callback<Base>() {
            @Override
            public void onResponse(Call<Base> call, Response<Base> response) {
                Base base = response.body();
                int count = base.getQuery().getCount();
                if (count != 0) {
                    String fTemp = base.getQuery().getResults().getChannel().getItem().getCondition().getTemp();
                    String city = base.getQuery().getResults().getChannel().getLocation().getCity();
                    String country = base.getQuery().getResults().getChannel().getLocation().getCountry();
                    String code = base.getQuery().getResults().getChannel().getItem().getCondition().getCode();
                    String condition = base.getQuery().getResults().getChannel().getItem().getCondition().getText();
                    String img = "http://l.yimg.com/a/i/us/we/52/";
                    String image_link = img + code + ".gif";
                    Picasso.with(getActivity()).load(image_link).into(cConditionImageView);


                    forecastArrayList = (ArrayList<Forecast>) base.getQuery().getResults().getChannel().getItem().getForecast();


                    cityTextView.setText(city + "," + country);

                    conditionTextView.setText(condition);

                    int f = Integer.parseInt(fTemp);
                    int cTemp = ((f - 32) * 5) / 9;
                    tempTextView.setText(cTemp + "" + (char) 0x00B0 + "C");

                    loading_progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "Location not found !", Toast.LENGTH_SHORT).show();
                    loading_progressBar.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<Base> call, Throwable t) {

            }
        });
    }


}