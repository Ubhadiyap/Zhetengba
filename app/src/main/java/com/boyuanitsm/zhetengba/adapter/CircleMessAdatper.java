package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.CircleglAct;
import com.boyuanitsm.zhetengba.activity.mess.PerpageAct;
import com.boyuanitsm.zhetengba.bean.CircleInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.CustomImageView;
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
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
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
    public int getItemViewType(int position) {
        if (circleInfoList.get(position).getMesstype().equals(0 + "")) {
            return 0;
        } else if (circleInfoList.get(position).getMesstype().equals(1 + "")) {
            return 1;
        } else if (circleInfoList.get(position).getMesstype().equals(2 + "")) {
            return 2;
        } else {
            return 4;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
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
        int type = getItemViewType(position);
        Holder1 holder1 = null;
        Holder2 holder2 = null;
        Holder3 holder3 = null;
        if (convertView != null && convertView.getTag() != null) {
            switch (type) {
                case STATE1:
                    convertView.setTag(holder1);
                    break;
                case STATE2:
                    convertView.setTag(holder2);
                    break;
                case SATE3:
                    convertView.setTag(holder3);
                    break;
            }
        } else {
            switch (type) {
                case STATE1://回复，赞布局
                    holder1 = new Holder1();
                    convertView = View.inflate(context, R.layout.item_mess, null);
                    holder1.tv_huifu = (TextView) convertView.findViewById(R.id.tv_huifu);
                    holder1.ll_reply = (LinearLayout) convertView.findViewById(R.id.ll_reply);
                    holder1.cv_head1 = (CircleImageView) convertView.findViewById(R.id.cv_head1);
                    holder1.niName1 = (TextView) convertView.findViewById(R.id.tv_niname);
                    holder1.createTime1 = (TextView) convertView.findViewById(R.id.tv_time);
                    holder1.iv_icon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
                    holder1.tv_talk = (TextView) convertView.findViewById(R.id.tv_talk);
                    holder1.cv_head1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, PerpageAct.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                    ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(circleInfoList.get(position).getUserIcon()), holder1.cv_head1, optionsImag);
                    if (!TextUtils.isEmpty(circleInfoList.get(position).getPetName())) {
                        holder1.niName1.setText(circleInfoList.get(position).getPetName());
                    }
                    if (!TextUtils.isEmpty(circleInfoList.get(position).getCreateTime())) {
                        holder1.createTime1.setText(ZtinfoUtils.timeChange(Long.parseLong(circleInfoList.get(position).getCreateTime())));
                    }
                    ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(UserInfoDao.getUser().getIcon()), holder1.iv_icon, optionsImag);
                    if (!TextUtils.isEmpty(circleInfoList.get(position).getCommentTalk())) {
                        holder1.tv_talk.setText(circleInfoList.get(position).getCommentTalk());
                    }
                    if (circleInfoList.get(position).getMessageState().equals(0 + "")) {
                        holder1.tv_huifu.setText("评论“我”：" + circleInfoList.get(position).getCommentContent());
                    } else if (circleInfoList.get(position).getMessageState().equals(1 + "")) {
                        holder1.tv_huifu.setText("赞了“我”的状态!");
                    }
                    convertView.setTag(holder1);
                    break;
                case STATE2://邀请，拒绝，接受布局
                    holder2 = new Holder2();
                    convertView = View.inflate(context, R.layout.item_mess_one, null);
                    holder2.tv_qingqiu = (TextView) convertView.findViewById(R.id.tv_qingqiu);
                    holder2.tv_beizhu = (TextView) convertView.findViewById(R.id.tv_beizhu);
                    holder2.cv_head2 = (CircleImageView) convertView.findViewById(R.id.cv_head2);
                    holder2.tv_petName = (TextView) convertView.findViewById(R.id.tv_petName);
                    holder2.tv_creatTime = (TextView) convertView.findViewById(R.id.tv_creatTime);
                    holder2.bt_yes = (Button) convertView.findViewById(R.id.bt_yes);
                    holder2.bt_no = (Button) convertView.findViewById(R.id.bt_no);
                    ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(circleInfoList.get(position).getUserIcon()), holder2.cv_head2, optionsImag);
                    if (!TextUtils.isEmpty(circleInfoList.get(position).getPetName())) {
                        holder2.tv_petName.setText(circleInfoList.get(position).getPetName());
                    }
                    if (!TextUtils.isEmpty(circleInfoList.get(position).getCreateTime())) {
                        holder2.tv_creatTime.setText(ZtinfoUtils.timeChange(Long.parseLong(circleInfoList.get(position).getCreateTime())));
                    }
                    holder2.cv_head2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, PerpageAct.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                    final Holder2 finalHolder = holder2;

                    if (circleInfoList.get(position).getMessageState().equals(0 + "")) {
                        holder2.tv_qingqiu.setText("请求加入" + circleInfoList.get(position).getCircleName());
                        holder2.bt_yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalHolder.bt_yes.setEnabled(false);
                                finalHolder.bt_no.setEnabled(false);
                                RequestManager.getTalkManager().sendAgreeCircleResp(circleInfoList.get(position).getCircleId(), circleInfoList.get(position).getUserId(), new ResultCallback<ResultBean<String>>() {
                                    @Override
                                    public void onError(int status, String errorMsg) {
                                        finalHolder.bt_yes.setEnabled(false);
                                        finalHolder.bt_no.setEnabled(false);
                                        MyToastUtils.showShortToast(context,"请求出错！");
                                    }

                                    @Override
                                    public void onResponse(ResultBean<String> response) {
                                        finalHolder.bt_yes.setText("已同意");
                                        finalHolder.bt_yes.setEnabled(false);
                                        finalHolder.bt_no.setEnabled(false);
                                        MyToastUtils.showShortToast(context, "同意" + circleInfoList.get(position).getPetName() + "加入" + circleInfoList.get(position).getCircleName());
                                    }
                                });
                            }
                        });
                        holder2.bt_no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalHolder.bt_yes.setEnabled(false);
                                finalHolder.bt_no.setEnabled(false);
                                RequestManager.getTalkManager().qingRefuseCircleResp(circleInfoList.get(position).getCircleId(), circleInfoList.get(position).getUserId(), new ResultCallback<ResultBean<String>>() {
                                    @Override
                                    public void onError(int status, String errorMsg) {

                                    }

                                    @Override
                                    public void onResponse(ResultBean<String> response) {
                                        finalHolder.bt_no.setText("已拒绝");
                                        finalHolder.bt_yes.setEnabled(false);
                                        finalHolder.bt_no.setEnabled(false);
                                        MyToastUtils.showShortToast(context, "拒绝" + circleInfoList.get(position).getPetName() + "加入" + circleInfoList.get(position).getCircleName());

                                    }
                                });

                            }
                        });
//                        holder2.tv_beizhu.setText("备注：你好，我是李宇春");
                    } else if (circleInfoList.get(position).getMessageState().equals(1 + "")) {
                        holder2.tv_qingqiu.setText("邀请你加入" + circleInfoList.get(position).getCircleName());
                        holder2.bt_yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalHolder.bt_yes.setEnabled(false);
                                finalHolder.bt_no.setEnabled(false);
                                //同意加入
                                RequestManager.getTalkManager().sendAgreeCircleInviteMsg(circleInfoList.get(position).getCircleId(), new ResultCallback<ResultBean<String>>() {
                                    @Override
                                    public void onError(int status, String errorMsg) {
                                        finalHolder.bt_yes.setEnabled(false);
                                        finalHolder.bt_no.setEnabled(false);
                                    }

                                    @Override
                                    public void onResponse(ResultBean<String> response) {
                                        finalHolder.bt_yes.setText("已加入");
                                        finalHolder.bt_yes.setEnabled(false);
                                        finalHolder.bt_no.setEnabled(false);
                                        MyToastUtils.showShortToast(context, "已加入" + circleInfoList.get(position).getCircleName());
                                    }
                                });
                            }
                        });
                        holder2.bt_no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalHolder.bt_yes.setEnabled(false);
                                finalHolder.bt_no.setEnabled(false);
                                //拒绝加入
                                RequestManager.getTalkManager().sendRefuseCircleResp(circleInfoList.get(position).getCircleId(), new ResultCallback<ResultBean<String>>() {
                                    @Override
                                    public void onError(int status, String errorMsg) {
                                        finalHolder.bt_yes.setEnabled(false);
                                        finalHolder.bt_no.setEnabled(false);
                                    }

                                    @Override
                                    public void onResponse(ResultBean<String> response) {
                                        finalHolder.bt_no.setText("已拒绝");
                                        finalHolder.bt_no.setEnabled(false);
                                        finalHolder.bt_yes.setEnabled(false);
                                        MyToastUtils.showShortToast(context, "已拒绝加入" + circleInfoList.get(position).getCircleName());
                                    }
                                });
                            }
                        });
//                        holder2.tv_beizhu.setText("备注：一起去买菜");
                    }
                    convertView.setTag(holder2);
                    break;
                case SATE3://同意，拒绝提示布局
                    holder3 = new Holder3();
                    convertView = View.inflate(context, R.layout.item_mess_two, null);
                    holder3.tv_shenqing = (TextView) convertView.findViewById(R.id.tv_shenqing);
                    holder3.cv_head3 = (CircleImageView) convertView.findViewById(R.id.cv_head3);
                    holder3.tv_creatTime = (TextView) convertView.findViewById(R.id.tv_creatTime);
                    holder3.tv_petName = (TextView) convertView.findViewById(R.id.tv_petName);
                    ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(circleInfoList.get(position).getUserIcon()),holder3.cv_head3,optionsImag);
                    if (!TextUtils.isEmpty(circleInfoList.get(position).getPetName())) {
                        holder3.tv_petName.setText(circleInfoList.get(position).getPetName());
                    }
                    if (!TextUtils.isEmpty(circleInfoList.get(position).getCreateTime())) {
                        holder3.tv_creatTime.setText(ZtinfoUtils.timeChange(Long.parseLong(circleInfoList.get(position).getCreateTime())));
                    }
                    holder3.cv_head3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, PerpageAct.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                    if (circleInfoList.get(position).getMessageState().equals(0 + "")) {
                        holder3.tv_shenqing.setText("同意了我的请求，已加入"+circleInfoList.get(position).getCircleName());
                    } else if (circleInfoList.get(position).getMessageState().equals(1 + "")) {
                        holder3.tv_shenqing.setText("拒绝了我的请求，不参加"+circleInfoList.get(position).getCircleName());
                    }else if (circleInfoList.get(position).getMessageState().equals(2+"")){
                        holder3.tv_shenqing.setText("同意了我的邀请，已加入"+circleInfoList.get(position).getCircleName());
                    }else if (circleInfoList.get(position).getMessageState().equals(3+"")){
                        holder3.tv_shenqing.setText("拒绝了我的邀请，不参加"+circleInfoList.get(position).getCircleName());
                    }
                    convertView.setTag(holder3);
                    break;
            }
        }

        return convertView;
    }

    static class Holder1 {
        private TextView tv_huifu;
        private LinearLayout ll_reply;
        private CircleImageView cv_head1;
        private TextView niName1;
        private TextView createTime1;
        private CircleImageView iv_icon;
        private TextView tv_talk;
    }

    static class Holder2 {
        private TextView tv_qingqiu;
        private TextView tv_beizhu;
        private Button bt_yes, bt_no;
        private CircleImageView cv_head2;
        private TextView tv_petName;
        private TextView tv_creatTime;
    }

    static class Holder3 {
        private TextView tv_shenqing;
        private CircleImageView cv_head3;
        private TextView tv_petName;
        private TextView tv_creatTime;
    }
}
