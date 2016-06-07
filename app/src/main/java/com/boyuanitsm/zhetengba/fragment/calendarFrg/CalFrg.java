package com.boyuanitsm.zhetengba.fragment.calendarFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CalAdapter;
import com.boyuanitsm.zhetengba.adapter.MyPageAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.ScheduleInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.loopview.LoopViewPager;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 档期界面
 * Created by xiaoke on 2016/4/24.
 */
public class CalFrg extends BaseFragment {
    private View view;
    private View viewHeader_calen;
    private PullToRefreshListView lv_calen;
    private LoopViewPager vp_loop_calen;
    private List<View> views = new ArrayList<View>();
    private LinearLayout.LayoutParams paramsL = new LinearLayout.LayoutParams(20, 20);
    private MyPageAdapter pageAdapter;
    private LinearLayout ll_point;
    private List<ScheduleInfo> list=new ArrayList<>();
    private List<ScheduleInfo> datas=new ArrayList<>();
    private List<LabelBannerInfo> bannerInfoList;
    private CalAdapter adapter;
    private int page=1,rows=10;
    private boolean flag=true;
    private IntentFilter filterFriend,filterAll;
    //    广播接收者更新档期数据
    private BroadcastReceiver calFriendChangeRecevier=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //仿照活动
            getFriendAllSchudle(page, rows, 0 + "");//切换到好友；
            flag=false;
        }
    };
    private BroadcastReceiver calAllChangeRecevier=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //仿照活动
            getScheduleList(page, rows);//切换到全部；
            flag=false;
        }
    };


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.calendar_frag, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //广播接收者，接受好友列表更新数据
        filterFriend=new IntentFilter();
        filterFriend.addAction("calendFriendDateChange");
        mActivity.registerReceiver(calFriendChangeRecevier, filterFriend);//切换到好友；
        //接受全部更新列表；
        filterAll=new IntentFilter();
        filterAll.addAction("calendAllDateChange");
        mActivity.registerReceiver(calAllChangeRecevier, filterAll);//切换到全部；
        //塞入item_loop_viewpager_calen，到viewpager   :view1
        viewHeader_calen = getLayoutInflater(savedInstanceState).inflate(R.layout.item_viewpager_act, null);
        lv_calen = (PullToRefreshListView) view.findViewById(R.id.lv_calen);
        //下拉刷新初始化
        LayoutHelperUtil.freshInit(lv_calen);
        //设置listview头部headview
        lv_calen.getRefreshableView().addHeaderView(viewHeader_calen);
        lv_calen.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                if (flag == false) {
                    getFriendAllSchudle(page, rows, 0 + "");//好友列表获取；
                } else {
                    getScheduleList(page, rows);//全部列表获取；
                }
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                if (flag == false) {
                    getFriendAllSchudle(page, rows, 0 + "");//好友列表获取；
                } else {
                    getScheduleList(page, rows);//全部列表获取；
                }
            }
        });
        if (flag){
            getScheduleList(page, rows);
        }else {
            getFriendAllSchudle(page, rows, 0 + "");
        }
        //档期轮播图片展示
        getScheduleBanner();

    }

    /***
     * 初始化viewpager适配器
     */

    private void initMyPageAdapter(List<LabelBannerInfo> list) {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new MyPageAdapter(mActivity,list);
            if (vp_loop_calen != null) {
                vp_loop_calen.setAdapter(pageAdapter);
            }

        } else {
            pageAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取档期列表；
     * @param page
     * @param rows
     */
    private void getScheduleList(final int page,int rows){
        RequestManager.getScheduleManager().getScheduleList(page, rows, new ResultCallback<ResultBean<DataBean<ScheduleInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_calen.onPullUpRefreshComplete();
                lv_calen.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<DataBean<ScheduleInfo>> response) {
                lv_calen.onPullUpRefreshComplete();
                lv_calen.onPullDownRefreshComplete();
                list = response.getData().getRows();
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
                if (datas != null || datas.size() > 0) {
                    if (adapter == null) {
                        //设置简约listview的条目
                        adapter = new CalAdapter(mActivity,datas);
                        lv_calen.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.update(datas);
                    }
                } else {
                    lv_calen.setHasMoreData(false);
                }


            }
        });
    }

    /**
     * 点击好友，全部回调
     * @param page
     * @param rows
     * @param state
     */
    private void getFriendAllSchudle(final int page,int rows,String state){
        RequestManager.getScheduleManager().getScheduleFriend(page, rows, state, new ResultCallback<ResultBean<DataBean<ScheduleInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<DataBean<ScheduleInfo>> response) {
                lv_calen.onPullUpRefreshComplete();
                lv_calen.onPullDownRefreshComplete();
                list = response.getData().getRows();
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
                if (datas != null || datas.size() > 0) {
                    if (adapter == null) {
                        //设置简约listview的条目
                        adapter = new CalAdapter(mActivity, datas);
                        lv_calen.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.update(datas);
                    }
                } else {
                    lv_calen.setHasMoreData(false);
                }

            }
        });
    }

    /**
     * 获取档期轮播图；
     */
    private void getScheduleBanner(){
        RequestManager.getScheduleManager().getScheduleBanner(new ResultCallback<ResultBean<List<LabelBannerInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<List<LabelBannerInfo>> response) {
                bannerInfoList = new ArrayList<LabelBannerInfo>();
                bannerInfoList = response.getData();
                vp_loop_calen = (LoopViewPager) view.findViewById(R.id.vp_loop_act);
                ll_point = (LinearLayout) view.findViewById(R.id.ll_point);
                //设置viewpager适配/轮播效果
                initMyPageAdapter(bannerInfoList);
                vp_loop_calen.setAuto(true);
                //设置监听
                vp_loop_calen.setOnPageChangeListener(getListener());
            }
        });
    }


    /***
     * 初始化点
     */
    private void initPoint() {
        views.clear();
        ll_point.removeAllViews();
        for (int i = 0; i < 3; i++) {
            View view = new View(mActivity);
            paramsL.setMargins(ZhetebaUtils.dip2px(mActivity, 5), 0, 0, 0);
            view.setLayoutParams(paramsL);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.point_focus);
            } else {
                view.setBackgroundResource(R.drawable.point_normal);
            }

            views.add(view);
            ll_point.addView(view);
        }
    }
    /***
     * viewpager监听
     * @return
     */
    @NonNull
    private ViewPager.OnPageChangeListener getListener() {
        return new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (views.size() != 0 && views.get(position) != null) {

                    for (int i = 0; i < views.size(); i++) {
                        if (i == position) {
                            views.get(i).setBackgroundResource(R.drawable.point_focus);
                        } else {
                            views.get(i).setBackgroundResource(R.drawable.point_normal);
                        }
                    }

                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };
    }

}
