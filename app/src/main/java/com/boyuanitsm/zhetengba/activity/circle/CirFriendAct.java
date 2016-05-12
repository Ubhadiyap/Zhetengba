package com.boyuanitsm.zhetengba.activity.circle;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CirFriGvAdapter;
import com.boyuanitsm.zhetengba.adapter.CirListViewAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
/**
 * 圈子，圈友主页
 * Created by xiaoke on 2016/5/12.
 */
public class CirFriendAct extends BaseActivity {
    private GridView cir_fri_gv;
    private GridView cir_fri_com;
    private PullToRefreshListView cir_fri_plv;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_cir_friend);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈友主页");//塞入返回的昵称
        cir_fri_plv = (PullToRefreshListView) findViewById(R.id.cir_fri_plv);
        View v=getLayoutInflater().inflate(R.layout.act_cir_friend_head,null);
        cir_fri_plv.setPullRefreshEnabled(true);//下拉刷新
        cir_fri_plv.setScrollLoadEnabled(true);//滑动加载
        cir_fri_plv.setPullLoadEnabled(false);//上拉刷新
        cir_fri_plv.setHasMoreData(true);//是否有更多数据
        cir_fri_plv.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        cir_fri_plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        cir_fri_plv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
        cir_fri_plv.getRefreshableView().setDivider(null);
        cir_fri_plv.getRefreshableView().addHeaderView(v);
        cir_fri_gv = (GridView) v.findViewById(R.id.cir_fri_gv);
        cir_fri_com = (GridView)v.findViewById(R.id.cir_fri_com);
        CirFriGvAdapter adapter=new CirFriGvAdapter(getApplicationContext());
        cir_fri_gv.setAdapter(adapter);
        cir_fri_com.setAdapter(adapter);
        CirListViewAdapter lvadapter=new CirListViewAdapter(getApplicationContext());
        cir_fri_plv.getRefreshableView().setAdapter(lvadapter);
    }
}
