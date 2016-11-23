package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.CircleEntity;

import java.util.List;

/**
 * 圈子正文，回复adapter
 * Created by xiaoke on 2016/11/23.
 */
public class CircleHfAdapter extends BaseAdapter{

    private List<CircleEntity> list;
    private Context context;
    private String lz;//楼主
    public CircleHfAdapter(Context context,List<CircleEntity> list,String strLz){
        this.context=context;
        this.list=list;
        this.lz=strLz;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView!=null&&convertView.getTag()!=null){
            holder= (Holder) convertView.getTag();
        }else {
            holder=new Holder();
            convertView=View.inflate(context, R.layout.item_hf_circle,null);
            holder.tv_hf= (TextView) convertView.findViewById(R.id.tv_hf);
            holder.tv_hf2= (TextView) convertView.findViewById(R.id.tv_hf2);
            holder.tv_hf_content= (TextView) convertView.findViewById(R.id.tv_hf_content);
            convertView.setTag(holder);
        }
      if (!TextUtils.isEmpty(list.get(position).getPetName())){
          holder.tv_hf.setText(list.get(position).getPetName());
      }
        if (!TextUtils.isEmpty(lz)){
            holder.tv_hf2.setText(lz+"：");
        }
        if (!TextUtils.isEmpty(list.get(position).getCommentContent())){
            holder.tv_hf_content.setText(list.get(position).getCommentContent());
        }
        return convertView;
    }
    class Holder{
        private TextView tv_hf,tv_hf2,tv_hf_content;
    }
}
