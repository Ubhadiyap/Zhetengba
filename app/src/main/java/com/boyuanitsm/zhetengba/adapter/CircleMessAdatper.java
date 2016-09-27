package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.PersonalAct;
import com.boyuanitsm.zhetengba.bean.CircleInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.CircleMessDao;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.EmojUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 圈子消息，同意，拒绝，回复，赞几种类型
 * Created by xiaoke on 2016/5/9.
 */
public class CircleMessAdatper extends BaseAdapter {
    public static final int STATE1 = 0;
    public static final int STATE2 = 1;
    public static final int SATE3 = 2;
    private List<CircleInfo> circleInfoList;
    private Context context;
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public CircleMessAdatper(Context context, List<CircleInfo> list) {
        this.context = context;
        this.circleInfoList = list;
    }

    public void updata(List<CircleInfo> list) {
        this.circleInfoList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return circleInfoList == null ? 0 : circleInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return circleInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //tiem_mess_one两种布局
        Holder1 holder1;
        if (convertView != null && convertView.getTag() != null) {
            holder1= (Holder1) convertView.getTag();
        } else {
            holder1 = new Holder1();
            convertView = View.inflate(context, R.layout.item_mess, null);
            holder1.tv_huifu = (TextView) convertView.findViewById(R.id.tv_huifu);
            holder1.cv_head1 = (CircleImageView) convertView.findViewById(R.id.cv_head1);
            holder1.niName1 = (TextView) convertView.findViewById(R.id.tv_niname);
            holder1.createTime1 = (TextView) convertView.findViewById(R.id.tv_time);
            holder1.iv_icon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            holder1.tv_talk = (TextView) convertView.findViewById(R.id.tv_talk);
            holder1.tv_qingqiu = (TextView) convertView.findViewById(R.id.tv_qingqiu);
            holder1.cv_head2 = (CircleImageView) convertView.findViewById(R.id.cv_head2);
            holder1.tv_petName = (TextView) convertView.findViewById(R.id.tv_petName);
            holder1.tv_creatTime = (TextView) convertView.findViewById(R.id.tv_creatTime);
            holder1.bt_yes = (Button) convertView.findViewById(R.id.bt_yes);
            holder1.bt_no = (Button) convertView.findViewById(R.id.bt_no);
            holder1.tv_shenqing = (TextView) convertView.findViewById(R.id.tv_shenqing);
            holder1.cv_head3 = (CircleImageView) convertView.findViewById(R.id.cv_head3);
            holder1.tv_creatTime2 = (TextView) convertView.findViewById(R.id.tv_creatTime2);
            holder1.tv_petName2 = (TextView) convertView.findViewById(R.id.tv_petName2);
           holder1.item_mess= (LinearLayout) convertView.findViewById(R.id.item_mess);
            holder1.item_mess_one=(LinearLayout)convertView.findViewById(R.id.item_mess_one);
            holder1.item_mess_two=(LinearLayout)convertView.findViewById(R.id.item_mess_two);
            convertView.setTag(holder1);
        }
        if (TextUtils.equals(circleInfoList.get(position).getMesstype(),0+"")){
            holder1.item_mess.setVisibility(View.VISIBLE);
            holder1.item_mess_one.setVisibility(View.GONE);
            holder1.item_mess_two.setVisibility(View.GONE);
        }else if (TextUtils.equals(circleInfoList.get(position).getMsgType(),1+"")){
            holder1.item_mess.setVisibility(View.GONE);
            holder1.item_mess_one.setVisibility(View.VISIBLE);
            holder1.item_mess_two.setVisibility(View.GONE);
        }else if (TextUtils.equals(circleInfoList.get(position).getMesstype(),2+"")){
            holder1.item_mess.setVisibility(View.GONE);
            holder1.item_mess_one.setVisibility(View.GONE);
            holder1.item_mess_two.setVisibility(View.VISIBLE);
        }
        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(circleInfoList.get(position).getUserIcon()), holder1.cv_head1, optionsImag);
        if (!TextUtils.isEmpty(circleInfoList.get(position).getPetName())) {
            holder1.niName1.setText(circleInfoList.get(position).getPetName());
            holder1.tv_petName2.setText(circleInfoList.get(position).getPetName());
        }
        if (!TextUtils.isEmpty(circleInfoList.get(position).getCreateTime())) {
            holder1.createTime1.setText(ZtinfoUtils.timeChange(Long.parseLong(circleInfoList.get(position).getCreateTime())));
            holder1.tv_creatTime2.setText(ZtinfoUtils.timeChange(Long.parseLong(circleInfoList.get(position).getCreateTime())));
        }
        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(UserInfoDao.getUser().getIcon()), holder1.iv_icon, optionsImag);
        if (!TextUtils.isEmpty(circleInfoList.get(position).getCommentTalk())) {
            holder1.tv_talk.setVisibility(View.VISIBLE);
            holder1.tv_talk.setText(circleInfoList.get(position).getCommentTalk());
        } else {
            holder1.tv_talk.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(circleInfoList.get(position).getMessageState())){
            if (circleInfoList.get(position).getMessageState().equals(0 + "")) {
                if (!TextUtils.isEmpty(circleInfoList.get(position).getCommentContent())) {
                    holder1.tv_huifu.setText("评论“我”：" + EmojUtils.decoder(circleInfoList.get(position).getCommentContent()));
                }
            } else if (circleInfoList.get(position).getMessageState().equals(1 + "")) {
                holder1.tv_huifu.setText("赞了“我”的说说!");
            }
        }

        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(circleInfoList.get(position).getUserIcon()), holder1.cv_head2, optionsImag);
        if (!TextUtils.isEmpty(circleInfoList.get(position).getPetName())) {
            holder1.tv_petName.setText(circleInfoList.get(position).getPetName());
        }
        if (!TextUtils.isEmpty(circleInfoList.get(position).getCreateTime())) {
            holder1.tv_creatTime.setText(ZtinfoUtils.timeChange(Long.parseLong(circleInfoList.get(position).getCreateTime())));
        }
//        holder1.cv_head2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, PersonalAct.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
        if (TextUtils.equals(circleInfoList.get(position).getHandleResult(), 0 + "")) {
            holder1.bt_yes.setText("已同意");
            holder1.bt_no.setBackgroundResource(R.drawable.bg_circle_stroke_gray2);
            holder1.bt_yes.setBackgroundResource(R.drawable.bg_circle_stroke_green);
            holder1.bt_yes.setEnabled(false);
            holder1.bt_no.setEnabled(false);
        } else if (TextUtils.equals(circleInfoList.get(position).getHandleResult(), 1 + "")) {
            holder1.bt_yes.setText("同意");
            holder1.bt_no.setText("已拒绝");
            holder1.bt_yes.setBackgroundResource(R.drawable.bg_circle_stroke_gray2);
            holder1.bt_no.setBackgroundResource(R.drawable.bg_circle_stroke_yellow);
            holder1.bt_yes.setEnabled(false);
            holder1.bt_no.setEnabled(false);
        } else {
            holder1.bt_yes.setText("同意");
            holder1.bt_no.setText("拒绝");
            holder1.bt_yes.setBackgroundResource(R.drawable.bg_circle_stroke_green);
            holder1.bt_no.setBackgroundResource(R.drawable.bg_circle_stroke_yellow);
            holder1.bt_yes.setEnabled(true);
            holder1.bt_no.setEnabled(true);
        }
        if (!TextUtils.isEmpty(circleInfoList.get(position).getMessageState())){
        if (circleInfoList.get(position).getMessageState().equals(0 + "")) {
            holder1.tv_qingqiu.setText(circleInfoList.get(position).getMsgContent());
            final Holder1 finalHolder = holder1;
            holder1.bt_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder.bt_yes.setEnabled(false);
                    finalHolder.bt_no.setEnabled(false);
                    RequestManager.getTalkManager().sendAgreeCircleResp(circleInfoList.get(position).getTypeId(), circleInfoList.get(position).getSender(), new ResultCallback<ResultBean<String>>() {
                        @Override
                        public void onError(int status, String errorMsg) {
                            finalHolder.bt_yes.setEnabled(false);
                            finalHolder.bt_no.setEnabled(false);
                            MyToastUtils.showShortToast(context, "请求出错！");
                        }

                        @Override
                        public void onResponse(ResultBean<String> response) {
                            if (TextUtils.equals(response.getData(), "1")) {
                                circleInfoList.get(position).setHandleResult(0 + "");
                                CircleMessDao.saveCircleMess(circleInfoList.get(position));
                                notifyDataSetChanged();
                                MyToastUtils.showShortToast(context, response.getMessage());
                            } else if (TextUtils.equals(response.getData(), "-1")) {
                                circleInfoList.remove(position);
                                notifyDataSetChanged();
                                MyToastUtils.showShortToast(context, response.getMessage());
                            }
                        }
                    });
                }
            });
            holder1.bt_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder.bt_yes.setEnabled(false);
                    finalHolder.bt_no.setEnabled(false);
                    RequestManager.getTalkManager().qingRefuseCircleResp(circleInfoList.get(position).getTypeId(), circleInfoList.get(position).getSender(), new ResultCallback<ResultBean<String>>() {
                        @Override
                        public void onError(int status, String errorMsg) {

                        }

                        @Override
                        public void onResponse(ResultBean<String> response) {
                            if (TextUtils.equals(response.getData(),-2+"")){
                                MyToastUtils.showShortToast(context,response.getMessage());
                                circleInfoList.remove(position);
                                notifyDataSetChanged();
                                return;
                            }else if (TextUtils.equals(response.getData(),-3+"")){
                                MyToastUtils.showShortToast(context,response.getMessage());
                                circleInfoList.remove(position);
                                notifyDataSetChanged();
                                return;
                            }else {
                                circleInfoList.get(position).setHandleResult(1 + "");
                                CircleMessDao.saveCircleMess(circleInfoList.get(position));
                                notifyDataSetChanged();
                                MyToastUtils.showShortToast(context, response.getMessage());
                            }
                        }
                    });

                }
            });
//                        holder2.tv_beizhu.setText("备注：你好，我是李宇春");
        } else if (circleInfoList.get(position).getMessageState().equals(1 + "")) {
            holder1.tv_qingqiu.setText(circleInfoList.get(position).getMsgContent());
            final Holder1 finalHolder1 = holder1;
            holder1.bt_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder1.bt_yes.setEnabled(false);
                    finalHolder1.bt_no.setEnabled(false);
                    //同意加入
                    RequestManager.getTalkManager().sendAgreeCircleInviteMsg(circleInfoList.get(position).getTypeId(), new ResultCallback<ResultBean<String>>() {
                        @Override
                        public void onError(int status, String errorMsg) {
                            finalHolder1.bt_yes.setEnabled(false);
                            finalHolder1.bt_no.setEnabled(false);
                        }

                        @Override
                        public void onResponse(ResultBean<String> response) {
                            if (TextUtils.equals(response.getData(),1+"")){
                                circleInfoList.get(position).setHandleResult(0 + "");
                                CircleMessDao.saveCircleMess(circleInfoList.get(position));
                                notifyDataSetChanged();
                                MyToastUtils.showShortToast(context, response.getMessage());
                            }else{
                                circleInfoList.remove(position);
                                notifyDataSetChanged();
//                                MyToastUtils.showShortToast(context,response.getMessage());
                            }

                        }
                    });
                }
            });
            final Holder1 finalHolder2 = holder1;
            holder1.bt_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder2.bt_yes.setEnabled(false);
                    finalHolder2.bt_no.setEnabled(false);
                    //拒绝加入
                    RequestManager.getTalkManager().sendRefuseCircleResp(circleInfoList.get(position).getTypeId(), new ResultCallback<ResultBean<String>>() {
                        @Override
                        public void onError(int status, String errorMsg) {
                            finalHolder2.bt_yes.setEnabled(false);
                            finalHolder2.bt_no.setEnabled(false);
                        }

                        @Override
                        public void onResponse(ResultBean<String> response) {
                            if (TextUtils.equals(response.getData(),1+"")){
                                circleInfoList.get(position).setHandleResult(1 + "");
                                notifyDataSetChanged();
                                CircleMessDao.saveCircleMess(circleInfoList.get(position));
                                MyToastUtils.showShortToast(context, response.getMessage());
                            }else{
                                circleInfoList.remove(position);
                                notifyDataSetChanged();
//                                MyToastUtils.showShortToast(context,response.getMessage());
                            }

                        }
                    });
                }
            });
        }
        }


        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(circleInfoList.get(position).getUserIcon()), holder1.cv_head3, optionsImag);
        if (!TextUtils.isEmpty(circleInfoList.get(position).getPetName())) {
            holder1.tv_petName.setText(circleInfoList.get(position).getPetName());
        }
        if (!TextUtils.isEmpty(circleInfoList.get(position).getCreateTime())) {
            holder1.tv_creatTime.setText(ZtinfoUtils.timeChange(Long.parseLong(circleInfoList.get(position).getCreateTime())));
        }
        if (!TextUtils.isEmpty(circleInfoList.get(position).getMessageState())){
            if (circleInfoList.get(position).getMessageState().equals(0 + "")) {
                holder1.tv_shenqing.setText("同意了我的请求，已加入" + circleInfoList.get(position).getCircleName());
            } else if (circleInfoList.get(position).getMessageState().equals(1 + "")) {
                holder1.tv_shenqing.setText("拒绝了我的请求，不参加" + circleInfoList.get(position).getCircleName());
            } else if (circleInfoList.get(position).getMessageState().equals(2 + "")) {
                holder1.tv_shenqing.setText("同意了我的邀请，已加入" + circleInfoList.get(position).getCircleName());
            } else if (circleInfoList.get(position).getMessageState().equals(3 + "")) {
                holder1.tv_shenqing.setText("拒绝了我的邀请，不参加" + circleInfoList.get(position).getCircleName());
            }
        }


        return convertView;
    }

   class Holder1 {
        private TextView tv_huifu;
        private CircleImageView cv_head1;
        private TextView niName1;
        private TextView createTime1;
        private CircleImageView iv_icon;
        private TextView tv_talk;

        private TextView tv_qingqiu;
        private Button bt_yes, bt_no;
        private CircleImageView cv_head2;
        private TextView tv_petName2;
        private TextView tv_creatTime2;

        private TextView tv_shenqing;
        private CircleImageView cv_head3;
        private TextView tv_petName;
        private TextView tv_creatTime;
        private LinearLayout item_mess,item_mess_one,item_mess_two;
    }
}
