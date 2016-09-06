package com.boyuanitsm.zhetengba.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

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
    }
}
