package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.ActivityLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by xiaoke on 2016/9/20.
 */
public class Simple_TextAdapter extends BaseAdapter {
    private Context context;
    private List<ActivityLabel> list=new ArrayList<>();
    private String labelId;
    public Simple_TextAdapter(Context context, List<ActivityLabel> list, String labelIds){
        this.context=context;
        this.list=list;
        this.labelId=labelIds;
    }

    public void update(List<ActivityLabel> labellist,String labelId) {
        this.list=labellist;
        this.labelId=labelId;
        notifyDataSetChanged();
    }
//    /**
//     * itemClick接口回调
//     */
//    public interface CheckedChangeListener {
//        void CheckedChangeListener(View view, int position,boolean flag);
//    }
//
//    private CheckedChangeListener mOnItemClickListener;
//
//    public void setOnItemClickListener(CheckedChangeListener mOnItemClickListener) {
//        this.mOnItemClickListener = mOnItemClickListener;
//    }
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
        final Holder holder;
        if (convertView!=null&&convertView.getTag()!=null){
            holder = (Holder) convertView.getTag();
        }else {
            convertView = View.inflate(context, R.layout.item_text, null);
            holder=new Holder();
            holder.rb_item= (TextView) convertView.findViewById(R.id.rb_item);
            convertView.setTag(holder);
        }
        holder.rb_item.setText(list.get(position).getLabelName());
        if (TextUtils.equals(list.get(position).getId(), labelId)){
            holder.rb_item.setBackgroundResource(R.color.bg_simple);
        }else {
            holder.rb_item.setBackgroundResource(R.color.white);
        }

//        if (mOnItemClickListener!=null){
//            holder.rb_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        mOnItemClickListener.CheckedChangeListener(holder.rb_item,position,isChecked);
//                }
//            });
//        }
        return convertView;
    }



    class Holder{
        private TextView rb_item;
    }
}
