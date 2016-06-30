package com.boyuanitsm.zhetengba.activity.mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.AppManager;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.MainAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.db.ActivityMessDao;
import com.boyuanitsm.zhetengba.db.LabelInterestDao;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.boyuanitsm.zhetengba.widget.ToggleButton;
import com.hyphenate.EMCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

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
                if (on) {//添加我时需要验证

                } else {//添加我时不需要验证

                }
            }
        });
    }


    @OnClick({R.id.cv_about, R.id.cv_feedback, R.id.cv_checkUpdate, R.id.cv_clearCache, R.id.llExit})
    public void todo(View view) {
        switch (view.getId()) {
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
            case R.id.llExit://退出
                new MyAlertDialog(SettingAct.this).builder().setTitle("提示")
                        .setMsg("确认退出吗？")
                        .setPositiveButton("退出", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loginOut();
                            }
                        }).setNegativeButton("取消", null).show();

                break;
        }
    }


    void logout(final ProgressDialog pd) {

        DemoHelper.getInstance().logout(false,new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        UserInfoDao.deleteUser();
                        LabelInterestDao.delAll();//清理数据库
                        ActivityMessDao.delAll();
                        JPushInterface.setAlias(getApplicationContext(), "", new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {

                            }
                        });
                        // 重新显示登陆页面
                        AppManager.getAppManager().finishActivity(MainAct.class);
                        finish();
                        startActivity(new Intent(SettingAct.this, LoginAct.class));

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
               runOnUiThread(new Runnable() {

                   @Override
                   public void run() {
                       // TODO Auto-generated method stub
                       pd.dismiss();
                       Toast.makeText(SettingAct.this, "退出失败", Toast.LENGTH_SHORT).show();

                   }
               });
            }
        });
    }

    /**
     * 登出
     */
    private void loginOut(){
        final ProgressDialog pd = new ProgressDialog(this);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
       RequestManager.getUserManager().Loginout(new ResultCallback<ResultBean<String>>() {
           @Override
           public void onError(int status, String errorMsg) {
               pd.dismiss();
               Toast.makeText(SettingAct.this, "退出失败", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onResponse(ResultBean response) {
               logout(pd);
           }
       });

    }



}
