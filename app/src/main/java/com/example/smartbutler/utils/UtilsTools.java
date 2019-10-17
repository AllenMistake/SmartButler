package com.example.smartbutler.utils;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.utils
 * 文件名:  UtilsTools
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/11 20:39
 * 描述:    工具统一类
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class UtilsTools {

    // 设置字体
    public static void setFont(Context context, TextView textView){

        Typeface fontType = Typeface.createFromAsset(context.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    // 保存图片到ShareUtils
    public static void putImageToShare(Context context, ImageView imageView){
        // 保存
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        // 第一步，将BitMap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        // 第二步，利用Base64 将字节数组输出流转化成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        ShareUtils.putString(context, "image_title", imgString);

    }

    public static void getImageToShare(Context context, ImageView imageView){
        String imgString = ShareUtils.getString(context, "image_title", "");
        if(!imgString.equals("")){
            // 利用Base64将String转换
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            // 生成Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }
    }
}

