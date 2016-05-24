package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.widget.RadioButton;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 注册信息界面面
 * Created by bitch-1 on 2016/5/9.
 */
public class RegInfoAct extends BaseActivity {

    @ViewInject(R.id.boy_rd)
    private RadioButton boy_rd;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_reginfo);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("注册信息");
//        boy_rd.setChecked(true);

    }
}
