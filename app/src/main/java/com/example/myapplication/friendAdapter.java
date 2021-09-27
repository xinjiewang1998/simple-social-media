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

import java.util.List;

public class friendAdapter extends RecyclerView.Adapter<friendAdapter.friendHolder> {
    public final Context ctx;
    public final List<User> dataset;
    public friendAdapter(Context ctx, List<User> dataset){
        this.ctx = ctx;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public friendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_holder,parent,false);

        return new friendHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull friendHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getName().setText(dataset.get(position).name);
        if(position ==0){
        holder.getImageView().setImageResource(R.drawable.ic_launcher_background);}
        else{
            holder.getImageView().setImageResource(R.drawable.ic_launcher_background);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ctx, chatBox.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                intent.putExtra("user",dataset.get(position).name);

//                Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
//                intent.putExtra("pic", ((BitmapDrawable)holder.getImageView().getDrawable()).getBitmap());
                ctx.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class friendHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView name;
        public ImageView getImageView() {
            return imageView;
        }

        public TextView getName() {
            return name;
        }


        public friendHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            name = (TextView) itemView.findViewById(R.id.user_name);
        }
    }

}
