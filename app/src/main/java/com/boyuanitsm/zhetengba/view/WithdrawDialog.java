package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

/**
 * Created by bitch-1 on 2016/9/6.
 */
public class WithdrawDialog implements View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private Display display;
    private EditText et_je;
    private TextView tv_qr;

    public WithdrawDialog(Context context) {
        this.context = context;
    }

    public WithdrawDialog builder(){
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.act_withdraw, null);
        // 设置Dialog最小宽度为屏幕宽度
//        view.setMinimumWidth(display.getWidth());
        //获取自定义布局中控件
        et_je= (EditText) view.findViewById(R.id.et_je);
        tv_qr= (TextView) view.findViewById(R.id.tv_qr);
        tv_qr.setOnClickListener(this);

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
      public WithdrawDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public WithdrawDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_qr:

                break;
        }

    }
}
