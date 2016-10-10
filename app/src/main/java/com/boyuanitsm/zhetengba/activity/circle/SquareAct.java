package com.boyuanitsm.zhetengba.activity.circle;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.LabelMangerAct;
import com.boyuanitsm.zhetengba.adapter.ChanAdapter;
import com.boyuanitsm.zhetengba.adapter.ChanelPageAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ACache;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.bounScrollView.BounceScrollView;
import com.boyuanitsm.zhetengba.view.bounScrollView.ViewPagerIndicator;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 吐槽界面
 * Created by xiaoke on 2016/8/11.
 */
public class SquareAct extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ivRight)
    private ImageView ivRight;
    @ViewInject(R.id.titleLayout)
    private ViewPagerIndicator titleLayout;//频道，头部标签布局
    @ViewInject(R.id.vp_chanel)
    private ViewPager viewPager;
    @ViewInject(R.id.hslv_chanel)
    private BounceScrollView hslv_chanel;
    @ViewInject(R.id.vp_chan)
    private PullToRefreshListView vp_chan;
    private int currentPos=0;//当前位置
    private ACache aCache;
    private List<UserInterestInfo> titleList;//标签集合
    private String labelStr="04a9c093215d11e6ba57eca86ba4ba05";//吐槽标签id，后期可能会变
//    private PullToRefreshListView vp_chan;//viewpager
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
    public static final String CURRENT_POS="currentPos";
    private String labelId;
    private ProgressDialog progressDialog;
    private int commentPosition=0;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_square);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("吐槽");
        llnoList = (LinearLayout) findViewById(R.id.noList);
        ivAnim = (ImageView) findViewById(R.id.ivAnim);
        noMsg = (TextView) findViewById(R.id.noMsg);
        LayoutHelperUtil.freshInit(vp_chan);
        aCache = ACache.get(SquareAct.this);
        getChannelTalks(labelStr, page, rows);
//        vp_chan.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
//                    ImageLoader.getInstance().resume();
//                } else {
//                    ImageLoader.getInstance().pause();
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
        vp_chan.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                vp_chan.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page = 1;
                getChannelTalks(labelStr, page, rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getChannelTalks(labelStr, page, rows);
            }
        });
//        getMyLabels(-1);
    }


    @OnClick({R.id.ll_add,R.id.ivRight})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRight:
                Intent intent = new Intent(SquareAct.this, CirclefbAct.class);
                intent.putExtra(CirclefbAct.TYPE, 0);
                intent.putExtra("labelId", labelStr);
//                intent.putExtra("flag", currentPos);
//                MyLogUtils.info("发布时的位置：====" + currentPos);
                startActivity(intent);
                break;
//            case R.id.bt_plan:
//                Intent intent = new Intent(SquareAct.this, CirclefbAct.class);
//                intent.putExtra(CirclefbAct.TYPE, 0);
//                intent.putExtra("labelId", titleList.get(currentPos).getInterestId());
//                intent.putExtra("flag", currentPos);
//                MyLogUtils.info("发布时的位置：====" + currentPos);
//                startActivity(intent);
//                break;
            case R.id.ll_add:
                openActivity(LabelMangerAct.class);
                break;
        }
    }
    /***
     * 填充数据
     */
    private void initDate(final List<UserInterestInfo> titleList) {
        titleLayout.setTabItemTitles(titleList);
//        if (pageAdapter==null){
          ChanelPageAdapter  pageAdapter = new ChanelPageAdapter(getSupportFragmentManager(), SquareAct.this, titleList, currentPos);
            viewPager.setAdapter(pageAdapter);
//        }else {
//            pageAdapter.updata(titleList);
//        }
        titleLayout.setViewPager(viewPager, hslv_chanel, currentPos);
        titleLayout.setOnPageChangeListener(new ViewPagerIndicator.PageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 获取我的兴趣标签
     *
     * @param limitNum
     */
    private void getMyLabels(int limitNum) {
        titleList = new ArrayList<>();
        RequestManager.getScheduleManager().selectMyLabels(null, limitNum, new ResultCallback<ResultBean<List<UserInterestInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                String str = aCache.getAsString("titleList");
                Gson gson = new Gson();
                titleList = gson.fromJson(str, new TypeToken<List<UserInterestInfo>>() {
                }.getType());
                initDate(titleList);
            }

            @Override
            public void onResponse(ResultBean<List<UserInterestInfo>> response) {
                titleList = response.getData();
                Gson gson = new Gson();
                aCache.put("titleList", gson.toJson(titleList));
                initDate(titleList);
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
    private void getChannelTalks(final String lableId, final int page, int rows) {
        channelTalkEntityList = new ArrayList<>();
        datalist = new ArrayList<>();
        RequestManager.getTalkManager().getChannelTalks(lableId, page, rows, new ResultCallback<ResultBean<DataBean<ChannelTalkEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                vp_chan.onPullUpRefreshComplete();
                vp_chan.onPullDownRefreshComplete();
                if (datas.size()>0){

                }else {
                    llnoList.setVisibility(View.VISIBLE);
                    ivAnim.setImageResource(R.mipmap.planeno);
                    noMsg.setText("加载失败");
                }
//                if (adapter != null) {
//                    adapter.notifyChange(datalist, channelTalkEntityList);
//                }
//                if (progressDialog.isShowing()){
//                    progressDialog.dismiss();
//                }
//                llnoList.setVisibility(View.VISIBLE);
//                ivAnim.setImageResource(R.mipmap.planeno);
//                noMsg.setText("加载失败");
//                animationDrawable = (AnimationDrawable) ivAnim.getDrawable();
//                animationDrawable.start();
            }

            @Override
            public void onResponse(ResultBean<DataBean<ChannelTalkEntity>> response) {
                vp_chan.onPullUpRefreshComplete();
                vp_chan.onPullDownRefreshComplete();
//                if (progressDialog.isShowing()){
//                    progressDialog.dismiss();
//                }
//                if (animationDrawable != null) {
//                    animationDrawable.stop();
//                    animationDrawable = null;
//                }
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
                    MyLogUtils.info("adapter==null===labelId是"+lableId);
                    adapter = new ChanAdapter(SquareAct.this, datalist, datas);
                    vp_chan.getRefreshableView().setAdapter(adapter);
                } else {
                    MyLogUtils.info("adapter!!!!==null====labelId是"+lableId);
                    adapter.notifyChange(datalist, datas);
                }
//                vp_chan.getRefreshableView().setSelection(commentPosition);
            }
        });
    }
    private MyReceiver myReceiver;
    public static final String TALK_LIST = "up_date_talks";
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            labelId = getArguments().getString(TITLE_LIST);
            Bundle bundle=  intent.getExtras();
            if (bundle!=null&&datas.size()>0){
                String tag=bundle.getString("tag");
                if (!TextUtils.isEmpty(tag)){
                    if (TextUtils.equals("comTag",tag)){
                        int cposition=bundle.getInt("ComtPosition");
                        int cNum=bundle.getInt("ComtNum");
                        datas.get(cposition).setCommentCounts(cNum);
                        ArrayList<ChannelTalkEntity> comtList = bundle.getParcelableArrayList("ComtList");
                        if (comtList!=null&&comtList.size()>0){
                            datas.get(cposition).setCommentsList(comtList);
                        }
                    }else if (TextUtils.equals("delTag",tag)){
                        int position= bundle.getInt("DelPosition");
                        MyLogUtils.info("datas内数据===="+datas.toString()+""+position);
                        datas.remove(position);
                        datalist.remove(position);
                    }
//                    ChannelTalkEntity channelTalkEntity=new ChannelTalkEntity();
//                    channelTalkEntity=bundle.getParcelable("channelTalkEntity");
//                    if (channelTalkEntity!=null){
//                        datas.add(0,channelTalkEntity);
//                    }
                    if (adapter == null) {
                        adapter = new ChanAdapter(SquareAct.this, datalist, datas);
                        vp_chan.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.notifyChange(datalist, datas);
                    }
                }
            } else {
                page=1;
                getChannelTalks(labelStr, page, rows);
            }

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (myReceiver == null) {
            myReceiver = new MyReceiver();
            registerReceiver(myReceiver, new IntentFilter(TALK_LIST));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
            myReceiver = null;
        }
    }

//    private MyBroadCastReceiverTalk receiverTalk;
//    public static final String MYLABELS = "labels_update";

//    private class MyBroadCastReceiverTalk extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (titleLayout != null)
//                titleLayout.removeAllViews();
//            if (titleList != null)
//                titleList.clear();
////            if (intent != null) {
////                Bundle bundle = intent.getExtras();
////                if (bundle != null) {
////                    currentPos = bundle.getInt("flag", currentPos);
////                }
////            }
//            getMyLabels(-1);
//        }
//    }
}
