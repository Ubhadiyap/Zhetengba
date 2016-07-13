package com.boyuanitsm.zhetengba.fragment;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.LabelMangerAct;
import com.boyuanitsm.zhetengba.activity.mine.MyColleitionAct;
import com.boyuanitsm.zhetengba.activity.mine.PersonalmesAct;
import com.boyuanitsm.zhetengba.activity.mine.SettingAct;
import com.boyuanitsm.zhetengba.activity.mine.ShareqrcodeAct;
import com.boyuanitsm.zhetengba.adapter.MonthSelectAdp;
import com.boyuanitsm.zhetengba.adapter.RecycleviewAdp;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.MyViewPager;
import com.hyphenate.util.Utils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 我的
 * Created by liwk on 2016/5/6.
 */
public class MineFrg extends BaseFragment implements ViewPager.OnPageChangeListener{
    @ViewInject(R.id.lv_timeAxis)
    private MyViewPager lvTimeAxis;//viewPager对象
    @ViewInject(R.id.rv_label)
    private RecyclerView rvLabel;//兴趣标签
    @ViewInject(R.id.iv_headIcon)
    private CircleImageView head;
    @ViewInject(R.id.tv_noLabel)
    private TextView tvNoLabel;
    @ViewInject(R.id.titleLayout)
    private LinearLayout titleLayout;
    @ViewInject(R.id.hslv_chanel)
    private HorizontalScrollView hslv_chanel;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
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
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_mine,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //设置横向
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvLabel.setLayoutManager(linearLayoutManager);
        if (!TextUtils.isEmpty(UserInfoDao.getUser().getPetName())){
            tv_name.setText(UserInfoDao.getUser().getPetName());
        }
        MyLogUtils.info(UserInfoDao.getUser().getIcon()+"图片地址");
        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(UserInfoDao.getUser().getIcon()),head,optionsImag);
        getlable();//获得兴趣标签；

//        findHistory();//获取事件轴


      initLayout();
       MyPagerAdapter adapter= new MyPagerAdapter(getChildFragmentManager());
        //设置viewpager标签适配器
        lvTimeAxis.setAdapter(adapter);
        lvTimeAxis.setOnPageChangeListener(this);

        currentPos = getCurrentMonth()-1;
        textViewList.get(currentPos).setTextColor(Color.parseColor("#e7e700"));//默认加载项，标签文字对应变色
        lvTimeAxis.setCurrentItem(currentPos);

    }



    /**
     *获得个人兴趣标签
     *
     */
    private void getlable() {
        RequestManager.getScheduleManager().findMyLabelListMoreByUserId(new ResultCallback<ResultBean<List<UserInterestInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(mActivity,errorMsg);
            }

            @Override
            public void onResponse(ResultBean<List<UserInterestInfo>> response) {
                List<UserInterestInfo> interestList=response.getData();
                if (interestList!=null&&interestList.size()>0){
                    rvLabel.setVisibility(View.VISIBLE);
                    tvNoLabel.setVisibility(View.GONE);

                    //标签recyclerview
                    //设置适配器
                    recycleviewAdp = new RecycleviewAdp(getContext(),interestList);
                    //点击更多跳转标签管理页面
                    recycleviewAdp.setOnItemClickListener(new RecycleviewAdp.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            openActivity(LabelMangerAct.class);
                        }
                    });
                    rvLabel.setAdapter(recycleviewAdp);
                }else{
                    tvNoLabel.setVisibility(View.VISIBLE);
                    rvLabel.setVisibility(View.GONE);
                }

            }
        });
    }

    /**
     * 月份那个
     * @param month
     * @param position
     */
    private void addTitleLayout(String month, int position) {
        //塞入条目
        final TextView textView = (TextView) mActivity.getLayoutInflater().inflate(R.layout.mine_child_title, null);
        //设置title
        textView.setTextSize(12);
        textView.setText(month + "月");
        textView.setTextColor(Color.parseColor("#ffffff"));

        //设置position Tag
        textView.setTag(position);
        //点击监听
        textView.setOnClickListener(new posOnClickListener());
        //LinearLayout管理器布局
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置左右间隙
        params.leftMargin = ZhetebaUtils.dip2px(mActivity, mMouthMargin);
        params.rightMargin = ZhetebaUtils.dip2px(mActivity, mMouthMargin);
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
        return monthList;
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        //当前位置textview 文字选中变色
        textViewList.get(currentPos).setTextColor(Color.parseColor("#ffffff"));
        textViewList.get(currentPos).setTextSize(12);
        textViewList.get(position).setTextColor(Color.parseColor("#e7e700"));
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
    private class MyPagerAdapter extends FragmentStatePagerAdapter{

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
            textViewList.get(currentPos).setTextColor(Color.parseColor("#ffffff"));
            currentPos = (int) view.getTag();
            textViewList.get(currentPos).setTextColor(Color.parseColor("#e7e700"));
//            textViewList.get(currentPos).setTextSize(14);
            lvTimeAxis.setCurrentItem(currentPos);
        }
    }

    @OnClick({R.id.iv_shareCode,R.id.iv_setting,R.id.iv_headIcon,R.id.iv_collection,R.id.tv_noLabel,R.id.iv_bg})
    public void todo(View view){
        switch (view.getId()){
            case R.id.iv_shareCode://二维码
                openActivity(ShareqrcodeAct.class);
                break;
            case R.id.iv_setting://设置
                openActivity(SettingAct.class);
                break;
            case R.id.iv_headIcon://头像
                openActivity(PersonalmesAct.class);
                break;
            case R.id.iv_collection://我的收藏
                openActivity(MyColleitionAct.class);
                break;
            case R.id.tv_noLabel://没有标签时显示添加标签
                openActivity(LabelMangerAct.class);
                break;
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
          UserInfo  user= UserInfoDao.getUser();
            MyLogUtils.degug(user.getPetName());
            if (user != null) {
               if (!TextUtils.isEmpty(user.getIcon())){
                   ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(UserInfoDao.getUser().getIcon()),head,optionsImag);
               }
                if (!TextUtils.isEmpty(user.getPetName())){
                    tv_name.setText(user.getPetName());
                    MyLogUtils.info(user.getPetName()+"广播接收后昵称");
                }
            }
            getlable();//当修改兴趣标界面修改（添加，删除）签获得兴趣标签；
            initLayout();
            if (adapter==null){
                adapter= new MyPagerAdapter(getChildFragmentManager());

            }else {
                adapter.notifyDataSetChanged();
            }
            lvTimeAxis.setAdapter(adapter);
            lvTimeAxis.setOnPageChangeListener(MineFrg.this);
            currentPos = getCurrentMonth()-1;
            textViewList.get(currentPos).setTextColor(Color.parseColor("#e7e700"));//默认加载项，标签文字对应变色
            lvTimeAxis.setCurrentItem(currentPos);

        }
    }

    private void initLayout() {
        mMouthMargin= ZhetebaUtils.dip2px(mActivity, 5);
        monthList = new ArrayList<>();
        textViewList = new ArrayList<>();
        moveToList=new ArrayList<>();
        monthList= getCurrenMonth();

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

    @Override
    public void onStart() {
        super.onStart();
        if (myReceiver==null) {
            myReceiver = new MyReceiver();
            getActivity().registerReceiver(myReceiver, new IntentFilter(USER_INFO));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myReceiver!=null){
            getActivity().unregisterReceiver(myReceiver);
            myReceiver=null;
        }
    }

}
