package com.chj.yummyproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class mainpage extends AppCompatActivity {
    Intent intent;
    String member_info_string;
    String member_id, member_pw, member_name;
    int member_halar, member_vegan, member_egg, member_nut, member_fish, member_bean;
    Button btn_rank;
    RelativeLayout rl_community;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        btn_rank = findViewById(R.id.btn_rank);
        rl_community = findViewById(R.id.rl_community);

        intent = getIntent();
        member_info_string = intent.getStringExtra("member_info");
        Log.d("info", member_info_string);

        try {
            JSONObject member_info = new JSONObject(member_info_string);
            member_id = member_info.getString("member_id");
            member_pw = member_info.getString("member_pw");
            member_name = member_info.getString("member_name");
            member_halar = member_info.getInt("halar");
            member_vegan = member_info.getInt("vegan");
            member_egg = member_info.getInt("egg");
            member_nut = member_info.getInt("nut");
            member_fish = member_info.getInt("fish");
            member_bean = member_info.getInt("bean");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rl_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainpage.this, CommunityActivity.class);
                intent.putExtra("id", member_id);
                startActivity(intent);
            }
        });
    }
}
