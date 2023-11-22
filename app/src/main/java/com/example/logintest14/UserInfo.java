package com.example.logintest14;

public class UserInfo {
    private static UserInfo userInfo;
    private int user_id;
    private String username;
    private String password;
    private String nickname;

    public UserInfo(int user_id, String username, String password, String nickname) {
        this.user_id = user_id;           //构造方法
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}