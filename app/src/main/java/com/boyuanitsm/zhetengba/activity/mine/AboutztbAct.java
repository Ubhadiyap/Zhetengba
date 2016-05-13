package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 关于折腾吧界面
 * Created by bitch-1 on 2016/5/3.
 */
public class AboutztbAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_aboutztb);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("关于折腾吧");

    }
}
