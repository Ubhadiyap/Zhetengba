package com.boyuanitsm.zhetengba.fragment.calendarFrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.adapter.CalAdapter;
import com.boyuanitsm.zhetengba.adapter.MyPageAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.ScheduleInfo;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ACache;
import com.boyuanitsm.zhetengba.utils.GsonUtils;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.loopview.LoopViewPager;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 档期界面
 * Created by xiaoke on 2016/4/24.
 */
public class CalFrg extends BaseFragment {
    private View view;
    private View viewHeader_calen;
    private PullToRefreshListView lv_calen;
    private LoopViewPager vp_loop_calen;
    private List<View> views = new ArrayList<View>();
    private LinearLayout.LayoutParams paramsL = new LinearLayout.LayoutParams(20, 20);
    private MyPageAdapter pageAdapter;
    private LinearLayout ll_point;
    private List<ScheduleInfo> list;
    private List<ScheduleInfo> datas=new ArrayList<>();
    private List<LabelBannerInfo> bannerInfoList;
    private CalAdapter adapter;
    private int page=1,rows=10;
    private int state=0;
    private boolean flag=true;
    private IntentFilter filter;
    private LinearLayout noList;
    private ImageView ivAnim;
    private TextView noMsg;
    private AnimationDrawable animationDrawable;
    private ACache aCache;
    private Gson gson;
    private PopupWindow mPopupWindow;
    private CheckBox cb_all,cb_all2;
    private View viewFloat;
    private LinearLayout ll_ft;
    private LinearLayout  ll_sx;
    private boolean flag2=false;
    //    广播接收者更新档期数据
    private BroadcastReceiver calFriendChangeRecevier=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            page=1;
            state=intent.getIntExtra("state",state);
            if (state==1){
                getScheduleList(page, rows);
            }else {
                getFriendAllSchudle(page, rows,state + "");//切换到好友；//切换到好友；
            }
        }
    };


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.calendar_frag, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        //塞入item_loop_viewpager_calen，到viewpager   :view1
        viewHeader_calen = getLayoutInflater(savedInstanceState).inflate(R.layout.item_viewpager_act, null);
        viewFloat = getLayoutInflater(savedInstanceState).inflate(R.layout.act_frag_floating2, null);
        ll_sx = (LinearLayout) viewFloat.findViewById(R.id.ll_sx);
        cb_all = (CheckBox) viewFloat.findViewById(R.id.cb_all);
        cb_all2 = (CheckBox) view.findViewById(R.id.cb_all);
        ll_ft = (LinearLayout) view.findViewById(R.id.ll_ft);
        lv_calen = (PullToRefreshListView) view.findViewById(R.id.lv_calen);
        noList = (LinearLayout) view.findViewById(R.id.noList);
        ivAnim = (ImageView) view.findViewById(R.id.ivAnim);
        noMsg = (TextView) view.findViewById(R.id.noMsg);
        vp_loop_calen = (LoopViewPager) viewHeader_calen.findViewById(R.id.vp_loop_act);
        ll_point = (LinearLayout) viewHeader_calen.findViewById(R.id.ll_point);
        //下拉刷新初始化
        LayoutHelperUtil.freshInit(lv_calen);
         aCache=ACache.get(mActivity);
        gson=new Gson();
        lv_calen.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                if (state == 0) {
                    getFriendAllSchudle(page, rows, state + "");//好友列表获取；
                } else if (state == 1) {
                    getScheduleList(page, rows);//全部列表获取；
                } else if (state == 2) {
                    getFriendAllSchudle(page, rows, state + "");//我的列表获取；
                }
                getScheduleBanner();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                if (state == 0) {
                    getFriendAllSchudle(page, rows, state + "");//好友列表获取；
                } else if (state == 1) {
                    getScheduleList(page, rows);//全部列表获取；
                } else if (state == 2) {
                    getFriendAllSchudle(page, rows, state + "");//我的列表获取；
                }
                getScheduleBanner();
            }
        });
        lv_calen.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    flag2 = true;
                    ll_ft.setVisibility(View.VISIBLE);
                } else {
                    flag2 = false;
                    ll_ft.setVisibility(View.GONE);
                }
            }
        });

        //设置listview头部headview
        lv_calen.getRefreshableView().addHeaderView(viewHeader_calen);
        lv_calen.getRefreshableView().addHeaderView(viewFloat);
        if (state==1){
            getScheduleList(page, rows);
        }else if (state==0){
            getFriendAllSchudle(page, rows, state + "");
        }else if (state==2){
            getFriendAllSchudle(page, rows, state + "");
        }
        //档期轮播图片展示
        getScheduleBanner();
        vp_loop_calen.setAuto(true);
        //设置监听
        vp_loop_calen.setOnPageChangeListener(getListener());
        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectPop();
                } else {
                    mPopupWindow.dismiss();
                }
            }
        });
        cb_all2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectPop();
                } else {
                    mPopupWindow.dismiss();
                }
            }
        });

    }
    public static final String CAL_DATA_CHANGE_KEY="cal_data_change_fragment";
    @Override
    public void onResume() {
        //广播接收者，接受好友列表更新数据
        filter=new IntentFilter();
        filter.addAction(CAL_DATA_CHANGE_KEY);
        mActivity.registerReceiver(calFriendChangeRecevier, filter);//切换到好友；
        super.onResume();
    }

    /***
     * 初始化viewpager适配器
     */

    private void initMyPageAdapter(List<LabelBannerInfo> list) {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new MyPageAdapter(mActivity,list);
            if (vp_loop_calen != null) {
                vp_loop_calen.setAdapter(pageAdapter);
            }

        } else {
            pageAdapter.upData(list);
        }
    }

    /**
     * 获取档期列表；
     * @param page
     * @param rows
     */
    private void getScheduleList(final int page,int rows){
        list=new ArrayList<>();
        RequestManager.getScheduleManager().getScheduleList(page, rows, new ResultCallback<ResultBean<DataBean<ScheduleInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_calen.onPullUpRefreshComplete();
                lv_calen.onPullDownRefreshComplete();
                String strList = aCache.getAsString("AllCal");
                List<ScheduleInfo> infos = new ArrayList<ScheduleInfo>();
//                Gson gson = new Gson();
//                infos = GsonUtils.gsonToList(strList,ScheduleInfo.class);
                infos = gson.fromJson(strList, new TypeToken<List<ScheduleInfo>>() {
                }.getType());

                if (infos != null && infos.size() > 0) {
                    if (adapter == null) {
                        //设置简约listview的条目
                        adapter = new CalAdapter(mActivity, infos);
                        lv_calen.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.update(infos);
                    }
                }
//                else {
//                    noList.setVisibility(View.VISIBLE);
//                    ivAnim.setImageResource(R.drawable.loadfail_list);
//                    animationDrawable = (AnimationDrawable) ivAnim.getDrawable();
//                    animationDrawable.start();
//                    noMsg.setText("加载失败...");
//                }
            }

            @Override
            public void onResponse(ResultBean<DataBean<ScheduleInfo>> response) {
                lv_calen.onPullUpRefreshComplete();
                lv_calen.onPullDownRefreshComplete();
//                if (animationDrawable != null) {
//                    animationDrawable.stop();
//                    animationDrawable = null;
//                    noList.setVisibility(View.GONE);
//                }
                list = response.getData().getRows();
                if (list.size() == 0) {
                    if (page == 1) {

                    } else {
                        lv_calen.setHasMoreData(false);
                    }
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
//                Gson gson = new Gson();
                aCache.put("AllCal", GsonUtils.bean2Json(datas));
                if (adapter == null) {
                    //设置简约listview的条目
                    adapter = new CalAdapter(mActivity, datas);
                    lv_calen.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.update(datas);
                }
            }
        });
    }

    /**
     * 点击好友，全部回调
     * @param page
     * @param rows
     * @param state
     */
    private void getFriendAllSchudle(final int page,int rows, final String state){
        RequestManager.getScheduleManager().getScheduleFriend(page, rows, state, new ResultCallback<ResultBean<DataBean<ScheduleInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_calen.onPullUpRefreshComplete();
                lv_calen.onPullDownRefreshComplete();
                String strList = null;
                if (TextUtils.equals(state, 0 + "")) {
                    strList = aCache.getAsString("FriendCal");

                } else if (TextUtils.equals(state, 2 + "")) {
                    strList = aCache.getAsString("MyCal");
                }
                List<ScheduleInfo> infos = new ArrayList<ScheduleInfo>();
//                Gson gson=new Gson();
                infos = gson.fromJson(strList, new TypeToken<List<ScheduleInfo>>() {
                }.getType());
                if (infos != null && infos.size() > 0) {
                    if (adapter == null) {
                        //设置简约listview的条目
                        adapter = new CalAdapter(mActivity, infos);
                        lv_calen.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.update(infos);
                    }
//                }else {
//                    noList.setVisibility(View.VISIBLE);
//                    ivAnim.setImageResource(R.drawable.loadfail_list);
//                    animationDrawable = (AnimationDrawable) ivAnim.getDrawable();
//                    animationDrawable.start();
//                    noMsg.setText("加载失败...");
                }

            }

            @Override
            public void onResponse(ResultBean<DataBean<ScheduleInfo>> response) {
                lv_calen.onPullUpRefreshComplete();
                lv_calen.onPullDownRefreshComplete();
//                if (animationDrawable!=null){
//                    animationDrawable.stop();
//                    animationDrawable=null;
//                    noList.setVisibility(View.GONE);
//                }
                list = response.getData().getRows();
                if (list.size() == 0) {
                    if (page == 1) {
//                      adapter.update(list);  //没有数据
                    } else {
                        lv_calen.setHasMoreData(false);
                    }
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
//                Gson gson=new Gson();
                if (TextUtils.equals(state, 0 + "")) {
                    aCache.put("FriendCal", GsonUtils.bean2Json(datas));
                } else if (TextUtils.equals(state, 2 + "")) {
                    aCache.put("MyCal", GsonUtils.bean2Json(datas));
                }
                if (adapter == null) {
                    //设置简约listview的条目
                    adapter = new CalAdapter(mActivity, datas);
                    lv_calen.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.update(datas);
                }
            }
        });
    }

    /**
     * 获取档期轮播图；
     */
    private void getScheduleBanner(){
        RequestManager.getScheduleManager().getScheduleBanner(new ResultCallback<ResultBean<List<LabelBannerInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
//                noList.setVisibility(View.VISIBLE);
//                ivAnim.setImageResource(R.drawable.loadfail_list);
//                animationDrawable = (AnimationDrawable) ivAnim.getDrawable();
//                animationDrawable.start();
//                noMsg.setText("加载失败...");
                String bannerList = aCache.getAsString("CalBanner");
//                Gson gson=new Gson();
                if (!TextUtils.isEmpty(bannerList)) {
//                    bannerInfos = GsonUtils.gsonToList(bannerList, LabelBannerInfo.class);
                    List<LabelBannerInfo> bannerInfos = gson.fromJson(bannerList, new TypeToken<List<LabelBannerInfo>>() {
                    }.getType());
                    initMyPageAdapter(bannerInfos);
                }
            }

            @Override
            public void onResponse(ResultBean<List<LabelBannerInfo>> response) {
//                if (animationDrawable!=null){
//                    animationDrawable.stop();
//                    animationDrawable=null;
//                }
                bannerInfoList = new ArrayList<LabelBannerInfo>();
                bannerInfoList = response.getData();
//                Gson gson=new Gson();
                aCache.put("CalBanner", GsonUtils.bean2Json(bannerInfoList));
                //设置viewpager适配/轮播效果
                initMyPageAdapter(bannerInfoList);
            }
        });
    }

    /**
     * 待解决：对话框布局有出入
     * 选择对话框，选择好友/全部
     */
    private void selectPop() {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.act_select_friend3, null);
        mPopupWindow = new PopupWindow(v, AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        RadioGroup rg_select = (RadioGroup) v.findViewById(R.id.rg_select);
        RadioButton rb_all = (RadioButton) v.findViewById(R.id.rb_all);
        RadioButton rb_friend = (RadioButton) v.findViewById(R.id.rb_friend);
        RadioButton rb_my = (RadioButton) v.findViewById(R.id.rb_my);
        LinearLayout ll_dimis = (LinearLayout) v.findViewById(R.id.ll_dimis);
        ll_dimis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        if (TextUtils.equals(cb_all2.getText(),"全部")){
            rb_all.setChecked(true);
        }else if (TextUtils.equals(cb_all2.getText(),"好友")){
            rb_friend.setChecked(true);
        }else if (TextUtils.equals(cb_all2.getText(),"我的")){
            rb_my.setChecked(true);
        }
        rg_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.rb_all:
                        page = 1;
                        state=1;
                        getScheduleList(page, rows);
                        cb_all2.setChecked(false);
                        cb_all.setChecked(false);
                        cb_all2.setText("全部");
                        cb_all.setText("全部");
                        mPopupWindow.dismiss();
                        break;
                    case R.id.rb_friend:
                        page = 1;
                        state = 0;
                        getFriendAllSchudle(page, rows, state + "");
                        cb_all2.setChecked(false);
                        cb_all.setChecked(false);
                        cb_all2.setText("好友");
                        cb_all.setText("好友");
                        mPopupWindow.dismiss();
                        break;
                    case R.id.rb_my:
                        page = 1;
                        state = 2;
                        getFriendAllSchudle(page, rows, state + "");
                        cb_all2.setChecked(false);
                        cb_all.setChecked(false);
                        cb_all2.setText("我的");
                        cb_all.setText("我的");
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
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cb_all.setChecked(false);
                cb_all2.setChecked(false);
            }
        });
        if (flag2) {
            mPopupWindow.showAsDropDown(ll_ft);
        } else {
            mPopupWindow.showAsDropDown(ll_sx);
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
            view.setLayoutParams(paramsL);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.point_focus);
            } else {
                view.setBackgroundResource(R.drawable.point_normal);
            }

            views.add(view);
            ll_point.addView(view);
        }
    }
    /***
     * viewpager监听
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(calFriendChangeRecevier);

    }
}
