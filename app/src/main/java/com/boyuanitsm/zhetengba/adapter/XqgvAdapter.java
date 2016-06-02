package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.RegInfoAct;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bitch-1 on 2016/6/1.
 */
public class XqgvAdapter extends BaseAdapter{
    private Context context;
    private Map<Integer,String> map;
    private RegInfoAct activity;

    public XqgvAdapter(Context context,RegInfoAct activity) {
        this.context = context;
        map=new HashMap<>();
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return 16;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int dex=position;
        View view=View.inflate(context, R.layout.item_gv,null);
        final CheckBox ck= (CheckBox) view.findViewById(R.id.ck_xq);
        ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if(isChecked){
                 map.put(dex,ck.getText().toString());
                 activity.setData(map);
             }else {
                 map.remove(dex);
                 activity.setData(map);
             }
            }
        });

        return view;
    }
}
