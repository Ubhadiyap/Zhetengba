package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.CityBean;

import java.util.HashMap;
import java.util.List;

/**
 * 使用过的城市适配器
 * Created by wangbin on 16/2/22.
 */
public class GvCityAdapter extends BaseAdapter {
    private List<CityBean> list;
    private Context context;
    private static HashMap<Integer, Boolean> isselected;
    int pos=-1;

    public GvCityAdapter(Context context, List<CityBean> list, int pos) {
        this.context = context;
        this.list = list;
        this.pos = pos;
        isselected = new HashMap<Integer, Boolean>();
        initData();
    }
    public GvCityAdapter(Context context, List<CityBean> list) {
        this.context = context;
        this.list = list;
        isselected = new HashMap<Integer, Boolean>();
        initData();
    }
    public void initData() {
        for (int i = 0; i < list.size(); i++) {
            CityBean peo = list.get(i);
            if (i != pos)
                getIsSelected().put(i, false);
            else
                getIsSelected().put(i, true);
        }
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
        if (getIsSelected().get(position)) {
            tvHist.setBackgroundResource(R.drawable.bgg);
        } else {
            tvHist.setBackgroundResource(R.drawable.gv_item_back);
        }
        return convertView;
    }

    static HashMap<Integer, Boolean> getIsSelected() {
        return isselected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelect) {
        GvCityAdapter.isselected = isSelect;
    }
}
