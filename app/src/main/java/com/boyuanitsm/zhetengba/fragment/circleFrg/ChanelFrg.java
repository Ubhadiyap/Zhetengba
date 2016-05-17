package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.LabelManaAct;
import com.boyuanitsm.zhetengba.adapter.ChaPagerAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;

import java.util.ArrayList;

/**
 * 频道界面
 * Created by xiaoke on 2016/5/2.
 */
public class ChanelFrg extends BaseFragment implements ViewPager.OnPageChangeListener {
    private View view;//当前view
    private ViewPager vp_chan;//viewpager
    private LinearLayout ll_add;//添加textview；
    private HorizontalScrollView scrollView;//scrollView
    private LinearLayout titleLayout;//频道，头部标签布局
    private int mTitleMargin;//头部标签之间空隙；
    private ChaPagerAdapter chaPagerAdapter;//viewpager适配器
    private ArrayList<ChaChildFrg> fragmentList;//viewpager嵌套fragment，将fragment装入fragmentlist集合内
    private ArrayList<TextView> textViewList;//承载标签的TextView集合
    private ArrayList<String> titleList;//标签集合
    private ArrayList<Integer> moveToList;//设置textview宽高集合
    private ArrayList<String> contentList;
    private ChaChildFrg chaChildFrg;//子fragment01
    private int currentPos;//当前位置
    private int j = 0;
    private String[] strList = new String[]{"足球", "篮球", "篮球", "旅游", "活动"};//标签
    private int[] idList = new int[]{0, 1, 2, 3, 4};//与标签对应id


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.chanel_frg, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //初始化控件
        scrollView = (HorizontalScrollView) view.findViewById(R.id.hslv_chanel);
        titleLayout = (LinearLayout) view.findViewById(R.id.titleLayout);
        vp_chan = (ViewPager) view.findViewById(R.id.vp_chan);
        ll_add = (LinearLayout) view.findViewById(R.id.ll_add);
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(LabelManaAct.class);
            }
        });
        //设置间隙
        mTitleMargin = dip2px(mActivity, 10);
        //填充数据
        initDate();
        //设置viewPager滑动监听
        vp_chan.setOnPageChangeListener(this);
    }


    /***
     * 填充数据
     */
    private void initDate() {
        //初始化
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        textViewList = new ArrayList<>();
        moveToList = new ArrayList<>();
        //设置fragmentlist
        //填充titleList,titleLayout布局
        for (int i = 0; i < strList.length; i++) {
            ChaChildFrg testFm = new ChaChildFrg().newInstance(contentList, i);
            fragmentList.add(testFm);
            titleList.add(strList[i]);
            addTitleLayout(titleList.get(i), idList[i]);
        }
        //设置viewpager适配数据
        chaPagerAdapter = new ChaPagerAdapter(getChildFragmentManager(), fragmentList);
        chaPagerAdapter.setFragments(fragmentList);
        vp_chan.setAdapter(chaPagerAdapter);
        vp_chan.setOffscreenPageLimit(9);//一共加载9页，如果此处不指定，默认只加载相邻页，提前加载增加用户体验
        textViewList.get(0).setTextColor(Color.parseColor("#52C791"));//默认加载项，标签文字对应变色
        currentPos = 0;

    }

    /***
     * 填充titleLayout
     *
     * @param title
     * @param position
     */

    private void addTitleLayout(String title, int position) {
        //塞入条目
        final TextView textView = (TextView) mActivity.getLayoutInflater().inflate(R.layout.chanel_child_title, null);
        //设置title
        textView.setText(title);
        //设置position Tag
        textView.setTag(position);
        //点击监听
        textView.setOnClickListener(new posOnClickListener());
        //LinearLayout管理器布局
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置左右间隙
        params.leftMargin = dip2px(mActivity, mTitleMargin);
        params.rightMargin = dip2px(mActivity, mTitleMargin);
        //将textView添加至params
        titleLayout.addView(textView, params);
        //把textView加入集合
        textViewList.add(textView);
        //设置宽高
        int width;
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

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /***
     * 滑动监听
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //当前位置textview 文字选中变色
        textViewList.get(currentPos).setTextColor(Color.parseColor("#666666"));
        textViewList.get(position).setTextColor(Color.parseColor("#52C791"));
        currentPos = position;
        scrollView.scrollTo((int) moveToList.get(position), 0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class posOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if ((int) view.getTag() == currentPos) {
                return;
            }
            textViewList.get(currentPos).setTextColor(Color.parseColor("#666666"));
            currentPos = (int) view.getTag();
            textViewList.get(currentPos).setTextColor(Color.parseColor("#52C791"));
            vp_chan.setCurrentItem(currentPos);
        }
    }

}
