package com.boyuanitsm.zhetengba.fragment.calendarFrg;

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

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;

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
    private LinearLayout ll_friend;
    private RadioGroup rg_simple;

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
        childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        defaultChildShow(fragmentTransaction);
        rg_simple= (RadioGroup) view.findViewById(R.id.rg_simple);
        rg_simple.setOnCheckedChangeListener(this);
        ll_friend.setOnClickListener(this);
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


        mPopupWindow.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.bg_circle_stroke));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setContentView(v);
        mPopupWindow.showAsDropDown(ll_friend, 20, 20);
        tv_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToastUtils.showShortToast(getContext(), "点击了好友");
                mPopupWindow.dismiss();
            }
        });
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToastUtils.showShortToast(getContext(), "点击了全部");
                mPopupWindow.dismiss();
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        hideChildFragment(fragmentTransaction);
        switch (rg_simple.getCheckedRadioButtonId()){
            case R.id.rb_simple:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                if (simpleFrg == null) {
                    simpleFrg = new SimpleFrg();
                    fragmentTransaction.add(R.id.fl_calendar, simpleFrg);
                } else {
                    fragmentTransaction.show(simpleFrg);
                }
                break;
            case R.id.rb_calendar:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                if (calFrg == null) {
                    calFrg = new CalFrg();

                    fragmentTransaction.add(R.id.fl_calendar, calFrg);
                } else {
                    fragmentTransaction.show(calFrg);
                }
                break;

        }
        fragmentTransaction.commit();
    }
}
