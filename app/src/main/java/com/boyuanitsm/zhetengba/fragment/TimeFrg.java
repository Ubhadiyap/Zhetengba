package com.boyuanitsm.zhetengba.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.TimeAxisListAdp;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.HistoryMsgBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.view.LoadingView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 时间轴Fragment
 * Created by wangbin on 16/6/12.
 */
public class TimeFrg extends BaseFragment {
    @ViewInject(R.id.lv_mine_list)
    private ListView lv_mine_list;
    private TimeAxisListAdp adapter;
    @ViewInject(R.id.noList)
    private RelativeLayout noList;
    public static final String INPUT_TIME = "input_time";
    private String inputTime;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_mine_lv, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        inputTime = getArguments().getString(INPUT_TIME);
        lv_mine_list.setSelector(new ColorDrawable(Color.TRANSPARENT));
        findHistory(inputTime);
    }

    /**
     * 获取时间轴
     */
    private void findHistory(String yearmonth) {
        RequestManager.getScheduleManager().findHistoryMessageListByYM(yearmonth, new ResultCallback<ResultBean<List<HistoryMsgBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<List<HistoryMsgBean>> response) {
                List<HistoryMsgBean> datas = response.getData();
                if (datas!=null&&datas.size()>0){
                    noList.setVisibility(View.GONE);
                    if (adapter == null) {
                        adapter = new TimeAxisListAdp(getContext(), datas);
                        lv_mine_list.setAdapter(adapter);
                    } else {
                        adapter.notifyChange(datas);
                    }
                }else {
                    noList.setVisibility(View.VISIBLE);
                }


            }
        });
    }
    private MyReceiver myReceiver;
    public static final String LISTORY_DATA = "com.update.history";
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            inputTime = getArguments().getString(INPUT_TIME);
            findHistory(inputTime);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (myReceiver==null) {
            myReceiver = new MyReceiver();
            getActivity().registerReceiver(myReceiver, new IntentFilter(LISTORY_DATA));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myReceiver!=null){
            getActivity().unregisterReceiver(myReceiver);
            myReceiver=null;
        }
    }

}
