package com.example.logintest14.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logintest14.Entity.EntityNoteCard;
import com.example.logintest14.R;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    ArrayList<EntityNoteCard> list;  //列表
    Context context ; //上下文

    public NoteAdapter(ArrayList<EntityNoteCard> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //创建一个ViewHolder 并返回   //视图绑定
        View view = LayoutInflater.from(context).inflate(R.layout.item_note,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        holder.username.setText(list.get(position).getUsername());   //holder.username 拿到username控件
       // holder.username.setText(list.get(position).getUsername());
        holder.slogan.setText(list.get(position).getSlogan());
        holder.createTime.setText(list.get(position).getCreateTime());
        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());
        //点击事件
        holder.delete.setOnClickListener(v -> System.out.println("delete is running!!!"));
    }
    @Override
    public int getItemCount() {

        return list.size();
    }

    //视图绑定  对应那张图片
    public class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView userAvatar;
        TextView username;
        TextView slogan;
        TextView createTime;
        ImageView cover;
        TextView title;
        TextView content;
        Button  delete;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            userAvatar=itemView.findViewById(R.id.user_avatar);
            username=itemView.findViewById(R.id.username);
            slogan=itemView.findViewById(R.id.slogan);
            createTime=itemView.findViewById(R.id.create_time);
            cover=itemView.findViewById(R.id.cover);
            title=itemView.findViewById(R.id.title);
            content=itemView.findViewById(R.id.content);
            delete=itemView.findViewById(R.id.delete);

        }
    }
}
