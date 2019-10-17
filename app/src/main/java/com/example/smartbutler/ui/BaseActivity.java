package com.example.smartbutler.ui;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.ui
 * 文件名:  BaseActivity
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/11 20:26
 * 描述:    Activity基类
 */

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 主要做的事情
 * 1. 统一的属性
 * 2. 统一的接口
 * 3. 统一的方法
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // 菜单栏操作

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
