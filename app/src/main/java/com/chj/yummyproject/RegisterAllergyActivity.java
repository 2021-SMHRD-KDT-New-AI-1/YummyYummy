package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterAllergyActivity extends AppCompatActivity {
    Button btn_egg, btn_nut, btn_fish, btn_bean, btn_meat, btn_halar, btn_next;
    int egg_checked, nut_checked, fish_checked, bean_checked, meat_checked, halar_checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_allergy);

        egg_checked = 0;
        nut_checked = 0;
        fish_checked = 0;
        bean_checked = 0;
        meat_checked = 0;
        halar_checked = 0;

        btn_egg = findViewById(R.id.btn_egg);
        btn_nut = findViewById(R.id.btn_nut);
        btn_fish = findViewById(R.id.btn_fish);
        btn_bean = findViewById(R.id.btn_bean);
        btn_meat = findViewById(R.id.btn_meat);
        btn_halar = findViewById(R.id.btn_hala);
        btn_next = findViewById(R.id.btn_next);

        btn_egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (egg_checked == 0) {
                    btn_egg.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFF")));
                    egg_checked = 1;
                } else {
                    btn_egg.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C1BC7F")));
                    egg_checked = 0;
                }
            }
        });

        btn_nut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nut_checked == 0) {
                    btn_nut.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFF")));
                    nut_checked = 1;
                } else {
                    btn_nut.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C1BC7F")));
                    nut_checked = 0;
                }
            }
        });

        btn_fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fish_checked == 0) {
                    btn_fish.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFF")));
                    fish_checked = 1;
                } else {
                    btn_fish.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C1BC7F")));
                    fish_checked = 0;
                }
            }
        });

        btn_bean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bean_checked == 0) {
                    btn_bean.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFF")));
                    bean_checked = 1;
                } else {
                    btn_bean.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C1BC7F")));
                    bean_checked = 0;
                }
            }
        });

        btn_meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (meat_checked == 0) {
                    btn_meat.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFF")));
                    meat_checked = 1;
                } else {
                    btn_meat.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C1BC7F")));
                    meat_checked = 0;
                }
            }
        });

        btn_halar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (halar_checked == 0) {
                    btn_halar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFF")));
                    halar_checked = 1;
                } else {
                    btn_halar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C1BC7F")));
                    halar_checked = 0;
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterAllergyActivity.this, RegisterActivity.class);
                intent.putExtra("egg", egg_checked);
                intent.putExtra("nut", nut_checked);
                intent.putExtra("fish", fish_checked);
                intent.putExtra("bean", bean_checked);
                intent.putExtra("vegan",meat_checked);
                intent.putExtra("halar", halar_checked);
                startActivity(intent);
            }
        });
    }
}