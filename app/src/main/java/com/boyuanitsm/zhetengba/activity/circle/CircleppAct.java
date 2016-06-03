package com.boyuanitsm.zhetengba.activity.circle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CirpplistAdapter;
import com.boyuanitsm.zhetengba.adapter.CirxqAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

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

    private List<UserInfo> userList;//圈子成员集合
    private CirpplistAdapter adapter;
    private String circleId;//圈子id

    private boolean flag=true;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_glcircle);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子成员");
        circleId=getIntent().getStringExtra("circleId");
        LayoutHelperUtil.freshInit(plv);
        userList=new ArrayList<>();

        getCircleMembers(circleId);
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
        plv.getRefreshableView().setDivider(null);//设置分隔线
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
//        plv.getRefreshableView().setAdapter(new CirpplistAdapter(getApplicationContext(),false));
    }

    @OnClick(R.id.tv_gl_member)
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_gl_member:
                Log.i("hah","gaga");
                if(flag){
                    flag=false;
                    tv_gl_member.setText("取消");
//                    setTopTitle("圈子成员");
                    adapter.notifyChange(true);
//                    plv.getRefreshableView().setAdapter(new CirpplistAdapter(getApplicationContext(), true));
                }
                else {
                    tv_gl_member.setText("管理成员");
                    flag=true;
                    adapter.notifyChange(false);
//                    setTopTitle("圈子成员");
//                      plv.getRefreshableView().setAdapter(new CirpplistAdapter(getApplicationContext(), false));
                }
                break;
        }


    }

//    @OnClick({R.id.tv_gl_member})
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.tv_gl_member:
//                openActivity(GLCirAct.class);
//        }
//    }

    //获取圈子人员
    private void getCircleMembers(String circleId){
        RequestManager.getTalkManager().myCircleMember(circleId, new ResultCallback<ResultBean<List<UserInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                plv.onPullUpRefreshComplete();
                plv.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<List<UserInfo>> response) {
                plv.onPullUpRefreshComplete();
                plv.onPullDownRefreshComplete();
                userList=response.getData();
                adapter=new CirpplistAdapter(CircleppAct.this,false,userList);
                plv.getRefreshableView().setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (receiver==null){
            receiver=new MyBroadCastReceiver();
            registerReceiver(receiver,new IntentFilter(MEMBER));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }

    private MyBroadCastReceiver receiver;
    public static final String MEMBER="member_update";
    private class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getCircleMembers(circleId);
            tv_gl_member.setText("管理成员");
            flag=true;
        }
    }
}
