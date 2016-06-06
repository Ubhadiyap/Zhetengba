package com.boyuanitsm.zhetengba.fragment.calendarFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CalAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.ScheduleInfo;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.loopview.LoopViewPager;
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
    private int calgznum = 0;
    private List<View> views = new ArrayList<View>();
    private LinearLayout.LayoutParams paramsL = new LinearLayout.LayoutParams(20, 20);
    private MyPageAdapter pageAdapter;
    private LinearLayout ll_point;
    private List<ScheduleInfo> list=new ArrayList<>();
    private List<ScheduleInfo> datas=new ArrayList<>();
//    广播接收者更新档期数据
    private BroadcastReceiver calDateChangeRecevier=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //仿照活动
        }
    };

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.calendar_frag, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //塞入item_loop_viewpager_calen，到viewpager   :view1
        viewHeader_calen = getLayoutInflater(savedInstanceState).inflate(R.layout.item_viewpager_act, null);
        lv_calen = (PullToRefreshListView) view.findViewById(R.id.lv_calen);
        CalAdapter adapter = new CalAdapter(mActivity);
        //下拉刷新初始化
        LayoutHelperUtil.freshInit(lv_calen);
        //设置listview头部headview
        lv_calen.getRefreshableView().addHeaderView(viewHeader_calen);
        vp_loop_calen = (LoopViewPager) view.findViewById(R.id.vp_loop_act);
         ll_point = (LinearLayout) view.findViewById(R.id.ll_point);
        //设置viewpager适配/轮播效果
        initMyPageAdapter();
        vp_loop_calen.setAuto(true);
//        ImageView iv_item_image = (ImageView) view.findViewById(R.id.iv_item_image);
//        iv_item_image.setImageResource(R.drawable.test_banner);
        lv_calen.getRefreshableView().setAdapter(adapter);
        vp_loop_calen.setOnPageChangeListener(getListener());
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
    /***
     * 初始化viewpager适配器
     */

    private void initMyPageAdapter() {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new MyPageAdapter();
            if (vp_loop_calen != null) {
                vp_loop_calen.setAdapter(pageAdapter);
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

    /***
     * viewpageradapter
     */
    private class MyPageAdapter extends PagerAdapter {
        // 图片缓存 默认 等
        private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.zanwutupian)
                .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View view = View.inflate(mActivity, R.layout.item_loop_viewpager_act, null);

            ImageView iv_iamge = (ImageView) view.findViewById(R.id.iv_item_image);
            //加载图片地址
            iv_iamge.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.test_banner));
//            ImageLoader.getInstance().displayImage(
//                    UrlManager.getPicFullUrl(bannerInfoList.get(position).getBannerPic()), iv_iamge,
//                    optionsImag);

//            iv_iamge.setBackgroundResource(newsPictures[position]);

            ((ViewPager) container).addView(view);

            iv_iamge.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });

            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

    }

//    private void getScheduleList(final int page,int rows){
//        RequestManager.getScheduleManager().getScheduleList(page, rows, new ResultCallback<ResultBean<List<ScheduleInfo>>>() {
//            @Override
//            public void onError(int status, String errorMsg) {
//
//            }
//
//            @Override
//            public void onResponse(ResultBean<List<ScheduleInfo>> response) {
//                lv_calen.onPullUpRefreshComplete();
//                lv_calen.onPullDownRefreshComplete();
//                list = response.getData();
//                if (page == 1) {
//                    datas.clear();
//                }
//                datas.addAll(list);
//                if (list != null || list.size() > 0) {
//                    if (adapter == null) {
//                        //设置简约listview的条目
//                        adapter = new ActAdapter(mActivity, list);
//                        lv_act.getRefreshableView().setAdapter(adapter);
//                    } else {
//                        adapter.update(datas);
//                    }
//                } else {
//                    lv_act.setHasMoreData(false);
//                }
//
//
//            }
//        });
//    }

}
