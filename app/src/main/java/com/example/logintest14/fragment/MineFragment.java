package com.example.logintest14.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.logintest14.LoginActivity;
import com.example.logintest14.R;
import com.example.logintest14.RegisterActivity;
import com.example.logintest14.UserInfo;
import com.example.logintest14.databinding.FragmentDiary2Binding;
import com.example.logintest14.databinding.FragmentMine2Binding;

import android.provider.MediaStore;    //11-27 -19.13

import java.io.ByteArrayOutputStream;
import java.io.IOException;
// 导入所需的其他包
import android.provider.MediaStore;
public class MineFragment extends Fragment {
    private View rootView;
    private TextView tv_username;
    private TextView tv_nickname;
    private FragmentMine2Binding binding;

    private static final int REQUEST_CODE_SELECT_IMAGE = 1001;    //-11-27-19-.13
    // 用于保存用户数据的键
    private static final String KEY_USERNAME = "username";                      //-11-29-8.51
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_AVATAR_URI = "avatar_uri";                    //-11-29-8.51
    private static final String KEY_USER_ID = "user_id";  // 添加键名                          //如果上面的用不到可以删掉
    private static final String KEY_PASSWORD = "password";  // 添加键名


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
     // rootView = inflater.inflate(R.layout.fragment_mine2, container, false);                                //11-29-9.34     保存用户信息

        binding = FragmentMine2Binding.inflate(inflater, container, false);                        //11-26-23.05
        View rootView = binding.getRoot();

        //初始化控件
        tv_username = binding.tvUsername;
        tv_nickname = binding.tvNickname;
        binding.tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditUsername();
            }

        });                              //11-26

// 选择头像                                                                                                         11-27-19.13
        binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用相册选择图片
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_CODE_SELECT_IMAGE);
            }
        });

        //退出登录
        rootView.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("温馨提示")
                        .setMessage("确定要推出登录吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {       //取消的按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 退出登录的逻辑
                                getActivity().finish();
                                //打开登录页面
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });                       //退出登录
        // 加载用户数据
        loadUserData();
        return rootView;
    }


    private void startEditUsername() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("修改用户名");
        builder.setMessage("请输入新的用户名");
        // 创建一个用于接收用户输入的EditText
        final EditText input = new EditText(getContext());
        // 设置EditText的布局参数
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        // 将EditText添加到对话框中
        builder.setView(input);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newUsername = input.getText().toString();
                tv_username.setText(newUsername);                     //  保存用户信息--11-29-8.54
                saveUserData(); // 保存用户名
                tv_username.setText(newUsername);                                  //直接没了???????????????????????????????????????????????
                dialog.dismiss(); // 关闭对话框
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // 关闭对话框
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //获取照片并渲染
    @Override
    //-11-27-19.13
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == getActivity().RESULT_OK && data != null) {
            // 根据选择的图片URI设置头像
            try {
                // 获取选择的图片URI
                Uri selectedImageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);
                binding.ivAvatar.setImageBitmap(bitmap);
                saveAvatarUri(selectedImageUri); // 保存头像URI
            } catch (IOException e) {
                e.printStackTrace();
            }
        }                                                                                               //-11-27-19.13
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    private void saveUserData() {
        // 获取当前用户名、昵称和头像URI
        String username = tv_username.getText().toString();
        String nickname = tv_nickname.getText().toString();
        Uri avatarUri = getAvatarUri();

        // 创建用户信息实例
        UserInfo userInfo = new UserInfo(1, username, "", nickname);
        userInfo.setAvatarUri(avatarUri);
        // 保存用户数据到持久化存储
        SharedPreferences preferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_USER_ID, userInfo.getUser_id());
        editor.putString(KEY_USERNAME, userInfo.getUsername());
        editor.putString(KEY_PASSWORD, userInfo.getPassword());
        editor.putString(KEY_NICKNAME, userInfo.getNickname());
        editor.putString(KEY_AVATAR_URI, userInfo.getAvatarUri().toString());
        editor.apply();
    }

    private void loadUserData() {
        // 从持久化存储中加载用户数据
        SharedPreferences preferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String username = preferences.getString(KEY_USERNAME, "");
        String nickname = preferences.getString(KEY_NICKNAME, "");
        String avatarUriString = preferences.getString(KEY_AVATAR_URI, "");
        Uri avatarUri = Uri.parse(avatarUriString);

        // 更新UI显示
        tv_username.setText(username);
        tv_nickname.setText(nickname);
        binding.ivAvatar.setImageURI(avatarUri);
    }


    private Uri getAvatarUri() {
        // 获取头像URI
        Uri avatarUri = null;
        Drawable drawable = binding.ivAvatar.getDrawable();
        BitmapDrawable bitmapDrawable = null;
        if (drawable instanceof BitmapDrawable) {
            bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null) { // 添加空引用检查
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] bytes = outputStream.toByteArray();
                String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Avatar", null);
                avatarUri = Uri.parse(path);
            }
        }
        // 保存头像到持久化存储
        if (bitmapDrawable != null) { // 添加空引用检查
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null) { // 添加空引用检查
                String path = MediaStore.Images.Media.insertImage(
                        getContext().getContentResolver(), bitmap, "Avatar", null
                );
                avatarUri = Uri.parse(path);
            }
        }
        return avatarUri;
    }

    private void saveAvatarUri(Uri avatarUri) {
        // 保存头像URI到持久化存储
        // 这里使用SharedPreferences作为示例，您可以根据实际需求选择其他存储方式
        SharedPreferences preferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_AVATAR_URI, avatarUri.toString());
        editor.apply();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 加载用户数据
        loadUserData();
    }
}