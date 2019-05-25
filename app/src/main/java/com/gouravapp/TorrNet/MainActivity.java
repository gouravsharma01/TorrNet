package com.gouravapp.TorrNet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.gouravapp.TorrNet.Browser.BrowserActivity;

import java.text.DecimalFormat;

import static com.gouravapp.TorrNet.SettingActivity.IMAGEVIEW;
import static com.gouravapp.TorrNet.SettingActivity.WEBSITE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener , CustomDialog.DialogListener{
    private static final String TAG = "MainActivity";
    private ImageView tpbIV, limeIv, tozIV, tpbPIV, limePIV, tozPIV;
    private SearchView mSearchView;
    private Button mButton;
    private ImageView selectedIv ;
    private String website;
    private Boolean buttonTapped = false;
    private String  seasonNo = "";
    private String  episodeNo = "";
    private NavigationView navigationView;
    boolean language = false;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    public static String MY_PREF = "myPreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawable);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        website = getSharedPreferences(MY_PREF,MODE_PRIVATE).getString(WEBSITE, "ThePirateBay");
        Log.i(TAG, "preference website " + website);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new BaseActivity(getApplicationContext(), drawerLayout));
        navigationView.setCheckedItem(R.id.torrnet);
        tpbIV = findViewById(R.id.imageView1);
        limeIv = findViewById(R.id.imageView2);
        tozIV = findViewById(R.id.imageView3);
        tpbPIV = findViewById(R.id.imageP1);
        limePIV = findViewById(R.id.imageP2);
        tozPIV = findViewById(R.id.imageP3);
        int ivId = getSharedPreferences(MY_PREF,MODE_PRIVATE).getInt(IMAGEVIEW, 0);
        if(ivId != 0){
            selectedIv = findViewById(ivId);
        }else{
            selectedIv = tpbIV;
        }

        RadioGroup radioGroup = findViewById(R.id.radioG);
        radioGroup.setOnCheckedChangeListener(this);
        selectedIv.setAlpha(1f);
        tpbIV.setOnClickListener(this); tpbPIV.setOnClickListener(this);
        limeIv.setOnClickListener(this); limePIV.setOnClickListener(this);
        tozIV.setOnClickListener(this); tozPIV.setOnClickListener(this);
        mSearchView = findViewById(R.id.main_searchView);
        mButton = findViewById(R.id.search_button);
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: enter");
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buttonTapped = false;
                hideKeyboard(MainActivity.this, mButton);
                mButton.performClick();
                Log.i(TAG, "onQueryTextSubmit: ");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hideKeyboard(v);
                if(buttonTapped) {
                    v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.click_anim));
                }
                if(mSearchView.getQuery().length() == 0 || mSearchView.getQuery().toString() == " "){
                    Toast.makeText(MainActivity.this, "Enter Torrent name to search", Toast.LENGTH_SHORT).show();
                    return;
                }
                DecimalFormat format = new DecimalFormat("00");
                String enterText = mSearchView.getQuery().toString();
                String searchterm = enterText;
                if(!seasonNo.isEmpty()){
                    searchterm = searchterm + " season " + seasonNo;
                    if(!episodeNo.isEmpty()){
                        String new_season_no = format.format(Integer.parseInt(seasonNo));
                        String new_episode_no = format.format(Integer.parseInt(episodeNo));
                        searchterm = enterText + " S" + new_season_no +"E"+new_episode_no;

                    }
                }else if(seasonNo.isEmpty()&& !episodeNo.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter Season No.", Toast.LENGTH_SHORT).show();
                    RadioButton radioButton = findViewById(R.id.allRB);
                    radioButton.setChecked(true);
                    return;
                }
                if(language){
                    searchterm = searchterm + " hindi";
                }
                final Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
                intent.putExtra("search", searchterm);
                intent.putExtra("website", website);
                final ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                startActivity(intent,option.toBundle());

            }
        });
        /*
        EditText searchText = mSearchView.findViewById(mSearchView.getContext().getResources().getIdentifier("android:id/search/src_text",null,null));
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event != null&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER ){
                    mButton.performClick();
                }
                return false;
            }
        });*/

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
    protected void onStop() {
        buttonTapped = true;
        mSearchView.setQuery("", false);
        mSearchView.clearFocus();
        RadioButton radioButton = findViewById(R.id.allRB);
        radioButton.setChecked(true);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        seasonNo= "";
        episodeNo = "";
        selectedIv.setAlpha(0.5f);
        website = getSharedPreferences(MY_PREF,MODE_PRIVATE).getString(WEBSITE, "ThePirateBay");
        navigationView.setCheckedItem(R.id.torrnet);
        int ivId = getSharedPreferences(MY_PREF,MODE_PRIVATE).getInt(IMAGEVIEW, 0);
        if(ivId != 0){
            selectedIv = findViewById(ivId);
        }else{
            selectedIv = tpbIV;
        }
        selectedIv.setAlpha(1f);
        language = false;
    }
    public static void hideKeyboard(Activity activity , View view_1){
        InputMethodManager inputMethodManager  = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        /*View view = activity.getCurrentFocus();
        if(view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return;
        }*/
        if(view_1!= null){
            inputMethodManager.hideSoftInputFromWindow(view_1.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: tapped");
        selectedIv.setAlpha(0.5f);
        selectedIv = (ImageView) v;
        v.setAlpha(1f);
        v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.click_anim));
        switch (v.getId()){
            case R.id.imageView1:
                website = "ThePirateBay";
                break;
            case R.id.imageView2:
                website = "Limetorrents";
                break;
            case R.id.imageView3:
                website = "Torrentz";
                break;
            case R.id.imageP1:
                website = "ThePirateBay(Proxy)";
                break;
            case R.id.imageP2:
                website = "Limetorrents(Proxy)";
                break;
            case R.id.imageP3:
                website = "Torrentz(Proxy)";
                break;
            default:
                website = null;
        }


    }

    public void openDialog(int layoutId){
        CustomDialog customDialog = new CustomDialog(this,layoutId);
        //customDialog.getWindow().setLayout((6*width)/7,(4*height)/5 );
        customDialog.show();
    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        hideKeyboard(MainActivity.this, mSearchView);
        switch (checkedId){
            case R.id.showsRB:
                openDialog(R.layout.custom_dialog);
                break;
            case R.id.moviesRB:
                openDialog(R.layout.custom_mov_dialog);
                break;
        }
    }

    @Override
    public void applyText(String season, String episode, boolean lang) {
        Log.i(TAG, "applyText: " + season + "  " +  episode +  "  " +  lang);
        seasonNo = season;
        episodeNo = episode;
        language = lang;
        if(CustomDialog.cancelbol){
            RadioButton radioButton = findViewById(R.id.allRB);
            radioButton.setChecked(true);
        }
    }
/*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.settings:
                startActivity(new Intent(this, SettingActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.shareAction:

                break;
            case R.id.contact:

                break;
            case R.id.torrnet:
                Log.i(TAG, "onNavigationItemSelected: " + getApplicationContext());
                startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }*/

}
