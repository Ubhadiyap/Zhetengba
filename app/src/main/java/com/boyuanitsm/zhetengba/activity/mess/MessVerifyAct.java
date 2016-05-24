package com.boyuanitsm.zhetengba.activity.mess;
import android.os.Bundle;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 加好友发送消息验证；圈友主页，加好友按钮
 * Created by xiaoke on 2016/5/20.
 */
public class MessVerifyAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_friend_verfy);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("allen");
    }
}
