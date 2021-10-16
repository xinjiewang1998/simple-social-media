package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter <PostAdapter.PostHolder> {
    private Context context;
    private List<HashMap<String,Object>> PostList;
    public PostAdapter(Context context, List<HashMap<String,Object>> PostList){
        this.context=context;
        this.PostList=PostList;
    }
    @Override
    public PostAdapter.PostHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        return new PostHolder(LayoutInflater.from(context).inflate(R.layout.activity_postlist_holder,parent,false));
    }
    public void onBindViewHolder(@NonNull PostAdapter.PostHolder holder, @SuppressLint("RecyclerView") int position){

        if(PostList!=null) {
            holder.getPostTag().setText(PostList.get(getItemCount() - 1 - position).get("text").toString().substring(Math.abs(PostList.get(getItemCount() - 1 - position).get("text").toString().indexOf('#')), Math.abs(PostList.get(getItemCount() - 1 - position).get("text").toString().indexOf('#')) + 4));
            holder.getPosterName().setText(PostList.get(getItemCount() - 1 - position).get("text").toString().substring((PostList.get(getItemCount() - 1 - position).get("text").toString().indexOf('@')), PostList.get(getItemCount() - 1 - position).get("text").toString().indexOf('@') + 4));
            holder.getLikeCount().setText(PostList.get(getItemCount() - 1 - position).get("like_count").toString());
            holder.getImageView().setImageResource(R.drawable.test);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, eachpost.class);
                    intent.putExtra("LIKE_C", PostList.get(getItemCount() - 1 - position).get("like_count").toString());
                    intent.putExtra("TEXT", PostList.get(getItemCount() - 1 - position).get("text").toString());
                    intent.putExtra("POSITION",getItemCount() - 1 - position);
                    context.startActivity(intent);
                }
            });
        }
        else if(PostList==null){
            holder.getPostTag().setText("");
            holder.getPosterName().setText("");
            holder.getLikeCount().setText("");
            holder.getImageView().setImageResource(0);
        }
    }

    @Override
    public int getItemCount(){
        return PostList==null?0:PostList.size();
    }
    public class PostHolder extends RecyclerView.ViewHolder{
          private TextView  postTag,posterName,likeCount;
          private ImageView imageView;

          public TextView getPostTag(){
              return postTag;
          }
         public TextView getPosterName(){
            return posterName;
         }
        public TextView getLikeCount(){
            return likeCount;
        }
        public ImageView getImageView(){
            return imageView;
        }


        public  PostHolder(@NonNull View itemView){
              super(itemView);
              postTag=itemView.findViewById(R.id.PostTag);
              posterName=itemView.findViewById(R.id.PosterName);
              likeCount=itemView.findViewById(R.id.LikeCount);
              imageView=itemView.findViewById(R.id.PostPhoto);
          }
    }

}
