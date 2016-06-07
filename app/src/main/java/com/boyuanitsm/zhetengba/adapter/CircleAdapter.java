package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.ShareDialogAct;
import com.boyuanitsm.zhetengba.activity.circle.CircleTextAct;
import com.boyuanitsm.zhetengba.activity.circle.CirxqAct;
import com.boyuanitsm.zhetengba.activity.circle.CommentAct;
import com.boyuanitsm.zhetengba.activity.mess.PerpageAct;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ScreenTools;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.view.PicShowDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 圈子列表适配器
 * Created by xiaoke on 2016/5/4.
 */
public class CircleAdapter extends BaseAdapter {
    private Context context;
    private List<List<ImageInfo>> dateList;
    private List<CircleEntity> list;
    private boolean flag=false;
    ViewHolder viewHolder = null;
    int clickPos;

    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public CircleAdapter(Context context, List<List<ImageInfo>> dateList) {
        this.context = context;
        this.dateList = dateList;

    }
    public CircleAdapter(Context context, List<List<ImageInfo>> dateList,List<CircleEntity> list) {
        this.context = context;
        this.dateList = dateList;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = null;
        final List<ImageInfo> itemList = dateList.get(position);
        if (convertView != null && convertView.getTag() != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_circle, null);
            viewHolder.ivChHead = (ImageView) convertView.findViewById(R.id.iv_ch_head);
            viewHolder.tvChNiName = (TextView) convertView.findViewById(R.id.tv_ch_niName);
            viewHolder.ivChGendar = (ImageView) convertView.findViewById(R.id.iv_ch_gendar);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.ll_like= (LinearLayout) convertView.findViewById(R.id.like);
            viewHolder.ll_share = (LinearLayout) convertView.findViewById(R.id.ll_share);
            viewHolder.ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
            viewHolder.iv_ch_image = (MyGridView) convertView.findViewById(R.id.iv_ch_image);
            viewHolder.iv_oneimage = (CustomImageView) convertView.findViewById(R.id.iv_oneimage);
            viewHolder.tv_cir_name = (TextView) convertView.findViewById(R.id.tv_cir_name);
            viewHolder.ll_two = (LinearLayout) convertView.findViewById(R.id.ll_two);
            viewHolder.iv_two_one = (CustomImageView) convertView.findViewById(R.id.iv_two_one);
            viewHolder.iv_two_two = (CustomImageView) convertView.findViewById(R.id.iv_two_two);
            viewHolder.iv_two_three = (CustomImageView) convertView.findViewById(R.id.iv_two_three);
            viewHolder.iv_two_four = (CustomImageView) convertView.findViewById(R.id.iv_two_four);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.znum= (TextView) convertView.findViewById(R.id.znum);
            viewHolder.cnum= (TextView) convertView.findViewById(R.id.cnum);
            viewHolder.snum= (TextView) convertView.findViewById(R.id.snum);
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
//            handlerOneImage(viewHolder, itemList.get(0));
            LayoutHelperUtil.handlerOneImage(context, itemList.get(0), viewHolder.iv_oneimage);
            viewHolder.iv_oneimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(context, itemList, 0);
                    dialog.show();
                }
            });
        } else if (itemList.size() == 4) {
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.VISIBLE);
//            viewHolder.iv_two_one.setImageUrl(itemList.get(0).getUrl());

            ImageLoader.getInstance().displayImage(itemList.get(0).getUrl(), viewHolder.iv_two_one, optionsImag);
            ImageLoader.getInstance().displayImage(itemList.get(1).getUrl(), viewHolder.iv_two_two, optionsImag);
            ImageLoader.getInstance().displayImage(itemList.get(2).getUrl(), viewHolder.iv_two_three, optionsImag);
            ImageLoader.getInstance().displayImage(itemList.get(3).getUrl(), viewHolder.iv_two_four, optionsImag);
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
                    PicShowDialog dialog = new PicShowDialog(context, itemList, 1);
                    dialog.show();
                }
            });

            viewHolder.iv_two_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(context, itemList, 2);
                    dialog.show();
                }
            });

            viewHolder.iv_two_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(context, itemList, 3);
                    dialog.show();
                }
            });

        } else {
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
            viewHolder.iv_ch_image.setVisibility(View.VISIBLE);
            viewHolder.iv_ch_image.setNumColumns(3);
            PicGdAdapter adapter = new PicGdAdapter(context, itemList, position);
            viewHolder.iv_ch_image.setAdapter(adapter);

        }

        if(list!=null){
//            if(!TextUtils.isEmpty(list.get(position).getUserId())){
//                viewHolder.tvChNiName.setText(list.get(position).getUserId());
//            }
            if(!TextUtils.isEmpty(list.get(position).getCreateTime())){
                viewHolder.tvTime.setText(ZtinfoUtils.timeToDate(Long.parseLong(list.get(position).getCreateTime())));
            }
            if(!TextUtils.isEmpty(list.get(position).getTalkContent())){
                viewHolder.tv_content.setText(list.get(position).getTalkContent());
            }
            if(!TextUtils.isEmpty(list.get(position).getCircleName())){
                viewHolder.tv_cir_name.setText(list.get(position).getCircleName());
            }
            if(!TextUtils.isEmpty(list.get(position).getLikeCounts()+"")){
                viewHolder.znum.setText(list.get(position).getLikeCounts()+"");
            }else {
                viewHolder.znum.setText("0");
            }
            if(!TextUtils.isEmpty(list.get(position).getCommentCounts()+"")){
                viewHolder.cnum.setText(list.get(position).getCommentCounts()+"");
            }else {
                viewHolder.cnum.setText("0");
            }
            if(!TextUtils.isEmpty(list.get(position).getSharedCounts()+"")){
                viewHolder.snum.setText(list.get(position).getSharedCounts()+"");
            }else {
                viewHolder.snum.setText("0");
            }
        }
        //点击用户头像，进入用户圈子主页
        viewHolder.ivChHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, PerpageAct.class);
                //需要开启新task,否则会报错
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        //点击户外圈进入圈子主页
        viewHolder.tv_cir_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, CirxqAct.class);
                //需要开启新task,否则会报错
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        //点赞
        viewHolder.ll_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPos=position;

                if (!flag){
                    addCircleLike(list.get(position).getId());
                }else {
                    removeCircleLike(list.get(position).getId());
                }
            }
        });
        //分享对话框
        viewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShareDialog dialog=new ShareDialog(context);
//                dialog.show();
                Intent intent = new Intent(context, ShareDialogAct.class);
                context.startActivity(intent);
            }
        });
        //评论
        viewHolder.ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, CircleTextAct.class);
                intent.putExtra("circleEntity", list.get(position));
                intent.putExtra("circleId", list.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, CircleTextAct.class);
                intent.putExtra("circleEntity",list.get(position));
                intent.putExtra("circleId",list.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    class ViewHolder {
        public ImageView ivChHead;
        public TextView tvChNiName;
        public ImageView ivChGendar;
        public TextView tvTime;
        public MyGridView iv_ch_image;
        public CustomImageView iv_oneimage;
        public TextView tv_cir_name;
        private LinearLayout ll_two;
        private CustomImageView iv_two_one, iv_two_two, iv_two_three, iv_two_four;
        private LinearLayout ll_like;
        private LinearLayout ll_share;
        private LinearLayout ll_comment;
        private TextView tv_content;
        private TextView znum;
        private TextView cnum;
        private TextView snum;

    }

    /**
     * 圈子说说点赞
     * @param circleTalkId
     */
    private void addCircleLike(String circleTalkId ){
        RequestManager.getTalkManager().addCircleLike(circleTalkId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                flag=true;
                list.get(clickPos).setLikeCounts(list.get(clickPos).getLikeCounts()+1);
                notifyDataSetChanged();
            }
        });
    }
    /**
     * 取消圈子说说点赞
     * @param circleTalkId
     */
    private void removeCircleLike(String circleTalkId ){
        RequestManager.getTalkManager().removeCircleLike(circleTalkId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                flag=false;
                list.get(clickPos).setLikeCounts(list.get(clickPos).getLikeCounts()-1);
                notifyDataSetChanged();
            }
        });
    }

}
