package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.boyuanitsm.zhetengba.R;

/**
 * 活动listview适配器
 * Created by xiaoke on 2016/4/25.
 */
public class ActAdapter extends BaseAdapter {
/*    private List<Person> persons;*/
    private Context context;

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
        View view;
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


        return view;
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
