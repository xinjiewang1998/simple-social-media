package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;



import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class imageUrl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_url);
        NetworkImageView networkImageView = (NetworkImageView) findViewById(R.id.postImage);
        RequestQueue mQueue;
        mQueue = Volley.newRequestQueue(imageUrl.this);

        LruImageCache lruImageCache = LruImageCache.instance();

        ImageLoader imageLoader = new ImageLoader(mQueue, lruImageCache);
        networkImageView.setDefaultImageResId(R.drawable.test);
        networkImageView.setErrorImageResId(R.drawable.bg);
        networkImageView.setImageUrl("http://ccyy.xyz/api/v0/images/img2", imageLoader);
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


}