package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.ContractedAct;
import com.boyuanitsm.zhetengba.activity.ScheduleAct;

/**
 * 简约界面条目点击弹出“活动详情”对话框
 * Created by xiaoke on 2016/4/28.
 */
public class PlaneDialog extends Dialog {
    private Context context;
    private LayoutAnimationController lac;
    public PlaneDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    public PlaneDialog(Context context) {
        this(context, R.style.Plane_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        setContentView(R.layout.act_plan_layout);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT/*Constant.height*/);

//diaolog动画
//        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
//                0f, Animation.RELATIVE_TO_SELF, 6f, Animation.RELATIVE_TO_SELF, 0);
//        animation.setInterpolator(new DecelerateInterpolator());
//        animation.setDuration(350);
//        animation.setStartOffset(150);

//        lac = new LayoutAnimationController(animation, 0.12f);
//        lac.setInterpolator(new DecelerateInterpolator());
        TextView tv_plan_act = (TextView) findViewById(R.id.tv_plan_act);
        TextView tv_plan_calen = (TextView) findViewById(R.id.tv_plan_calen);
        LinearLayout ll_cancel = (LinearLayout) findViewById(R.id.ll_cancel);
        tv_plan_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入活动界面
                Intent intent=new Intent();
                intent.setClass(context, ContractedAct.class);
                context.startActivity(intent);
            }
        });
        tv_plan_calen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context,ScheduleAct.class);
                context.startActivity(intent);
            }
        });
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

}

