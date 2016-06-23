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
        GlCircleAdapter adapter=new GlCircleAdapter(getApplicationContext());
        gl_cir_lv.setAdapter(adapter);
    }
}
