package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 设置里面新消息界面
 * Created by bitch-1 on 2016/7/19.
 */
public class NewxxAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_newxx);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("新消息提醒");

    }
}
