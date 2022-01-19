package com.satria.desaku;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity{

    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.satria.desaku";

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();

            }
        },3000);

    }
    protected void checkUser() {
        String storedUsername = mPreferences.getString("LOGIN_USERNAME", "false");
        if(storedUsername.equals("false")) {
            Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            Intent intent=new Intent(SplashActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
