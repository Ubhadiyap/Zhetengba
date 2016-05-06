package com.boyuanitsm.zhetengba.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.boyuanitsm.zhetengba.Constant;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.fragment.CalendarFrg;
import com.boyuanitsm.zhetengba.fragment.CircleFrg;
import com.boyuanitsm.zhetengba.fragment.MessFrg;
import com.boyuanitsm.zhetengba.utils.ZtbUtils;
import com.boyuanitsm.zhetengba.view.PlaneDialog;

/***
 * 设置首页信息
 */
public class MainAct extends FragmentActivity {
    private FragmentManager fragmentManager;
    private CalendarFrg calendarFrg;
    private CircleFrg circleFrg;
    private MessFrg messFrg;
    private  PlaneDialog planeDialog;
    private final static int[] icons = {R.drawable.menu_ticket_b, R.drawable.menu_chat_b, R.drawable.menu_loop_b, R.drawable.menu_me_b};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_main_layout);
        //获取frg的管理器
        fragmentManager = getSupportFragmentManager();
        //获取事物
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //默认展示“简约”界面
        defaultShow(transaction);
        //设置RadioButton的点击事件
        RadioGroup rg_button = (RadioGroup) findViewById(R.id.rg_button);
        RadioButton rb_cal = (RadioButton) findViewById(R.id.rb_cal);
        ImageView iv_plane = (ImageView) findViewById(R.id.iv_plane);
        planeDialog=new PlaneDialog(this);
        rb_cal.setChecked(true);
        iv_plane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planeDialog.show();
            }
        });
        rg_button.setOnCheckedChangeListener(new OnRadiGrroupCheckedChangeListener());
    }


    /***
     * 首页默认加载节约界面
     */
    private void defaultShow(FragmentTransaction transaction) {
        hideFragment(transaction);
        if (calendarFrg == null) {
            calendarFrg = new CalendarFrg();
            transaction.add(R.id.fl_main, calendarFrg);
        } else {
            transaction.show(calendarFrg);
        }
        transaction.commit();

    }

    /***
     * 隐藏所有页面
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (calendarFrg != null) {
            transaction.hide(calendarFrg);
        }
        if (messFrg != null) {
            transaction.hide(messFrg);
        }
        if (circleFrg != null) {
            transaction.hide(circleFrg);
        }

    }

    /***
     * radiobutton点击事件
     */

    private class OnRadiGrroupCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            hideFragment(transaction);
            switch (group.getCheckedRadioButtonId()) {
                case R.id.rb_cal://点击档期显示：简约/档期界面
                    if (calendarFrg == null) {
                        calendarFrg = new CalendarFrg();
                        transaction.add(R.id.fl_main, calendarFrg);
                    } else {
                        transaction.show(calendarFrg);
                    }
                    break;
                case R.id.rb_mes://点击显示：消息界面
                    if (messFrg == null) {
                        messFrg = new MessFrg();
                        transaction.add(R.id.fl_main, messFrg);
                    } else {
                        transaction.show(messFrg);
                    }
                    break;
                case R.id.rb_cir://点击显示：圈子界面
                    if (circleFrg == null) {
                        circleFrg = new CircleFrg();
                        transaction.add(R.id.fl_main, circleFrg);
                    } else {
                        transaction.show(circleFrg);
                    }
                    break;
                case R.id.rb_my://点击显示：我的界面
                    break;
            }
            transaction.commit();
        }
    }
}
