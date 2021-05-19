package com.ahmedkenawy.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedkenawy.myapplication.R;
import com.ahmedkenawy.myapplication.chats.MessageActivity;
import com.ahmedkenawy.myapplication.model.Chat;
import com.ahmedkenawy.myapplication.model.User;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private ArrayList<User> users;
    private boolean ischat;
    private String lastMessage;
    public UserAdapter(Context context, ArrayList<User> users,boolean ischat) {
        this.context = context;
        this.users = users;
        this.ischat=ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.usertv.setText(user.getName());

        if (user.getImageProfile().equals("default")) {
            holder.useriv.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(user.getImageProfile()).into(holder.useriv);
        }
        if (ischat){
            setLastMessage(user.getUserid(),holder.userLastMessage);
        }else{
            holder.userLastMessage.setVisibility(View.GONE);
        }

        if (ischat){
            if (user.getStatus().equals("online")){
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.INVISIBLE);
            }else{
                holder.img_on.setVisibility(View.INVISIBLE);
                holder.img_off.setVisibility(View.VISIBLE);
            }

        } else {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MessageActivity.class);
                intent.putExtra("userid",user.getUserid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView useriv;
        TextView usertv;
        ImageView img_on;
        ImageView img_off;
        TextView userLastMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            useriv = itemView.findViewById(R.id.userImageView);
            usertv = itemView.findViewById(R.id.userTextView);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            userLastMessage=itemView.findViewById(R.id.userlastmessage);

        }
    }

    public void setLastMessage(String userid,TextView tv){
        lastMessage="default";
        FirebaseUser fUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Chat chat=dataSnapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(fUser.getUid())&&chat.getSender().equals(userid)||
                            chat.getReceiver().equals(userid)&&chat.getSender().equals(fUser.getUid())){
                        lastMessage=chat.getMessage();
                    }

                }
                switch (lastMessage){
                    case "default":
                        tv.setText("No Message");
                        break;
                    default:
                        tv.setText(lastMessage);
                        break;
                }
                lastMessage="default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}
