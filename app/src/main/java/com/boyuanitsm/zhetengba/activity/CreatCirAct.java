package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 建立圈子界面
 * Created by bitch-1 on 2016/5/9.
 */
public class CreatCirAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_creatcir);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("建立圈子");

    }
}
