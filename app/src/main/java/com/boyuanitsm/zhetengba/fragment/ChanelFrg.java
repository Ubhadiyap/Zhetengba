package com.boyuanitsm.zhetengba.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.HorizontalListViewAdapter;
import com.boyuanitsm.zhetengba.view.HorizontalListView;

/**
 * 频道界面
 * Created by xiaoke on 2016/5/2.
 */
public class ChanelFrg extends Fragment {
    private View view;
    private final String[] strs={"足球","篮球","聚餐","旅行","动漫","咖啡","旅行","动漫","咖啡"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.chanel_frg,null);
        //设置listview的适配器
        HorizontalListView lv_chanel = (HorizontalListView) view.findViewById(R.id.lv_chanel);
        HorizontalListViewAdapter adapter=new HorizontalListViewAdapter(getContext(),strs);
        lv_chanel.setAdapter(adapter);
        return view;
    }
}
