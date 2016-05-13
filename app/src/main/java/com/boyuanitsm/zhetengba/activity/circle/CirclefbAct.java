package com.boyuanitsm.zhetengba.activity.circle;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.cirfbAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 圈子发布界面
 * Created by bitch-1 on 2016/5/9.
 */
public class CirclefbAct extends BaseActivity {
    @ViewInject(R.id.gv_qzfb)//圈子发布界面gridview
    private MyGridView gv_qzfb;

    @ViewInject(R.id.my_gv)
    private com.leaf.library.widget.MyGridView gvPhoto;//添加图片gridview

    @Override
    public void setLayout() {
        setContentView(R.layout.act_circlefb);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("Alic");
        gv_qzfb.setAdapter(new cirfbAdapter(getApplicationContext()));

    }
}
