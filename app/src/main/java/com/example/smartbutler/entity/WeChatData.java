package com.example.smartbutler.entity;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.entity
 * 文件名:  WeChatData
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/21 19:25
 * 描述:    微信精选数据类
 */

public class WeChatData {

    // 标题
    private String title;
    // 出处
    private String source;
    // 图片的URL
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
