package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.HistoryMsgBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 时间轴里面适配器
 * Created by Administrator on 2016/5/6.
 */
public class TimeAxisListAdp extends BaseAdapter {
    private Context context;
    private List<HistoryMsgBean> list;

    public TimeAxisListAdp(Context context, List <HistoryMsgBean> list) {
        this.context = context;
        this.list=list;
    }

    public void notifyChange(List <HistoryMsgBean> list) {
        this.list=list;
        notifyDataSetChanged();
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
        ViewHolder viewHolder;
        if (convertView==null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeaxis_listitem, null);
            viewHolder.tvContent= (TextView) convertView.findViewById(R.id.tvContent);
            viewHolder.tvTime= (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvContent.setText(list.get(position).getMessage());
        viewHolder.tvTime.setText(timeToDate(Long.valueOf(list.get(position).getCreateTime())));
        return convertView;
    }
    class ViewHolder{
        TextView tvContent;
        TextView tvTime;
    }


    public  String timeToDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(new Date(time));

    }
}
