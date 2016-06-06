package com.boyuanitsm.zhetengba.fragment.calendarFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.adapter.MyPageAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.BannerInfo;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.CustomDialog;
import com.boyuanitsm.zhetengba.view.loopview.LoopViewPager;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 简约界面
 * Created by xiaoke on 2016/4/24.
 */
public class SimpleFrg extends BaseFragment {
    private PullToRefreshListView lv_act;
    private View view;
    private ListView lv_calen;
    private View viewHeader_act;
    private ActAdapter adapter;
    private LoopViewPager viewPager;
    private MyPageAdapter pageAdapter;
    private LinearLayout ll_point;
    private List<View> views = new ArrayList<View>();
    private LinearLayout.LayoutParams paramsL = new LinearLayout.LayoutParams(20, 20);
    private List<LabelBannerInfo> bannerInfoList;
    private List<SimpleInfo> list;//活动对象集合
    private List<SimpleInfo> datas=new ArrayList<>();
    private int page=1;
    private int rows=10;
    private BroadcastReceiver simDteChangeRecevier=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                  getFriendOrAllAcitvity(1, 10, 0 + "");//切换到好友；

        }
    };
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.act_frag, null, false);
        return view;
    }
    @Override
    public void initData(Bundle savedInstanceState) {

        //广播接收者，更新数据
        IntentFilter filter=new IntentFilter();
        filter.addAction("simpleDateChange");
        mActivity.registerReceiver(simDteChangeRecevier, filter);

        viewHeader_act = getLayoutInflater(savedInstanceState).inflate(R.layout.item_viewpager_act, null);
        lv_act = (PullToRefreshListView) view.findViewById(R.id.lv_act);
        //刷新初始化
        LayoutHelperUtil.freshInit(lv_act);
        lv_act.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                getActivityList(page,rows);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getActivityList(page,rows);
            }
        });
        //设置简约listview的headerview：item_viewpager_act.xml
        lv_act.getRefreshableView().addHeaderView(viewHeader_act);
        getActivityList(page, rows);
        lv_act.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog();
            }
        });
        //首页轮播图片展示
        getBanner();

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
     * @param bannerInfoList
     */

    private void initMyPageAdapter(List<LabelBannerInfo> bannerInfoList) {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new MyPageAdapter(mActivity,bannerInfoList);
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




    private void showDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(mActivity);
        builder.setMessage("没有活动详情");
//        builder.setPositiveButton("加为好友", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                MyToastUtils.showShortToast(context,"点击了第一个button");
//            }
//        });
        builder.setNegativeButton("你们两个是同事", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyToastUtils.showShortToast(mActivity, "点击了第二个button");
            }
        });
        builder.create().show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(simDteChangeRecevier);//注销广播
    }

    /***
     * 获取首页轮播图
     */
    private void getBanner() {
        RequestManager.getScheduleManager().getBanner(new ResultCallback<ResultBean<List<LabelBannerInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<List<LabelBannerInfo>> response) {
                bannerInfoList=new ArrayList<LabelBannerInfo>();
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
     * @param page
     * @param row
     */
    private void getActivityList(final int page, int row) {
        RequestManager.getScheduleManager().getActivityList(page, row, new ResultCallback<ResultBean<DataBean<SimpleInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<DataBean<SimpleInfo>> response) {
                lv_act.onPullUpRefreshComplete();
                lv_act.onPullDownRefreshComplete();
                list = response.getData().getRows();
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
                if (list != null || list.size() > 0) {
                    if (adapter == null) {
                        //设置简约listview的条目
                        adapter = new ActAdapter(mActivity, list);
                        lv_act.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.update(datas);
                    }
                } else {
                    lv_act.setHasMoreData(false);
                }


            }
        });

    }

    /**
     * 获取活动详情
     *
     * @param activityId
     */
    private void getActivityDetials(String activityId) {
        RequestManager.getScheduleManager().getActivityDetials(activityId, new ResultCallback<ResultBean<SimpleInfo>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<SimpleInfo> response) {
                SimpleInfo simpleInfo = response.getData();
            }
        });
    }

    /***
     * 活动信息：好友/全部显示
     * @param page
     * @param rows
     * @param state
     */
    private void getFriendOrAllAcitvity(final int page,int rows,String state){
        RequestManager.getScheduleManager().getFriendOrAllActivity(page, rows, state, new ResultCallback<ResultBean<List<SimpleInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_act.onPullUpRefreshComplete();
                lv_act.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<List<SimpleInfo>> response) {
                lv_act.onPullUpRefreshComplete();
                lv_act.onPullDownRefreshComplete();
                list = response.getData();
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
                if (list != null || list.size() > 0) {
                    if (adapter == null) {
                        //设置简约listview的条目
                        adapter = new ActAdapter(mActivity, list);
                        lv_act.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.update(datas);
                    }
                } else {
                    lv_act.setHasMoreData(false);
                }


            }
        });
    }

}
