package com.example.calendertestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        plantShiftA = findViewById(R.id.button);
        plantShiftB = findViewById(R.id.button2);
        plantShiftC = findViewById(R.id.button3);
        cMtceShiftA = findViewById(R.id.button4);
        cMtceShiftB = findViewById(R.id.button5);
        cMtceShiftC = findViewById(R.id.button6);
        securityShiftA = findViewById(R.id.button7);
        securityShiftB = findViewById(R.id.button8);
        securityShiftC = findViewById(R.id.button9);

        plantShiftA.setOnClickListener(this);
        plantShiftB.setOnClickListener(this);
        plantShiftC.setOnClickListener(this);
        cMtceShiftA.setOnClickListener(this);
        cMtceShiftB.setOnClickListener(this);
        cMtceShiftC.setOnClickListener(this);
        securityShiftA.setOnClickListener(this);
        securityShiftB.setOnClickListener(this);
        securityShiftC.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                startActivity(new Intent(this, PlantShiftAActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, PlantShiftBActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(this, PlantShiftCActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(this, CMtceShiftAActivity.class));
                break;
            case R.id.button5:
                startActivity(new Intent(this, CMtceShiftBActivity.class));
                break;
            case R.id.button6:
                startActivity(new Intent(this, CMtceShiftCActivity.class));
                break;
            case R.id.button7:
                startActivity(new Intent(this, SecurityShiftAActivity.class));
                break;
            case R.id.button8:
                startActivity(new Intent(this, SecurityShiftBActivity.class));
                break;
            case R.id.button9:
                startActivity(new Intent(this, SecurityShiftCActivity.class));
                break;
            default:
        }
    }
}