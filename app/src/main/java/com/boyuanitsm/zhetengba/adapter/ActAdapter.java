package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.PersonalmesAct;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.view.CustomDialog;

import java.util.List;

/**
 * 活动listview适配器.
 * 设置条目点击
 * Created by xiaoke on 2016/4/25.
 */
public class ActAdapter extends BaseAdapter{
    private Context context;
    private boolean image_record_out;

    public ActAdapter(Context context) {
        this.context=context;
    }

    private List<SimpleInfo> infos ;

    public ActAdapter(Context context, List<SimpleInfo> infos) {
        this.infos = infos ;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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
            convertView.setTag(viewHolder);

        }
        viewHolder.tv_niName.setText("会说话的猫");
        viewHolder.tv_loaction.setText("汤泉国际");
        viewHolder.tv_hdtheme.setText("周末一起去旅游吧");
        viewHolder.tv_guanzhu_num.setText(0+"");
        viewHolder.tv_join_num.setText(0+"");
        viewHolder.tv_join_tal_num.setText(15+"");
        viewHolder.tv_date.setText("3月6日 15：00—18：30");
        viewHolder.tv_join_num.setTextColor(Color.parseColor("#999999"));
        viewHolder.iv_headphoto.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.test_user));
        viewHolder.iv_gender.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.female));
        viewHolder.iv_actdetial.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.test_01));
           viewHolder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.add));
        viewHolder.iv_simple_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect));//默认图标

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
                showDialog();
            }
        };
        viewHolder.tv_hdtheme.setOnClickListener(listener);
        viewHolder.tv_loaction.setOnClickListener(listener);
        viewHolder.iv_actdetial.setOnClickListener(listener);
        viewHolder.tv_date.setOnClickListener(listener);


        //展示个人资料
        View.OnClickListener listener1=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context, PersonalmesAct.class);
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
     *
     * @param
     */
    private void showDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
//        builder.setPositiveButton("你们两个是同事", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.setNegativeButton("共参加过2次活动", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        builder.create().show();


    }
//
//    public interface IUpdateZan {
//       void registGuanZhu();
//       void registJoin();
//    }

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

    }


}
