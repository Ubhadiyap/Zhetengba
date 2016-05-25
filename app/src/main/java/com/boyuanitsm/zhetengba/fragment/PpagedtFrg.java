package com.boyuanitsm.zhetengba.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.PpdtfrgAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.view.MyListview;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 消息个人主页界面圈子动态frg
 * Created by bitch-1 on 2016/5/16.
 */
public class PpagedtFrg extends BaseFragment {
    @ViewInject(R.id.lv_ppcal)
    private MyListview lv_ppcal;
    private View view;
    @Override
    public View initView(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.frg_ppagedt,null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        lv_ppcal.setAdapter(new PpdtfrgAdapter(getActivity()));

    }
}
