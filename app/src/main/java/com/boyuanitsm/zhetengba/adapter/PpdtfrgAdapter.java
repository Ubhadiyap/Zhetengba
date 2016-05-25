package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.boyuanitsm.zhetengba.R;

/**
 * 消息里面个人主页圈子动态frg
 * Created by bitch-1 on 2016/5/24.
 */
public class PpdtfrgAdapter extends BaseAdapter {
    private Context context;

    public PpdtfrgAdapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context, R.layout.item_circle,null);
        return view;
    }
}
