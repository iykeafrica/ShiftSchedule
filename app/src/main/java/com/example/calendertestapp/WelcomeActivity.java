package com.example.calendertestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.example.calendertestapp.cmtce.CMtceShiftAActivity;
import com.example.calendertestapp.cmtce.CMtceShiftBActivity;
import com.example.calendertestapp.cmtce.CMtceShiftCActivity;
import com.example.calendertestapp.plant.PlantShiftAActivity;
import com.example.calendertestapp.plant.PlantShiftBActivity;
import com.example.calendertestapp.plant.PlantShiftCActivity;
import com.example.calendertestapp.security.SecurityShiftAActivity;
import com.example.calendertestapp.security.SecurityShiftBActivity;
import com.example.calendertestapp.security.SecurityShiftCActivity;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button plantShiftA, plantShiftB, plantShiftC, cMtceShiftA, cMtceShiftB, cMtceShiftC, securityShiftA, securityShiftB, securityShiftC;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = pref.edit();

        plantShiftA = findViewById(R.id.button);
        plantShiftB = findViewById(R.id.button2);
        plantShiftC = findViewById(R.id.button3);
        cMtceShiftA = findViewById(R.id.button4);
        cMtceShiftB = findViewById(R.id.button5);
        cMtceShiftC = findViewById(R.id.button6);
        securityShiftA = findViewById(R.id.button7);
        securityShiftB = findViewById(R.id.button8);
        securityShiftC = findViewById(R.id.button9);

        if (!pref.getBoolean("first_time_on_boarding", false)) {
            plantShiftA.setOnClickListener(this);
            plantShiftB.setOnClickListener(this);
            plantShiftC.setOnClickListener(this);
            cMtceShiftA.setOnClickListener(this);
            cMtceShiftB.setOnClickListener(this);
            cMtceShiftC.setOnClickListener(this);
            securityShiftA.setOnClickListener(this);
            securityShiftB.setOnClickListener(this);
            securityShiftC.setOnClickListener(this);
        } else {
            if (pref.getBoolean("first_time_on_boarding", true)){
                startActivity(new Intent(this, PlantShiftAActivity.class));
            } else if (pref.getBoolean("first_time_on_boarding", true)){
                startActivity(new Intent(this, PlantShiftBActivity.class));
            } else if (pref.getBoolean("first_time_on_boarding", true)){
                startActivity(new Intent(this, PlantShiftCActivity.class));
            } else if (pref.getBoolean("first_time_on_boarding", true)){
                startActivity(new Intent(this, CMtceShiftAActivity.class));
            } else if (pref.getBoolean("first_time_on_boarding", true)){
                startActivity(new Intent(this, CMtceShiftBActivity.class));
            } else if (pref.getBoolean("first_time_on_boarding", true)){
                startActivity(new Intent(this, CMtceShiftCActivity.class));
            } else if (pref.getBoolean("first_time_on_boarding", true)){
                startActivity(new Intent(this, SecurityShiftAActivity.class));
            } else if (pref.getBoolean("first_time_on_boarding", true)){
                startActivity(new Intent(this, SecurityShiftBActivity.class));
            } else if (pref.getBoolean("first_time_on_boarding", true)){
                startActivity(new Intent(this, SecurityShiftCActivity.class));
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(this, PlantShiftAActivity.class));
                mEditor.putBoolean("first_time_on_boarding", true);
                mEditor.apply();
                break;
            case R.id.button2:
                startActivity(new Intent(this, PlantShiftBActivity.class));
                mEditor.putBoolean("first_time_on_boarding", true);
                mEditor.apply();
                break;
            case R.id.button3:
                startActivity(new Intent(this, PlantShiftCActivity.class));
                mEditor.putBoolean("first_time_on_boarding", true);
                mEditor.apply();
                break;
            case R.id.button4:
                startActivity(new Intent(this, CMtceShiftAActivity.class));
                mEditor.putBoolean("first_time_on_boarding", true);
                mEditor.apply();
                break;
            case R.id.button5:
                startActivity(new Intent(this, CMtceShiftBActivity.class));
                mEditor.putBoolean("first_time_on_boarding", true);
                mEditor.apply();
                break;
            case R.id.button6:
                startActivity(new Intent(this, CMtceShiftCActivity.class));
                mEditor.putBoolean("first_time_on_boarding", true);
                mEditor.apply();
                break;
            case R.id.button7:
                startActivity(new Intent(this, SecurityShiftAActivity.class));
                mEditor.putBoolean("first_time_on_boarding", true);
                mEditor.apply();
                break;
            case R.id.button8:
                startActivity(new Intent(this, SecurityShiftBActivity.class));
                mEditor.putBoolean("first_time_on_boarding", true);
                mEditor.apply();
                break;
            case R.id.button9:
                startActivity(new Intent(this, SecurityShiftCActivity.class));
                mEditor.putBoolean("first_time_on_boarding", true);
                mEditor.apply();
                break;
            default:
        }
    }
}