package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;

import java.util.List;

/**
 * 圈子正文列表adapter
 * Created by xiaoke on 2016/5/12.
 */
public class CircleTextAdapter extends BaseAdapter {
    private Context context;
    private List<CircleEntity> list;
//    private String[] str=new String[]{"小明","小红","小红","小红","小红","小红","小红"};
    public CircleTextAdapter(Context context){
        this.context=context;
    }
    public CircleTextAdapter(Context context, List<CircleEntity> list){
        this.context=context;
        this.list=list;
    }
    public void notifyChange(List<CircleEntity> list){
        this.list=list;
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
        ChaHolder chaHolder = null;
        if (convertView!=null&&convertView.getTag()!=null){
            chaHolder= (ChaHolder) convertView.getTag();
        }else {
            chaHolder=new ChaHolder();
            convertView=View.inflate(context,R.layout.item_chane_text,null);
            chaHolder.head= (CircleImageView) convertView.findViewById(R.id.head);
            chaHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_user_name);
            chaHolder.time= (TextView) convertView.findViewById(R.id.time);
            chaHolder.content= (TextView) convertView.findViewById(R.id.tv_comment_text);
            convertView.setTag(chaHolder);
        }
        if(list!=null&&list.size()>0) {
//            if(!TextUtils.isEmpty(list.get(position).getCommentUserId())) {
//                chaHolder.tv_name.setText(list.get(position).getCommentUserId());
//            }
            if(!TextUtils.isEmpty(list.get(position).getCommentTime())){
                chaHolder.time.setText(ZtinfoUtils.timeToDate(Long.parseLong(list.get(position).getCommentTime())));
            }
            if(!TextUtils.isEmpty(list.get(position).getCommentContent())){
                chaHolder.content.setText(list.get(position).getCommentContent());
            }
        }
        return convertView;
    }
   static class ChaHolder{
       private CircleImageView head;
       private TextView tv_name;
       private TextView time;
       private TextView content;
    }
}
