package com.boyuanitsm.zhetengba.activity.mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CommonView;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.hyphenate.EMCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

//import cn.jpush.android.api.JPushInterface;
//import cn.jpush.android.api.TagAliasCallback;

/**
 * 设置界面
 * Created by bitch-1 on 2016/5/3.
 */
public class SettingAct extends BaseActivity {
//    @ViewInject(R.id.tb_verification)
//    private ToggleButton tbVerification;
    @ViewInject(R.id.iv_yz)
    private ImageView iv_yz;

    private int select=1;//默认加我需要验证，0加我时不需要验证

    private SharedPreferences sharedPrefrences;
    private SharedPreferences.Editor editor;

    @ViewInject(R.id.cv_clearCache)
    private CommonView cv_clearCache;
//    private int select=0;//加我不需要验证
    private String totalCacheSize;
    private String version;
    private String platform;


    @Override
    public void setLayout() {
        setContentView(R.layout.act_setting);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("设置");

        sharedPrefrences = this.getSharedPreferences("type",MODE_PRIVATE);//得到SharedPreferences，会生成user.xml
        editor = sharedPrefrences.edit();

        String typenum = sharedPrefrences.getString("typenum", null);
        if(typenum!=null){
            if(typenum.equals("1")){
                iv_yz.setBackgroundDrawable(getResources().getDrawable(R.drawable.switch_on));
            }
            if(typenum.equals("0")){
                iv_yz.setBackgroundDrawable(getResources().getDrawable(R.drawable.switch_off));
            }
        }else {
            iv_yz.setBackgroundDrawable(getResources().getDrawable(R.drawable.switch_on));
        }


//        tbVerification.setIsSwitch(true);
//        tbVerification.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
//            @Override
//            public void onToggle(boolean on) {
//                if (on) {//添加我时需要验证
//
//                } else {//添加我时不需要验证
//
//                }
//            }
//        });

        initData();
    }

    private void initData() {
        try {
            totalCacheSize = ZhetebaUtils.getTotalCacheSize(getApplicationContext());
            cv_clearCache.setNotesText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.iv_yz,R.id.cv_about, R.id.cv_feedback, R.id.cv_checkUpdate, R.id.cv_clearCache, R.id.llExit})
    public void todo(View view) {
        switch (view.getId()) {
            case R.id.iv_yz:
                if(select==1){
                    select=0;
                    iv_yz.setBackgroundDrawable(getResources().getDrawable(R.drawable.switch_off));
                    MyToastUtils.showShortToast(SettingAct.this, select + "");
                    ischeck();//默认进来不需要，点击后不需要
                    editor.putString("typenum", "0");
                    editor.commit();
                    return;
                }
                if(select==0){
                    select=1;
                    iv_yz.setBackgroundDrawable(getResources().getDrawable(R.drawable.switch_on));
                    MyToastUtils.showShortToast(SettingAct.this, select + "");
                    ischeck();//需要
                    editor.putString("typenum", "1");
                    editor.commit();
                    return;
                }

                break;
            case R.id.cv_about://关于
                openActivity(AboutztbAct.class);
                break;
            case R.id.cv_feedback://反馈
                openActivity(FeedbackAct.class);
                break;
            case R.id.cv_checkUpdate://检查更新
                version=ZtinfoUtils.getVersion(SettingAct.this);
                findNewVersion(version, platform);
                break;
            case R.id.cv_clearCache://清楚缓存
                clearCache();
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

    /**
<<<<<<< HEAD
     * 添加好友默认是不需要添加状态，掉一次后台切换一次状态
     */
    private void ischeck() {
//        RequestManager.getMessManager().isCheck(new ResultCallback<ResultBean<String>>()
        RequestManager.getMessManager().isCheck(new ResultCallback() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }


    /**检查版本更新
     * @param version
     * @param platform
     */
    private void findNewVersion(String version,String platform) {
        RequestManager.getUserManager().findNewApp(version, platform, new ResultCallback() {

            @Override
            public void onError(int status, String errorMsg) {

            }


            public void onResponse(Object response) {


            }
        });
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

    /**
     * 清理缓存
     */
    private void clearCache() {
        ZhetebaUtils.clearAllCache(getApplicationContext());
        try {
            totalCacheSize = ZhetebaUtils.getTotalCacheSize(getApplicationContext());
            cv_clearCache.setNotesText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyToastUtils.showShortToast(SettingAct.this, "已清除缓存");
    }


}
