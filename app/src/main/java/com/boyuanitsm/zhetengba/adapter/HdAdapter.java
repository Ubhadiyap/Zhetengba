package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.H5Web;
import com.boyuanitsm.zhetengba.bean.ActivityDetail;
import com.boyuanitsm.zhetengba.view.CommonView;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的界面，活动列表adapter
 * Created by xiaoke on 2016/9/6.
 */
public class HdAdapter extends BaseAdapter {
    private Context context;
    private List<ActivityDetail> list=new ArrayList<>();
    public HdAdapter(Context context,List<ActivityDetail> list){
        this.context=context;
        this.list=list;
    }
    public void update(List<ActivityDetail> actvityList) {
        this.list=actvityList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context, R.layout.item_hd_list, null);
        CommonView item_cj = (CommonView) convertView.findViewById(R.id.item_cj);
        if (!TextUtils.isEmpty(list.get(position).getActivityName())){
            item_cj.setDesText(list.get(position).getActivityName());
        }
        if (TextUtils.isEmpty(list.get(position).getHyperLink())){
            list.remove(position);
        }
        item_cj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, H5Web.class);
                if (!TextUtils.isEmpty(list.get(position).getActivityName())) {
                    intent.putExtra("topTitle", list.get(position).getActivityName());
                }
                if (!TextUtils.isEmpty(list.get(position).getHyperLink())) {
                    intent.putExtra("url", list.get(position).getHyperLink());
                }
                context.startActivity(intent);
            }
        });
        return convertView;
    }


}
