package com.example.logintest14.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "t_note")    //()表的名字
public class EntityNote {
    @PrimaryKey(autoGenerate = true)
    long noteId;
    @ColumnInfo(name = "note_user_id")
    long noteUserId; //note 里用户的id
    @ColumnInfo(name = "note_title")
    String noteTitle;
    @ColumnInfo(name = "note_content")
    String noteContent;  //内容
    @ColumnInfo(name = "note_image_url")
    String noteImageUrl; //图片
    @ColumnInfo(name = "create_time")
    String createTime;

    //不用构造id  他是自增的
    public EntityNote(long noteUserId, String noteTitle, String noteContent, String noteImageUrl, String createTime) {
        this.noteUserId = noteUserId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteImageUrl = noteImageUrl;
        this.createTime = createTime;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public long getNoteUserId() {
        return noteUserId;
    }

    public void setNoteUserId(long noteUserId) {
        this.noteUserId = noteUserId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteImageUrl() {
        return noteImageUrl;
    }

    public void setNoteImageUrl(String noteImageUrl) {
        this.noteImageUrl = noteImageUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "EntityNote{" +
                "noteId=" + noteId +
                ", noteUserId=" + noteUserId +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteContent='" + noteContent + '\'' +
                ", noteImageUrl='" + noteImageUrl + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
