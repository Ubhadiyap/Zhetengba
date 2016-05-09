package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.boyuanitsm.zhetengba.R;

/**
 * Created by Administrator on 2016/5/6.
 */
public class TimeAxisListAdp extends BaseAdapter {
    private Context context;
    private ImageView imageView;

    public TimeAxisListAdp(Context context) {
        this.context = context;

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
        ViewHolder viewHolder;
        if (convertView==null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeaxis_listitem, null);
            imageView = (ImageView) convertView.findViewById(R.id.image_1);
            viewHolder.ivImage = imageView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position==1){
            imageView.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHolder{
        ImageView ivImage;
    }
}
