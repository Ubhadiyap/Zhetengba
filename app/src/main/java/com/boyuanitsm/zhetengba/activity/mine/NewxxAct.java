package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.chat.DemoModel;
import com.boyuanitsm.zhetengba.utils.SpUtils;
import com.boyuanitsm.zhetengba.widget.ToggleButton;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.jpush.android.api.JPushInterface;

/**
 * 设置里面新消息界面
 * Created by bitch-1 on 2016/7/19.
 */
public class NewxxAct extends BaseActivity {
    @ViewInject(R.id.tbZd)
    private ToggleButton tbzb;
    @ViewInject(R.id.tbCZd)
    private ToggleButton tbCZb;
    @ViewInject(R.id.tbChatZd)
    private ToggleButton tbChatZb;
    @ViewInject(R.id.tbVoice)
    private ToggleButton tbVoice;
    @ViewInject(R.id.llZd)
    private LinearLayout llZb;

    private DemoModel settingsModel;
    private int zd,voice;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_newxx);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("新消息提醒");
        settingsModel = DemoHelper.getInstance().getModel();
        zd= SpUtils.getZd(getApplicationContext());
        voice=SpUtils.getVoice(getApplicationContext());
//        if(zd==0){
//            llZb.setVisibility(View.VISIBLE);
//            tbzb.setIsSwitch(true);
//        }else{
//            llZb.setVisibility(View.GONE);
//            tbzb.setIsSwitch(false);
//            tbCZb.setIsSwitch(false);
//            tbChatZb.setIsSwitch(false);
//        }

//        if(voice==0){
//            tbVoice.setIsSwitch(true);
//        }else{
//            tbVoice.setIsSwitch(false);
//        }

        // 是否打开震动
        // vibrate notification is switched on or not?
        if (settingsModel.getSettingMsgVibrate()) {
            tbzb.setIsSwitch(true);
        } else {
            tbzb.setIsSwitch(false);
        }

        //是否打开声音
        if(settingsModel.getSettingMsgSound()){
            tbVoice.setIsSwitch(true);
        }else{
            tbVoice.setIsSwitch(false);
        }

        //震动是否全部打开
        tbzb.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if(on){
                    settingsModel.setSettingMsgVibrate(true);
                    JPushInterface.setSilenceTime(getApplicationContext(), 00, 00, 00, 00);
                }else{
                    JPushInterface.setSilenceTime(getApplicationContext(), 00,00, 23, 00);
                    settingsModel.setSettingMsgVibrate(false);
                }
            }
        });
//        //聊天震动
//        tbChatZb.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
//            @Override
//            public void onToggle(boolean on) {
//                if(on){
//                    settingsModel.setSettingMsgVibrate(true);
//                }else{
//                    settingsModel.setSettingMsgVibrate(false);
//                }
//            }
//        });
        //聊天声音
        tbVoice.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if(on){
                    settingsModel.setSettingMsgSound(true);
                    JPushInterface.setSilenceTime(getApplicationContext(), 00,00, 00, 00);
                }else{
                    JPushInterface.setSilenceTime(getApplicationContext(), 00,00, 23, 00);
                    settingsModel.setSettingMsgSound(false);
                }
            }
        });
    }


}
