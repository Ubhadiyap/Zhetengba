package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.view.MyGridView;

/**
 * Created by bitch-1 on 2016/5/10.
 */
public class GlcirAdapter extends BaseAdapter{
    private Context context;

    public GlcirAdapter(Context context) {
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
        View view=View.inflate(context, R.layout.item_glcir,null);
        MyGridView gv_bq= (MyGridView) view.findViewById(R.id.gv_bq);
        gv_bq.setAdapter(new GvglcAdapter(context));
        return view;
    }
}
