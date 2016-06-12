package com.boyuanitsm.zhetengba.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.TimeAxisListAdp;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.HistoryMsgBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 时间轴Fragment
 * Created by wangbin on 16/6/12.
 */
public class TimeFrg extends BaseFragment {
    @ViewInject(R.id.lv_mine_list)
    private ListView lv_mine_list;
    private TimeAxisListAdp adapter;

    public static final String INPUT_TIME = "input_time";
    private String inputTime;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_mine_lv, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        inputTime = getArguments().getString(INPUT_TIME);
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
                if (adapter == null) {
                    adapter = new TimeAxisListAdp(getContext(), datas);
                    lv_mine_list.setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }

            }
        });
    }
}
