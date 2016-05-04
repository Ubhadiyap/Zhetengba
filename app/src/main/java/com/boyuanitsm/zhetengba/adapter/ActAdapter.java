package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
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
public class ActAdapter extends BaseAdapter implements View.OnClickListener{
/*    private List<Person> persons;*/
    private Context context;
    private View view;

    private LinearLayout ll_person;//个人信息id
    private TextView tv_hdtheme;//活动主题
    private TextView tv_loaction;//活动位置
    private TextView tv_date;//活动日期
    private LinearLayout ll_guanzhu;//关注数量
    private ImageView iv_guanzhu;//关注图标
    private TextView tv_guanzhu_num;//关注数量设置
    private LinearLayout ll_join;//参加人数
    private ImageView iv_join;//参加头像
    private TextView tv_join_num;//参加数量
    private TextView tv_join_tal_num;//活动总人数设置

    public ActAdapter(Context context) {
        this.context=context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
/*
        Person person = persons.get(position);
        final Holder viewHolder;
        if (convertView != null && convertView.getTag() != null) {
            viewHolder = (Holder) convertView.getTag();

        } else {
            View.inflate(context, R.layout.item_act, null);
            viewHolder = new Holder();
            viewHolder.iv_headphoto = (ImageView) convertView.findViewById(R.id.iv_headphoto);
            viewHolder.iv_gender = (ImageView) convertView.findViewById(R.id.iv_gender);
            viewHolder.tv_niName = (TextView) convertView.findViewById(R.id.tv_nickName);
            viewHolder.tv_adr = (TextView) convertView.findViewById(R.id.tv_adr);
            viewHolder.tv_guanzhu_num = (TextView) convertView.findViewById(R.id.tv_guanzhu_num);
            viewHolder.tv_hdtheme = (TextView) convertView.findViewById(R.id.tv_hdtheme);
            viewHolder.iv_actdetial = (ImageView) convertView.findViewById(R.id.iv_actdetial);
            viewHolder.tv_xiangying_num = (TextView) convertView.findViewById(R.id.tv_xiangying_num);
            viewHolder.tv_tatol_num = (TextView) convertView.findViewById(R.id.tv_tatl_num);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);

        }
        viewHolder.tv_niName.setText(person.getNickName());
        viewHolder.tv_adr.setText(person.getAddr());
        viewHolder.tv_hdtheme.setText(person.getAct());
        viewHolder.tv_guanzhu_num.setText(person.getAttentionnumber());
        viewHolder.tv_xiangying_num.setText(person.getAnswernumber());
        viewHolder.tv_tatol_num.setText(person.getAnswertatolnumber());
        viewHolder.tv_time.setText((CharSequence) person.getCustime());
        viewHolder.iv_gender.setImageDrawable(Drawable.createFromPath(person.getHeadPhoto()));
*/
            view=View.inflate(context, R.layout.item_act,null);
            init();

        return view;
    }

    /***
     * 初始化条目控件
     */
    private void init() {
        ll_person = (LinearLayout) view.findViewById(R.id.ll_person);
        tv_hdtheme = (TextView) view.findViewById(R.id.tv_hdtheme);
        tv_loaction= (TextView) view.findViewById(R.id.tv_loaction);
        tv_date= (TextView) view.findViewById(R.id.tv_date);
        ll_guanzhu= (LinearLayout) view.findViewById(R.id.ll_guanzhu);
        iv_guanzhu= (ImageView) view.findViewById(R.id.iv_guanzhu);
        tv_guanzhu_num= (TextView) view.findViewById(R.id.tv_guanzhu_num);
        ll_join= (LinearLayout) view.findViewById(R.id.ll_join);
        tv_loaction= (TextView) view.findViewById(R.id.tv_loaction);
        iv_join = (ImageView) view.findViewById(R.id.iv_join);
        tv_join_num= (TextView) view.findViewById(R.id.tv_join_num);
        tv_join_tal_num= (TextView) view.findViewById(R.id.tv_join_tal_num);

    }


    @Override
    public void onClick(View v) {
        
    }

/*    class Holder {
        private ImageView iv_headphoto;
        private TextView tv_niName;
        private ImageView iv_gender;
        private TextView tv_hdtheme;
        private ImageView iv_actdetial;
        private TextView tv_adr;
        private TextView tv_time;
        private TextView tv_guanzhu_num;
        private TextView tv_xiangying_num;
        private TextView tv_tatol_num;

    }*/
}
