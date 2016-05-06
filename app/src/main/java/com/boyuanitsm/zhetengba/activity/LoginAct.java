package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * Created by bitch-1 on 2016/4/29.
 */
public class LoginAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.login);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        TextView tv_regist = (TextView) findViewById(R.id.tv_regist);
        TextView tv_forget_pw = (TextView) findViewById(R.id.tv_forget_pw);
        tv_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(RegistAct.class);
            }
        });
        tv_forget_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(FrogetpwdAct.class);
            }
        });

  }

}
