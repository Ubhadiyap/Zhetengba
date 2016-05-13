package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

import java.util.List;

/**
 * Created by Administrator on 2016/5/12.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private final LayoutInflater inflater;
    private List<String> list;

    public GridAdapter(Context context,List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.label_griditem, parent,false);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_label);
            convertView.setTag(viewHolder);
        }else{
            viewHolder  = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView textView;
    }
    public void addLabel(String label){
        list.add(label);
        notifyDataSetChanged();
    }
    public void removLabel(int position){
        list.remove(position);
        notifyDataSetChanged();
    }
}
