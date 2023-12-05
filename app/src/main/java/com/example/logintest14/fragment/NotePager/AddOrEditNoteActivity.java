package com.example.logintest14.fragment.NotePager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.logintest14.Adapter.NoteAdapter;
import com.example.logintest14.Dao.NoteDao;
import com.example.logintest14.Entity.EntityNote;
import com.example.logintest14.InitDataBase.InitDataBase;
import com.example.logintest14.R;
import com.example.logintest14.Util.UtilMethod;
import com.example.logintest14.databinding.ActivityAddOrEditNoteBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddOrEditNoteActivity extends AppCompatActivity {
    String selectedDate; // 声明selectedDate变量
    EditText etDate;
    //    TextView tvDate;             //日期选择器
//    DatePickerDialog.OnDateSetListener setListener;            //日期选择器
    ActivityAddOrEditNoteBinding binding;
    //获取实体类
    InitDataBase initDataBase;
    NoteDao noteDao;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:mm");
    String createTime;
    boolean isAdd = true;
    EntityNote note;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        selectedDate = ""; // 或者设置一个默认的初始值

        super.onCreate(savedInstanceState);
        binding = ActivityAddOrEditNoteBinding.inflate(getLayoutInflater());                  //binding赋值
        // setContentView(R.layout.activity_add_or_edit_note);     // 原来的
        setContentView(binding.getRoot());
        initMethod();  //初始化

        //tvDate = binding.tvData;                                                                 //时间选择器
        etDate = findViewById(R.id.et_date);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);                                                    //时间选择器

        Intent intent = getIntent();
        if (!(intent != null && intent.getBooleanExtra("isAdd", true))) {
            isAdd = false;
            long noteId = intent.getLongExtra("noteId", -1);
            note = noteDao.getNoteById(noteId);
            binding.noteTitle.setText(note.getNoteTitle().length() > 0 ? note.getNoteTitle() : "");      //渲染
            binding.noteContent.setText(note.getNoteContent());
            if (note.getNoteImageUrl() != null && !note.getNoteImageUrl().isEmpty()){
                imageUri = note.getNoteImageUrl();
                Glide.with(getApplicationContext()).load(note.getNoteImageUrl()).into(binding.noteImage);//图片    into显示的位置
            }
            binding.weather.setText(note.getWeather()); // 设置天气文本内容
            binding.mood.setText(note.getMood()); // 设置心情文本内容

        } else {
            note = new EntityNote(); // 初始化note对象
        }
        binding.closeButton.setOnClickListener(view -> {
            finish();
        });
        //保存  封装成一个方法
        binding.saveButton.setOnClickListener(view -> {
            saveNote();
        });
        binding.noteContent.addTextChangedListener(new TextWatcher() {               //日记总字数更新
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable  editable) {
                binding.wordsCount.setText(getString(R.string.note_length, editable.toString().trim().length()));          //editable是他给的参数
            }
        });
        binding.checkImage.setOnClickListener(view -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });
        binding.wordsCount.setText(getString(R.string.note_length, binding.noteContent.getText().toString().length()));

//        tvDate.setOnClickListener(new View.OnClickListener() {                                                            //时间选择器
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog =new DatePickerDialog(
//                        AddOrEditNoteActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
//                        ,setListener,year,month,day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.show();
//            }
//        });                                                                                                               //text view的时间选择器
//        setListener =new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month =month+1;
//                String date = day+"/"+month+"/"+year;
//                tvDate.setText(date);
//
//            }
//        };

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddOrEditNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        etDate.setText(date);
                        selectedDate = date; // 将所选日期赋值给selectedDate变量
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }


    private void initMethod() {  //初始化
        createTime = simpleDateFormat.format(new Date(System.currentTimeMillis()));//成员变量给控件
        binding.createTime.setText(createTime);  //拿时间
        initDataBase = UtilMethod.getInstance(getApplicationContext());
        noteDao = initDataBase.noteDao();
        binding.wordsCount.setText(getString(R.string.note_length, 0));
        pickMedia =         //选择图片之后的回调函数
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        imageUri = UtilMethod.getPath(getApplicationContext(), uri);                       //调用了UtilMethod.getPath的方法
                        Glide.with(getApplicationContext()).load(uri).into(binding.noteImage);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

    }

    private void saveNote() {
        if (isAdd) {
            if (binding.noteContent.getText().toString().trim().isEmpty() || binding.weather.getText().toString().trim().isEmpty() || binding.mood.getText().toString().trim().isEmpty()) {
                //getText() 内容   toString()整理成字符串trim()去除空字符   isEmpty()判断是否为空
                // 显示错误消息，要求填写所有必填字段
                Toast.makeText(getApplicationContext(), "请填写所有必填字段", Toast.LENGTH_SHORT).show();

                return;
            } else {
//                noteDao.insertNote(new EntityNote(
//                        1, // 假设noteUserId为1
//                        binding.noteTitle.getText().toString().trim(),
//                        binding.noteContent.getText().toString().trim(),
//                        null,
//                        selectedDate, // 设置创建时间为所选日期
//                        binding.weather.getText().toString().trim(),
//                        binding.mood.getText().toString().trim(),
//                        selectedDate // 设置selectTime为所选日期
//                ));
                getCurrentNote();
                noteDao.insertNote(getCurrentNote());
                UtilMethod.showToast(getApplicationContext(), "添加成功！");
                finish();
            }
        } else {        //编辑更新
            if (binding.noteContent.getText().toString().trim().isEmpty() || binding.weather.getText().toString().trim().isEmpty() || binding.mood.getText().toString().trim().isEmpty()) {
                //getText() 内容   toString()整理成字符串trim()去除空字符   isEmpty()判断是否为空
                // 显示错误消息，要求填写所有必填字段
                Toast.makeText(getApplicationContext(), "请填写所有必填字段", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String content = binding.noteContent.getText().toString().trim();
                note.setNoteContent(content);
                note.setNoteTitle(binding.noteTitle.getText().toString().trim().length() > 0 ? binding.noteTitle.getText().toString().trim() : "");
                note.setWeather(binding.weather.getText().toString().trim()); // 更新天气字段的值
                note.setMood(binding.mood.getText().toString().trim()); // 更新心情字段的值
                note.setSelectTime(selectedDate); // 设置选择日期为所选日期
                note.setNoteImageUrl(imageUri == null ? null:imageUri);
                noteDao.updateNote(note);
                noteDao.updateSelectTime(note.getNoteId(), selectedDate); // 更新选择日期
                UtilMethod.showToast(getApplicationContext(), "保存成功!");
                finish();
            }
        }
    }

    private EntityNote getCurrentNote() {
        String content = binding.noteContent.getText().toString().trim();
        String title = binding.noteTitle.getText().toString().trim();
        return new EntityNote(1,
                binding.noteContent.getText().toString().trim(),
                binding.noteTitle.getText().toString().trim(),
                imageUri == null ? null : imageUri,
                createTime,
                binding.weather.getText().toString().trim(),
                binding.mood.getText().toString().trim(),
                selectedDate );

    }
}