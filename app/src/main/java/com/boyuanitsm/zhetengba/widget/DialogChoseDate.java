package com.boyuanitsm.zhetengba.widget;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;

/**
 * Created by wangbin on 16/5/27.
 */
public class DialogChoseDate {

    private Context context;
    private Dialog dialog;
    private Display display;
    private SexClickListener listener;
    private ArrayList<String> list = new ArrayList<>();
    public DialogChoseDate(Context context,ArrayList<String> list) {
        this.context = context;
        this.list = list;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    public DialogChoseDate builder(int position) {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.day_select, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        // 获取自定义Dialog布局中的控件
        TextView save=(TextView) view.findViewById(R.id.adress_Save);
        final MonthPicker citypicker=(MonthPicker) view.findViewById(R.id.citypicker);
        citypicker.setDefault(position,list);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.getAdress(citypicker.getCity_string());
                dialog.dismiss();
            }
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        initdata();
        return this;
    }


    private void initdata() {
        // 添加change事件

    }

    public DialogChoseDate setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public DialogChoseDate setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

    public interface SexClickListener {
        void getAdress(String adress);
    }

    public void setOnSheetItemClickListener(SexClickListener listener){
        this.listener=listener;
    }
}
