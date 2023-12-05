package com.example.logintest14.Entity;

public class EntityNoteCard {
    long noteCardId;
    String username;
    String slogan;
    String userAvatar;
    String cover;
    String title;
    String content;
    String createTime;
    String weather;
    String mood;
    String selectTime;


    public EntityNoteCard() {
    }



    public EntityNoteCard(long noteCardId, String username, String slogan, String userAvatar, String cover, String title, String content, String createTime,String weather, String mood,String selectTime) {
        this.noteCardId = noteCardId;
        this.username = username;
        this.slogan = slogan;
        this.userAvatar = userAvatar;
        this.cover = cover;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.weather = weather;
        this.mood = mood;
        this.selectTime = selectTime;

    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getNoteCardId() {
        return noteCardId;
    }

    public void setNoteCardId(long noteCardId) {
        this.noteCardId = noteCardId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(String selectTime) {
        this.selectTime = selectTime;
    }

    @Override
    public String toString() {
        return "EntityNoteCard{" +
                "noteCardId=" + noteCardId +
                ", username='" + username + '\'' +
                ", slogan='" + slogan + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", weather='" + weather + '\'' +
                ", mood='" + mood + '\'' +
                ", selectTime='" + selectTime + '\'' +
                '}';
    }
}
