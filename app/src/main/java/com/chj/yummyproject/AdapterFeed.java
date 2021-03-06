package com.chj.yummyproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.MyViewHolder> {
    Context context;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    RequestManager glide;
    URLInfo urlInfo;
    RequestQueue requestQueue;

    public AdapterFeed(Context context, ArrayList<ModelFeed> modelFeedArrayList) {
        this.context = context;
        this.modelFeedArrayList = modelFeedArrayList;
        glide = Glide.with(context);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_feed, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ModelFeed modelFeed = modelFeedArrayList.get(position);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        holder.tv_name.setText(modelFeed.getName());
        holder.tv_time.setText(modelFeed.getTime().substring(0,10));
        holder.tv_likes.setText(String.valueOf(modelFeed.getLikes()));
        holder.tv_comments.setText(modelFeed.getComments() + " comments");
        holder.tv_status.setText(modelFeed.getStatus());
        Log.d("time",modelFeed.getTime());

        holder.rl_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlInfo.getUrl();
                url += "Like";
                url += "?post_num=" + modelFeed.getId();

                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("result",response);
                                Intent intent = new Intent(context, CommunityActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(intent);
                                ((Activity)context).finish();
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

        holder.rl_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("post_num",modelFeed.getId());
                intent.putExtra("member_id", modelFeed.getName());
                context.startActivity(intent);
            }
        });

        glide.load(modelFeed.getPropic()).into(holder.imgView_proPic);

        if (modelFeed.getPostpic() == 0) {
            holder.imgView_postPic.setVisibility(View.GONE);
        } else {
            holder.imgView_postPic.setVisibility(View.VISIBLE);
            glide.load(modelFeed.getPostpic()).into(holder.imgView_postPic);
        }
    }

    @Override
    public int getItemCount() {
        return modelFeedArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_time, tv_likes, tv_comments, tv_status;
        ImageView imgView_proPic, imgView_postPic;
        RelativeLayout rl_like, rl_comment, rl_share;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgView_proPic = (ImageView) itemView.findViewById(R.id.imgView_proPic);
            imgView_postPic = (ImageView) itemView.findViewById(R.id.imgView_postPic);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_likes = itemView.findViewById(R.id.tv_likes);
            tv_comments = itemView.findViewById(R.id.tv_comments);
            tv_status = itemView.findViewById(R.id.tv_status);

            rl_like = itemView.findViewById(R.id.rl_like);
            rl_comment = itemView.findViewById(R.id.rl_comment);
            rl_share = itemView.findViewById(R.id.rl_share);
        }
    }
}