package com.boyuanitsm.zhetengba.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.PpdtfrgAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.PersonalMain;
import com.boyuanitsm.zhetengba.bean.ScheduleInfo;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.utils.ListViewUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
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
    private List<List<ImageInfo>> datalist=new ArrayList<>();
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
        List<UserInfo> userEntity = personalMain.getUserEntity();
        initImageData();
        lv_ppcal.setAdapter(new PpdtfrgAdapter(mActivity, circleTalkEntity, userEntity, datalist));
    }

    /**
     * 图片地址初始化
     */
    private void initImageData() {
        for (int j=0;j<circleTalkEntity.size();j++) {
            List<ImageInfo> itemList=new ArrayList<>();
            //将图片地址转化成数组
            if(!TextUtils.isEmpty(circleTalkEntity.get(j).getTalkImage())) {
                String[] urlList = ZtinfoUtils.convertStrToArray(circleTalkEntity.get(j).getTalkImage());
                for (int i = 0; i < urlList.length; i++) {
                    itemList.add(new ImageInfo(urlList[i], 1624, 914));
                }
            }
            datalist.add(itemList);
        }
    }
}
