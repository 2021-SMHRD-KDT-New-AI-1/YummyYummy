package com.chj.yummyproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DialogAdapter extends BaseAdapter {
    private ArrayList<Listitem> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Listitem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dialog_list, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView iv_voice = (ImageView) convertView.findViewById(R.id.img_voice) ;
        TextView tv_eng = (TextView) convertView.findViewById(R.id.eng) ;
        TextView tv_kor = (TextView) convertView.findViewById(R.id.kor) ;



        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        Listitem myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        iv_voice.setImageResource(myItem.getIcon());
        tv_eng.setText(myItem.getEng());
        tv_kor.setText(myItem.getKor());

        iv_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = MediaPlayer.create(context, myItem.getVoice_file());
                mediaPlayer.start();
            }
        });
        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(int img, String eng,String kor, int voice_file) {

        Listitem mItem = new Listitem();

        /* MyItem에 아이템을 setting한다. */
        mItem.setIcon(img);
        mItem.setEng(eng);
        mItem.setKor(kor);
        mItem.setVoice_file(voice_file);


        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }
}
