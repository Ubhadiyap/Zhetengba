package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.CircleAct;
import com.boyuanitsm.zhetengba.activity.circle.SquareAct;
import com.boyuanitsm.zhetengba.activity.mess.ScanQrcodeAct;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.NewCircleMess;
import com.boyuanitsm.zhetengba.db.CircleNewMessDao;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.view.CommonView;
import com.leaf.library.http.TRequestConfig;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 圈子界面
 * Created by xiaoke on 2016/5/2.
 */
public class CircleFrg extends BaseFragment implements View.OnClickListener{
    @ViewInject(R.id.cv_gc)
    private CommonView cv_gc;
    @ViewInject(R.id.cv_qz)
    private CommonView cv_qz;
    @ViewInject(R.id.cv_sys)
    private CommonView cv_sys;
    @ViewInject(R.id.iv_xnew)
    private TextView iv_new2;
    @ViewInject(R.id.iv_xnew2)
    private TextView tv_new;
    private View view;
    private FragmentManager childFragmentManager;
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
    private LocalBroadcastManager broadcastManager;



    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.circle_frg, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences=mActivity.getSharedPreferences("ztb_cirNews",
                Activity.MODE_PRIVATE);
        int cir_newsCount = sharedPreferences.getInt("cir_NewsCount", 0);
        SharedPreferences sharedPreferences2=mActivity.getSharedPreferences("sqa_cir",
                Activity.MODE_PRIVATE);
        int sqa_newsCount = sharedPreferences2.getInt("sqa_NewsCount", 0);
        if (sqa_newsCount==0){
            tv_new.setVisibility(View.GONE);
        }else {
            tv_new.setVisibility(View.VISIBLE);
            tv_new.setText(sqa_newsCount+"");
        }
        if (cir_newsCount==0){
            iv_new2.setVisibility(View.GONE);
        }else {
            iv_new2.setVisibility(View.VISIBLE);
            iv_new2.setText(cir_newsCount+"");
        }
//        NewCircleMess newMess = CircleNewMessDao.getUser();
//        if (newMess!=null){
//            if (newMess.isCircle()==false){
//                iv_new2.setVisibility(View.VISIBLE);
//            }else {
//                iv_new2.setVisibility(View.GONE);
//            }
//        }
    }


    @OnClick({R.id.cv_gc,R.id.cv_qz,R.id.cv_sys})
    @Override
    public void onClick(View v) {
//        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.cv_gc://跳转至广场
                openActivity(SquareAct.class);
                break;
            case R.id.cv_qz:
//                if (CircleNewMessDao.getUser()!=null){
//                    NewCircleMess circleMess = CircleNewMessDao.getUser();
//                    if (circleMess.isCircle()==false){
//                        iv_new2.setVisibility(View.GONE);
//                        circleMess.setIsCircle(true);
//                        circleMess.setIsMain(true);
//                        CircleNewMessDao.updateMess(circleMess);
//                    }
//                }
                openActivity(CircleAct.class);
                break;
            case R.id.cv_sys:
                openActivity(ScanQrcodeAct.class);
                break;
        }


    }
    private UpdateBroadCastReceiver receiver;
    public static final String UPDATE = "circleFrg_update";

    class UpdateBroadCastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            MyLogUtils.info(" iv_new.setVisibility(View.INVISIBLE);");
            SharedPreferences sharedPreferences=mActivity.getSharedPreferences("ztb_cirNews",
                    Activity.MODE_PRIVATE);
            int cir_newsCount = sharedPreferences.getInt("cir_NewsCount", 0);
            SharedPreferences sharedPreferences2=mActivity.getSharedPreferences("sqa_cir",
                    Activity.MODE_PRIVATE);
            int sqa_newsCount = sharedPreferences2.getInt("sqa_NewsCount", 0);
            if (sqa_newsCount==0){
                tv_new.setVisibility(View.GONE);
            }else {
                tv_new.setVisibility(View.VISIBLE);
                tv_new.setText(sqa_newsCount+"");
            }
            if (cir_newsCount==0){
                iv_new2.setVisibility(View.GONE);
            }else {
                iv_new2.setVisibility(View.VISIBLE);
                iv_new2.setText(cir_newsCount+"");
            }
//            if (CircleNewMessDao.getUser()!=null){
//                NewCircleMess newCircleMess = CircleNewMessDao.getUser();
//                if (newCircleMess.isCircle()==false){
//                    iv_new2.setVisibility(View.VISIBLE);
//                }else {
//                    iv_new2.setVisibility(View.GONE);
//                }
//            }else {
//                iv_new2.setVisibility(View.GONE);
//            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences=mActivity.getSharedPreferences("ztb_cirNews",
                Activity.MODE_PRIVATE);
        int cir_newsCount = sharedPreferences.getInt("cir_NewsCount", 0);
        SharedPreferences sharedPreferences2=mActivity.getSharedPreferences("sqa_cir",
                Activity.MODE_PRIVATE);
        int sqa_newsCount = sharedPreferences2.getInt("sqa_NewsCount", 0);
        if (sqa_newsCount==0){
            tv_new.setVisibility(View.GONE);
        }else {
            tv_new.setVisibility(View.VISIBLE);
            tv_new.setText(sqa_newsCount+"");
        }
        if (cir_newsCount==0){
            iv_new2.setVisibility(View.GONE);
        }else {
            iv_new2.setVisibility(View.VISIBLE);
            iv_new2.setText(cir_newsCount+"");
        }
//        if (CircleNewMessDao.getUser()!=null){
//            NewCircleMess newCircleMess = CircleNewMessDao.getUser();
//            if (newCircleMess.isCircle()==false){
//                iv_new2.setVisibility(View.VISIBLE);
//            }else {
//                iv_new2.setVisibility(View.GONE);
//            }
//        }else {
//            iv_new2.setVisibility(View.GONE);
//        }
        if (receiver==null) {
            receiver = new UpdateBroadCastReceiver();
            mActivity.registerReceiver(receiver, new IntentFilter(UPDATE));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver!=null){
            mActivity.unregisterReceiver(receiver);
            receiver=null;
        }
    }
}

