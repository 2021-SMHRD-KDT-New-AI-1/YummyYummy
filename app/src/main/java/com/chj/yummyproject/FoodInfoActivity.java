package com.chj.yummyproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
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
    String food_img_path, food_ingre, food_favor, food_kcal, food_desc;
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
        String food_name = i.getStringExtra("result");

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = urlInfo.getUrl();
        url += "Food";
        url += "?food_name=" + food_name;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray foodInfo = new JSONArray(response);
                            JSONObject info = (JSONObject) foodInfo.get(0);
                            food_num = info.getInt("food_num");
                            food_img_path = info.getString("food_img_path");
                            food_ingre = info.getString("food_ingre");
                            food_favor = info.getString("food_favor");
                            food_kcal = info.getString("food_kcal");
                            food_desc = info.getString("food_desc");

                            Glide.with(getApplicationContext()).load(food_img_path).into(img_food);
                            tv_title.setText(food_name);
                            tv_ingredient.setText(food_ingre);
                            tv_describe.setText(food_desc);
                            tv_cal.setText(food_kcal);
                            tv_flavor.setText(food_favor);

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
