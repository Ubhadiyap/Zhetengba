package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.BillDateBean;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;

import java.util.List;

/**
 * Created by bitch-1 on 2016/9/5.
 */
public class WallerAdapter extends BaseAdapter {
    private Context context;
    private List<BillDateBean>list;

    public WallerAdapter(Context context,List<BillDateBean>list) {
        this.context = context;
        this.list=list;
    }

    public void notify(List<BillDateBean> list) {
        this.list = list;
        notifyDataSetChanged();
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
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView =View.inflate(context, R.layout.item_wallet,null);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_leixing= (TextView) convertView.findViewById(R.id.tv_leixing);
            holder.tv_jine= (TextView) convertView.findViewById(R.id.tv_jiner);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        if(list!=null&&list.size()>0){
            holder.tv_time.setText(ZhetebaUtils.timeToDater(Long.parseLong(list.get(position).getModifyTime())));
            if(list.get(position).getOperationMode().equals("0")){
                holder.tv_leixing.setText("提现记录");
                holder.tv_jine.setTextColor(Color.parseColor("#52c791"));
                holder.tv_jine.setText("-"+list.get(position).getAmount());
            }
            if(list.get(position).getOperationMode().equals("1")){
                holder.tv_leixing.setText("抽奖奖励");
                holder.tv_jine.setTextColor(Color.parseColor("#eb5e5d"));
                holder.tv_jine.setText("+"+list.get(position).getAmount());
            }
            if(list.get(position).getOperationMode().equals("2")){
                holder.tv_leixing.setText("邀请奖励");
                holder.tv_jine.setTextColor(Color.parseColor("#eb5e5d"));
                holder.tv_jine.setText("+"+list.get(position).getAmount());
            }
        }

        return convertView;
    }

    class ViewHolder{
        private TextView tv_time,tv_leixing,tv_jine;
    }
}
