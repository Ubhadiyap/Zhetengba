package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.TestAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 测试listview
 * Created by xiaoke on 2016/5/26.
 */
public class TestListView extends BaseActivity{
    @Override
    public void setLayout() {
        setContentView(R.layout.test_perpage);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ListView test_lv= (ListView) findViewById(R.id.test_lv);
        View inflate = getLayoutInflater().inflate(R.layout.test_item_header, null);
        test_lv.addHeaderView(inflate);
        test_lv.setAdapter(new TestAdapter(TestListView.this));
    }
}
