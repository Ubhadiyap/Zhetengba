package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

import java.util.List;

/**
 * 频道正文列表adapter
 * Created by xiaoke on 2016/5/12.
 */
public class ChaTextAdapter extends BaseAdapter {
    private Context context;
    private String[] str=new String[]{"小明","小红","小红","小红","小红","小红","小红"};
    public ChaTextAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int position) {
        return str[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChaHolder chaHolder = null;
        if (convertView!=null&&convertView.getTag()!=null){
            chaHolder= (ChaHolder) convertView.getTag();
        }else {
            chaHolder=new ChaHolder();
            convertView=View.inflate(context,R.layout.item_chane_text,null);
            chaHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_user_name);
            convertView.setTag(chaHolder);
        }
        chaHolder.tv_name.setText(str[position]);
        return convertView;
    }
   static class ChaHolder{
        private TextView tv_name;
    }
}
