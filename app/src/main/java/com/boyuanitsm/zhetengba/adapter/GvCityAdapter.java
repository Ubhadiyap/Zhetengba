package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.CityBean;

import java.util.List;

/**
 * 使用过的城市适配器
 * Created by wangbin on 16/2/22.
 */
public class GvCityAdapter extends BaseAdapter {
    private List<CityBean> list;
    private Context context;

    public GvCityAdapter(Context context, List<CityBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        convertView = View.inflate(context, R.layout.gv_history_item, null);
        TextView tvHist = (TextView) convertView.findViewById(R.id.tvHist);
        tvHist.setText(list.get(position).getName());
        return convertView;
    }
}
