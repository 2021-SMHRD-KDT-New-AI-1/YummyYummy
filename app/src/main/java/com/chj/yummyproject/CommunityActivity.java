package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    AdapterFeed adapterFeed;
    FloatingActionButton fab_main;
    String member_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        recyclerView = findViewById(R.id.recyclerView);
        fab_main = findViewById(R.id.fab_main);

        Intent i = getIntent();
        member_id = i.getStringExtra("id");

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
            }
        });

        populateRecyclerView();
    }

    public void populateRecyclerView() {
        ModelFeed modelFeed = new ModelFeed(1,9,2, R.drawable.person, R.drawable.img_post1, "John Cena", "2 hrs", "Today, I went to eat Gamjatang with my Korean friends, and it was so delicious to eat hot rice with soju.");
        modelFeedArrayList.add(modelFeed);
        modelFeed = new ModelFeed(2,26,6, R.drawable.person, 0, "Karun Shrestha", "9 hrs", "Don't be afraid of your fears. They're not there to scare you. They're let you know that something is worth it.");
        modelFeedArrayList.add(modelFeed);
        modelFeed = new ModelFeed(3,17,5, R.drawable.person, R.drawable.img_post2, "Cho Hyun Joong", "13 hrs", "That reflection!!");
        modelFeedArrayList.add(modelFeed);

        adapterFeed.notifyDataSetChanged();
    }
}