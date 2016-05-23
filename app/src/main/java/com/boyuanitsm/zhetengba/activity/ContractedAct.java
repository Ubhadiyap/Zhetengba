package com.boyuanitsm.zhetengba.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.EventdetailsAct;
import com.boyuanitsm.zhetengba.adapter.GvTbAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.widget.time.TimeDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 简约界面
 * Created by bitch-1 on 2016/5/3.
 */
public class ContractedAct extends BaseActivity {
    @ViewInject(R.id.tv_select)
    private TextView tv_select_location;
    @ViewInject(R.id.ll_theme_content)
    private LinearLayout ll_theme_content;
    @ViewInject(R.id.gv_tab)
    private MyGridView gv_tab;
    @ViewInject(R.id.ll_view)
    private LinearLayout ll_view;
    @ViewInject(R.id.ll_select_tab)
    private LinearLayout ll_select_tab;
    @ViewInject(R.id.iv_arrow)
    private ImageView iv_arrow;
    @ViewInject(R.id.et_start)
    private EditText et_start;//开始时间
    @ViewInject(R.id.et_end)
    private EditText et_end;//结束时间



    private Map<Integer,String> map;
    private boolean flag=true;

    private String startDate, endDate;

    private List<String> tabList=new ArrayList<>();
    @Override
    public void setLayout() {
        setContentView(R.layout.act_contracted);
    }

    @Override
    public void init(Bundle savedInstanceState) {
    setTopTitle("简约");
//        tv_select_location = (TextView) findViewById(R.id.tv_select);
//         ll_theme_content = (LinearLayout) findViewById(R.id.ll_theme_content);
//        ll_view = (LinearLayout) findViewById(R.id.ll_view);
//        gv_tab = (MyGridView) findViewById(R.id.gv_tab);
//        ll_select_tab = (LinearLayout) findViewById(R.id.ll_select_tab);
//        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
        //设置标签的，适配器
        GvTbAdapter adapter=new GvTbAdapter(this,this);
        gv_tab.setAdapter(adapter);
//        tv_select_location.setOnClickListener(this);
//        ll_theme_content.setOnClickListener(this);
//        ll_select_tab.setOnClickListener(this);

    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    @OnClick({R.id.tv_select,R.id.ll_theme_content,R.id.ll_select_tab,R.id.ll_start_time,R.id.ll_end_time})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_select://选择地点
                openActivity(LocationAct.class);
                break;
            case R.id.ll_theme_content://活动详情
                openActivity(EventdetailsAct.class);
                break;
            case R.id.ll_select_tab://选择标签
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
            case R.id.ll_start_time://开始时间
                TimeDialog startDialog = new TimeDialog(ContractedAct.this, TimeDialog.Type.YEAR_MONTH_DAY);
                startDialog.setRange(getCurrentYear() - 50, getCurrentYear());
                startDialog.builder();
                startDialog.show();
                startDialog.setOnTimeSelectListener(new TimeDialog.OnTimeSelectListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSelect(Date date) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String time = format.format(date);
                        startDate = time;
                        et_start.setText(time);
                    }
                });
                break;


            case R.id.ll_end_time://结束时间时间
                TimeDialog endDialog = new TimeDialog(ContractedAct.this, TimeDialog.Type.YEAR_MONTH_DAY);
                endDialog.setRange(getCurrentYear() - 50, getCurrentYear());
                endDialog.builder();
                endDialog.show();
                endDialog.setOnTimeSelectListener(new TimeDialog.OnTimeSelectListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSelect(Date date) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String time = format.format(date);
                        startDate = time;
                        et_end.setText(time);
                    }
                });

                break;



        }
    }


    /**
     * 获取年
     *
     * @return
     */
    private int getCurrentYear() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currDate = sf.format(new Date());
        return Integer.valueOf(currDate.substring(0, 4));
    }

}
