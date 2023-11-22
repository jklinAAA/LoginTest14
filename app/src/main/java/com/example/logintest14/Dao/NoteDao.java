package com.example.logintest14.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.logintest14.Entity.EntityNote;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM t_note")
    List<EntityNote>getAllNote();
    @Query("SELECT * FROM t_note where note_user_id=:userid")
    List<EntityNote>getAllNoteByUserId(long userid);

    @Insert
    long  insertNote(EntityNote note); // EntityNote... note 三个点表示可以插入多个 我们现在一次只插入一个

    @Delete
    void deleteNote(EntityNote note);
    @Update
    void updateNote(EntityNote note);
}
