package com.boyuanitsm.zhetengba.activity.mine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CollectionBean;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 我的收藏
 * Created by Administrator on 2016/5/9.
 */
public class MyColleitionAct extends BaseActivity{
    
    @ViewInject(R.id.plv)
    private PullToRefreshListView plv;
    private ActAdapter adapter;
    private boolean isComment,isComment2;
    private int gznum=0;//默认关注人数0
    private int jionum=0;//默认参加人数0；

    private int page=1;
    private int rows=10;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_mycollection);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的收藏");
        plv.setPullRefreshEnabled(true);//下拉刷新
        plv.setScrollLoadEnabled(true);//滑动加载
        plv.setPullLoadEnabled(false);//上拉刷新
        plv.setHasMoreData(true);//是否有更多数据
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(ZhetebaUtils.getCurrentTime());
        adapter = new ActAdapter(MyColleitionAct.this);
        plv.getRefreshableView().setAdapter(adapter);
        findgzPortsMsg(page, rows);
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });


    }



    /**
     * 关注列表
     * @param page
     * @param rows
     *
     *
     */
    public void findgzPortsMsg(final int page,int rows){
        RequestManager.getUserManager().findCollectionListByUserId(page, rows, new ResultCallback<ResultBean<DataBean<List<CollectionBean>>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<DataBean<List<CollectionBean>>> response) {
                List<CollectionBean>list=response.getData().getRows();

            }
        });



    }

}
