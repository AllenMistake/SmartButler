package com.example.smartbutler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_forget_password;
    private EditText et_email;
    private EditText et_now;
    private EditText et_new;
    private EditText et_new_password;
    private Button brn_update_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();
    }

    // 初始化View
    private void initView(){
        btn_forget_password = findViewById(R.id.btn_forget_password);
        et_email = findViewById(R.id.et_email);
        btn_forget_password.setOnClickListener(this);
        et_email.setOnClickListener(this);
        et_now = findViewById(R.id.et_now);
        et_new = findViewById(R.id.et_new);
        et_new_password = findViewById(R.id.et_new_password);
        btn_forget_password = findViewById(R.id.btn_update_password);
        btn_forget_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_forget_password:
                // 1. 获取输入框的邮箱
                final String email = et_email.getText().toString().trim();
                // 2. 判断是否为空
                if(!TextUtils.isEmpty(email)){
                    // 3. 发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Toast.makeText(ForgetPasswordActivity.this, "邮箱已经发送至: "+ email, Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(ForgetPasswordActivity.this, "邮箱发送失败+ "+ e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.et_email:
                break;
            case R.id.btn_update_password:
                // 1. 获取输入框的值
                String now = et_now.getText().toString().trim();
                String news = et_new.getText().toString().trim();
                String new_password = et_new_password.getText().toString().trim();
                if(!TextUtils.isEmpty(now) && !TextUtils.isEmpty(news) && !TextUtils.isEmpty(new_password)){

                    //3. 判断两次密码是否一致
                    if(news.equals(new_password)){
                        // 重置密码
                        MyUser.updateCurrentUserPassword(now, news, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码失败: " + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(ForgetPasswordActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ForgetPasswordActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
