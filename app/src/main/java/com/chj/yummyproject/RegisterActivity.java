package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {
    EditText et_id, et_pw, et_pw2;
    Button btn_double_check, btn_register;
    RequestQueue requestQueue;
    Intent i;
    JSONArray idInfo;
    URLInfo urlInfo = new URLInfo();
    boolean found = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        et_pw2 = findViewById(R.id.et_pw2);

        btn_double_check = findViewById(R.id.btn_double_check);
        btn_register = findViewById(R.id.btn_register);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = getIntent();
                String id = et_id.getText().toString();
                String pw = et_pw.getText().toString();
                String pw2 = et_pw2.getText().toString();
                String name = "name";
                int halar = i.getIntExtra("halar",0);
                int vegan = i.getIntExtra("vegan",0);
                int egg = i.getIntExtra("egg",0);
                int nut = i.getIntExtra("nut",0);
                int fish = i.getIntExtra("fish",0);
                int bean = i.getIntExtra("bean",0);
                String url = urlInfo.getUrl();
                url += "Register?id=" + id;
                url += "&pw=" + pw;
                url += "&name=" + name;
                url += "&halar=" + halar;
                url += "&vegan=" + vegan;
                url += "&egg=" + egg;
                url += "&nut=" + nut;
                url += "&fish=" + fish;
                url += "&bean=" + bean;

                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

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

                if (!pw.equals(pw2)) {
                    Toast.makeText(getApplicationContext(), "Not Correct Password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Register Completed!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        btn_double_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String written_id = et_id.getText().toString();
                String url = urlInfo.getUrl();
                url += "DoubleCheck";

                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    found = false;
                                    idInfo = new JSONArray(response);
                                    for (int i = 0;i<idInfo.length();i++) {
                                        JSONObject info = (JSONObject) idInfo.get(i);
                                        String id = info.getString("member_id");
                                        if (written_id.equals(id)) {
                                            found = true;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );
                requestQueue.add(request);
                if (found == true) {
                    Toast.makeText(getApplicationContext(), "Duplicated ID", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "ID is Possible", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}