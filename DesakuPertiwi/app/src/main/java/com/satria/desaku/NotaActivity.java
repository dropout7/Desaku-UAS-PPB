package com.satria.desaku;
import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
public class NotaActivity extends AppCompatActivity{
    ListView listView;
    private static String url = "http://10.0.2.2/admin/admin/view_data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_nota);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String purchaseAmount = intent.getStringExtra("purchaseAmount");
        String kembalianAmount = intent.getStringExtra("kembalianAmount");
        String pembayaranAmount = intent.getStringExtra("pembayaranAmount");
        ArrayList list_belanja_array = intent.getStringArrayListExtra("listNotaBelanja");

        String[] from = {"qty", "nama", "idku", "harga"};
        int to[] = {R.id.qty, R.id.nama, R.id.idku, R.id.harga};
        listView = (ListView) findViewById(R.id.list_belanjaan);
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(),
                list_belanja_array, R.layout.list_item_belanjaan, from, to);

        listView.setAdapter(simpleAdapter);
        TextView purchaseAmountTv = findViewById(R.id.purchaseAmount);
        TextView kembaliantv = findViewById(R.id.kembalianAmount);
        TextView pembayarantv = findViewById(R.id.pembayaranAmount);

        purchaseAmountTv.setText("Total Pembelian : Rp."  + purchaseAmount);
        kembaliantv.setText("Total Kembalian : Rp."  + kembalianAmount);
        pembayarantv.setText("Total Pembayaran :Rp."  + pembayaranAmount);
    }
}
