package com.boyuanitsm.zhetengba.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.PersonalAct;
import com.boyuanitsm.zhetengba.activity.circle.ChanelTextAct;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.EmojUtils;
import com.boyuanitsm.zhetengba.utils.LUtils;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.GcDialog;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.view.PicShowDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leaf.library.widget.MyListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现广场listview适配器
 * Created by xiaoke on 2016/5/3.
 */
public class ChanAdapter extends BaseAdapter {
    private Context context;
    private List<List<ImageInfo>> dateList = new ArrayList<>();
    private List<ChannelTalkEntity> list = new ArrayList<>();
    private String channelId;//说说id
    private String userid;
    private boolean image_record_out;
    int clickPos = 0;
    private PopupWindow popupWindow;
    private Gson gson=new Gson();
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.tum)
            .showImageOnLoading(R.mipmap.tum)
            .showImageOnFail(R.mipmap.tum).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    private DisplayImageOptions optionsImagh = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

//    public ChanAdapter(Context context, List<List<ImageInfo>> dateList, List<ChannelTalkEntity> datas) {
//        this.context = context;
//        this.dateList = dateList;
//    }

    public ChanAdapter(Context context, List<List<ImageInfo>> dateList, List<ChannelTalkEntity> list) {
        this.context = context;
        this.dateList = dateList;
        this.list = list;
        userid= UserInfoDao.getUser().getId();
    }

    public void notifyChange(List<List<ImageInfo>> dateList, List<ChannelTalkEntity> list) {
        this.dateList = dateList;
        this.list = list;
        notifyDataSetChanged();
    }
    /**
     * itemClick接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(View view,String id,int cusPosition);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
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
        List<ChannelTalkEntity> clist=list.get(position).getCommentsList();
        if (convertView != null && convertView.getTag() != null) {
            viewHolder = (CaViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.item_chanle, null);
            viewHolder = new CaViewHolder();
            viewHolder.zimg = (ImageView) convertView.findViewById(R.id.zimg);
            viewHolder.sex = (ImageView) convertView.findViewById(R.id.iv_ch_gendar);
            viewHolder.head = (CircleImageView) convertView.findViewById(R.id.iv_ch_head);
            viewHolder.ll_like = (LinearLayout) convertView.findViewById(R.id.ll_like);
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
            viewHolder.rl_xia= (RelativeLayout) convertView.findViewById(R.id.rl_xia);
            viewHolder.iv_more = (ImageView) convertView.findViewById(R.id.iv_more);
            viewHolder.cnumText = (TextView) convertView.findViewById(R.id.cnumText);
            viewHolder.znumText = (TextView) convertView.findViewById(R.id.znumText);
            viewHolder.ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
            viewHolder.tv_more= (TextView) convertView.findViewById(R.id.tv_more);
            viewHolder.rl_more= (RelativeLayout) convertView.findViewById(R.id.rl_more);
            viewHolder.lv_pl = (MyListView) convertView.findViewById(R.id.lv_pl);
            convertView.setTag(viewHolder);
        }
        if (itemList.isEmpty() || itemList.isEmpty()) {
//            viewHolder.ll_ch_image.setVisibility(View.GONE);
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
        } else if (itemList.size() == 1) {
//            viewHolder.ll_ch_image.setVisibility(View.VISIBLE);
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.VISIBLE);
            itemList.get(0).setWidth(120);
            itemList.get(0).setHeight(120);
            LayoutHelperUtil.handlerOneImage(context, itemList.get(0), viewHolder.iv_oneimage);
            viewHolder.iv_oneimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(context, itemList, 0);
                    dialog.show();
                }
            });
        } else if (itemList.size() == 4) {
//            viewHolder.ll_ch_image.setVisibility(View.VISIBLE);
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ZhetebaUtils.dip2px(context, 255), ActionBar.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,ZhetebaUtils.dip2px(context,10),0,0);
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
                viewHolder.tv_time.setText(ZtinfoUtils.timeChange(Long.parseLong(list.get(position).getCreateTiem())));
            }
            if (!TextUtils.isEmpty(list.get(position).getChannelContent())) {
                viewHolder.ll_content.setVisibility(View.VISIBLE);
                viewHolder.tv_content.setText(list.get(position).getChannelContent());
            }else {
                viewHolder.ll_content.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(list.get(position).getLikeCounts() + "")) {
                if (list.get(position).getLikeCounts() == 0) {
                    viewHolder.znum.setVisibility(View.GONE);
                    viewHolder.znumText.setVisibility(View.GONE);
                } else {
                    viewHolder.znum.setVisibility(View.VISIBLE);
                    viewHolder.znumText.setVisibility(View.VISIBLE);
                    viewHolder.znum.setText(list.get(position).getLikeCounts() + "");
                }
            }
            if (!TextUtils.isEmpty(list.get(position).getCommentCounts() + "")) {
                if (list.get(position).getCommentCounts() == 0) {
                    viewHolder.cnum.setVisibility(View.GONE);
                    viewHolder.cnumText.setVisibility(View.GONE);
                    MyLogUtils.info("getCommentCounts====" + list.get(position).getCommentCounts());
                } else {
                    viewHolder.cnum.setVisibility(View.VISIBLE);
                    viewHolder.cnumText.setVisibility(View.VISIBLE);
                    viewHolder.cnum.setText(list.get(position).getCommentCounts() + "");
                    MyLogUtils.info("Counts====" + list.get(position).getCommentCounts());
                    if (list.get(position).getCommentCounts()>5){
                        viewHolder.rl_more.setVisibility(View.VISIBLE);
                    }else {
                        viewHolder.rl_more.setVisibility(View.GONE);
                    }
                }
            }
                if (clist!=null&&clist.size()>0){
                    viewHolder.ll_comment.setVisibility(View.VISIBLE);
                    viewHolder.lv_pl.setAdapter(new ChanelPlAdapter(context,clist));
                }else {
                    viewHolder.ll_comment.setVisibility(View.GONE);
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
                intent.putExtra("CommentPosition", position);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
        viewHolder.tv_more.setOnClickListener(listener);
        viewHolder.ll_comment.setOnClickListener(listener);
        viewHolder.ll_content.setOnClickListener(listener);
        viewHolder.tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GcDialog gcDialog=new GcDialog(context,userid,list.get(position).getCreatePersonId(),list.get(position).getId(),position);
                gcDialog.builder().show();

            }
        });

        //点击跳转圈友主页
        viewHolder.ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonalAct.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId", list.get(position).getCreatePersonId());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        viewHolder.rl_xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GcDialog gcDialog = new GcDialog(context, userid, list.get(position).getCreatePersonId(), list.get(position).getId(), position);
                gcDialog.builder().show();


            }
        });


        viewHolder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPos = position;
                showPopupWindow(viewHolder.iv_more, clickPos);
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
        private LinearLayout ll_like;
        private TextView znum;
        private TextView cnum;
        private RelativeLayout rl_xia;
        private ImageView iv_more;
        private TextView znumText;
        private TextView cnumText;
        private LinearLayout ll_comment,ll_comment_one,ll_comment_two,ll_comment_three;
        private RelativeLayout rl_more;
        private TextView tv_more;
        private MyListView lv_pl;

    }
    /**
     *
     */
    private void showPopupWindow(View parent, final int position) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.pupu_cir_item, null);

        // 实例化popupWindow
        popupWindow = new PopupWindow(layout, AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));

//        int[] location = new int[2];parent.getLocationOnScreen(location);//这两个是用来显示上方左方右方的
        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        int[] location=new int[2];
        parent.getLocationOnScreen(location);
        //获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;
                int ypos=location[1]-popupWindow.getHeight()/2;
                int ypos1=ypos-ZhetebaUtils.dip2px(context,10);
        //xoff,yoff基于anchor的左下角进行偏移。
//<<<<<<< HEAD
//        popupWindow.showAsDropDown(parent, xpos, 0);
        popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY,xpos,ypos1);
//=======
//        popupWindow.showAsDropDown(parent, xpos, 0);//这里因为要显示在左方所以注释掉
//        popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, location[0]-popupWindow.getWidth(), location[1]);

        final LinearLayout ll_zan = (LinearLayout) layout.findViewById(R.id.ll_zan);
        final LinearLayout ll_cmt = (LinearLayout) layout.findViewById(R.id.ll_cmt);
        final TextView ivzan = (TextView) layout.findViewById(R.id.tvzan);
        if (!TextUtils.isEmpty(list.get(position).getLiked() + "")) {
            if (0 == list.get(clickPos).getLiked()) {//未点赞
                ivzan.setText("赞");
            } else {//if (1 == list.get(clickPos).getLiked())
                ivzan.setText("取消");
            }
        }
        ll_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_zan.setEnabled(false);
                if (0 == list.get(clickPos).getLiked()) {
                    addChannelLike(list.get(clickPos).getId(), ll_zan, ivzan);
                } else{// if (1 == list.get(clickPos).getLiked())
                    removeChannelLike(list.get(clickPos).getId(), ll_zan, ivzan);
                }
            }
        });
        ll_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(ll_cmt,list.get(position).getId(),position);
//                Intent intent = new Intent();
//                intent.setClass(context, ChanelTextAct.class);
//                intent.putExtra("channelEntity", list.get(position));
//                intent.putExtra("channelId", list.get(position).getId());
//                intent.putExtra("CommentPosition", position);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                popupWindow.dismiss();
//                context.startActivity(intent);
            }
        });


    }



    /**
     * 点赞
     *  @param channelId
     * @param ll_like
     * @param ivzan
     */
    private void addChannelLike(String channelId, final LinearLayout ll_like, final TextView ivzan) {
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
                ivzan.setText("取消");
                popupWindow.dismiss();
                if (!TextUtils.isEmpty(response.getData())) {
                    list.get(clickPos).setLikeCounts(Integer.parseInt(response.getData()));
                }
                notifyDataSetChanged();
            }
        });

    }

    /**
     * 取消点赞
     *  @param channelId
     * @param ll_like
     * @param ivzan
     */
    private void removeChannelLike(String channelId, final LinearLayout ll_like, final TextView ivzan) {
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
                ivzan.setText("赞");
                popupWindow.dismiss();
                if (!TextUtils.isEmpty(response.getData())) {
                    list.get(clickPos).setLikeCounts(Integer.parseInt(response.getData()));
                }
                notifyDataSetChanged();
            }
        });
    }

}
