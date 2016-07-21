package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.CirMessAct;
import com.boyuanitsm.zhetengba.activity.circle.CircleglAct;
import com.boyuanitsm.zhetengba.activity.circle.CreatCirAct;
import com.boyuanitsm.zhetengba.base.BaseFragment;

/**
 * 圈子界面
 * Created by xiaoke on 2016/5/2.
 */
public class CircleFrg extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private View view;
    private FragmentManager childFragmentManager;
    private ChanelFrg chanelFrg;
    private CirFrg cirFrg;
    private boolean tag = true;
    private RadioButton rb_chanel;
    private RadioButton rb_circle;
    private ImageView iv_quan;
    private ImageView iv_newmes;
    private RadioGroup rg_cir;
    private LinearLayout ll_quan;
    private LinearLayout ll_newmes;
    private ImageView iv_new_red;
    private RelativeLayout rl_more;


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.circle_frg, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        rb_chanel = (RadioButton) view.findViewById(R.id.rb_chanel);
        rb_circle = (RadioButton) view.findViewById(R.id.rb_circle);
        rl_more= (RelativeLayout) view.findViewById(R.id.rl_more);
        iv_quan = (ImageView) view.findViewById(R.id.iv_quan);
        iv_newmes = (ImageView) view.findViewById(R.id.iv_newmes);
        ll_quan = (LinearLayout) view.findViewById(R.id.ll_quan);
        ll_newmes = (LinearLayout) view.findViewById(R.id.ll_newmes);
        iv_new_red = (ImageView) view.findViewById(R.id.iv_new_red);
        rg_cir = (RadioGroup) view.findViewById(R.id.rg_cir);
        childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        defaultChildShow(fragmentTransaction);
        rg_cir.setOnCheckedChangeListener(this);
        ll_newmes.setOnClickListener(this);
        ll_quan.setOnClickListener(this);
        rl_more.setOnClickListener(this);
//        List<CircleInfo> list= CircleMessDao.getCircleUser();
//        if (list!=null&&list.size()>0){
//            iv_new_red.setVisibility(View.VISIBLE);
//        }else {
//            iv_new_red.setVisibility(View.GONE);
//        }
    }

    /***
     * 默认展示页面
     *
     * @param fragmentTransaction
     */
    private void defaultChildShow(FragmentTransaction fragmentTransaction) {
        hideChildFragment(fragmentTransaction);
        if (chanelFrg == null) {
            chanelFrg = new ChanelFrg();
            fragmentTransaction.add(R.id.fl_circle, chanelFrg);
        } else {
            fragmentTransaction.show(chanelFrg);
        }
        fragmentTransaction.commit();
    }

    /***
     * 隐藏子页面
     *
     * @param fragmentTransaction
     */
    private void hideChildFragment(FragmentTransaction fragmentTransaction) {
        if (chanelFrg != null) {
            fragmentTransaction.hide(chanelFrg);
        }
        if (cirFrg != null) {
            fragmentTransaction.hide(cirFrg);
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_quan:
                //跳转至圈子管理
                intent.setClass(mActivity, CircleglAct.class);
                startActivity(intent);
                break;
            case R.id.ll_newmes:
                //跳转至圈子消息
//                iv_new_red.setVisibility(View.GONE);
                intent.setClass(mActivity, CirMessAct.class);
                startActivity(intent);
                break;

            case R.id.rl_more://弹出PopupWindow对话框
                View rl_more=view.findViewById(R.id.rl_more);
                showPopupWindow(rl_more);
                break;
        }


    }

    /**
     *
     */
    private void showPopupWindow(View parent) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.popuwindowsquzi_dialog, null);
        // 实例化popupWindow
        final PopupWindow popupWindow = new PopupWindow(layout, AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
       WindowManager manager = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);

//        @SuppressWarnings("deprecation")
        //获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(parent, xpos, 0);

        TextView tv_myqz= (TextView) layout.findViewById(R.id.tv_myqz);
        TextView tv_xx= (TextView) layout.findViewById(R.id.tv_xx);
        TextView tv_newqz= (TextView) layout.findViewById(R.id.tv_newqz);

        tv_myqz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openActivity(CircleglAct.class);
            }
        });

        tv_xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openActivity(CirMessAct.class);
            }
        });
        tv_newqz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openActivity(CreatCirAct.class);

            }
        });

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        hideChildFragment(fragmentTransaction);
        switch (rg_cir.getCheckedRadioButtonId()) {
            case R.id.rb_chanel:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                if (chanelFrg == null) {
                    chanelFrg = new ChanelFrg();
                    fragmentTransaction.add(R.id.fl_circle, chanelFrg);
                } else {
                    fragmentTransaction.show(chanelFrg);
                }
                break;
            case R.id.rb_circle:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                if (cirFrg == null) {
                    cirFrg = new CirFrg();
                    fragmentTransaction.add(R.id.fl_circle, cirFrg);
                } else {
                    fragmentTransaction.show(cirFrg);
                }
                break;
        }
        fragmentTransaction.commit();
    }
}
