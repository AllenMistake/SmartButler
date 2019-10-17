package com.example.smartbutler.adapter;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.adapter
 * 文件名:  CourierAdapter
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/16 21:36
 * 描述:    TODO
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.CourierData;

import java.util.List;

public class CourierAdapter extends BaseAdapter {
    private Context mContext;
    private List<CourierData> mList;
    //布局加载器
    private LayoutInflater inflater;
    private CourierData data;

    public CourierAdapter(Context mContext, List<CourierData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
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
        ViewHolder viewHolder = null;
        //第一次加载
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.layout_courier_item,null);
            viewHolder.tv_remark =  view.findViewById(R.id.tv_remark);
            viewHolder.tv_zone =  view.findViewById(R.id.tv_zone);
            viewHolder.tv_datetime =  view.findViewById(R.id.tv_datetime);
            //设置缓存
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        //设置数据
        data = mList.get(position);

        viewHolder.tv_remark.setText(data.getRemark());
        viewHolder.tv_zone.setText(data.getZone());
        viewHolder.tv_datetime.setText(data.getDatetime());
        return view;
    }

    class ViewHolder{
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;
    }
}
