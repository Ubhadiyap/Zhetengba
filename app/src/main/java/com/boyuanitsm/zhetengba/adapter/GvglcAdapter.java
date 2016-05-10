package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.boyuanitsm.zhetengba.R;

/**
 * Created by bitch-1 on 2016/5/10.
 */
public class GvglcAdapter extends BaseAdapter {
    private Context context;
    private final String TITLE[]={"小鲜肉","吃货","摄影控"};

    public GvglcAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return TITLE.length;
    }

    @Override
    public Object getItem(int position) {
        return TITLE[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context, R.layout.gvcir_item,null);
        return view;
    }
}
