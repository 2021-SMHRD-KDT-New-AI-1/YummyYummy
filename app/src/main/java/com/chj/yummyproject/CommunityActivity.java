package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    AdapterFeed adapterFeed;
    FloatingActionButton fab_main;
    String member_id;
    RequestQueue requestQueue;
    URLInfo urlInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        recyclerView = findViewById(R.id.recyclerView);
        fab_main = findViewById(R.id.fab_main);

        Intent i = getIntent();
        member_id = i.getStringExtra("id");

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapterFeed = new AdapterFeed(this, modelFeedArrayList);
        recyclerView.setAdapter(adapterFeed);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityActivity.this, PostingActivity.class);
                intent.putExtra("id", member_id);
                startActivity(intent);
                finish();
            }
        });

        populateRecyclerView();
    }

    public void populateRecyclerView() {
        String url = urlInfo.getUrl();
        url += "Community";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray postInfo = new JSONArray(response);
                            for (int i=0;i<postInfo.length();i++) {
                                JSONObject info = (JSONObject) postInfo.get(i);
                                int post_num = info.getInt("post_num");
                                String member_id = info.getString("member_id");
                                String post_content = info.getString("post_content");
                                String post_date = info.getString("post_date");
                                int post_like = info.getInt("post_like");
                                int post_comments = info.getInt("post_comments");
                                ModelFeed modelFeed = new ModelFeed(post_num, post_like, post_comments, R.drawable.person, 0, member_id, post_date, post_content);
                                modelFeedArrayList.add(modelFeed);
                            }
                            ModelFeed modelFeed = new ModelFeed(10,9,2, R.drawable.person, R.drawable.img_post1, "John Cena", "2021-12-08T15:00:00.000Z", "Today, I went to eat Gamjatang with my Korean friends, and it was so delicious to eat hot rice with soju.");
                            modelFeedArrayList.add(modelFeed);
                            modelFeed = new ModelFeed(11,26,6, R.drawable.person, 0, "Karun Shrestha", "2021-12-08T15:00:00.000Z", "Don't be afraid of your fears. They're not there to scare you. They're let you know that something is worth it.");
                            modelFeedArrayList.add(modelFeed);
                            modelFeed = new ModelFeed(12,17,5, R.drawable.person, R.drawable.img_post2, "Cho Hyun Joong", "2021-12-08T15:00:00.000Z", "That reflection!!");
                            modelFeedArrayList.add(modelFeed);
                            adapterFeed.notifyDataSetChanged();
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
    }
}