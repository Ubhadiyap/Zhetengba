package com.boyuanitsm.zhetengba.activity.circle;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.GlCircleAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

/**
 * 管理圈子
 * Created by xiaoke on 2016/5/11.
 */
public class GLCirAct extends BaseActivity {
    private ListView gl_cir_lv;
    @Override
    public void setLayout() {
    setContentView(R.layout.act_gl_circle);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("管理圈子");
        setRight("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        gl_cir_lv= (ListView) findViewById(R.id.gl_cir_lv);
//        gl_cir_lv.setPullRefreshEnabled(true);//下拉刷新
//        gl_cir_lv.setScrollLoadEnabled(true);//滑动加载
//        gl_cir_lv.setPullLoadEnabled(false);//上拉刷新
//        gl_cir_lv.setHasMoreData(true);//是否有更多数据
//        gl_cir_lv.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
//        gl_cir_lv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
//        gl_cir_lv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
//        gl_cir_lv.getRefreshableView().setDivider(null);
        GlCircleAdapter adapter=new GlCircleAdapter(getApplicationContext());
        gl_cir_lv.setAdapter(adapter);
    }
}
