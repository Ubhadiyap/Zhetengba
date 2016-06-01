package com.boyuanitsm.zhetengba.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.PpfrgAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.utils.ListViewUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.view.MyListview;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 个人主页里面档期frg
 * Created by bitch-1 on 2016/5/16.
 */
public class PpagecalFrg extends BaseFragment {
    private View view;
    @ViewInject(R.id.lv_ppcal)
    private MyListview lv_ppcal;


    @Override
    public View initView(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.frg_ppagecal,null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
            lv_ppcal.setAdapter(new PpfrgAdapter(getActivity()));
            int lvHeight = ListViewUtil.MeasureListView(lv_ppcal);
            MyLogUtils.degug("lvheight=======" + lvHeight);

   }
}
