package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 扫一扫界面 布局里面只有一个二维码
 * Created by bitch-1 on 2016/5/11.
 */
public class ScanAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_scan);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("扫一扫");

    }
}
