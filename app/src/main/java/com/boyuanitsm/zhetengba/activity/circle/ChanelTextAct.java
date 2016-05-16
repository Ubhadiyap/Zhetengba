package com.boyuanitsm.zhetengba.activity.circle;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ChaTextAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.leaf.library.widget.MyListView;

/**
 * 频道正文
 * Created by xiaoke on 2016/5/11.
 */
public class ChanelTextAct extends BaseActivity {
    private MyListView my_lv;
    private ScrollView sl_chanel;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_chanel_text);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("频道正文");
         my_lv = (MyListView) findViewById(R.id.my_lv);
       sl_chanel = (ScrollView) findViewById(R.id.sl_chanel);
        ChaTextAdapter adapter=new ChaTextAdapter(getApplicationContext());
        my_lv.setAdapter(adapter);
        sl_chanel.smoothScrollTo(0,0);
    }
}
