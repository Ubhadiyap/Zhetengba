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
    private int currentPos;//当前位置
    private int pos = 0;//返回的位置
    private int mTitleMargin;//头部标签之间空隙；
    private int page = 1;
    private int rows = 10;
    private View view;//当前view
    private PullToRefreshListView vp_chan;//viewpager
    private ChanAdapter adapter;
    private LinearLayout titleLayout;//频道，头部标签布局
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
        titleLayout = (LinearLayout) view.findViewById(R.id.titleLayout);
//        vp_chan = (PullToRefreshListView) view.findViewById(R.id.vp_chan);
        viewPager = (ViewPager) view.findViewById(R.id.vp_chanel);
        hslv_chanel = (BounceScrollView) view.findViewById(R.id.hslv_chanel);
    }


    private int preItem = 0;

    /***
     * 填充数据
     */
    private void initDate(final List<UserInterestInfo> titleList) {
        MyLogUtils.info(currentPos + "刷新位置信息。。。。");
        textViewList = new ArrayList<>();
        moveToList = new ArrayList<>();
        fragments = new ArrayList<>();
        if (titleList.size() <= currentPos) {
            currentPos = 0;
        } else {
            currentPos = pos;
        }
        if (titleList.size() > 0) {
            ChanelPageAdapter pageAdapter = new ChanelPageAdapter(getChildFragmentManager(), mActivity, titleList, currentPos);
            viewPager.setAdapter(pageAdapter);
            for (int i = 0; i < titleList.size(); i++) {
                addTitleLayout(titleList.get(i).getDictName(), i);
            }
            if (textViewList != null && textViewList.size() > 0) {
                textViewList.get(currentPos).setTextColor(Color.parseColor("#52C791"));//默认加载项，标签文字对应变色
            }
            viewPager.setCurrentItem(currentPos);//默认加载条目
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {//滑动监听
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    //当前位置textview 文字选中变色
                    textViewList.get(currentPos).setTextColor(Color.parseColor("#999999"));
                    textViewList.get(i).setTextColor(Color.parseColor("#52C791"));
                    currentPos = i;
                    if (i == preItem) {
                        //不作处理
                        MyLogUtils.degug("没有移动");
                        return;
                    }
                    if (i > preItem) {
                        //从左向右滑
                        MyLogUtils.degug("从左向右滑");
                        if (i > titleList.size()/2) {
                            hslv_chanel.scrollTo((int) moveToList.get(i), 0);
                        }
                        preItem = i;
                        return;
                    }
                    if (i < preItem) {
                        //从右向左滑
                        if (i < titleList.size()/2) {
                            hslv_chanel.scrollTo((int) moveToList.get(i), 0);
                        }
                        MyLogUtils.degug("从右向左滑");
                        preItem = i;
                        return;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }

    /***
     * 填充titleLayout
     *
     * @param title
     * @param position
     */

    private void addTitleLayout(String title, int position) {
        final TextView textView = (TextView) mActivity.getLayoutInflater().inflate(R.layout.chanel_child_title, null);
        textView.setText(title);
        textView.setTextSize(14);
        textView.setTextColor(Color.parseColor("#999999"));
        textView.setTag(position);//设置position Tag
        MyLogUtils.info("title：位置" + title + ":" + position);
        textView.setOnClickListener(new posOnClickListener());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mTitleMargin = ZtinfoUtils.dip2px(mActivity, 5);        //设置左右间隙
        params.leftMargin = ZtinfoUtils.dip2px(mActivity, mTitleMargin);
        params.rightMargin = ZtinfoUtils.dip2px(mActivity, mTitleMargin);
        titleLayout.addView(textView, params);
        textViewList.add(textView);
        int width;        //设置宽高
        if (position == 0) {
            width = 0;
            moveToList.add(width);
        } else {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            textViewList.get(position - 1).measure(w, h);
            width = textViewList.get(position - 1).getMeasuredWidth() + mTitleMargin * 4;
            moveToList.add(width + moveToList.get(moveToList.size() - 1));
        }
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

    class posOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            MyLogUtils.degug("传过来的位置：" + currentPos + "====点击位置：" + (int) view.getTag());
            if ((int) view.getTag() == currentPos) {
                return;
            }
            textViewList.get(currentPos).setTextColor(Color.parseColor("#999999"));
            currentPos = (int) view.getTag();
            page = 1;
            rows = 10;
            viewPager.setCurrentItem(currentPos);
            textViewList.get(currentPos).setTextColor(Color.parseColor("#52C791"));
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
