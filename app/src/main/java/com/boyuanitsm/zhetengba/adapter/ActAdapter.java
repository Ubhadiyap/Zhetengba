package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.fragment.CalendarFrg;
import com.boyuanitsm.zhetengba.fragment.SimpleFrg;
import com.boyuanitsm.zhetengba.view.CustomDialog;

import org.w3c.dom.ProcessingInstruction;

/**
 * 活动listview适配器.
 * 设置条目点击
 * Created by xiaoke on 2016/4/25.
 */
public class ActAdapter extends BaseAdapter{
/*    private List<Person> persons;*/
    private Context context;
    private LinearLayout ll_person;//个人信息id
    private TextView tv_hdtheme;//活动主题
    private TextView tv_loaction;//活动位置
    private TextView tv_date;//活动日期
    private LinearLayout ll_guanzhu;//关注数量
    private ImageView iv_simple_guanzhu;//关注图标
    private TextView tv_guanzhu_num;//关注数量设置
    private LinearLayout ll_join;//参加人数
    private ImageView iv_join;//参加头像
    private TextView tv_join_num;//参加数量
    private TextView tv_join_tal_num;//活动总人数设置
    private ImageView iv_actdetial;//活动标签
    private TextView tv_text_jion;//参加，取消参加
    private TextView tv_text_guanzhu;//关注文本
    private int gznum=0;//默认关注人数0
    private boolean image_record_out;
    private IUpdateZan iUpdateZan;

    public ActAdapter(Context context ,IUpdateZan updateZan) {
        this.context=context;
        this.iUpdateZan=updateZan;
    }

    @Override
    public int getCount() {
        return 3;
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
        //设置关注图标动态效果
        viewHolder.ll_guanzhu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        switch (v.getId()) {
                            case R.id.ll_guanzhu:
                                image_record_out = false;
                                viewHolder.iv_simple_guanzhu.setAlpha(0.5f);
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        switch (v.getId()) {//手指一旦离开点赞的控件，就把点击事件取消
                            case R.id.ll_guanzhu:
                                int x = (int) event.getX();
                                int y = (int) event.getY();
                                if (x < 0 || y < 0 || x > viewHolder.ll_guanzhu.getWidth() || y > viewHolder.ll_guanzhu.getHeight()) {
                                    image_record_out = true;
                                }
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        switch (v.getId()) {
                            case R.id.ll_guanzhu://点赞
                                viewHolder.iv_simple_guanzhu.setAlpha(1.0f);
                               /* *//**//*if (!image_record_out) {
                                    //这里开始啦
                                    // 得到你点击的item的position；然后请求你的网络接口，你会问这个网络接口是啥子，这么说：写后台那个人给你写的网络接口。
                                    // commentAttention这个就是我调用网络接口的方法，我这里就直接强转了。
                                    // commentAttention方法就在InformationActivity类里面
                                    ((InformationActivity) context).commentAttention(position);
                                }*//**//**/
                                /*((CalendarFrg)context).updateZan(position);*/
                                iUpdateZan.registGuanZhu(position + 1);
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        switch (v.getId()) {
                            case R.id.ll_guanzhu:
                                viewHolder.iv_simple_guanzhu.setAlpha(1.0f);
                                break;
                        }
                }
                return true;
            }
        });
        //设置参加图标动态效果
        viewHolder.ll_join.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        switch (v.getId()) {
                            case R.id.ll_join:
                                image_record_out = false;
                                viewHolder.iv_join.setAlpha(0.5f);
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        switch (v.getId()) {//手指一旦离开点赞的控件，就把点击事件取消
                            case R.id.ll_join:
                                int x = (int) event.getX();
                                int y = (int) event.getY();
                                if (x < 0 || y < 0 || x > viewHolder.ll_join.getWidth() || y > viewHolder.ll_join.getHeight()) {
                                    image_record_out = true;
                                }
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        switch (v.getId()) {
                            case R.id.ll_join://点赞
                                viewHolder.iv_join.setAlpha(1.0f);
                               /* *//**//*if (!image_record_out) {
                                    //这里开始啦
                                    // 得到你点击的item的position；然后请求你的网络接口，你会问这个网络接口是啥子，这么说：写后台那个人给你写的网络接口。
                                    // commentAttention这个就是我调用网络接口的方法，我这里就直接强转了。
                                    // commentAttention方法就在InformationActivity类里面
                                    ((InformationActivity) context).commentAttention(position);
                                }*//**//**/
                                /*((CalendarFrg)context).updateZan(position);*/
                                iUpdateZan.registJoin(position + 1);
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        switch (v.getId()) {
                            case R.id.ll_join:
                                viewHolder.iv_join.setAlpha(1.0f);
                                break;
                        }
                }
                return true;
            }
        });
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
          /*init(view);*/
         /* initData();*/
        return convertView;
    }
    public interface IUpdateZan{
        void registGuanZhu(int position);
        void registJoin(int position);

    }
    /***
     * 填充控件数据
     */
    private void initData() {


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

//    /***
//     * 初始化条目控件
//     * @param view
//     */
//   private void init(View view) {
//        ll_person = (LinearLayout) view.findViewById(R.id.ll_person);
//        tv_hdtheme = (TextView) view.findViewById(R.id.tv_hdtheme);
//        tv_loaction= (TextView) view.findViewById(R.id.tv_loaction);
//        tv_date= (TextView)view.findViewById(R.id.tv_date);
//        ll_guanzhu= (LinearLayout)view.findViewById(R.id.ll_guanzhu);
//       iv_simple_guanzhu= (ImageView)view.findViewById(R.id.iv_simple_guanzhu);
//        tv_guanzhu_num= (TextView)view.findViewById(R.id.tv_guanzhu_num);
//        ll_join= (LinearLayout)view.findViewById(R.id.ll_join);
//        tv_loaction= (TextView)view.findViewById(R.id.tv_loaction);
//        iv_join = (ImageView) view.findViewById(R.id.iv_join);
//        tv_join_num= (TextView)view.findViewById(R.id.tv_join_num);
//        tv_join_tal_num= (TextView)view.findViewById(R.id.tv_join_tal_num);
//        tv_text_jion = (TextView)view.findViewById(R.id.tv_text_jion);
//
//   }


   /* @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_person:
               //展示个人页面
                break;
            case R.id.tv_hdtheme:
                //显示活动主题dialog
                break;
            case  R.id.ll_theme_location:
                //显示活动主题dialog
                break;
            case  R.id.tv_date:
                //显示活动主题dialog
                break;
         *//*   case R.id.ll_guanzhu:
                //关注活动，关注图标变色，关注数目tv_guanzhu_num++；
                changeIconText(v,tag);
                tag=false;
                break;*//*
            case R.id.ll_join:
                //参加活动，参加活动图标变色，活动数目tv_join_num++，颜色变红；tv_join_tal总数不变。
                break;
            case R.id.iv_actdetial:
                //显示活动主题dialog
                break;

        }
        
    }*/

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
