package com.boyuanitsm.zhetengba.activity.circle;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.publish.MyPlaneAct;
import com.boyuanitsm.zhetengba.adapter.CircleMessAdatper;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleInfo;
import com.boyuanitsm.zhetengba.util.ZhetebaUtils;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子--圈子消息
 * Created by xiaoke on 2016/5/6.
 */
public class CirMessAct extends BaseActivity {
    @ViewInject(R.id.lv_cir_mess)//下拉刷新
    private PullToRefreshListView lv_cir_mess;
    private PopupWindow mPopupWindow;
    //    private int[] icons = {R.drawable.test_01};
//    private List<ImageInfo> datalist=new ArrayList<>();
    @ViewInject(R.id.ll_reply)
    private LinearLayout ll_reply;
    @ViewInject(R.id.et_reply)
    private EditText et_reply;
    @ViewInject(R.id.bt_reply)
    private Button bt_reply;
    private List<CircleInfo> list = new ArrayList<CircleInfo>();
    private CircleInfo info;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_circle_mess);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子消息");
        setRight("我的发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转我发布界面；
                //直接跳转发布界面；
                openActivity(MyPlaneAct.class);
            }
        });
        //初始化刷新列表
        LayoutHelperUtil.freshInit(lv_cir_mess);
        lv_cir_mess.getRefreshableView().setDivider(null);
        for (int i = 1; i <= 3; i++) {
            info = new CircleInfo();
            info.setType(i);
            info.setState(i);
            list.add(info);
        }
        CircleMessAdatper adapter = new CircleMessAdatper(CirMessAct.this, list);
        lv_cir_mess.getRefreshableView().setAdapter(adapter);
//        lv_cir_mess.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CircleInfo circleInfo = (CircleInfo) lv_cir_mess.getRefreshableView().getItemAtPosition(position);
//                if (circleInfo.getType() == 1 && circleInfo.getState() == 1) {
//                    ll_reply.setVisibility(View.VISIBLE);
//                    et_reply.setFocusable(true);
//                    et_reply.setFocusableInTouchMode(true);
//                    et_reply.requestFocus();
//                    et_reply.requestFocusFromTouch();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(CirMessAct.this.INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//
//                }
//            }
//        });
    }


}
