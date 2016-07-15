package com.boyuanitsm.zhetengba.fragment.calendarFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.adapter.MyPageAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.loopview.LoopViewPager;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 会友界面
 * Created by xiaoke on 2016/4/24.
 */
public class SimpleFrg extends BaseFragment {
    private PullToRefreshListView lv_act;
    private View view;
    private View viewHeader_act;
    private ActAdapter adapter;
    private LoopViewPager viewPager;
    private MyPageAdapter pageAdapter;
    private LinearLayout ll_point;
    private List<View> views = new ArrayList<View>();
    private LinearLayout.LayoutParams paramsL = new LinearLayout.LayoutParams(20, 20);
    private List<LabelBannerInfo> bannerInfoList;
    private List<SimpleInfo> list;//活动对象集合
    private List<SimpleInfo> datas = new ArrayList<>();
    private int page = 1;
    private int rows = 20;
    private int state=1;
    private IntentFilter filter;
    private LinearLayout noList;
    private ImageView ivAnim;
    private TextView noMsg;
    private AnimationDrawable animationDrawable;
    private BroadcastReceiver DteChangeRecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            page = 1;
          state=intent.getIntExtra("state",state);
            if (state==1){
                getActivityList(page,rows);
            }else {
                getFriendOrAllAcitvity(page, rows, state + "");//切换到好友；
            }
        }
    };
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.act_frag, null, false);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewHeader_act = getLayoutInflater(savedInstanceState).inflate(R.layout.item_viewpager_act, null);
        lv_act = (PullToRefreshListView) view.findViewById(R.id.lv_act);
         noList = (LinearLayout) view.findViewById(R.id.noList);
        ivAnim = (ImageView) view.findViewById(R.id.ivAnim);
        noMsg = (TextView) view.findViewById(R.id.noMsg);
        //刷新初始化
        LayoutHelperUtil.freshInit(lv_act);
        lv_act.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                if (state==0) {
                    getFriendOrAllAcitvity(page, rows, state + "");//好友列表获取；
                } else if (state==1){
                    getActivityList(page, rows);//全部列表获取；
                }else if (state==2) {
                    getFriendOrAllAcitvity(page,rows,state+"");//获取我的列表
                }
//                getBanner();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                if (state==0) {
                    getFriendOrAllAcitvity(page, rows, state + "");//好友列表获取；
                } else if (state==1){
                    getActivityList(page, rows);//全部列表获取；
                }else if (state==2){
                    getFriendOrAllAcitvity(page,rows,state+"");//获取我的
                }
//                getBanner();
            }
        });
        //设置简约listview的headerview：item_viewpager_act.xml
        lv_act.getRefreshableView().addHeaderView(viewHeader_act);
        if (state==1) {
            getActivityList(page, rows);
        } else if (state==0){
            getFriendOrAllAcitvity(page, rows, state + "");
        }else if (state==2){
            getFriendOrAllAcitvity(page,rows,state+"");
        }
        //首页活动轮播图片展示
        getBanner();

    }

    public static final String DATA_CHANGE_KEY="data_change_fragment";
    @Override
    public void onStart() {
        //广播接收者，接受好友列表更新数据
        filter = new IntentFilter();
        filter.addAction(DATA_CHANGE_KEY);
        mActivity.registerReceiver(DteChangeRecevier, filter);//切换到好友；
        super.onStart();
    }

    /***
     * viewpager监听
     *
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

    /***
     * 初始化viewpager适配器
     *
     * @param bannerInfoList
     */

    private void initMyPageAdapter(List<LabelBannerInfo> bannerInfoList) {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new MyPageAdapter(mActivity, bannerInfoList);
            if (viewPager != null) {
                viewPager.setAdapter(pageAdapter);
            }

        } else {
            pageAdapter.notifyDataSetChanged();
        }
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(DteChangeRecevier);//注销广播

    }

    /***
     * 获取活动首页轮播图
     */
    private void getBanner() {
        RequestManager.getScheduleManager().getBanner(new ResultCallback<ResultBean<List<LabelBannerInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
//                noList.setVisibility(View.VISIBLE);
//                ivAnim.setImageResource(R.drawable.loadfail_list);
//                animationDrawable = (AnimationDrawable) ivAnim.getDrawable();
//                animationDrawable.start();
//                noMsg.setText("加载失败...");
            }

            @Override
            public void onResponse(ResultBean<List<LabelBannerInfo>> response) {
//                if (animationDrawable!=null){
//                    animationDrawable.stop();
//                    animationDrawable=null;
//                    noList.setVisibility(View.GONE);
//                }
                bannerInfoList = new ArrayList<LabelBannerInfo>();
                bannerInfoList = response.getData();
                viewPager = (LoopViewPager) view.findViewById(R.id.vp_loop_act);
                ll_point = (LinearLayout) view.findViewById(R.id.ll_point);
                //设置viewpager适配/轮播效果
                initMyPageAdapter(bannerInfoList);
                viewPager.setAuto(true);
                //设置监听
                viewPager.setOnPageChangeListener(getListener());
            }
        });
    }

    /***
     * 获取活动列表
     *
     * @param page
     * @param row
     */
    private void getActivityList(final int page, int row) {
        list = new ArrayList<SimpleInfo>();
        RequestManager.getScheduleManager().getActivityList(page, row, new ResultCallback<ResultBean<DataBean<SimpleInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_act.onPullUpRefreshComplete();
                lv_act.onPullDownRefreshComplete();
                if (adapter!=null){
                    adapter.update(list);
                }
                noList.setVisibility(View.VISIBLE);
                ivAnim.setImageResource(R.drawable.loadfail_list);
                animationDrawable = (AnimationDrawable) ivAnim.getDrawable();
                animationDrawable.start();
                noMsg.setText("加载失败...");
            }

            @Override
            public void onResponse(ResultBean<DataBean<SimpleInfo>> response) {
                if (animationDrawable!=null){
                    animationDrawable.stop();
                    animationDrawable=null;
                    noList.setVisibility(View.GONE);
                }
                lv_act.onPullUpRefreshComplete();
                lv_act.onPullDownRefreshComplete();
                list = response.getData().getRows();
                if (list.size() == 0) {
                    if (page == 1) {
//                        adapter.update(list);
                    } else {
                        lv_act.setHasMoreData(false);
                    }
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
                if (adapter == null) {
                    //设置简约listview的条目
                    adapter = new ActAdapter(mActivity, datas);
                    lv_act.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.update(datas);
                }


            }
        });

    }

    /***
     * 活动信息：好友/全部显示
     *
     * @param page
     * @param rows
     * @param state
     */
    private void getFriendOrAllAcitvity(final int page, int rows, String state) {
        list=new ArrayList<SimpleInfo>();
        RequestManager.getScheduleManager().getFriendOrAllActivity(page, rows, state, new ResultCallback<ResultBean<DataBean<SimpleInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_act.onPullUpRefreshComplete();
                lv_act.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<DataBean<SimpleInfo>> response) {
                lv_act.onPullUpRefreshComplete();
                lv_act.onPullDownRefreshComplete();
                list = response.getData().getRows();
                //获取到的list没有数据时
                if (list.size() == 0) {
                    if (page == 1) {
                        adapter.update(list); //第一页就没有数据，表示全部无数据
                    } else {
                        //无更多数据
                        lv_act.setHasMoreData(false);
                    }
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
                if (adapter == null) {
                    //设置简约listview的条目
                    adapter = new ActAdapter(mActivity, datas);
                    lv_act.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.update(datas);
                }

            }
        });
    }

}
