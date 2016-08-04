package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

/**
 * Created by bitch-1 on 2016/8/4.
 */
public class GcDialog implements View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView tv_sc,tv_jb,tv_qx;

    public GcDialog(Context context) {
        this.context=context;
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

                break;

            case R.id.tv_jb://举报

                break;

            case R.id.tv_qx://取消
                dialog.dismiss();
                break;
        }

    }
}


