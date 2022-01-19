package com.satria.desaku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.satria.desaku.MainActivity;
import com.satria.desaku.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    EditText edtUserId, edtPassword;
    Button btnLogin;
    TextView txtInfo;
    String sharedPrefFile = "com.satria.desaku";
    private static String url = "http://10.0.2.2/admin/admin/view_data.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserId = findViewById(R.id.edtUserid);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserId.setText("");
        edtPassword.setText("");
        btnLogin = findViewById(R.id.btnLogin);
        txtInfo = findViewById(R.id.txtInfo);
        shp = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUserId.getText().toString().equals("") || edtPassword.getText().toString().equals(""))
                    txtInfo.setText("Please insert userid and password");
                else
                    DoLogin(edtUserId.getText().toString(), edtPassword.getText().toString());
            }
        });
    }


    public void DoLogin(String userid, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
//        String storedUsername = shp.getString("USERNAME", "secret");
//        String storedPassword = shp.getString("PASSWORD", "secret");
        String urlGetUser = url + "?get_user="+userid+"&password="+password;
        JsonObjectRequest jsonObjReq = new
                JsonObjectRequest(Request.Method.GET, urlGetUser, null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("THEBUG", String.valueOf(response));
                        try {
                            int result = response.getInt("result");
                            if(result == 1){
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Username Password Salah!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Gagal Koneksi ke server", Toast.LENGTH_LONG).show();
                Log.d("THEBUG", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonObjReq);

//        try {
//            if (password.equals(storedPassword) && userid.equals(storedUsername)) {
//                if (shp == null)
//                    shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
//
//                shpEditor = shp.edit();
//                shpEditor.putString("LOGIN_USERNAME", userid);
//                shpEditor.commit();
//
//                Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(i);
//                finish();
//            } else
//                txtInfo.setText("Invalid Credentails");
//        } catch (Exception ex) {
//            txtInfo.setText(ex.getMessage().toString());
//        }
    }
}
