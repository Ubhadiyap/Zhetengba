package com.boyuanitsm.zhetengba.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 简约界面
 * Created by bitch-1 on 2016/5/3.
 */
public class ContractedAct extends BaseActivity implements View.OnClickListener{
    private TextView tv_select_location;
    private LinearLayout ll_theme_content;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_contracted);

    }

    @Override
    public void init(Bundle savedInstanceState) {
    setTopTitle("简约");
        tv_select_location = (TextView) findViewById(R.id.tv_select);
         ll_theme_content = (LinearLayout) findViewById(R.id.ll_theme_content);
        tv_select_location.setOnClickListener(this);
        ll_theme_content.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_select:
                openActivity(LocationAct.class);
                break;
            case R.id.ll_theme_content:
                openActivity(EventdetailsAct.class);
        }
    }
}
