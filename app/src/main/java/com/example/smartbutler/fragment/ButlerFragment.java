package com.example.smartbutler.fragment;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.fragment
 * 文件名:  ButlerFragment
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/11 21:52
 * 描述:    TODO
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartbutler.R;
import com.example.smartbutler.adapter.ChatListAdapter;
import com.example.smartbutler.entity.ChatListData;
import com.example.smartbutler.utils.HttpRequest;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.ShareUtils;
import com.example.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.smartbutler.utils.StaticClass.CHAT_LIST_KEY;


public class ButlerFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;
    private Button btn_send;
    private EditText et_text;

    private List<ChatListData> mList = new ArrayList<>();
    ChatListAdapter adapter;
    private ChatListData chatMessage = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mChatListView = view.findViewById(R.id.mChatListView);
        btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        et_text = view.findViewById(R.id.et_text);
        // 设置适配器

        adapter = new ChatListAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);

        addLeftItem("你好，我是达达");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                //1.获取输入框的内容
                final String text = et_text.getText().toString();
                //2.判断是否为空
                if (!TextUtils.isEmpty(text)) {
                    //3.判断长度不能大于30
                    if (text.length() > 30) {
                        Toast.makeText(getActivity(), R.string.text_more_length, Toast.LENGTH_SHORT).show();
                    } else {
                        //4.清空当前的输入框
                        et_text.setText("");
                        //5.添加你输入的内容到right item
                        addRightItem(text);
                        //6.发送给机器人请求返回内容
//                        String url = "http://op.juhe.cn/robot/index?info=" + text
//                                + "&key=" + StaticClass.CHAT_LIST_KEY;
                        // 发送消息去服务器端，返回数据
                        new Thread() {
                            public void run() {
                                ChatListData chat = HttpRequest.sendMessage(text);
                                Message message = new Message();
                                message.what = 0x1;
                                message.obj = chat;
                                handler.sendMessage(message);
                            };
                        }.start();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.text_tost_empty, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x1) {
                if (msg.obj != null) {
                    chatMessage = (ChatListData) msg.obj;
                }
                //更新数据
                mList.add(chatMessage);
                // 通知adapter刷新
                adapter.notifyDataSetChanged();
                // 滚动到底部
                mChatListView.setSelection(mChatListView.getBottom());
            }
        };
    };

    public static String steamToString(InputStream in) {
        String result = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
                out.flush();
            }
            result = new String(out.toByteArray(), "utf-8");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObhect = new JSONObject(t);
            JSONObject jsonresult = jsonObhect.getJSONObject("result");
            //拿到返回值
            String text = jsonresult.getString("50000");
            //7.拿到机器人的返回值之后添加在left item
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addLeftItem(String text) {
//        boolean isSpeak = ShareUtils.getBoolean(getActivity(), "isSpeak", false);
//        if (isSpeak) {
//            startSpeak(text);
//        }
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mList.add(data);
        // 通知adapter刷新
        adapter.notifyDataSetChanged();
        // 滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    public void addRightItem(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mList.add(data);
        // 通知adapter刷新
        adapter.notifyDataSetChanged();
        // 滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }


}
