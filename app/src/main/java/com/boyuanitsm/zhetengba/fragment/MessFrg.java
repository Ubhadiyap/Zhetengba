package com.boyuanitsm.zhetengba.fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boyuanitsm.zhetengba.R;

/**
 * Created by xiaoke on 2016/4/29.
 */
public class MessFrg extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.mess_frag,null);
        return view;
    }

}
