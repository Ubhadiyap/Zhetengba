package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.boyuanitsm.zhetengba.activity.mess.PerpageAct;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.view.PicShowDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息里面个人主页圈子动态frg
 * Created by bitch-1 on 2016/5/24.
 */
public class PpdtfrgAdapter extends BaseAdapter {
    private Context context;
    private List<List<ImageInfo>> dateList;
    private List<CircleEntity> circleTalkEntityList=new ArrayList<>();
    private List<UserInfo> userInfoList;
    ViewHolder viewHolder = null;
    int clickPos;
    private boolean flag;
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    public PpdtfrgAdapter(Context context, List<CircleEntity> circleEntityList, List<UserInfo> userEntity) {
        this.context = context;
        this.circleTalkEntityList=circleEntityList;
        this.userInfoList=userEntity;
    }

    @Override
    public int getCount() {
        return circleTalkEntityList==null?0:circleTalkEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return circleTalkEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String[] str=new String[]{};
        if (!TextUtils.isEmpty(circleTalkEntityList.get(position).getTalkImage())){
            str=ZtinfoUtils.convertStrToArray(circleTalkEntityList.get(position).getTalkImage());
        }
//        final List<ImageInfo> itemList = dateList.get(position);
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
           viewHolder.zimg= (ImageView) convertView.findViewById(R.id.zimg);
            convertView.setTag(viewHolder);
        }
        if (str==null) {
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
        } else if (str.length== 1) {
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.VISIBLE);
//            handlerOneImage(viewHolder, itemList.get(0));
//            展示图片，暂时注掉
//            LayoutHelperUtil.handlerOneImage(context, str[0], viewHolder.iv_oneimage);
//            viewHolder.iv_oneimage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PicShowDialog dialog = new PicShowDialog(context, str, 0);
//                    dialog.show();
//                }
//            });
        } else if (str.length == 4) {
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.VISIBLE);
//            viewHolder.iv_two_one.setImageUrl(itemList.get(0).getUrl());

            ImageLoader.getInstance().displayImage(str[0], viewHolder.iv_two_one, optionsImag);
            ImageLoader.getInstance().displayImage(str[1], viewHolder.iv_two_two, optionsImag);
            ImageLoader.getInstance().displayImage(str[2], viewHolder.iv_two_three, optionsImag);
            ImageLoader.getInstance().displayImage(str[3], viewHolder.iv_two_four, optionsImag);
//            viewHolder.iv_two_one.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PicShowDialog dialog = new PicShowDialog(context, itemList, 0);
//                    dialog.show();
//                }
//            });

//            viewHolder.iv_two_two.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PicShowDialog dialog = new PicShowDialog(context, itemList, 1);
//                    dialog.show();
//                }
//            });
//
//            viewHolder.iv_two_three.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PicShowDialog dialog = new PicShowDialog(context, itemList, 2);
//                    dialog.show();
//                }
//            });
//
//            viewHolder.iv_two_four.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PicShowDialog dialog = new PicShowDialog(context, itemList, 3);
//                    dialog.show();
//                }
//            });

        } else {
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
            viewHolder.iv_ch_image.setVisibility(View.VISIBLE);
            viewHolder.iv_ch_image.setNumColumns(3);
//            PicGdAdapter adapter = new PicGdAdapter(context, itemList, position);
//            viewHolder.iv_ch_image.setAdapter(adapter);

        }

        if(circleTalkEntityList!=null){
//            if(!TextUtils.isEmpty(list.get(position).getUserId())){
//                viewHolder.tvChNiName.setText(list.get(position).getUserId());
//            }
            if(!TextUtils.isEmpty(circleTalkEntityList.get(position).getCreateTime())){
                viewHolder.tvTime.setText(ZtinfoUtils.timeToDate(Long.parseLong(circleTalkEntityList.get(position).getCreateTime())));
            }
            if(!TextUtils.isEmpty(circleTalkEntityList.get(position).getTalkContent())){
                viewHolder.tv_content.setText(circleTalkEntityList.get(position).getTalkContent());
            }
            if(!TextUtils.isEmpty(circleTalkEntityList.get(position).getCircleName())){
                viewHolder.tv_cir_name.setText(circleTalkEntityList.get(position).getCircleName());
            }
            if(!TextUtils.isEmpty(circleTalkEntityList.get(position).getLikedCounts()+"")){
                viewHolder.znum.setText(circleTalkEntityList.get(position).getLikedCounts()+"");
            }else {
                viewHolder.znum.setText("0");
            }
            if(!TextUtils.isEmpty(circleTalkEntityList.get(position).getCommentCounts()+"")){
                viewHolder.cnum.setText(circleTalkEntityList.get(position).getCommentCounts()+"");
            }else {
                viewHolder.cnum.setText("0");
            }
            if(!TextUtils.isEmpty(circleTalkEntityList.get(position).getSharedCounts()+"")){
                viewHolder.snum.setText(circleTalkEntityList.get(position).getSharedCounts()+"");
            }else {
                viewHolder.snum.setText("0");
            }
            if (circleTalkEntityList.get(position).getLiked()==1){
                viewHolder.zimg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.zan_b));
                flag=true;
            }else if (circleTalkEntityList.get(position).getLiked()==0){
                viewHolder.zimg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.zan));
                flag=false;
            }
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(circleTalkEntityList.get(position).getUserIcon()),viewHolder.ivChHead,optionsImag);
            if (!TextUtils.isEmpty(circleTalkEntityList.get(position).getUserSex())){
                if (circleTalkEntityList.get(position).getUserSex().equals(0+"")){
                    viewHolder.ivChGendar.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.female));
                }else if (circleTalkEntityList.get(position).getUserSex().equals(1+"")){
                    viewHolder.ivChGendar.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.male));
                }
            }

            if (!TextUtils.isEmpty(userInfoList.get(0).getPetName())){
                viewHolder.tvChNiName.setText(userInfoList.get(0).getPetName());
            }else {
                viewHolder.tvChNiName.setText("暂无昵称");
            }
        }
        //点击用户头像，进入用户圈子主页
        viewHolder.ivChHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
               intent.putExtra("circleId", circleTalkEntityList.get(position).getCircleId());
                intent.setClass(context, CirxqAct.class);
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
                intent.putExtra("circleId", circleTalkEntityList.get(position).getCircleId());
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
                if (flag==false){
                    addCircleLike(circleTalkEntityList.get(position).getId());
                }else if (flag==true){
                    removeCircleLike(circleTalkEntityList.get(position).getId());
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
                intent.putExtra("circleEntity", circleTalkEntityList.get(position));
                intent.putExtra("circleId", circleTalkEntityList.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, CircleTextAct.class);
                intent.putExtra("circleEntity",circleTalkEntityList.get(position));
                intent.putExtra("circleId",circleTalkEntityList.get(position).getId());
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
        private ImageView zimg;

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
                circleTalkEntityList.get(clickPos).setLikedCounts(circleTalkEntityList.get(clickPos).getLikedCounts()+1);
                circleTalkEntityList.get(clickPos).setLiked(1);
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
                circleTalkEntityList.get(clickPos).setLikedCounts(circleTalkEntityList.get(clickPos).getLikedCounts()-1);
                circleTalkEntityList.get(clickPos).setLiked(0);
                notifyDataSetChanged();
            }
        });
    }
}
