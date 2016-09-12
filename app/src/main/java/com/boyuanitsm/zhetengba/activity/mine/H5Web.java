package com.boyuanitsm.zhetengba.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by xiaoke on 2016/9/6.
 */
public class H5Web extends BaseActivity {
    @ViewInject(R.id.wb)
    private WebView wb;
    private String topTitle;
    private String hdUrl;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_hd);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        topTitle= intent.getStringExtra("topTitle");
        hdUrl=intent.getStringExtra("url");
        if (!TextUtils.isEmpty(topTitle)){
            setTopTitle(topTitle);
        }
        if(!TextUtils.isEmpty(hdUrl)){
            wb.loadUrl(hdUrl);
        }

        WebSettings webSettings=wb.getSettings();
        //        webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
//        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setSupportZoom(true);
//        wv.requestFocusFromTouch();
        wb.loadUrl(hdUrl);
        wb.setWebViewClient(new WebViewClient() {
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                                    view.loadUrl(url);
                                    return true;
                                }

                                @Override
                                public void onPageFinished(WebView view, String url) {
//                                    loadingView.loadComplete();
                                }
                            }
        );

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (wb.canGoBack()) {
            wb.goBack();
        }
    }


}
