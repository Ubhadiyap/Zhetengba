package com.boyuanitsm.zhetengba.activity;

import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.EventdetailsAct;
import com.boyuanitsm.zhetengba.adapter.GvTbAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.view.MyGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简约界面
 * Created by bitch-1 on 2016/5/3.
 */
public class ContractedAct extends BaseActivity implements View.OnClickListener{
    private TextView tv_select_location;
    private LinearLayout ll_theme_content;
    private MyGridView gv_tab;
    private LinearLayout ll_view;
    private LinearLayout ll_select_tab;
    private ImageView iv_arrow;
    private Map<Integer,String> map;
    private boolean flag=true;

    private List<String> tabList=new ArrayList<>();
    @Override
    public void setLayout() {
        setContentView(R.layout.act_contracted);
    }

    @Override
    public void init(Bundle savedInstanceState) {
    setTopTitle("简约");
        tv_select_location = (TextView) findViewById(R.id.tv_select);
         ll_theme_content = (LinearLayout) findViewById(R.id.ll_theme_content);
        ll_view = (LinearLayout) findViewById(R.id.ll_view);
        gv_tab = (MyGridView) findViewById(R.id.gv_tab);
        ll_select_tab = (LinearLayout) findViewById(R.id.ll_select_tab);
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
        //设置标签的，适配器
        GvTbAdapter adapter=new GvTbAdapter(this,this);
        gv_tab.setAdapter(adapter);
        tv_select_location.setOnClickListener(this);
        ll_theme_content.setOnClickListener(this);
        ll_select_tab.setOnClickListener(this);

    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_select:
                openActivity(LocationAct.class);
                break;
            case R.id.ll_theme_content:
                openActivity(EventdetailsAct.class);
                break;
            case R.id.ll_select_tab:
                if (flag){
                    ll_view.setVisibility(View.VISIBLE);
                    gv_tab.setClickable(true);
                    iv_arrow.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.arrow_down2));

                    flag=false;
                }else {
                    ll_view.setVisibility(View.INVISIBLE);
                    ll_view.setVisibility(View.GONE);
                    iv_arrow.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.arrow_right));
                    flag=true;
                }
                break;
        }
    }
}
