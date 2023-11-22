package com.example.logintest14.InitDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.logintest14.Dao.NoteDao;
import com.example.logintest14.Entity.EntityNote;

@Database(entities = {EntityNote.class},version = 1, exportSchema = false)  //每次迭代 比如user+1 都要改version
public abstract class InitDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
