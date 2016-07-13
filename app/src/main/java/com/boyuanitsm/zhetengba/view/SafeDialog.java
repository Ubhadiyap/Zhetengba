package com.boyuanitsm.zhetengba.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * 防止dialog的Activity消失的时候，调用Dismiss方法报错
 * Created by wangbin on 16/7/13.
 */
public class SafeDialog extends ProgressDialog{

    Activity mParentActivity;
    public SafeDialog(Context context)
    {
        super(context);
        mParentActivity = (Activity) context;
    }

    @Override
    public void dismiss()
    {
        if (mParentActivity != null && !mParentActivity.isFinishing())
        {
            super.dismiss();    //调用超类对应方法
        }
    }
}
