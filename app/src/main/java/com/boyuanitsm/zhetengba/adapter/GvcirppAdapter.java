package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

/**
 * 圈子成员item里面水平listview适配器
 * Created by bitch-1 on 2016/5/10.
 */
public class GvcirppAdapter extends BaseAdapter {
    private Context context;
    private String[] title;

    public GvcirppAdapter(Context context) {
        this.context = context;
    }
    public GvcirppAdapter(Context context,String[] title) {
        this.context = context;
        this.title=title;
    }

    @Override
    public int getCount() {
        return title.length>=3?3:title.length;
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
        View view=View.inflate(context, R.layout.gvcir_item,null);
        TextView tv_bq= (TextView) view.findViewById(R.id.tv_bq);
        tv_bq.setText(title[position]);

        return view;
    }
}
