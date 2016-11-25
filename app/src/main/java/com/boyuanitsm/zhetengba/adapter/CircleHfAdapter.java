package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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
    public CircleHfAdapter(Context context,List<CircleEntity> list){
        this.context=context;
        this.list=list;
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
            convertView.setTag(holder);
        }
      if (!TextUtils.isEmpty(list.get(position).getPetName())){
          String str=list.get(position).getPetName()+"回复"+list.get(position).getCommentedUsername()+":"+list.get(position).getCommentContent();
          SpannableStringBuilder builder = new SpannableStringBuilder(str);
          //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
          ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#52c791"));
          ForegroundColorSpan redSpan2 = new ForegroundColorSpan(Color.parseColor("#52c791"));
          builder.setSpan(redSpan, 0, list.get(position).getPetName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
          builder.setSpan(redSpan2,list.get(position).getPetName().length()+2,list.get(position).getPetName().length()+list.get(position).getCommentedUsername().length()+2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
          holder.tv_hf.setText(builder);
      }
        return convertView;
    }
    class Holder{
        private TextView tv_hf;
    }
}
