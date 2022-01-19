package com.satria.desaku;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.satria.desaku.R;

public class CallCenterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_center);
        getSupportActionBar().hide();
    }
}