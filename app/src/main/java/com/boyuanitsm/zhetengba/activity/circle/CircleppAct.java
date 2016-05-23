package com.boyuanitsm.zhetengba.activity.circle;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CirpplistAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.util.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 圈子成员界面
 * Created by bitch-1 on 2016/5/9.
 */
public class CircleppAct extends BaseActivity {
    @ViewInject(R.id.plv)
    private PullToRefreshListView plv;

    private String tv_right;//标题栏右边文字
    @ViewInject(R.id.tv_gl_member)
    private TextView tv_gl_member;

    private boolean flag;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_glcircle);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子成员");
//        tv_right=tv_gl_member.getText().toString();
//        tv_gl_member.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openActivity(GLCirAct.class);
//            }
//        });
        plv.setPullRefreshEnabled(true);//下拉刷新
        plv.setScrollLoadEnabled(true);//滑动加载
        plv.setPullLoadEnabled(false);//上拉刷新
        plv.setHasMoreData(true);//是否有更多数据
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(ZhetebaUtils.getCurrentTime());
        plv.getRefreshableView().setDivider(null);
        plv.getRefreshableView().setAdapter(new CirpplistAdapter(getApplicationContext(),false));
    }

    @OnClick(R.id.tv_gl_member)
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_gl_member:
                Log.i("hah","gaga");
                if(flag){
                    flag=false;
                    tv_gl_member.setText("取消");
                    plv.getRefreshableView().setAdapter(new CirpplistAdapter(getApplicationContext(), true));
                }
                else {tv_gl_member.setText("管理成员"); flag=true;
                      plv.getRefreshableView().setAdapter(new CirpplistAdapter(getApplicationContext(), false));}
                break;
        }


    }
}
