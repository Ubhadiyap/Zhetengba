package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 位置信息
 * Created by xiaoke on 2016/5/10.
 */
public class LocationAct extends BaseActivity {
    @Override
    public void setLayout() {
    setContentView(R.layout.act_location);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("位置信息");
    }
}
