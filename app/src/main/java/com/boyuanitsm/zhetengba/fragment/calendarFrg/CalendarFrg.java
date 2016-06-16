package com.boyuanitsm.zhetengba.fragment.calendarFrg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;

import java.util.List;

/**
 * 简约/档期界面
 * Created by xiaoke on 2016/4/24.
 */
public class CalendarFrg extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private FragmentManager childFragmentManager;//frg嵌套，拿到子管理器
    public SimpleFrg simpleFrg;
    private CalFrg calFrg;
    private RadioButton rb_simple, rb_calendar;//拿到
    private boolean tag = true;
    private LinearLayout linearLayout;
    private PopupWindow mPopupWindow;
    private LinearLayout ll_friend,ll_friend_two;
    private RadioGroup rg_simple;
    private TextView tv_friend_all,tv_friend_all_two;
    private List<SimpleInfo> list;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.frag_calendar, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        rb_simple = (RadioButton) view.findViewById(R.id.rb_simple);
        rb_calendar = (RadioButton) view.findViewById(R.id.rb_calendar);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_title_border);
        ll_friend = (LinearLayout) view.findViewById(R.id.ll_friend);
        ll_friend_two=(LinearLayout) view.findViewById(R.id.ll_friend_two);
        tv_friend_all = (TextView) view.findViewById(R.id.tv_friend_all);
        tv_friend_all_two= (TextView) view.findViewById(R.id.tv_friend_all_two);
        childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        defaultChildShow(fragmentTransaction);
        rg_simple = (RadioGroup) view.findViewById(R.id.rg_simple);
        rg_simple.setOnCheckedChangeListener(this);
        ll_friend.setOnClickListener(this);
        ll_friend_two.setOnClickListener(this);
    }


    /***
     * childFragmet默认加载页面
     *
     * @param fragmentTransaction
     */
    private void defaultChildShow(FragmentTransaction fragmentTransaction) {
        hideChildFragment(fragmentTransaction);
        if (simpleFrg == null) {
            simpleFrg = new SimpleFrg();
            fragmentTransaction.add(R.id.fl_calendar, simpleFrg);
        } else {
            fragmentTransaction.show(simpleFrg);
        }
        fragmentTransaction.commit();
    }

    /***
     * 隐藏子页面
     *
     * @param fragmentTransaction
     */
    private void hideChildFragment(FragmentTransaction fragmentTransaction) {
        if (simpleFrg != null) {
            fragmentTransaction.hide(simpleFrg);
        }
        if (calFrg != null) {
            fragmentTransaction.hide(calFrg);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_friend:
                selectPop();
                break;
            case R.id.ll_friend_two:
                selectPop();
                break;
        }

    }

    /**
     * 待解决：对话框布局有出入
     * 选择对话框，选择好友/全部
     */
    private void selectPop() {
        mPopupWindow = new PopupWindow(200, 200);
        View v = LayoutInflater.from(mActivity).inflate(R.layout.act_select_friend, null);
        TextView tv_friend = (TextView) v.findViewById(R.id.tv_friend);
        TextView tv_all = (TextView) v.findViewById(R.id.tv_all);
        TextView tv_me= (TextView) v.findViewById(R.id.tv_me);
        mPopupWindow.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.bg_circle_stroke));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setContentView(v);
        mPopupWindow.showAsDropDown(ll_friend, 20, 20);
        final Intent intentRecevier = new Intent();
        tv_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_simple.isChecked()) {
                    tv_friend_all.setText("好友");
                } else if (rb_calendar.isChecked()) {
                    tv_friend_all_two.setText("好友");
                }
                int state=0;
                intentRecevier.putExtra("state",state);
                intentRecevier.setAction(ConstantValue.DATA_CHANGE_KEY);
                mActivity.sendBroadcast(intentRecevier);
                mPopupWindow.dismiss();
            }
        });
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyToastUtils.showShortToast(getContext(), "点击了全部");
                //发送数据变化广播，通知CaleFrg更新数据
                if (rb_simple.isChecked()) {
                    tv_friend_all.setText("全部");
                } else if (rb_calendar.isChecked()) {
                    tv_friend_all_two.setText("全部");
                }
                int state=1;
                intentRecevier.putExtra("state",state);
                intentRecevier.setAction(ConstantValue.DATA_CHANGE_KEY);
                mActivity.sendBroadcast(intentRecevier);
                mPopupWindow.dismiss();
            }
        });
        tv_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_simple.isChecked()) {
                    tv_friend_all.setText("我的");
                } else if (rb_calendar.isChecked()) {
                    tv_friend_all_two.setText("我的");
                }
                int state=2;
                intentRecevier.putExtra("state",state);
                intentRecevier.setAction(ConstantValue.DATA_CHANGE_KEY);
                mActivity.sendBroadcast(intentRecevier);
                mPopupWindow.dismiss();
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        hideChildFragment(fragmentTransaction);
        switch (rg_simple.getCheckedRadioButtonId()) {
            case R.id.rb_simple:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                if (simpleFrg == null) {
                    simpleFrg = new SimpleFrg();
                    fragmentTransaction.add(R.id.fl_calendar, simpleFrg);
                } else {
                    fragmentTransaction.show(simpleFrg);
                }
                ll_friend.setVisibility(View.VISIBLE);
                ll_friend_two.setVisibility(View.GONE);
                break;
            case R.id.rb_calendar:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                if (calFrg == null) {
                    calFrg = new CalFrg();
                    fragmentTransaction.add(R.id.fl_calendar, calFrg);
                } else {
                    fragmentTransaction.show(calFrg);
                }
                ll_friend.setVisibility(View.GONE);
                ll_friend_two.setVisibility(View.VISIBLE);
                break;

        }
        fragmentTransaction.commitAllowingStateLoss();
    }

}
