package com.boyuanitsm.zhetengba.activity.circle;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
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
    @ViewInject(R.id.et_comment)//评论edittext
    private EditText et_comment;

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
                if (et_comment.getText().toString().equals("")) {
                    MyToastUtils.showShortToast(CommentAct.this, "输入内容不能为空");
                } else {
                    MyToastUtils.showShortToast(CommentAct.this, "评论成功");
                    finish();
                }

            }
        });
//        et_comment.setFocusable(true);
//        et_comment.setFocusableInTouchMode(true);
//        et_comment.requestFocus();
//        et_comment.requestFocusFromTouch();
//        InputMethodManager imm = (InputMethodManager)getSystemService(CommentAct.this.INPUT_METHOD_SERVICE);
//        imm.showSoftInputFromInputMethod(et_comment.getWindowToken(), 0);
    }

    @OnClick({R.id.et_comment})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.ll_zhuanfa:
//                cb_zhuanfa.setBackgroundDrawable(CommentAct.this.getResources().getDrawable(R.drawable.comment_check));
//                break;
            case R.id.et_comment:
                break;
        }
    }


}
