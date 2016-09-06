package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.WallerAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.BillDateBean;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.view.MyListview;
import com.boyuanitsm.zhetengba.view.WithdrawDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 钱包
 * Created by bitch-1 on 2016/9/5.
 */
public class WalletAct extends BaseActivity {
    @ViewInject(R.id.lv_zd)
    private MyListview lv_zd;//账单明细
    @ViewInject(R.id.tv_tx)
    private TextView tv_tx;//提现按钮
    @ViewInject(R.id.tv_je)
    private TextView tv_je;
    private WallerAdapter adapter;
    private WithdrawDialog dialog;

    private String money;//余额
    private int page,rows;
    private List<BillDateBean>list=new ArrayList<>();
    @Override
    public void setLayout() {
        setContentView(R.layout.act_wallet);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的钱包");
        page=1;rows=10;
        getMoneyNum(UserInfoDao.getUser().getId());//获取用户余额
        getZhangdan(page,rows);//获取账单

    }

    /**
     *账单明细
     * @param page
     * @param rows
     */
    private void getZhangdan(int page,int rows) {
        RequestManager.getUserManager().getBilldeta(page, rows, new ResultCallback<ResultBean<DataBean<BillDateBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<DataBean<BillDateBean>> response) {
                list=response.getData().getRows();
                adapter=new WallerAdapter(WalletAct.this,list);
                lv_zd.setAdapter(adapter);
            }
        });


    }


    /**
     * 获取用户信息
     * @param Id
     */
    private void getMoneyNum(String Id) {
        RequestManager.getMessManager().findUserIcon(Id, new ResultCallback<ResultBean<UserInfo>>(){
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<UserInfo> response) {
                money=response.getData().getBalance();
                tv_je.setText(money);
            }
        });


    }

    @OnClick(R.id.tv_tx)
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_tx://提现
                dialog=new WithdrawDialog(WalletAct.this,money).builder();
                dialog.show();
                dialog.setCanceledOnTouchOutside(true);
                break;
        }
    }
}
