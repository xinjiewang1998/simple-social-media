package com.example.myapplication.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class eachpost extends AppCompatActivity {
    private Integer Position;
    private String LIKE_C;
    private TextView textView_like;
    private int ClickCount;
    private Boolean flag=false;
    private FirebaseUser firebaseUser;
    private String imgUrl;
    private String TEXT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eachpost);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ClickCount=1;
        Intent intent=getIntent();
        LIKE_C=intent.getStringExtra("LIKE_C");
        TEXT=intent.getStringExtra("TEXT");
        Position=intent.getIntExtra("POSITION",0);
        imgUrl=intent.getStringExtra("IMG_URL");
        textView_like=(TextView)findViewById(R.id.LikeCount);
        TextView textView_text=(TextView)findViewById(R.id.posttext);
        textView_like.setText(LIKE_C);
        textView_text.setText(TEXT);
        NetworkImageView networkImageView = (NetworkImageView) findViewById(R.id.eachPostImage);
        RequestQueue mQueue;
        mQueue = Volley.newRequestQueue(eachpost.this);

        LruImageCache lruImageCache = LruImageCache.instance();

        ImageLoader imageLoader = new ImageLoader(mQueue, lruImageCache);
        networkImageView.setImageUrl(imgUrl, imageLoader);
        Button LikeButton=(Button) findViewById(R.id.Like);
        LikeButton.setOnClickListener(LikeListener);
        Button FavoriteButton=(Button) findViewById(R.id.Favorite);
        FavoriteButton.setOnClickListener(FavoriteListener);
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
    private View.OnClickListener FavoriteListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference myRef=firebaseDatabase.getReference("FavoritePost");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dss: snapshot.getChildren()){
                        if(TEXT.equals(dss.child("text").getValue(String.class)) && firebaseUser.getEmail().equals(dss.child("User").getValue(String.class))){
                            flag=true;     // not to add duplicate post for the same user.
                            break;
                        }
                    }
                    if(!flag){
                        HashMap<String,Object> UserPost=new HashMap<>();
                        UserPost.put("User",firebaseUser.getEmail());
                        UserPost.put("img_url",imgUrl);
                        UserPost.put("like_count",Integer.parseInt(LIKE_C));
                        UserPost.put("text",TEXT);
                        myRef.push().setValue(UserPost);
                        Toast.makeText(getApplicationContext(),"Collect into Favorite Post Successfully",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            if(flag){
                Toast.makeText(getApplicationContext(),"Have already collect this post",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private View.OnClickListener LikeListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference myRef=firebaseDatabase.getReference("allpost");
            Integer NewLikeCount=(Integer.parseInt(LIKE_C));
            if(ClickCount%2!=0){
                NewLikeCount++;
                Button button=findViewById(R.id.Like);
                button.setText("Unlike");
            }
            else{
                Button button=findViewById(R.id.Like);
                button.setText("like");
            }
            ClickCount++;
            myRef.child(Position.toString()).child("like_count").setValue(NewLikeCount);
            textView_like.setText(NewLikeCount.toString());
        }
    };
}