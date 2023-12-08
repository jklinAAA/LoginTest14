package com.example.logintest14.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logintest14.Dao.NoteDao;
import com.example.logintest14.Entity.EntityNoteCard;
import com.example.logintest14.InitDataBase.InitDataBase;
import com.example.logintest14.R;
import com.example.logintest14.Util.UtilMethod;
import com.example.logintest14.fragment.NotePager.AddOrEditNoteActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;               //新增  查询


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    ArrayList<EntityNoteCard> list;                                                                                  //可删
    private ArrayList<EntityNoteCard> originalList;  // 初始列表
    private ArrayList<EntityNoteCard> filteredList;  // 过滤后的列表
    private Context context; //上下文
    private int deletePosition;   //成员变量，删除的位置
    //删除数据库里的内容 需要
    private InitDataBase initDataBase;
    private NoteDao noteDao;
    //更新日记的数量
    private CountListen countListen;
    private String query = ""; // 查询条件
    private NoteItemTouchHelper itemTouchHelper;        //左滑删除
    private RecyclerView recyclerView;
    private GridView gridView;
    private boolean isGrid = false;


    public NoteAdapter(ArrayList<EntityNoteCard> list, Context context, CountListen countListen,RecyclerView recyclerView, GridView gridView){
        this.originalList = list;
        this.filteredList = new ArrayList<>(list);
        this.context = context;
        this.countListen = countListen;
        initDataBase = UtilMethod.getInstance(context); //初始化
        noteDao = initDataBase.noteDao();
        this.recyclerView = recyclerView;
        this.gridView = gridView;

        this.recyclerView = recyclerView; // 添加这一行，将传入的RecyclerView赋值给成员变量                                          左滑删除
        itemTouchHelper = new NoteItemTouchHelper(this); // 添加这一行，创建NoteItemTouchHelper实例
        itemTouchHelper.attachToRecyclerView(recyclerView); // 添加这一行，将NoteItemTouchHelper与RecyclerView关联
    }


    public void switchLayout(boolean isGrid) {
        this.isGrid = isGrid;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //创建一个ViewHolder 并返回   //视图绑定
        View view;
        if (isGrid) {
            view = LayoutInflater.from(context).inflate(R.layout.item_note_grid, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {

        if (position == holder.getLayoutPosition()) {

            EntityNoteCard noteCard = filteredList.get(position);
            holder.username.setText(originalList.get(position).getUsername());   //holder.username 拿到username控件
            // holder.username.setText(list.get(position).getUsername());
            holder.slogan.setText(originalList.get(position).getSlogan());
            holder.createTime.setText(originalList.get(position).getCreateTime());
            holder.title.setText(originalList.get(position).getTitle());
            holder.content.setText(originalList.get(position).getContent());
            holder.weatherTextView.setText(originalList.get(position).getWeather());                             //  holder.weatherTextView.setText(noteCard.getWeather());
            holder.moodTextView.setText(originalList.get(position).getMood());                                   // holder.moodTextView.setText(noteCard.getMood());
            if (originalList.get(position).getCover() == null) {
                holder.cover.setVisibility(View.GONE);
            } else {
                holder.cover.setVisibility(View.VISIBLE);
                Glide.with(context).load(originalList.get(position).getCover()).into(holder.cover);
            }
            Glide.with(context).load(R.drawable.czl).into(holder.userAvatar);
            //点击事件  删除
            holder.delete.setOnClickListener(view -> {
                new MaterialAlertDialogBuilder(context).setTitle("你想要删除这篇日记吗？").setPositiveButton("yes", (dialog, which) -> {
                    deletePosition = holder.getLayoutPosition();    //传值
                    deleteNote();
                }).setNegativeButton("no", null).show();
            });
            //修改的点击事件
            holder.itemCard.setOnClickListener(view -> {
                Intent intent = new Intent(context, AddOrEditNoteActivity.class);
                intent.putExtra("isAdd", false);
                intent.putExtra("noteId", noteCard.getNoteCardId()); //拿到一个CardId
                context.startActivity(intent);    //AddOrEditNoteActivity 默认是添加 不能直接到
            });
        }


        // 长按删除
        holder.itemCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("确认删除这篇日记吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletePosition = holder.getLayoutPosition();
                                deleteNote();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return true;
            }
        });

    }

    //左滑删除
    public void onItemSwiped(int position) {
        deletePosition = position;
        deleteNote();
    }


    private void deleteNote() {
        long noteId = filteredList.get(deletePosition).getNoteCardId();
        noteDao.deleteNoteById(noteId);

        originalList.removeIf(noteCard -> noteCard.getNoteCardId() == noteId);
        filteredList.remove(deletePosition);

        notifyItemRemoved(deletePosition);
        notifyItemRangeChanged(deletePosition, filteredList.size());

        countListen.countListen(originalList.size());

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    // 新增方法：设置过滤后的列表
    public void setFilteredList(ArrayList<EntityNoteCard> filteredList) {
        this.filteredList = new ArrayList<>(filteredList);
        notifyDataSetChanged();
    }

    // 新增方法：设置查询条件
    public void setQuery(String query) {
        this.query = query;
        filterNotes(); // 设置查询条件后立即进行列表过滤
    }

    // 新增方法：根据查询条件过滤日记列表
    private void filterNotes() {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            for (EntityNoteCard noteCard : originalList) {
                // 根据您的查询条件进行筛选，这里使用标题进行模糊匹配
                if (noteCard.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(noteCard);
                }
            }
        }

        notifyDataSetChanged();

    }

    //视图绑定  对应那张图片
    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView itemCard;
        ImageView userAvatar;
        TextView username;
        TextView slogan;
        TextView createTime;
        ImageView cover;
        TextView title;
        TextView content;
        Button delete;
        TextView weatherTextView;
        TextView moodTextView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            itemCard = itemView.findViewById(R.id.item_card);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            username = itemView.findViewById(R.id.username);
            slogan = itemView.findViewById(R.id.slogan);
            createTime = itemView.findViewById(R.id.create_time);
            cover = itemView.findViewById(R.id.cover);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            delete = itemView.findViewById(R.id.delete);
            weatherTextView = itemView.findViewById(R.id.weatherTextView);
            moodTextView = itemView.findViewById(R.id.moodTextView);


        }
    }


    public interface CountListen {
        void countListen(int count);
    }
}