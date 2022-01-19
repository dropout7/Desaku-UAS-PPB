package com.satria.desaku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.satria.desaku";
    EditText edtPassword, edtUsername, edtFullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);
        edtFullname = findViewById(R.id.edtFullname);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        getSupportActionBar().hide();
    }

    public void submitRegister(View view) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("FULLNAME", edtFullname.getText().toString());
        preferencesEditor.putString("USERNAME", edtUsername.getText().toString());
        preferencesEditor.putString("LOGIN_USERNAME", edtUsername.getText().toString());
        preferencesEditor.putString("PASSWORD", edtPassword.getText().toString());
        preferencesEditor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, com.satria.desaku.LoginActivity.class);
        startActivity(intent);
    }
}
