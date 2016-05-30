package com.boyuanitsm.zhetengba.activity.publish;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mess.ContractsAct;
import com.boyuanitsm.zhetengba.activity.mine.AssignScanAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.view.CommonView;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 档期界面
 * Created by bitch-1 on 2016/4/29.
 */
public class ScheduleAct extends BaseActivity {
    private TextView tv_xlws, tv_bwln, tv_wlzj, tv_xdys;//闲来无事，百无聊赖，无聊至极，闲的要死
    private CommonView ll_hu_can, ll_hu_no_can;


    @Override
    public void setLayout() {
        setContentView(R.layout.act_schedule);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("档期");
        assignView();
        setcheckitem(0);
    }

    private void assignView() {
        tv_xlws = (TextView) findViewById(R.id.tv_xlwu);
        tv_bwln = (TextView) findViewById(R.id.tv_bwln);
        tv_wlzj = (TextView) findViewById(R.id.tv_wuzj);
        tv_xdys = (TextView) findViewById(R.id.tv_xdys);
        ll_hu_can = (CommonView) findViewById(R.id.ll_hu_can);
        ll_hu_no_can = (CommonView) findViewById(R.id.ll_hu_no_can);
    }

    @OnClick({R.id.tv_xlwu, R.id.tv_bwln, R.id.tv_wuzj, R.id.tv_xdys,R.id.ll_hu_can,R.id.ll_hu_no_can})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_xlwu:
                setAllTabNor();
                setcheckitem(0);

                break;

            case R.id.tv_bwln:
                setAllTabNor();
                setcheckitem(1);
                break;

            case R.id.tv_wuzj:
                setAllTabNor();
                setcheckitem(2);
                break;

            case R.id.tv_xdys:
                setAllTabNor();
                setcheckitem(3);
                break;
            case R.id.ll_hu_can:
                openActivity(AssignScanAct.class);
                break;
            case R.id.ll_hu_no_can:
                openActivity(AssignScanAct.class);
                break;


        }


    }

    /**
     * 四个状态全部切换成普通样式
     */
    private void setAllTabNor() {
        tv_xlws.setTextColor(Color.parseColor("#666666"));
        tv_xlws.setBackgroundResource(R.drawable.rdbt_xl_nocheck);
        tv_bwln.setTextColor(Color.parseColor("#666666"));
        tv_bwln.setBackgroundResource(R.drawable.rdbt_bw_nocheck);
        tv_wlzj.setTextColor(Color.parseColor("#666666"));
        tv_wlzj.setBackgroundResource(R.drawable.rdbt_wl_nocheck);
        tv_xdys.setTextColor(Color.parseColor("#666666"));
        tv_xdys.setBackgroundResource(R.drawable.rdbt_ys_nocheck);


    }


    /**
     * 当点击四个状态时候选中样式
     *
     * @param position
     */
    private void setcheckitem(int position) {
        switch (position) {
            case 0://闲来无聊被选中
                tv_xlws.setTextColor(Color.parseColor("#FFFFFF"));
                tv_xlws.setBackgroundResource(R.drawable.rdbt_xl_check);
                break;
            case 1://百无聊赖被选中
                tv_bwln.setTextColor(Color.parseColor("#FFFFFF"));
                tv_bwln.setBackgroundResource(R.drawable.rdbt_bw_check);
                break;

            case 2://无聊至极被选中
                tv_wlzj.setTextColor(Color.parseColor("#FFFFFF"));
                tv_wlzj.setBackgroundResource(R.drawable.rdbt_wl_check);
                break;

            case 3://闲的要死被选中
                tv_xdys.setTextColor(Color.parseColor("#FFFFFF"));
                tv_xdys.setBackgroundResource(R.drawable.rdbt_ys_check);
                break;


        }


    }


}
