package com.example.smartbutler.utils;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.utils
 * 文件名:  PicassoUtils
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/23 19:45
 * 描述:    Picasso封装
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.core.net.ConnectivityManagerCompat;

import com.example.smartbutler.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class PicassoUtils {

    // 默认加载图片
    public static void loadImageView(String url, ImageView imageView){
        Picasso.get().load(url).into(imageView);
    }

    // 默认加载图片（指定大小）
    public static void loadImageViewSize(String url, int width, int height, ImageView imageView){
        Picasso.get()
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }

    public static void loadImageViewHolder(String url, int loadImg, int errorImg, ImageView imageView){
        Picasso.get()
                .load(url)
                .placeholder(loadImg)
                .error(errorImg)
                .into(imageView );
    }

    // 裁剪图片
    public static void loadImageViewCrop(String url , ImageView imageView){
        Picasso.get()
                .load(url)
                .transform(new CropSquareTransformation())
                .into(imageView);

    }

    // 按比例裁剪
    public static class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() {
            return "square()";
        }
    }

}
