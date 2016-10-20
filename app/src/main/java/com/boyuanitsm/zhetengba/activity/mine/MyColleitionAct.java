package com.boyuanitsm.zhetengba.activity.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CollectAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CollectionBean;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.LoadingView;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 * Created by Administrator on 2016/5/9.
 */
public class MyColleitionAct extends BaseActivity {

    @ViewInject(R.id.plv)
    private PullToRefreshListView plv;
    @ViewInject(R.id.loadview)
    private LoadingView loadview;
    private boolean isComment, isComment2;
    private int gznum = 0;//默认关注人数0
    private int jionum = 0;//默认参加人数0；

    private int page = 1;
    private int rows = 10;
    private List<CollectionBean> list;
    private List<CollectionBean> datas = new ArrayList<>();
    private CollectAdapter adapter;
    private LinearLayout llnoList;
    private ImageView ivAnim;
    private  TextView noMsg;
    private AnimationDrawable animationDrawable;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_mycollection);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的关注");
//        llnoList = (LinearLayout) findViewById(R.id.noList);
//        ivAnim = (ImageView) findViewById(R.id.ivAnim);
//        noMsg = (TextView) findViewById(R.id.noMsg);
        LayoutHelperUtil.freshInit(plv);
        findgzPortsMsg(page, rows);
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                plv.setLastUpdatedLabel(ZhetebaUtils.getCurrentTime());
                page = 1;
                findgzPortsMsg(page, rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                findgzPortsMsg(page, rows);
            }
        });

        loadview.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                findgzPortsMsg(page, rows);
            }
        });


    }


    /**
     * 关注列表
     *
     * @param page
     * @param rows
     */
    public void findgzPortsMsg(final int page, int rows) {
        RequestManager.getUserManager().findCollectionListByUserId(page, rows, new ResultCallback<ResultBean<DataBean<CollectionBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                loadview.loadError();
                plv.onPullUpRefreshComplete();
                plv.onPullDownRefreshComplete();
//                if (list.size()>0){
//                    if (adapter != null) {
//                        adapter.notify(list);
//                    }
//                }else {
//                    llnoList.setVisibility(View.VISIBLE);
//                    ivAnim.setImageResource(R.mipmap.planeno);
//                    noMsg.setText("加载失败");
//                }
            }

            @Override
            public void onResponse(ResultBean<DataBean<CollectionBean>> response) {
                loadview.loadComplete();
                plv.onPullUpRefreshComplete();
                plv.onPullDownRefreshComplete();
                list = response.getData().getRows();
                if (list.size() == 0) {
                    if (page == 1) {
//                        llnoList.setVisibility(View.VISIBLE);
//                        ivAnim.setImageResource(R.mipmap.planeno);
//                        noMsg.setText("暂无内容");
                        loadview.noContent();
                    } else {
                        plv.setHasMoreData(false);
                    }
//                    return;
                }else {

                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
                if (adapter == null) {
                    adapter = new CollectAdapter(MyColleitionAct.this, datas);
                    plv.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notify(datas);
                }

            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        if (receiverTalk==null){
            receiverTalk=new MyBroadCastReceiverTalk();
            registerReceiver(receiverTalk, new IntentFilter(COLLECTION));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiverTalk!=null){
          unregisterReceiver(receiverTalk);
            receiverTalk=null;
        }
    }
    private MyBroadCastReceiverTalk receiverTalk;
    public static final String COLLECTION="collection_update";
    private class MyBroadCastReceiverTalk extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            page=1;
            findgzPortsMsg(page, rows);
        }
    }

}
