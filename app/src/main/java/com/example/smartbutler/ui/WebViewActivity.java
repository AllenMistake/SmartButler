package com.example.smartbutler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.smartbutler.R;
import com.example.smartbutler.utils.L;

public class WebViewActivity extends BaseActivity{

    // 进度
    private ProgressBar mProogressBar;
    // 网页
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {

        mProogressBar = findViewById(R.id.mProgressBar);
        mWebView = findViewById(R.id.mWebView);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");

        L.i("url = " +url);
        // 设置标题
        getSupportActionBar().setTitle(title);

        // 进行加载网页的逻辑

        // 支持JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 支持缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setWebChromeClient(new WebViewClient());
        // 加载网页
        mWebView.loadUrl(url);

        // 本地显示
        mWebView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                // 我接受这个事件
                return true;
            }
        });


    }
    public class WebViewClient extends WebChromeClient{

        // 进度变化监听
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100){
                mProogressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
