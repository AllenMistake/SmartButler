package com.example.smartbutler.ui;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.utils
 * 文件名:  UtilsTools
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/11 20:39
 * 描述:    注册
 */


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisteredActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_user;
    private EditText et_age;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private EditText et_pass;
    private EditText et_password;
    private EditText et_eamil;
    private Button btnRegistered;
    //性别 默认为男
    private boolean isGender = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        initView();
    }

    private void initView() {
        et_user = findViewById(R.id.et_user);
        et_age = findViewById(R.id.et_age);
        et_desc = findViewById(R.id.et_desc);
        mRadioGroup = findViewById(R.id.mRadioGroup);
        et_pass = findViewById(R.id.et_pass);
        et_password = findViewById(R.id.et_password);
        et_eamil = findViewById(R.id.et_email);
        btnRegistered = findViewById(R.id.btnRegistered);
        btnRegistered.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegistered:
                // 获取到输入框的值
                String name = et_user.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String email = et_eamil.getText().toString().trim();

                // 判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age)
                        & !TextUtils.isEmpty(pass)
                        & !TextUtils.isEmpty(password)
                        & !TextUtils.isEmpty(email)) {

                    // 判断两次密码是否一致
                    if (pass.equals(password)) {
                        // 判断性别
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                if (i == R.id.rb_boy) {
                                    isGender = true;
                                } else if (i == R.id.rb_girl) {
                                    isGender = false;
                                }
                            }
                        });

                        // 判断简介是否为空
                        if (TextUtils.isEmpty(desc)) {
                            desc = "这个人很懒，什么都没有留下";
                        }

                        // 注册
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setAge(Integer.parseInt(age));
                        user.setDesc(desc);
                        user.setEmail(email);
                        user.setSex(isGender);
                        user.setPassword(pass);

                        // Bmob后端注册
                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(RegisteredActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegisteredActivity.this, "注册失败： " + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(this, "两次输密码不一致", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(this, R.string.text_tost_empty, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
