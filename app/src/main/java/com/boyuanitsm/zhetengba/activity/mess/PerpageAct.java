package com.boyuanitsm.zhetengba.activity.mess;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.HlvppAdapter;
import com.boyuanitsm.zhetengba.adapter.PpagevpAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.fragment.PpagecalFrg;
import com.boyuanitsm.zhetengba.fragment.PpagedtFrg;
import com.boyuanitsm.zhetengba.view.HorizontalListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息里面的个人主页界面
 * Created by bitch-1 on 2016/5/16.
 */
public class PerpageAct extends BaseActivity {
    @ViewInject(R.id.hlv_perpage)
    private HorizontalListView hlv_perpage;//水平listview
    @ViewInject(R.id.vpS)
    private ViewPager vpS;//档期，圈子动态
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


//    @ViewInject(R.id.tab_selcet)
//    private PagerSlidingTabStrip tab_selcet;

    private List<Fragment> frgList;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_perpage);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        frgList=new ArrayList<Fragment>();
        frgList.add(new PpagecalFrg());
        frgList.add(new PpagedtFrg());
        hlv_perpage.setAdapter(new HlvppAdapter(getApplicationContext()));//她的圈子下面水平view适配器

//        hlv_perpage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(position==4){
//                    openActivity(CircleppAct.class);
//                }
//            }
//        });
        frgList.add(new PpagecalFrg());//档期frg
        frgList.add(new PpagedtFrg());//圈子动态frg
        vpS.setAdapter(new PpagevpAdapter(getSupportFragmentManager(), frgList));
        setSelect(0);
//        tab_selcet.setViewPager(vpS);

        vpS.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                resetTabBtn();
                int currentItem = vpS.getCurrentItem();
                setTab(currentItem);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    /**
     * 选中 vierpage item
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
     * 显示viewpage 的item
     *
     * @param i
     */
    private void setSelect(int i) {
        vpS.setCurrentItem(i);
        setTab(i);
    }

    private void resetTabBtn() {
        tv_dangqi.setTextColor(Color.parseColor("#cdcdcd"));
        tv_dongtai.setTextColor(Color.parseColor("#cdcdcd"));
        view_dangqi.setBackgroundColor(Color.parseColor("#cdcdcd"));
        view_dongtai.setBackgroundColor(Color.parseColor("#cdcdcd"));
    }

    @OnClick({R.id.rl_dangqi,R.id.rl_dongtai,R.id.iv_set})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.rl_dangqi://档期
                setSelect(0);
                break;

            case R.id.rl_dongtai://圈子动态
                setSelect(1);
                break;

            case R.id.iv_set://右上角设置图标
                View view = findViewById(R.id.iv_set);
                showPopupWindow(view);
                break;
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
                popupWindow.dismiss();

            }
        });

        ll_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
}}
