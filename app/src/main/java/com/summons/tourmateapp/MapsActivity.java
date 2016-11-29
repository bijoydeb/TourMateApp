package com.summons.tourmateapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.summons.tourmateapp.DetailsModel.DirectionMain;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    double lat;
    double lon;
    double c_lat;
    double c_lon;
    String name;
    String placeId;
    private GoogleMap mMap;
    NearbyPlaceApi nearbyPlaceApi;
    LinearLayout detailsLayout;
    LinearLayout numLayout;
    LinearLayout websiteLayout;
    TextView titleTextView;
    TextView addressTextView;
    TextView numberTextView;
    TextView websiteTextView;
    TextView showDetailsTextView;
    TextView visibilityTextView;
    TextView statusTextView;
    Button directionButton;
    String base_url = "https://maps.googleapis.com/maps/api/place/details/";
    String link;
    String webSite;
    private Intent callIntent;
    Context context;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.context = this;

        detailsLayout = (LinearLayout) findViewById(R.id.detailsLayout);
        numLayout = (LinearLayout) findViewById(R.id.numberLayout);
        websiteLayout = (LinearLayout) findViewById(R.id.webLayout);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        addressTextView = (TextView) findViewById(R.id.addressTextView);
        numberTextView = (TextView) findViewById(R.id.numberTextView);
        websiteTextView = (TextView) findViewById(R.id.websiteTextView);
        statusTextView = (TextView) findViewById(R.id.statusTextView);
        visibilityTextView = (TextView) findViewById(R.id.visibilityTextView);
        showDetailsTextView = (TextView) findViewById(R.id.showDetailsTextView);
        directionButton = (Button) findViewById(R.id.directionButton);


        lat = getIntent().getDoubleExtra("lat", 0.0d);
        lon = getIntent().getDoubleExtra("lon", 0.0d);
        c_lat = getIntent().getDoubleExtra("c_lat", 0.0d);
        c_lon = getIntent().getDoubleExtra("c_lon", 0.0d);

        name = getIntent().getStringExtra("name");
        placeId = getIntent().getStringExtra("placeId");

        titleTextView.setText(name);

        visibilityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsLayout.setVisibility(View.GONE);
                showDetailsTextView.setVisibility(View.VISIBLE);
            }
        });

        showDetailsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsLayout.setVisibility(View.VISIBLE);
                showDetailsTextView.setVisibility(View.GONE);
            }
        });
        networkLibraryInitializer();
        getData();

        numberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + numberTextView.getText().toString()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        callIntent.setPackage("com.android.server.telecom");
                    } else {
                        callIntent.setPackage("com.android.phone");
                    }
                    if (checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 101);
                        return;
                    }
                    context.startActivity(callIntent);
                } catch (Exception ex) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + Uri.encode(numberTextView.getText().toString())));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(callIntent);
                }
            }
        });

        websiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, WebViewActivity.class);
                intent.putExtra("link", webSite);
                startActivity(intent);
            }
        });

        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, DirectionActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("c_lat", c_lat);
                intent.putExtra("c_lon", c_lon);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }

    private int checkSelfPermission(Context context, String callPhone) {
        return 0;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(latLng).title(name)).showInfoWindow();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        if (mMap.isMyLocationEnabled()) {
//            mMap.addMarker(new MarkerOptions());
        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(15).build();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    private void networkLibraryInitializer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nearbyPlaceApi = retrofit.create(NearbyPlaceApi.class);

    }

    private void getData() {
        link = "json?placeid=" + placeId + "&key=AIzaSyB5oELzprE-xCyETQrulCdVGJufbot7KMU";
        Call<DirectionMain> getAllData = nearbyPlaceApi.getIDList(link);

        getAllData.enqueue(new Callback<DirectionMain>() {
            @Override
            public void onResponse(Call<DirectionMain> call, Response<DirectionMain> response) {

                DirectionMain directionMain = response.body();


                String address = directionMain.getResult().getFormattedAddress();

                if (!address.equals("")) {
                    addressTextView.setText(address);
                }

                if (response.body().getResult().getFormattedPhoneNumber() == null) {
                    numLayout.setVisibility(View.INVISIBLE);
                } else {
                    String number = directionMain.getResult().getFormattedPhoneNumber().trim();
                    numberTextView.setText(number);
                }
                if (response.body().getResult().getWebsite() == null) {
                    websiteLayout.setVisibility(View.INVISIBLE);
                } else {
                    webSite = directionMain.getResult().getWebsite().trim();
                    websiteTextView.setText(webSite);
                }

                if (response.body().getResult().getOpeningHours() == null) {
                    statusTextView.setVisibility(View.GONE);
                } else {
                    boolean status = directionMain.getResult().getOpeningHours().getOpenNow();
                    if (status) {
                        statusTextView.setText("Now " + "Open");
                    } else {
                        statusTextView.setText("Now " + "Close");
                    }
                }


            }

            @Override
            public void onFailure(Call<DirectionMain> call, Throwable t) {

            }
        });
    }


}
