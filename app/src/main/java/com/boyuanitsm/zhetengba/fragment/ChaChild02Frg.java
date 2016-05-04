package com.boyuanitsm.zhetengba.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ChanAdapter;

/**
 * 频道里
 * viewpager适配的fragment
 * Created by xiaoke on 2016/5/3.
 */
public class ChaChild02Frg extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.frag_chanel_child02,null);
        ListView lv_ch02 = (ListView) view.findViewById(R.id.lv_ch02);
        ChanAdapter adapter=new ChanAdapter(getContext());
        lv_ch02.setAdapter(adapter);
        return view ;
    }
}
