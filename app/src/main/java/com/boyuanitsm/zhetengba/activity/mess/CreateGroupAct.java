package com.boyuanitsm.zhetengba.activity.mess;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 建立群聊
 * Created by wangbin on 16/5/19.
 */
public class CreateGroupAct extends BaseActivity {

    @Override
    public void setLayout() {
        setContentView(R.layout.act_create_group);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("邀请好友");
    }
}
