package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mess.PerpageAct;
import com.boyuanitsm.zhetengba.bean.ScheduleInfo;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 档期 listview适配器
 * Created by xiaoke on 2016/4/27.
 */
public class CalAdapter extends BaseAdapter {
    private Context context;
    private List<ScheduleInfo> list;
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    public   CalAdapter(Context context, List<ScheduleInfo> list){
        this.context=context;
        this.list=list;
    }
    public void update(List<ScheduleInfo> datas) {
        this.list=list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CalHolder calHolder;
        if (convertView!=null&&convertView.getTag()!=null){
           calHolder= (CalHolder) convertView.getTag();
        }else {
            convertView=View.inflate(context,R.layout.item_calen,null);
            calHolder=new CalHolder();
            calHolder.iv_icon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            calHolder.tv_Name=(TextView)convertView.findViewById(R.id.tv_Name);
            calHolder.iv_gen=(ImageView)convertView.findViewById(R.id.iv_gen);
            calHolder.tv_time_cal=(TextView)convertView.findViewById(R.id.tv_time_cal);
            calHolder.tv_state=(TextView)convertView.findViewById(R.id.tv_state);
            calHolder.ll_cal_guanzhu=(LinearLayout)convertView.findViewById(R.id.ll_cal_guanzhu);
            calHolder.iv_cal_guanzhu= (ImageView) convertView.findViewById(R.id.iv_cal_guanzhu);
            calHolder.tv_cal_text_guanzhu=(TextView)convertView.findViewById(R.id.tv_cal_text_guanzhu);
            calHolder.tv_gzcal_num=(TextView)convertView.findViewById(R.id.tv_gzcal_num);
            calHolder.ll_yh=(LinearLayout)convertView.findViewById(R.id.ll_yh);
            calHolder.iv_cal_yh=(ImageView)convertView.findViewById(R.id.iv_cal_yh);
            calHolder.tv_cal_yh=(TextView)convertView.findViewById(R.id.tv_cal_yh);
            calHolder.ll_guanzhu=(LinearLayout)convertView.findViewById(R.id.ll_guanzhu);
            calHolder.ll_yue=(LinearLayout)convertView.findViewById(R.id.ll_yue);
            calHolder.ll_name = (LinearLayout) convertView.findViewById(R.id.ll_name);
            convertView.setTag(calHolder);
        }

        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getUserIcon()),calHolder.iv_icon,optionsImag);//用户头像；
//        calHolder.iv_icon.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.test_user));
        if (!TextUtils.isEmpty(list.get(position).getUserNm())){
            calHolder.tv_Name.setText(list.get(position).getUserNm());//用户昵称
        }else {
            calHolder.tv_Name.setText("无用户名");
        }
       if (list.get(position).getUserSex()=="1"){
           calHolder.iv_gen.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.male));//用户性别
       }else {
           calHolder.iv_gen.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.female));
       }

        calHolder.tv_time_cal.setText(ZhetebaUtils.timeToDate(Long.parseLong(list.get(position).getStartTime()))+ "—" + ZhetebaUtils.timeToDate(Long.parseLong(list.get(position).getEndTime())));
       if (!TextUtils.isEmpty(list.get(position).getDictName())){
           calHolder.tv_state.setText(list.get(position).getDictName());//标签名称
       }

//        if (list.get(position).)//是否关注
        calHolder.iv_cal_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect));
        calHolder.tv_cal_text_guanzhu.setText("关注");
        if (list.get(position).getFollowNum()!=null){
            calHolder.tv_gzcal_num.setText(list.get(position).getFollowNum()+"");
        }else {
            calHolder.tv_gzcal_num.setText(0+"");
        }

        calHolder.iv_cal_yh.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.finger));
        calHolder.tv_cal_yh.setText("约Ta");
//        calHolder.ll_guanzhu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calHolder.iv_cal_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect_b));
//            }
//        });
//        calHolder.tv_cal_text_guanzhu.setText("1");
        calHolder.ll_yue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calHolder.iv_cal_yh.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.finger_b));
            }
        });
        //点击头像昵称进入个人主页
        calHolder.ll_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PerpageAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }


    public static class CalHolder {
        public CircleImageView iv_icon;//头像
        public TextView tv_Name;//昵称
        public ImageView iv_gen;//性别
        public TextView tv_time_cal;//活动日期
        public TextView tv_state;//状态
        public LinearLayout ll_cal_guanzhu;//关注数量
        public ImageView iv_cal_guanzhu;//关注图标
        public TextView tv_cal_text_guanzhu;//关注文本
        public TextView tv_gzcal_num;//关注数量设置
        public LinearLayout ll_yh;//约会
        public ImageView iv_cal_yh;//参加头像
        public TextView tv_cal_yh;//参加数量
        public int cal_gznum=0;//默认关注人数0
        public LinearLayout ll_guanzhu;//关注
        public LinearLayout ll_yue;//约TA
        private LinearLayout ll_name;//个人昵称
    }
}
