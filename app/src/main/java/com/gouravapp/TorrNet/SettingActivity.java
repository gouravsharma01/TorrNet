package com.gouravapp.TorrNet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.gouravapp.TorrNet.MainActivity.MY_PREF;

public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public static String WEBSITE = "website";
    public static String IMAGEVIEW = "imageView";
    private static final String TAG = "SettingActivity";
    private List<String> array ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Spinner spinner = findViewById(R.id.spinner);
        array = new ArrayList<>();
        array.add("ThePirateBay"); array.add("LimeTorrents"); array.add("Torrentz");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.spinner_item, array);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        drawerLayout= findViewById(R.id.drawable);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new BaseActivity(getApplicationContext(), drawerLayout));
        navigationView.setCheckedItem(R.id.settings);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        navigationView.setCheckedItem(R.id.settings);
        array.remove(getSharedPreferences(MY_PREF, MODE_PRIVATE).getString(WEBSITE, "ThePirateBay"));
        array.add(0, getSharedPreferences(MY_PREF, MODE_PRIVATE).getString(WEBSITE , "ThePirateBay"));
        super.onResume();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedSite = parent.getItemAtPosition(position).toString();
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF,MODE_PRIVATE);
        sharedPreferences.edit().putString(WEBSITE, selectedSite).apply();
        int imId;
        if(selectedSite.equalsIgnoreCase("thepiratebay")) {
            imId = R.id.imageView1;
        }else if(selectedSite.equalsIgnoreCase("limetorrents")){
            imId = R.id.imageView2;
        }else {
            imId = R.id.imageView3;
        }
        Log.i(TAG, "sharePreference " + selectedSite);

        sharedPreferences.edit().putInt(IMAGEVIEW, imId).apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
