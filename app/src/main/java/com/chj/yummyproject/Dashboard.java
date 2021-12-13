package com.chj.yummyproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {
    String member_id, member_pw, member_name;
    int member_halar, member_vegan, member_egg, member_nut, member_fish, member_bean;
    LinearLayout ll_community, ll_best, ll_dialog;
    TextView tv_username, tv_userid;
    FloatingActionButton btn_camera, btn_image_camera, btn_text_camera;
    boolean isFabOpen = false;
    private long backBtnTime = 0;
    DrawerLayout drawer_layout;
    View drawer;
    CardView cv_home, cv_update, cv_delete, cv_logout;
    ImageView img_menu;
    URLInfo urlInfo;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawer_layout = findViewById(R.id.drawer_layout);
        drawer = findViewById(R.id.drawer);

        ll_community = findViewById(R.id.ll_community);
        ll_best = findViewById(R.id.ll_best);
        ll_dialog = findViewById(R.id.ll_dialog);
        tv_username = findViewById(R.id.tv_username);
        tv_userid = findViewById(R.id.tv_userid);
        img_menu = findViewById(R.id.img_menu);

        btn_camera = findViewById(R.id.btn_camera);
        btn_image_camera = findViewById(R.id.btn_image_camera);
        btn_text_camera = findViewById(R.id.btn_text_camera);

        cv_home = findViewById(R.id.cv_home);
        cv_update = findViewById(R.id.cv_update);
        cv_delete = findViewById(R.id.cv_delete);
        cv_logout = findViewById(R.id.cv_logout);

        SharedPreferences prefs = getSharedPreferences("shared", MODE_PRIVATE);
        String member_info_string = prefs.getString("INFO", null);
        Log.d("info", member_info_string);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

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

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(drawer);
            }
        });

        drawer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        cv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //TODO 액티비티 화면 재갱신 시키는 코드
                    Intent intent = getIntent();
                    finish(); //현재 액티비티 종료 실시
                    overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                    startActivity(intent); //현재 액티비티 재실행 실시
                    overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        cv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(Dashboard.this)
                        .setTitle("Logout")
                        .setMessage("Do you want log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Dashboard.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog msgDlg = msgBuilder.create();
                msgDlg.show();
            }
        });

        cv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, UpdateActivity.class);
                startActivity(intent);
            }
        });

        cv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlInfo.getUrl();
                url += "Delete";
                url += "?member_id=" + member_id;

                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );
                requestQueue.add(request);
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(Dashboard.this)
                        .setTitle("Withdraw")
                        .setMessage("Do you want to leave?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Dashboard.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog msgDlg = msgBuilder.create();
                msgDlg.show();
            }
        });

        ll_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, CommunityActivity.class);
                intent.putExtra("id", member_id);
                startActivity(intent);
            }
        });

        tv_username.setText(member_id);
        tv_userid.setText(member_id);

        ll_best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
            finishAffinity();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "Press it one more time to end.", Toast.LENGTH_SHORT).show();
        }
    }
}