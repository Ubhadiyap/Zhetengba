package com.boyuanitsm.zhetengba.activity.circle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleglAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子管理界面
 * Created by bitch-1 on 2016/5/6.
 */
public class CircleglAct extends BaseActivity {
    @ViewInject(R.id.lv_circlegl)
    private PullToRefreshListView lv_circlegl;

    private List<CircleEntity> list;
    private int page=1;
    private int rows=10;
    private CircleglAdapter adapter;
    private String str,str1;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_circlegl);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        if (bundle!=null){
            String ppuserId=bundle.getString("PPuserId");
            if (!TextUtils.isEmpty(ppuserId)) {
                if (ppuserId.equals(UserInfoDao.getUser().getId())) {
                    str="圈子管理";
                    setTopTitle(str);
                    getCircleList(null, page, rows);
                } else {
                    str="TA的圈子";
                    setTopTitle(str);
                    getCircleList(ppuserId, page, rows);
                }
            }
        }else {
            str="圈子管理";
            setTopTitle(str);
            getCircleList(null,page,rows);
        }
        lv_circlegl.setPullRefreshEnabled(true);//下拉刷新
        lv_circlegl.setScrollLoadEnabled(true);//滑动加载
        lv_circlegl.setPullLoadEnabled(false);//上拉刷新
        lv_circlegl.setHasMoreData(true);//是否有更多数据
        lv_circlegl.getRefreshableView().setDivider(null);//设置分隔线
        lv_circlegl.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        lv_circlegl.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        lv_circlegl.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
//        lv_circlegl.getRefreshableView().setAdapter(new CircleglAdapter(this,list));
        lv_circlegl.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CircleglAct.this, CirxqAct.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("circleId", datas.get(position).getId());
                if (str.equals("圈子管理")){
                    bundle1.putInt("IsInCircle", 1);
                }else if (str.equals("TA的圈子")){
                    bundle1.putInt("IsInCircle", 0);
                }
//                intent.putExtra("circleId", datas.get(position).getId());
                intent.putExtras(bundle1);
                startActivity(intent);
//                openActivity(CirxqAct.class);
            }
        });
        lv_circlegl.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lv_circlegl.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page=1;
                getCircleList(null,page,rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getCircleList(null,page,rows);
            }
        });


    }
    @OnClick({R.id.iv_jia,R.id.iv_serch})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.iv_serch:
                openActivity(SerchCirAct.class);
                break;

            case R.id.iv_jia:
                openActivity(CreatCirAct.class);
                break;


        }
    }

    private List<CircleEntity> datas=new ArrayList<>();
    //获取圈子列表
    private void getCircleList(String userId,final int page,int rows){
        RequestManager.getTalkManager().myCircleList(userId,page, rows, new ResultCallback<ResultBean<DataBean<CircleEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_circlegl.onPullUpRefreshComplete();
                lv_circlegl.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<DataBean<CircleEntity>> response) {
                lv_circlegl.onPullUpRefreshComplete();
                lv_circlegl.onPullDownRefreshComplete();
                list= response.getData().getRows();
                if (list.size() == 0) {
                    if (page == 1) {

                    } else {
                        lv_circlegl.setHasMoreData(false);
                    }
                    return;
                }
                if(page==1){
                    datas.clear();
                }
                datas.addAll(list);
                if(adapter==null) {
                    adapter=new CircleglAdapter(CircleglAct.this,datas);
                    lv_circlegl.getRefreshableView().setAdapter(adapter);
                }else {
                    adapter.notifyChange(datas);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (receiver==null){
            receiver=new MyBroadCastReceiver();
            registerReceiver(receiver,new IntentFilter(INTENTFLAG));
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
    public static final String INTENTFLAG="circle_update";
    private class MyBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            page=1;
            getCircleList(null,page,rows);
        }
    }
}
