package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

/**
 * Created by bitch-1 on 2016/9/12.
 */
public class TipsDrawDialog implements View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView tv_qd;
    private LinearLayout ll_close;

    public TipsDrawDialog(Context context) {
        this.context = context;
    }
    public TipsDrawDialog builder(){
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.act_tipsdraw, null);
        tv_qd= (TextView) view.findViewById(R.id.tv_qd);
        ll_close= (LinearLayout) view.findViewById(R.id.ll_clse);
        ll_close.setOnClickListener(this);
        tv_qd.setOnClickListener(this);
        // 设置Dialog最小宽度为屏幕宽度
//        view.setMinimumWidth(display.getWidth());

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    public TipsDrawDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public TipsDrawDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_qd:
                dialog.dismiss();
                break;
            case R.id.ll_clse:
                dialog.dismiss();
                break;
        }
    }
}

