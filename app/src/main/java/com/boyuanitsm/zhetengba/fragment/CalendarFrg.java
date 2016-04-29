package com.boyuanitsm.zhetengba.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;

/**
 * Created by xiaoke on 2016/4/24.
 */
public class CalendarFrg extends Fragment implements View.OnClickListener {
    private View view;
    private FragmentManager childFragmentManager;
    private SimpleFrg simpleFrg;
    private CalFragment calFragment;
    private TextView tv_activity, tv_dangqi;
    private boolean tag = true;
    private LinearLayout linearLayout;
    private PopupWindow mPopupWindow;
    private LinearLayout ll_friend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        childFragmentManager = getChildFragmentManager();
        view = inflater.inflate(R.layout.fragment_calendar, null);
        tv_activity = (TextView) view.findViewById(R.id.tv_activity);
        tv_dangqi = (TextView) view.findViewById(R.id.tv_dangqi);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_title_border);
        ll_friend = (LinearLayout) view.findViewById(R.id.ll_friend);
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        defaultChildShow(fragmentTransaction);
        tv_activity.setOnClickListener(this);
        tv_dangqi.setOnClickListener(this);
        ll_friend.setOnClickListener(this);
        return view;
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
        tag = true;
        textColorChange(tag);
        fragmentTransaction.commit();
    }

    private void hideChildFragment(FragmentTransaction fragmentTransaction) {
        if (simpleFrg != null) {
            fragmentTransaction.hide(simpleFrg);
        }
        if (calFragment != null) {
            fragmentTransaction.hide(calFragment);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        hideChildFragment(fragmentTransaction);
        switch (v.getId()) {
            case R.id.tv_activity:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
                if (simpleFrg == null) {
                    simpleFrg = new SimpleFrg();
                    fragmentTransaction.add(R.id.fl_calendar, simpleFrg);
                } else {
                    fragmentTransaction.show(simpleFrg);
                }
                tag = true;
                textColorChange(tag);
                fragmentTransaction.commit();
                break;
            case R.id.tv_dangqi:
                //动画进出
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                if (calFragment == null) {
                    calFragment = new CalFragment();

                    fragmentTransaction.add(R.id.fl_calendar, calFragment);
                } else {
                    fragmentTransaction.show(calFragment);
                }
                tag = false;
                textColorChange(tag);
                fragmentTransaction.commit();
                break;
            case R.id.ll_friend:
                selectPop();
                break;
        }

    }

    /**
     * 选择对话框，选择好友/全部
     */
    private void selectPop() {
        mPopupWindow = new PopupWindow(200, 200);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.act_select_friend, null);
        TextView tv_friend = (TextView) v.findViewById(R.id.tv_friend);
        TextView tv_all = (TextView) v.findViewById(R.id.tv_all);


        mPopupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_circle_stroke));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setContentView(v);
        mPopupWindow.showAsDropDown(ll_friend, 20, 20);
        tv_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击了好友", 1).show();
                mPopupWindow.dismiss();
            }
        });
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击了全部", 1).show();
                mPopupWindow.dismiss();
            }
        });

    }

    /***
     * 设置导航选中后颜色
     * @param tag
     */
    private void textColorChange(boolean tag) {
        if (tag == true) {
            tv_activity.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_left_white_circle_yes_stroke));
            tv_activity.setTextColor(getActivity().getResources().getColor(R.color.main_color));
            tv_dangqi.setTextColor(getActivity().getResources().getColor(R.color.dq_color));
            tv_dangqi.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_right_white_stroke_dangqi2));


        } else {
            tv_dangqi.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_right_white_stroke_dangqi));
            tv_activity.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_left_white_circle_yes_stroke2));
            tv_activity.setTextColor(getActivity().getResources().getColor(R.color.dq_color));
            tv_dangqi.setTextColor(getActivity().getResources().getColor(R.color.main_color));

        }
    }
}
