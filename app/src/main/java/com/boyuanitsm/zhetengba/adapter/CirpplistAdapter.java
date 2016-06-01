package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.view.HorizontalListView;

/**
 * 圈子成员list适配器
 * Created by bitch-1 on 2016/5/10.
 */
public class CirpplistAdapter extends BaseAdapter{
    private Context context;
    private boolean isshanchu;

    public CirpplistAdapter(Context context,boolean isshanchu) {
        this.context = context;
        this.isshanchu=isshanchu;
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
        View view=View.inflate(context, R.layout.item_cirpp_list,null);
        LinearLayout ll_renshu= (LinearLayout) view.findViewById(R.id.ll_renshu);
        LinearLayout ll_shanchu= (LinearLayout) view.findViewById(R.id.ll_shanchu);
        if(isshanchu==false){
            ll_renshu.setVisibility(View.VISIBLE);
            ll_shanchu.setVisibility(View.GONE);
        }else { ll_renshu.setVisibility(View.GONE);
            ll_shanchu.setVisibility(View.VISIBLE);}
        HorizontalListView hlv_cirpp= (HorizontalListView) view.findViewById(R.id.hlv_cirpp);
        hlv_cirpp.setAdapter(new GvcirppAdapter(context));
        return view;
    }
}
