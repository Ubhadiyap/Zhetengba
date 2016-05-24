package com.boyuanitsm.zhetengba.fragment.calendarFrg;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.BannerInfo;
import com.boyuanitsm.zhetengba.util.ZhetebaUtils;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.loopview.LoopViewPager;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.net.URL;
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
    private List<BannerInfo> bannerInfoList;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.act_frag, null, false);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewHeader_act = getLayoutInflater(savedInstanceState).inflate(R.layout.item_viewpager_act, null);
        lv_act = (PullToRefreshListView) view.findViewById(R.id.lv_act);
        //刷新初始化
        LayoutHelperUtil.freshInit(lv_act);
        //设置简约listview的headerview：item_viewpager_act.xml
        lv_act.getRefreshableView().addHeaderView(viewHeader_act);
        //设置简约listview的条目
        adapter = new ActAdapter(mActivity);
        lv_act.getRefreshableView().setAdapter(adapter);
        viewPager = (LoopViewPager) view.findViewById(R.id.vp_loop_act);
        ll_point = (LinearLayout) view.findViewById(R.id.ll_point);
        //设置viewpager适配/轮播效果
        initMyPageAdapter();
        viewPager.setAuto(true);
        //设置监听
        viewPager.setOnPageChangeListener(getListener());
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
     */

    private void initMyPageAdapter() {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new MyPageAdapter();
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
            //            return  bannerInfoList==null?0:bannerInfoList.size();
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
            //            ImageLoader.getInstance().displayImage(UrlManager.getPicFullUrl(bannerInfoList.get(position).getBannerLink()),iv_iamge,optionsImag);
            //UrlManager,网络地址管理类

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


}
