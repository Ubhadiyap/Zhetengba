package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.CirclefbAct;
import com.boyuanitsm.zhetengba.activity.mine.LabelMangerAct;
import com.boyuanitsm.zhetengba.adapter.ChanAdapter;
import com.boyuanitsm.zhetengba.adapter.ChanelPageAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ACache;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.bounScrollView.BounceScrollView;
import com.boyuanitsm.zhetengba.view.bounScrollView.ViewPagerIndicator;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道界面
 * Created by xiaoke on 2016/5/2.
 */
public class ChanelFrg extends BaseFragment implements View.OnClickListener {
    private int currentPos=0;//当前位置
    private int pos = 0;//返回的位置
    private int mTitleMargin;//头部标签之间空隙；
    private int page = 1;
    private int rows = 10;
    private View view;//当前view
    private PullToRefreshListView vp_chan;//viewpager
    private ChanAdapter adapter;
    private ViewPagerIndicator titleLayout;//频道，头部标签布局
    private ArrayList<TextView> textViewList;//承载标签的TextView集合
    private List<UserInterestInfo> titleList;//标签集合
    private ArrayList<Integer> moveToList;//设置textview宽高集合
    private List<ChannelTalkEntity> channelTalkEntityList;
    private List<List<ImageInfo>> datalist;
    private List<ChanelItemFrg> fragments;
    private List<ChannelTalkEntity> datas = new ArrayList<>();
    //    private ChanelPageAdapter pageAdapter;
    private ViewPager viewPager;
    private BounceScrollView hslv_chanel;
    LinearLayout llnoList;
    ImageView ivAnim;
    TextView noMsg;
    private AnimationDrawable animationDrawable;
    private ACache aCache;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.chanel_frg, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();        //初始化控件
        aCache = ACache.get(mActivity);
        getMyLabels(-1);
        MyLogUtils.info(titleList.toString());
    }

    private void initView() {
        titleLayout = (ViewPagerIndicator) view.findViewById(R.id.titleLayout);
//        vp_chan = (PullToRefreshListView) view.findViewById(R.id.vp_chan);
        viewPager = (ViewPager) view.findViewById(R.id.vp_chanel);
        hslv_chanel = (BounceScrollView) view.findViewById(R.id.hslv_chanel);
    }


    private int preItem = 0;

    /***
     * 填充数据
     */
    private void initDate(final List<UserInterestInfo> titleList) {
        titleLayout.setTabItemTitles(titleList);
        ChanelPageAdapter pageAdapter = new ChanelPageAdapter(getChildFragmentManager(), mActivity, titleList, currentPos);
            viewPager.setAdapter(pageAdapter);
        titleLayout.setViewPager(viewPager, hslv_chanel, currentPos);
        titleLayout.setOnPageChangeListener(new ViewPagerIndicator.PageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPos=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @OnClick({R.id.bt_plan, R.id.ll_add})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_plan:
                Intent intent = new Intent(mActivity, CirclefbAct.class);
                intent.putExtra(CirclefbAct.TYPE, 0);
                intent.putExtra("labelId", titleList.get(currentPos).getInterestId());
                intent.putExtra("flag", currentPos);
                MyLogUtils.info("发布时的位置：====" + currentPos);
                startActivity(intent);
                break;
            case R.id.ll_add:
                openActivity(LabelMangerAct.class);
                break;
        }
    }


    /**
     * 获取我的兴趣标签
     *
     * @param limitNum
     */
    private void getMyLabels(int limitNum) {
        titleList = new ArrayList<>();
        RequestManager.getScheduleManager().selectMyLabels(null, limitNum, new ResultCallback<ResultBean<List<UserInterestInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                String str = aCache.getAsString("titleList");
                Gson gson = new Gson();
                titleList = gson.fromJson(str, new TypeToken<List<UserInterestInfo>>() {
                }.getType());
                initDate(titleList);
            }

            @Override
            public void onResponse(ResultBean<List<UserInterestInfo>> response) {
                titleList = response.getData();
                Gson gson = new Gson();
                aCache.put("titleList", gson.toJson(titleList));
                initDate(titleList);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (receiverTalk == null) {
            receiverTalk = new MyBroadCastReceiverTalk();
            mActivity.registerReceiver(receiverTalk, new IntentFilter(MYLABELS));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiverTalk != null) {
            mActivity.unregisterReceiver(receiverTalk);
            receiverTalk = null;
        }
    }

    private MyBroadCastReceiverTalk receiverTalk;
    public static final String MYLABELS = "labels_update";

    private class MyBroadCastReceiverTalk extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            page = 1;
            if (titleLayout != null)
                titleLayout.removeAllViews();
            if (textViewList != null)
                textViewList.clear();
            if (titleList != null)
                titleList.clear();
            if (datalist != null)
                datalist.clear();
            LogUtils.i("广播后" + titleList.size());
            if (intent != null) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    pos = bundle.getInt("flag", currentPos);
                }
            }
            getMyLabels(-1);
        }
    }

}
