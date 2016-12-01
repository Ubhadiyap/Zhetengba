package com.boyuanitsm.zhetengba.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.PersonalAct;
import com.boyuanitsm.zhetengba.activity.circle.ChanelTextAct;
import com.boyuanitsm.zhetengba.activity.circle.CircleTextAct;
import com.boyuanitsm.zhetengba.activity.circle.SquareAct;
import com.boyuanitsm.zhetengba.bean.IconFilePath;
import com.boyuanitsm.zhetengba.bean.SquareInfo;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by xiaoke on 2016/11/29.
 */
public class SqMessAdapter extends BaseAdapter {
    private Context context;
    private List<SquareInfo> list;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    private DisplayImageOptions option= new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.tum)
            .showImageOnFail(R.mipmap.tum).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    public SqMessAdapter(Context context, List<SquareInfo> list) {
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
        ViewHolder holder = null;
        if (convertView != null && convertView.getTag() != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_sq_mess, null);
            holder.cvHead= (CircleImageView) convertView.findViewById(R.id.cv_head);
            holder.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_talk= (TextView) convertView.findViewById(R.id.tv_talk);
            holder.iv_talk= (ImageView) convertView.findViewById(R.id.iv_talkImage);
            holder.tv_huifu= (TextView) convertView.findViewById(R.id.tv_huifu);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        }
        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getUserIcon()),holder.cvHead,options);
        if (!TextUtils.isEmpty(list.get(position).getPetName())){
            holder.tv_name.setText(list.get(position).getPetName());
        }
        if (!TextUtils.isEmpty(list.get(position).getImgTalk())){
            holder.iv_talk.setVisibility(View.VISIBLE);
            holder.tv_talk.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getImgTalk()),holder.iv_talk,option);
        }else {
            holder.iv_talk.setVisibility(View.GONE);
            holder.tv_talk.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(list.get(position).getCommentTalk())){
                holder.tv_talk.setText("\""+list.get(position).getCommentTalk()+"\"");
            }
        }
        if (!TextUtils.isEmpty(list.get(position).getMessage())){
            holder.tv_huifu.setText(list.get(position).getMessage());
        }
        if (!TextUtils.isEmpty(list.get(position).getCommentContent())){
            holder.tv_content.setVisibility(View.VISIBLE);
            holder.tv_content.setText(list.get(position).getCommentContent());
        }else {
            holder.tv_content.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(list.get(position).getCreateTime())){
            holder.tv_time.setText(ZtinfoUtils.timeChange(Long.parseLong(list.get(position).getCreateTime())));
        }
        final View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonalAct.class);
                Bundle bundle = new Bundle();
                if (!TextUtils.isEmpty(list.get(position).getUserId())) {
                    bundle.putString("userId", list.get(position).getUserId());
                }else if (!TextUtils.isEmpty(list.get(position).getSender())){
                    bundle.putString("userId", list.get(position).getSender());
                }
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
        holder.cvHead.setOnClickListener(listener);
        holder.tv_name.setOnClickListener(listener);
        View.OnClickListener listener1=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ChanelTextAct.class);
//                intent.putExtra("circleEntity", circleInfoList.get(position));
                intent.putExtra("channelId", list.get(position).getCircleTalkId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
        holder.tv_content.setOnClickListener(listener1);
        holder.iv_talk.setOnClickListener(listener1);
        holder.tv_huifu.setOnClickListener(listener1);
        return convertView;
    }

    public void notifyDataChange(List<SquareInfo> list) {
        this.list=list;
        notifyDataSetChanged();
    }

    class ViewHolder {
        CircleImageView cvHead;
        TextView tv_name;
        TextView tv_huifu;
        TextView tv_content;
        TextView tv_time;
        ImageView iv_talk;
        TextView tv_talk;
    }
}
