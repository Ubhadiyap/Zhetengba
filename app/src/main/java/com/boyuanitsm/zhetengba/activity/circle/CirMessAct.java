package com.boyuanitsm.zhetengba.activity.circle;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.MyPlaneAct;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.adapter.CircleMessAdatper;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.util.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

/**
 * 圈子--圈子消息
 * Created by xiaoke on 2016/5/6.
 */
public class CirMessAct extends BaseActivity {
    private PullToRefreshListView lv_cir_mess;
//    private int[] icons = {R.drawable.test_01};
//    private List<ImageInfo> datalist=new ArrayList<>();
    @Override
    public void setLayout() {
        setContentView(R.layout.act_circle_mess);
    }

    @Override
    public void init(Bundle savedInstanceState) {
         lv_cir_mess = (PullToRefreshListView) findViewById(R.id.lv_cir_mess);
        setTopTitle("圈子消息");
        setRight("我的发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转我发布界面；
                //直接跳转发布界面；
                openActivity(MyPlaneAct.class);
            }
        });
        lv_cir_mess.setPullRefreshEnabled(true);//下拉刷新
        lv_cir_mess.setScrollLoadEnabled(true);//滑动加载
        lv_cir_mess.setPullLoadEnabled(false);//上拉刷新
        lv_cir_mess.setHasMoreData(true);//是否有更多数据
        lv_cir_mess.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        lv_cir_mess.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        lv_cir_mess.setLastUpdatedLabel(ZhetebaUtils.getCurrentTime());
        lv_cir_mess.getRefreshableView().setDivider(null);
        CircleMessAdatper adapter=new CircleMessAdatper(getApplicationContext());
        lv_cir_mess.getRefreshableView().setAdapter(adapter);

    }
}
