package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.WalletAct;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.EditInputFilter;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;

import java.math.BigDecimal;

/**
 * Created by bitch-1 on 2016/9/6.
 */
public class WithdrawDialog implements View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private Display display;
    private EditText et_je;
    private TextView tv_qr;
    private String num;//输入金额
    private String money;//余额

    public WithdrawDialog(Context context, String money) {
        this.context = context;
        this.money = money;
    }

    public WithdrawDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.act_withdraw, null);
        // 设置Dialog最小宽度为屏幕宽度
//        view.setMinimumWidth(display.getWidth());
        //获取自定义布局中控件
        et_je = (EditText) view.findViewById(R.id.et_je);
        tv_qr = (TextView) view.findViewById(R.id.tv_qr);
        tv_qr.setOnClickListener(this);
        InputFilter[] filters = {new EditInputFilter()};
        et_je.setFilters(filters);

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
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        num = et_je.getText().toString().trim();
        switch (v.getId()) {
            case R.id.tv_qr:
                if (TextUtils.isEmpty(num)||num==null) {
                    MyToastUtils.showShortToast(context, "输入提现金额");
                    return;
                }else {
                    BigDecimal bignum = new BigDecimal(num);
                    BigDecimal bigmoney = new BigDecimal(money);
                    int r = bignum.compareTo(bigmoney);
                    int d = bignum.compareTo(new BigDecimal("50"));
                    if (d != 1) {
                        MyToastUtils.showShortToast(context, "提现金额不能小于50元");
                        return;
                    }
                    if (r == 1) {//大于
                        MyToastUtils.showShortToast(context, "余额不足");
                        return;
                    }
                    tiXian(num);
                }
                break;
        }

    }



    private void tiXian(String amount) {
        RequestManager.getUserManager().getMoney(amount, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                dialog.dismiss();
                TxScussDialog scussDialog=new TxScussDialog(context).builder();
                scussDialog.show();
                scussDialog.setCanceledOnTouchOutside(true);
                context.sendBroadcast(new Intent(WalletAct.TAG));
            }
        });
    }
}
