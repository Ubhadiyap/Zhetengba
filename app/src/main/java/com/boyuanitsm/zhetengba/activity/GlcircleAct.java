package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 管理圈子界面
 * Created by bitch-1 on 2016/5/9.
 */
public class GlcircleAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_glcircle);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("管理圈子");

    }
}
