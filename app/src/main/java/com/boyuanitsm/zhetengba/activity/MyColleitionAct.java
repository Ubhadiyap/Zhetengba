package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的收藏
 * Created by Administrator on 2016/5/9.
 */
public class MyColleitionAct extends BaseActivity{
    
    @ViewInject(R.id.lv_myCollection)
    private ListView lvMyCollection;
    private ActAdapter adapter;
    private boolean isComment,isComment2;
    private int gznum=0;//默认关注人数0
    private int jionum=0;//默认参加人数0；

    @Override
    public void setLayout() {
        setContentView(R.layout.act_mycollection);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的收藏");
        adapter = new ActAdapter(MyColleitionAct.this);
        lvMyCollection.setAdapter(adapter);
    }

}
