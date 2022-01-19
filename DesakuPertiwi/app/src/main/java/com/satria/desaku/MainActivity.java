package com.satria.desaku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private ArrayList<Paket> mPaket;
    private PaketAdapter mAdapter;
    private SwipeRefreshLayout swipe;
    private ArrayList<String> list_produk;
    RecyclerView.LayoutManager layoutManager;
    private static String url = "http://10.0.2.2/admin/admin/view_data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           mPaket = new ArrayList<>();
                           swipe.setRefreshing(true);
                           mPaket.clear();
                           mRecyclerView.setHasFixedSize(true);
                           layoutManager = new
                                   GridLayoutManager(getApplicationContext(), 2);
                           mRecyclerView.setLayoutManager(layoutManager);
                           mAdapter = new
                                   PaketAdapter(getApplicationContext(), mPaket,
                                   findViewById(R.id.totalPrice), findViewById(R.id.listof));
                           mRecyclerView.setAdapter(mAdapter);
                           display();
                       }
                   }
        );
//        initializeData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_drawer, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.updateuser) {
            startActivity(new Intent(this, UserPassword.class));
        } else if (item.getItemId() == R.id.lokasi) {
            startActivity(new Intent(this, LocationActivity.class));
        } else if (item.getItemId() == R.id.call) {
            startActivity(new Intent(this, CallCenterActivity.class));
        } else if (item.getItemId() == R.id.smscenter) {
            startActivity(new Intent(this, SMSCenterActivity.class));
        }

        return true;
    }

    public void onClickText(View v) {
        TextView txtNama = findViewById(R.id.name);
        TextView txtHarga = findViewById(R.id.hargaText);
        TextView txtDeskripsi = findViewById(R.id.deskripsi);

        Intent myIntent = new Intent(this, DetailActivity.class);

        myIntent.putExtra("nama", txtNama.getText());
        myIntent.putExtra("harga", txtHarga.getText());
        myIntent.putExtra("deskripsi", txtDeskripsi.getText());
        myIntent.putExtra("gambar", R.id.foodImageDetail);
        startActivity(myIntent);
    }

    public void goToPurchaseDetail(View view) {
        TextView purchaseAmountTv = findViewById(R.id.totalPrice);
        String purchaseAmount = purchaseAmountTv.getText().toString().split("=")[1].trim();
        TextView listofbelanjatv = findViewById(R.id.listof);
        String listofbelanja = listofbelanjatv.getText().toString();

        Intent intent = new Intent(this, PembayaranActivity.class);

        intent.putExtra("purchaseAmount", purchaseAmount);
        intent.putExtra("listOfBelanja", listofbelanja);

        startActivity(intent);
    }

    public void onRefresh() {
        mPaket.clear();
        mAdapter.notifyDataSetChanged();
        display();
    }

    private void display() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlGetProduct = url + "?get_produk=1";
        JsonArrayRequest jsonArrayRequest = new
                JsonArrayRequest(Request.Method.GET, urlGetProduct, null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Log.d("THEBUG", String.valueOf(object));
                                Paket datas = new Paket(
                                        object.getInt("id_paket"),
                                        object.getString("nama_paket").toString(),
                                        object.getString("deskripsi").toString(),
                                        object.getInt("harga"),
                                        object.getString("gambar")
                                );
                                mPaket.add(datas);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Gagal Koneksi ke server", Toast.LENGTH_LONG).show();
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }
}