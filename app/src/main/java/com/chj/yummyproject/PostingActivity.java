package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class PostingActivity extends AppCompatActivity {
    TextView tv_id;
    EditText et_post;
    Button btn_send;
    String member_id;
    URLInfo urlInfo;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);



        tv_id = findViewById(R.id.tv_id);
        et_post = findViewById(R.id.et_post);
        btn_send = findViewById(R.id.btn_send);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        Intent i = getIntent();
        member_id = i.getStringExtra("id");

        tv_id.setText(member_id);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = member_id;
                String content = et_post.getText().toString();

                String url = urlInfo.getUrl();
                url += "Post?id=" + id;
                url += "&content=" + content;

                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("PostingActivity", response);
                                Intent intent = new Intent(PostingActivity.this, CommunityActivity.class);
                                intent.putExtra("id", member_id);
                                startActivity(intent);
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );
                requestQueue.add(request);
            }
        });

    }
}