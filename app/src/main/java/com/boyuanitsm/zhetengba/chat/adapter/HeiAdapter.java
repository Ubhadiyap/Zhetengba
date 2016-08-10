package com.boyuanitsm.zhetengba.chat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.boyuanitsm.zhetengba.R;

/**
 * 黑名单
 * Created by xiaoke on 2016/8/9.
 */
public class HeiAdapter extends BaseAdapter {
    private Context context;
    public HeiAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return View.inflate(context, R.layout.item_hei_contact,null);
    }
}
