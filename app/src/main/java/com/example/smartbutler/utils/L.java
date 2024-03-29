package com.example.smartbutler.utils;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.utils
 * 文件名:  L
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/12 15:56
 * 描述:    TODO
 */

import android.util.Log;

public class L {

    // 开关
    public static final boolean DEBUG = true;
    // TAG
    public static final String TAG = "Smartbutler";

    // 四个等级 DIWE

    public static void d(String text){
        if(DEBUG){
            Log.d(TAG, text);
        }
    }
    public static void i(String text){
        if(DEBUG){
            Log.i(TAG, text);
        }
    }
    public static void w(String text){
        if(DEBUG){
            Log.w(TAG, text);
        }
    }
    public static void e(String text){
        if(DEBUG){
            Log.e(TAG, text);
        }
    }

}
