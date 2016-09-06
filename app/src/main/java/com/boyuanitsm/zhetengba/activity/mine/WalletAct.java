package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.WallerAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.view.MyListview;
import com.boyuanitsm.zhetengba.view.WithdrawDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 钱包
 * Created by bitch-1 on 2016/9/5.
 */
public class WalletAct extends BaseActivity {
    @ViewInject(R.id.lv_zd)
    private MyListview lv_zd;//账单明细
    @ViewInject(R.id.tv_tx)
    private TextView tv_tx;//提现按钮
    private WallerAdapter adapter;
    private WithdrawDialog dialog;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_wallet);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的钱包");
        adapter=new WallerAdapter(WalletAct.this);
        lv_zd.setAdapter(adapter);

    }

    @OnClick(R.id.tv_tx)
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_tx://提现
                dialog=new WithdrawDialog(WalletAct.this).builder();
                dialog.show();
                dialog.setCanceledOnTouchOutside(true);
                break;
        }
    }
}
