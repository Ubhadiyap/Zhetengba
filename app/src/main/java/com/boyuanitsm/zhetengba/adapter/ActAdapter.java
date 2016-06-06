package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.boyuanitsm.zhetengba.activity.mess.MessVerifyAct;
import com.boyuanitsm.zhetengba.activity.mess.PerpageAct;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.CustomDialog;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页简约：listview适配器.
 * 设置条目点击
 * Created by xiaoke on 2016/4/25.
 */
public class ActAdapter extends BaseAdapter{
    private Context context;
    private List<SimpleInfo> infos=new ArrayList<>();
    private boolean flag=true;//true参加
    private int joinNum,noticNum;
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public ActAdapter(Context context) {
        this.context=context;
    }

    public ActAdapter(Context context, List<SimpleInfo> infos) {
        this.infos = infos ;
        this.context = context;
    }
    public void update(List<SimpleInfo> list){
        this.infos=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
      return infos==null?0:infos.size();
    }

    @Override
    public Object getItem(int position) {

        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder viewHolder;
        if (convertView != null && convertView.getTag() != null) {
            viewHolder = (Holder) convertView.getTag();

        } else {
           convertView= View.inflate(context, R.layout.item_act, null);
            viewHolder = new Holder();
            viewHolder.iv_headphoto = (CircleImageView) convertView.findViewById(R.id.iv_headphoto);
            viewHolder.tv_niName = (TextView) convertView.findViewById(R.id.tv_niName);
            viewHolder.ll_person = (LinearLayout) convertView.findViewById(R.id.ll_person);
            viewHolder.tv_hdtheme = (TextView) convertView.findViewById(R.id.tv_hdtheme);
            viewHolder.tv_loaction= (TextView) convertView.findViewById(R.id.tv_loaction);
            viewHolder.tv_date= (TextView)convertView.findViewById(R.id.tv_date);
            viewHolder.ll_guanzhu= (LinearLayout)convertView.findViewById(R.id.ll_guanzhu);
            viewHolder.iv_simple_guanzhu= (ImageView)convertView.findViewById(R.id.iv_simple_guanzhu);
            viewHolder.tv_guanzhu_num= (TextView)convertView.findViewById(R.id.tv_guanzhu_num);
            viewHolder.ll_join= (LinearLayout)convertView.findViewById(R.id.ll_join);
            viewHolder.iv_join = (ImageView) convertView.findViewById(R.id.iv_join);
            viewHolder.tv_join_num= (TextView)convertView.findViewById(R.id.tv_join_num);
            viewHolder.tv_join_tal_num= (TextView)convertView.findViewById(R.id.tv_join_tal_num);
            viewHolder.iv_gender= (ImageView) convertView.findViewById(R.id.iv_gender);
            viewHolder.iv_actdetial = (CircleImageView) convertView.findViewById(R.id.iv_actdetial);
            viewHolder.tv_text_jion = (TextView)convertView.findViewById(R.id.tv_text_jion);
            viewHolder.tv_text_guanzhu = (TextView) convertView.findViewById(R.id.tv_guanzhu);
            viewHolder.ll_show= (LinearLayout) convertView.findViewById(R.id.ll_show);
            viewHolder.ll_show2= (LinearLayout) convertView.findViewById(R.id.ll_show2);
            viewHolder.ll_show3= (LinearLayout) convertView.findViewById(R.id.ll_show3);
            viewHolder.ll_del =(LinearLayout) convertView.findViewById(R.id.ll_del);
            viewHolder.ll_simple_share=(LinearLayout)convertView.findViewById(R.id.ll_simple_share);
            viewHolder.ll_theme_location = (LinearLayout)convertView.findViewById(R.id.ll_theme_location);
            convertView.setTag(viewHolder);

        }
        if (infos.get(position).getUserNm()!=null){
            viewHolder.tv_niName.setText(infos.get(position).getUserNm());//字段缺少用户名
        }else {
            viewHolder.tv_niName.setText("无用户名");//字段缺少用户名
        }

        if (infos.get(position).getActivitySite()!=null){
            viewHolder.tv_loaction.setText(infos.get(position).getActivitySite());//活动位置
        }else {
            viewHolder.ll_theme_location.setVisibility(View.GONE);
        }

        viewHolder.tv_hdtheme.setText(infos.get(position).getActivityTheme());//活动主题
        if (infos.get(position).getUserId()!="本地用户id"){
            viewHolder.ll_guanzhu.setVisibility(View.VISIBLE);
            viewHolder.ll_join.setVisibility(View.VISIBLE);
            viewHolder.ll_del.setVisibility(View.GONE);
            viewHolder.ll_simple_share.setVisibility(View.GONE);
        }else {
            viewHolder.ll_guanzhu.setVisibility(View.GONE);
            viewHolder.ll_join.setVisibility(View.GONE);
            viewHolder.ll_del.setVisibility(View.VISIBLE);
            viewHolder.ll_simple_share.setVisibility(View.VISIBLE);
        }
        if (infos.get(position).getFollowNum()!=null){
            viewHolder.tv_guanzhu_num.setText(infos.get(position).getFollowNum().toString());//关注人数
            noticNum=infos.get(position).getFollowNum();//获取关注数
        }else {
            viewHolder.tv_guanzhu_num.setText(0+"");//关注人数
            noticNum=0;//获取关注数
        }
        if (infos.get(position).getMemberNum()!=null){
            viewHolder.tv_join_num.setText(infos.get(position).getMemberNum());//目前成员数量；
            joinNum=infos.get(position).getMemberNum();//获取参加人数
        }else {
            viewHolder.tv_join_num.setText(0+"");//目前成员数量；
            joinNum=0;//获取参加人数
        }

        viewHolder.tv_join_tal_num.setText(infos.get(position).getInviteNumber()+"");//邀约人数
        viewHolder.tv_date.setText(ZhetebaUtils.timeToDate(Long.parseLong(infos.get(position).getStartTime()))+ "—" + ZhetebaUtils.timeToDate(Long.parseLong(infos.get(position).getEndTime())));//活动时间；
//        viewHolder.tv_join_num.setTextColor(Color.parseColor("#999999"));
        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(infos.get(position).getUserIcon()), viewHolder.iv_headphoto, optionsImag);//用户头像
//        viewHolder.iv_headphoto.setBackgroundDrawable(infos.get(position).getIcon());
        if (infos.get(position).getUserSex()=="男"){
            viewHolder.iv_gender.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.male));//用户性别
        }else {
            viewHolder.iv_gender.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.female));//用户性别
        }
//        ImageLoader.getInstance().displayImage(infos.get(position).getUserSex(),viewHolder.iv_gender,optionsImag);//用户性别
        if (infos.get(position).getIcon()!=null){
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(infos.get(position).getIcon()), viewHolder.iv_actdetial, optionsImag);//详情icon
        }
//        viewHolder.iv_actdetial.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.test_01));//labelId详情icon
//        返回状态判断是否参加，
        if (infos.get(position).isJoin()){
            viewHolder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cancel));//参加icon
            viewHolder.tv_text_jion.setText("取消参加");
            viewHolder.tv_join_num.setTextColor(Color.parseColor("#fd3838"));
            viewHolder.ll_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MyAlertDialog(context).builder().setTitle("提示").setMsg("确认取消参加活动？").setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            stateChange(position, viewHolder);
                        }
                    }).setNegativeButton("取消",null).show();
                }
            });
        }else {
            flag=false;
            viewHolder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.add));//参加icon
            viewHolder.tv_text_jion.setText("参加");
            viewHolder.ll_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stateChange(position,viewHolder);
                }
            });
        }

        viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAlertDialog(context).builder().setTitle("提示").setMsg("确认删除此条活动？").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //调用删除此活动接口,刷新数据；
                    }
                }).setPositiveButton("取消",null).show();

            }
        });
        viewHolder.ll_simple_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启分享界面
                Intent intent=new Intent();
                intent.setClass(context,ShareDialogAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


          // viewHolder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.add));//参加icon
//        返回状态判断是否关注;
        if (infos.get(position).isFollow()){
            viewHolder.iv_simple_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect_b));//已关注
            viewHolder.tv_text_guanzhu.setText("已关注");
            viewHolder.ll_guanzhu.setClickable(false);
        }else {
            viewHolder.iv_simple_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect));//默认图标
            viewHolder.tv_text_guanzhu.setText("关注");
            viewHolder.ll_guanzhu.setClickable(true);
            viewHolder.ll_guanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                // 调用关注接口
                    RequestManager.getScheduleManager().getScheduleCollection(infos.get(position).getId(), new ResultCallback<ResultBean<String>>() {
                        @Override
                        public void onError(int status, String errorMsg) {

                        }

                        @Override
                        public void onResponse(ResultBean<String> response) {
                            viewHolder.iv_simple_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect_b));//默认图标
                            viewHolder.tv_text_guanzhu.setText("已关注");
                            infos.get(position).setFollow(true);
                            noticNum=noticNum+1;
                            infos.get(position).setFollowNum(noticNum);
                            viewHolder.tv_guanzhu_num.setText(noticNum+"");
                            viewHolder.ll_guanzhu.setClickable(false);
                            MyToastUtils.showShortToast(context,"已关注");
                        }
                    });

                }
            });
        }
//           //展示活动详情；
//            viewHolder.ll_show.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showDialog(position,infos.get(position).getActivityParticulars(),infos.get(position).isColleagues(),infos.get(position).isFriend());
//                }
//            });

//        viewHolder.tv_guanzhu_num.setText(infos.get(position).getAttentionNum() + "");
//
//        //设置是否可点击关注
//        viewHolder.tv_guanzhu_num.setEnabled(!infos.get(position).isAttention);
//        viewHolder.tv_guanzhu_num.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                //1。访问网络，通知服务器关注当前人
//
//                //2.在服务器返回回调里判断是否添加成功 如果成功则关注数量加1，如果失败，提示用户
//
//                infos.get(position).setAttentionNum(infos.get(position).getAttentionNum()+1);
//                viewHolder.tv_guanzhu_num.setText(infos.get(position).getAttentionNum()+"");
//
//
//            }
//        });

        //展示活动详情
//        View.OnClickListener listener=new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //判断返回活动是否为null；
//                showDialog(position, infos.get(position).getActivityParticulars(), infos.get(position).isColleagues(), infos.get(position).isFriend());
//            }
//        };
//        viewHolder.iv_actdetial.setOnClickListener(listener);


        //展示个人资料
        View.OnClickListener listener1=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context, PerpageAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
        viewHolder.iv_headphoto.setOnClickListener(listener1);
        viewHolder.tv_niName.setOnClickListener(listener1);
          /*init(view);*/
         /* initData();*/
        return convertView;
    }

    /**
    * 参加或响应活动接口
    * @param
    */

    private void stateChange(final int position, final Holder viewHolder) {
        RequestManager.getScheduleManager().getRespondActivity(infos.get(position).getId(), new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
//                            String state=response.getData();//返回关注后状态
                if (flag) {
                    viewHolder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.add));
                    viewHolder.tv_text_jion.setText("参加");
                    viewHolder.tv_join_num.setTextColor(Color.parseColor("#999999"));
                    joinNum = joinNum - 1;
                    viewHolder.tv_join_num.setText(joinNum);
//                    infos.get(position).setJoin(false);;
//                    notifyDataSetChanged();
                    flag = false;
                } else {
                    viewHolder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cancel));//参加icon
                    viewHolder.tv_text_jion.setText("取消参加");
                    viewHolder.tv_join_num.setTextColor(Color.parseColor("#fd3838"));
                    joinNum = joinNum + 1;
                    viewHolder.tv_join_num.setText(joinNum);
                    flag=true;
                }
            }
        });
    }


    public static class Holder {
       public CircleImageView iv_headphoto;//头像
       public TextView tv_niName;//昵称
       public LinearLayout ll_person;//个人信息id
       public TextView tv_hdtheme;//活动主题
       public TextView tv_loaction;//活动位置
       public TextView tv_date;//活动日期
       public LinearLayout ll_guanzhu;//关注数量
       public ImageView iv_simple_guanzhu;//关注图标
       public TextView tv_text_guanzhu;//关注文本
       public TextView tv_guanzhu_num;//关注数量设置
       public LinearLayout ll_join;//参加人数
       public ImageView iv_join;//参加头像
       public TextView tv_join_num;//参加数量
       public TextView tv_join_tal_num;//活动总人数设置
       public CircleImageView iv_actdetial;//活动标签
       public ImageView iv_gender;//性别
       public TextView tv_text_jion;//参加/取消参加
        public LinearLayout ll_theme_location;//活动位置Linear
       public LinearLayout ll_show,ll_show2,ll_show3;
        public LinearLayout ll_del,ll_simple_share;
    }


}
