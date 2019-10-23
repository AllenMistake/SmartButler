package com.example.smartbutler.ui;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.ui
 * 文件名:  LoginActivity
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/13 14:52
 * 描述:    登录
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartbutler.MainActivity;
import com.example.smartbutler.R;
import com.example.smartbutler.entity.MyUser;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.ShareUtils;
import com.example.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    // 注册按钮
    private Button btn_registered;
    private EditText et_name;
    private EditText et_password;
    private Button btn_login;
    private CheckBox keep_password;
    private TextView tv_forget;
    private CustomDialog dialog;
    private CircleImageView profile_image;
    private  String mFile;

//    Bitmap downloadUserAvater = BitmapFactory.decodeFile(mFile);
//    profile_image.setImageBitmap(downloadUserAvater);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        btn_registered = findViewById(R.id.btn_register);
        btn_registered.setOnClickListener(this);

        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_login_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        keep_password = findViewById(R.id.keep_password);

        tv_forget = findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        //初始化dialog
        dialog = new CustomDialog(this, 0, 0, R.layout.dialog_loading, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        // 屏幕外点击无效
        dialog.setCancelable(false);

        // 设置选中的状态
        boolean isChecked = ShareUtils.getBoolean(this, "keeppass", false);
        keep_password.setChecked(isChecked);
        if(isChecked){
            // 设置密码
            et_name.setText(ShareUtils.getString(this, "name", ""));
            et_password.setText(ShareUtils.getString(this, "password", ""));
        }

        String name = et_name.getText().toString().trim();
        L.d(name);

//        if (mFile == null) {
//            mFile = Environment.getExternalStorageDirectory() + "/" +"wode/"+ userImageName + "_avater.png";
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
            case R.id.btn_register:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.btn_login:
                // 获取输入框的值
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                // 判断是否为空
                if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)){

                    dialog.show();
                    // 登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if( e == null){
                                // 判断邮箱是否验证
                                if(user.getEmailVerified()){
                                    // 跳转
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "登陆失败: " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this, R.string.text_tost_empty, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //假设我现在输入用户名和密码，但我不点击登录，而是直接退出
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 保存状态
        ShareUtils.putBoolean(this, "keeppass", keep_password.isChecked());

        // 是否记住密码
        if(keep_password.isChecked()){
            // 记住用户名和密码
            ShareUtils.putString(this, "name", et_name.getText().toString().trim());
            ShareUtils.putString(this, "password", et_password.getText().toString().trim());
        }else{
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "password");
        }
    }
}
