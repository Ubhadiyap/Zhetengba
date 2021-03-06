package com.boyuanitsm.zhetengba.fragment.calendarFrg;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mess.AddFriendsAct;
import com.boyuanitsm.zhetengba.activity.mess.CreateGroupAct;
import com.boyuanitsm.zhetengba.activity.mess.ScanQrcodeAct;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;

import java.util.List;

/**
 * 会友/档期界面
 * Created by xiaoke on 2016/4/24.
 */
public class CalendarFrg extends BaseFragment implements View.OnClickListener{
    private FragmentManager childFragmentManager;//frg嵌套，拿到子管理器
    public CalendarFrg simpleFrg;
    private CalFrg calFrg;
    private RadioButton rb_simple, rb_calendar;//拿到
    private PopupWindow mPopupWindow;
    private LinearLayout ll_friend, ll_friend_two;
    private RadioGroup rg_simple;
    private TextView tv_friend_all, tv_friend_all_two;
    private List<SimpleInfo> list;
    private PopupWindow mPopupWindowAd;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.frag_calendar, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        rb_simple = (RadioButton) view.findViewById(R.id.rb_simple);
        rb_calendar = (RadioButton) view.findViewById(R.id.rb_calendar);
        ll_friend = (LinearLayout) view.findViewById(R.id.ll_friend);
        ll_friend_two = (LinearLayout) view.findViewById(R.id.ll_friend_two);
        tv_friend_all = (TextView) view.findViewById(R.id.tv_friend_all);
        tv_friend_all_two = (TextView) view.findViewById(R.id.tv_friend_all_two);
        childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        defaultChildShow(fragmentTransaction);
//        rg_simple = (RadioGroup) view.findViewById(R.id.rg_simple);
//        rg_simple.setOnCheckedChangeListener(this);
        ll_friend.setOnClickListener(this);
//        ll_friend_two.setOnClickListener(this);
    }


    /***
     * childFragmet默认加载页面
     *
     * @param fragmentTransaction
     */
    private void defaultChildShow(FragmentTransaction fragmentTransaction) {
        hideChildFragment(fragmentTransaction);
        if (simpleFrg == null) {
            simpleFrg = new CalendarFrg();
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
//                selectPop();
                addPop();
                break;
//            case R.id.ll_friend_two:
//                selectPop();
//                break;
        }

    }

    /**
     * 待解决：对话框布局有出入
     * 选择对话框，选择好友/全部
     */
    private void selectPop() {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.act_select_friend, null);
        mPopupWindow = new PopupWindow(v, AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        TextView tv_friend = (TextView) v.findViewById(R.id.tv_friend);
        TextView tv_all = (TextView) v.findViewById(R.id.tv_all);
        TextView tv_me = (TextView) v.findViewById(R.id.tv_me);
        //获取xoff
        WindowManager manager = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        int xpos = manager.getDefaultDisplay().getWidth() / 2 - mPopupWindow.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAsDropDown(ll_friend, xpos, 0);
        final Intent intentRecevier = new Intent();
        tv_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = 0;
                intentRecevier.putExtra("state", state);
                if (rb_simple.isChecked()) {
                    tv_friend_all.setText("好友");
                    intentRecevier.setAction(SimpleFrg.DATA_CHANGE_KEY);
                } else if (rb_calendar.isChecked()) {
                    tv_friend_all_two.setText("好友");
                    intentRecevier.setAction(CalFrg.CAL_DATA_CHANGE_KEY);
                }

                mActivity.sendBroadcast(intentRecevier);
                mPopupWindow.dismiss();
            }
        });
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyToastUtils.showShortToast(getContext(), "点击了全部");
                //发送数据变化广播，通知CaleFrg更新数据
                int state = 1;
                intentRecevier.putExtra("state", state);
                if (rb_simple.isChecked()) {
                    tv_friend_all.setText("全部");
                    intentRecevier.setAction(SimpleFrg.DATA_CHANGE_KEY);
                } else if (rb_calendar.isChecked()) {
                    tv_friend_all_two.setText("全部");
                    intentRecevier.setAction(CalFrg.CAL_DATA_CHANGE_KEY);
                }
                mActivity.sendBroadcast(intentRecevier);
                mPopupWindow.dismiss();
            }
        });
        tv_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = 2;
                intentRecevier.putExtra("state", state);
                if (rb_simple.isChecked()) {
                    tv_friend_all.setText("我的");
                    intentRecevier.setAction(SimpleFrg.DATA_CHANGE_KEY);
                } else if (rb_calendar.isChecked()) {
                    tv_friend_all_two.setText("我的");
                    intentRecevier.setAction(CalFrg.CAL_DATA_CHANGE_KEY);
                }
                mActivity.sendBroadcast(intentRecevier);
                mPopupWindow.dismiss();
            }
        });

    }
    /**
     * 待解决：对话框布局有出入
     * 选择对话框，选择好友/全部
     */
    private void addPop() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.act_pop_mess2, null);
        mPopupWindowAd = new PopupWindow(v,layoutParams.width, layoutParams.height);
        LinearLayout ll_sao = (LinearLayout) v.findViewById(R.id.ll_sao);
        LinearLayout ll_qun = (LinearLayout) v.findViewById(R.id.ll_qunavatar);
        LinearLayout ll_add_friend = (LinearLayout) v.findViewById(R.id.ll_add_friend);
        mPopupWindowAd.setBackgroundDrawable(new BitmapDrawable(null, ""));
        mPopupWindowAd.setFocusable(true);
        //获取xoff
        WindowManager manager = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        int xpos = manager.getDefaultDisplay().getWidth() / 2 - mPopupWindowAd.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        mPopupWindowAd.showAsDropDown(ll_friend, xpos, 0);
        ll_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //扫一扫
                getActivity().startActivity(new Intent(getContext(), ScanQrcodeAct.class));
                mPopupWindowAd.dismiss();
            }
        });
//        ll_qun.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //创建群组
//                getActivity().startActivity(new Intent(getContext(), CreateGroupAct.class));
//                mPopupWindowAd.dismiss();
//            }
//        });
        ll_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加好友
                getActivity().startActivity(new Intent(getContext(), AddFriendsAct.class));
                mPopupWindowAd.dismiss();
            }
        });

    }

//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
//        hideChildFragment(fragmentTransaction);
//        switch (rg_simple.getCheckedRadioButtonId()) {
//            case R.id.rb_simple:
//                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
//                if (simpleFrg == null) {
//                    simpleFrg = new CalendarFrg();
//                    fragmentTransaction.add(R.id.fl_calendar, simpleFrg);
//                } else {
//                    fragmentTransaction.show(simpleFrg);
//                }
////                ll_friend.setVisibility(View.VISIBLE);
////                ll_friend_two.setVisibility(View.GONE);
//                break;
//            case R.id.rb_calendar:
//                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
//                if (calFrg == null) {
//                    calFrg = new CalFrg();
//                    fragmentTransaction.add(R.id.fl_calendar, calFrg);
//                } else {
//                    fragmentTransaction.show(calFrg);
//                }
////                ll_friend.setVisibility(View.GONE);
////                ll_friend_two.setVisibility(View.VISIBLE);
//                break;
//
//        }
//        fragmentTransaction.commitAllowingStateLoss();
//    }

}
