package com.boyuanitsm.zhetengba.activity.circle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mess.SquareMessAct;
import com.boyuanitsm.zhetengba.activity.mine.LabelMangerAct;
import com.boyuanitsm.zhetengba.adapter.ChanAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ACache;
import com.boyuanitsm.zhetengba.utils.GsonUtils;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.LoadingView;
import com.boyuanitsm.zhetengba.view.bounScrollView.BounceScrollView;
import com.boyuanitsm.zhetengba.view.bounScrollView.ViewPagerIndicator;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.boyuanitsm.zhetengba.view.scan.Intents;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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
    @ViewInject(R.id.ll_comment)
    private LinearLayout ll_comment;
    @ViewInject(R.id.et_comment)
    private EditText et_comment;
    @ViewInject(R.id.iv_chanel_comment)
    private Button bt_send;
    @ViewInject(R.id.load_view)
    private LoadingView load_view;
    private int cusPos;
    private int currentPos = 0;//当前位置
    private ACache aCache;
    private List<UserInterestInfo> titleList;//标签集合
    private String labelStr = "04a9c093215d11e6ba57eca86ba4ba05";//吐槽标签id，后期可能会变
    private LinearLayout llnoList;
    private ImageView ivAnim;
    private TextView noMsg;
    private AnimationDrawable animationDrawable;
    private List<ChannelTalkEntity> channelTalkEntityList;
    //    private List<List<ImageInfo>> datalist;
    private List<ChannelTalkEntity> datas = new ArrayList<>();
    private int page = 1;
    private int rows = 10;
    private ChanAdapter adapter;
    public static final String CURRENT_POS = "currentPos";
    private String labelId;
    private ProgressDialog progressDialog;
    private int commentPosition = 0;
    private String channelId;

    private String strlist;
    private Gson gson;
    private List<ChannelTalkEntity> infos;

    private String petName;
    private String commentedUserId;
    private String fatherCommentId;
    private boolean flag;
    private View headview;
    private LinearLayout ll_news2;
    private TextView tv_mess2;
    private CircleImageView cv_head2;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

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
        headview = View.inflate(SquareAct.this, R.layout.cir_frg_floating, null);
        LayoutHelperUtil.freshInit(vp_chan);
        vp_chan.getRefreshableView().addHeaderView(headview);
        ll_news2 = (LinearLayout) headview.findViewById(R.id.ll_news);
        tv_mess2 = (TextView) headview.findViewById(R.id.tv_mess);
        cv_head2 = (CircleImageView) headview.findViewById(R.id.cv_head);
        SharedPreferences sharedPreferences = getSharedPreferences("sqa_cir",
                Activity.MODE_PRIVATE);
        String cir_news = sharedPreferences.getString("sqa_news", "");
        int cir_newsCount = sharedPreferences.getInt("sqa_NewsCount", 0);
        if (cir_newsCount == 0) {
            ll_news2.setVisibility(View.GONE);
        } else {
            ll_news2.setVisibility(View.VISIBLE);
            tv_mess2.setText(cir_newsCount + "条新消息");
        }
        if (!TextUtils.isEmpty(cir_news)) {
//            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(cir_news),cv_head,options);
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(cir_news), cv_head2, options);
        }
        ll_news2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SquareMessAct.class);
            }
        });
        //用缓存
        aCache = ACache.get(SquareAct.this);
        strlist = aCache.getAsString("channelTalkList");
        gson = new Gson();
        if (!TextUtils.isEmpty(strlist)) {
            infos = gson.fromJson(strlist, new TypeToken<List<ChannelTalkEntity>>() {
            }.getType());
        }
        if (infos != null && infos.size() > 0) {
            load_view.loadComplete();
            if (adapter == null) {
                //设置简约listview的条目
                adapter = new ChanAdapter(SquareAct.this, infos);
                vp_chan.getRefreshableView().setAdapter(adapter);
            } else {
                adapter.notifyChange(infos);
            }
        }
        getChannelTalks(labelStr, page, rows);
        et_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    bt_send.setBackgroundResource(R.drawable.main_btn_nor);
                    bt_send.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    bt_send.setBackgroundColor(Color.parseColor("#f4f4f4"));
                    bt_send.setTextColor(Color.parseColor("#999999"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        vp_chan.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                ll_comment.setVisibility(View.GONE);
                ZtinfoUtils.hideSoftKeyboard(getApplicationContext(), et_comment);
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
                getChannelTalks(labelStr, page, rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getChannelTalks(labelStr, page, rows);
            }
        });

        load_view.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getChannelTalks(labelStr, page, rows);
            }
        });
    }


    @OnClick({R.id.ll_add, R.id.ivRight, R.id.iv_chanel_comment})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRight:
                Intent intent = new Intent(SquareAct.this, CirclefbAct.class);
                intent.putExtra(CirclefbAct.TYPE, 0);
                intent.putExtra("labelId", labelStr);
                startActivity(intent);
                break;
            case R.id.ll_add:
                openActivity(LabelMangerAct.class);
                break;
            case R.id.iv_chanel_comment:
                if (flag) {
                    if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
                        bt_send.setEnabled(false);
                        commentChannelTalk(channelId, fatherCommentId, commentedUserId, et_comment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(getApplicationContext(), "请输入回复内容！");
                    }
                } else {
                    if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
                        bt_send.setEnabled(false);
                        commentChannelTalk(channelId, null, null, et_comment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(getApplicationContext(), "请输入评论内容！");
                    }
                }
//                if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
//                    bt_send.setEnabled(false);
//                    bt_send.setClickable(false);
//                    commentChannelTalk(channelId, null, et_comment.getText().toString().trim());
//                } else {
//                    MyToastUtils.showShortToast(getApplicationContext(), "请输入评论内容！");
//                }
                break;
        }
    }

    /**
     * 频道说说评论
     *
     * @param channelTalkId
     * @param fatherCommentId
     * @param commentContent
     */
    private void commentChannelTalk(final String channelTalkId, String fatherCommentId, String commentedUserId, final String commentContent) {
        RequestManager.getTalkManager().commentChannelTalk(channelTalkId, fatherCommentId, commentedUserId, commentContent, new ResultCallback<ResultBean<ChannelTalkEntity>>() {
            @Override
            public void onError(int status, String errorMsg) {
                bt_send.setEnabled(true);
            }

            @Override
            public void onResponse(ResultBean<ChannelTalkEntity> response) {
                ChannelTalkEntity entity = response.getData();
                //重新获取评论列表，刷新评论数目，关闭键盘
                et_comment.setText("");
                ZtinfoUtils.hideSoftKeyboard(getApplicationContext(), et_comment);
                //封装数据
//                if (cusPos>0){
                if (flag) {
                    if (datas.get(cusPos).getCommentsList() == null) {
                        List<ChannelTalkEntity> list = new ArrayList<ChannelTalkEntity>();
                        datas.get(cusPos).setCommentsList(list);
                        datas.get(cusPos).getCommentsList().add(entity);
                    } else {
                        datas.get(cusPos).getCommentsList().add(entity);
                    }
                    if (!TextUtils.isEmpty(datas.get(cusPos).getCommentCounts() + "")) {
                        datas.get(cusPos).setCommentCounts(Integer.parseInt(entity.getRemark()));
                    } else {
                        datas.get(cusPos).setCommentCounts(1);
                    }
                } else {
                    if (datas.get(cusPos).getCommentsList() == null) {
                        List<ChannelTalkEntity> list = new ArrayList<ChannelTalkEntity>();
                        datas.get(cusPos).setCommentsList(list);
                        datas.get(cusPos).getCommentsList().add(entity);
                    } else {
                        datas.get(cusPos).getCommentsList().add(entity);
                    }
                    if (!TextUtils.isEmpty(datas.get(cusPos).getCommentCounts() + "")) {
                        datas.get(cusPos).setCommentCounts(Integer.parseInt(entity.getRemark()));
                    } else {
                        datas.get(cusPos).setCommentCounts(1);
                    }
//                }
                }

                if (adapter == null) {
                    adapter = new ChanAdapter(SquareAct.this, datas);
                    vp_chan.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }
                MyToastUtils.showShortToast(getApplicationContext(), response.getMessage());
                bt_send.setEnabled(true);
                ll_comment.setVisibility(View.GONE);
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
//        datalist = new ArrayList<>();
        RequestManager.getTalkManager().getChannelTalks(lableId, page, rows, new ResultCallback<ResultBean<DataBean<ChannelTalkEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                vp_chan.onPullUpRefreshComplete();
                vp_chan.onPullDownRefreshComplete();
                load_view.loadError();
                if (datas.size() > 0) {

                } else {
//                    llnoList.setVisibility(View.VISIBLE);
//                    ivAnim.setImageResource(R.mipmap.planeno);
//                    noMsg.setText("加载失败");
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
//                        llnoList.setVisibility(View.VISIBLE);
//                        ivAnim.setImageResource(R.mipmap.planeno);
//                        noMsg.setText("暂无内容");
                        load_view.noContent();
                    } else {
                        vp_chan.setHasMoreData(false);
                    }
                } else {
                    load_view.loadComplete();
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(channelTalkEntityList);
                aCache.put("channelTalkList", GsonUtils.bean2Json(datas));//实体转换成json
                if (adapter == null) {
                    adapter = new ChanAdapter(SquareAct.this, datas);
                    vp_chan.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }
                adapter.setOnItemClickListener(new ChanAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, String id, int position) {
                        channelId = id;
                        flag=false;
                        vp_chan.getRefreshableView().setSelection(position+1);
                        cusPos = position;
                        ll_comment.setVisibility(View.VISIBLE);
                        et_comment.requestFocus();
                        et_comment.setHint("说点什么吧...");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });
            }
        });
    }

    private MyReceiver myReceiver;
    public static final String TALK_LIST = "up_date_talks";

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String cir_hf = intent.getStringExtra("cir_hf");
            petName = intent.getStringExtra("petName");
            String fatherId = intent.getStringExtra("fatherId");
            String comId = intent.getStringExtra("comId");
            channelId = intent.getStringExtra("channelTalkId");
            cusPos = intent.getIntExtra("clickPos", cusPos);
            if (TextUtils.equals("cir_hf", cir_hf)) {
                ll_comment.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(fatherId)) {
                    if (!TextUtils.isEmpty(comId)) {
                        commentedUserId = comId;
                    }
                    fatherCommentId = fatherId;
                    if (!TextUtils.isEmpty(petName)) {
                        et_comment.setHint("回复" + petName + ":");
                    }
                    flag = true;
                } else {
                    et_comment.setHint("说点什么吧...");
                    flag = false;
                }
                vp_chan.getRefreshableView().setSelection(cusPos+1);
                et_comment.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
            SharedPreferences sharedPreferences = getSharedPreferences("sqa_cir",
                    Activity.MODE_PRIVATE);
            String cir_news = sharedPreferences.getString("sqa_news", "");
            int cir_newsCount = sharedPreferences.getInt("sqa_NewsCount", 0);
            if (cir_newsCount == 0) {
                ll_news2.setVisibility(View.GONE);
            } else {
                ll_news2.setVisibility(View.VISIBLE);
                tv_mess2.setText(cir_newsCount + "条新消息");
                if (!TextUtils.isEmpty(cir_news)) {
                    ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(cir_news), cv_head2, options);
                    return;
                }
            }
            Bundle bundle = intent.getExtras();
            if (bundle != null && datas.size() > 0) {
                String tag = bundle.getString("tag");
                if (!TextUtils.isEmpty(tag)) {
                    if (TextUtils.equals("comTag", tag)) {
                        int cposition = bundle.getInt("ComtPosition");
                        int cNum = bundle.getInt("ComtNum");
                        datas.get(cposition).setCommentCounts(cNum);
                        ArrayList<ChannelTalkEntity> comtList = bundle.getParcelableArrayList("ComtList");
                        if (comtList != null && comtList.size() > 0) {
                            datas.get(cposition).setCommentsList(comtList);
                        }
                    } else if (TextUtils.equals("delTag", tag)) {
                        int position = bundle.getInt("DelPosition");
                        MyLogUtils.info("datas内数据====" + datas.toString() + "" + position);
                        datas.remove(position);
//                        datalist.remove(position);
                    }
                    if (adapter == null) {
                        adapter = new ChanAdapter(SquareAct.this, datas);
                        vp_chan.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.notifyChange(datas);
                    }
                }
            } else {
                page = 1;
                getChannelTalks(labelStr, page, rows);
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("sqa_cir",
                Activity.MODE_PRIVATE);
        String cir_news = sharedPreferences.getString("sqa_news", "");
        int cir_newsCount = sharedPreferences.getInt("sqa_NewsCount", 0);
        if (cir_newsCount == 0) {
            ll_news2.setVisibility(View.GONE);
        } else {
            ll_news2.setVisibility(View.VISIBLE);
            tv_mess2.setText(cir_newsCount + "条新消息");
        }
        if (!TextUtils.isEmpty(cir_news)) {
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(cir_news), cv_head2, options);
        }
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
}
