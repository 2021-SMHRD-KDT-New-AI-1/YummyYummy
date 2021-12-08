package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CommentActivity extends AppCompatActivity {
    TextView tv_postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        tv_postId = findViewById(R.id.tv_postId);
        Intent i = getIntent();
        int post_id = i.getIntExtra("post_num", 0);

        tv_postId.setText(String.valueOf(post_id));
    }
}