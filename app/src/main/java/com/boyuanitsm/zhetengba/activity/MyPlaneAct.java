package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 圈子--我的发布界面
 * Created by xiaoke on 2016/5/6.
 */
public class MyPlaneAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_my_plane);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的发布");
    }
}
