package com.boyuanitsm.zhetengba.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.TestListView;
import com.boyuanitsm.zhetengba.activity.mess.PerpageAct;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.view.CustomDialog;

import java.util.List;

/**
 * 首页简约：listview适配器.
 * 设置条目点击
 * Created by xiaoke on 2016/4/25.
 */
public class ActAdapter extends BaseAdapter{
    private Context context;
    private List<SimpleInfo> infos ;
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
        if (infos!=null){
            return infos.size();
        }else {
            return 0;
        }

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
            viewHolder.iv_headphoto = (ImageView) convertView.findViewById(R.id.iv_headphoto);
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
            viewHolder.iv_actdetial = (ImageView) convertView.findViewById(R.id.iv_actdetial);
            viewHolder.tv_text_jion = (TextView)convertView.findViewById(R.id.tv_text_jion);
            viewHolder.tv_text_guanzhu = (TextView) convertView.findViewById(R.id.tv_guanzhu);
            viewHolder.ll_show= (LinearLayout) convertView.findViewById(R.id.ll_show);
            viewHolder.ll_show2= (LinearLayout) convertView.findViewById(R.id.ll_show2);
            viewHolder.ll_show3= (LinearLayout) convertView.findViewById(R.id.ll_show3);
            convertView.setTag(viewHolder);

        }
//        viewHolder.tv_niName.setText(infos.get(position));字段缺少用户名
        viewHolder.tv_loaction.setText(infos.get(position).getActivitySite());
        viewHolder.tv_hdtheme.setText(infos.get(position).getActivityTheme());
        viewHolder.tv_guanzhu_num.setText(infos.get(position).getFollowNum());
        viewHolder.tv_join_num.setText(infos.get(position).getMemberNum());
        viewHolder.tv_join_tal_num.setText(infos.get(position).getInviteNumber());
        viewHolder.tv_date.setText(infos.get(position).getStartTime().toString() + infos.get(position).getEndTime().toString());
        viewHolder.tv_join_num.setTextColor(Color.parseColor("#999999"));
//        viewHolder.iv_headphoto.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.test_user));//用户头像
//        viewHolder.iv_gender.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.female));//用户性别
        viewHolder.iv_actdetial.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.test_01));//labelId
           viewHolder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.add));//参加icon
            viewHolder.ll_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });
        viewHolder.iv_simple_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect));//默认图标
//        返回状态判定是否关注
        viewHolder.ll_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.iv_simple_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect_b));//点击关注
                viewHolder.tv_text_guanzhu.setText("已关注");
                viewHolder.tv_guanzhu_num.setText(1+"");
            }
        });
        viewHolder.ll_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cancel));
                viewHolder.tv_text_jion.setText("取消参加");
                viewHolder.tv_join_num.setText("1");
            }
        });

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
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断返回活动是否为null；
                showDialog();
            }
        };
//        viewHolder.tv_hdtheme.setOnClickListener(listener);
//        viewHolder.tv_loaction.setOnClickListener(listener);
        viewHolder.iv_actdetial.setOnClickListener(listener);
//        viewHolder.tv_date.setOnClickListener(listener);
//        viewHolder.ll_show.setOnClickListener(listener);
//        viewHolder.ll_show2.setOnClickListener(listener);
//        viewHolder.ll_show3.setOnClickListener(listener);


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


    /***
     * 设置条目点击显示活动详情dialog
     *1.有活动详情，是好友，2.没有活动详情，陌生人，设置添加好友按钮可见
     * @param
     */
    private void showDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setMessage("没有活动详情");
//        builder.setPositiveButton("加为好友", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                MyToastUtils.showShortToast(context,"点击了第一个button");
//            }
//        });
        builder.setNegativeButton("你们两个是同事", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyToastUtils.showShortToast(context,"点击了第二个button");
            }
        });
        builder.create().show();

    }


    public static class Holder {
       public ImageView iv_headphoto;//头像
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
       public ImageView iv_actdetial;//活动标签
       public ImageView iv_gender;//性别
       public TextView tv_text_jion;//参加/取消参加
       public int gznum=0;//默认关注人数0
       public int jionum=0;//默认参加人数0；
        public LinearLayout ll_show,ll_show2,ll_show3;
    }

    /***
     * 关注活动
     * @param activityId
     */
    private void getActivityCollection(String activityId){
        RequestManager.getScheduleManager().getActivityCollection(activityId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(context,errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                String state=response.getData();//返回关注后状态
            }
        });
    }

    /**
     * 参加或响应活动
     * @param activityId
     */
    private void getRespondActivity(String activityId){
        RequestManager.getScheduleManager().getRespondActivity(activityId, new ResultCallback() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }
}
