package com.boyuanitsm.zhetengba.activity.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.WallerAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.BillDateBean;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.PrizeBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.LoadingView;
import com.boyuanitsm.zhetengba.view.WithdrawDialog;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 钱包
 * Created by bitch-1 on 2016/9/5.
 */
public class WalletAct extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.lv_zd)
    private PullToRefreshListView lv_zd;//账单明细
//    @ViewInject(R.id.tv_tx)
//    private TextView tv_tx;//提现按钮
//    @ViewInject(R.id.tv_je)
//    private TextView tv_je;

    private TextView tv_je,tv_tx,tv_jpmc;//余额，提现，奖品名称
    private RelativeLayout rl_jpmx;//奖明细
    private LinearLayout ll_zdmx; // ,账单明细
    private WallerAdapter adapter;
    private WithdrawDialog dialog;

    private String money;//余额

    private int page,rows;
    private List<BillDateBean>list;
    private List<BillDateBean> datas = new ArrayList<>();
    private String prizename;

    public static final String TAG = "com.updat.activity";
    private MyReceiver myReceiver;
    @ViewInject(R.id.load_view)
    private LoadingView load_view;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_wallet);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的奖励");
        View hendview=getLayoutInflater().inflate(R.layout.hendview,null);
        tv_je= (TextView) hendview.findViewById(R.id.tv_je);
        tv_tx= (TextView) hendview.findViewById(R.id.tv_tx);
        tv_tx.setOnClickListener(this);
        tv_jpmc= (TextView) hendview.findViewById(R.id.tv_jpmc);
        rl_jpmx= (RelativeLayout) hendview.findViewById(R.id.rl_jpmx);
        ll_zdmx= (LinearLayout) hendview.findViewById(R.id.ll_zdmx);
        lv_zd.getRefreshableView().addHeaderView(hendview);
        LayoutHelperUtil.freshInit(lv_zd);
//        lv_zd.setPullRefreshEnabled(false);//下拉刷新
        lv_zd.getRefreshableView().setDividerHeight(0);

        getMoneyNum(UserInfoDao.getUser().getId());//获取用户余额
        getGift();
        load_view.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getMoneyNum(UserInfoDao.getUser().getId());//获取用户余额
            }
        });
        page=1;rows=10;
        getZhangdan(page,rows);//获取账单
        lv_zd.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lv_zd.setLastUpdatedLabel(ZhetebaUtils.getCurrentTime());
                page = 1;
                getMoneyNum(UserInfoDao.getUser().getId());
                getGift();
                getZhangdan(page,rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getMoneyNum(UserInfoDao.getUser().getId());
                getGift();
                getZhangdan(page,rows);

            }
        });


    }

    /**
     * 获取奖品明细
     */
    private void getGift() {
        RequestManager.getUserManager().getGift(new ResultCallback<ResultBean<PrizeBean>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<PrizeBean> response) {
                prizename=response.getData().getPrizeName();
                if(prizename==null|| TextUtils.isEmpty(prizename)){
                    rl_jpmx.setVisibility(View.GONE);
                }else {
                    tv_jpmc.setText(prizename);
                }

            }
        });
    }

    /**
     *账单明细
     * @param page
     * @param rows
     */
    private void getZhangdan(final int page,int rows) {
        RequestManager.getUserManager().getBilldeta(page, rows, new ResultCallback<ResultBean<DataBean<BillDateBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_zd.onPullUpRefreshComplete();
                lv_zd.onPullDownRefreshComplete();

            }

            @Override
            public void onResponse(ResultBean<DataBean<BillDateBean>> response) {
                lv_zd.onPullUpRefreshComplete();
                lv_zd.onPullDownRefreshComplete();
                list=response.getData().getRows();
                if(list.size()==0){
                    if(page==1){

                    }else {
                        lv_zd.setHasMoreData(false);
                    }

                }
                if(page==1){
                    datas.clear();
                }
                datas.addAll(list);
                if(adapter==null){
                    adapter=new WallerAdapter(WalletAct.this,datas);
                    lv_zd.getRefreshableView().setAdapter(adapter);
                }else {
                    adapter.notify(datas);

                }

            }
        });


    }


    /**
     * 获取用户金额
     * @param Id
     */
    private void getMoneyNum(String Id) {
        RequestManager.getMessManager().findUserIcon(Id, new ResultCallback<ResultBean<UserInfo>>(){
            @Override
            public void onError(int status, String errorMsg) {
                load_view.loadError();
            }

            @Override
            public void onResponse(ResultBean<UserInfo> response) {
                money=response.getData().getBalance();
                load_view.loadComplete();
                tv_je.setText(money);
                BigDecimal bigmoney=new BigDecimal(money);
                int d=bigmoney.compareTo(new BigDecimal("50"));
                if(d==-1){
                    tv_tx.setBackgroundResource(R.drawable.cir_bag_htx);
                    tv_tx.setEnabled(false);
                }else {
                    tv_tx.setBackgroundResource(R.drawable.cir_bag_tixian);
                    tv_tx.setEnabled(true);
                }

            }
        });


    }

//    @OnClick(R.id.tv_tx)
//    public void OnClick(View v){
//        switch (v.getId()){
//            case R.id.tv_tx://提现
//
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_tx:
                dialog=new WithdrawDialog(WalletAct.this,money).builder();
                dialog.show();
                dialog.setCanceledOnTouchOutside(true);
                break;
        }

    }
    public class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            getMoneyNum(UserInfoDao.getUser().getId());//获取用户余额
            getZhangdan(page,rows);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (myReceiver == null) {
            myReceiver = new MyReceiver();
        }
        registerReceiver(myReceiver, new IntentFilter(TAG));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
        }
    }
}
