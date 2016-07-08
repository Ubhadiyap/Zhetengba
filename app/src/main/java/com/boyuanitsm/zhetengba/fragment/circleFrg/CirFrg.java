package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 子界面-圈子界面
 * Created by xiaoke on 2016/5/2.
 */
public class CirFrg extends Fragment {
    private List<List<ImageInfo>> datalist;
    private List<CircleEntity> circleEntityList;
    private PullToRefreshListView lv_cir;
    private CircleAdapter adapter;
    private int page=1;
    private int rows=10;
    LinearLayout llnoList;

    ImageView ivAnim;
    TextView noMsg;
    private AnimationDrawable animationDrawable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cir_frg, null);
        getAllCircleTalk(page, rows);
        lv_cir = (PullToRefreshListView) view.findViewById(R.id.lv_cir);
        llnoList= (LinearLayout) view.findViewById(R.id.noList);
        ivAnim= (ImageView) view.findViewById(R.id.ivAnim);
        noMsg= (TextView) view.findViewById(R.id.noMsg);
//        initData();
//        datalist=new ArrayList<>();
        LayoutHelperUtil.freshInit(lv_cir);

        lv_cir.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lv_cir.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page = 1;
                getAllCircleTalk(page, rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getAllCircleTalk(page, rows);
            }
        });
        return view;
    }

    private List<CircleEntity> datas=new ArrayList<>();
    /**
     * 获取所有圈子说说
     * @param page
     * @param rows
     */
    private void getAllCircleTalk(final int page,int rows){
        circleEntityList=new ArrayList<>();
        datalist=new ArrayList<>();
        RequestManager.getTalkManager().getAllCircleTalk(page, rows, new ResultCallback<ResultBean<DataBean<CircleEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_cir.onPullUpRefreshComplete();
                lv_cir.onPullDownRefreshComplete();
                llnoList.setVisibility(View.VISIBLE);
                ivAnim.setImageResource(R.drawable.loadfail_list);
                animationDrawable = (AnimationDrawable) ivAnim.getDrawable();
                animationDrawable.start();
                noMsg.setText("加载失败");
            }

            @Override
            public void onResponse(ResultBean<DataBean<CircleEntity>> response) {
                if (animationDrawable!=null){
                    animationDrawable.stop();
                    animationDrawable=null;
                }
                lv_cir.onPullUpRefreshComplete();
                lv_cir.onPullDownRefreshComplete();
                circleEntityList=response.getData().getRows();
                if (circleEntityList.size() == 0) {
                    if (page == 1) {
                        llnoList.setVisibility(View.VISIBLE);
                        ivAnim.setImageResource(R.mipmap.planeno);
                        noMsg.setText("暂无内容");
                    } else {
                        lv_cir.setHasMoreData(false);
                    }
                    return;
                }else {
                    llnoList.setVisibility(View.GONE);
                }
                if(page==1){
                    datas.clear();
                }
                datas.addAll(circleEntityList);
                for (int j=0;j<datas.size();j++) {
                    final List<ImageInfo> itemList=new ArrayList<>();
                    //将图片地址转化成数组
                    if(!TextUtils.isEmpty(datas.get(j).getTalkImage())) {
                        final String[] urlList = ZtinfoUtils.convertStrToArray(datas.get(j).getTalkImage());
                            for (int i = 0; i < urlList.length; i++) {
                                itemList.add(new ImageInfo(urlList[i], 1624, 914));
                        }

                    }
                    datalist.add(itemList);
                }
                if(adapter==null) {
                    adapter=new CircleAdapter(getContext(),datalist,datas);
                    lv_cir.getRefreshableView().setAdapter(adapter);
                }else {
                    adapter.notifyChange(datalist,datas);
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (receiverTalk==null){
            receiverTalk=new MyBroadCastReceiverTalk();
            getActivity().registerReceiver(receiverTalk, new IntentFilter(ALLTALKS));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiverTalk!=null){
            getActivity().unregisterReceiver(receiverTalk);
            receiverTalk=null;
        }
    }
    private MyBroadCastReceiverTalk receiverTalk;
    public static final String ALLTALKS="alltalk_update";
    private class MyBroadCastReceiverTalk extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            page=1;
            getAllCircleTalk(page, rows);
        }
    }
}
