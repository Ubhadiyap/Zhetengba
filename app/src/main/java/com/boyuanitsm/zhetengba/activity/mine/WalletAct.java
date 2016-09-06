package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.WallerAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.view.MyListview;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 钱包
 * Created by bitch-1 on 2016/9/5.
 */
public class WalletAct extends BaseActivity {
    @ViewInject(R.id.lv_zd)
    private MyListview lv_zd;//账单明细
    private WallerAdapter adapter;
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
}
