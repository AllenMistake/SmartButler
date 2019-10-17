package com.example.smartbutler.ui;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.ui
 * 文件名:  SettingActivity
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/12 15:37
 * 描述:    设置
 */

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.smartbutler.R;

public class SettingActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
