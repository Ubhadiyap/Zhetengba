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
    private IUpdateYh iUpdateYh;
    private boolean image_record_out1,image_record_out2;
    public interface IUpdateYh{
        void registCalGuanZhu(int position);
        void registCalYh(int position);

    }
    public   CalAdapter(Context context,IUpdateYh iUpdateYh1){
        this.context=context;
        this.iUpdateYh=iUpdateYh1;
    }
    @Override
    public int getCount() {
        return 10;
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
        //设置图标动态效果
        calHolder.ll_cal_guanzhu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        switch (v.getId()) {
                            case R.id.ll_cal_guanzhu:
                                image_record_out1 = false;
                                calHolder.iv_cal_guanzhu.setAlpha(0.5f);
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        switch (v.getId()) {//手指一旦离开点赞的控件，就把点击事件取消
                            case R.id.ll_cal_guanzhu:
                                int x = (int) event.getX();
                                int y = (int) event.getY();
                                if (x < 0 || y < 0 || x > calHolder.iv_cal_guanzhu.getWidth() || y >calHolder.iv_cal_guanzhu.getHeight()) {
                                    image_record_out1 = true;
                                }
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        switch (v.getId()) {
                            case R.id.ll_cal_guanzhu://点赞
                                calHolder.iv_cal_guanzhu.setAlpha(1.0f);
                               /* *//**//*if (!image_record_out) {
                                    //这里开始啦
                                    // 得到你点击的item的position；然后请求你的网络接口，你会问这个网络接口是啥子，这么说：写后台那个人给你写的网络接口。
                                    // commentAttention这个就是我调用网络接口的方法，我这里就直接强转了。
                                    // commentAttention方法就在InformationActivity类里面
                                    ((InformationActivity) context).commentAttention(position);
                                }*//**//**/
                                /*((CalendarFrg)context).updateZan(position);*/
                                iUpdateYh.registCalGuanZhu(position+1);
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        switch (v.getId()) {
                            case R.id.ll_cal_guanzhu:
                                calHolder.iv_cal_guanzhu.setAlpha(1.0f);
                                break;
                        }
                }
                return true;
            }
        });
        //设置约会图标动态效果
        calHolder.ll_yh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        switch (v.getId()) {
                            case R.id.ll_yh:
                                image_record_out2 = false;
                                calHolder.iv_cal_yh.setAlpha(0.5f);
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        switch (v.getId()) {//手指一旦离开点赞的控件，就把点击事件取消
                            case R.id.ll_yh:
                                int x = (int) event.getX();
                                int y = (int) event.getY();
                                if (x < 0 || y < 0 || x > calHolder.iv_cal_yh.getWidth() || y >calHolder.iv_cal_yh.getHeight()) {
                                    image_record_out1 = true;
                                }
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        switch (v.getId()) {
                            case R.id.ll_yh://点赞
                                calHolder.iv_cal_yh.setAlpha(1.0f);
                               /* *//**//*if (!image_record_out) {
                                    //这里开始啦
                                    // 得到你点击的item的position；然后请求你的网络接口，你会问这个网络接口是啥子，这么说：写后台那个人给你写的网络接口。
                                    // commentAttention这个就是我调用网络接口的方法，我这里就直接强转了。
                                    // commentAttention方法就在InformationActivity类里面
                                    ((InformationActivity) context).commentAttention(position);
                                }*//**//**/
                                /*((CalendarFrg)context).updateZan(position);*/
                                iUpdateYh.registCalYh(position+1);
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        switch (v.getId()) {
                            case R.id.ll_yh:
                                calHolder.iv_cal_yh.setAlpha(1.0f);
                                break;
                        }
                }
                return true;
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

    }
}
