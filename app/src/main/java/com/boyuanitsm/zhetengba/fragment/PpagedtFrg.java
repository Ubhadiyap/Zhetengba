package com.boyuanitsm.zhetengba.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseFragment;

/**
 * 消息个人主页界面圈子动态frg
 * Created by bitch-1 on 2016/5/16.
 */
public class PpagedtFrg extends BaseFragment {
    private View view;
    @Override
    public View initView(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.frg_ppagedt,null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
