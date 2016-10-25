package com.boyuanitsm.zhetengba.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.photo.ImageBrowserActivity;
import com.boyuanitsm.zhetengba.activity.photo.PicSelectActivity;
import com.boyuanitsm.zhetengba.bean.ImageBean;
import com.boyuanitsm.zhetengba.utils.MyBitmapUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bitch-1 on 2016/5/16.
 */
public class GvPhotoAdapter extends BaseAdapter {
    private List<ImageBean> list;
    private Activity context;
    public static final int PHOTO_CODE0 = 0x456;
    public static final int ADD_PHOTO_CODE = 0x300;

    private int maxCount;

    public GvPhotoAdapter(List<ImageBean> list, int maxCount, Activity context) {
        this.list = list;
        this.context = context;
        this.maxCount=maxCount;
    }

    public void notifyDataChange(List<ImageBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size() == maxCount? list.size() : list.size() + 1;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.photo_gv_item, null);
            viewHolder.ivPhoto = (ImageView) convertView
                    .findViewById(R.id.ivPhoto);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list.size() < maxCount) {
            if (position == list.size()) {
                viewHolder.ivPhoto.setImageBitmap(BitmapFactory.decodeResource(
                        context.getResources(), R.mipmap.add_image));
                viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,
                                PicSelectActivity.class);
                        intent.putExtra("select_count", list.size());
                        intent.putExtra(PicSelectActivity.TOTAL_COUNT,maxCount);
                        context.startActivityForResult(intent, ADD_PHOTO_CODE);
                    }
                });
            } else {
                Bitmap bitmap=MyBitmapUtils.LoadBigImg(list
                        .get(position).path, 70, 70);
                if (bitmap==null){
                    //判断所选图片是否为破损图片
                    MyToastUtils.showShortToast(context,"选择图片有误，请重新选择！");
                    viewHolder.ivPhoto.setImageResource(R.mipmap.tum);
                }else {
                    viewHolder.ivPhoto.setImageBitmap(bitmap);
                }
                viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,
                                ImageBrowserActivity.class);
                        intent.putExtra("images", (Serializable) list);
                        intent.putExtra("position", position);
                        intent.putExtra("isdel", true);
                        context.startActivityForResult(intent, PHOTO_CODE0);
                    }
                });
            }
        } else {
            viewHolder.ivPhoto.setImageBitmap(MyBitmapUtils.LoadBigImg(list
                    .get(position).path, 70, 70));
            viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,
                            ImageBrowserActivity.class);
                    intent.putExtra("images", (Serializable) list);
                    intent.putExtra("position", position);
                    intent.putExtra("isdel", true);
                    context.startActivityForResult(intent, PHOTO_CODE0);
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        ImageView ivPhoto;
    }
}
