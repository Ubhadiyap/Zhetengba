package com.boyuanitsm.zhetengba.activity.circle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.LabelMangerAct;
import com.boyuanitsm.zhetengba.adapter.ChanelPageAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ACache;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.view.bounScrollView.BounceScrollView;
import com.boyuanitsm.zhetengba.view.bounScrollView.ViewPagerIndicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 广场界面
 * Created by xiaoke on 2016/8/11.
 */
public class SquareAct extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ivRight)
    private ImageView ivRight;
    @ViewInject(R.id.titleLayout)
    private ViewPagerIndicator titleLayout;//频道，头部标签布局
    @ViewInject(R.id.vp_chanel)
    private ViewPager viewPager;
    @ViewInject(R.id.hslv_chanel)
    private BounceScrollView hslv_chanel;
    private int currentPos=0;//当前位置
    private ACache aCache;
    private List<UserInterestInfo> titleList;//标签集合
//    private ChanelPageAdapter pageAdapter;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_square);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("广场");
        aCache = ACache.get(SquareAct.this);
        getMyLabels(-1);
    }


    @OnClick({R.id.ll_add,R.id.ivRight})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRight:
                Intent intent = new Intent(SquareAct.this, CirclefbAct.class);
                intent.putExtra(CirclefbAct.TYPE, 0);
                intent.putExtra("labelId", titleList.get(currentPos).getInterestId());
                intent.putExtra("flag", currentPos);
                MyLogUtils.info("发布时的位置：====" + currentPos);
                startActivity(intent);
                break;
//            case R.id.bt_plan:
//                Intent intent = new Intent(SquareAct.this, CirclefbAct.class);
//                intent.putExtra(CirclefbAct.TYPE, 0);
//                intent.putExtra("labelId", titleList.get(currentPos).getInterestId());
//                intent.putExtra("flag", currentPos);
//                MyLogUtils.info("发布时的位置：====" + currentPos);
//                startActivity(intent);
//                break;
            case R.id.ll_add:
                openActivity(LabelMangerAct.class);
                break;
        }
    }
    /***
     * 填充数据
     */
    private void initDate(final List<UserInterestInfo> titleList) {
        titleLayout.setTabItemTitles(titleList);
//        if (pageAdapter==null){
          ChanelPageAdapter  pageAdapter = new ChanelPageAdapter(getSupportFragmentManager(), SquareAct.this, titleList, currentPos);
            viewPager.setAdapter(pageAdapter);
//        }else {
//            pageAdapter.updata(titleList);
//        }
        titleLayout.setViewPager(viewPager, hslv_chanel, currentPos);
        titleLayout.setOnPageChangeListener(new ViewPagerIndicator.PageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
            registerReceiver(receiverTalk, new IntentFilter(MYLABELS));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiverTalk != null) {
            unregisterReceiver(receiverTalk);
            receiverTalk = null;
        }
    }

    private MyBroadCastReceiverTalk receiverTalk;
    public static final String MYLABELS = "labels_update";

    private class MyBroadCastReceiverTalk extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (titleLayout != null)
                titleLayout.removeAllViews();
            if (titleList != null)
                titleList.clear();
//            if (intent != null) {
//                Bundle bundle = intent.getExtras();
//                if (bundle != null) {
//                    currentPos = bundle.getInt("flag", currentPos);
//                }
//            }
            getMyLabels(-1);
        }
    }
}
