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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简约界面：标签适配器
 * Created by xiaoke on 2016/5/11.
 */
public class GvTbAdapter extends BaseAdapter{
    private Context context;
    private ContractedAct act;
    private List<String> tabList;
    private Map<Integer,String> map;
    private boolean flag;
    private  final String[] TABSTR={"美食","旅行","K歌","电影","运动","棋牌","演出","亲子","逛街","读书","美容","其他"};
    public GvTbAdapter(Context context,ContractedAct act){
        this.context=context;
        this.act=act;
        map=new HashMap<>();
    }
    @Override
    public int getCount() {
        return TABSTR.length;
    }

    @Override
    public Object getItem(int position) {
        return TABSTR[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TabHolder tabHolder;
        if (convertView!=null&&convertView.getTag()!=null){
           tabHolder= (TabHolder) convertView.getTag();
        }else {
            tabHolder=new TabHolder();
            convertView=View.inflate(context, R.layout.item_grid_tab,null);
            tabHolder.tv_tab= (TextView) convertView.findViewById(R.id.tv_tab);
           tabHolder.ll_gv_tab= (LinearLayout) convertView.findViewById(R.id.ll_gv_tab);
            convertView.setTag(tabHolder);
        }
        tabHolder.tv_tab.setText(TABSTR[position]);
        tabHolder.ll_gv_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag){
                    tabHolder.tv_tab.setTextColor(Color.parseColor("#ffffff"));
                    tabHolder.tv_tab.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_circle_stroke_green));
                    map.put(position, tabHolder.tv_tab.getText().toString() + "");
                    act.setMap(map);
                    flag=true;

                }else {
                    tabHolder.tv_tab.setTextColor(Color.parseColor("#333333"));
                    tabHolder.tv_tab.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_circle_stroke_green_boder));
                    map.remove(position);
                    act.setMap(map);
                    flag=false;
                }

            }
        });
        return convertView;
        }
    class TabHolder{
        private TextView tv_tab;
        private LinearLayout ll_gv_tab;
    }
}
