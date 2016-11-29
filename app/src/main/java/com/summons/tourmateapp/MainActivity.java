package com.summons.tourmateapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.summons.tourmateapp.Database.SignUpManager;
import com.summons.tourmateapp.DialogFragments.ExitDialog;
import com.summons.tourmateapp.Fragments.EventFragment;
import com.summons.tourmateapp.Fragments.ExpenseFragment;
import com.summons.tourmateapp.Fragments.MomentFragment;
import com.summons.tourmateapp.Fragments.PlacesFragment;
import com.summons.tourmateapp.Fragments.WeatherFragment;
import com.summons.tourmateapp.Utils.TourMateSharedPreference;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private Toolbar toolbar;
    private ImageView profileImageView;
    private DrawerLayout drawerLayout;
    private TextView titleTextView;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private ListView drawerListView;
    private FragmentTransaction fragmentTransaction;
    android.support.v4.app.FragmentManager fragmentManager;
    FrameLayout contentF;
    TourMateSharedPreference sharedPreference;
    SignUpManager signUpManager;
    String userId = "";
    String userImage;
    String[] items = {"Travel Event", "Expense", "Moment", "NearBy","Weather"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TourMate");

        contentF = (FrameLayout) findViewById(R.id.contentF);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerListView = (ListView) findViewById(R.id.drawerListView);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);

        sharedPreference = new TourMateSharedPreference(this);
        signUpManager = new SignUpManager(this);
        userId = sharedPreference.getUserId();
        userImage = signUpManager.getUserImage(userId);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, items);
        drawerListView.setAdapter(arrayAdapter);
        drawerListView.setOnItemClickListener(new SlideMenuClickListener());

        if (savedInstanceState == null) {
            displayView(0);
        }

        if (!userImage.equals("")) {
            profileImageView.setImageURI(Uri.parse(userImage));
        }
    }

    public class SlideMenuClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            displayView(i);

        }
    }

    public void displayView(int i) {

        Fragment fragment2 = null;

        switch (i) {
            case 0:
                fragment2 = new EventFragment();
                titleTextView.setText("Event List");
                break;
//            case 1:
//                fragment2 = new ExpenseFragment();
//                titleTextView.setText("Expense List");
//                break;
//            case 2:
//                fragment2 = new MomentFragment();
//                titleTextView.setText("Moment List");
//                break;
            case 3:
                fragment2 = new PlacesFragment();
                titleTextView.setText("Nearby Places");
                break;
            case 4:
                fragment2 = new WeatherFragment();
                titleTextView.setText("Weather");
                break;

        }
        if (fragment2 != null) {
            fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.contentF, fragment2).addToBackStack(null).commit();

            drawerLayout.closeDrawer(navigationView);
        } else {
            drawerLayout.closeDrawer(navigationView);
        }

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            android.app.FragmentManager manager = getFragmentManager();
            ExitDialog dialog = new ExitDialog();
            dialog.show(manager, "Exit_Dialog");
            userImage = "";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onPause() {
//        boolean status = sharedPreference.getStatus();
//        if (!status) {
//            logout();
//        }
//        super.onPause();
//    }

    private void logout() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tourMate", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }
}
