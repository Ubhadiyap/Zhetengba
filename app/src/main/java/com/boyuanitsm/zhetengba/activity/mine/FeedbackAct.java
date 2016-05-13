package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.widget.EditText;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 反馈
 * Created by Administrator on 2016/5/6.
 */
public class FeedbackAct extends BaseActivity {
    @ViewInject(R.id.feedback_et)
    private EditText etFeedBack;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_eventdetails);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("反馈");
        etFeedBack.setHint("请输入您的意见和建议...");
    }
}
