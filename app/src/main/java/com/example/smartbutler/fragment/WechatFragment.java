package com.example.smartbutler.fragment;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.fragment
 * 文件名:  WechatFragment
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/11 21:52
 * 描述:    TODO
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartbutler.R;
import com.example.smartbutler.adapter.WeChatAdapter;
import com.example.smartbutler.entity.WeChatData;
import com.example.smartbutler.ui.WebViewActivity;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WechatFragment extends Fragment {

    private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();

    private List<String> mListTitle = new ArrayList<>();
    private List<String> mListUrl = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mListView = view.findViewById(R.id.mListView);

        // 解析接口
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.d(t);
                parsingJson(t);
            }
        });

        // 点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                L.d("position :" + i);
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title", mListTitle.get(i));
                intent.putExtra("url", mListUrl.get(i));
                startActivity(intent);
            }
        });
    }

    private void parsingJson(String t) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonList = jsonResult.getJSONArray("list");
            for(int i = 0; i < jsonList.length(); i++){
                JSONObject json = (JSONObject) jsonList.get(i);
                WeChatData data = new WeChatData();
                String title = json.getString("title");
                data.setTitle(title);
                String url = json.getString("url");
                data.setSource(json.getString("source"));
                data.setImgUrl(json.getString("firstImg"));

                mList.add(data);
                mListTitle.add(title);
                mListUrl.add(url);

            }
            WeChatAdapter adapter = new WeChatAdapter(getActivity(), mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
