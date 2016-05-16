package com.boyuanitsm.zhetengba.activity.mess;

import android.os.Bundle;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.DqMesAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 档期消息
 * Created by wangbin on 16/5/13.
 */
public class DqMesAct extends BaseActivity {
    @ViewInject(R.id.lvDqMes)
    private ListView lvDqMes;
    private DqMesAdapter adapter;//档期消息适配器
    @Override
    public void setLayout() {
        setContentView(R.layout.act_dq_mes);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("档期消息");
        adapter=new DqMesAdapter(this);
        lvDqMes.setAdapter(adapter);
    }
}
