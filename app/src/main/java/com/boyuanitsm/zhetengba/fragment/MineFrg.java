package com.boyuanitsm.zhetengba.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.MyColleitionAct;
import com.boyuanitsm.zhetengba.activity.PersonalmesAct;
import com.boyuanitsm.zhetengba.activity.SettingAct;
import com.boyuanitsm.zhetengba.activity.ShareqrcodeAct;
import com.boyuanitsm.zhetengba.adapter.MonthSelectAdp;
import com.boyuanitsm.zhetengba.adapter.RecycleviewAdp;
import com.boyuanitsm.zhetengba.adapter.TimeAxisListAdp;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我的
 * Created by liwk on 2016/5/6.
 */
public class MineFrg extends BaseFragment{
    @ViewInject(R.id.lv_timeAxis)
    private ListView lvTimeAxis;
    @ViewInject(R.id.rv_label)
    private RecyclerView rvLabel;
    @ViewInject(R.id.rv_monthSelect)
    private RecyclerView rvMonthSelect;
    private List<String> myDatas;
    private RecycleviewAdp recycleviewAdp;
    private MonthSelectAdp monthSelectAdp;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_mine,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        myDatas = new ArrayList<String>(Arrays.asList("吃货", "不正经", "逗比", "乐观主义", "爱好摄影","hahhah","g"));
        TimeAxisListAdp timeAxisListAdp = new TimeAxisListAdp(getActivity());
        lvTimeAxis.setDivider(null);
        lvTimeAxis.setAdapter(timeAxisListAdp);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //设置横向
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvLabel.setLayoutManager(linearLayoutManager);

        //标签recyclerview
        //设置适配器
        recycleviewAdp = new RecycleviewAdp(getContext(), myDatas);
        //点击更多跳转标签管理页面
        recycleviewAdp.setOnItemClickListener(new RecycleviewAdp.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),"点击了...",Toast.LENGTH_SHORT).show();
            }
        });
        rvLabel.setAdapter(recycleviewAdp);

        //月份RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMonthSelect.setLayoutManager(layoutManager);
        monthSelectAdp = new MonthSelectAdp(getContext());
        monthSelectAdp.setOnItemClickListener(new MonthSelectAdp.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),"选择了月份...",Toast.LENGTH_SHORT).show();
            }
        });
        rvMonthSelect.setAdapter(monthSelectAdp);
    }
    @OnClick({R.id.iv_shareCode,R.id.iv_setting,R.id.iv_headIcon,R.id.iv_collection})
    public void todo(View view){
        switch (view.getId()){
            case R.id.iv_shareCode://二维码
                openActivity(ShareqrcodeAct.class);
                break;
            case R.id.iv_setting://设置
                openActivity(SettingAct.class);
                break;
            case R.id.iv_headIcon://头像
                openActivity(PersonalmesAct.class);
                break;
            case R.id.iv_collection://我的收藏
                openActivity(MyColleitionAct.class);
                break;
        }
    }
}
