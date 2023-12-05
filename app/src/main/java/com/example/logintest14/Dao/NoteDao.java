package com.example.logintest14.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.logintest14.Entity.EntityNote;
import com.example.logintest14.Entity.EntityNoteCard;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM t_note")
    List<EntityNote>getAllNote();

    @Query("SELECT * FROM t_note where note_user_id=:userid")
    List<EntityNote>getAllNoteByUserId(long userid);

    @Query("SELECT * FROM t_note where note_id=:noteId")
    EntityNote getNoteById(long noteId);
    @Insert
    long  insertNote(EntityNote note); // EntityNote... note 三个点表示可以插入多个 我们现在一次只插入一个

    @Delete
    void deleteNote(EntityNote note); //根据对象来删
    @Query("DELETE  FROM t_note where note_id=:noteId")
    void deleteNoteById(long noteId);   //根据id来删
    @Update
    void updateNote(EntityNote note);
    // 添加的代码开始
    @Query("SELECT * FROM t_note WHERE note_title LIKE '%' || :keyword || '%' OR note_content LIKE '%' || :keyword || '%'")  //搜索
    List<EntityNote> searchNotesByKeyword(String keyword);
    // 添加的代码结束

    //天气和心情
    @Query("UPDATE t_note SET weather = :weather WHERE note_id = :noteId")
    void updateWeather(long noteId, String weather);

    @Query("UPDATE t_note SET mood = :mood WHERE note_id = :noteId")
    void updateMood(long noteId, String mood);

//    @Query("SELECT * FROM t_note WHERE weather = :weather")             //根据天气和心情查找
//    List<EntityNote> searchNotesByWeather(String weather);
//
//    @Query("SELECT * FROM t_note WHERE mood = :mood")
//    List<EntityNote> searchNotesByMood(String mood);

    @Query("SELECT * FROM t_note WHERE weather = :weather OR mood = :mood")
    List<EntityNote> searchNotesByWeatherOrMood(String weather, String mood);

    @Query("SELECT * FROM t_note WHERE weather = :weather AND mood = :mood AND (note_title LIKE '%' || :keyword || '%' OR note_content LIKE '%' || :keyword || '%')")
    List<EntityNote> searchNotes(String keyword, String weather, String mood);

    @Query("UPDATE t_note SET selectTime = :selectTime WHERE note_id = :noteId") //保存选择日期
    void updateSelectTime(long noteId, String selectTime);

    @Query("UPDATE t_note SET note_image_url = :imageUrl WHERE note_id = :noteId")    //保存图片
    void updateNoteImageUrl(long noteId, String imageUrl);


}
