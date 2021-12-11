package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {
    Intent intent;
    String member_info_string;
    String member_id, member_pw, member_name;
    int member_halar, member_vegan, member_egg, member_nut, member_fish, member_bean;
    LinearLayout ll_community, ll_best, ll_dialog;
    TextView tv_username;
    FloatingActionButton btn_camera, btn_image_camera, btn_text_camera;
    boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ll_community = findViewById(R.id.ll_community);
        ll_best = findViewById(R.id.ll_best);
        ll_dialog = findViewById(R.id.ll_dialog);
        tv_username = findViewById(R.id.tv_username);

        btn_camera = findViewById(R.id.btn_camera);
        btn_image_camera = findViewById(R.id.btn_image_camera);
        btn_text_camera = findViewById(R.id.btn_text_camera);

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

        ll_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, CommunityActivity.class);
                intent.putExtra("id", member_id);
                startActivity(intent);
            }
        });

        tv_username.setText(member_id);

        ll_best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        ll_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, DialogActivity.class);
                startActivity(intent);
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFabOpen) {
                    ObjectAnimator.ofFloat(btn_image_camera, "translationY", 0f).start();
                    ObjectAnimator.ofFloat(btn_text_camera, "translationY", 0f).start();
                } else {
                    ObjectAnimator.ofFloat(btn_image_camera, "translationY", -200f).start();
                    ObjectAnimator.ofFloat(btn_text_camera, "translationY", -400f).start();
                }
                isFabOpen = !isFabOpen;
            }
        });

        btn_image_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, CameraActivity.class);
                startActivity(intent);
            }
        });
    }
}