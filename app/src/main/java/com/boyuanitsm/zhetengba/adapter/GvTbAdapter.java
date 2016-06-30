package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.publish.ContractedAct;
import com.boyuanitsm.zhetengba.bean.ActivityLabel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简约界面：标签适配器
 * Created by xiaoke on 2016/5/11.
 */
public class GvTbAdapter extends BaseAdapter {
    private Context context;
    private ContractedAct act;
    private List<String> tabList;
    private Map<Integer, String> map;
    private boolean flag = false;
    private int clickTemp = -1;//标识选择的Item
    private final String[] TABSTR = {"美食", "旅行", "K歌", "电影", "运动", "棋牌", "演出", "亲子", "逛街", "读书", "美容", "其他"};
    private List<ActivityLabel> list;

    public GvTbAdapter(Context context, ContractedAct act) {
        this.context = context;
        this.act = act;
        map = new HashMap<>();
    }
    public GvTbAdapter(Context context,List<ActivityLabel> list){
        this.context=context;
        this.list=list;
    }

    public void setSeclection(int position) {
        clickTemp = position;
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
        final TabHolder tabHolder;
        if (convertView != null && convertView.getTag() != null) {
            tabHolder = (TabHolder) convertView.getTag();
        } else {
            tabHolder = new TabHolder();
            convertView = View.inflate(context, R.layout.item_grid_tab, null);
            tabHolder.tv_tab = (TextView) convertView.findViewById(R.id.tv_tab);
            convertView.setTag(tabHolder);
        }

        tabHolder.tv_tab.setText(list.get(position).getLabelName());
//        tabHolder.tv_tab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!flag){
//                    tabHolder.tv_tab.setTextColor(Color.parseColor("#ffffff"));
//                    tabHolder.tv_tab.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_circle_stroke_green));
//                    map.put(position, tabHolder.tv_tab.getText().toString() + "");
//                    act.setMap(map);
//                    flag=true;
//
//                }else {
//                    tabHolder.tv_tab.setTextColor(Color.parseColor("#333333"));
//                    tabHolder.tv_tab.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_circle_stroke_green_boder));
//                    map.remove(position);
//                    act.setMap(map);
//                    flag=false;
//                }

//            }
//        });
        //设置点击颜色变换
        if (clickTemp == position) {
            tabHolder.tv_tab.setTextColor(Color.parseColor("#ffffff"));
            tabHolder.tv_tab.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_circle_stroke_green));
//            map.put(position, tabHolder.tv_tab.getText().toString() + "");
//            act.setMap(map);
        } else {
            tabHolder.tv_tab.setTextColor(Color.parseColor("#333333"));
            tabHolder.tv_tab.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_circle_stroke_green_boder));
//            map.remove(position);
//            act.setMap(map);
        }
        return convertView;
    }

    class TabHolder {
        private TextView tv_tab;
        private LinearLayout ll_gv_tab;
    }

}
