package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 分享二维码界面
 * Created by bitch-1 on 2016/5/3.
 */
public class ShareqrcodeAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_shareqrcode);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("分享二维码");

    }
}
