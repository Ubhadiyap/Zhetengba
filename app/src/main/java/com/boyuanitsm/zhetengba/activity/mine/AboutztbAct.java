package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 关于折腾吧界面
 * Created by bitch-1 on 2016/5/3.
 */
public class AboutztbAct extends BaseActivity {
    @ViewInject(R.id.tv_publish)
    private TextView tv_publish;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_aboutztb);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("关于折腾吧");
        tv_publish.setText("版本号:" + ZhetebaUtils.getAppVersion(getApplicationContext()));
    }
}
