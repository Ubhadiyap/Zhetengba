package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.PicShowDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by xiaoke on 2016/5/18.
 */
public class PicGdAdapter extends BaseAdapter {
    private List<ImageInfo> list;
    private Context context;
    private int position;

    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    public  PicGdAdapter(Context context,List<ImageInfo> list,int position){
        this.context=context;
        this.list=list;
        this.position=position;
    }
    @Override
    public int getCount() {
        return list.size();
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
       convertView= View.inflate(context, R.layout.item_pic_gd, null);
        CustomImageView iv_chanel_pic = (CustomImageView) convertView.findViewById(R.id.iv_chanel_pic);
        ImageLoader.getInstance().displayImage(list.get(position).getUrl(),iv_chanel_pic,optionsImag);
        iv_chanel_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicShowDialog dialog=new PicShowDialog(context,list,position);
                dialog.show();
            }
        });
        return convertView ;
    }
}
