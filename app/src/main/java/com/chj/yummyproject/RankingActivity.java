package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.service.notification.NotificationListenerService;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);

        data = new ArrayList<RankingVO>();
        rv = findViewById(R.id.rv);

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

                                data.add(new RankingVO(food_count, food_img_path, food_name, food_score));
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
    }
}