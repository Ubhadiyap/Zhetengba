package com.boyuanitsm.zhetengba.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

/**
 * 子界面-圈子界面
 * Created by xiaoke on 2016/5/2.
 */
public class CirFrg extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cir_frg, null);
        PullToRefreshListView lv_cir = (PullToRefreshListView) view.findViewById(R.id.lv_cir);
        CircleAdapter adapter=new CircleAdapter(getContext());
        lv_cir.setPullRefreshEnabled(true);//下拉刷新
        lv_cir.setScrollLoadEnabled(true);//滑动加载
        lv_cir.setPullLoadEnabled(false);//上拉刷新
        lv_cir.setHasMoreData(true);//是否有更多数据
        lv_cir.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        lv_cir.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        lv_cir.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
        lv_cir.getRefreshableView().setDivider(null);
        lv_cir.getRefreshableView().setAdapter(adapter);
        return view;
    }
}
