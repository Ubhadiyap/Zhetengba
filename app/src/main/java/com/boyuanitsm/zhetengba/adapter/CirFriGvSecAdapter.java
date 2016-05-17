package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.boyuanitsm.zhetengba.R;

/**
 * 圈友主页，界面共同圈子gridview适配器
 * Created by xiaoke on 2016/5/12.
 */
public class CirFriGvSecAdapter extends BaseAdapter {
    private Context context;
    public CirFriGvSecAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 3;
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
        return View.inflate(context, R.layout.item_grid_cir_sec,null);
    }
}
