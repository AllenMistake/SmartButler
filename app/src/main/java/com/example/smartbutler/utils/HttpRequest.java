package com.example.smartbutler.utils;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.utils
 * 文件名:  HttpRequest
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/17 21:45
 * 描述:    TODO
 */

import com.example.smartbutler.adapter.ChatListAdapter;
import com.example.smartbutler.entity.ChatListData;
import com.example.smartbutler.entity.Result;
import com.google.gson.Gson;//Google Gson包
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class HttpRequest {
    public static ChatListData sendMessage(String message) {
        ChatListData chatMessage = new ChatListData();
        String gsonResult = doGet(message);//连接请求的内容
        Gson gson = new Gson();
        Result result = null;
        if (gsonResult != null) {
            try {
                result = gson.fromJson(gsonResult, Result.class);//从json数据（json格式字符串）转为java对象（解析数据）
                chatMessage.setText(result.getText());//http连接获取的内容解析之后的结果给聊天信息赋值
            }
            catch (Exception e) {
                chatMessage.setText("请求错误...");
            }
        }
        chatMessage.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        return chatMessage;
    }

    public static String doGet(String message) {
        String result = "";
        String url = setParmat(message);
        System.out.println("------------url = " + url);
        InputStream input = null;
        ByteArrayOutputStream output = null;
        try {
            //创建URL实例，打开URLConnection 创建连接
            URL urls = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
            //设置Connection参数
            connection.setReadTimeout(5 * 1000);//读取超时
            connection.setConnectTimeout(5 * 1000);//连接超时
            connection.setRequestMethod("GET"); //使用GET提交模式
            input = connection.getInputStream();
            output = new ByteArrayOutputStream();//初始化字节数组输出流对象
            int len = -1;
            byte[] buff = new byte[1024];
            while ((len = input.read(buff)) != -1) {//读取长度
                output.write(buff, 0, len);// 将buff字节数组中从偏移量0开始的 len 个字节写入此字节数组输出流
            }
            output.flush();
            result = new String(output.toByteArray());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output .close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    //拼接api地址
    private static String setParmat(String message) {
        String url = "";//api地址
        try {
            url ="http://www.tuling123.com/openapi/api?key=" + StaticClass.CHAT_LIST_KEY + "&info="
                    + URLEncoder.encode(message, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

}