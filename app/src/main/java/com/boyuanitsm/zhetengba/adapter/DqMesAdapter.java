package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.hyphenate.easeui.widget.CircleImageView;

/**
 * 档期消息适配器
 * Created by wangbin on 16/5/13.
 */
public class DqMesAdapter extends BaseAdapter {
    private Context context;

    public DqMesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 8;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.lv_dqmes_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            viewHolder.llInvitation.setVisibility(View.VISIBLE);
        } else {
            viewHolder.llInvitation.setVisibility(View.GONE);
        }
        return convertView;
    }

    public class ViewHolder {
        public final CircleImageView civhead;
        public final TextView tvTime;
        public final TextView tvAccept;
        public final TextView tvRefuse;
        public final LinearLayout llInvitation;
        public final View root;

        public ViewHolder(View root) {
            civhead = (CircleImageView) root.findViewById(R.id.civhead);
            tvTime = (TextView) root.findViewById(R.id.tvTime);
            tvAccept = (TextView) root.findViewById(R.id.tvAccept);
            tvRefuse = (TextView) root.findViewById(R.id.tvRefuse);
            llInvitation = (LinearLayout) root.findViewById(R.id.llInvitation);
            this.root = root;
        }
    }
}
