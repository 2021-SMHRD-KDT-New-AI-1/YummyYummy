package com.chj.yummyproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<CommentVO> data;
    LayoutInflater inflater;

    public CommentAdapter(Context context, int layout, List<CommentVO> data) {
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
            view = inflater.inflate(layout,null);
        }

        TextView tv_memberId = view.findViewById(R.id.tv_memerId);
        TextView tv_date = view.findViewById(R.id.tv_date);
        TextView tv_commentCon = view.findViewById(R.id.tv_commentCon);

        tv_memberId.setText(data.get(i).getMember_id());
        tv_date.setText(data.get(i).getComment_date().substring(0,10));
        tv_commentCon.setText(data.get(i).getComment_cont());

        return view;
    }
}
