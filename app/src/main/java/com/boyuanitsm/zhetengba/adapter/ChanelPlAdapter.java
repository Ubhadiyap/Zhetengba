package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.utils.EmojUtils;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoke on 2016/9/26.
 */
public class ChanelPlAdapter extends BaseAdapter {
    private Context context;
    private List<ChannelTalkEntity> list=new ArrayList<>();
    public ChanelPlAdapter(Context context, List<ChannelTalkEntity> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
      return list.size()>5?5:list.size();
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
        PlHolder holder;
        if (convertView!=null&&convertView.getTag()!=null){
            holder = (PlHolder) convertView.getTag();
        }else {
            holder=new PlHolder();
            convertView= View.inflate(context, R.layout.item_pl_chanel, null);
            holder.tv_ct = (TextView) convertView.findViewById(R.id.tv_ct);
        }
        if (!TextUtils.isEmpty(list.get(position).getPetName())){
            SpannableStringBuilder style1=new SpannableStringBuilder(list.get(position).getPetName()+ "："+ list.get(position).getCommentContent());
            style1.setSpan(new ForegroundColorSpan(Color.parseColor("#52c791")), 0, list.get(position).getPetName().length()+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.tv_ct.setText(style1);
        }

        return convertView;
    }
    class  PlHolder{
        private TextView tv_ct;
    }
}
