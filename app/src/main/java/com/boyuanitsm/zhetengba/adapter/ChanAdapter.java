package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.ChanelTextAct;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.utils.ScreenTools;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.NineGridlayout;
import com.boyuanitsm.zhetengba.view.ShareDialog;

import java.util.List;

/**
 * 频道listview适配器
 * Created by xiaoke on 2016/5/3.
 */
public class ChanAdapter extends BaseAdapter {
    private Context context;
    private List<List<ImageInfo>> dateList;
    public ChanAdapter(Context context,List<List<ImageInfo>> dateList){
        this.context=context;
        this.dateList=dateList;
    }
    @Override
    public int getCount() {
        return dateList.size();
    }

    @Override
    public Object getItem(int position) {
        return dateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       CaViewHolder viewHolder;
        List<ImageInfo> itemList = dateList.get(position);
        if (convertView!=null&&convertView.getTag()!=null){
            viewHolder= (CaViewHolder) convertView.getTag();
        }else {
           convertView= View.inflate(context,R.layout.item_chanle,null);
            viewHolder=new CaViewHolder();
            viewHolder.ll_share= (LinearLayout) convertView.findViewById(R.id.ll_share);
            viewHolder.ll_answer = (LinearLayout) convertView.findViewById(R.id.ll_answer);
            viewHolder.iv_ch_image= (NineGridlayout) convertView.findViewById(R.id.iv_ch_image);
            viewHolder.iv_oneimage = (CustomImageView) convertView.findViewById(R.id.iv_oneimage);
            convertView.setTag(viewHolder);
        }
        if (itemList.isEmpty() || itemList.isEmpty()) {
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.GONE);
        } else if (itemList.size() == 1) {
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.VISIBLE);
            handlerOneImage(viewHolder, itemList.get(0));
        } else {
            viewHolder.iv_ch_image.setVisibility(View.VISIBLE);
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.iv_ch_image.setImagesData(itemList);
        }
        //分享对话框
        viewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog dialog=new ShareDialog(context);
                dialog.show();
            }
        });
        //频道正文
        viewHolder.ll_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context,ChanelTextAct.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    class CaViewHolder{
        private LinearLayout ll_share;
        private LinearLayout ll_answer;
        private CustomImageView iv_oneimage;
        private NineGridlayout iv_ch_image;

    }
    private void handlerOneImage(CaViewHolder viewHolder, ImageInfo image) {
        int totalWidth;
        int imageWidth;
        int imageHeight;
        ScreenTools screentools = ScreenTools.instance(context);
        totalWidth = screentools.getScreenWidth() - screentools.dip2px(80);
        imageWidth = screentools.dip2px(image.getWidth());
        imageHeight = screentools.dip2px(image.getHeight());
        if (image.getWidth() <= image.getHeight()) {
            if (imageHeight > totalWidth) {
                imageHeight = totalWidth;
                imageWidth = (imageHeight * image.getWidth()) / image.getHeight();
            }
        } else {
            if (imageWidth > totalWidth) {
                imageWidth = totalWidth;
                imageHeight = (imageWidth * image.getHeight()) / image.getWidth();
            }
        }
        ViewGroup.LayoutParams layoutparams = viewHolder.iv_oneimage.getLayoutParams();
        layoutparams.height = imageHeight;
        layoutparams.width = imageWidth;
        viewHolder.iv_oneimage.setLayoutParams(layoutparams);
        viewHolder.iv_oneimage.setClickable(true);
        viewHolder.iv_oneimage.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHolder.iv_oneimage.setImageUrl(image.getUrl());

    }
}
