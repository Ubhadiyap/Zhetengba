package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bitch-1 on 2016/5/6.
 */
public class CircleglAdapter extends BaseAdapter {
    private Context context;
    private List<CircleEntity> list=new ArrayList<>();

    public CircleglAdapter(Context context) {
        this.context = context;
    }
    public CircleglAdapter(Context context,List<CircleEntity> list) {
        this.context = context;
        this.list=list;
    }
    public void notifyChange(List<CircleEntity> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size()>0?list.size():1;
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
        ViewHolder holder=null;
        if(convertView==null) {
            holder=new ViewHolder();
            convertView = View.inflate(context, R.layout.item_circlegl, null);
            holder.head= (CircleImageView) convertView.findViewById(R.id.iv_sysmes);
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.notice= (TextView) convertView.findViewById(R.id.notice);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        if (list!=null&&list.size()>0){
            if(!TextUtils.isEmpty(list.get(position).getAddress())) {
                ImageLoader.getInstance().displayImage(IZtbUrl.BASE_URL + list.get(position).getAddress(), holder.head);
            }
            if(!TextUtils.isEmpty(list.get(position).getCircleName())) {
                holder.name.setText(list.get(position).getCircleName());
            }
            if(!TextUtils.isEmpty(list.get(position).getNotice())) {
                holder.notice.setText(list.get(position).getNotice());
            }
        }
        return convertView;
    }

    class ViewHolder{
        private CircleImageView head;
        private TextView name;
        private TextView notice;
    }
}
