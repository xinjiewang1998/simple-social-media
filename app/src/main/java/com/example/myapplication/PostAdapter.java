package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.util.LruCache;
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
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull PostAdapter.PostHolder holder, @SuppressLint("RecyclerView") int position){

        if(PostList!=null) {
            String textString = PostList.get(getItemCount() - 1 - position).get("text").toString();
            holder.getPostTag().setText(extractEngine.extractTag(textString).toString());
            holder.getPosterName().setText(extractEngine.extractUserName(textString).toString());

            holder.getPosterName().setText(PostList.get(getItemCount() - 1 - position).get("text").toString().substring((PostList.get(getItemCount() - 1 - position).get("text").toString().indexOf('@')), PostList.get(getItemCount() - 1 - position).get("text").toString().indexOf('@') + 10));
            holder.getLikeCount().setText(PostList.get(getItemCount() - 1 - position).get("like_count").toString());
            holder.setImgUrl(PostList.get(getItemCount() - 1 - position).get("img_url").toString());
            holder.setNetworkImageView();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, eachpost.class);
                    intent.putExtra("IMG_URL",PostList.get(getItemCount() - 1 - position).get("img_url").toString());
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
            holder.getNetworkImageView().setErrorImageResId(0);
        }
    }
    public static class LruImageCache implements ImageLoader.ImageCache {

        private static LruCache<String, Bitmap> mMemoryCache;

        private static LruImageCache lruImageCache;

        private LruImageCache(){
            // Get the Max available memory
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int cacheSize = maxMemory / 8;
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
                @Override
                protected int sizeOf(String key, Bitmap bitmap){
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        public static LruImageCache instance(){
            if(lruImageCache == null){
                lruImageCache = new LruImageCache();
            }
            return lruImageCache;
        }

        @Override
        public Bitmap getBitmap(String arg0) {
            return mMemoryCache.get(arg0);
        }

        @Override
        public void putBitmap(String arg0, Bitmap arg1) {
            if(getBitmap(arg0) == null){
                mMemoryCache.put(arg0, arg1);
            }
        }

    }
    @Override
    public int getItemCount(){
        return PostList==null?0:PostList.size();
    }
    public class PostHolder extends RecyclerView.ViewHolder{
          private TextView  postTag,posterName,likeCount;
          private NetworkImageView networkImageView;
          private String imgUrl;
          public TextView getPostTag(){
              return postTag;
          }
         public TextView getPosterName(){
            return posterName;
         }
        public TextView getLikeCount(){
            return likeCount;
        }
        public NetworkImageView getNetworkImageView() {
            return networkImageView;
        }
        public void setNetworkImageView(){
            RequestQueue mQueue;
            mQueue = Volley.newRequestQueue(context);

            LruImageCache lruImageCache = LruImageCache.instance();

            ImageLoader imageLoader = new ImageLoader(mQueue, lruImageCache);
            networkImageView.setImageUrl(imgUrl, imageLoader);
        }
        public void setImgUrl(String imgUrl){
              this.imgUrl=imgUrl;
        }

        public  PostHolder(@NonNull View itemView){
              super(itemView);
              postTag=itemView.findViewById(R.id.PostTag);
              posterName=itemView.findViewById(R.id.PosterName);
              likeCount=itemView.findViewById(R.id.LikeCount);
              networkImageView =itemView.findViewById(R.id.postImage);
          }
    }

}
