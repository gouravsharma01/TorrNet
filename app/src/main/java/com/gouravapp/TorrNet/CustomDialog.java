package com.gouravapp.TorrNet;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import javax.xml.parsers.FactoryConfigurationError;

import static android.content.ContentValues.TAG;

public class CustomDialog extends Dialog implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private int layoutId;
    private boolean language = false;
    private Activity mActivity;
    private EditText season , episode;
    private DialogListener mDialogListener;
    static boolean cancelbol = false;

    public CustomDialog(Activity activity, int layoutId){
        super(activity);
        mDialogListener = (DialogListener) activity;
        this.mActivity = activity;
        this.layoutId = layoutId;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);/*
        requestWindowFeature(Window.FEATURE_NO_TITLE);*/
        setContentView(layoutId);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        Button okButton = findViewById(R.id.ok_button);
        Button cancelButton = findViewById(R.id.cancel_button);
        okButton.setOnClickListener(this); cancelButton.setOnClickListener(this);
        setCancelable(false);
        season = findViewById(R.id.season_ET);
        episode = findViewById(R.id.episode_ET);
        Switch lang = findViewById(R.id.language_drop);
        lang.setOnCheckedChangeListener(this);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: "+ v.getId());
        v.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.click_anim));
        String seasonNo = "";
        String episodeNo = "";
        switch (v.getId()){
            case R.id.ok_button:
                com.gouravapp.TorrNet.MainActivity.hideKeyboard(mActivity, v);

                if(season != null) {
                    seasonNo = season.getText().toString();
                    episodeNo = episode.getText().toString();
                    if(seasonNo.isEmpty()){
                        Toast.makeText(mActivity, "Season No. is required", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                cancelbol = false;
                //dismiss();
                break;
            case R.id.cancel_button:
                Log.i(TAG, "rb disabled");
                cancelbol = true;
                break;
        }
        mDialogListener.applyText(seasonNo, episodeNo, language);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               dismiss();
            }
        }, 500);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            language = true;
        }else {
            language = false;
        }
    }

    /*@Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            mDialogListener = (DialogListener) getContext();
        }catch (ClassCastException e){
            throw new ClassCastException(getContext().toString()+ "Must Implement");
        }
    }*/

    public interface DialogListener{
        void applyText(String season, String episode, boolean lang);
    }
}
