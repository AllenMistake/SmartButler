package com.example.smartbutler.fragment;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.fragment
 * 文件名:  WechatFragment
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/11 21:52
 * 描述:    TODO
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartbutler.R;

public class WechatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        return view;
    }
}
