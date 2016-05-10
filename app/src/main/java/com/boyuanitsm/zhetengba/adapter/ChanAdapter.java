package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.ShareDialog;

/**
 * 频道listview适配器
 * Created by xiaoke on 2016/5/3.
 */
public class ChanAdapter extends BaseAdapter {
    private Context context;
    public ChanAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       CaViewHolder viewHolder;
        if (convertView!=null&&convertView.getTag()!=null){
            viewHolder= (CaViewHolder) convertView.getTag();
        }else {
           convertView= View.inflate(context,R.layout.item_chanle,null);
            viewHolder=new CaViewHolder();
            viewHolder.ll_share= (LinearLayout) convertView.findViewById(R.id.ll_share);
            convertView.setTag(viewHolder);
        }
        viewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog dialog=new ShareDialog(context);
                dialog.show();
            }
        });
        return convertView;
    }
    class CaViewHolder{
        private LinearLayout ll_share;
    }
}
