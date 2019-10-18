package com.example.smartbutler.adapter;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.adapter
 * 文件名:  ChatListAdapter
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/17 19:55
 * 描述:    对话adapter
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.ChatListData;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {

    public static final int VALUE_LEFT_TEXT = 1;
    // 左面type
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context mContext;
    private LayoutInflater inflater;
    private ChatListData data;
    private List<ChatListData> mList;

    public ChatListAdapter(Context mContext, List<ChatListData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        // 获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;

        // 获取当前要显示type， 根据这个type来区分数据的加载
        int type = getItemViewType(position);
        // 第一次加载
        if (view == null) {
            switch (type) {
                case VALUE_LEFT_TEXT:

                    viewHolderLeftText = new ViewHolderLeftText();
                    view = inflater.inflate(R.layout.left_item, null);
                    viewHolderLeftText.tv_left_text = view.findViewById(R.id.tv_left_text);
                    view.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    view = inflater.inflate(R.layout.right_item, null);
                    viewHolderRightText.tv_right_text = view.findViewById(R.id.tv_right_text);
                    view.setTag(viewHolderRightText);
                    break;
            }
        }else{
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) view.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText) view.getTag();
                    break;
            }
        }

        // 赋值
        ChatListData data = mList.get(position);
        switch (type){
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.tv_right_text.setText(data.getText());
                break;
        }

        return view;
    }

    // 根据数据源的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = mList.get(position);
        int type = data.getType();
        return type;
    }

    // 返回所有layout数量
    @Override
    public int getViewTypeCount() {
        return 3;//mList.size() + 1
    }

    // 左边的文本
    class ViewHolderLeftText {
        private TextView tv_left_text;
    }

    // 右边的文本
    class ViewHolderRightText {
        private TextView tv_right_text;
    }
}
