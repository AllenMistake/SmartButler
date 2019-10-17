package com.example.smartbutler.application;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.application
 * 文件名:  BaseApplication
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/11 20:24
 * 描述:    TODO
 */

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.example.smartbutler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new
                    StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        // 初始化Bugly
        //CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);

        // 初始化Bomb
        Bmob.initialize(this, StaticClass.BOMB_APP_ID);
    }
}
