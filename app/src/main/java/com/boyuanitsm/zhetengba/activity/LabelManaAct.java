package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.view.FlowLayout;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/5/10.
 */
public class LabelManaAct extends BaseActivity {
    @ViewInject(R.id.fl_myLabel)
    private FlowLayout flMyLabel;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_labelmana);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("标签管理");
    }
}
