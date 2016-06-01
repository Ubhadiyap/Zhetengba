package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签管理适配器
 * Created by xiaoke on 2016/5/27.
 */
public class LabelGvMyadapter extends BaseAdapter {
    private Context context;
    List<LabelBannerInfo> list=new ArrayList<>();
    public LabelGvMyadapter(Context context, List<LabelBannerInfo> list){
        this.context=context;
        this.list=list;
    }

    public void update(List<LabelBannerInfo> list){
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
        labelHolder.tv_label2.setText(list.get(position).getDictName());
        labelHolder.tv_label2.setTextColor(Color.parseColor("#ffffff"));
        labelHolder.tv_label2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_circle_stroke_green));
        return convertView;
    }
    class LabelHolder{
        private TextView tv_label2;
    }
}
