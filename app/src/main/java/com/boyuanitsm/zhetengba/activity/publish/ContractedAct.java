package com.boyuanitsm.zhetengba.activity.publish;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.boyuanitsm.zhetengba.Constant;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.MainAct;
import com.boyuanitsm.zhetengba.activity.SerchcityAct;
import com.boyuanitsm.zhetengba.activity.circle.EventdetailsAct;
import com.boyuanitsm.zhetengba.activity.mine.AssignScanAct;
import com.boyuanitsm.zhetengba.activity.mine.TimeHistoryAct;
import com.boyuanitsm.zhetengba.adapter.GvTbAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ActivityLabel;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.bean.SuggestionInfoBean;
import com.boyuanitsm.zhetengba.fragment.TimeFrg;
import com.boyuanitsm.zhetengba.fragment.calendarFrg.SimpleFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ACache;
import com.boyuanitsm.zhetengba.utils.GsonUtils;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CanotEmojEditText;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.widget.time.TimeDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简约界面
 * Created by bitch-1 on 2016/5/3.
 */
public class ContractedAct extends BaseActivity implements BDLocationListener {
    @ViewInject(R.id.tv_select)
    private TextView tv_select_location;
    @ViewInject(R.id.et_theme)
    private CanotEmojEditText et_theme;//主题名称
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
    @ViewInject(R.id.et_pp_num)//邀约人数
    private EditText et_pp_num;
    @ViewInject(R.id.ll_hu_no_can)//制定谁不能看
    private LinearLayout ll_hu_no_can;
    @ViewInject(R.id.ll_hu_can)//指定谁能看
    private LinearLayout ll_hu_can;
    @ViewInject(R.id.ll_theme)//活动详情
    private LinearLayout ll_theme;
    @ViewInject(R.id.ll_tab)//选择标签
    private LinearLayout ll_tab;
    @ViewInject(R.id.tv_select)//地点
    private CanotEmojEditText tv_select;
    @ViewInject(R.id.iv_friend)//按钮
    private ImageView iv_friend;
    @ViewInject(R.id.bt_plane)
    private Button bt_plan;
    @ViewInject(R.id.tv_hu_no_can)
    private TextView tv_hu_no_can;
    @ViewInject(R.id.tv_hu_can)
    private TextView tv_hu_can;
    @ViewInject(R.id.ll_hide_key)
    private LinearLayout ll_hide_key;
    @ViewInject(R.id.ll_zhiding)//指定谁看，不让谁看那个布局
    private LinearLayout ll_zhiding;
    private Map<Integer, String> map;
    private boolean flag = true;
    private int MIN_MARK = 2;
    private int MAX_MARK = 120;
    private Map<String, String> newMap = new HashMap<>();
    private List<ActivityLabel> list;
    private GvTbAdapter adapter;
    private int select = 0;//好友可见；0全部可见
    private int clickTemp = 0;//1是谁能看，2是谁不能看
    private SimpleInfo simpleInfo = new SimpleInfo();
    private String backTheme;
    private String hucanUserIds;
    private String hu_no_canUserIds;
    private String strUserIds;//用于存储指定谁可见用户ids；
    private String strUserNoIds;//用户存错谁不能见；
    private ProgressDialog pd;//缓冲弹出框
    private Date startDate, endDate;
    private LocationClient locationClient;
    private ACache aCache;
    private Gson gson;
    private LocalBroadcastManager broadcastManager;
    private IntentFilter filter;
    private String cityCode;//城市编码
    private double addLat;//坐标经度
    private double addLng;//坐标维度

    @Override
    public void setLayout() {
        setContentView(R.layout.act_contracted);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("档期");
        map = new HashMap<>();
        pd = new ProgressDialog(ContractedAct.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("发布中...");
        aCache=ACache.get(ContractedAct.this);
        gson=new Gson();
        list = new ArrayList<ActivityLabel>();
        String labelStr=aCache.getAsString("activityLabel");
        if (!TextUtils.isEmpty(labelStr)){
            List<ActivityLabel> labellist= gson.fromJson(labelStr, new TypeToken<List<ActivityLabel>>() {
            }.getType());
            if (adapter==null){
                adapter=new GvTbAdapter(ContractedAct.this,labellist);
                gv_tab.setAdapter(adapter);
            }else {
                adapter.updata(labellist);
            }
            gv_tab.setSelector(new ColorDrawable(Color.TRANSPARENT));
            gv_tab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (list != null&&list.size()>0) {
                        ActivityLabel activityLabel = list.get(position);
                        simpleInfo.setLabelId(activityLabel.getId());
                        simpleInfo.setIcon(activityLabel.getIcon());
                        adapter.setSeclection(position);
                        adapter.notifyDataSetChanged();
                    }

                }
            });
        }
        position();
        getAcitivtyLabel();
        et_pp_num.addTextChangedListener(judgeEditNum());
        iv_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select == 1) {
                    ll_zhiding.setVisibility(View.GONE);
                    iv_friend.setBackgroundDrawable(getResources().getDrawable(R.drawable.switch_off));
                    select = 0;
                    clickTemp = 0;
                    changeHuCan();
                } else {
                    ll_zhiding.setVisibility(View.VISIBLE);
                    iv_friend.setBackgroundDrawable(getResources().getDrawable(R.drawable.switch_on));
                    select = 1;
                    clickTemp = 0;
                    changeHuCan();
                }
            }
        });

    }

    /**
     * 指定谁能看，谁不能看，
     */
    private void changeHuCan() {
        if (clickTemp == 0) {
            tv_hu_can.setTextColor(Color.parseColor("#333333"));
            tv_hu_no_can.setTextColor(Color.parseColor("#333333"));
        }else if (clickTemp==1){
           tv_hu_can.setTextColor(Color.parseColor("#999999"));
            tv_hu_no_can.setTextColor(Color.parseColor("#333333"));
        }else if (clickTemp==2){
            tv_hu_can.setTextColor(Color.parseColor("#333333"));
            tv_hu_no_can.setTextColor(Color.parseColor("#999999"));
        }
    }

    /**
     * 初始化simpleInfo
     */
    private void initData() {
        if (!TextUtils.isEmpty(et_theme.getText().toString())) {
            if (et_theme.getText().length()>=4){
                simpleInfo.setActivityTheme(et_theme.getText().toString());
            }else {
                MyToastUtils.showShortToast(getApplicationContext(),"档期主题至少输入4个字！");
                et_theme.requestFocus();
                return;
            }
        } else {
            MyToastUtils.showShortToast(ContractedAct.this, "您有档期信息未完善，请完善！");
            return;
        }
        if (!TextUtils.isEmpty(tv_select.getText().toString())) {
            simpleInfo.setActivitySite(tv_select.getText().toString());//位置
        }
        if (!TextUtils.isEmpty(et_pp_num.getText().toString())) {
            if (Integer.parseInt(et_pp_num.getText().toString()) < 2) {
                MyToastUtils.showShortToast(ContractedAct.this, "邀约人数不得少于2人");
                return;
            } else {
                simpleInfo.setInviteNumber(Integer.parseInt(et_pp_num.getText().toString()));
            }
        } else {
            MyToastUtils.showShortToast(ContractedAct.this, "您有档期信息未完善，请完善！");
            return;
        }

        if (TextUtils.isEmpty(simpleInfo.getLabelId())) {
            MyToastUtils.showShortToast(ContractedAct.this, "您有档期信息未完善，请完善！");
            return;
        }
        if (!TextUtils.isEmpty(et_start.getText()) && !TextUtils.isEmpty(et_end.getText())) {
            Date nowday = new Date();
            if (startDate.getTime() < nowday.getTime()) {
                MyToastUtils.showShortToast(ContractedAct.this, "开始时间不能小于当前时间！");
                return;
            }
            Long times = endDate.getTime() - startDate.getTime();
            Long day= (endDate.getTime() - startDate.getTime())/ (1000*60*60*24);
            if (times > 0) {
                simpleInfo.setStartTime(et_start.getText().toString());
                simpleInfo.setEndTime(et_end.getText().toString());
            } else {
                MyToastUtils.showShortToast(ContractedAct.this, "开始时间不得大于结束时间，请重新选择！");
                return;
            }
            if(day<15){
                simpleInfo.setStartTime(et_start.getText().toString());
                simpleInfo.setEndTime(et_end.getText().toString());
            }else {
                MyToastUtils.showShortToast(ContractedAct.this, "开始时间到结束时间应<15天，请重新选择！");
                return;
            }
        } else {
            MyToastUtils.showShortToast(ContractedAct.this, "您有档期信息未完善，请完善！");
            return;
        }
        simpleInfo.setActivityVisibility(select);//全部可见
        simpleInfo.setActivityParticulars(backTheme);
        if (!TextUtils.isEmpty(cityCode)){
            simpleInfo.setCityCode(cityCode);
        }
        if (!TextUtils.isEmpty(addLat+"")){
            simpleInfo.setAddLat(addLat);
        }
        if (!TextUtils.isEmpty(addLng+"")){
            simpleInfo.setAddLng(addLng);
        }
      if (select==1){
          if (clickTemp==2){
              simpleInfo.setNoticeUserIds(hucanUserIds);//指定谁可见
          }else if (clickTemp==1){
              simpleInfo.setInvisibleUserIds(hu_no_canUserIds);//指定谁不可见

          }
        }
        pd.show();
        addActivity(simpleInfo);
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    @OnClick({R.id.tv_select, R.id.ll_theme_content, R.id.ll_select_tab, R.id.ll_start_time, R.id.ll_end_time, R.id.ll_theme, R.id.ll_hu_can, R.id.ll_hu_no_can, R.id.ll_tab, R.id.ll_hide, R.id.bt_plane,R.id.ll_hide_key,R.id.rl_city})
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.rl_city://地址那栏
                openActivity(SerchcityAct.class);
                break;
            case R.id.ll_tab://选择标签
                ZtinfoUtils.hideSoftKeyboard(ContractedAct.this,ll_select_tab);
                selectTab();
                break;
            case R.id.ll_hide_key:
                ZtinfoUtils.hideSoftKeyboard(ContractedAct.this,ll_hide_key);
                break;
            case R.id.ll_select_tab://选择标签
                ZtinfoUtils.hideSoftKeyboard(ContractedAct.this,ll_select_tab);
                selectTab();
                break;
            case R.id.ll_start_time://开始时间
                TimeDialog startDialog = new TimeDialog(ContractedAct.this, TimeDialog.Type.ALL);
                startDialog.setRange(getCurrentYear(), getCurrentYear()+1);
                startDialog.builder();
                startDialog.show();
                startDialog.setOnTimeSelectListener(new TimeDialog.OnTimeSelectListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSelect(Date date) {
                        //比较当前时间与选择时间，当前时间大于选择时间直接赋值，否则不赋值
//                        Date nowday = new Date();
//                        if (date.getTime() < nowday.getTime()) {
//                            MyToastUtils.showShortToast(ContractedAct.this, "开始时间不能小于当前时间！");
//                            return;
//                        }
//                        MyLogUtils.info(date + "date是多少");
                        startDate = date;
                        String time = ZhetebaUtils.compareTime(ContractedAct.this, date.getTime());
                        MyLogUtils.info(time + "time是多少");
                        et_start.setText(time);
                    }
                });
                break;


            case R.id.ll_end_time://结束时间时间
                TimeDialog endDialog = new TimeDialog(ContractedAct.this, TimeDialog.Type.ALL);
                endDialog.setRange(getCurrentYear(), getCurrentYear()+1);
                endDialog.builder();
                endDialog.show();
                endDialog.setOnTimeSelectListener(new TimeDialog.OnTimeSelectListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSelect(Date date) {
//                        if (date.getTime() < startDate.getTime()) {
//                            MyToastUtils.showShortToast(ContractedAct.this, "结束时间不能小于开始时间！");
//                            return;
//                        }
                        endDate = date;
                        String time = ZhetebaUtils.compareTime(ContractedAct.this, date.getTime());
                        et_end.setText(time);

                    }
                });

                break;
            case R.id.ll_hu_can:
                if (select == 1) {
                    intent = new Intent();
                    bundle = new Bundle();
                    String str3 = "hu_can";
                    bundle.putString("can", str3);
                    bundle.putString("canFlag", "canFlag");
                    if (!TextUtils.isEmpty(strUserIds)) {
                        bundle.putString("canUserIds", strUserIds);
                    }
                    intent.putExtras(bundle);
                    intent.setClass(this, AssignScanAct.class);
                    startActivityForResult(intent, 1);
                    clickTemp = 2;
                    changeHuCan();
//                    ll_hu_no_can.setEnabled(true);
//                    tv_hu_no_can.setTextColor(Color.parseColor("#999999"));
                }
                break;
            case R.id.ll_hu_no_can:
                if (select == 1) {
                    intent = new Intent();
                    bundle = new Bundle();
                    String str4 = "hu_no_can";
                    bundle.putString("can", str4);
                    bundle.putString("canFlag", "noCanFlag");
                    if (!TextUtils.isEmpty(strUserNoIds)) {
                        bundle.putString("noCanUserIds", strUserNoIds);
                    }
//                    bundle.putInt(AssignScanAct.CANTYPE,1);//不能看
                    intent.putExtras(bundle);
                    intent.setClass(this, AssignScanAct.class);
                    startActivityForResult(intent, 2);
                    clickTemp = 1;
                    changeHuCan();
//                    ll_hu_can.setEnabled(false);
//                    ll_hu_no_can.setEnabled(true);
//                    tv_hu_can.setTextColor(Color.parseColor("#999999"));
                }

                break;
            case R.id.ll_theme:
                intent = new Intent();
                if (backTheme != null) {
                    bundle = new Bundle();
                    bundle.putString("backTheme", backTheme);
                    intent.putExtras(bundle);
                }
                intent.setClass(this, EventdetailsAct.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.ll_hide://点击输入框以外地方，软键盘消失
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(ContractedAct.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_theme.getWindowToken(), 0);
                break;
            case R.id.bt_plane:
                initData();
        }
    }

    /**
     * 活动详情
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle bundle;
            switch (requestCode) {
                case 0:
                    bundle = data.getBundleExtra("bundle2");
                    backTheme = bundle.getString("detailsTheme");
                    break;
                case 1://谁能看
                    bundle = data.getBundleExtra("bundle3");
                    hucanUserIds = bundle.getString("bundleIds");
                    strUserIds = hucanUserIds;
                    MyLogUtils.info(strUserIds + "最终谁能看id");
                    break;
                case 2://谁不能看
                    bundle = data.getBundleExtra("bundle3");
                    hu_no_canUserIds = bundle.getString("bundleIds");
                    strUserNoIds = hu_no_canUserIds;
                    break;
            }

        }

    }


    /***
     * 发布活动
     *
     * @param simpleInfo
     */
    private void addActivity(SimpleInfo simpleInfo) {
        RequestManager.getScheduleManager().addActivity(simpleInfo, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                pd.dismiss();
                MyToastUtils.showShortToast(getApplication(),errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                pd.dismiss();
                response.getData();
                if(response.getData().equals("2")){//表示第一次发布
                    broadcastManager=  LocalBroadcastManager.getInstance(ContractedAct.this);
                    Intent intent=new Intent(ContractedAct.this,MainAct.class);
                    //设置Action
                    intent.setAction(Constant.ACTION_CONTACT_CHANAGED);
                    //携带数据
                    intent.putExtra("flag", response.getData().toString());
                    //发送广播
                    broadcastManager.sendBroadcast(intent);

//                    TipsDrawDialog tipsDrawDialog=new TipsDrawDialog(ContractedAct.this).builder();
//                    tipsDrawDialog.show();
//                    tipsDrawDialog.setCanceledOnTouchOutside(true);

                }
                sendBroadcast(new Intent(SimpleFrg.DATA_CHANGE_KEY));
                sendBroadcast(new Intent(TimeHistoryAct.USER_INFO));
                sendBroadcast(new Intent(TimeFrg.LISTORY_DATA));
                MyToastUtils.showShortToast(ContractedAct.this, "发布档期成功");
                finish();

            }
        });
    }

    /***
     * 获取活动标签
     */
    private void getAcitivtyLabel() {
        RequestManager.getScheduleManager().getAllActivityLabel(new ResultCallback<ResultBean<List<ActivityLabel>>>() {
            @Override
            public void onError(int status, String errorMsg) {
               String strList= aCache.getAsString("activityLabel");
                if (!TextUtils.isEmpty(strList)){
                 final List<ActivityLabel> labellist= gson.fromJson(strList, new TypeToken<List<ActivityLabel>>() {
                    }.getType());
                    if (adapter==null){
                        adapter=new GvTbAdapter(ContractedAct.this,labellist);
                        gv_tab.setAdapter(adapter);
                    }else {
                        adapter.updata(labellist);
                    }
                    gv_tab.setSelector(new ColorDrawable(Color.TRANSPARENT));
                    gv_tab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ActivityLabel activityLabel = labellist.get(position);
                            simpleInfo.setLabelId(activityLabel.getId());
                            simpleInfo.setIcon(activityLabel.getIcon());
                            adapter.setSeclection(position);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onResponse(ResultBean<List<ActivityLabel>> response) {

                list = response.getData();
                if (list!=null&&list.size()>0){
                    aCache.put("activityLabel", GsonUtils.bean2Json(list));
                }
                if (adapter==null){
                    adapter = new GvTbAdapter(ContractedAct.this, list);
                    gv_tab.setAdapter(adapter);
                }else {
                    adapter.updata(list);
                }
//                //默认选中第一个；
//                adapter.setSeclection(0);
//                adapter.notifyDataSetChanged();
                //设置标签的，适配器
                gv_tab.setSelector(new ColorDrawable(Color.TRANSPARENT));
                gv_tab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ActivityLabel activityLabel = list.get(position);
                        simpleInfo.setLabelId(activityLabel.getId());
                        simpleInfo.setIcon(activityLabel.getIcon());
                        adapter.setSeclection(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /***
     * 选择标签
     */

    private void selectTab() {
        if (flag) {
            ll_view.setVisibility(View.VISIBLE);
            gv_tab.setClickable(true);
            iv_arrow.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.arrow_down2));
            flag = false;
        } else {
            ll_view.setVisibility(View.INVISIBLE);
            ll_view.setVisibility(View.GONE);
            iv_arrow.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.arrow_right));
            flag = true;
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

    /***
     * 判断输入num的限制字数
     *
     * @return
     */
    @NonNull
    private TextWatcher judgeEditNum() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 0) {
                    if (MIN_MARK != 1 && MAX_MARK != -1) {
                        int num = Integer.parseInt(s.toString());
                        if (num > MAX_MARK) {
                            s = String.valueOf(MAX_MARK);
                            et_pp_num.setText(s);
                        } else if (num < MIN_MARK)
                            s = String.valueOf(MIN_MARK);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.equals("")) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        int markVal = 0;
                        try {
                            markVal = Integer.parseInt(s.toString());
                        } catch (NumberFormatException e) {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK) {
                            MyToastUtils.showShortToast(ContractedAct.this, "最大不能超过120人");
                            et_pp_num.setText(String.valueOf(MAX_MARK));
                        }
                        return;
                    }
                }
            }
        };
    }

    private void position() {
        // 实例化定位服务，LocationClient类必须在主线程中声明
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(this);// 注册定位监听接口
        /**
         * LocationClientOption 该类用来设置定位SDK的定位方式。
         */
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPRS
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("gcj02");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先
        option.setScanSpan(0); // 设置发起定位请求的间隔时间为5000ms
        option.disableCache(true);// 禁止启用缓存定位
        locationClient.setLocOption(option); // 设置定位参数
        locationClient.start();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        LogUtils.i("定位回掉。。。。。。。。。。。。。。。");
//        String cityCode = bdLocation.getCityCode();
//        MyLogUtils.info("城市编码是===="+cityCode);
        if (bdLocation != null) {
            if (!TextUtils.isEmpty(bdLocation.getProvince()) && !TextUtils.isEmpty(bdLocation.getDistrict()) && !TextUtils.isEmpty(bdLocation.getStreet())) {
                if (ZhetebaUtils.isCity(bdLocation.getProvince())) {
                    tv_select.setText(bdLocation.getCity() + bdLocation.getDistrict() + bdLocation.getStreet());
                } else {
                    tv_select.setText(bdLocation.getProvince() + bdLocation.getCity() + bdLocation.getDistrict() + bdLocation.getStreet());
                }
            } else {
                tv_select.setHint("无法获取位置信息，请手动输入！");
            }
            cityCode = bdLocation.getCityCode();
            addLat = bdLocation.getLatitude();
            addLng= bdLocation.getAltitude();
        } else {
            tv_select.setHint("无法获取位置信息，请手动输入！");
        }
        locationClient.stop();
    }
    public static final String UPDATA_ET = "serchcityact_send";//接收发档期收索过来的更改et
    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SuggestionInfoBean infoBean=intent.getParcelableExtra("suggestionInfo");
            cityCode = intent.getStringExtra("cityCode");
            String key=infoBean.getKey();
            String city=infoBean.getCity();
            String district=infoBean.getDistrict();
            addLat=infoBean.getPt().latitude;
            addLng=infoBean.getPt().longitude;
            tv_select.setText(city+district+key);


        }
    };

    @Override
    protected void onStart() {
        filter=new IntentFilter();
        filter.addAction(UPDATA_ET);
        registerReceiver(broadcastReceiver,filter);
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
