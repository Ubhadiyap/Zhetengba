package com.boyuanitsm.zhetengba.activity.mess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.fragment.ContractsFrg;
import com.boyuanitsm.zhetengba.fragment.MessFrg;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;

/**
 * 联系人
 * Created by wangbin on 16/5/13.
 */
public class ContractsAct extends BaseActivity {

    private ContractsFrg frg;
    private FragmentManager fragmentManager;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_contracts);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("联系人");
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (frg == null) {
            frg = new ContractsFrg();
            fragmentTransaction.add(R.id.frameContainer, frg);
        } else {
            fragmentTransaction.show(frg);
        }
        fragmentTransaction.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        sendBroadcast(new Intent(MessFrg.UPDATE_CONTRACT));
        MyLogUtils.degug("contracts发送广播");
    }
}
