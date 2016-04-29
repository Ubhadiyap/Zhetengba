package com.boyuanitsm.zhetengba.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.fragment.CalendarFrg;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainAct extends BaseActivity  {
    private FragmentManager fragmentManager;
    private CalendarFrg calendarFrg;
    @ViewInject(R.id.rb_cal)
    private RadioButton rb_cal;
    private final static int[] icons={R.drawable.menu_ticket_b,R.drawable.menu_chat_b,R.drawable.menu_loop_b,R.drawable.menu_me_b};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //设置布局
        setContentView(R.layout.act_main_layout);
        //获取frg的管理器
        fragmentManager= getSupportFragmentManager();
        //获取事物
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //默认展示“简约”界面
        defaultShow(transaction);
        //代码设置radiobutton图片大小
       /* Drawable dr_cal = getResources().getDrawable(R.drawable.menu_ticket);
        dr_cal.setBounds(0, 0, 100, 100);
        rb_cal.setCompoundDrawables(null, dr_cal, null, null);
*/
         //设置RadioButton的点击事件
       RadioGroup rg_button= (RadioGroup) findViewById(R.id.rg_button);
        rg_button.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction  transaction=fragmentManager.beginTransaction();
                hideFragment(transaction);
                switch (group.getCheckedRadioButtonId()){
                    case R.id.rb_cal:
                        if (calendarFrg ==null){
                            calendarFrg =new CalendarFrg();
                            transaction.add(R.id.fl_main, calendarFrg);
                        }else{
                            transaction.show(calendarFrg);
                        }
                        break;
                    case R.id.rb_mes:
                        break;
                    case R.id.rb_pl:
                        break;
                    case R.id.rb_cir:
                        break;
                    case R.id.rb_my:
                        break;
                }
                transaction.commit();
            }
        });

    }

    /***
     * 默认加载界面
     */
    private void defaultShow(FragmentTransaction transaction) {
        hideFragment(transaction);
        if (calendarFrg ==null){
            calendarFrg =new CalendarFrg();
            transaction.add(R.id.fl_main, calendarFrg);
        }else{
            transaction.show(calendarFrg);
        }
        transaction.commit();

    }

    /***
     * 隐藏所有页面
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (calendarFrg !=null){
            transaction.hide(calendarFrg);
        }

    }
    @Override
    public void setLayout() {

    }

    @Override
    public void init(Bundle savedInstanceState) {

    }


   /* @Override
    public void onClick(View v) {
        FragmentTransaction  transaction=fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (v.getId()){
            case R.id.ll_calendar:
                if (calendarFrg ==null){
                    calendarFrg =new CalendarFrg();
                    transaction.add(R.id.fl_main, calendarFrg);
                }else{
                    transaction.show(calendarFrg);
                }
                changeIcoTextColor(iv_cal,tv_cal,icons[0]);
                break;
            case R.id.ll_message:
                changeIcoTextColor(iv_mess,tv_mess,icons[1]);
                break;
            case R.id.ll_circle:
                changeIcoTextColor(iv_cir,tv_cir,icons[2]);
                break;
            case R.id.ll_me:
                changeIcoTextColor(iv_my,tv_my,icons[3]);
                break;

        }
        transaction.commit();

    }*/

    /*private void changeIcoTextColor(ImageView iv,TextView tv,int adr) {
        iv.setBackgroundResource(adr);
        tv.setTextColor(Color.parseColor("#52c791"));
    }*/
}
