package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.SquareAct;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.chat.db.UserDao;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;

/**
 * Created by bitch-1 on 2016/8/4.
 */
public class GcDialog implements View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private Display display;
    private String userid,creatid;
    private String takeid;
    private TextView tv_sc,tv_jb,tv_qx;
    private int position;

    public GcDialog(Context context, String userid, String creatid, String takeid, int position) {
        this.context=context;
        this.userid=userid;
        this.creatid=creatid;
        this.takeid=takeid;
        this.position=position;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public GcDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_gc, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        tv_sc= (TextView) view.findViewById(R.id.tv_sc);
        tv_jb= (TextView) view.findViewById(R.id.tv_jb);
        tv_qx= (TextView) view.findViewById(R.id.tv_qx);
        if(UserInfoDao.getUser().getId().equals(creatid)){
            tv_sc.setVisibility(View.VISIBLE);
        }else {
            tv_sc.setVisibility(View.GONE);
        }
        tv_sc.setOnClickListener(this);
        tv_jb.setOnClickListener(this);
        tv_qx.setOnClickListener(this);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    public GcDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sc://删除
                deleat(takeid);

                break;

            case R.id.tv_jb://举报
                MyToastUtils.showShortToast(context,"举报成功！");
                dialog.dismiss();
                break;

            case R.id.tv_qx://取消
                dialog.dismiss();
                break;
        }

    }

    private void deleat(String takeid) {
        RequestManager.getTalkManager().deleteTalk(takeid, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                MyToastUtils.showShortToast(context, "删除成功");
                dialog.dismiss();
                Intent intent=new Intent(SquareAct.TALK_LIST);
                Bundle bundle=new Bundle();
                bundle.putInt("DelPosition", position);
                bundle.putString("tag", "delTag");
                intent.putExtras(bundle);
                context.sendBroadcast(intent);
//                dialog.dismiss();

            }
        });
    }
}


