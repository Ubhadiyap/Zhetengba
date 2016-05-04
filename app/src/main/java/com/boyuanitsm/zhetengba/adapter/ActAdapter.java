package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

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
    private int gznum=0;//默认关注人数0
    private boolean tag=true;

    public ActAdapter(Context context) {
        this.context=context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
            convertView.setTag(viewHolder);

        }
        viewHolder.tv_niName.setText("会说话的猫");
        viewHolder.tv_loaction.setText("汤泉国际");
        viewHolder.tv_hdtheme.setText("周末一起去旅游吧");
        viewHolder.tv_guanzhu_num.setText(0+"");
        viewHolder.tv_join_num.setText(0+"");
        viewHolder.tv_join_tal_num.setText(15+"");
        viewHolder.tv_date.setText("3月6日 15：00—18：30");
        viewHolder.iv_headphoto.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.test_user));
        viewHolder.iv_gender.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.female));
        viewHolder.iv_actdetial.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.test_01));
        viewHolder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.add));
        viewHolder.iv_simple_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect));
          /*init(view);*/
         /* initData();*/
        return convertView;
    }

    /***
     * 填充控件数据
     */
    private void initData() {


    }

    /***
     * 初始化条目控件
     * @param view
     */
   private void init(View view) {
        ll_person = (LinearLayout) view.findViewById(R.id.ll_person);
        tv_hdtheme = (TextView) view.findViewById(R.id.tv_hdtheme);
        tv_loaction= (TextView) view.findViewById(R.id.tv_loaction);
        tv_date= (TextView)view.findViewById(R.id.tv_date);
        ll_guanzhu= (LinearLayout)view.findViewById(R.id.ll_guanzhu);
       iv_simple_guanzhu= (ImageView)view.findViewById(R.id.iv_simple_guanzhu);
        tv_guanzhu_num= (TextView)view.findViewById(R.id.tv_guanzhu_num);
        ll_join= (LinearLayout)view.findViewById(R.id.ll_join);
        tv_loaction= (TextView)view.findViewById(R.id.tv_loaction);
        iv_join = (ImageView) view.findViewById(R.id.iv_join);
        tv_join_num= (TextView)view.findViewById(R.id.tv_join_num);
        tv_join_tal_num= (TextView)view.findViewById(R.id.tv_join_tal_num);

    }


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

    /***
     * 修改背景，数目
     * @param v
     * @param tag
     */
    private void changeIconText(View v, boolean tag) {
        if (tag==true){
            iv_simple_guanzhu.setBackgroundDrawable(v.getResources().getDrawable(R.drawable.collect_b));
            tv_guanzhu_num.setText("关注" + (gznum++));
                    tag=false;

        }else {
            iv_simple_guanzhu.setBackgroundDrawable(v.getResources().getDrawable(R.drawable.collect));
            tv_guanzhu_num.setText("参加" + (gznum--));
            tag=true;
        }


    }

    class Holder {
        private ImageView iv_headphoto;//头像
        private TextView tv_niName;//昵称
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
        private ImageView iv_gender;//性别
        private int gznum=0;//默认关注人数0
        private boolean tag=true;

    }
}
