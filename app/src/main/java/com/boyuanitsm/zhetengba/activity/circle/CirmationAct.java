package com.boyuanitsm.zhetengba.activity.circle;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mess.ScanQrcodeAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.view.CommonView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 圈子资料界面
 * Created by bitch-1 on 2016/5/11.
 */
public class CirmationAct extends BaseActivity {
    @ViewInject(R.id.notice)
    private TextView notice;//公告
    @ViewInject(R.id.com_ewm)
    private CommonView com_ewm;//二维码
    @ViewInject(R.id.com_jb)
    private CommonView com_jb;//举报
    @ViewInject(R.id.tv_tc)
    private CommonView tv_tc;//退出按钮

    private CircleEntity circleEntity;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_cirmation);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子资料");
        circleEntity=getIntent().getParcelableExtra("circleEntity");
        if(circleEntity!=null){
            if(!TextUtils.isEmpty(circleEntity.getNotice())) {
                notice.setText(circleEntity.getNotice());
            }else {
                notice.setText("暂无");
            }
        }
    }

    @OnClick({R.id.com_ewm,R.id.com_jb,R.id.tv_tc})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.com_ewm://圈子二维码
                openActivity(CircleEr.class);
                break;
            case R.id.com_jb://圈子举报

                break;
            case R.id.tv_tc://圈子退出
                exitCircle(circleEntity.getId());
//                Toast.makeText(getApplicationContext(),"hah",Toast.LENGTH_SHORT).show();

                break;

        }
    }

    /**
     * 成员退出圈子（群主退出，圈也注销）
     * @param circleId
     */
    private void exitCircle(String circleId){
        RequestManager.getTalkManager().removeCircle(circleId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {

            }
        });
    }
}
