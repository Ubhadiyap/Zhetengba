package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 分享二维码界面
 * Created by bitch-1 on 2016/5/3.
 */
public class ShareewmAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_shareewm);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("分享二维码");

    }
}
