package com.boyuanitsm.zhetengba.adapter;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.ShareDialogAct;
import com.boyuanitsm.zhetengba.activity.circle.CircleAct;
import com.boyuanitsm.zhetengba.activity.circle.CircleTextAct;
import com.boyuanitsm.zhetengba.activity.mess.PerpageAct;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.fragment.circleFrg.CirFrg;
import com.boyuanitsm.zhetengba.fragment.circleFrg.CircleFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
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
 * 圈子详情圈子动态适配器
 * Created by xiaoke on 2016/5/4.
 */
public class CirclexqListAdapter extends BaseAdapter {
    private Context context;
    private List<List<ImageInfo>> dateList = new ArrayList<>();
    private List<CircleEntity> list = new ArrayList<>();
    int clickPos;
    int circleDelPos;
    private PopupWindow popupWindow;
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

    public CirclexqListAdapter(Context context, List<List<ImageInfo>> dateList, List<CircleEntity> list) {
        this.context = context;
        this.dateList = dateList;
        this.list = list;
    }

    public void notifyChange(List<List<ImageInfo>> dateList, List<CircleEntity> list) {
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
        ViewHolder viewHolder = null;
        List<ImageInfo> itemList1 = new ArrayList<>();
        if (list.get(position) != null) {
            itemList1 = dateList.get(position);
        }


        if (convertView != null && convertView.getTag() != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_circle, null);
            viewHolder.ivChHead = (ImageView) convertView.findViewById(R.id.iv_ch_head);
            viewHolder.tvChNiName = (TextView) convertView.findViewById(R.id.tv_ch_niName);
            viewHolder.ivChGendar = (ImageView) convertView.findViewById(R.id.iv_ch_gendar);
            viewHolder.zimg = (ImageView) convertView.findViewById(R.id.zimg);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.ll_share = (LinearLayout) convertView.findViewById(R.id.ll_share);
            viewHolder.ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
            viewHolder.iv_comment = (ImageView) convertView.findViewById(R.id.iv_comment);
            viewHolder.llphoto = (LinearLayout) convertView.findViewById(R.id.llphoto);
            viewHolder.iv_ch_image = (MyGridView) convertView.findViewById(R.id.iv_ch_image);
            viewHolder.iv_oneimage = (CustomImageView) convertView.findViewById(R.id.iv_oneimage);
            viewHolder.tv_cir_name = (TextView) convertView.findViewById(R.id.tv_cir_name);
            viewHolder.ll_two = (LinearLayout) convertView.findViewById(R.id.ll_two);
            viewHolder.like = (LinearLayout) convertView.findViewById(R.id.like);
            viewHolder.iv_two_one = (ImageView) convertView.findViewById(R.id.iv_two_one);
            viewHolder.iv_two_two = (ImageView) convertView.findViewById(R.id.iv_two_two);
            viewHolder.iv_two_three = (ImageView) convertView.findViewById(R.id.iv_two_three);
            viewHolder.iv_two_four = (ImageView) convertView.findViewById(R.id.iv_two_four);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.znum = (TextView) convertView.findViewById(R.id.znum);
            viewHolder.cnum = (TextView) convertView.findViewById(R.id.cnum);
            viewHolder.iv_share= (ImageView) convertView.findViewById(R.id.iv_share);
            viewHolder.ll_xia=(LinearLayout)convertView.findViewById(R.id.ll_xia);
            viewHolder.znum2 = (TextView) convertView.findViewById(R.id.znum2);
            viewHolder.cnum2 = (TextView) convertView.findViewById(R.id.cnum2);
            viewHolder.cnumText = (TextView) convertView.findViewById(R.id.cnumText);
            viewHolder.znumText = (TextView) convertView.findViewById(R.id.znumText);
            viewHolder.iv_more=(ImageView)convertView.findViewById(R.id.iv_more);
            convertView.setTag(viewHolder);
        }
//        viewHolder.llphoto.setVisibility(View.VISIBLE);
        final List<ImageInfo> itemList = itemList1;
        if (itemList.isEmpty() || itemList.isEmpty()) {
//            viewHolder.llphoto.setVisibility(View.GONE);
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
        } else if (itemList.size() == 1) {
            viewHolder.iv_ch_image.setVisibility(View.GONE);
            viewHolder.ll_two.setVisibility(View.GONE);
            viewHolder.iv_oneimage.setVisibility(View.VISIBLE);
//            itemList.get(0).setWidth(200);
//            itemList.get(0).setHeight(200);
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ZhetebaUtils.dip2px(context, 255), ActionBar.LayoutParams.WRAP_CONTENT);
            viewHolder.iv_ch_image.setLayoutParams(params);
            viewHolder.iv_ch_image.setNumColumns(3);
            PicGdAdapter adapter = new PicGdAdapter(context, itemList, position);
            viewHolder.iv_ch_image.setAdapter(adapter);
        }

        if (list != null && list.size() > 0) {
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getUserIcon()), viewHolder.ivChHead, optionsImagh);
            if (!TextUtils.isEmpty(list.get(position).getUserName())) {
                viewHolder.tvChNiName.setText(list.get(position).getUserName());
            } else {
                String str = list.get(position).getUserId();
                viewHolder.tvChNiName.setText(str.substring(0, 3) + "***" + str.substring(str.length() - 3, str.length()));
            }
            if (!TextUtils.isEmpty(list.get(position).getUserSex())) {
                if ("0".equals(list.get(position).getUserSex())) {
                    viewHolder.ivChGendar.setImageResource(R.mipmap.gfemale);//女0
                } else if ("1".equals(list.get(position).getUserSex())) {
                    viewHolder.ivChGendar.setImageResource(R.mipmap.male);//男1
                }
            }
            if (!TextUtils.isEmpty(list.get(position).getCreateTime() + "")) {
                viewHolder.tvTime.setText(ZtinfoUtils.timeChange(Long.parseLong(list.get(position).getCreateTime() + "")));
            }
            if (!TextUtils.isEmpty(list.get(position).getTalkContent())) {
                viewHolder.tv_content.setVisibility(View.VISIBLE);
                viewHolder.tv_content.setText(list.get(position).getTalkContent());
            }else {
                viewHolder.tv_content.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(list.get(position).getCircleName())) {
                viewHolder.tv_cir_name.setText(list.get(position).getCircleName());
            }
//            if (!TextUtils.isEmpty(list.get(position).getLiked() + "")) {
//                if (0 == list.get(position).getLiked()) {//未点赞
//                    viewHolder.zimg.setImageResource(R.drawable.zan);
//                } else if (1 == list.get(position).getLiked()) {
//                    viewHolder.zimg.setImageResource(R.drawable.zan_b);
//                }
//            }
            if (!TextUtils.isEmpty(list.get(position).getLikedCounts() + "")) {
                if (list.get(position).getLikedCounts() == 0) {
                    viewHolder.znum2.setVisibility(View.GONE);
                    viewHolder.znumText.setVisibility(View.GONE);
                } else {
                    viewHolder.znum2.setVisibility(View.VISIBLE);
                    viewHolder.znumText.setVisibility(View.VISIBLE);
                    viewHolder.znum2.setText(list.get(position).getLikedCounts() + "");
                }
            }
            if (!TextUtils.isEmpty(list.get(position).getCommentCounts() + "")) {
                if (list.get(position).getCommentCounts() == 0) {
                    viewHolder.cnum2.setVisibility(View.GONE);
                    viewHolder.cnumText.setVisibility(View.GONE);
                } else {
                    viewHolder.cnum2.setVisibility(View.VISIBLE);
                    viewHolder.cnumText.setVisibility(View.VISIBLE);
                    viewHolder.cnum2.setText(list.get(position).getCommentCounts() + "");
                }
            }
        }
        //点击用户头像，进入用户圈子主页
        viewHolder.ivChHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("userId", list.get(position).getUserId());
                intent.putExtras(bundle);
                intent.setClass(context, PerpageAct.class);
                //需要开启新task,否则会报错
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.tv_content.setOnClickListener(new View.OnClickListener() {
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
        viewHolder.ll_xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleDelPos=position;
                CricleDialog dialog=new CricleDialog();
                dialog.builder().show();
            }
        });
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPos=position;
                showPopupWindow(finalViewHolder1.iv_more, clickPos);
            }
        });
        return convertView;
    }


    class ViewHolder {
        public ImageView ivChHead;
        public TextView tvChNiName;
        public ImageView ivChGendar;
        public ImageView zimg;
        public TextView tvTime;
        public MyGridView iv_ch_image;
        public CustomImageView iv_oneimage;
        public TextView tv_cir_name;
        private LinearLayout ll_two;
        private ImageView iv_two_one, iv_two_two, iv_two_three, iv_two_four;
        private LinearLayout like;
        private LinearLayout ll_share;
        private LinearLayout ll_comment;
        private LinearLayout llphoto;
        private TextView tv_content;
        private TextView znum;
        private TextView cnum;
        private TextView snum;
        private ImageView iv_comment,iv_share;
        private LinearLayout ll_xia;
        private TextView znum2;
        private TextView cnum2;
        private TextView znumText;
        private TextView cnumText;
        private ImageView iv_more;
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
        WindowManager manager = (WindowManager)context. getSystemService(context.WINDOW_SERVICE);
        int[] location=new int[2];
        parent.getLocationOnScreen(location);
        @SuppressWarnings("deprecation")
        //获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;
        int ypos=location[1]-popupWindow.getHeight()/2;
        int ypos1=ypos-ZhetebaUtils.dip2px(context,10);
        //xoff,yoff基于anchor的左下角进行偏移。
//        popupWindow.showAsDropDown(parent, xpos, 0);
        popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY,xpos,ypos1);
        final LinearLayout ll_zan= (LinearLayout) layout.findViewById(R.id.ll_zan);
        LinearLayout ll_cmt= (LinearLayout) layout.findViewById(R.id.ll_cmt);
        final TextView ivzan= (TextView) layout.findViewById(R.id.tvzan);
        if (!TextUtils.isEmpty(list.get(position).getLiked() + "")) {
            if (0 == list.get(clickPos).getLiked()) {//未点赞
                ivzan.setText("赞");
            } else{// if (1 == list.get(clickPos).getLiked())
                ivzan.setText("取消");
            }
        }
        ll_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_zan.setEnabled(false);
                if (0 == list.get(clickPos).getLiked()) {
                    addCircleLike(list.get(clickPos).getId(), ll_zan,ivzan);
                } else{// if (1 == list.get(clickPos).getLiked())
                    removeCircleLike(list.get(clickPos).getId(), ll_zan,ivzan);
                }
            }
        });
        ll_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, CircleTextAct.class);
                intent.putExtra("circleEntity", list.get(position));
                intent.putExtra("circleId", list.get(position).getId());
                intent.putExtra("CirCommentPosition", position);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                popupWindow.dismiss();
                context.startActivity(intent);
            }
        });


    }
    /**
     * 圈子说说点赞
     *
     * @param circleTalkId
     * @param ll_zan
     * @param ivzan
     */
    private void addCircleLike(String circleTalkId, final LinearLayout ll_zan, final TextView ivzan) {
        RequestManager.getTalkManager().addCircleLike(circleTalkId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(context, errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                list.get(clickPos).setLiked(1);
                ll_zan.setEnabled(true);
                ivzan.setText("取消");
                popupWindow.dismiss();
                if (!TextUtils.isEmpty(response.getData())) {
                    list.get(clickPos).setLikedCounts(Integer.parseInt(response.getData()));
                }
                notifyDataSetChanged();
            }
        });
    }

    /**
     * 取消圈子说说点赞
     *
     * @param circleTalkId
     * @param ll_zan
     * @param ivzan
     */
    private void removeCircleLike(String circleTalkId, final LinearLayout ll_zan, final TextView ivzan) {
        RequestManager.getTalkManager().removeCircleLike(circleTalkId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(context, errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                list.get(clickPos).setLiked(0);
                ivzan.setText("赞");
                ll_zan.setEnabled(true);
                popupWindow.dismiss();
                if (!TextUtils.isEmpty(response.getData())) {
                    list.get(clickPos).setLikedCounts(Integer.parseInt(response.getData()));
                }
                notifyDataSetChanged();
            }
        });
    }
    class CricleDialog implements View.OnClickListener{
        private Dialog dialog;
        private Display display;
        private TextView tv_sc,tv_jb,tv_qx;

        public CricleDialog builder() {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            display = windowManager.getDefaultDisplay();
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_gc, null);
            // 设置Dialog最小宽度为屏幕宽度
            view.setMinimumWidth(display.getWidth());

            tv_sc= (TextView) view.findViewById(R.id.tv_sc);
            tv_jb= (TextView) view.findViewById(R.id.tv_jb);
            tv_qx= (TextView) view.findViewById(R.id.tv_qx);
            if(UserInfoDao.getUser().getId().equals(list.get(circleDelPos).getUserId())){
                tv_sc.setVisibility(View.VISIBLE);
            }else {
                tv_sc.setVisibility(View.GONE);
            }
            tv_sc.setOnClickListener(this);
            tv_jb.setOnClickListener(this);
            tv_qx.setOnClickListener(this);
            // 定义Dialog布局和参数
            dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
            dialog.setContentView(view);
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;
            dialogWindow.setAttributes(lp);
            return this;
        }

        public CricleDialog setCanceledOnTouchOutside(boolean cancel) {
            dialog.setCanceledOnTouchOutside(cancel);
            return this;
        }

        public void show() {
            dialog.show();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_sc://删除
                    deleat(list.get(circleDelPos).getId());

                    break;

                case R.id.tv_jb://举报
                    MyToastUtils.showShortToast(context,"举报成功！");
                    dialog.dismiss();
                    break;

                case R.id.tv_qx://取消
                    dialog.dismiss();
                    break;
            }

        }

        private void deleat(String takeid) {
            RequestManager.getTalkManager().deleteTalk(takeid, new ResultCallback<ResultBean<String>>() {
                @Override
                public void onError(int status, String errorMsg) {

                }

                @Override
                public void onResponse(ResultBean<String> response) {
                    MyToastUtils.showShortToast(context, "删除成功");
                    dialog.dismiss();
                    list.remove(circleDelPos);
                    dateList.remove(circleDelPos);
                    context.sendBroadcast(new Intent(CircleAct.ALLTALKS));
                    notifyDataSetChanged();
//                dialog.dismiss();

                }
            });
        }
    }

}
