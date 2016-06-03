package com.boyuanitsm.zhetengba.activity.circle;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleglAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.boyuanitsm.zhetengba.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;

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
        plv.setPullRefreshEnabled(true);//下拉刷新
        plv.setScrollLoadEnabled(true);//滑动加载
        plv.setPullLoadEnabled(false);//上拉刷新
        plv.setHasMoreData(true);//是否有更多数据
        plv.getRefreshableView().setDivider(null);//设置分隔线
        plv.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        plv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
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

    //搜索圈子
    private void getCircle(String circleName,int page,int rows){
        RequestManager.getTalkManager().searchCircle(circleName, page, rows, new ResultCallback<ResultBean<List<CircleEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                plv.onPullUpRefreshComplete();
                plv.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<List<CircleEntity>> response) {
                plv.onPullUpRefreshComplete();
                plv.onPullDownRefreshComplete();
                list=response.getData();
                adapter=new CircleglAdapter(SerchCirAct.this,list);
                plv.getRefreshableView().setAdapter(adapter);
            }
        });
    }
}
