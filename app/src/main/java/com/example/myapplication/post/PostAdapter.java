package com.example.myapplication.post;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private final Context context;
    private final List<HashMap<String, Object>> postList;

    public PostAdapter(Context context, List<HashMap<String, Object>> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public PostAdapter.PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostHolder(LayoutInflater.from(context).inflate(R.layout.activity_postlist_holder, parent, false));
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull PostAdapter.PostHolder holder, @SuppressLint("RecyclerView") int position) {

        if (postList != null) {
            String textString = postList.get(getItemCount() - 1 - position).get("text").toString();
            holder.getPostTag().setText(ExtractEngine.extractTag(textString).toString());
            holder.getPosterName().setText(ExtractEngine.extractUserName(textString).toString());
            holder.getLikeCount().setText(postList.get(getItemCount() - 1 - position).get("like_count").toString());
            holder.setImgUrl(postList.get(getItemCount() - 1 - position).get("img_url").toString());
            holder.setNetworkImageView();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, EachPostActivity.class);
                    intent.putExtra("IMG_URL", postList.get(getItemCount() - 1 - position).get("img_url").toString());
                    intent.putExtra("LIKE_C", postList.get(getItemCount() - 1 - position).get("like_count").toString());
                    intent.putExtra("TEXT", postList.get(getItemCount() - 1 - position).get("text").toString());
                    intent.putExtra("POSITION", getItemCount() - 1 - position);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        } else {
            holder.getPostTag().setText("");
            holder.getPosterName().setText("");
            holder.getLikeCount().setText("");
            holder.getNetworkImageView().setErrorImageResId(0);
        }
    }

    public static class LruImageCache implements ImageLoader.ImageCache {

        private static LruCache<String, Bitmap> mMemoryCache;

        private static LruImageCache lruImageCache;

        private LruImageCache() {
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int cacheSize = maxMemory / 8;
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        public static LruImageCache instance() {
            if (lruImageCache == null) {
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
            if (getBitmap(arg0) == null) {
                mMemoryCache.put(arg0, arg1);
            }
        }

    }

    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        private final TextView postTag;
        private final TextView posterName;
        private final TextView likeCount;
        private final NetworkImageView networkImageView;
        private String imgUrl;

        public TextView getPostTag() {
            return postTag;
        }

        public TextView getPosterName() {
            return posterName;
        }

        public TextView getLikeCount() {
            return likeCount;
        }

        public NetworkImageView getNetworkImageView() {
            return networkImageView;
        }

        public void setNetworkImageView() {
            RequestQueue mQueue;
            mQueue = Volley.newRequestQueue(context);

            LruImageCache lruImageCache = LruImageCache.instance();

            ImageLoader imageLoader = new ImageLoader(mQueue, lruImageCache);
            networkImageView.setImageUrl(imgUrl, imageLoader);
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            postTag = itemView.findViewById(R.id.PostTag);
            posterName = itemView.findViewById(R.id.PosterName);
            likeCount = itemView.findViewById(R.id.LikeCount);
            networkImageView = itemView.findViewById(R.id.postImage);
        }
    }

}
