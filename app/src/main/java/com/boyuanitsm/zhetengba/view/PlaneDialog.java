package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.ScheduleAct;

/**
 * 简约界面条目点击弹出“活动详情”对话框
 * Created by xiaoke on 2016/4/28.
 */
public class PlaneDialog extends Dialog {
    private Context context;

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
        TextView tv_plan_act = (TextView) findViewById(R.id.tv_plan_act);
        TextView tv_plan_calen = (TextView) findViewById(R.id.tv_plan_calen);
        LinearLayout ll_cancel = (LinearLayout) findViewById(R.id.ll_cancel);
        tv_plan_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入活动界面

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

