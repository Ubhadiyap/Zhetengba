package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ChanAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 频道里
 * viewpager适配的fragment
 * Created by xiaoke on 2016/5/3.
 */
public class ChaChildFrg extends BaseFragment {
    private List<String> list = new ArrayList<String>();
    private int flag;
    private List<List<ImageInfo>> datalist=new ArrayList<>();
    private String[][] images=new String[][]{{
            ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL2,"1624","914"}
            ,{ConstantValue.IMAGEURL3,"1624","914"}
            ,{ConstantValue.IMAGEURL4,"1624","914"}
            ,{ConstantValue.IMAGEURL5,"250","250"}
            ,{ConstantValue.IMAGEURL2,"250","250"}
            ,{ConstantValue.IMAGEURL3,"250","250"}
            ,{ConstantValue.IMAGEURL4,"250","250"}
            ,{ConstantValue.IMAGEURL5,"1280","800"}
    };

    private PullToRefreshListView lv_ch01;
    private int page=1;
    private int rows=10;
    private ChanAdapter adapter;
    private String lableId;
    private List<ChannelTalkEntity> channelTalkEntityList;

    @Override
    public View initView(LayoutInflater inflater) {
         view= inflater.inflate(R.layout.frag_chanel_child01,null);

        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView(view);
    }

    private void initView(View view) {
        lv_ch01 = (PullToRefreshListView) view.findViewById(R.id.lv_ch01);
        //传入参数，标签对应集合
        LayoutHelperUtil.freshInit(lv_ch01);
//        initDate();
        datalist=new ArrayList<>();
        lableId="c32595fc215c11e6ba57eca86ba4ba05";
        getChannelTalks(lableId,page,rows);
//        adapter=new ChanAdapter(mActivity,datalist);
//        lv_ch01.getRefreshableView().setAdapter(adapter);
        lv_ch01.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lv_ch01.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page=1;
                getChannelTalks(lableId,page,rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getChannelTalks(lableId,page,rows);
            }
        });

    }
//    public void setData(List<ChanelInfo> info){
//
//        adapter.notifyDateCshange();
//    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            list = bundle.getStringArrayList("content");
            flag = bundle.getInt("flag");
        }
    }
    public static ChaChildFrg newInstance(List<String> contentList,int flag){
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("content", (ArrayList<String>) contentList);
        bundle.putInt("flag", flag);
        ChaChildFrg testFm = new ChaChildFrg();
        testFm.setArguments(bundle);
        return testFm;

    }
    private void initDate() {
        datalist=new ArrayList<>();
        //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        ArrayList<ImageInfo> singleList=new ArrayList<>();
        singleList.add(new ImageInfo(images[8][0],Integer.parseInt(images[8][1]),Integer.parseInt(images[8][2])));
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

    private List<ChannelTalkEntity> datas=new ArrayList<>();
    /**
     * 获取频道说说列表
     * @param lableId
     * @param page
     * @param rows
     */
    private void getChannelTalks(String lableId, final int page,int rows){
        RequestManager.getTalkManager().getChannelTalks(lableId, page, rows, new ResultCallback<ResultBean<DataBean<ChannelTalkEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_ch01.onPullUpRefreshComplete();
                lv_ch01.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<DataBean<ChannelTalkEntity>> response) {
                lv_ch01.onPullUpRefreshComplete();
                lv_ch01.onPullDownRefreshComplete();
                channelTalkEntityList=response.getData().getRows();
                if(page==1){
                    datas.clear();
                }
                datas.addAll(channelTalkEntityList);
                for (int j=0;j<datas.size();j++) {
                    List<ImageInfo> itemList=new ArrayList<>();
                    //将图片地址转化成数组
                    if(!TextUtils.isEmpty(datas.get(j).getChannelImage())) {
                        String[] urlList = ZtinfoUtils.convertStrToArray(datas.get(j).getChannelImage());
                        for (int i = 0; i < urlList.length; i++) {
                            itemList.add(new ImageInfo(Uitls.imageFullUrl(urlList[i]), 1624, 914));
                        }
                    }
                    datalist.add(itemList);
                }
                if (datas != null && datas.size() > 0) {
                    if(adapter==null) {
                        adapter=new ChanAdapter(mActivity,datalist,datas);
                        lv_ch01.getRefreshableView().setAdapter(adapter);
                    }else {
                        adapter.notifyChange(datalist,datas);
                    }
                } else {
                    lv_ch01.setHasMoreData(false);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (receiverTalk==null){
            receiverTalk=new MyBroadCastReceiverTalk();
            getActivity().registerReceiver(receiverTalk, new IntentFilter(CHANNELTALKS));
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
    public static final String CHANNELTALKS="chanleTalk_update";
    private class MyBroadCastReceiverTalk extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            page=1;
            getChannelTalks(lableId,page, rows);
        }
    }
}
