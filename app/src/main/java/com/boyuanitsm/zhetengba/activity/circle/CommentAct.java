package com.boyuanitsm.zhetengba.activity.circle;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 评论界面
 * Created by xiaoke on 2016/5/17.
 */
public class CommentAct extends BaseActivity implements View.OnClickListener {
//    @ViewInject(R.id.ll_zhuanfa)
//    private LinearLayout ll_zhuanfa;
//    @ViewInject(R.id.cb_zhuanfa)
//    private CheckBox cb_zhuanfa;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_comment);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("评论");
        setRight("发送", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

//    @OnClick({R.id.ll_zhuanfa,R.id.cb_zhuanfa})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.ll_zhuanfa:
//                cb_zhuanfa.setBackgroundDrawable(CommentAct.this.getResources().getDrawable(R.drawable.comment_check));
//                break;
        }
    }
}
