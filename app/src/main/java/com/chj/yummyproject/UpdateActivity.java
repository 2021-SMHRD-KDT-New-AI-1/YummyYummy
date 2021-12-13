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

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateActivity extends AppCompatActivity {
    EditText et_uppw, et_uppw2;
    Button btn_update;
    RequestQueue requestQueue;
    String member_id;
    URLInfo urlInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        et_uppw = findViewById(R.id.et_uppw);
        et_uppw2 = findViewById(R.id.et_uppw2);

        btn_update = findViewById(R.id.btn_update);

        SharedPreferences prefs = getSharedPreferences("shared", MODE_PRIVATE);
        String member_info_string = prefs.getString("INFO", null);
        Log.d("info", member_info_string);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        try {
            JSONObject member_info = new JSONObject(member_info_string);
            member_id = member_info.getString("member_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String member_pw = et_uppw.getText().toString();
                String member_pw2 = et_uppw2.getText().toString();

                String url = urlInfo.getUrl();
                url += "Update";
                url += "?member_id=" + member_id;
                url += "&member_pw=" + member_pw;

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

                            }
                        }
                );
                requestQueue.add(request);
                if (!member_pw.equals(member_pw2)) {
                    Toast.makeText(getApplicationContext(), "Not Correct Password!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Password Update Completed!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateActivity.this, Dashboard.class);
                    startActivity(intent);
                }
            }
        });
    }
}