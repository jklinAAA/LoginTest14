package com.example.logintest14.fragment.NotePager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logintest14.Adapter.NoteAdapter;
import com.example.logintest14.Entity.EntityNoteCard;
import com.example.logintest14.R;
import com.example.logintest14.databinding.FragmentDiary2Binding;

import java.util.ArrayList;


public class DiaryFragment extends Fragment {

    FragmentDiary2Binding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentDiary2Binding.inflate(getLayoutInflater());
        ArrayList<EntityNoteCard> list=new ArrayList<>();
        list.add(new EntityNoteCard(1,"czl","My SloganMy SloganMy Slogan",null,null,"The title",getString(R.string.item_content),getString(R.string.item_time)));
        list.add(new EntityNoteCard(1,"czl","My SloganMy SloganMy Slogan",null,null,"The title",getString(R.string.item_content),getString(R.string.item_time)));
        list.add(new EntityNoteCard(1,"czl","My SloganMy SloganMy Slogan",null,null,"The title",getString(R.string.item_content),getString(R.string.item_time)));
        list.add(new EntityNoteCard(1,"czl","My SloganMy SloganMy Slogan",null,null,"The title",getString(R.string.item_content),getString(R.string.item_time)));
        list.add(new EntityNoteCard(1,"czl","My SloganMy SloganMy Slogan",null,null,"The title",getString(R.string.item_content),getString(R.string.item_time)));
        list.add(new EntityNoteCard(1,"czl","My SloganMy SloganMy Slogan",null,null,"The title",getString(R.string.item_content),getString(R.string.item_time)));
        list.add(new EntityNoteCard(1,"czl","My SloganMy SloganMy Slogan",null,null,"The title",getString(R.string.item_content),getString(R.string.item_time)));
        list.add(new EntityNoteCard(1,"czl","My SloganMy SloganMy Slogan",null,null,"The title",getString(R.string.item_content),getString(R.string.item_time)));
         NoteAdapter noteAdapter =new NoteAdapter(list,getContext());
         binding.noteList.setAdapter(noteAdapter);
         binding.noteList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
         return binding.getRoot();

    }
}