package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.CirclefbAct;
import com.boyuanitsm.zhetengba.activity.mine.LabelMangerAct;
import com.boyuanitsm.zhetengba.adapter.ChanAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;
import com.boyuanitsm.zhetengba.db.LabelInterestDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道界面
 * Created by xiaoke on 2016/5/2.
 */
public class ChanelFrg extends BaseFragment implements View.OnClickListener {
    private int currentPos;//当前位置
    private int pos = 0;//返回的位置
    private int mTitleMargin;//头部标签之间空隙；
    private int page = 1;
    private int rows = 10;
    private View view;//当前view
    private PullToRefreshListView vp_chan;//viewpager
    private ChanAdapter adapter;
    private LinearLayout titleLayout;//频道，头部标签布局
    private ArrayList<TextView> textViewList;//承载标签的TextView集合
    private List<UserInterestInfo> titleList;//标签集合
    private ArrayList<Integer> moveToList;//设置textview宽高集合
    private List<ChannelTalkEntity> channelTalkEntityList;
    private List<List<ImageInfo>> datalist;
    private List<ChannelTalkEntity> datas = new ArrayList<>();;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.chanel_frg, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();        //初始化控件
        titleList = LabelInterestDao.getInterestLabel();        //设置间隙
        if (titleList == null) {
            getMyLabels(-1);
        } else {
            initDate(titleList);
        }
        MyLogUtils.info(titleList.toString());
    }

    private void initView() {
        titleLayout = (LinearLayout) view.findViewById(R.id.titleLayout);
        vp_chan = (PullToRefreshListView) view.findViewById(R.id.vp_chan);
    }

    /***
     * 填充数据
     */
    private void initDate(final List<UserInterestInfo> titleList) {
        ;
        textViewList = new ArrayList<>();
        moveToList = new ArrayList<>();
        getChannelTalks(titleList.get(currentPos).getInterestId(), page, rows);
        for (int i = 0; i < titleList.size(); i++) {
            addTitleLayout(titleList.get(i).getDictName(), i);
        }

        if (textViewList != null && textViewList.size() > 0) {
            textViewList.get(currentPos).setTextColor(Color.parseColor("#52C791"));//默认加载项，标签文字对应变色
        }
        LayoutHelperUtil.freshInit(vp_chan);
        vp_chan.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                vp_chan.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page = 1;
                getChannelTalks(titleList.get(currentPos).getInterestId(), page, rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getChannelTalks(titleList.get(currentPos).getInterestId(), page, rows);
            }
        });
        currentPos = pos;
    }

    /***
     * 填充titleLayout
     *
     * @param title
     * @param position
     */

    private void addTitleLayout(String title, int position) {
        final TextView textView = (TextView) mActivity.getLayoutInflater().inflate(R.layout.chanel_child_title, null);
        textView.setText(title);
        textView.setTextSize(14);
        textView.setTextColor(Color.parseColor("#999999"));
        textView.setTag(position);        //设置position Tag
        textView.setOnClickListener(new posOnClickListener());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mTitleMargin = ZtinfoUtils.dip2px(mActivity, 10);        //设置左右间隙
        params.leftMargin = ZtinfoUtils.dip2px(mActivity, mTitleMargin);
        params.rightMargin = ZtinfoUtils.dip2px(mActivity, mTitleMargin);
        titleLayout.addView(textView, params);
        textViewList.add(textView);
        int width;        //设置宽高
        if (position == 0) {
            width = 0;
            moveToList.add(width);
        } else {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            textViewList.get(position - 1).measure(w, h);
            width = textViewList.get(position - 1).getMeasuredWidth() + mTitleMargin * 4;
            moveToList.add(width + moveToList.get(moveToList.size() - 1));
        }
    }


    @OnClick({R.id.bt_plan, R.id.ll_add})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_plan:
                Intent intent = new Intent(mActivity, CirclefbAct.class);
                intent.putExtra(CirclefbAct.TYPE, 0);
                intent.putExtra("labelId", titleList.get(currentPos).getInterestId());
                intent.putExtra("flag", currentPos);
                startActivity(intent);
                break;
            case R.id.ll_add:
                openActivity(LabelMangerAct.class);
                break;
        }
    }

    class posOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if ((int) view.getTag() == currentPos) {
                return;
            }
            textViewList.get(currentPos).setTextColor(Color.parseColor("#999999"));
            currentPos = (int) view.getTag();
            page = 1;
            rows = 10;
            getChannelTalks(titleList.get(currentPos).getInterestId(), page, rows);
            textViewList.get(currentPos).setTextColor(Color.parseColor("#52C791"));
        }
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

            }

            @Override
            public void onResponse(ResultBean<List<UserInterestInfo>> response) {
                titleList = response.getData();
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
    private void getChannelTalks(String lableId, final int page, int rows) {
        channelTalkEntityList = new ArrayList<>();
        datalist = new ArrayList<>();

        RequestManager.getTalkManager().getChannelTalks(lableId, page, rows, new ResultCallback<ResultBean<DataBean<ChannelTalkEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                vp_chan.onPullUpRefreshComplete();
                vp_chan.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<DataBean<ChannelTalkEntity>> response) {
                vp_chan.onPullUpRefreshComplete();
                vp_chan.onPullDownRefreshComplete();
                channelTalkEntityList = response.getData().getRows();
                if (channelTalkEntityList.size() == 0) {
                    if (page == 1) {

                    } else {
                        vp_chan.setHasMoreData(false);
                    }
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
                            itemList.add(new ImageInfo(urlList[i], 1624, 914));
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

    @Override
    public void onStart() {
        super.onStart();
        if (receiverTalk == null) {
            receiverTalk = new MyBroadCastReceiverTalk();
            mActivity.registerReceiver(receiverTalk, new IntentFilter(MYLABELS));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiverTalk != null) {
            mActivity.unregisterReceiver(receiverTalk);
            receiverTalk = null;
        }
    }

    private MyBroadCastReceiverTalk receiverTalk;
    public static final String MYLABELS = "labels_update";

    private class MyBroadCastReceiverTalk extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            titleLayout.removeAllViews();
            textViewList.clear();
            titleList.clear();
            datalist.clear();
            titleList = LabelInterestDao.getInterestLabel();
            if (intent!=null) {
                Bundle bundle=intent.getExtras();
                if (bundle!=null) {
                    pos = bundle.getInt("flag", currentPos);
                }
            }
            if (titleList == null) {
                getMyLabels(-1);
            } else {
                initDate(titleList);
            }
        }
    }

}
