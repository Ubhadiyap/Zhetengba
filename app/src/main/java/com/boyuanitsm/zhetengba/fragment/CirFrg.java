package com.boyuanitsm.zhetengba.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boyuanitsm.zhetengba.R;

/**
 * 子界面-圈子界面
 * Created by xiaoke on 2016/5/2.
 */
public class CirFrg extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cir_frg,null);
    }
}
