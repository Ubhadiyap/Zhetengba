package com.boyuanitsm.zhetengba.activity.mess;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.EditAct;
import com.boyuanitsm.zhetengba.activity.mine.LabelMangerAct;
import com.boyuanitsm.zhetengba.activity.mine.PersonalmesAct;
import com.boyuanitsm.zhetengba.adapter.CircleglAdapter;
import com.boyuanitsm.zhetengba.adapter.HlvppAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.PersonalMain;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.ScheduleInfo;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.bean.UserBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;
import com.boyuanitsm.zhetengba.fragment.PpagecalFrg;
import com.boyuanitsm.zhetengba.fragment.PpagedtFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.HorizontalListView;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息里面的个人主页界面
 * Created by bitch-1 on 2016/5/16.
 */
public class PerpageAct extends BaseActivity {
    private static final String TAG = "PerpageAct";
    @ViewInject(R.id.hlv_perpage)
    private HorizontalListView hlv_perpage;//水平listview
    //    @ViewInject(R.id.vpS)
//    private ViewPager vpS;//档期，圈子动态
    @ViewInject(R.id.rl_dangqi)
    private RelativeLayout rl_dangqi;
    @ViewInject(R.id.rl_dongtai)
    private RelativeLayout rl_dongtai;
    @ViewInject(R.id.tv_dangqi)
    private TextView tv_dangqi;
    @ViewInject(R.id.tv_dongtai)
    private TextView tv_dongtai;
    @ViewInject(R.id.view_dangqi)
    private View view_dangqi;
    @ViewInject(R.id.view_dongtai)
    private View view_dongtai;
    @ViewInject(R.id.cv_photo)//头像
    private CircleImageView cv_photo;
    @ViewInject(R.id.msv_scroll)//
    private ScrollView msv_scroll;

    @ViewInject(R.id.fra_main)
    private FrameLayout fra_main;
    @ViewInject(R.id.ll_tab)//标签
    private LinearLayout ll_tab;
    @ViewInject(R.id.tv_tab1)//第1个标签
    private TextView tv_tab1;
    @ViewInject(R.id.tv_tab2)//第2个标签
    private TextView tv_tab2;
    @ViewInject(R.id.tv_tab3)//第3个标签
    private TextView tv_tab3;
    @ViewInject(R.id.tv_tab4)//第4个标签
    private TextView tv_tab4;
    @ViewInject(R.id.tv_niName)
    private TextView tv_niName;//昵称
    @ViewInject(R.id.ll_add_friend)
    private LinearLayout ll_add_riend;
//    @ViewInject(R.id.tab_selcet)
//    private PagerSlidingTabStrip tab_selcet;

    private String userId;
    private FragmentManager manager;
    private Fragment ppagecalFrg=new PpagecalFrg(), ppagedtFrg=new PpagedtFrg();//档期frg 圈子动态frg
    private List<ScheduleInfo> scheduleEntity = new ArrayList<>();
    private List<CircleEntity> circleEntity = new ArrayList<>();

    private List<CircleEntity> circleTalkEntity = new ArrayList<>();
    private List<UserInfo> userEntity = new ArrayList<>();
    private List<UserInterestInfo> userInterestEntity = new ArrayList<>();
    private PersonalMain personalMain;
    private String PAGEFRG_KEY="perpage_to_pagecalFrg";
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    @Override
    public void setLayout() {
        setContentView(R.layout.act_perpage);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getString("userId");
        getPersonalMain(userId);
        manager = getSupportFragmentManager();
//        gv_perpage.setAdapter(new GridViewPerAdapter(PerpageAct.this));
        msv_scroll.smoothScrollTo(0, 0);


    }

    /**
     * 用户信息初始化
     * @param userEntity
     */
    private void initUserData(List<UserInfo> userEntity) {
        if (!TextUtils.isEmpty(userEntity.get(0).getPetName())){
            tv_niName.setText("昵称："+userEntity.get(0).getPetName());
        }else {
            tv_niName.setText("暂无昵称");
        }

            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(userEntity.get(0).getIcon()), cv_photo, optionsImag);

    }

    /**
     * 兴趣标签初始化
     * @param str
     */
    private void iniTab(List<UserInterestInfo> str) {
        if (str.size() == 0) {
            ll_tab.setVisibility(View.GONE);
        } else if (str.size() == 1) {
            ll_tab.setVisibility(View.VISIBLE);
            tv_tab1.setVisibility(View.VISIBLE);
            tv_tab1.setText(str.get(0).getDictName());
            tv_tab2.setVisibility(View.GONE);
            tv_tab3.setVisibility(View.GONE);
            tv_tab4.setVisibility(View.GONE);
        } else if (str.size() == 2) {
            ll_tab.setVisibility(View.VISIBLE);
            tv_tab1.setVisibility(View.VISIBLE);
            tv_tab2.setVisibility(View.VISIBLE);
            tv_tab1.setText(str.get(0).getDictName());
            tv_tab2.setText(str.get(1).getDictName());
            tv_tab3.setVisibility(View.GONE);
            tv_tab4.setVisibility(View.GONE);
        } else if (str.size() == 3) {
            ll_tab.setVisibility(View.VISIBLE);
            tv_tab1.setVisibility(View.VISIBLE);
            tv_tab2.setVisibility(View.VISIBLE);
            tv_tab3.setVisibility(View.VISIBLE);
            tv_tab1.setText(str.get(0).getDictName());
            tv_tab2.setText(str.get(1).getDictName());
            tv_tab3.setText(str.get(2).getDictName());
            tv_tab4.setVisibility(View.GONE);
        } else if (str.size() == 4) {
            ll_tab.setVisibility(View.VISIBLE);
            tv_tab1.setVisibility(View.VISIBLE);
            tv_tab2.setVisibility(View.VISIBLE);
            tv_tab3.setVisibility(View.VISIBLE);
            tv_tab4.setVisibility(View.VISIBLE);
            tv_tab1.setText(str.get(0).getDictName());
            tv_tab2.setText(str.get(1).getDictName());
            tv_tab3.setText(str.get(2).getDictName());
            tv_tab4.setText(str.get(3).getDictName());
            tv_tab4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(LabelMangerAct.class);
                }
            });
        }
    }

    /**
     * 选中 选中btn颜色
     *
     * @param i
     */
    private void setTab(int i) {
        switch (i) {
            case 0:
                tv_dangqi.setTextColor(Color.parseColor("#52c791"));
                view_dangqi.setBackgroundColor(Color.parseColor("#52c791"));
                break;
            case 1:
                tv_dongtai.setTextColor(Color.parseColor("#52c791"));
                view_dongtai.setBackgroundColor(Color.parseColor("#52c791"));
                break;
        }
    }


    /**
     * 初识化btn颜色
     */
    private void resetTabBtn() {
        tv_dangqi.setTextColor(Color.parseColor("#cdcdcd"));
        tv_dongtai.setTextColor(Color.parseColor("#cdcdcd"));
        view_dangqi.setBackgroundColor(Color.parseColor("#cdcdcd"));
        view_dongtai.setBackgroundColor(Color.parseColor("#cdcdcd"));
    }

    @OnClick({R.id.rl_dangqi, R.id.rl_dongtai, R.id.iv_set, R.id.cv_photo})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.rl_dangqi://档期
                resetTabBtn();
                setSelect(0);
                break;

            case R.id.rl_dongtai://圈子动态
                resetTabBtn();
                setSelect(1);
                break;

            case R.id.iv_set://右上角设置图标
                View view = findViewById(R.id.iv_set);
                showPopupWindow(view);
                break;
            case R.id.cv_photo://个人资料
                openActivity(PersonalmesAct.class);
                break;

        }


    }

    /**
     * 点击button后选择显示的frg
     */
    private void setSelect(int position) {
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(transaction);
        switch (position) {
            case 0://档期frg
                setTab(0);
                if (ppagecalFrg == null) {
                    transaction.add(R.id.fra_main, ppagecalFrg);
                } else {
                    transaction.show(ppagecalFrg);
                }
                break;
            case 1://圈子动态frg
                setTab(1);
                if (ppagedtFrg == null) {
                    transaction.add(R.id.fra_main, ppagedtFrg);
                } else {
                    transaction.show(ppagedtFrg);
                }
                break;


        }
        transaction.commit();
    }

    /**
     * 影藏所有frg
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (ppagecalFrg != null) {
            transaction.hide(ppagecalFrg);
        }
        if (ppagedtFrg != null) {
            transaction.hide(ppagedtFrg);
        }
    }


    private void showPopupWindow(View parent) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.popuwindows_dialog, null);
        // 实例化popupWindow
        final PopupWindow popupWindow = new PopupWindow(layout, AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) getSystemService(PerpageAct.this.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        //获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(parent, xpos, 0);


        LinearLayout ll_schy = (LinearLayout) layout.findViewById(R.id.ll_schy);//删除好友
        LinearLayout ll_xiugai = (LinearLayout) layout.findViewById(R.id.ll_xiugai);//修改备注

        ll_schy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAlertDialog(PerpageAct.this).builder().setTitle("提示").setMsg("确定删除该好友").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setNegativeButton("取消", null).show();

            }
        });

        ll_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(EditAct.USER_TYPE, 8);
                startActivity(intent);
                popupWindow.dismiss();

            }
        });
    }

    /**
     * 获取数据
     *
     * @param id
     */
    private void getPersonalMain(String id) {
        RequestManager.getScheduleManager().getPersonalMain(id, new ResultCallback<ResultBean<PersonalMain>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<PersonalMain> response) {
                personalMain = response.getData();
                circleEntity = personalMain.getCircleEntity();
                scheduleEntity = personalMain.getScheduleEntity();
                circleTalkEntity = personalMain.getCircleTalkEntity();
                userEntity = personalMain.getUserEntity();
                userInterestEntity = personalMain.getUserInterestEntity();
                initUserData(userEntity);
                iniTab(userInterestEntity);
                toPageCalFrg();
                setSelect(0);
                hlv_perpage.setAdapter(new HlvppAdapter(PerpageAct.this, circleEntity));//她的圈子下面水平view适配器
            }
        });
    }

    /**
     * 请求档期数据
     */
    private void toPageCalFrg() {
        Intent intent=new Intent();
        intent.setAction("dataChange");
        Bundle bundle=new Bundle();
        bundle.putParcelable(PAGEFRG_KEY, personalMain);
        intent.putExtras(bundle);
        ppagecalFrg.setArguments(bundle);
//        sendBroadcast(intent);
    }

}
