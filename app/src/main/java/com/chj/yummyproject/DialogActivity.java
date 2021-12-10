package com.chj.yummyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DialogActivity extends AppCompatActivity {
    TextView toLanguageTV;
    TextView fromLanguageTV;
    TextView changedTextTV;

    ImageButton languageChangeIB;

    EditText whatTranslateET;
    ListView listView;
    DialogAdapter adapter;
    ArrayList<String> arrayList;
    MediaPlayer mediaPlayer;
    // 기본 언어 한글
    String language = "English";
    private static final String TAG = "DialogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        adapter =new DialogAdapter();
        arrayList =new ArrayList<String>();
        Field[] fields =R.raw.class.getFields();

        listView  = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        adapter.addItem(R.drawable.ic_mic,"What's the environmental charge?","환경부담금은 얼마입니까?", R.raw.audio1);
        adapter.addItem(R.drawable.ic_mic,"Boss, please make sure to roll up the hot rice soup!","사장님 뜨근한 국밥, 든든하게 말아주세요!", R.raw.audio2);
        adapter.addItem(R.drawable.ic_mic,"I'm having trouble deciding. Could you recommend something for me?","결정하기 힘드네요. 추천할만한 메뉴가 있나요?",R.raw.audio3);
        adapter.addItem(R.drawable.ic_mic,"I'm a vegetarin. What vegetarian dishes do you have?","저는 채식주의자입니다. 채식주의자를 위한 메뉴는 어떤 것들이 있나요?",R.raw.audio4);
        adapter.notifyDataSetChanged();

        init();
        buttonClickListenr();

        whatTranslateET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable editable) {

                new Thread(){
                    @Override
                    public void run() {
                        String word = whatTranslateET.getText().toString();
                        // Papago는 3번에서 만든 자바 코드이다.
                        Papago papago = new Papago();
                        String resultWord;

                        if(language.equals("Korean")){
                            resultWord= papago.getTranslation(word,"ko","en");
                        }else{
                            resultWord= papago.getTranslation(word,"en","ko");
                        }

                        Bundle papagoBundle = new Bundle();
                        papagoBundle.putString("resultWord",resultWord);

                        Log.d(TAG, "resultWord" + resultWord);

                        Message msg = papago_handler.obtainMessage();
                        msg.setData(papagoBundle);
                        papago_handler.sendMessage(msg);

                        Log.d(TAG, "msg" + msg);
                    }
                }.start();

            }
        });
    }

    private void init(){
        toLanguageTV = findViewById(R.id.toLanguageTV);
        fromLanguageTV = findViewById(R.id.fromLanguageTV);
        languageChangeIB = findViewById(R.id.languageChangeIB);
        whatTranslateET = findViewById(R.id.whatTranslateET);
        changedTextTV = findViewById(R.id.changedTextTV);
    }

    @SuppressLint("HandlerLeak")
    Handler papago_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String resultWord = bundle.getString("resultWord");
            changedTextTV.setText(resultWord);
        }
    };

    private void buttonClickListenr(){

        languageChangeIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "languageChangeIB onClick");

                if(language.equals("Korean")){
                    language= "English";

                    toLanguageTV.setText("English");
                    fromLanguageTV.setText("Korean");

                }else{
                    language= "Korean";

                    toLanguageTV.setText("Korean");
                    fromLanguageTV.setText("English");
                }
            }
        });
    }
}