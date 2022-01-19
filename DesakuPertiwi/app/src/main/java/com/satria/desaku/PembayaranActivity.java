package com.satria.desaku;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PembayaranActivity extends AppCompatActivity {
    ListView listView;
    String[] list_belanja;
    ArrayList<HashMap<String, Object>> list_jadi_belanja = new ArrayList<>();
    private static String url = "http://10.0.2.2/admin/admin/view_data.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pembayaran);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String purchaseAmount = intent.getStringExtra("purchaseAmount");
        String listBelanja = intent.getStringExtra("listOfBelanja");
        Log.d("THEBUG", String.valueOf(listBelanja));
        list_belanja = listBelanja.toString().split(",");
        listView = (ListView) findViewById(R.id.list_belanjaan);

        for (String part : list_belanja) {
            getProductDetail(part);
        }

        TextView purchaseAmountTv = findViewById(R.id.purchaseAmount);
        TextView kembaliantv = findViewById(R.id.textView18);
        purchaseAmountTv.setText(purchaseAmount);
        EditText edtPaymentAmount = findViewById(R.id.editTextNumber);

        edtPaymentAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int harga =  parseInt(editable.toString()) - parseInt(purchaseAmount);
                kembaliantv.setText(harga + "");
            }
        });
    }

    public void changePaymentAmount() {
        Intent intent = getIntent();
        Integer purchaseAmount = parseInt(intent.getStringExtra("purchaseAmount")) ;
        EditText edtPaymentAmount = findViewById(R.id.editTextNumber);
        TextView returnAmountText = findViewById(R.id.textView18);
        if(edtPaymentAmount.getText().toString().equals("") || edtPaymentAmount.getText().toString().equals("0")) {
            return;
        }
        Integer paymentAmount = parseInt(edtPaymentAmount.getText().toString());
        Integer returnAmount = paymentAmount - purchaseAmount;
        returnAmountText.setText(returnAmount.toString());
    }

    public void finishPurchaseDetail(View view) {
        TextView totalTrans = findViewById(R.id.purchaseAmount);
        TextView kembalian = findViewById(R.id.textView18);
        EditText pembayaran = findViewById(R.id.editTextNumber);

        HashMap<String, Object> params = new HashMap();
        ArrayList<HashMap<String, Object>> list_sebelum_jadi = list_jadi_belanja;

        params.put("total_trans", totalTrans.getText().toString());
        params.put("total_kembalian", kembalian.getText().toString());
        params.put("total_pembayaran",pembayaran.getText().toString());
        list_jadi_belanja.add(params);
        JSONArray arrayku = new JSONArray(list_jadi_belanja);
        String urlPostTo = url + "?save_post="+String.valueOf(arrayku);
        //Log.d("THEBUGXXX", urlPostTo);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, urlPostTo,
                null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("THEBUGXXX", String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);
        Toast toast = Toast.makeText(PembayaranActivity.this,"Total Bayar : Rp. " + pembayaran.getText() + " Dengan Kembalian " + kembalian.getText(),Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, NotaActivity.class);

        intent.putExtra("purchaseAmount", totalTrans.getText().toString());
        intent.putExtra("kembalianAmount", kembalian.getText().toString());
        intent.putExtra("pembayaranAmount", pembayaran.getText().toString());
        intent.putExtra("listNotaBelanja", list_sebelum_jadi);
        startActivity(intent);
    }

    public void getProductDetail(String id_product) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlGetUser = url + "?get_produk_detail="+id_product;
        Log.d("THEBUG", String.valueOf(urlGetUser));
        JsonObjectRequest jsonObjReq = new
                JsonObjectRequest(Request.Method.GET, urlGetUser, null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("THEBUG", String.valueOf(response));

                        try {
                            JSONObject result = response.getJSONObject("result");
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("qty", 1);
                            map.put("nama", result.getString("nama_paket"));
                            map.put("idku", result.getString("id_paket"));
                            map.put("harga", result.getInt("harga"));
                            list_jadi_belanja.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String[] from = {"qty", "nama", "idku", "harga"};
                        int to[] = {R.id.qty, R.id.nama, R.id.idku, R.id.harga};

                        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(),
                                list_jadi_belanja, R.layout.list_item_belanjaan, from, to);
                        listView.setAdapter(simpleAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PembayaranActivity.this, "Gagal Koneksi ke server", Toast.LENGTH_LONG).show();
                Log.d("THEBUG", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonObjReq);
    }
}
