package com.boyuanitsm.zhetengba.activity.mess;

import android.os.Bundle;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.DqMesAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ActivityMess;
import com.boyuanitsm.zhetengba.db.ActivityMessDao;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 档期消息
 * Created by wangbin on 16/5/13.
 */
public class DqMesAct extends BaseActivity {
    @ViewInject(R.id.lvDqMes)
    private ListView lvDqMes;
    private DqMesAdapter adapter;//档期消息适配器
    private List<ActivityMess> list=new ArrayList<>();
    @Override
    public void setLayout() {
        setContentView(R.layout.act_dq_mes);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("档期消息");
        list= ActivityMessDao.getCircleUser();
        adapter=new DqMesAdapter(this,list);
        lvDqMes.setAdapter(adapter);
    }
}
