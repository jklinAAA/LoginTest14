package com.example.logintest14.Util;

import android.content.Context;
import android.widget.Toast;

import androidx.room.Room;

import com.example.logintest14.InitDataBase.InitDataBase;

public class UtilMethod {
    public  static InitDataBase baseRoomDatabase;
    public  static  InitDataBase getInstance(Context context){
        if (baseRoomDatabase==null){
            baseRoomDatabase = Room.databaseBuilder(context,InitDataBase.class,"zl_database.db")
                    .allowMainThreadQueries().build();
        }
        return baseRoomDatabase;
    }
    public  static  void  showToast(Context context ,String info){
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }
}
