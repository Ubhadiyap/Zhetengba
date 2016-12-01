package com.boyuanitsm.zhetengba.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.AppManager;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.MainAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by xiaoke on 2016/11/29.
 */
public class LoginFirstAct extends BaseActivity {
    @ViewInject(R.id.tvLogin)
    private TextView tvLogin;
    @ViewInject(R.id.tvLogin2)
    private TextView tvLogin2;
    private boolean autoLogin=false;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_login_first);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        // 如果登录成功过，直接进入主页面
        if (DemoHelper.getInstance().isLoggedIn()) {
            autoLogin = true;
            startActivity(new Intent(LoginFirstAct.this, MainAct.class));
            return;
        }
        AppManager.getAppManager().addActivity(this);
        tvLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openActivity(LoginAct.class);

        }
    });
        tvLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(RegistAct.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }
}
