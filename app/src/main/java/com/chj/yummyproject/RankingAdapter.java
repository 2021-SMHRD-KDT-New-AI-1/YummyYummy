package com.chj.yummyproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import java.util.List;

public class RankingAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<RankingVO> data;
    LayoutInflater inflater;

    public RankingAdapter(Context context, int layout, List<RankingVO> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(layout, null);
        }

        TextView rank_num = view.findViewById(R.id.rank_num);
        ImageView rank_img = view.findViewById(R.id.rank_img);
        TextView rank_name = view.findViewById(R.id.rank_name);
        TextView rank_score = view.findViewById(R.id.rank_score);
        ConstraintLayout rank_list = view.findViewById(R.id.rank_list);

        rank_num.setText(String.valueOf(data.get(i).getRank_num()));
        Glide.with(context).load(data.get(i).getRank_img()).into(rank_img);
        rank_name.setText(data.get(i).getRank_name());
        rank_score.setText(String.valueOf(data.get(i).getRank_score()));
        rank_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodInfoActivity.class);
                intent.putExtra("result", data.get(i).getRank_kor_name());
                context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        return view;
    }
}
