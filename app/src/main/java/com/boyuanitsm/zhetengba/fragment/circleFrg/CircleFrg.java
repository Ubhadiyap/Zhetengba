package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.CirMessAct;
import com.boyuanitsm.zhetengba.activity.circle.CircleglAct;
import com.boyuanitsm.zhetengba.base.BaseFragment;

/**
 * 圈子界面
 * Created by xiaoke on 2016/5/2.
 */
public class CircleFrg extends BaseFragment implements View.OnClickListener{
    private View view;
    private FragmentManager childFragmentManager;
    private ChanelFrg chanelFrg;
    private CirFrg cirFrg;
    private boolean tag = true;
    private TextView tv_chanel;
    private TextView tv_circle;
    private ImageView iv_quan;
    private ImageView iv_newmes;



    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.circle_frg, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_chanel = (TextView) view.findViewById(R.id.tv_chanel);
        tv_circle = (TextView) view.findViewById(R.id.tv_circle);
        iv_quan = (ImageView) view.findViewById(R.id.iv_quan);
        iv_newmes = (ImageView) view.findViewById(R.id.iv_newmes);
        childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        defaultChildShow(fragmentTransaction);
        tv_chanel.setOnClickListener(this);
        tv_circle.setOnClickListener(this);
        iv_quan.setOnClickListener(this);
        iv_newmes.setOnClickListener(this);
    }

    /***
     * 默认展示页面
     * @param fragmentTransaction
     */
    private void defaultChildShow(FragmentTransaction fragmentTransaction) {
        hideChildFragment(fragmentTransaction);
        if ( chanelFrg== null) {
            chanelFrg = new ChanelFrg();
            fragmentTransaction.add(R.id.fl_circle, chanelFrg);
        } else {
            fragmentTransaction.show(chanelFrg);
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
        if ( chanelFrg!= null) {
            fragmentTransaction.hide(chanelFrg);
        }
        if (cirFrg != null) {
            fragmentTransaction.hide(cirFrg);
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.tv_chanel:
                hideChildFragment(fragmentTransaction);
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
                if (chanelFrg == null) {
                    chanelFrg = new ChanelFrg();
                    fragmentTransaction.add(R.id.fl_circle, chanelFrg);
                } else {
                    fragmentTransaction.show(chanelFrg);
                }
                tag = true;
                textColorChange(tag);
                fragmentTransaction.commit();
                break;
            case R.id.tv_circle:
                hideChildFragment(fragmentTransaction);
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                if (cirFrg==null){
                    cirFrg=new CirFrg();
                    fragmentTransaction.add(R.id.fl_circle,cirFrg);
                }else {
                    fragmentTransaction.show(cirFrg);
                }
                tag = false;
                textColorChange(tag);
                fragmentTransaction.commit();
                break;
            case R.id.iv_quan:
                //跳转至圈子管理
                intent.setClass(mActivity,CircleglAct.class);
                startActivity(intent);
                break;
            case R.id.iv_newmes:
                //跳转至，圈子消息
                intent.setClass(mActivity, CirMessAct.class);
                startActivity(intent);
                break;
        }


    }
    /***
     * 设置导航选中后颜色
     * @param tag
     */
    private void textColorChange(boolean tag) {
        if (tag == true) {
            tv_chanel.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_left_white_circle_yes_stroke));
            tv_chanel.setTextColor(getActivity().getResources().getColor(R.color.main_color));
            tv_circle.setTextColor(getActivity().getResources().getColor(R.color.dq_color));
            tv_circle.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_right_white_stroke_calendar2));


        } else {
            tv_circle.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_right_white_stroke_calendar));
            tv_chanel.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_left_white_circle_yes_stroke2));
            tv_chanel.setTextColor(getActivity().getResources().getColor(R.color.dq_color));
            tv_circle.setTextColor(getActivity().getResources().getColor(R.color.main_color));

        }
    }
}
