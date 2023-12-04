package com.example.logintest14.Util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.room.Room;

import com.example.logintest14.InitDataBase.InitDataBase;

public class UtilMethod {
    public  static InitDataBase baseRoomDatabase;
    public  static  InitDataBase getInstance(Context context){
        if (baseRoomDatabase==null){
            baseRoomDatabase = Room.databaseBuilder(context,InitDataBase.class,"zl_database.db")
//                    .addAutoMigrationSpec()
                    .allowMainThreadQueries().build();
        }
        return baseRoomDatabase;
    }
    public  static  void  showToast(Context context ,String info){
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public static String getRealPathFromUri(Context context, Uri uri) {                                   //为了保存图片写的   实现不了就删掉
        if (uri.getScheme().equals("content")) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                cursor.close();
                return path;
            }
        }
        return uri.getPath(); // 如果无法查询到实际路径，则返回 URI 的路径部分
    }
}
