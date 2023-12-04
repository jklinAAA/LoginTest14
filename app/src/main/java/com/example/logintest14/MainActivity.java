package com.example.logintest14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.MenuItem;

import com.example.logintest14.Dao.NoteDao;
import com.example.logintest14.Entity.EntityNote;
import com.example.logintest14.InitDataBase.InitDataBase;
import com.example.logintest14.Util.UtilMethod;
import com.example.logintest14.fragment.NotePager.DiaryFragment;
import com.example.logintest14.fragment.MarkFragment;
import com.example.logintest14.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private DiaryFragment mDiaryFragment;
    private MarkFragment mMarkFragment;
    private MineFragment mMineFragment;
    private BottomNavigationView mBottomNavigationView;

    InitDataBase initDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // room
        // initDataBase = Room.databaseBuilder(getApplicationContext(), InitDataBase.class, "zl_database").build(); //zl_database数据库名字
        initDataBase = UtilMethod.getInstance(getApplicationContext());
        NoteDao noteDao = initDataBase.noteDao();
        long l = noteDao.insertNote(new EntityNote(1, "title", "content", null, "2023-12-10","weather","moon","selectTime"));
        System.out.println("insertNoteId" + l);
        System.out.println(noteDao.getAllNote() + "-=-==-=-=-");//+"-=-==-=-=-"方便在log日志找到定位


        //初始化控件
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);

        //设置点击事件
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.diary) {
                    selectedFragment(0);
                } else if (item.getItemId() == R.id.mark) {
                    selectedFragment(1);
                } else {
                    selectedFragment(2);
                }
                return true;
            }
        });
        //默认首页选中
        selectedFragment(0);
    }

    private void selectedFragment(int position) {
        final androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideFragment(fragmentTransaction);
        if (position == 0) {
            if (mDiaryFragment == null) {
                mDiaryFragment = new DiaryFragment();
                fragmentTransaction.add(R.id.content, mDiaryFragment);
            } else {
                fragmentTransaction.show(mDiaryFragment);
            }
        } else if (position == 1) {
            if (mMarkFragment == null) {
                mMarkFragment = new MarkFragment();
                fragmentTransaction.add(R.id.content, mMarkFragment);
            } else {
                fragmentTransaction.show(mMarkFragment);
            }
        } else {
            if (mMineFragment == null) {
                mMineFragment = new MineFragment();
                fragmentTransaction.add(R.id.content, mMineFragment);
            } else {
                fragmentTransaction.show(mMineFragment);
            }
        }
        //提交
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mDiaryFragment != null) {
            fragmentTransaction.hide(mDiaryFragment);
        }
        if (mMarkFragment != null) {
            fragmentTransaction.hide(mMarkFragment);
        }
        if (mMineFragment != null) {
            fragmentTransaction.hide(mMineFragment);
        }
    }
}