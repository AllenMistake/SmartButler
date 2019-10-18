package com.example.smartbutler.entity;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.entity
 * 文件名:  Result
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/17 21:57
 * 描述:    返回类型的实体类
 */

public class Result {
    private int code; // code码
    private String text; // 信息
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}