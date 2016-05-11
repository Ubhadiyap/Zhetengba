package com.boyuanitsm.zhetengba.fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseFragment;

/**
 * Created by xiaoke on 2016/4/29.
 */
public class MessFrg extends BaseFragment {

    @Override
    public View initView(LayoutInflater inflater) {
        view =inflater.inflate(R.layout.mess_frag,null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

}
