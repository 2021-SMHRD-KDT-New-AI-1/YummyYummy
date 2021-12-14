package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button btn_create, btn_login;
    EditText login_id, login_pw;
    URLInfo urlInfo;
    RequestQueue requestQueue;
    private long backBtnTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_create = findViewById(R.id.btn_create);
        btn_login = findViewById(R.id.btn_login);
        login_id = findViewById(R.id.login_id);
        login_pw = findViewById(R.id.login_pw);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterAllergyActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = login_id.getText().toString();
                String user_pw = login_pw.getText().toString();

                String url = urlInfo.getUrl();
                url += "Login";
                Log.d("test", "onClick: "+url);
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("test", "onResponse: "+response);
                                    JSONArray memberInfo = new JSONArray(response);
                                    boolean found = false;
                                    for (int i=0;i<memberInfo.length();i++) {
                                        JSONObject info = (JSONObject) memberInfo.get(i);
                                        String id = info.getString("member_id");
                                        String pw = info.getString("member_pw");
                                        String name = info.getString("member_name");
                                        int halar = info.getInt("halar");
                                        int vegan = info.getInt("vegan");
                                        int egg = info.getInt("egg");
                                        int nut = info.getInt("nut");
                                        int fish = info.getInt("fish");
                                        int bean = info.getInt("bean");

                                        if (user_id.equals(id) && user_pw.equals(pw)) {
                                            found = true;
                                            SharedPreferences.Editor editor = getSharedPreferences("shared", MODE_PRIVATE).edit();
                                            editor.putString("INFO",info.toString());
                                            editor.commit();
                                            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                            startActivity(intent);
                                        }
                                    }
                                    if (found == false) {
                                        Toast.makeText(getApplicationContext(), "Not found ID or PW", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                );
                requestQueue.add(request);


            }
        });
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
            finishAffinity();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "Press it one more time to end.", Toast.LENGTH_SHORT).show();
        }
    }
}