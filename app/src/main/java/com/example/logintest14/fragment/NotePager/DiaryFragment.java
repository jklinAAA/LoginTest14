package com.example.logintest14.fragment.NotePager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.Editable;            //新加的    查询
import android.text.TextWatcher;       //新加的    查询
import android.widget.SearchView;         //新加的    查询


import com.example.logintest14.Adapter.NoteAdapter;
import com.example.logintest14.Dao.NoteDao;
import com.example.logintest14.Entity.EntityNote;
import com.example.logintest14.Entity.EntityNoteCard;
import com.example.logintest14.InitDataBase.InitDataBase;
import com.example.logintest14.R;
import com.example.logintest14.Util.UtilMethod;
import com.example.logintest14.databinding.FragmentDiary2Binding;

import java.util.ArrayList;
import java.util.List;


public class DiaryFragment extends Fragment {

    FragmentDiary2Binding binding;
    InitDataBase initDataBase;   //需要数据库的引用
    NoteDao noteDao;
    private String searchKeyword = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiary2Binding.inflate(getLayoutInflater());
        initMethod(); // 可以用接口回调？

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 更新搜索关键字
                searchKeyword = newText.trim();
                // 重新加载日记列表
                initList();
                return true;
            }
        });                 //查找  11-28-21.45
        initList();
        //跳转表达式  加文本
        binding.floatingActionButton.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AddOrEditNoteActivity.class));     //上下文 +要去的地方
        });
        return binding.getRoot();


    }

    private void initList() {
        List<EntityNote> localNote = getLocalNote();//查询
        List<EntityNote> filteredNote;
        if (!searchKeyword.isEmpty()) {
            // 使用搜索关键字进行模糊搜索
            filteredNote = noteDao.searchNotesByKeyword(searchKeyword);
        } else {
            filteredNote = localNote;  // 如果搜索关键字为空，则显示所有的笔记
        }




        if (filteredNote != null && filteredNote.size() > 0) {             //需要的是EntityNoteCard实体类 查询是EntityNote实体类  需要把后者转到前者
            ArrayList<EntityNoteCard> list = noteToCard(filteredNote);
            binding.noNote.setVisibility(View.GONE);
            binding.noteAlert.setVisibility(View.VISIBLE);                                                                                    //12-5-16.45
            NoteAdapter noteAdapter = new NoteAdapter(list, getContext(), count -> {//内部匿名接口  实现更新日记的数量
                if (count == 0) {
                    binding.noNote.setVisibility(View.VISIBLE);
                }
                binding.noteAlert.setText(getString(R.string.note_alter, count + ""));
            });
            binding.noteList.setAdapter(noteAdapter);         //渲染
            binding.noteList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            binding.noteAlert.setText(getString(R.string.note_alter, list.size() + ""));
        } else {
            binding.noNote.setVisibility(View.VISIBLE);
            binding.noteAlert.setVisibility(View.GONE);                                                                                      //12-5-16.45
        }
    }


    private ArrayList<EntityNoteCard> noteToCard(List<EntityNote> list) {                          //如果没有渲染成功考虑以下是不是这里少东西了
        ArrayList<EntityNoteCard> cards = new ArrayList<>();
        for (EntityNote note : list) {                                               //到EntityNoteCard给一个无参构造            public EntityNoteCard() {}
            EntityNoteCard entityNoteCard = new EntityNoteCard();
            entityNoteCard.setNoteCardId(note.getNoteId());
            entityNoteCard.setContent(note.getNoteContent());
            entityNoteCard.setTitle(note.getNoteTitle());
            entityNoteCard.setCreateTime(note.getCreateTime());
            entityNoteCard.setCover(note.getNoteImageUrl());
            entityNoteCard.setUsername(getString(R.string.item_username));            //先写死 后面再改成动态
            entityNoteCard.setSlogan(getString(R.string.item_slogan));
            entityNoteCard.setWeather(note.getWeather());
            entityNoteCard.setMood(note.getMood());
            entityNoteCard.setSelectTime(note.getSelectTime());
            cards.add(entityNoteCard);
        }
        return cards;
    }

    private void initMethod() {
        initDataBase = UtilMethod.getInstance(getContext());
        noteDao = initDataBase.noteDao();
    }

    private List<EntityNote> getLocalNote() {
        List<EntityNote> allNote = noteDao.getAllNote();
        if (allNote.size() > 0) {
            return allNote;
        } else {
            return null;
        }

    }

    @Override
    public void onResume() {
        initList();
        super.onResume();
    }
}