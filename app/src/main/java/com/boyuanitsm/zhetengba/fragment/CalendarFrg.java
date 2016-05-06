package com.boyuanitsm.zhetengba.fragment;

import android.os.Bundle;
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
 * 简约/档期界面
 * Created by xiaoke on 2016/4/24.
 */
public class CalendarFrg extends Fragment implements View.OnClickListener {
    private FragmentManager childFragmentManager;//frg嵌套，拿到子管理器
    public SimpleFrg simpleFrg;
    private CalFrg calFrg;
    private TextView tv_simple, tv_calendar;//拿到
    private boolean tag = true;
    private LinearLayout linearLayout;
    private PopupWindow mPopupWindow;
    private LinearLayout ll_friend;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view=  inflater.inflate(R.layout.frag_calendar,null);
        tv_simple = (TextView) view.findViewById(R.id.tv_simple);
        tv_calendar = (TextView) view.findViewById(R.id.tv_calendar);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_title_border);
        ll_friend = (LinearLayout) view.findViewById(R.id.ll_friend);
        childFragmentManager=getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        defaultChildShow(fragmentTransaction);
        tv_simple.setOnClickListener(this);
        tv_calendar.setOnClickListener(this);
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

    /***
     * 隐藏子页面
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
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();

        switch (v.getId()) {
            case R.id.tv_simple:
                hideChildFragment(fragmentTransaction);
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
                if (simpleFrg == null) {
                    simpleFrg = new SimpleFrg();
                    fragmentTransaction.add(R.id.fl_calendar, simpleFrg);
                } else {
                    fragmentTransaction.show(simpleFrg);
                }
                tag = true;
                textColorChange(tag);
                break;
            case R.id.tv_calendar:
                hideChildFragment(fragmentTransaction);
                //动画进出
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                if (calFrg == null) {
                    calFrg = new CalFrg();

                    fragmentTransaction.add(R.id.fl_calendar, calFrg);
                } else {
                    fragmentTransaction.show(calFrg);
                }
                tag = false;
                textColorChange(tag);

                break;
            case R.id.ll_friend:
                selectPop();
                break;
        }
        fragmentTransaction.commit();

    }

    /**
     * 待解决：对话框布局有出入
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
            tv_simple.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_left_white_circle_yes_stroke));
            tv_simple.setTextColor(getActivity().getResources().getColor(R.color.main_color));
            tv_calendar.setTextColor(getActivity().getResources().getColor(R.color.dq_color));
            tv_calendar.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_right_white_stroke_calendar2));


        } else {
            tv_calendar.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_right_white_stroke_calendar));
            tv_simple.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_left_white_circle_yes_stroke2));
            tv_simple.setTextColor(getActivity().getResources().getColor(R.color.dq_color));
            tv_calendar.setTextColor(getActivity().getResources().getColor(R.color.main_color));

        }
    }
}
