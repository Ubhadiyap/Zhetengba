package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.boyuanitsm.zhetengba.R;

/**
 * 提现成功dialog
 * Created by bitch-1 on 2016/9/7.
 */
public class TxScussDialog {
    private Context context;
    private Dialog dialog;
    private Display display;

    public TxScussDialog(Context context) {
        this.context = context;
    }

    public TxScussDialog builder(){
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.act_txdialog, null);
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

    public TxScussDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public TxScussDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }
}
