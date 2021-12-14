package com.chj.yummyproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodInfoActivity extends AppCompatActivity {
    ImageView img_food;
    TextView tv_title, tv_ingredient, tv_describe, tv_cal, tv_flavor;
    Button btn_confirm;
    RequestQueue requestQueue;
    URLInfo urlInfo;
    int food_num;
    String food_name, food_img_path, food_ingre, food_favor, food_kcal, food_desc;
    int member_halar, member_vegan, member_egg, member_nut, member_fish, member_bean;
    int no_halar, no_vegan, no_egg, no_nut, no_fish, no_bean;
    boolean found;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodinfo);

        img_food = findViewById(R.id.img_food);
        tv_title = findViewById(R.id.tv_title);
        tv_ingredient = findViewById(R.id.tv_ingredient);
        tv_describe = findViewById(R.id.tv_describe);
        tv_cal = findViewById(R.id.tv_cal);
        tv_flavor = findViewById(R.id.tv_flavor);
        btn_confirm = findViewById(R.id.btn_confirm);

        Intent i = getIntent();
        String food_kor_name = i.getStringExtra("result");

        SharedPreferences prefs = getSharedPreferences("shared", MODE_PRIVATE);
        String member_info_string = prefs.getString("INFO", null);

        JSONObject member_info = null;
        try {
            member_info = new JSONObject(member_info_string);
            member_halar = member_info.getInt("halar");
            member_vegan = member_info.getInt("vegan");
            member_egg = member_info.getInt("egg");
            member_nut = member_info.getInt("nut");
            member_fish = member_info.getInt("fish");
            member_bean = member_info.getInt("bean");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int[] member_avoid = {member_halar, member_vegan, member_egg, member_nut, member_fish, member_bean};


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = urlInfo.getUrl();
        url += "Food";
        url += "?food_kor_name=" + food_kor_name;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray foodInfo = new JSONArray(response);
                            JSONObject info = (JSONObject) foodInfo.get(0);
                            food_name = info.getString("food_name");
                            food_img_path = info.getString("food_img_path");
                            food_ingre = info.getString("food_ingre");
                            food_favor = info.getString("food_favor");
                            food_kcal = info.getString("food_kcal");
                            food_desc = info.getString("food_desc");
                            no_halar = info.getInt("no_halar");
                            no_vegan = info.getInt("no_vegan");
                            no_egg = info.getInt("no_egg");
                            no_nut = info.getInt("no_nut");
                            no_fish = info.getInt("no_fish");
                            no_bean = info.getInt("no_bean");

                            Glide.with(getApplicationContext()).load(food_img_path).into(img_food);
                            tv_title.setText(food_name);
                            tv_ingredient.setText(food_ingre);
                            tv_describe.setText(food_desc);
                            tv_cal.setText(food_kcal);
                            tv_flavor.setText(food_favor);

                            if (no_halar == 1 && no_halar == member_halar) {
                                Log.d("할랄", "할랄");
                            }
                            if (no_vegan == 1 && no_vegan == member_vegan) {
                                Log.d("비건", "비건");
                            }
                            if (no_egg == 1 && no_egg == member_egg) {
                                Log.d("달걀", "달걀");
                            }
                            if (no_nut == 1 && no_nut == member_nut) {
                                Log.d("견과", "견과");
                            }
                            if (no_fish == 1 && no_fish == member_fish) {
                                Log.d("생선", "생선");
                            }
                            if (no_bean == 1 && no_bean == member_bean) {
                                Log.d("콩", "콩");
                            }
                            Log.d("member", String.valueOf(no_halar));
                            Log.d("food", String.valueOf(member_halar));
                            int[] food_avoid = {no_halar, no_vegan, no_egg, no_nut, no_fish, no_bean};
                            found = false;
                            for (int i=0;i<food_avoid.length;i++) {
                                if (member_avoid[i] == 1 && food_avoid[i] == 1) {
                                    found = true;
                                }
                            }
                            Log.d("found", String.valueOf(found));
                            CustomDialog customDialog = new CustomDialog(FoodInfoActivity.this);
                            if (found == true) {
                                customDialog.callFunction(member_avoid, food_avoid);
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





        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodInfoActivity.this, Dashboard.class);
                startActivity(intent);
            }
        });
    }
}
