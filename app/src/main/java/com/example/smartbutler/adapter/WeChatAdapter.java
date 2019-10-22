package com.example.smartbutler.adapter;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.adapter
 * 文件名:  WeChatAdapter
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/21 19:24
 * 描述:    微信精选
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.WeChatData;

import java.util.List;

public class WeChatAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<WeChatData> mList;
    private WeChatData data;

    public WeChatAdapter(Context context, List<WeChatData> list){
        this.mContext = context;
        this.mList = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.wechat_item, null);
            viewHolder.iv_img = view.findViewById(R.id.iv_img);
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            viewHolder.tv_source = view.findViewById(R.id.tv_source);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        data = mList.get(i);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_source.setText(data.getSource());
        return view;
    }

    class ViewHolder{
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_source;
    }
}
