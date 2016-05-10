package com.boyuanitsm.zhetengba.fragment.calendarFrg;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.adapter.CalAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

/**
 * 档期界面
 * Created by xiaoke on 2016/4/24.
 */
public class CalFrg extends Fragment {
    private View view;
    private View viewHeader_calen;
    private PullToRefreshListView lv_calen;
    private ImageView vp_loop_calen;
    private int calgznum=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calendar_frag, null);
        //塞入item_loop_viewpager_calen，到viewpager   :view1
        viewHeader_calen = getLayoutInflater(savedInstanceState).inflate(R.layout.item_loop_viewpager_calen, null);
        lv_calen = (PullToRefreshListView) view.findViewById(R.id.lv_calen);
        CalAdapter adapter = new CalAdapter(getActivity());

        lv_calen.setPullRefreshEnabled(true);//下拉刷新
        lv_calen.setScrollLoadEnabled(true);//滑动加载
        lv_calen.setPullLoadEnabled(false);//上拉刷新
        lv_calen.setHasMoreData(true);//是否有更多数据
        lv_calen.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        lv_calen.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        lv_calen.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
        lv_calen.getRefreshableView().setDivider(null);
        //设置listview头部headview
        lv_calen.getRefreshableView().addHeaderView(viewHeader_calen);
        vp_loop_calen = (ImageView) view.findViewById(R.id.vp_loop_calen);
        ImageView iv_item_image = (ImageView) view.findViewById(R.id.iv_item_image);
        iv_item_image.setImageResource(R.drawable.test_banner);
        lv_calen.getRefreshableView().setAdapter(adapter);
        return view;
    }

}
