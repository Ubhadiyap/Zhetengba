package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.ActivityMess;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 档期消息适配器
 * Created by wangbin on 16/5/13.
 */
public class DqMesAdapter extends BaseAdapter {
    private Context context;
    private List<ActivityMess> list = new ArrayList<>();
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public DqMesAdapter(Context context, List<ActivityMess> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.lv_dqmes_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).getType().equals(0 + "")) {
            viewHolder.llInvitation.setVisibility(View.GONE);
        } else if (list.get(position).getType().equals(1 + "") && list.get(position).getMesstype().equals(1 + "")) {
            viewHolder.llInvitation.setVisibility(View.VISIBLE);
        }
        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getUserIcon()), viewHolder.civhead, optionsImag);
        if (!TextUtils.isEmpty(list.get(position).getPetName())) {
            viewHolder.tv_name.setText(list.get(position).getPetName());
        }
        if (!TextUtils.isEmpty(list.get(position).getMessage())) {
            viewHolder.tv_second.setText(list.get(position).getMessage());
        }

        if (!TextUtils.isEmpty(list.get(position).getCreateTime())) {
            viewHolder.tvTime.setText(ZtinfoUtils.timeChange(Long.parseLong(list.get(position).getCreateTime())));
        }
        viewHolder.tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.tvAccept.setClickable(false);
                viewHolder.tvRefuse.setClickable(false);
                RequestManager.getScheduleManager().agreeActivity(list.get(position).getActivityId(), list.get(position).getScheduleId(), new ResultCallback<ResultBean<String>>() {
                    @Override
                    public void onError(int status, String errorMsg) {
                        viewHolder.tvAccept.setClickable(true);
                        viewHolder.tvRefuse.setClickable(true);
                    }

                    @Override
                    public void onResponse(ResultBean<String> response) {
                        viewHolder.tvAccept.setClickable(false);
                        viewHolder.tvRefuse.setClickable(false);
                        viewHolder.tvAccept.setBackgroundColor(Color.GRAY);
                        MyToastUtils.showShortToast(context, "已经同意！");

                    }
                });
            }
        });
        viewHolder.tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.tvAccept.setClickable(false);
                viewHolder.tvRefuse.setClickable(false);
                RequestManager.getScheduleManager().refuseActivity(list.get(position).getActivityId(), list.get(position).getScheduleId(), new ResultCallback<ResultBean<String>>() {
                    @Override
                    public void onError(int status, String errorMsg) {
                        viewHolder.tvAccept.setClickable(true);
                        viewHolder.tvRefuse.setClickable(true);
                    }

                    @Override
                    public void onResponse(ResultBean<String> response) {
                        viewHolder.tvAccept.setClickable(false);
                        viewHolder.tvRefuse.setClickable(false);
                        viewHolder.tvRefuse.setBackgroundColor(Color.GRAY);
                        MyToastUtils.showShortToast(context, "已经拒绝！");
                    }
                });
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public final CircleImageView civhead;
        public final TextView tvTime;
        public final TextView tvAccept;
        public final TextView tvRefuse;
        public final LinearLayout llInvitation;
        public final TextView tv_name;
        public final TextView tv_second;
        public final View root;

        public ViewHolder(View root) {
            tv_second = (TextView) root.findViewById(R.id.tv_second);
            tv_name = (TextView) root.findViewById(R.id.tv_petName);
            civhead = (CircleImageView) root.findViewById(R.id.civhead);
            tvTime = (TextView) root.findViewById(R.id.tvTime);
            tvAccept = (TextView) root.findViewById(R.id.tvAccept);
            tvRefuse = (TextView) root.findViewById(R.id.tvRefuse);
            llInvitation = (LinearLayout) root.findViewById(R.id.llInvitation);
            this.root = root;
        }
    }
}
