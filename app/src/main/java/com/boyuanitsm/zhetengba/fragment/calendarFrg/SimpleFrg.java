package com.boyuanitsm.zhetengba.fragment.calendarFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mess.AddFriendsAct;
import com.boyuanitsm.zhetengba.activity.mess.ScanQrcodeAct;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.adapter.MyPageAdapter;
import com.boyuanitsm.zhetengba.adapter.Simple_TextAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.ActivityLabel;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ACache;
import com.boyuanitsm.zhetengba.utils.GsonUtils;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.loopview.LoopViewPager;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 会友界面
 * Created by xiaoke on 2016/4/24.
 */
public class SimpleFrg extends BaseFragment {
    private PullToRefreshListView lv_act;
    private View view;
    private View viewHeader_act;
    private View viewFloat;
    private ActAdapter adapter;
    private LoopViewPager viewPager;
    private MyPageAdapter pageAdapter;
    private LinearLayout ll_point;
    private LinearLayout ll_ft;
    private LinearLayout ll_sx;
    private CheckBox cb_all, cb_all2, cb_sj, cb_sj2, cb_bq, cb_bq2;
    private List<View> views = new ArrayList<View>();
    private LinearLayout.LayoutParams paramsL = new LinearLayout.LayoutParams(20, 20);
    private List<LabelBannerInfo> bannerInfoList;
    private List<SimpleInfo> list;//活动对象集合
    private List<ActivityLabel> labellist;
    private Simple_TextAdapter simple_textAdapter;
    private List<SimpleInfo> datas = new ArrayList<>();
    private ACache aCache;
    private int page = 1;
    private int rows = 10;
    private int state = 1;
    private int cusPos = -1;//0时间，1标签，2全部
    private boolean flag = false;
    private IntentFilter filter;
    private LinearLayout noList;
    private ImageView ivAnim;
    private TextView noMsg;
    private AnimationDrawable animationDrawable;
    private PopupWindow mPopupWindow;
    private Gson gson;
    private String times;
    private String labelIds;
    private LinearLayout ll_friend;
    private PopupWindow mPopupWindowAd;
    private BroadcastReceiver DteChangeRecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            page = 1;
            state = intent.getIntExtra("state", state);
            getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);//切换到好友；
        }
    };

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.act_frag, null, false);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewHeader_act = getLayoutInflater(savedInstanceState).inflate(R.layout.item_viewpager_act, null);
        viewFloat = getLayoutInflater(savedInstanceState).inflate(R.layout.act_frag_floating, null);
        ll_friend = (LinearLayout) view.findViewById(R.id.ll_friend);
        ll_sx = (LinearLayout) viewFloat.findViewById(R.id.ll_sx);
        cb_all = (CheckBox) viewFloat.findViewById(R.id.cb_all);
        cb_all2 = (CheckBox) view.findViewById(R.id.cb_all);
        cb_sj = (CheckBox) viewFloat.findViewById(R.id.cb_sj);
        cb_sj2 = (CheckBox) view.findViewById(R.id.cb_sj);
        cb_bq = (CheckBox) viewFloat.findViewById(R.id.cb_bq);
        cb_bq2 = (CheckBox) view.findViewById(R.id.cb_bq);
        lv_act = (PullToRefreshListView) view.findViewById(R.id.lv_act);
        ll_ft = (LinearLayout) view.findViewById(R.id.ll_ft);
        noList = (LinearLayout) view.findViewById(R.id.noList);
        ivAnim = (ImageView) view.findViewById(R.id.ivAnim);
        noMsg = (TextView) view.findViewById(R.id.noMsg);
        viewPager = (LoopViewPager) viewHeader_act.findViewById(R.id.vp_loop_act);
        ll_point = (LinearLayout) viewHeader_act.findViewById(R.id.ll_point);
        //刷新初始化
        LayoutHelperUtil.freshInit(lv_act);
//        noList.setVisibility(View.VISIBLE);
        aCache = ACache.get(mActivity);
        gson = new Gson();
        ll_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPop();
            }
        });
        lv_act.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);//获取我的列表
                getBanner();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);//获取我的
                getBanner();
            }
        });
        lv_act.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    flag = true;
                    ll_ft.setVisibility(View.VISIBLE);
                } else {
                    flag = false;
                    ll_ft.setVisibility(View.GONE);
                }
            }
        });
        //设置简约listview的headerview：item_viewpager_act.xml
        lv_act.getRefreshableView().addHeaderView(viewHeader_act);
        lv_act.getRefreshableView().addHeaderView(viewFloat);
        getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);
        //首页活动轮播图片展示
        getBanner();
        //获取活动标签
        getAcitivtyLabel(-1);
        viewPager.setAuto(true);
        //设置监听
        viewPager.setOnPageChangeListener(getListener());
        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cusPos = 2;
                    selectPop(cusPos);
                } else {
                    mPopupWindow.dismiss();
                }
            }
        });
        cb_all2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cusPos = 2;
                    selectPop(cusPos);
                } else {
                    mPopupWindow.dismiss();
                }
            }
        });
        cb_sj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cusPos = 0;
                    selectPop(cusPos);
                } else {
                    mPopupWindow.dismiss();
                }
            }
        });
        cb_sj2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cusPos = 0;
                    selectPop(cusPos);
                } else {
                    mPopupWindow.dismiss();
                }
            }
        });
        cb_bq.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (labellist != null && labellist.size() > 0) {
                        cusPos = 1;
                        selectPop(cusPos);
                    } else {
                        getAcitivtyLabel(0);
                    }

                } else {
                    mPopupWindow.dismiss();
                }
            }
        });
        cb_bq2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (labellist != null && labellist.size() > 0) {
                        cusPos = 1;
                        selectPop(cusPos);
                    } else {
                        getAcitivtyLabel(0);
                    }
                } else {
                    mPopupWindow.dismiss();
                }
            }
        });
    }

    public static final String DATA_CHANGE_KEY = "data_change_fragment";

    @Override
    public void onStart() {
        //广播接收者，接受好友列表更新数据
        filter = new IntentFilter();
        filter.addAction(DATA_CHANGE_KEY);
        mActivity.registerReceiver(DteChangeRecevier, filter);//切换到好友；
        super.onStart();
    }

    /***
     * viewpager监听
     *
     * @return
     */
    @NonNull
    private ViewPager.OnPageChangeListener getListener() {
        return new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (views.size() != 0 && views.get(position) != null) {
                    for (int i = 0; i < views.size(); i++) {
                        if (i == position) {
                            views.get(i).setBackgroundResource(R.drawable.point_focus);
                        } else {
                            views.get(i).setBackgroundResource(R.drawable.point_normal);
                        }
                    }

                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };
    }

    /***
     * 初始化viewpager适配器
     *
     * @param bannerInfoList
     */

    private void initMyPageAdapter(List<LabelBannerInfo> bannerInfoList) {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new MyPageAdapter(mActivity, bannerInfoList);
            if (viewPager != null) {
                viewPager.setAdapter(pageAdapter);
            }

        } else {
            pageAdapter.upData(bannerInfoList);
        }
    }

    /***
     * 初始化点
     */
    private void initPoint() {
        views.clear();
        ll_point.removeAllViews();
        for (int i = 0; i < 3; i++) {
            View view = new View(mActivity);
            paramsL.setMargins(ZhetebaUtils.dip2px(mActivity, 5), 0, 0, 0);
            view.setLayoutParams(paramsL);//*
            if (i == 0) {
                view.setBackgroundResource(R.drawable.point_focus);
            } else {
                view.setBackgroundResource(R.drawable.point_normal);
            }

            views.add(view);
            ll_point.addView(view);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(DteChangeRecevier);//注销广播

    }

    /***
     * 获取活动首页轮播图
     */
    private void getBanner() {
        RequestManager.getScheduleManager().getBanner(new ResultCallback<ResultBean<List<LabelBannerInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
//                noList.setVisibility(View.GONE);
//                noList.setVisibility(View.VISIBLE);
//                ivAnim.setImageResource(R.drawable.loadfail_list);
//                animationDrawable = (AnimationDrawable) ivAnim.getDrawable();
//                animationDrawable.start();
//                noMsg.setText("加载失败...");
                String bannerList = aCache.getAsString("SimpleBanner");
//                Gson gson=new Gson();
                MyLogUtils.info(bannerList + "bannerlist是=====");
                if (!TextUtils.isEmpty(bannerList)) {
                    List<LabelBannerInfo> bannerInfos = gson.fromJson(bannerList, new TypeToken<List<LabelBannerInfo>>() {
                    }.getType());
                    initMyPageAdapter(bannerInfos);
                }
                MyToastUtils.showShortToast(mActivity, errorMsg);
            }

            @Override
            public void onResponse(ResultBean<List<LabelBannerInfo>> response) {
                noList.setVisibility(View.GONE);
//                if (animationDrawable!=null){
//                    animationDrawable.stop();
//                    animationDrawable=null;
//                    noList.setVisibility(View.GONE);
//                }
                bannerInfoList = new ArrayList<LabelBannerInfo>();
                bannerInfoList = response.getData();
//                Gson gson=new Gson();
                aCache.put("SimpleBanner", GsonUtils.bean2Json(bannerInfoList));
                //设置viewpager适配/轮播效果
                initMyPageAdapter(bannerInfoList);
            }
        });
    }

    /***
     * 获取活动列表
     *
     * @param page
     * @param row
     */
    private void getActivityList(final int page, int row) {
        list = new ArrayList<SimpleInfo>();
        RequestManager.getScheduleManager().getActivityList(page, row, new ResultCallback<ResultBean<DataBean<SimpleInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_act.onPullUpRefreshComplete();
                lv_act.onPullDownRefreshComplete();
                String strList = aCache.getAsString("AllsimpleInfoList");
                List<SimpleInfo> infos = new ArrayList<SimpleInfo>();
                infos = gson.fromJson(strList, new TypeToken<List<SimpleInfo>>() {
                }.getType());
//                infos= GsonUtils.gsonToList(strList, SimpleInfo.class);;
                if (infos != null && infos.size() > 0) {
                    if (adapter == null) {
                        //设置简约listview的条目
                        adapter = new ActAdapter(mActivity, infos);
                        lv_act.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.update(infos);
                    }
                }
//                else {
//                    noList.setVisibility(View.VISIBLE);
//                    ivAnim.setImageResource(R.mipmap.planeno);
//                    noMsg.setText("加载失败");
//                }
            }

            @Override
            public void onResponse(ResultBean<DataBean<SimpleInfo>> response) {
//                if (animationDrawable!=null){
//                    animationDrawable.stop();
//                    animationDrawable=null;
//                    noList.setVisibility(View.GONE);
//                }
                lv_act.onPullUpRefreshComplete();
                lv_act.onPullDownRefreshComplete();
                list = response.getData().getRows();
                if (list.size() == 0) {
                    if (page == 1) {
//                        adapter.update(list);
//                        noList.setVisibility(View.VISIBLE);
//                        ivAnim.setImageResource(R.mipmap.planeno);
//                        noMsg.setText("暂无内容");
                    } else {
                        lv_act.setHasMoreData(false);
                    }
                }
//                else {
//                    noList.setVisibility(View.GONE);
//                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
//                Gson gson=new Gson();
                aCache.put("AllsimpleInfoList", GsonUtils.bean2Json(datas));
                MyLogUtils.info("datas数据是：" + datas.toString());
                if (adapter == null) {
                    //设置简约listview的条目
                    adapter = new ActAdapter(mActivity, datas);
                    lv_act.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.update(datas);
                }


            }
        });

    }

    /***
     * 活动信息：好友/全部显示
     *
     * @param page
     * @param rows
     * @param state
     */
    private void getFriendOrAllAcitvity(final int page, int rows, final String state, String labelIds, String days) {
        list = new ArrayList<SimpleInfo>();
        RequestManager.getScheduleManager().getFriendOrAllActivity(page, rows, state, labelIds, days, new ResultCallback<ResultBean<DataBean<SimpleInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_act.onPullUpRefreshComplete();
                lv_act.onPullDownRefreshComplete();
                noList.setVisibility(View.GONE);
                String strList = null;
                if (TextUtils.equals(state, 0 + "")) {
                    strList = aCache.getAsString("FriendsimpleInfoList");
                } else if (TextUtils.equals(state, 2 + "")) {
                    strList = aCache.getAsString("MysimpleInfoList");
                }
                List<SimpleInfo> infos = new ArrayList<SimpleInfo>();
//                Gson gson=new Gson();
//                infos=GsonUtils.gsonToList(strList,SimpleInfo.class);
                infos = gson.fromJson(strList, new TypeToken<List<SimpleInfo>>() {
                }.getType());
                if (infos != null && infos.size() > 0) {
                    if (adapter == null) {
                        //设置简约listview的条目
                        adapter = new ActAdapter(mActivity, infos);
                        lv_act.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.update(infos);
                    }
                }
//                else {
//                    noList.setVisibility(View.VISIBLE);
//                    ivAnim.setImageResource(R.mipmap.planeno);
//                    noMsg.setText("加载失败");
//                }
            }

            @Override
            public void onResponse(ResultBean<DataBean<SimpleInfo>> response) {
                lv_act.onPullUpRefreshComplete();
                lv_act.onPullDownRefreshComplete();
                list = response.getData().getRows();
                noList.setVisibility(View.GONE);
                //获取到的list没有数据时
                if (list.size() == 0) {
                    if (page == 1) {
//                        adapter.update(list); //第一页就没有数据，表示全部无数据
//                        noList.setVisibility(View.VISIBLE);
//                        ivAnim.setImageResource(R.mipmap.planeno);
//                        noMsg.setText("暂无内容");
                    } else {
                        //无更多数据
                        lv_act.setHasMoreData(false);
                    }
                }
//                else {
//                    noList.setVisibility(View.GONE);
//                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
                if (TextUtils.equals(state, 0 + "")) {
//                    Gson gson=new Gson();
                    aCache.put("FriendsimpleInfoList", GsonUtils.bean2Json(datas));
                } else if (TextUtils.equals(state, 2 + "")) {
                    Gson gson = new Gson();
                    aCache.put("MysimpleInfoList", GsonUtils.bean2Json(datas));
                }
                if (adapter == null) {
                    //设置简约listview的条目
                    adapter = new ActAdapter(mActivity, datas);
                    lv_act.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.update(datas);
                }

            }
        });
    }

    /**
     * 待解决：对话框布局有出入
     * 选择对话框，选择好友/全部
     *
     * @param cusPos
     */
    private void selectPop(int cusPos) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.act_select_friend2, null);
        mPopupWindow = new PopupWindow(v, AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        RadioGroup rg_select = (RadioGroup) v.findViewById(R.id.rg_select);
        RadioButton rb_all = (RadioButton) v.findViewById(R.id.rb_all);
        RadioButton rb_friend = (RadioButton) v.findViewById(R.id.rb_friend);
        RadioButton rb_my = (RadioButton) v.findViewById(R.id.rb_my);
        RadioGroup rg_sj = (RadioGroup) v.findViewById(R.id.rg_sj);
        final RadioButton sj_all = (RadioButton) v.findViewById(R.id.sj_all);
        final RadioButton rb_one = (RadioButton) v.findViewById(R.id.rb_one);
        final RadioButton rb_three = (RadioButton) v.findViewById(R.id.rb_three);
        final RadioButton rb_week = (RadioButton) v.findViewById(R.id.rb_week);
        final RadioButton rb_month = (RadioButton) v.findViewById(R.id.rb_month);

        ListView lv_sx = (ListView) v.findViewById(R.id.lv_bq);
        if (cusPos == 0) {
            rg_sj.setVisibility(View.VISIBLE);
            lv_sx.setVisibility(View.GONE);
            rg_select.setVisibility(View.GONE);
        } else if (cusPos == 1) {
            rg_sj.setVisibility(View.GONE);
            lv_sx.setVisibility(View.VISIBLE);
            rg_select.setVisibility(View.GONE);
        } else if (cusPos == 2) {
            rg_select.setVisibility(View.VISIBLE);
            rg_sj.setVisibility(View.GONE);
            lv_sx.setVisibility(View.GONE);
        }
        if (labellist != null && labellist.size() > 0) {
            simple_textAdapter = new Simple_TextAdapter(mActivity, labellist, labelIds);
            lv_sx.setAdapter(simple_textAdapter);
            lv_sx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    labelIds = labellist.get(position).getId();
                        if (TextUtils.equals(labellist.get(position).getLabelName(),"全部")){
                            cb_bq.setText("标签");
                            cb_bq2.setText("标签");
                        }else {
                            cb_bq.setText(labellist.get(position).getLabelName());
                            cb_bq2.setText(labellist.get(position).getLabelName());
                        }
                        page = 1;
                        getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);
                        mPopupWindow.dismiss();
                }
            });
        }

        LinearLayout ll_dimis = (LinearLayout) v.findViewById(R.id.ll_dimis);
        ll_dimis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        if (TextUtils.equals(cb_all2.getText(), "全部")) {
            rb_all.setChecked(true);
        } else if (TextUtils.equals(cb_all2.getText(), "好友")) {
            rb_friend.setChecked(true);
        } else if (TextUtils.equals(cb_all2.getText(), "我的")) {
            rb_my.setChecked(true);
        }
        if (TextUtils.equals(cb_sj2.getText(), "时间")) {
            sj_all.setChecked(true);
        } else if (TextUtils.equals(cb_sj2.getText(), "一天内")) {
            rb_one.setChecked(true);
        } else if (TextUtils.equals(cb_sj2.getText(), "三天内")) {
            rb_three.setChecked(true);
        } else if (TextUtils.equals(cb_sj2.getText(), "一周内")) {
            rb_week.setChecked(true);
        } else if (TextUtils.equals(cb_sj2.getText(), "一月内")) {
            rb_month.setChecked(true);
        }
        rg_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.rb_all:
                        page = 1;
                        state = 1;
                        getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);
                        cb_all2.setChecked(false);
                        cb_all.setChecked(false);
                        cb_all2.setText("全部");
                        cb_all.setText("全部");
                        mPopupWindow.dismiss();
                        break;
                    case R.id.rb_friend:
                        page = 1;
                        state = 0;
                        getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);
                        cb_all2.setChecked(false);
                        cb_all.setChecked(false);
                        cb_all2.setText("好友");
                        cb_all.setText("好友");
                        mPopupWindow.dismiss();
                        break;
                    case R.id.rb_my:
                        page = 1;
                        state = 2;
                        getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);
                        cb_all2.setChecked(false);
                        cb_all.setChecked(false);
                        cb_all2.setText("我的");
                        cb_all.setText("我的");
                        mPopupWindow.dismiss();
                        break;
                }
            }
        });
        rg_sj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.sj_all:
                        times = null;
                        page = 1;
                        rb_one.setChecked(false);
                        rb_three.setChecked(false);
                        rb_week.setChecked(false);
                        rb_month.setChecked(false);
                        cb_sj.setText("时间");
                        cb_sj2.setText("时间");
                        getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);
                        mPopupWindow.dismiss();
                        break;
                    case R.id.rb_one:
                        times = 1 + "";
                        page = 1;
                        sj_all.setChecked(false);
                        rb_three.setChecked(false);
                        rb_week.setChecked(false);
                        rb_month.setChecked(false);
                        cb_sj.setText("一天内");
                        cb_sj2.setText("一天内");
                        getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);
                        mPopupWindow.dismiss();
                        break;
                    case R.id.rb_three:
                        times = 3 + "";
                        page = 1;
                        sj_all.setChecked(false);
                        rb_one.setChecked(false);
                        rb_week.setChecked(false);
                        rb_month.setChecked(false);
                        cb_sj.setText("三天内");
                        cb_sj2.setText("三天内");
                        getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);
                        mPopupWindow.dismiss();
                        break;
                    case R.id.rb_week:
                        times = 7 + "";
                        page = 1;
                        sj_all.setChecked(false);
                        rb_one.setChecked(false);
                        rb_three.setChecked(false);
                        rb_month.setChecked(false);
                        cb_sj.setText("一周内");
                        cb_sj2.setText("一周内");
                        getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);
                        mPopupWindow.dismiss();
                        break;
                    case R.id.rb_month:
                        times = 30 + "";
                        page = 1;
                        sj_all.setChecked(false);
                        rb_one.setChecked(false);
                        rb_week.setChecked(false);
                        rb_three.setChecked(false);
                        cb_sj.setText("一月内");
                        cb_sj2.setText("一月内");
                        getFriendOrAllAcitvity(page, rows, state + "", labelIds, times);
                        mPopupWindow.dismiss();
                        break;
                }
            }
        });
        WindowManager manager = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        int xpos = manager.getDefaultDisplay().getWidth() / 2 - mPopupWindow.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.ppAnimBottom);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cb_all.setChecked(false);
                cb_all2.setChecked(false);
                cb_sj.setChecked(false);
                cb_sj2.setChecked(false);
                cb_bq2.setChecked(false);
                cb_bq.setChecked(false);
            }
        });
        if (flag) {
            mPopupWindow.showAsDropDown(ll_ft);
        } else {
            mPopupWindow.showAsDropDown(ll_sx);
        }
    }

    private void getAcitivtyLabel(final int type) {
        RequestManager.getScheduleManager().getAllActivityLabel(new ResultCallback<ResultBean<List<ActivityLabel>>>() {
            @Override
            public void onError(int status, String errorMsg) {
//                String strList= aCache.getAsString("activityLabel");
//                if (!TextUtils.isEmpty(strList)){
//                  labellist= gson.fromJson(strList, new TypeToken<List<ActivityLabel>>() {
//                    }.getType());
//                }
            }

            @Override
            public void onResponse(ResultBean<List<ActivityLabel>> response) {

                ActivityLabel activityLabel=new ActivityLabel();
                activityLabel.setLabelName("全部");
                if (labellist!=null){
                    labellist.clear();
                }
                labellist = response.getData();
                labellist.add(0, activityLabel);
                if (type == 0) {
                    cusPos = 1;
                    selectPop(cusPos);
                }
            }
        });
    }
    /**
     * 待解决：对话框布局有出入
     * 选择对话框，选择好友/全部
     */
    private void addPop() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.act_pop_mess2, null);
        mPopupWindowAd = new PopupWindow(v,layoutParams.width, layoutParams.height);
        LinearLayout ll_sao = (LinearLayout) v.findViewById(R.id.ll_sao);
        LinearLayout ll_qun = (LinearLayout) v.findViewById(R.id.ll_qunavatar);
        LinearLayout ll_add_friend = (LinearLayout) v.findViewById(R.id.ll_add_friend);
        mPopupWindowAd.setBackgroundDrawable(new BitmapDrawable(null, ""));
        mPopupWindowAd.setFocusable(true);
        //获取xoff
        WindowManager manager = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        int xpos = manager.getDefaultDisplay().getWidth() / 2 - mPopupWindowAd.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        mPopupWindowAd.showAsDropDown(ll_friend, xpos, 0);
        ll_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //扫一扫
                getActivity().startActivity(new Intent(getContext(), ScanQrcodeAct.class));
                mPopupWindowAd.dismiss();
            }
        });
        ll_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加好友
                getActivity().startActivity(new Intent(getContext(), AddFriendsAct.class));
                mPopupWindowAd.dismiss();
            }
        });

    }
}
