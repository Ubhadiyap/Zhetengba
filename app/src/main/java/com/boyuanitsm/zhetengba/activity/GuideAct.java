package com.boyuanitsm.zhetengba.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.LoginAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.view.CirclePageIndicator;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 引导页
 * Created by wangbin on 16/6/21.
 */
public class GuideAct extends BaseActivity {
    @ViewInject(R.id.indicator)
    private CirclePageIndicator indicator;
    @ViewInject(R.id.pager)
    private ViewPager pager;
    private GuidePagerAdapter adapter;

    private final static int[] guideImages = {R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3};

    @Override
    public void setLayout() {
        setContentView(R.layout.act_guide);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        adapter = new GuidePagerAdapter();
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                 if(position==guideImages.length-1){
                     indicator.setVisibility(View.INVISIBLE);
                 }else{
                     indicator.setVisibility(View.VISIBLE);
                 }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    private class GuidePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {

            return guideImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {

            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(GuideAct.this, R.layout.guide_vp_item, null);

            TextView tv_next = (TextView) view.findViewById(R.id.tv_next);
            if (position == guideImages.length - 1) {
                tv_next.setVisibility(View.VISIBLE);
            } else {
                tv_next.setVisibility(View.GONE);
            }

            view.setBackgroundResource(guideImages[position]);
            tv_next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GuideAct.this, LoginAct.class);
                    startActivity(intent);
                    finish();

                }
            });

            ((ViewPager) container).addView(view);

            return view;
        }

    }
}
