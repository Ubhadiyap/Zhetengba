package com.boyuanitsm.zhetengba.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.EventdetailsAct;
import com.boyuanitsm.zhetengba.activity.mine.AssignScanAct;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.adapter.GvTbAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ActivityLabel;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.widget.ToggleButton;
import com.boyuanitsm.zhetengba.widget.time.TimeDialog;
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
public class ContractedAct extends BaseActivity {
    @ViewInject(R.id.tv_select)
    private TextView tv_select_location;
    @ViewInject(R.id.et_theme)
    private EditText et_theme;//主题名称
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
    private EditText tv_select;
    @ViewInject(R.id.iv_friend)//按钮
    private ImageView iv_friend;

    private Map<Integer, String> map;
    private boolean flag = true;
    private int MIN_MARK = 1;
    private int MAX_MARK = 120;
    private Map<String, String> newMap = new HashMap<>();
    private List<ActivityLabel> list;
    private GvTbAdapter adapter;
    private int select = 1;//好友可见；0全部可见
    private SimpleInfo simpleInfo = new SimpleInfo();
    private String backTheme;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_contracted);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("简约");
        map = new HashMap<>();
        list = new ArrayList<ActivityLabel>();
        getAcitivtyLabel();
        et_pp_num.addTextChangedListener(judgeEditNum());
        iv_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select == 1) {
                    iv_friend.setBackgroundDrawable(getResources().getDrawable(R.drawable.switch_off));
                    select = 0;
                    return;
                } else {
                    iv_friend.setBackgroundDrawable(getResources().getDrawable(R.drawable.switch_on));
                    select = 1;
                    return;
                }
            }
        });
    }

    /**
     * 初始化simpleInfo
     */
    private void initData() {

        if (et_theme.getText().toString() != null && et_start.getText().toString() != null && et_end.getText().toString() != null && et_pp_num.getText().toString() != null) {
            simpleInfo.setActivityTheme(et_theme.getText().toString());
            simpleInfo.setStartTime(et_start.getText().toString());
            simpleInfo.setActivitySite(tv_select.getText().toString());//位置
            simpleInfo.setInviteNumber(Integer.parseInt(et_pp_num.getText().toString()));
            simpleInfo.setEndTime(et_end.getText().toString());
            simpleInfo.setActivityVisibility(select);//全部可见
            simpleInfo.setActivityParticulars(backTheme);
        } else {
            MyToastUtils.showShortToast(ContractedAct.this, "您有未输入的内容");
        }

    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    @OnClick({R.id.tv_select, R.id.ll_theme_content, R.id.ll_select_tab, R.id.ll_start_time, R.id.ll_end_time, R.id.ll_theme, R.id.ll_hu_can, R.id.ll_hu_no_can, R.id.ll_tab, R.id.ll_hide, R.id.bt_plane})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tab://选择标签
                selectTab();
                break;
            case R.id.ll_select_tab://选择标签
                selectTab();
                break;
            case R.id.ll_start_time://开始时间
                TimeDialog startDialog = new TimeDialog(ContractedAct.this, TimeDialog.Type.ALL);
                startDialog.setRange(getCurrentYear() - 50, getCurrentYear());
                startDialog.builder();
                startDialog.show();
                startDialog.setOnTimeSelectListener(new TimeDialog.OnTimeSelectListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSelect(Date date) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        ;
                        String time = format.format(date);
                        et_start.setText(time);
                    }
                });
                break;


            case R.id.ll_end_time://结束时间时间
                TimeDialog endDialog = new TimeDialog(ContractedAct.this, TimeDialog.Type.ALL);
                endDialog.setRange(getCurrentYear() - 50, getCurrentYear());
                endDialog.builder();
                endDialog.show();
                endDialog.setOnTimeSelectListener(new TimeDialog.OnTimeSelectListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSelect(Date date) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String time = format.format(date);
                        et_end.setText(time);
                    }
                });

                break;
            case R.id.ll_hu_can:
                if (select == 1) {
                    openActivity(AssignScanAct.class);
                }
                break;
            case R.id.ll_hu_no_can:
                if (select == 1) {
                    openActivity(AssignScanAct.class);
                }

                break;
            case R.id.ll_theme:
                Intent intent = new Intent();
                intent.setClass(this, EventdetailsAct.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.ll_theme_content:
                openActivity(EventdetailsAct.class);
                break;
            case R.id.ll_hide://点击输入框以外地方，软键盘消失
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(ContractedAct.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_theme.getWindowToken(), 0);
                break;
            case R.id.bt_plane:
                initData();
                addActivity(simpleInfo);

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
        if (requestCode == 0) {
            Bundle bundle = data.getBundleExtra("bundle2");
            backTheme = bundle.getString("detailsTheme");

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
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                response.getData();
                Intent intentRecevier=new Intent();
                intentRecevier.setAction("simpleAllDateChange");
                sendBroadcast(intentRecevier);
                MyToastUtils.showShortToast(ContractedAct.this, "发布活动成功");
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

            }

            @Override
            public void onResponse(ResultBean<List<ActivityLabel>> response) {

                list = response.getData();
                adapter = new GvTbAdapter(ContractedAct.this, list);
//                //默认选中第一个；
//                adapter.setSeclection(0);
//                adapter.notifyDataSetChanged();
                //设置标签的，适配器
                gv_tab.setAdapter(adapter);
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
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
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
}
