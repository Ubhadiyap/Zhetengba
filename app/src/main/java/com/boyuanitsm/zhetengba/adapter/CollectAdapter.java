package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;

import java.util.List;

/**
 * 我的收藏列表
 * Created by bitch-1 on 2016/6/3.
 */
public class CollectAdapter extends BaseAdapter {
    private Context context;
    private List<SimpleInfo> infos;

    public CollectAdapter(Context context) {
        this.context = context;
    }


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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.item_act, null);
        return view;

    }

}
