package com.boyuanitsm.zhetengba.activity.mess;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.boyuanitsm.zhetengba.activity.mine.PersonalmesAct;
import com.boyuanitsm.zhetengba.adapter.HlvppAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.fragment.PpagecalFrg;
import com.boyuanitsm.zhetengba.fragment.PpagedtFrg;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.HorizontalListView;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

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


//    @ViewInject(R.id.tab_selcet)
//    private PagerSlidingTabStrip tab_selcet;


    private FragmentManager manager;
    private Fragment ppagecalFrg,ppagedtFrg;//档期frg 圈子动态frg

    @Override
    public void setLayout() {
        setContentView(R.layout.act_perpage);

    }

    @Override
    public void init(Bundle savedInstanceState) {

        manager=getSupportFragmentManager();
        hlv_perpage.setAdapter(new HlvppAdapter(getApplicationContext()));//她的圈子下面水平view适配器
        msv_scroll.smoothScrollTo(0, 0);
        setSelect(0);

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
        FragmentTransaction transaction=manager.beginTransaction();
        hideFragments(transaction);
        switch (position){
            case 0://档期frg
                setTab(0);
                if(ppagecalFrg==null){
                    ppagecalFrg=new PpagecalFrg();
                    transaction.add(R.id.fra_main,ppagecalFrg);
                }else {transaction.show(ppagecalFrg);}
                break;
            case 1://圈子动态frg
                setTab(1);
                if(ppagedtFrg==null){
                    ppagedtFrg=new PpagedtFrg();
                    transaction.add(R.id.fra_main,ppagedtFrg);
                }else {transaction.show(ppagedtFrg);}
                break;


        }
        transaction.commit();
    }

    /**
     * 影藏所有frg
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if(ppagecalFrg!=null){
            transaction.hide(ppagecalFrg);
        }
        if(ppagedtFrg!=null){transaction.hide(ppagedtFrg);}
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


}
