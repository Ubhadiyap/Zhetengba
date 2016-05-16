package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.boyuanitsm.zhetengba.R;

/**
 * Created by xiaoke on 2016/5/9.
 */
public class CircleMessAdatper extends BaseAdapter {
    public static final int SELECT=1;
    public static final int STATE=2;
    private Context context;
    public CircleMessAdatper(Context context){
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        //复写返回类型 list.get(position).type
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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
        //tiem_mess_one两种布局
        return View.inflate(context, R.layout.item_mess,null);
    }
}
