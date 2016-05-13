package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 系统消息界面
 * Created by bitch-1 on 2016/5/4.
 */
public class SystemmesAct extends BaseActivity {
    @ViewInject(R.id.lv_sysmes)
    private ListView lv_sysmes;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_actsystemmes);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("系统消息");
        lv_sysmes.setAdapter(new SystemmesAdapetr());

    }



    class SystemmesAdapetr extends BaseAdapter{
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(SystemmesAct.this,R.layout.item_systemmes,null);
            return view;
        }
    }
}
