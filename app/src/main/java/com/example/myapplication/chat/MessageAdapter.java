package com.example.myapplication.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    public final Context ctx;
    public final List<Chat> dataset;
    FirebaseUser fUser;

    public MessageAdapter(Context ctx, List<Chat> dataset){
        this.ctx = ctx;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==1){
            // load the right side layout
            View view = LayoutInflater.from(ctx).inflate(R.layout.chat_item_right,parent,false);
            return new MessageHolder(view);
        }else {
            // load the left side layout
            View view = LayoutInflater.from(ctx).inflate(R.layout.chat_item_left,parent,false);
            return new MessageHolder(view);
        }



    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, @SuppressLint("RecyclerView") int position) {
            Chat chat = dataset.get(position);
            holder.getMsg_show().setText(chat.getMsg());
            holder.getProfile_image().setImageResource(R.drawable.test);
    }
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder{
        private final ImageView profile_image;
        private final TextView msg_show;
        public ImageView getProfile_image() {
            return profile_image;
        }

        public TextView getMsg_show() {
            return msg_show;
        }


        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = (ImageView) itemView.findViewById(R.id.profile_image);
            msg_show = (TextView) itemView.findViewById(R.id.msg_show);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // current user
        fUser  =FirebaseAuth.getInstance().getCurrentUser();
        // determine the message show on the left side or right side.
        if(dataset.get(position).getSender().equals(fUser.getUid())){
            return 1;
        }
        else{
            return 0;
        }
    }
}
