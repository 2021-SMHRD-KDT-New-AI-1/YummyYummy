package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

public class CommentActivity extends AppCompatActivity {
    Button btn_comment;
    EditText et_comment;
    RequestQueue requestQueue;
    List<CommentVO> data;
    int post_num;
    ListView lv;
    URLInfo urlInfo;
    JSONArray commentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        btn_comment = findViewById(R.id.btn_comment);
        et_comment = findViewById(R.id.et_comment);
        Intent i = getIntent();
        post_num = i.getIntExtra("post_num", 0);
        data = new ArrayList<CommentVO>();
        lv = findViewById(R.id.lv);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = urlInfo.getUrl();
        url += "Comment";
        url += "?post_num=" + post_num;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            commentInfo = new JSONArray(response);
                            for (int i=0;i<commentInfo.length();i++) {
                                JSONObject info = (JSONObject) commentInfo.get(i);
                                int comment_num = info.getInt("comment_num");
                                String member_id = info.getString("member_id");
                                String comment_cont = info.getString("comment_cont");
                                String comment_date = info.getString("comment_date");

                                data.add(new CommentVO(member_id, comment_cont, comment_date, comment_num));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        CommentAdapter adapter = new CommentAdapter(getApplicationContext(), R.layout.comment_list, data);
                        lv.setAdapter(adapter);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                String member_id = i.getStringExtra("member_id");
                String comment_con = et_comment.getText().toString();
                String url = urlInfo.getUrl();
                url += "Write?member_id=" + member_id;
                url += "&comment_con=" + comment_con;
                url += "&post_num=" + post_num;
                Log.d("result", member_id + comment_con + post_num);

                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Intent intent = getIntent();
                                finish(); //현재 액티비티 종료 실시
                                overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                                startActivity(intent); //현재 액티비티 재실행 실시
                                overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
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