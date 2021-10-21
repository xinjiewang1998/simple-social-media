package com.example.myapplication.chat;

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

import com.example.myapplication.R;
import com.example.myapplication.login.User;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.friendHolder> {
    public final Context ctx;
    public final List<User> dataset;
    public FriendAdapter(Context ctx, List<User> dataset){
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
        holder.getName().setText(dataset.get(position).getEmail());
        holder.getImageView().setImageResource(R.drawable.test);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ctx, ChatBox.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                intent.putExtra("user",dataset.get(position).getEmail());
                intent.putExtra("userId",dataset.get(position).getId());

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
