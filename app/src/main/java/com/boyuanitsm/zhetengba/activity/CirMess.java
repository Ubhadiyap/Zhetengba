package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 圈子--圈子消息
 * Created by xiaoke on 2016/5/6.
 */
public class CirMess extends BaseActivity {
    private ListView lv_cir_mess;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_circle_mess);
    }

    @Override
    public void init(Bundle savedInstanceState) {
         lv_cir_mess = (ListView) findViewById(R.id.lv_cir_mess);
        setTopTitle("圈子消息");
        setRight("我的发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转我发布界面；
                //直接跳转发布界面；
                openActivity(MyPlane.class);
            }
        });

    }
}
