package com.boyuanitsm.zhetengba.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.boyuanitsm.zhetengba.MyApplication;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.SpUtils;
import com.boyuanitsm.zhetengba.view.LoadingView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoke on 2016/9/6.
 */
public class H5Web extends BaseActivity {
    @ViewInject(R.id.wb)
    private WebView wb;
    @ViewInject(R.id.loadingview)
    private LoadingView loadingView;
    private String topTitle;
    private String hdUrl;
    private String cookies;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_hd);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        cookies= SpUtils.getCookie(H5Web.this).toString();
        MyLogUtils.info("hah" + cookies);
        Intent intent = getIntent();
        topTitle= intent.getStringExtra("topTitle");
        hdUrl=intent.getStringExtra("url");
        if (!TextUtils.isEmpty(topTitle)){
//            setTopTitle(topTitle);
            setLayoutbac(Color.parseColor("#00000000"));
            setLeftRes(R.mipmap.black_back);
        }
        if(!TextUtils.isEmpty(hdUrl)){
            initWebViewSettings();
            synCookies(H5Web.this, hdUrl);
//            syncCookie(H5Web.this,hdUrl,cookies);

//            initWebViewSettings();

//            Map<String,String> extraHeaders = new HashMap<String, String>();
//            extraHeaders.put("Set-Cookie", SpUtils.getCookie(MyApplication.getInstance()));
            wb.loadUrl(hdUrl);

        }

        wb.setWebViewClient(new WebViewClient() {
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//                                    Map<String,String> extraHeaders = new HashMap<String, String>();
//                                    extraHeaders.put("Set-Cookie", SpUtils.getCookie(MyApplication.getInstance()));
//                                    view.loadUrl(url,extraHeaders);
                                    return true;
                                }

                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    loadingView.loadComplete();
                                }
                            }
        );
//        wb.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                if (newProgress>=100){
//                    loadingView.loadComplete();
//                }
//            }
//        });

    }

    /**
     *
     * @param context
     * @param url
     * @param cookies
     */

    /**
     *设置webview
     */
    private void initWebViewSettings() {
        WebSettings webSettings=wb.getSettings();
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

    }

    /**初始化webview设置cookie
     * @param context
     * @param url
     */
    private void synCookies(Context context,String url) {
        try{
//            MyLogUtils.info("Nat: webView.syncCookie.url" + url);
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();

            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(String.format("JSESSIONID=%s",cookieSplit(cookies)));

//            sbCookie.append(String.format(";Domain=%s","139.196.234.89"));//180.76.149.156

//            sbCookie.append(String.format(";Domain=%s","172.16.6.253"));//180.76.149.156

            sbCookie.append(String.format(";Path=%s","/zhetengba/"));
//            sbCookie.append(String.format(";HttpOnly"));

            String cookieValue = sbCookie.toString();
//            MyLogUtils.info("sile" + cookieValue);
            cookieManager.setCookie(url, cookieValue);
            CookieSyncManager.getInstance().sync();
//            String newCookie = cookieManager.getCookie(url);
//            if(newCookie != null){
//                MyLogUtils.info("Nat: webView.syncCookie.newCookie"+ newCookie);
//            }else {
//                MyLogUtils.info("空的不说话了");
//            }
        }catch(Exception e){
            MyLogUtils.info("Nat: webView.syncCookie failed"+ e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (wb.canGoBack()) {
            wb.goBack();
        }
    }
    private  String cookieSplit(String cookies){
        if (!TextUtils.isEmpty(cookies)){
                String[] cookievalues=cookies.split(";");
            String[] cookieId = cookievalues[0].split("=");
            MyLogUtils.info(cookieId[1]+"cookieID是====");
            return cookieId[1];
        }else {
            return null;
        }
    }

}
