package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.ShareDialogAct;
import com.boyuanitsm.zhetengba.activity.circle.CommentAct;
import com.boyuanitsm.zhetengba.activity.mine.PersonalmesAct;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ScreenTools;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.view.PicShowDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 圈子：圈子朋友主页
 * Created by xiaoke on 2016/5/12.
 */
public class CirListViewAdapter extends BaseAdapter {
    private Context context;
    private List<List<ImageInfo>> dateList;

    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public CirListViewAdapter(Context context,List<List<ImageInfo>> dateList){
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
        ViewHolder viewHolder = null;
      final   List<ImageInfo> itemList = dateList.get(position);
        if (convertView != null && convertView.getTag() != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_circle, null);
            viewHolder.iv_ch_head = (ImageView)convertView.findViewById(R.id.iv_ch_head);
            viewHolder.iv_ch_image = (MyGridView) convertView.findViewById(R.id.iv_ch_image);
            viewHolder.iv_oneimage= (CustomImageView) convertView.findViewById(R.id.iv_oneimage);
            viewHolder.ll_two= (LinearLayout) convertView.findViewById(R.id.ll_two);
            viewHolder.iv_two_one= (CustomImageView) convertView.findViewById(R.id.iv_two_one);
            viewHolder.iv_two_two= (CustomImageView) convertView.findViewById(R.id.iv_two_two);
            viewHolder.iv_two_three= (CustomImageView) convertView.findViewById(R.id.iv_two_three);
            viewHolder.iv_two_four= (CustomImageView) convertView.findViewById(R.id.iv_two_four);
            //分享，评论
            viewHolder.ll_share= (LinearLayout) convertView.findViewById(R.id.ll_share);
            viewHolder.ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
            convertView.setTag(viewHolder);
        }
        if (itemList.isEmpty() || itemList.isEmpty()) {
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
        } else if (itemList.size() == 1) {
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.VISIBLE);
            Bitmap bitmap=ImageLoader.getInstance().loadImageSync(Uitls.imageFullUrl(itemList.get(0).getUrl()),optionsImag);
            itemList.get(0).setWidth(bitmap.getWidth());
            itemList.get(0).setHeight(bitmap.getHeight());
            LayoutHelperUtil.handlerOneImage(context,itemList.get(0),viewHolder.iv_oneimage);
//            handlerOneImage(viewHolder, itemList.get(0));
            viewHolder.iv_oneimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(context, itemList, 0);
                    dialog.show();
                }
            });
        } else if (itemList.size()==4){
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.VISIBLE);
//            viewHolder.iv_two_one.setImageUrl(itemList.get(0).getUrl());
//            viewHolder.iv_two_two.setImageUrl(itemList.get(1).getUrl());
//            viewHolder.iv_two_three.setImageUrl(itemList.get(2).getUrl());
//            viewHolder.iv_two_four.setImageUrl(itemList.get(3).getUrl());
            ImageLoader.getInstance().displayImage(itemList.get(0).getUrl(), viewHolder.iv_two_one, optionsImag);
            ImageLoader.getInstance().displayImage(itemList.get(1).getUrl(),viewHolder.iv_two_two,optionsImag);
            ImageLoader.getInstance().displayImage(itemList.get(2).getUrl(),viewHolder.iv_two_three,optionsImag);
            ImageLoader.getInstance().displayImage(itemList.get(3).getUrl(),viewHolder.iv_two_four,optionsImag);
            viewHolder.iv_two_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(context, itemList, 0);
                    dialog.show();
                }
            });

            viewHolder.iv_two_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(context, itemList,1);
                    dialog.show();
                }
            });

            viewHolder.iv_two_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(context, itemList,2);
                    dialog.show();
                }
            });

            viewHolder.iv_two_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(context, itemList,3);
                    dialog.show();
                }
            });

        }else {
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
            viewHolder.iv_ch_image.setVisibility(View.VISIBLE);
            viewHolder.iv_ch_image.setNumColumns(3);
            PicGdAdapter adapter=new PicGdAdapter(context,itemList,position);
            viewHolder.iv_ch_image.setAdapter(adapter);

        }
        //进入个人资料主页界面
        viewHolder.iv_ch_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PersonalmesAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //分享对话框
        viewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShareDialog dialog=new ShareDialog(context);
//                dialog.show();
                Intent intent=new Intent(context, ShareDialogAct.class);
                context.startActivity(intent);
            }
        });

        //评论
        viewHolder.ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context, CommentAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        return convertView;
    }
    class ViewHolder{
        private LinearLayout ll_share;
        private LinearLayout ll_comment;
        private ImageView iv_ch_head;
        private CustomImageView iv_oneimage;
        private MyGridView iv_ch_image;
        private LinearLayout ll_two;
        private CustomImageView iv_two_one,iv_two_two,iv_two_three,iv_two_four;

    }
}
