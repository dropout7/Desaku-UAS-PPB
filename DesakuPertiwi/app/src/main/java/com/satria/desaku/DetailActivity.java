package com.satria.desaku;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    TextView edtUserId;
    TextView edtPassword;
    TextView edtDeskripsi;
    ImageView mFoodImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent myIntent = getIntent(); // this is just for example purpose
        String coba = myIntent.getStringExtra("nama");
        int jadi = myIntent.getIntExtra("harga", 0);
        String deskripsi = myIntent.getStringExtra("deskripsi");


        edtUserId = findViewById(R.id.detail_text);
        edtPassword = findViewById(R.id.hargaText);
        edtDeskripsi = findViewById(R.id.deskripsi_text);
        mFoodImage = findViewById(R.id.foodImageDetail);

        int resId = myIntent.getIntExtra("gambar", 0);
        mFoodImage.setImageResource(resId);

        edtUserId.setText("Nama : " + coba);
        edtPassword.setText("Harga : " + jadi);
        edtDeskripsi.setText("Deskripsi : " + deskripsi);
    }
}
