package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;

public class PostAdapter extends RecyclerView.Adapter <PostAdapter.PostHolder> {
    private Context context;
    private ArrayList<HashMap<String,Object>> PostList;
    public PostAdapter(Context context,ArrayList<HashMap<String,Object>> PostList){
        this.context=context;
        this.PostList=PostList;
    }
    @Override
    public PostAdapter.PostHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        return new PostHolder(LayoutInflater.from(context).inflate(R.layout.activity_postlist_holder,parent,false));
    }
    public void onBindViewHolder(@NonNull PostAdapter.PostHolder holder, @SuppressLint("RecyclerView") int position){
          holder.getPostTag().setText(PostList.get(position).get("text").toString().substring(0,2));
          holder.getPosterName().setText(PostList.get(position).get("text").toString().substring(0,2));
          holder.getCommentCount().setText(PostList.get(position).get("comment_count").toString());
          holder.getLikeCount().setText(PostList.get(position).get("like_count").toString());
          holder.getImageView().setImageResource(R.drawable.test);
          //holder.getNetworkImageView().setImageUrl(,imageLoader);
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent();
                  intent.setClass(context,eachpost.class);
                  intent.putExtra("IMG_URL",PostList.get(position).get("img_url").toString());
                  intent.putExtra("COMMENT_C",PostList.get(position).get("comment_count").toString());
                  intent.putExtra("LIKE_C",PostList.get(position).get("like_count").toString());
                  intent.putExtra("TEXT",PostList.get(position).get("text").toString());
                  context.startActivity(intent);
              }
          });
    }

    @Override
    public int getItemCount(){
        return PostList.size();
    }
    public class PostHolder extends RecyclerView.ViewHolder{
          private TextView  postTag,posterName,likeCount,commentCount;
          private ImageView imageView;
          private NetworkImageView networkImageView;

          public TextView getPostTag(){
              return postTag;
          }
         public TextView getPosterName(){
            return posterName;
         }
        public TextView getLikeCount(){
            return likeCount;
        }
        public TextView getCommentCount(){
            return commentCount;
        }
        public ImageView getImageView(){
            return imageView;
        }
        public NetworkImageView getNetworkImageView(){return networkImageView;}


        public  PostHolder(@NonNull View itemView){
              super(itemView);
              postTag=itemView.findViewById(R.id.PostTag);
              posterName=itemView.findViewById(R.id.PosterName);
              likeCount=itemView.findViewById(R.id.LikeCount);
              commentCount=itemView.findViewById(R.id.CommentCount);
              imageView=itemView.findViewById(R.id.PostPhoto);
              networkImageView=itemView.findViewById(R.id.nv_image);
          }
    }

}
