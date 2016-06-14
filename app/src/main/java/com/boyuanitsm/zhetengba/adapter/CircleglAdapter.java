package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bitch-1 on 2016/5/6.
 */
public class CircleglAdapter extends BaseAdapter {
    private Context context;
    private List<CircleEntity> list=new ArrayList<>();
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public CircleglAdapter(Context context) {
        this.context = context;
    }
    public CircleglAdapter(Context context,List<CircleEntity> list) {
        this.context = context;
        this.list=list;
    }
    public void notifyChange(List<CircleEntity> list){
        this.list=list;
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
        if(convertView==null) {
            holder=new ViewHolder();
            convertView = View.inflate(context, R.layout.item_circlegl, null);
            holder.head= (CircleImageView) convertView.findViewById(R.id.iv_sysmes);
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.notice= (TextView) convertView.findViewById(R.id.notice);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        if (list!=null&&list.size()>0){
            if(!TextUtils.isEmpty(list.get(position).getCircleLogo())) {
                ImageLoader.getInstance().displayImage(IZtbUrl.BASE_URL + list.get(position).getCircleLogo(), holder.head,options);
            }
            if(!TextUtils.isEmpty(list.get(position).getCircleName())) {
                holder.name.setText(list.get(position).getCircleName());
            }
            if(!TextUtils.isEmpty(list.get(position).getNotice())) {
                holder.notice.setText("公告："+list.get(position).getNotice());
            }else {
                holder.notice.setText("公告：暂无");
            }
        }
        return convertView;
    }

    class ViewHolder{
        private CircleImageView head;
        private TextView name;
        private TextView notice;
    }
}
