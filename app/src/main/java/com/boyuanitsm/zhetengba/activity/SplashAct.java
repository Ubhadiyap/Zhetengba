package com.boyuanitsm.zhetengba.activity;

import android.content.Intent;
import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.LoginAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.utils.SpUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.hyphenate.chat.EMClient;

/**
 * Created by wangbin on 16/5/10.
 */
public class SplashAct extends BaseActivity {

    private static final int sleepTime = 2000;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_splash);
    }

    @Override
    public void init(Bundle savedInstanceState) {
//       new Handler().postDelayed(new Runnable() {
//           @Override
//           public void run() {
//               openActivity(LoginAct.class);
//           }
//       }, 0);
        SpUtils.setScreenWith(getApplicationContext(), ZhetebaUtils.getScreenWidth(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            public void run() {
                if (DemoHelper.getInstance().isLoggedIn()) {
                    // ** 免登陆情况 加载所有本地群和会话
                    //不是必须的，不加sdk也会自动异步去加载(不会重复加载)；
                    //加上的话保证进了主页面会话和群组都已经load完毕
                    long start = System.currentTimeMillis();
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    long costTime = System.currentTimeMillis() - start;
                    //等待sleeptime时长
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //进入主页面
                    startActivity(new Intent(SplashAct.this, MainAct.class));
                    finish();
                }else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    startActivity(new Intent(SplashAct.this, LoginAct.class));
                    finish();
                }
            }
        }).start();

    }

}
