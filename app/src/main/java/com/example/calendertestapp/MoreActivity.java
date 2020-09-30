package com.example.calendertestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;

//        Add this line to Manifest for the activity
//        <meta-data
//        android:name="android.support.PARENT_ACTIVITY"
//        android:value="MainActivity" />
    }
}