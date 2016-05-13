package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.view.View;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.AboutztbAct;
import com.boyuanitsm.zhetengba.activity.mine.FeedbackAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.widget.ToggleButton;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 设置界面
 * Created by bitch-1 on 2016/5/3.
 */
public class SettingAct extends BaseActivity {
    @ViewInject(R.id.tb_verification)
    private ToggleButton tbVerification;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_setting);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("设置");
        tbVerification.setIsSwitch(true);
        tbVerification.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on){//添加我时需要验证

                }else{//添加我时不需要验证

                }
            }
        });
    }


    @OnClick({R.id.cv_about,R.id.cv_feedback,R.id.cv_checkUpdate,R.id.cv_clearCache,R.id.tv_signOut})
    public void todo(View view){
        switch (view.getId()){
            case R.id.cv_about://关于
                openActivity(AboutztbAct.class);
                break;
            case R.id.cv_feedback://反馈
                openActivity(FeedbackAct.class);
                break;
            case R.id.cv_checkUpdate://检查更新

                break;
            case R.id.cv_clearCache://清楚缓存

                break;
            case R.id.tv_signOut://退出

                break;
        }
    }
}
