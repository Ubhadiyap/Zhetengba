package com.boyuanitsm.zhetengba.activity.circle;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleglAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * Created by xiaoke on 2016/5/20.
 */
public class SerchCirAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_serch_cir);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ListView lv_cir_serch = (ListView) findViewById(R.id.lv_cir_serch);
            lv_cir_serch.setAdapter(new CircleglAdapter(this));
    }
}
