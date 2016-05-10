package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ChanAdapter;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道里
 * viewpager适配的fragment
 * Created by xiaoke on 2016/5/3.
 */
public class ChaChildFrg extends Fragment {
    private List<String> list = new ArrayList<String>();
    private int flag;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.frag_chanel_child01,null);
        initView(view);

        return view ;
    }

    private void initView(View view) {
        PullToRefreshListView lv_ch01 = (PullToRefreshListView) view.findViewById(R.id.lv_ch01);
        //传入参数，标签对应集合
        lv_ch01.setPullRefreshEnabled(true);//下拉刷新
        lv_ch01.setScrollLoadEnabled(true);//滑动加载
        lv_ch01.setPullLoadEnabled(false);//上拉刷新
        lv_ch01.setHasMoreData(true);//是否有更多数据
        lv_ch01.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        lv_ch01.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        lv_ch01.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
        lv_ch01.getRefreshableView().setDivider(null);
        ChanAdapter adapter=new ChanAdapter(getContext());
        lv_ch01.getRefreshableView().setAdapter(adapter);

    }
//    public void setData(List<ChanelInfo> info){
//
//        adapter.notifyDateCshange();
//    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            list = bundle.getStringArrayList("content");
            flag = bundle.getInt("flag");
        }
    }
    public static ChaChildFrg newInstance(List<String> contentList,int flag){
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("content", (ArrayList<String>) contentList);
        bundle.putInt("flag", flag);
        ChaChildFrg testFm = new ChaChildFrg();
        testFm.setArguments(bundle);
        return testFm;

    }
}
