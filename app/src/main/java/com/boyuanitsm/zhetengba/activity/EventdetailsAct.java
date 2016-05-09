package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 活动详情
 * Created by bitch-1 on 2016/4/28.
 */
public class EventdetailsAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_eventdetails);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("活动详情");

    }
}
