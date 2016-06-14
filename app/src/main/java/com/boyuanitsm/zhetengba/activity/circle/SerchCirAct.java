package com.boyuanitsm.zhetengba.activity.circle;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleglAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.boyuanitsm.zhetengba.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索圈子界面
 * Created by xiaoke on 2016/5/20.
 */
public class SerchCirAct extends BaseActivity {
    @ViewInject(R.id.cetSearch)
    private ClearEditText circleName;
    private int page=1;
    private int rows=10;
    private CircleglAdapter adapter;
    private List<CircleEntity> list;
    @ViewInject(R.id.lv_cir_serch)
    private PullToRefreshListView plv;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_serch_cir);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子搜索");
        LayoutHelperUtil.freshInit(plv);
        plv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
        plv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SerchCirAct.this, CirxqAct.class);
                intent.putExtra("circleId", list.get(position).getId());
                startActivity(intent);
            }
        });
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                plv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page=1;
                getCircle(circleName.getText().toString().trim(),page,rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getCircle(circleName.getText().toString().trim(),page,rows);
            }
        });
        circleName.addTextChangedListener(watcher);
    }
    TextWatcher watcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            getCircle(s.toString(),page,rows);
        }
    };

    private List<CircleEntity> datas=new ArrayList<>();
    //搜索圈子
    private void getCircle(String circleName, final int page, int rows){
        if(!TextUtils.isEmpty(circleName)) {
            list=new ArrayList<>();
            RequestManager.getTalkManager().searchCircle(circleName, page, rows, new ResultCallback<ResultBean<DataBean<CircleEntity>>>() {
                @Override
                public void onError(int status, String errorMsg) {
                    plv.onPullUpRefreshComplete();
                    plv.onPullDownRefreshComplete();
                }

                @Override
                public void onResponse(ResultBean<DataBean<CircleEntity>> response) {
                    plv.onPullUpRefreshComplete();
                    plv.onPullDownRefreshComplete();
                    list = response.getData().getRows();
                    if (list.size()==0){
                        if (page==1){

                        }else {
                            plv.setHasMoreData(false);
                        }
                        return;
                    }
                    if (page==1){
                        datas.clear();
                    }
                    datas.addAll(list);
                    if (adapter==null) {
                        adapter = new CircleglAdapter(SerchCirAct.this, datas);
                        plv.getRefreshableView().setAdapter(adapter);
                    }else {
                        adapter.notifyChange(datas);
                    }
                }
            });
        }
    }
}
