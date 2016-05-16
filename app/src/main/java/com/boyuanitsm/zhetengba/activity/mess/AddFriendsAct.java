package com.boyuanitsm.zhetengba.activity.mess;

import android.os.Bundle;
import android.view.View;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 添加好友
 * Created by wangbin on 16/5/16.
 */
public class AddFriendsAct extends BaseActivity {

    @Override
    public void setLayout() {
        setContentView(R.layout.actt_add_friends);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("添加好友");
    }

    @OnClick({R.id.rlPhone,R.id.ivWx,R.id.ivQQ})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rlPhone://手机联系人

                break;
            case R.id.ivWx:

                break;
            case R.id.ivQQ:

                break;
        }
    }

}
