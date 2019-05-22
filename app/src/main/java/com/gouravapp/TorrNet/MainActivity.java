package com.gouravapp.TorrNet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener , CustomDialog.DialogListener {
    private static final String TAG = "MainActivity";
    private ImageView tpbIV, limeIv, tozIV, tpbPIV, limePIV, tozPIV;
    private SearchView mSearchView;
    private Button mButton;
    private ImageView selectedIv ;
    private String website;
    private Boolean buttonTapped = false;
    private String  seasonNo = "";
    private String  episodeNo = "";
    boolean language = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        website = "ThePirateBay";
        tpbIV = findViewById(R.id.imageView1);
        limeIv = findViewById(R.id.imageView2);
        tozIV = findViewById(R.id.imageView3);
        tpbPIV = findViewById(R.id.imageP1);
        limePIV = findViewById(R.id.imageP2);
        tozPIV = findViewById(R.id.imageP3);
        selectedIv = tpbIV;
        RadioGroup radioGroup = findViewById(R.id.radioG);
        radioGroup.setOnCheckedChangeListener(this);
        tpbIV.setAlpha(1f);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 500);
        super.onPause();


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
        language = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        com.gouravapp.TorrNet.CustomDialog customDialog = new com.gouravapp.TorrNet.CustomDialog(this,layoutId);
        customDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
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
}
