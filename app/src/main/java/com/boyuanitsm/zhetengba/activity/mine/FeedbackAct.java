package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 反馈
 * Created by Administrator on 2016/5/6.
 */
public class FeedbackAct extends BaseActivity {
    @ViewInject(R.id.feedback_et)
    private EditText etFeedBack;


    private String content;//输入的反馈内容
    @Override
    public void setLayout() {
        setContentView(R.layout.act_eventdetails);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("反馈");
    }
    @OnClick({R.id.tv_btn})
    public void OnClick(View v){
        content=etFeedBack.getText().toString().trim();
        switch (v.getId()){
            case R.id.tv_btn://完成标签
                if(TextUtils.isEmpty(content)){
                    MyToastUtils.showShortToast(getApplicationContext(),"请输入内容");
                    return;
                }else
                    FeedBack(content);
                break;

        }

    }

    /**
     * 反馈
     * @param content
     */
    public void FeedBack(String content){
        RequestManager.getUserManager().addFeedBack(content, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                MyToastUtils.showShortToast(getApplicationContext(),"反馈成功");
                finish();

            }
        });




    }
}
