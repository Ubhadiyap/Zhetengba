package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

/**
 * 档期 listview适配器
 * Created by xiaoke on 2016/4/27.
 */
public class CalAdapter extends BaseAdapter {
    private Context context;
    public   CalAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 4;
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
        final CalHolder calHolder;
        if (convertView!=null&&convertView.getTag()!=null){
           calHolder= (CalHolder) convertView.getTag();
        }else {
            convertView=View.inflate(context,R.layout.item_calen,null);
            calHolder=new CalHolder();
            calHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
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
            convertView.setTag(calHolder);
        }
        calHolder.iv_icon.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.test_user));
        calHolder.tv_Name.setText("会说话的Tom");
        calHolder.iv_gen.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.male));
        calHolder.tv_time_cal.setText("3月6日 15：00—18：30");
        calHolder.tv_state.setText("无聊透顶");
        calHolder.iv_cal_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect));
        calHolder.tv_cal_text_guanzhu.setText("关注");
        calHolder.tv_gzcal_num.setText("0");
        calHolder.iv_cal_yh.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.finger));
        calHolder.tv_cal_yh.setText("约Ta");
        calHolder.ll_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calHolder.iv_cal_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect_b));
            }
        });
        calHolder.tv_cal_text_guanzhu.setText("1");
        calHolder.ll_yue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calHolder.iv_cal_yh.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.finger_b));
            }
        });

        return convertView;
    }
    public static class CalHolder {
        public ImageView iv_icon;//头像
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
    }
}
