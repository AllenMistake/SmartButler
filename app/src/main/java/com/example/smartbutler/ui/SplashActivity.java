package com.example.smartbutler.ui;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.ui
 * 文件名:  SplashActivity
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/12 16:29
 * 描述:    闪屏
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartbutler.MainActivity;
import com.example.smartbutler.R;
import com.example.smartbutler.utils.ShareUtils;
import com.example.smartbutler.utils.StaticClass;
import com.example.smartbutler.utils.UtilsTools;

import java.lang.reflect.Type;

public class SplashActivity extends AppCompatActivity {

    /**
     * 1. 延时2000ms
     * 2. 判断程序是否是第一次运行
     * 3. 自定义字体
     * 4. Activity全屏主题
     */

    private TextView tv_splash;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                // 判断程序是否是第一次运行
                case StaticClass.HANDLER_SPLASH:
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        // 延时2000ms
        mHandler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        tv_splash = findViewById(R.id.tv_splash);
        UtilsTools.setFont(this, tv_splash);

    }

    // 判断程序是否第一次运行
    public boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if(isFirst){
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        }else{
            return false;
        }
    }


}
