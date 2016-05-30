package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.boyuanitsm.zhetengba.R;

/**
 *圈子发布界面标签适配器
 * Created by bitch-1 on 2016/5/9.
 */
public class CirfbAdapter extends BaseAdapter {
    private CheckBox ck_xx;

    private final String TITLES[] = {"摄影圈", "聚餐圈", "吃饭圈", "桑拿圈", "足球圈",
            "八卦圈","保龄圈","娱乐圈","星座圈","地方圈","明清圈","东方圈"
    };

    private Context context;
    private int clickTemp = -1;//标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

    public CirfbAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Object getItem(int position) {
        return TITLES[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context, R.layout.cir_item,null);
        ck_xx= (CheckBox) view.findViewById(R.id.ck_xx);
        ck_xx.setText(TITLES[position]);
//        ck_xx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        });
        //设置点击颜色变换
        if (clickTemp == position) {
            ck_xx.setChecked(true);
//            ck_xx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                }
//            });
        } else {
            ck_xx.setChecked(false);

        }

        return view;
    }
}
