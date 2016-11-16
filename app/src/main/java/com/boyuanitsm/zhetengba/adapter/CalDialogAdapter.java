package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoke on 2016/6/28.
 */
public class CalDialogAdapter extends BaseAdapter{
    private List<SimpleInfo> list=new ArrayList<>();
    private Context context;
    public CalDialogAdapter(Context context,List<SimpleInfo> simpleInfos){
        this.context=context;
        this.list=simpleInfos;
    }
    public void updata(List<SimpleInfo> simpleInfos){
        this.list=simpleInfos;
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
        convertView=View.inflate(context, R.layout.item_cal_dialog, null);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_activity_name);
        String strTheme=list.get(position).getActivityTheme();
        if (strTheme.length()>7){
            tv_name.setText(strTheme.substring(0,7)+"...");
        }else {
            tv_name.setText(strTheme);
        }
        return convertView;
    }
}
