package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by bitch-1 on 2016/7/15.
 */
public class WebAct extends BaseActivity {
    @ViewInject(R.id.wv)
    private WebView wv;
    String url = null;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_web);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("注册协议");
        url="file:///android_asset/Agree.htm";
        WebSettings webSettings = wv.getSettings();
//        webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
//        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setSupportZoom(true);
//        wv.requestFocusFromTouch();
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient() {
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
}
