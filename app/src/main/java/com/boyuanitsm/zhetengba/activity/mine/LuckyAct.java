package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.LuckyAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 我的抽奖界面
 * Created by bitch-1 on 2016/8/11.
 */
public class LuckyAct extends BaseActivity {
    @ViewInject(R.id.plv)
    private PullToRefreshListView plv;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_lucky);

    }

    @Override
    public void init(Bundle savedInstaceState) {
        setTopTitle("我的抽奖");
        LayoutHelperUtil.freshInit(plv);
        LuckyAdapter adapter=new LuckyAdapter(this);
        plv.getRefreshableView().setAdapter(adapter);


    }
}
