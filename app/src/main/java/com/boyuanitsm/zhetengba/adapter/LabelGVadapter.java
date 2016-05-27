package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.TestListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签管理适配器
 * Created by xiaoke on 2016/5/27.
 */
public class LabelGVadapter extends BaseAdapter {
    private Context context;
    List<String> list=new ArrayList<>();
    private int clickTemp=-1;
    public LabelGVadapter(Context context,List<String> list){
        this.context=context;
        this.list=list;
    }
    public void setSelection(int position){
        this.clickTemp=position;
    }
    public void update(List<String> list){
        this.list=list;
        notifyDataSetChanged();
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
        LabelHolder labelHolder=null;
        if (convertView!=null&&convertView.getTag()!=null){
            labelHolder= (LabelHolder) convertView.getTag();
        }else {
            labelHolder=new LabelHolder();
            convertView=View.inflate(context, R.layout.item_label2,null);
            labelHolder.tv_label2=(TextView)convertView.findViewById(R.id.tv_label2);
            convertView.setTag(labelHolder);
        }
        labelHolder.tv_label2.setText(list.get(position));
        //设置点击颜色变换
        if (clickTemp == position) {
            labelHolder.tv_label2.setTextColor(Color.parseColor("#ffffff"));
            labelHolder.tv_label2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_circle_stroke_green));
//            map.put(position, tabHolder.tv_tab.getText().toString() + "");
//            act.setMap(map);
        } else {
            labelHolder.tv_label2.setTextColor(Color.parseColor("#333333"));
            labelHolder.tv_label2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_circle_stroke_green_boder));
//            map.remove(position);
//            act.setMap(map);
        }
        return convertView;
    }
    class LabelHolder{
        private TextView tv_label2;
    }
}
