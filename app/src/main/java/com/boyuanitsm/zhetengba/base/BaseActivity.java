package com.boyuanitsm.zhetengba.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.lidroid.xutils.ViewUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 基础Activity
 * Created by wangbin on 16/1/12.
 */
public abstract class BaseActivity extends FragmentActivity {
    private TextView tv_title;
    private RelativeLayout rlRight;
    private TextView tvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setLayout();
        // 注入控件
        ViewUtils.inject(this);
        init(savedInstanceState);
    }

    /**
     * 设置布局
     */
    public abstract void setLayout();

    /**
     * 填充数据
     */
    public abstract void init(Bundle savedInstanceState);


    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


    /**
     * 设置顶部标题
     *
     * @param title
     */
    public void setTopTitle(String title) {
        tv_title = (TextView) findViewById(R.id.tv_title);
        if (title != null) {
            tv_title.setText(title);
        }
    }

    public void setRight(String rightText,View.OnClickListener onClick){
        rlRight= (RelativeLayout) findViewById(R.id.rlRight);
        tvRight= (TextView) findViewById(R.id.tvRight);
        rlRight.setVisibility(View.VISIBLE);
        tvRight.setText(rightText);
        rlRight.setOnClickListener(onClick);
    }

    public void setRightEnable(boolean flag){
        tvRight.setEnabled(flag);
    }
    /**
     * 返回
     *
     * @param view
     */
    public void goback(View view) {
        finish();
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
