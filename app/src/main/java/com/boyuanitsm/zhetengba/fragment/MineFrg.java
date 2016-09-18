package com.boyuanitsm.zhetengba.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.LabelMangerAct;
import com.boyuanitsm.zhetengba.activity.mine.MyColleitionAct;
import com.boyuanitsm.zhetengba.activity.mine.PersonalmesAct;
import com.boyuanitsm.zhetengba.activity.mine.SettingAct;
import com.boyuanitsm.zhetengba.activity.mine.ShareqrcodeAct;
import com.boyuanitsm.zhetengba.activity.mine.TimeHistoryAct;
import com.boyuanitsm.zhetengba.activity.mine.WalletAct;
import com.boyuanitsm.zhetengba.adapter.HdAdapter;
import com.boyuanitsm.zhetengba.adapter.MonthSelectAdp;
import com.boyuanitsm.zhetengba.adapter.RecycleviewAdp;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.ActivityDetail;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.CommonView;
import com.boyuanitsm.zhetengba.view.MyViewPager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的
 * Created by liwk on 2016/5/6.
 */
public class MineFrg extends BaseFragment{
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
    @ViewInject(R.id.cj_ll)
    private LinearLayout cj;
    @ViewInject(R.id.cv_set)
    private CommonView cv_set;
    @ViewInject(R.id.hd)
    private CommonView hd;
    @ViewInject(R.id.hd_arrow)
    private ImageView hd_arrow;
    @ViewInject(R.id.lv_hd)
    private ListView lv_hd;
    @ViewInject(R.id.rl_hd)
    private RelativeLayout rl_hd;
    @ViewInject(R.id.vi_li)
    private View vi_li;
    private List<Integer> monthList;//设置时间集合
    private List<TextView> textViewList;//设置时间textview集合
    private ArrayList<Integer> moveToList;//设置textview宽高集合
    private RecycleviewAdp recycleviewAdp;
    private MonthSelectAdp monthSelectAdp;
    private int currentPos;//当前位置
    private int mMouthMargin;//设置月份间隙
    private List<String> timeList=new ArrayList<>();//存储时间
    private int cjOn=0;
    private List<ActivityDetail> actvityList=new ArrayList<>();
    private HdAdapter adapter;
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
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_mine_2,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //设置布局管理器
        //恢复原来，不报错。
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //设置横向
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvLabel.setLayoutManager(linearLayoutManager);
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
        MyLogUtils.info(UserInfoDao.getUser().getIcon() + "图片地址");
        getlable();//获得兴趣标签；
        getActivityMannger(0);
    }

    private void getActivityMannger(final int cusPos) {
        RequestManager.getMessManager().getActivity(new ResultCallback<ResultBean<List<ActivityDetail>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                hd.setEnabled(true);
            }

            @Override
            public void onResponse(ResultBean<List<ActivityDetail>> response) {
                if (actvityList!=null&&actvityList.size()>0){
                    actvityList.clear();
                }
                actvityList=response.getData();
                if (actvityList!=null&&actvityList.size()>0){
                    rl_hd.setVisibility(View.VISIBLE);
                    vi_li.setVisibility(View.VISIBLE);
                    hd.setEnabled(true);
                    if (adapter==null){
                        adapter=new HdAdapter(mActivity,actvityList);
                        lv_hd.setAdapter(adapter);
                    }else {
                        adapter.update(actvityList);
                    }
                    if (cjOn==1){
                        cj.setVisibility(View.VISIBLE);
                        hd.setArrowGone();
                        hd_arrow.setVisibility(View.VISIBLE);
                        cjOn=0;
                    }else{
                        cj.setVisibility(View.GONE);
                        hd.setArrowVisible();
                        hd_arrow.setVisibility(View.GONE);
                        cjOn=1;
                    }
                }else if (cusPos==0){
                    rl_hd.setVisibility(View.GONE);
                    vi_li.setVisibility(View.GONE);
                }
                hd.setEnabled(true);
            }
        });
    }

    /**
     *获得个人兴趣标签
     *
     */
    private void getlable() {
        RequestManager.getScheduleManager().findMyLabelListMoreByUserId(new ResultCallback<ResultBean<List<UserInterestInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
//                MyToastUtils.showShortToast(mActivity,errorMsg);
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





    @OnClick({R.id.iv_shareCode,R.id.cv_set,R.id.iv_headIcon,R.id.my_collect,R.id.tv_noLabel,R.id.iv_bg,R.id.cv_time,R.id.hd,R.id.qb})
    public void todo(View view){
        switch (view.getId()){
            case R.id.cv_time:
                openActivity(TimeHistoryAct.class);
                break;
            case R.id.iv_shareCode://二维码
                openActivity(ShareqrcodeAct.class);
                break;
            case R.id.cv_set://设置
                openActivity(SettingAct.class);
                break;
            case R.id.iv_headIcon://头像
                openActivity(PersonalmesAct.class);
                break;
            case R.id.my_collect://我的收藏
                openActivity(MyColleitionAct.class);
                break;
            case R.id.tv_noLabel://没有标签时显示添加标签
                openActivity(LabelMangerAct.class);
                break;
            case R.id.hd:
                hd.setEnabled(false);
                getActivityMannger(1);
                break;
            case R.id.qb://钱包
                openActivity(WalletAct.class);
                break;
        }
    }
    private MyReceiver myReceiver;
    public static final String USER_INFO = "com.update.userinfo";

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
          UserInfo  user= UserInfoDao.getUser();
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
            getlable();//当修改兴趣标界面修改（添加，删除）签获得兴趣标签；
            getActivityMannger(0);
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        MyLogUtils.info("onStart=====");
        if (myReceiver==null) {
            myReceiver = new MyReceiver();
            getActivity().registerReceiver(myReceiver, new IntentFilter(USER_INFO));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MyLogUtils.info("onResume=====");
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
