package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

/**
 * 点击分享，弹出分享dialog
 * Created by xiaoke on 2016/5/10.
 */
public class ShareDialog extends Dialog {
    private Context context;
    public ShareDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    public ShareDialog(Context context) {
        this(context, R.style.Plane_Dialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater =getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_share, null);
        view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slid_bottom_to_top));
        setContentView(view);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT/*Constant.height*/);
//        TextView tv_plan_act = (TextView) findViewById(R.id.tv_plan_act);
//        TextView tv_plan_calen = (TextView) findViewById(R.id.tv_plan_calen);
        LinearLayout ll_cancel_share = (LinearLayout) findViewById(R.id.ll_cancel_share);
//        tv_plan_act.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //进入活动界面
//                Intent intent=new Intent();
//                intent.setClass(context, ContractedAct.class);
//                context.startActivity(intent);
//            }
//        });
//        tv_plan_calen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setClass(context,ScheduleAct.class);
//                context.startActivity(intent);
//            }
//        });
        ll_cancel_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
