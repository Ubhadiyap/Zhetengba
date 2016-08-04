package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoke on 2016/7/27.
 */
public class ChanelItemFrg extends BaseFragment {
    private PullToRefreshListView vp_chan;//viewpager
    private LinearLayout llnoList;
    private ImageView ivAnim;
    private TextView noMsg;
    private AnimationDrawable animationDrawable;
    private List<ChannelTalkEntity> channelTalkEntityList;
    private List<List<ImageInfo>> datalist;
    private List<ChannelTalkEntity> datas = new ArrayList<>();
    private int page=1;
    private int rows=10;
    private ChanAdapter adapter;
    public static final String TITLE_LIST = "title_list";
    private String labelId;
    private ProgressDialog progressDialog;
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.item_vp_chanel, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        labelId= getArguments().getString(TITLE_LIST);
        vp_chan = (PullToRefreshListView) view.findViewById(R.id.vp_chan);
        llnoList = (LinearLayout) view.findViewById(R.id.noList);
        ivAnim = (ImageView) view.findViewById(R.id.ivAnim);
        noMsg = (TextView) view.findViewById(R.id.noMsg);
//        progressDialog=new ProgressDialog(mActivity);
//        progressDialog.setMessage("数据加载中...");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        LayoutHelperUtil.freshInit(vp_chan);
        //快速滚动时停止加载图片
        inistal(labelId);
        getChannelTalks(labelId, page, rows);
    }

    /**
     * 快速滚动时停止加载图片
     * 刷新
     * @param labelId
     */
    private void inistal(final String labelId) {
        vp_chan.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    ImageLoader.getInstance().resume();
                } else {
                    ImageLoader.getInstance().pause();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        vp_chan.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                vp_chan.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page = 1;
                getChannelTalks(labelId, page, rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getChannelTalks(labelId, page, rows);
            }
        });
    }

    /**
     * 获取频道说说列表
     *
     * @param lableId
     * @param page
     * @param rows
     */
    private void getChannelTalks(String lableId, final int page, int rows) {
        channelTalkEntityList = new ArrayList<>();
        datalist = new ArrayList<>();
        RequestManager.getTalkManager().getChannelTalks(lableId, page, rows, new ResultCallback<ResultBean<DataBean<ChannelTalkEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                vp_chan.onPullUpRefreshComplete();
                vp_chan.onPullDownRefreshComplete();
                if (adapter != null) {
                    adapter.notifyChange(datalist, channelTalkEntityList);
                }
//                if (progressDialog.isShowing()){
//                    progressDialog.dismiss();
//                }
                llnoList.setVisibility(View.VISIBLE);
                ivAnim.setImageResource(R.drawable.loadfail_list);
                noMsg.setText("加载数据失败...");
                animationDrawable = (AnimationDrawable) ivAnim.getDrawable();
                animationDrawable.start();
            }

            @Override
            public void onResponse(ResultBean<DataBean<ChannelTalkEntity>> response) {
                vp_chan.onPullUpRefreshComplete();
                vp_chan.onPullDownRefreshComplete();
//                if (progressDialog.isShowing()){
//                    progressDialog.dismiss();
//                }
                if (animationDrawable != null) {
                    animationDrawable.stop();
                    animationDrawable = null;
                }
                channelTalkEntityList = response.getData().getRows();
                if (channelTalkEntityList.size() == 0) {
                    if (page == 1) {
                        llnoList.setVisibility(View.VISIBLE);
                        ivAnim.setImageResource(R.mipmap.planeno);
                        noMsg.setText("暂无内容");
                    } else {
                        vp_chan.setHasMoreData(false);
                    }
                } else {
                    llnoList.setVisibility(View.GONE);
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(channelTalkEntityList);
                for (int j = 0; j < datas.size(); j++) {
                    List<ImageInfo> itemList = new ArrayList<>();
                    //将图片地址转化成数组
                    if (!TextUtils.isEmpty(datas.get(j).getChannelImage())) {
                        String[] urlList = ZtinfoUtils.convertStrToArray(datas.get(j).getChannelImage());
                        for (int i = 0; i < urlList.length; i++) {
                            itemList.add(new ImageInfo(urlList[i], 120, 120));
                        }
                    }
                    datalist.add(itemList);
                }
                if (adapter == null) {
                    adapter = new ChanAdapter(mActivity, datalist, datas);
                    vp_chan.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datalist, datas);
                }
            }
        });
    }
    private MyReceiver myReceiver;
    public static final String TALK_LIST = "up_date_talks";
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            labelId = getArguments().getString(TITLE_LIST);
            getChannelTalks(labelId, page, rows);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (myReceiver==null) {
            myReceiver = new MyReceiver();
            getActivity().registerReceiver(myReceiver, new IntentFilter(TALK_LIST));
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
