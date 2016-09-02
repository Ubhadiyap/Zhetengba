package com.boyuanitsm.zhetengba.activity.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.MonthSelectAdp;
import com.boyuanitsm.zhetengba.adapter.RecycleviewAdp;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.fragment.TimeFrg;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.CommonView;
import com.boyuanitsm.zhetengba.view.MyViewPager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 时间轴页面
 * Created by xiaoke on 2016/8/12.
 */
public class TimeHistoryAct extends BaseActivity implements ViewPager.OnPageChangeListener{
    @ViewInject(R.id.titleLayout)
    private LinearLayout titleLayout;
    @ViewInject(R.id.lv_timeAxis)
    private MyViewPager lvTimeAxis;//viewPager对象
    @ViewInject(R.id.rv_label)
    private RecyclerView rvLabel;//兴趣标签
    @ViewInject(R.id.iv_headIcon)
    private CircleImageView head;
    @ViewInject(R.id.tv_noLabel)
    private TextView tvNoLabel;
    @ViewInject(R.id.hslv_chanel)
    private HorizontalScrollView hslv_chanel;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.cv_time)
    private CommonView cv_time;
    @ViewInject(R.id.my_collect)
    private CommonView my_collect;
    @ViewInject(R.id.qb)
    private CommonView qb;
//    @ViewInject(R.id.cj)
//    private CommonView cj;
    @ViewInject(R.id.cv_set)
    private CommonView cv_set;
    private List<Integer> monthList;//设置时间集合
    private List<TextView> textViewList;//设置时间textview集合
    private ArrayList<Integer> moveToList;//设置textview宽高集合
    private RecycleviewAdp recycleviewAdp;
    private MonthSelectAdp monthSelectAdp;
    private int currentPos;//当前位置
    private int mMouthMargin;//设置月份间隙
    private List<String> timeList=new ArrayList<>();//存储时间
    private MyPagerAdapter adapter;
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImagb = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userb)
            .showImageOnFail(R.mipmap.userb).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    private DisplayImageOptions optionsImagg = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userg)
            .showImageOnFail(R.mipmap.userg).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    @Override
    public void setLayout() {
        setContentView(R.layout.frag_mine);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        if (!TextUtils.isEmpty(UserInfoDao.getUser().getPetName())){
            tv_name.setText(UserInfoDao.getUser().getPetName());
        }
        if (!TextUtils.isEmpty(UserInfoDao.getUser().getSex())&&UserInfoDao.getUser().getSex()!=null){
            if(UserInfoDao.getUser().getSex().equals("0")){//女孩
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(UserInfoDao.getUser().getIcon()),head,optionsImagg);

            }else {
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(UserInfoDao.getUser().getIcon()),head,optionsImagb);

            }

        }else {
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(UserInfoDao.getUser().getIcon()),head,optionsImagb);

        }
        if (titleLayout!=null){
            titleLayout.removeAllViews();
        }
        initLayout();
        MyPagerAdapter adapter= new MyPagerAdapter(getSupportFragmentManager());
        //设置viewpager标签适配器
        lvTimeAxis.setAdapter(adapter);
        lvTimeAxis.setOnPageChangeListener(this);
        currentPos =0;
        textViewList.get(0).setTextColor(Color.parseColor("#ff7e84"));//默认加载项，标签文字对应变色
        lvTimeAxis.setCurrentItem(0);
    }
    private void initLayout() {
        mMouthMargin= ZhetebaUtils.dip2px(TimeHistoryAct.this, 5);
        monthList = new ArrayList<>();
        textViewList = new ArrayList<>();
        moveToList=new ArrayList<>();
        monthList= getCurrenMonth();
        if (titleLayout!=null){
            titleLayout.removeAllViews();
        }
        //填充titleList,titleLayout布局
        for (int i = 0; i < monthList.size(); i++) {
            if((monthList.get(i)+"").length()==1){
                timeList.add("20160"+monthList.get(i));
            }else{
                timeList.add("2016"+monthList.get(i));
            }

            addTitleLayout(monthList.get(i).toString(),i);
        }
    }
    /**
     * 月份那个
     * @param month
     * @param position
     */
    private void addTitleLayout(String month, int position) {
        //塞入条目
        final TextView textView = (TextView)getLayoutInflater().inflate(R.layout.mine_child_title, null);
        //设置title
        textView.setTextSize(12);
        textView.setText(month + "月");
        textView.setTextColor(Color.parseColor("#58b487"));

        //设置position Tag
        textView.setTag(position);
        //点击监听
        textView.setOnClickListener(new posOnClickListener());
        //LinearLayout管理器布局
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置左右间隙
        params.leftMargin = ZhetebaUtils.dip2px(TimeHistoryAct.this, mMouthMargin);
        params.rightMargin = ZhetebaUtils.dip2px(TimeHistoryAct.this, mMouthMargin);
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
            width = textViewList.get(position - 1).getMeasuredWidth() + mMouthMargin * 4;
            moveToList.add(width + moveToList.get(moveToList.size() - 1));
        }
    }
    /***
     * 获取当前月份
     * @return
     */
    private List<Integer> getCurrenMonth(){
        int month = getCurrentMonth() ;
        for (int i=1;i<=month;i++){
            monthList.add(i);
        }
        Collections.reverse(monthList);
        return monthList;
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        //当前位置textview 文字选中变色
        textViewList.get(currentPos).setTextColor(Color.parseColor("#58b487"));
        textViewList.get(currentPos).setTextSize(12);
        textViewList.get(position).setTextColor(Color.parseColor("#ff7e84"));
        textViewList.get(position).setTextSize(14);
        currentPos = position;
        hslv_chanel.scrollTo((int) moveToList.get(position), 0);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /***
     * viewPager适配器
     */
    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            TimeFrg timeFrg=new TimeFrg();
            Bundle bundle=new Bundle();
            bundle.putString(TimeFrg.INPUT_TIME,timeList.get(i));
            timeFrg.setArguments(bundle);
            return timeFrg;
        }

        @Override
        public int getCount() {
            return timeList.size();
        }
    }

    /***
     * 内部类点击事件
     */
    class posOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if ((int) view.getTag() == currentPos) {
                return;
            }
            textViewList.get(currentPos).setTextSize(12);
            textViewList.get(currentPos).setTextColor(Color.parseColor("#58b487"));
            currentPos = (int) view.getTag();
            textViewList.get(currentPos).setTextColor(Color.parseColor("#ff7e84"));
//            textViewList.get(currentPos).setTextSize(14);
            lvTimeAxis.setCurrentItem(currentPos);
        }
    }
    public  Integer getCurrentMonth() {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return Integer.valueOf(format.format(new Date()));
    }

    private MyReceiver myReceiver;
    public static final String USER_INFO = "com.update.userinfo";

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            UserInfo user= UserInfoDao.getUser();
            String sex=user.getSex();
            MyLogUtils.degug(user.getPetName());
            if (user != null) {
                if (!TextUtils.isEmpty(user.getIcon())){
                    if(sex!=null&&!TextUtils.isEmpty(sex)&&sex.equals("0")){
                        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(UserInfoDao.getUser().getIcon()),head,optionsImagg);}
                    else {
                        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(UserInfoDao.getUser().getIcon()),head,optionsImagb);
                    }
                }
                if (!TextUtils.isEmpty(user.getPetName())){
                    tv_name.setText(user.getPetName());
                    MyLogUtils.info(user.getPetName()+"广播接收后昵称");
                }
            }
//            initLayout();
            if (adapter==null){
                adapter= new MyPagerAdapter(getSupportFragmentManager());

            }else {
                adapter.notifyDataSetChanged();
            }
            lvTimeAxis.setAdapter(adapter);
            lvTimeAxis.setOnPageChangeListener(TimeHistoryAct.this);
            currentPos = 0;
            textViewList.get(0).setTextColor(Color.parseColor("#ff7e84"));//默认加载项，标签文字对应变色
            lvTimeAxis.setCurrentItem(0);

        }
    }



    @Override
    public void onStart() {
        super.onStart();
        if (myReceiver==null) {
            myReceiver = new MyReceiver();
            registerReceiver(myReceiver, new IntentFilter(USER_INFO));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myReceiver!=null){
           unregisterReceiver(myReceiver);
            myReceiver=null;
        }
    }
}
