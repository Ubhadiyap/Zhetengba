package com.boyuanitsm.zhetengba.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.PpdtfrgAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.PersonalMain;
import com.boyuanitsm.zhetengba.bean.ScheduleInfo;
import com.boyuanitsm.zhetengba.utils.ListViewUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.view.MyListview;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息个人主页界面圈子动态frg
 * Created by bitch-1 on 2016/5/16.
 */
public class PpagedtFrg extends BaseFragment {
    @ViewInject(R.id.lv_ppcal)
    private MyListview lv_ppcal;
    private View view;
    private List<CircleEntity> circleTalkEntity=new ArrayList<>();
    private PersonalMain personalMain;
    private String PAGEFRG_KEY="perpage_to_pagecalFrg";
    private int i =0;
    @Override
    public View initView(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.frg_ppagedt,null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        personalMain = bundle.getParcelable(PAGEFRG_KEY);
        circleTalkEntity=personalMain.getCircleTalkEntity();
            lv_ppcal.setAdapter(new PpdtfrgAdapter(mActivity,circleTalkEntity));
            int lvHeight = ListViewUtil.MeasureListView(lv_ppcal);
            MyLogUtils.degug("lvheight=======" + lvHeight);
    }
}
