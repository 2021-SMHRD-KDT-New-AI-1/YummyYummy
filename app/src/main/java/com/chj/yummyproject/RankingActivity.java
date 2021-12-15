package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    List<RankingVO> data;
    ListView rv;
    RequestQueue requestQueue;
    URLInfo urlInfo;
    JSONArray rankInfo;
    ImageView img_first, img_second, img_third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);

        data = new ArrayList<RankingVO>();
        rv = findViewById(R.id.rv);
        img_first = findViewById(R.id.img_first);
        img_second = findViewById(R.id.img_second);
        img_third = findViewById(R.id.img_third);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = urlInfo.getUrl();
        url += "Ranking";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            rankInfo = new JSONArray(response);
                            for (int i = 0;i<rankInfo.length();i++) {
                                JSONObject info = (JSONObject) rankInfo.get(i);
                                int food_count = info.getInt("food_count");
                                String food_name = info.getString("food_name");
                                String food_img_path = info.getString("food_img_path");
                                double food_score = info.getDouble("food_score");
                                String food_kor_name = info.getString("food_kor_name");

                                data.add(new RankingVO(food_count, food_img_path, food_name, food_score, food_kor_name));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RankingAdapter adapter = new RankingAdapter(getApplicationContext(), R.layout.rank_dialog, data);
                        rv.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);

        img_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankingActivity.this, FoodInfoActivity.class);
                intent.putExtra("result", "라면");
                startActivity(intent);
            }
        });

        img_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankingActivity.this, FoodInfoActivity.class);
                intent.putExtra("result", "족발");
                startActivity(intent);
            }
        });

        img_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankingActivity.this, FoodInfoActivity.class);
                intent.putExtra("result", "순대");
                startActivity(intent);
            }
        });
    }
}