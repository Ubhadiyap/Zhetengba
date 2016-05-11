package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.view.CommonView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 圈子资料界面
 * Created by bitch-1 on 2016/5/11.
 */
public class CirmationAct extends BaseActivity {
    @ViewInject(R.id.com_ewm)
    private CommonView com_ewm;//二维码
    @ViewInject(R.id.com_jb)
    private CommonView com_jb;//举报
    @ViewInject(R.id.tv_tc)
    private CommonView tv_tc;//退出按钮

    @Override
    public void setLayout() {
        setContentView(R.layout.act_cirmation);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子资料");
    }

    @OnClick({R.id.com_ewm,R.id.com_jb,R.id.tv_tc})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.com_ewm://圈子二维码
                openActivity(ScanAct.class);
                break;
            case R.id.com_jb://圈子举报

                break;
            case R.id.tv_tc://圈子退出
                Toast.makeText(getApplicationContext(),"hah",Toast.LENGTH_SHORT).show();

                break;




        }


    }
}
