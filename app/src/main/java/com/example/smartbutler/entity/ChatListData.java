package com.example.smartbutler.entity;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.entity
 * 文件名:  ChatListData
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/17 20:00
 * 描述:    对话列表的实体
 */

public class ChatListData {

    //type
    private int type;
    //文本
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
