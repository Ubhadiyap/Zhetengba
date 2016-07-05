package com.boyuanitsm.zhetengba.activity.circle;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 圈子二维码
 * Created by xiaoke on 2016/5/24.
 */
public class CircleEr extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.tv_share)//分享二维码
    private TextView tv_share;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_shareqrcode2);
    }

    @Override
    public void init(Bundle savedInstanceState) {
            setTopTitle("圈子二维码");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_share:
//              openActivity(ShareDialogAct.class);
        }
    }
}
