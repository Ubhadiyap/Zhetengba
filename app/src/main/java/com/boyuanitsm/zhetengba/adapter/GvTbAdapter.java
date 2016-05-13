package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

import java.util.List;

/**
 * 简约界面：标签适配器
 * Created by xiaoke on 2016/5/11.
 */
public class GvTbAdapter extends BaseAdapter{
    private Context context;
    private List<String> tabList;
    public GvTbAdapter(Context context, List<String> tabList){
        this.context=context;
        this.tabList=tabList;
    }
    @Override
    public int getCount() {
        return tabList.size();
    }

    @Override
    public Object getItem(int position) {
        return tabList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TabHolder tabHolder;
        if (convertView!=null&&convertView.getTag()!=null){
           tabHolder= (TabHolder) convertView.getTag();
        }else {
            tabHolder=new TabHolder();
            convertView=View.inflate(context, R.layout.item_grid_tab,null);
            tabHolder.tv_tab= (TextView) convertView.findViewById(R.id.tv_tab);
            convertView.setTag(tabHolder);
        }
        tabHolder.tv_tab.setText(tabList.get(position));
        return convertView;
        }
    class TabHolder{
        private TextView tv_tab;
    }
}
