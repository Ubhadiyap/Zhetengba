package com.boyuanitsm.zhetengba.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.ShareDialogAct;
import com.boyuanitsm.zhetengba.activity.circle.ChanelTextAct;
import com.boyuanitsm.zhetengba.activity.mess.PerpageAct;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.view.PicShowDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道listview适配器
 * Created by xiaoke on 2016/5/3.
 */
public class ChanAdapter extends BaseAdapter {
    private Context context;
    private List<List<ImageInfo>> dateList = new ArrayList<>();
    private List<ChannelTalkEntity> list = new ArrayList<>();
    private String channelId;//说说id
    private LinearLayout ll_like;
    int clickPos=0;
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    private DisplayImageOptions optionsImagh = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    public ChanAdapter(Context context, List<List<ImageInfo>> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    public ChanAdapter(Context context, List<List<ImageInfo>> dateList, List<ChannelTalkEntity> list) {
        this.context = context;
        this.dateList = dateList;
        this.list = list;
    }

    public void notifyChange(List<List<ImageInfo>> dateList, List<ChannelTalkEntity> list) {
        this.dateList = dateList;
        this.list = list;
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
        final CaViewHolder viewHolder;
        final List<ImageInfo> itemList = dateList.get(position);
        if (convertView != null && convertView.getTag() != null) {
            viewHolder = (CaViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.item_chanle, null);
            viewHolder = new CaViewHolder();
            viewHolder.zimg = (ImageView) convertView.findViewById(R.id.zimg);
            viewHolder.sex = (ImageView) convertView.findViewById(R.id.iv_ch_gendar);
            viewHolder.head = (CircleImageView) convertView.findViewById(R.id.iv_ch_head);
            ll_like = (LinearLayout) convertView.findViewById(R.id.ll_like);
            viewHolder.ll_share = (LinearLayout) convertView.findViewById(R.id.ll_share);
            viewHolder.ll_answer = (LinearLayout) convertView.findViewById(R.id.ll_answer);
            viewHolder.iv_ch_image = (MyGridView) convertView.findViewById(R.id.iv_ch_image);
            viewHolder.iv_oneimage = (CustomImageView) convertView.findViewById(R.id.iv_oneimage);
            viewHolder.tv_ch_niName = (TextView) convertView.findViewById(R.id.tv_ch_niName);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.ll_two = (LinearLayout) convertView.findViewById(R.id.ll_two);
            viewHolder.iv_two_one = (CustomImageView) convertView.findViewById(R.id.iv_two_one);
            viewHolder.iv_two_two = (CustomImageView) convertView.findViewById(R.id.iv_two_two);
            viewHolder.iv_two_three = (CustomImageView) convertView.findViewById(R.id.iv_two_three);
            viewHolder.iv_two_four = (CustomImageView) convertView.findViewById(R.id.iv_two_four);
            viewHolder.ll_content = (LinearLayout) convertView.findViewById(R.id.ll_content);
            viewHolder.ll_date = (RelativeLayout) convertView.findViewById(R.id.ll_date);
            viewHolder.ll_user = (LinearLayout) convertView.findViewById(R.id.ll_user);
            viewHolder.ll_ch_image = (LinearLayout) convertView.findViewById(R.id.ll_ch_image);
            viewHolder.znum = (TextView) convertView.findViewById(R.id.znum);
            viewHolder.cnum = (TextView) convertView.findViewById(R.id.cnum);
            convertView.setTag(viewHolder);
        }
        viewHolder.ll_ch_image.setVisibility(View.VISIBLE);
        if (itemList.isEmpty() || itemList.isEmpty()) {
            viewHolder.ll_ch_image.setVisibility(View.GONE);
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
        } else if (itemList.size() == 1) {
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.VISIBLE);
            itemList.get(0).setWidth(200);
            itemList.get(0).setHeight(200);
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
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(itemList.get(0).getUrl()), viewHolder.iv_two_one, optionsImag);
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(itemList.get(1).getUrl()), viewHolder.iv_two_two, optionsImag);
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(itemList.get(2).getUrl()), viewHolder.iv_two_three, optionsImag);
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(itemList.get(3).getUrl()), viewHolder.iv_two_four, optionsImag);
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
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ZhetebaUtils.dip2px(context,255), ActionBar.LayoutParams.WRAP_CONTENT);
            viewHolder.iv_ch_image.setLayoutParams(params);
            viewHolder.iv_ch_image.setNumColumns(3);
            PicGdAdapter adapter = new PicGdAdapter(context, itemList, position);
            viewHolder.iv_ch_image.setAdapter(adapter);
        }
        if (list != null) {
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getUserIcon()), viewHolder.head, optionsImagh);
            if (!TextUtils.isEmpty(list.get(position).getUserName())) {
                viewHolder.tv_ch_niName.setText(list.get(position).getUserName());
            } else {
                String str = list.get(position).getCreatePersonId();
                viewHolder.tv_ch_niName.setText(str.substring(0, 3) + "***" + str.substring(str.length() - 3, str.length()));
            }
            if (!TextUtils.isEmpty(list.get(position).getUserSex())) {
                if ("0".equals(list.get(position).getUserSex())) {
                    viewHolder.sex.setImageResource(R.mipmap.gfemale);//女0
                } else if ("1".equals(list.get(position).getUserSex())) {
                    viewHolder.sex.setImageResource(R.mipmap.male);//男1
                }
            }
            if (!TextUtils.isEmpty(list.get(position).getCreateTiem())) {
//                ZtinfoUtils.timeToDate(Long.parseLong(list.get(position).getCreateTiem()))
                viewHolder.tv_time.setText(ZtinfoUtils.timeChange(Long.parseLong(list.get(position).getCreateTiem())));
            }
            if (!TextUtils.isEmpty(list.get(position).getChannelContent())) {
                viewHolder.tv_content.setText(list.get(position).getChannelContent());
            }
            if (!TextUtils.isEmpty(list.get(position).getLiked() + "")) {
                if (0 == list.get(position).getLiked()) {//未点赞
                    viewHolder.zimg.setImageResource(R.drawable.zan);
                } else if (1 == list.get(position).getLiked()) {
                    viewHolder.zimg.setImageResource(R.drawable.zan_b);
                }
            }
            if (!TextUtils.isEmpty(list.get(position).getLikeCounts() + "")) {
                if (list.get(position).getLikeCounts() == 0) {
                    viewHolder.znum.setVisibility(View.GONE);
                } else {
                    viewHolder.znum.setVisibility(View.VISIBLE);
                    viewHolder.znum.setText(list.get(position).getLikeCounts() + "");
                }
            }
            if (!TextUtils.isEmpty(list.get(position).getCommentCounts() + "")) {
                if (list.get(position).getCommentCounts() == 0) {
                    viewHolder.cnum.setVisibility(View.GONE);
                } else {
                    viewHolder.cnum.setVisibility(View.VISIBLE);
                    viewHolder.cnum.setText(list.get(position).getCommentCounts() + "");
                }
            }
        }
        //点击活动详情跳转频道正文
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ChanelTextAct.class);
                intent.putExtra("channelEntity", list.get(position));
                intent.putExtra("channelId", list.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
        viewHolder.ll_content.setOnClickListener(listener);
        viewHolder.ll_date.setOnClickListener(listener);
        //点击跳转圈友主页
        viewHolder.ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PerpageAct.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId", list.get(position).getCreatePersonId());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        //点赞
        ll_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_like.setEnabled(false);
                clickPos = position;
                channelId = list.get(position).getId();
                if (0 == list.get(position).getLiked()) {
                    addChannelLike(channelId);
                } else if (1 == list.get(position).getLiked()) {
                    removeChannelLike(channelId);
                }
            }
        });
        //分享对话框
        viewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShareDialogAct.class);
                intent.putExtra("type",4);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //评论
        viewHolder.ll_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setClass(context, ChanelTextAct.class);
                intent.putExtra("channelEntity", list.get(position));
                intent.putExtra("channelId", list.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class CaViewHolder {
        private ImageView zimg;
        private ImageView sex;
        private CircleImageView head;
        private LinearLayout ll_share;
        private LinearLayout ll_answer;
        private CustomImageView iv_oneimage;
        private MyGridView iv_ch_image;
        private TextView tv_ch_niName;
        private TextView tv_content;
        private TextView tv_time;
        private LinearLayout ll_two;
        private CustomImageView iv_two_one, iv_two_two, iv_two_three, iv_two_four;
        private LinearLayout ll_user, ll_content, ll_ch_image;
        private RelativeLayout ll_date;
        private TextView znum;
        private TextView cnum;


    }

    /**
     * 点赞
     *
     * @param channelId
     */
    private void addChannelLike(String channelId) {
        RequestManager.getTalkManager().addChannelLike(channelId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                ll_like.setEnabled(true);
                MyToastUtils.showShortToast(context, errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                ll_like.setEnabled(true);
                list.get(clickPos).setLiked(1);
                if (!TextUtils.isEmpty(response.getData())) {
                    list.get(clickPos).setLikeCounts(Integer.parseInt(response.getData()));
                }
                notifyDataSetChanged();
            }
        });

    }

    /**
     * 取消点赞
     *
     * @param channelId
     */
    private void removeChannelLike(String channelId) {
        RequestManager.getTalkManager().removeChannelLike(channelId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                ll_like.setEnabled(true);
                MyToastUtils.showShortToast(context, errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                ll_like.setEnabled(true);
                list.get(clickPos).setLiked(0);
                if (!TextUtils.isEmpty(response.getData())) {
                    list.get(clickPos).setLikeCounts(Integer.parseInt(response.getData()));
                }
                notifyDataSetChanged();
            }
        });
    }

}
