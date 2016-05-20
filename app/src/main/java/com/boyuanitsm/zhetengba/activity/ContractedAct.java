package com.boyuanitsm.zhetengba.activity;

import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.EventdetailsAct;
import com.boyuanitsm.zhetengba.activity.mess.ContractsAct;
import com.boyuanitsm.zhetengba.adapter.GvTbAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.view.MyGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简约界面
 * Created by bitch-1 on 2016/5/3.
 */
public class ContractedAct extends BaseActivity {
    private TextView tv_select_location;
    private LinearLayout ll_theme_content;
    private MyGridView gv_tab;
    private LinearLayout ll_view;
    private LinearLayout ll_select_tab;
    private ImageView iv_arrow;
    private Map<Integer, String> map;
    private  EditText et_pp_num;
    private boolean flag = true;
    private int MIN_MARK=1;
    private int MAX_MARK=120;

    private List<String> tabList = new ArrayList<>();

    @Override
    public void setLayout() {
        setContentView(R.layout.act_contracted);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("简约");
        ll_theme_content = (LinearLayout) findViewById(R.id.ll_theme_content);
        ll_view = (LinearLayout) findViewById(R.id.ll_view);
        gv_tab = (MyGridView) findViewById(R.id.gv_tab);
        ll_select_tab = (LinearLayout) findViewById(R.id.ll_select_tab);
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
        LinearLayout ll_sel_tab = (LinearLayout) findViewById(R.id.ll_sel_tab);
        LinearLayout ll_act = (LinearLayout) findViewById(R.id.ll_act);
        LinearLayout ll_hu_can = (LinearLayout) findViewById(R.id.ll_hu_can);
        LinearLayout ll_hu_no_can = (LinearLayout) findViewById(R.id.ll_hu_no_can);
        et_pp_num = (EditText) findViewById(R.id.et_pp_num);
        et_pp_num.addTextChangedListener(judgeEditNum());
        //设置标签的，适配器
        GvTbAdapter adapter = new GvTbAdapter(this, this);
        gv_tab.setAdapter(adapter);
        ll_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(EventdetailsAct.class);
            }
        });
        ll_hu_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyToastUtils.showShortToast(ContractedAct.this, "指定谁看");
                openActivity(ContractsAct.class);
            }
        });
        ll_hu_no_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyToastUtils.showShortToast(ContractedAct.this, "谁不能看");
                openActivity(ContractsAct.class);
            }
        });
        ll_sel_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

    }

    /***
     * 判断输入num的限制字数
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
                if (start > 0)
                {
                    if (MIN_MARK != -1 && MAX_MARK != -1)
                    {
                        int num = Integer.parseInt(s.toString());
                        if (num > MAX_MARK)
                        {
                            s = String.valueOf(MAX_MARK);
                            et_pp_num.setText(s);
                        }
                        else if(num < MIN_MARK)
                            s = String.valueOf(MIN_MARK);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.equals(""))
                {
                    if (MIN_MARK != -1 && MAX_MARK != -1)
                    {
                        int markVal = 0;
                        try
                        {
                            markVal = Integer.parseInt(s.toString());
                        }
                        catch (NumberFormatException e)
                        {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK)
                        {
                            MyToastUtils.showShortToast(ContractedAct.this, "最大不能超过120人");
                            et_pp_num.setText(String.valueOf(MAX_MARK));
                        }
                        return;
                    }
                }
            }
        };
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.tv_select:
//                openActivity(LocationAct.class);
//                break;
//            case R.id.ll_theme_content:
//                openActivity(EventdetailsAct.class);
//                break;
//            case R.id.ll_select_tab:
//
//                break;
//        }
//    }
}
