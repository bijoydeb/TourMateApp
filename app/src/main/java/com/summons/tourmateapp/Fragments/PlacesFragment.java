package com.summons.tourmateapp.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.summons.tourmateapp.Adapter.PlacesItemAdapter;
import com.summons.tourmateapp.PlaceListActivity;
import com.summons.tourmateapp.R;
import com.summons.tourmateapp.Utils.ConnectionDetector;
import com.summons.tourmateapp.Utils.GPSTracker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.type;
import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends Fragment {
    GridView placesGridView;
    public static String[] optionList = {"Atm", "Bank", "BusStation", "Cafe", "Doctor", "Food",
            "GasStation", "Hospital", "Park", "Police", "Restaurant",
            "Mall"};

    public static int[] optionImages = {R.drawable.atm, R.drawable.bank, R.drawable.bus, R.drawable.cafe,
            R.drawable.doctor, R.drawable.food, R.drawable.gas, R.drawable.hospitals,
            R.drawable.park, R.drawable.police, R.drawable.restaurant,R.drawable.mall};

    ConnectionDetector detector;
    Boolean isInternetConnection = false;
    GPSTracker gpsTracker;
    boolean isGPSEnabled = false;
    LocationManager locationManager;


    public PlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        placesGridView = (GridView) view.findViewById(R.id.optionGridView);

        detector = new ConnectionDetector(getActivity());
        isInternetConnection = detector.isConnectingToInternet();

        placesGridView.setAdapter(new PlacesItemAdapter(getActivity(), optionList, optionImages));
        placesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        valuePass(adapterView, i, "atm");
                        break;
                    case 1:
                        valuePass(adapterView, i, "bank");
                        break;
                    case 2:
                        valuePass(adapterView, i, "bus_station");
                        break;
                    case 3:
                        valuePass(adapterView, i, "cafe");
                        break;
                    case 4:
                        valuePass(adapterView, i, "doctor");
                        break;

                    case 5:
                        valuePass(adapterView, i, "food");
                        break;
                    case 6:
                        valuePass(adapterView, i, "gas_station");
                        break;
                    case 7:
                        valuePass(adapterView, i, "hospital");
                        break;
                    case 8:
                        valuePass(adapterView, i, "park");
                        break;
                    case 9:
                        valuePass(adapterView, i, "police");
                        break;
                    case 10:
                        valuePass(adapterView, i, "restaurant");
                        break;
                    case 11:
                        valuePass(adapterView, i, "mall");
                        break;
                }


            }
        });
        return view;
    }

    private void valuePass(AdapterView<?> adapterView, int i, String type) {

        if (isInternetConnection) {
            gpsTracker = new GPSTracker(getActivity());

            locationManager = (LocationManager) getActivity()
                    .getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                Intent intent = new Intent(getActivity(), PlaceListActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("c_lat", gpsTracker.getLatitude());
                intent.putExtra("c_lon", gpsTracker.getLongitude());
                startActivity(intent);
            } else {
                gpsEnableAlert(type);
            }
        } else {
            internetConnectionAlert();
        }

    }

    private void internetConnectionAlert() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Connect Internet/Not?");
        builder.setMessage("NO Internet Connection. Please Connect.. \nDo you want to Enable Internet Connection?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void gpsEnableAlert(final String type) {
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
                Intent intent = new Intent(getActivity(), PlaceListActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("c_lat", 15.8700);
                intent.putExtra("c_lon", 100.9925);
                startActivity(intent);


            }
        });
        alertDialog.show();
    }


}
