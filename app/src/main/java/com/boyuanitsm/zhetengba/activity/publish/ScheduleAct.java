package com.boyuanitsm.zhetengba.activity.publish;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.AssignScanAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.ScheduleInfo;
import com.boyuanitsm.zhetengba.fragment.MineFrg;
import com.boyuanitsm.zhetengba.fragment.TimeFrg;
import com.boyuanitsm.zhetengba.fragment.calendarFrg.CalFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.CommonView;
import com.boyuanitsm.zhetengba.widget.time.TimeDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 档期界面
 * Created by bitch-1 on 2016/4/29.
 */
public class ScheduleAct extends BaseActivity {
    @ViewInject(R.id.cet_start)
    private EditText cet_start;
    @ViewInject(R.id.cet_end)
    private EditText cet_end;
    @ViewInject(R.id.tv_plane)
    private TextView tv_plane;
    private ImageView iv_button;
    private int select=1;
    private TextView tv_xlws, tv_bwln, tv_wlzj, tv_xdys;//闲来无事，百无聊赖，无聊至极，闲的要死
    private CommonView ll_hu_can, ll_hu_no_can;
    private List<LabelBannerInfo> listLabel = new ArrayList<>();//档期标签集合；
    private ScheduleInfo scheduleInfo;
    private Date startDate,endDate;
    private String hucanUserIds,hu_no_canUserIds;

    private ProgressDialog pd;//缓冲弹出框


    @Override
    public void setLayout() {
        setContentView(R.layout.act_schedule);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        scheduleInfo=new ScheduleInfo();
        setTopTitle("有空");
        pd=new ProgressDialog(ScheduleAct.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("发布中....");
        getInterestScheduleLabel(1 + "");
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
        iv_button = (ImageView) findViewById(R.id.iv_button);
        scheduleInfo.setScheduleVisibility(1);
        ll_hu_can.setEnabled(false);
        ll_hu_no_can.setEnabled(false);
        iv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select == 1) {
                    iv_button.setBackgroundDrawable(getResources().getDrawable(R.drawable.switch_on));
                    ll_hu_can.setEnabled(true);
                    ll_hu_no_can.setEnabled(true);
                    select = 2;
                    scheduleInfo.setScheduleVisibility(2);
                } else {
                    ll_hu_can.setEnabled(false);
                    ll_hu_no_can.setEnabled(false);
                    iv_button.setBackgroundDrawable(getResources().getDrawable(R.drawable.switch_off));
                    select = 1;
                    scheduleInfo.setScheduleVisibility(1);
                }
            }
        });
    }

    @OnClick({R.id.tv_xlwu, R.id.tv_bwln, R.id.tv_wuzj, R.id.tv_xdys, R.id.view_start, R.id.view_end, R.id.ll_hu_can, R.id.ll_hu_no_can,R.id.tv_plane})
    public void onClick(View v) {
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        switch (v.getId()) {
            case R.id.tv_xlwu:
                setAllTabNor();
                setcheckitem(0);
                scheduleInfo.setLabelId(listLabel.get(0).getId());
                break;

            case R.id.tv_bwln:
                setAllTabNor();
                setcheckitem(1);
                scheduleInfo.setLabelId(listLabel.get(1).getId());
                break;

            case R.id.tv_wuzj:
                setAllTabNor();
                setcheckitem(2);
                scheduleInfo.setLabelId(listLabel.get(2).getId());
                break;

            case R.id.tv_xdys:
                setAllTabNor();
                setcheckitem(3);
                scheduleInfo.setLabelId(listLabel.get(3).getId());
                break;
            case R.id.ll_hu_can:
                if (select==2){
                    intent = new Intent();
                    bundle=new Bundle();
                    String str3="cal_hu_can";
                    bundle.putString("can",str3);
                    bundle.putString("canFlag","CalcanFlag");
                    if (!TextUtils.isEmpty(hucanUserIds)){
                        bundle.putString("canUserIds",hucanUserIds);
                    }
                    bundle.putInt(AssignScanAct.CANTYPE,0);//能看
                    intent.putExtras(bundle);
                    intent.setClass(this, AssignScanAct.class);
                    startActivityForResult(intent, 3);
                }
                break;
            case R.id.ll_hu_no_can:
                if (select==2){
                    intent = new Intent();
                    bundle=new Bundle();
                    String str4="cal_hu_no_can";
                    bundle.putString("can",str4);
                    bundle.putString("canFlag","CalnocanFlag");
                    if (!TextUtils.isEmpty(hu_no_canUserIds)){
                        bundle.putString("canUserIds",hu_no_canUserIds);
                    }
                    bundle.putInt(AssignScanAct.CANTYPE,1);//不能看
                    intent.putExtras(bundle);
                    intent.setClass(this, AssignScanAct.class);
                    startActivityForResult(intent, 4);
                }
                break;

            case R.id.view_start://开始时间
                TimeDialog startDialog = new TimeDialog(ScheduleAct.this, TimeDialog.Type.ALL);
                startDialog.setRange(getCurrentYear(), getCurrentYear());
                startDialog.builder();
                startDialog.show();
                startDialog.setOnTimeSelectListener(new TimeDialog.OnTimeSelectListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSelect(Date date) {
                        Date nowday = new Date();
                        if (date.getTime() < nowday.getTime()) {
                            MyToastUtils.showShortToast(ScheduleAct.this, "开始时间不能小于当前时间！");
                            return;
                        }
                        startDate=date;
                        String time=ZhetebaUtils.compareTime(ScheduleAct.this,date.getTime());
                        cet_start.setText(time);
                    }
                });
                break;
            case R.id.view_end://结束时间
                final TimeDialog endDialog = new TimeDialog(ScheduleAct.this, TimeDialog.Type.ALL);
                endDialog.setRange(getCurrentYear(), getCurrentYear());
                endDialog.builder();
                endDialog.show();
                endDialog.setOnTimeSelectListener(new TimeDialog.OnTimeSelectListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSelect(Date date) {
//                        if (date.getTime() < startDate.getTime()) {
//                            MyToastUtils.showShortToast(ScheduleAct.this, "结束时间不能小于开始时间！");
//                            return;
//                        }
                        endDate=date;
                        String time= ZhetebaUtils.compareTime(ScheduleAct.this, date.getTime());
                        cet_end.setText(time);
                    }
                });
                break;
            case R.id.tv_plane:
                initDate(scheduleInfo);
                break;

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            Bundle bundle;
            switch (requestCode){
                case 3://谁能看
                    bundle=data.getBundleExtra("bundle3");
                    hucanUserIds=bundle.getString("bundleIds");
                    break;
                case 4://谁不能看
                    bundle=data.getBundleExtra("bundle3");
                    hu_no_canUserIds=bundle.getString("bundleIds");
                    break;
            }
        }
    }

    //初始化对象数据
    private void initDate(ScheduleInfo scheduleInfo) {
        if (!TextUtils.isEmpty(cet_end.getText().toString())&&!TextUtils.isEmpty(cet_start.getText().toString())){
            Date nowday = new Date();
            if (startDate.getTime() < nowday.getTime()) {
                MyToastUtils.showShortToast(ScheduleAct.this, "开始时间不能小于当前时间！");
                return;
            }
                Long times=endDate.getTime()-startDate.getTime();
                if (times>0){
                    scheduleInfo.setStartTime(cet_start.getText().toString());
                    scheduleInfo.setEndTime(cet_end.getText().toString());
                }else {
                    MyToastUtils.showShortToast(ScheduleAct.this,"开始时间不得大于结束时间，请重新选择！");
                    return;
                }
        }else {
        MyToastUtils.showShortToast(ScheduleAct.this, "您有活动信息未完善，请完善！");
        return;
    }
//        scheduleInfo.setScheduleVisibility(select);
        scheduleInfo.setNoticeUserIds(hucanUserIds);
        scheduleInfo.setInvisibleUserIds(hu_no_canUserIds);
        pd.show();
        addSchedule(scheduleInfo);
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

    /**
     * 获取档期标签
     * @param dicType
     */
    private void getInterestScheduleLabel(String dicType) {
        RequestManager.getScheduleManager().getIntrestLabelList(dicType, new ResultCallback<ResultBean<List<LabelBannerInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<List<LabelBannerInfo>> response) {
                listLabel=response.getData();
                if(listLabel!=null){
                    tv_xlws.setText(listLabel.get(0).getDictName());
                    tv_bwln.setText(listLabel.get(1).getDictName());
                    tv_wlzj.setText(listLabel.get(2).getDictName());
                    tv_xdys.setText(listLabel.get(3).getDictName());
                }
                scheduleInfo.setLabelId(listLabel.get(0).getId());
            }
        });
    }

    private void addSchedule(ScheduleInfo scheduleInfo){
        RequestManager.getScheduleManager().addSchedule(scheduleInfo, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
            }

            @Override
            public void onResponse(ResultBean<String> response) {

                pd.dismiss();
                MyToastUtils.showShortToast(ScheduleAct.this, "发布档期成功");
                sendBroadcast(new Intent(CalFrg.CAL_DATA_CHANGE_KEY));
                sendBroadcast(new Intent(MineFrg.USER_INFO));
                sendBroadcast(new Intent(TimeFrg.LISTORY_DATA));
                finish();
            }
        });
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
