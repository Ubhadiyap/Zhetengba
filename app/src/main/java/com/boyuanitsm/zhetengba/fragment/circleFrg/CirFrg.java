package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 子界面-圈子界面
 * Created by xiaoke on 2016/5/2.
 */
public class CirFrg extends Fragment {
    private int[] icons = {R.drawable.test_banner, R.drawable.test_chanel, R.drawable.test_chanel, R.drawable.test_chanel};
    private List<List<ImageInfo>> datalist=new ArrayList<>();
    private String[][] images=new String[][]{{
            ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL2,"1624","914"}
            ,{ConstantValue.IMAGEURL3,"1624","914"}
            ,{ConstantValue.IMAGEURL4,"250","250"}
            ,{ConstantValue.IMAGEURL5,"250","250"}
            ,{ConstantValue.IMAGEURL4,"250","250"}
            ,{ConstantValue.IMAGEURL3,"250","250"}
            ,{ConstantValue.IMAGEURL5,"1280","800"}
    };

    private List<CircleEntity> circleEntityList;

    private PullToRefreshListView lv_cir;
    private CircleAdapter adapter;
    private int page=1;
    private int rows=10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cir_frg, null);
        getAllCircleTalk(page, rows);
        lv_cir = (PullToRefreshListView) view.findViewById(R.id.lv_cir);
        initData();

        LayoutHelperUtil.freshInit(lv_cir);

        lv_cir.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lv_cir.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page=1;
                getAllCircleTalk(page,rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getAllCircleTalk(page,rows);
            }
        });
        return view;
    }


    private void initData() {
        datalist=new ArrayList<>();
        //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        ArrayList<ImageInfo> singleList=new ArrayList<>();
        singleList.add(new ImageInfo(images[8][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));
        datalist.add(singleList);
        //从一到9生成9条朋友圈内容，分别是1~9张图片
        for(int i=0;i<9;i++){
            ArrayList<ImageInfo> itemList=new ArrayList<>();
            for(int j=0;j<=i;j++){
                itemList.add(new ImageInfo(images[j][0],Integer.parseInt(images[j][1]),Integer.parseInt(images[j][2])));
            }
            datalist.add(itemList);
        }
    }

    /**
     * 获取所有圈子说说
     * @param page
     * @param rows
     */
    private void getAllCircleTalk(int page,int rows){
        RequestManager.getTalkManager().getAllCircleTalk(page, rows, new ResultCallback<ResultBean<DataBean<CircleEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_cir.onPullUpRefreshComplete();
                lv_cir.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<DataBean<CircleEntity>> response) {
                lv_cir.onPullUpRefreshComplete();
                lv_cir.onPullDownRefreshComplete();
                circleEntityList=response.getData().getRows();
                adapter=new CircleAdapter(getContext(),datalist,circleEntityList);
                lv_cir.getRefreshableView().setAdapter(adapter);
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
