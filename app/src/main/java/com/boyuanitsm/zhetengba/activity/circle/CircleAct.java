package com.boyuanitsm.zhetengba.activity.circle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.NewCircleMess;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.CircleNewMessDao;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ACache;
import com.boyuanitsm.zhetengba.utils.GsonUtils;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.LoadingView;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.util.Utils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子界面
 * Created by xiaoke on 2016/8/11.
 */
public class CircleAct extends BaseActivity implements View.OnClickListener {
    private List<List<ImageInfo>> datalist;
    private List<CircleEntity> circleEntityList;
    private PullToRefreshListView lv_cir;
    private CircleAdapter adapter;
    private int page = 1;
    private int rows = 10;
    private LinearLayout llnoList;
    private ImageView ivAnim;
    private TextView noMsg;
    private AnimationDrawable animationDrawable;
    @ViewInject(R.id.rl_more)
    private RelativeLayout rl_more;
    @ViewInject(R.id.iv_xnew)
    private TextView iv_xnew;//有新圈子消息红点
    @ViewInject(R.id.load_view)
    private LoadingView load_view;

    private TextView iv_dailognew;//dialog上面小红点
    //    private int type;
    private boolean flag;
    @ViewInject(R.id.ll_comment)
    private LinearLayout ll_comment;
    @ViewInject(R.id.et_comment)
    private EditText et_comment;
    @ViewInject(R.id.iv_chanel_comment)
    private Button bt_send;
    //    @ViewInject(R.id.ll_news)
//    private LinearLayout ll_news;
//    @ViewInject(R.id.cv_head)
//    private CircleImageView cv_head;
//    @ViewInject(R.id.tv_mess)
//    private TextView tv_mess;
    private int cusPos;
    private String cirId;

    private ACache aCache;
    private String strlist;
    private Gson gson;
    private List<CircleEntity> infos;
    String petName;
    private String fatherCommentId, commentedUserId;
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
        setContentView(R.layout.cir_frg);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子");
        lv_cir = (PullToRefreshListView) findViewById(R.id.lv_cir);
        headview = View.inflate(CircleAct.this, R.layout.cir_frg_floating, null);
        ll_news2 = (LinearLayout) headview.findViewById(R.id.ll_news);
        tv_mess2 = (TextView) headview.findViewById(R.id.tv_mess);
        cv_head2 = (CircleImageView) headview.findViewById(R.id.cv_head);
        SharedPreferences sharedPreferences = getSharedPreferences("ztb_cirNews",
                Activity.MODE_PRIVATE);
        String cir_news = sharedPreferences.getString("cir_news", "");
        int cir_newsCount = sharedPreferences.getInt("cir_NewsCount", 0);
        if (cir_newsCount == 0) {
//            ll_news.setVisibility(View.GONE);
            ll_news2.setVisibility(View.GONE);
        } else {
//            ll_news.setVisibility(View.VISIBLE);
            ll_news2.setVisibility(View.VISIBLE);
            tv_mess2.setText(cir_newsCount + "条新消息");
//            tv_mess.setText(cir_newsCount + "条新消息");
        }
        ll_news2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(CirMessAct.class);
            }
        });
        if (!TextUtils.isEmpty(cir_news)) {
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(cir_news), cv_head2, options);
        }
//        if (CircleNewMessDao.getUser() != null) {
//            NewCircleMess newCircleMess = CircleNewMessDao.getUser();
//            if (newCircleMess.isMess() == false) {
//                iv_xnew.setVisibility(View.VISIBLE);
////                ll_news.setVisibility(View.VISIBLE);
//            } else {
//                iv_xnew.setVisibility(View.GONE);
////                ll_news.setVisibility(View.GONE);
//            }
//        }
        LayoutHelperUtil.freshInit(lv_cir);
        lv_cir.getRefreshableView().addHeaderView(headview);
        //用缓存
        aCache = ACache.get(CircleAct.this);
        gson = new Gson();
        strlist = aCache.getAsString("CircleTalkList");
        infos = new ArrayList<CircleEntity>();
        if (!TextUtils.isEmpty(strlist)) {
            infos = gson.fromJson(strlist, new TypeToken<List<CircleEntity>>() {
            }.getType());
        }
        if (infos != null && infos.size() > 0) {
            load_view.loadComplete();
            if (adapter == null) {
                //设置简约listview的条目
                adapter = new CircleAdapter(CircleAct.this, infos);
                lv_cir.getRefreshableView().setAdapter(adapter);
            } else {
                adapter.notifyChange(infos);
            }
        }
        getAllCircleTalk(page, rows);//获取说说列表

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
        lv_cir.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                ll_comment.setVisibility(View.GONE);
                ZtinfoUtils.hideSoftKeyboard(getApplicationContext(), et_comment);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
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

        load_view.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getAllCircleTalk(page, rows);
            }
        });
    }

    private List<CircleEntity> datas = new ArrayList<>();

    /**
     * 获取所有圈子说说
     *
     * @param page
     * @param rows
     */
    private void getAllCircleTalk(final int page, int rows) {
        circleEntityList = new ArrayList<>();
        datalist = new ArrayList<>();
        RequestManager.getTalkManager().getAllCircleTalk(page, rows, new ResultCallback<ResultBean<DataBean<CircleEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_cir.onPullUpRefreshComplete();
                lv_cir.onPullDownRefreshComplete();
                load_view.loadError();
                if (datas.size() > 0) {
//                    if (adapter != null) {
//                        adapter.notifyChange(datalist, circleEntityList);
//                    }
                } else {

//                    llnoList.setVisibility(View.VISIBLE);
//                    ivAnim.setImageResource(R.mipmap.planeno);
//                    noMsg.setText("加载失败");
                }

            }

            @Override
            public void onResponse(ResultBean<DataBean<CircleEntity>> response) {
                lv_cir.onPullUpRefreshComplete();
                lv_cir.onPullDownRefreshComplete();
                circleEntityList = response.getData().getRows();
                if (circleEntityList.size() == 0) {
                    if (page == 1) {
                        load_view.noContent();
                    } else {
                        lv_cir.setHasMoreData(false);
                    }
                } else {
                    load_view.loadComplete();
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(circleEntityList);
                aCache.put("CircleTalkList", GsonUtils.bean2Json(datas));//实体转换成json
                if (adapter == null) {
                    adapter = new CircleAdapter(CircleAct.this, datas);
                    lv_cir.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }
                adapter.setOnItemClickListener(new CircleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, String id, int position) {
                        cirId = id;
                        lv_cir.getRefreshableView().setSelection(position);
                        cusPos = position;
                        flag = false;
                        et_comment.setHint("说点什么吧...");
                        ll_comment.setVisibility(View.VISIBLE);
                        et_comment.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });
            }
        });
    }


    @OnClick({R.id.rl_more, R.id.iv_serch, R.id.iv_chanel_comment})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_more:
//                if (CircleNewMessDao.getUser() != null) {
//                    NewCircleMess newCircleMess = CircleNewMessDao.getUser();
//                    if (newCircleMess.isMess() == false) {
//                        iv_xnew.setVisibility(View.GONE);
//                        newCircleMess.setIsMess(true);
//                        newCircleMess.setIsMain(true);
//                        newCircleMess.setIsCircle(true);
//                        CircleNewMessDao.updateMess(newCircleMess);
//                    } else {
//                        iv_xnew.setVisibility(View.GONE);
//                    }
//                } else {
//                    iv_xnew.setVisibility(View.GONE);
//                }
                showPopupWindow(rl_more);
                break;
            case R.id.iv_serch:
                openActivity(SerchCirAct.class);
                break;
            case R.id.iv_chanel_comment:
                if (flag) {
                    if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
                        bt_send.setEnabled(false);
                        commentCircleTalk(commentedUserId, cirId, fatherCommentId, et_comment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(getApplicationContext(), "请输入回复内容！");
                    }
                } else {
                    if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
                        bt_send.setEnabled(false);
                        commentCircleTalk(null, cirId, null, et_comment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(getApplicationContext(), "请输入评论内容！");
                    }
                }

                break;
//            case R.id.ll_news:
//                ll_news.setVisibility(View.GONE);
//                openActivity(CirMessAct.class);
//                break;

        }
    }

    /**
     * 圈子说说评论
     *
     * @param circleTalkId
     * @param fatherCommentId
     * @param commentContent
     */
    private void commentCircleTalk(final String commtedId, final String circleTalkId, final String fatherCommentId, final String commentContent) {
        RequestManager.getTalkManager().commentCircleTalk(commtedId, circleTalkId, fatherCommentId, commentContent, new ResultCallback<ResultBean<CircleEntity>>() {
            @Override
            public void onError(int status, String errorMsg) {
                bt_send.setEnabled(true);
            }

            @Override
            public void onResponse(ResultBean<CircleEntity> response) {
                CircleEntity entity = response.getData();
                //重新获取评论列表，刷新评论数目，关闭键盘
                et_comment.setText("");
                ZtinfoUtils.hideSoftKeyboard(getApplicationContext(), et_comment);
                //封装数据
//                if (cusPos>0){
                if (flag) {
                    if (datas.get(cusPos).getCommentsList() == null) {
                        List<CircleEntity> list = new ArrayList<CircleEntity>();
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
                        List<CircleEntity> list = new ArrayList<CircleEntity>();
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
                    adapter = new CircleAdapter(CircleAct.this, datas);
                    lv_cir.getRefreshableView().setAdapter(adapter);
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
     *
     */
    private void showPopupWindow(View parent) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.popuwindowsquzi_dialog, null);
        iv_dailognew = (TextView) layout.findViewById(R.id.iv_dailognew);
        if (CircleNewMessDao.getUser() != null) {
            NewCircleMess newCircleMess = CircleNewMessDao.getUser();
            iv_dailognew.setVisibility(View.VISIBLE);
        } else {
            iv_dailognew.setVisibility(View.GONE);
        }

        // 实例化popupWindow
        final PopupWindow popupWindow = new PopupWindow(layout, AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

//        @SuppressWarnings("deprecation")
        //获取xoff
        int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(parent, xpos, 0);

        TextView tv_myqz = (TextView) layout.findViewById(R.id.tv_myqz);
        TextView tv_xx = (TextView) layout.findViewById(R.id.tv_xx);
        TextView tv_newqz = (TextView) layout.findViewById(R.id.tv_newqz);

        tv_myqz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openActivity(CircleglAct.class);
            }
        });

        tv_xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CircleNewMessDao.getUser() != null) {
                    NewCircleMess circleMess = CircleNewMessDao.getUser();
                    if (circleMess.isMess() == false) {
                        iv_dailognew.setVisibility(View.GONE);
                    }
                    CircleNewMessDao.deleteUser();
                }
                popupWindow.dismiss();
                openActivity(CirMessAct.class);
            }
        });
        tv_newqz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openActivity(CreatCirAct.class);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("ztb_cirNews",
                Activity.MODE_PRIVATE);
        String cir_news = sharedPreferences.getString("cir_news", "");
        int cir_newsCount = sharedPreferences.getInt("cir_NewsCount", 0);
        if (cir_newsCount == 0) {
            ll_news2.setVisibility(View.GONE);
        } else {
            ll_news2.setVisibility(View.VISIBLE);
            tv_mess2.setText(cir_newsCount + "条新消息");
        }
        if (!TextUtils.isEmpty(cir_news)) {
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(cir_news), cv_head2, options);
        }
        if (receiverTalk == null) {
            receiverTalk = new MyBroadCastReceiverTalk();
            registerReceiver(receiverTalk, new IntentFilter(ALLTALKS));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiverTalk != null) {
            unregisterReceiver(receiverTalk);
            receiverTalk = null;
        }
    }

    private MyBroadCastReceiverTalk receiverTalk;
    public static final String ALLTALKS = "alltalk_update";


    private class MyBroadCastReceiverTalk extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String cir_hf = intent.getStringExtra("cir_hf");
            petName = intent.getStringExtra("petName");
            String fatherId = intent.getStringExtra("fatherId");
            String comId = intent.getStringExtra("comId");
            cirId = intent.getStringExtra("circleId");
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
                lv_cir.getRefreshableView().setSelection(cusPos);
                et_comment.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
            SharedPreferences sharedPreferences = getSharedPreferences("ztb_cirNews",
                    Activity.MODE_PRIVATE);
            String cir_news = sharedPreferences.getString("cir_news", "");
            int cir_newsCount = sharedPreferences.getInt("cir_NewsCount", 0);
            if (cir_newsCount == 0) {
//                ll_news.setVisibility(View.GONE);
                ll_news2.setVisibility(View.GONE);
            } else {
                ll_news2.setVisibility(View.VISIBLE);
//                ll_news.setVisibility(View.VISIBLE);
                tv_mess2.setText(cir_newsCount + "条新消息");
//                tv_mess.setText(cir_newsCount + "条新消息");
                if (!TextUtils.isEmpty(cir_news)) {
//                    ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(cir_news),cv_head,options);
                    ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(cir_news), cv_head2, options);
                    return;
                }
            }
//            if (CircleNewMessDao.getUser() != null) {
//                if (CircleNewMessDao.getUser().isMess() == false) {
//                    iv_xnew.setVisibility(View.VISIBLE);
//                } else {
//                    iv_xnew.setVisibility(View.GONE);
//                }
//            } else {
//                iv_xnew.setVisibility(View.GONE);
//            }
            Bundle bundle = intent.getExtras();
            if (bundle != null && datas.size() > 0) {
                String tag = bundle.getString("tag");
                if (!TextUtils.isEmpty(tag)) {
                    if (TextUtils.equals(tag, "CirComtTag")) {
                        int cposition = bundle.getInt("CirCommentPosition");
                        int cNum = bundle.getInt("CirComtNum");
                        datas.get(cposition).setCommentCounts(cNum);
                        ArrayList<CircleEntity> comtList = bundle.getParcelableArrayList("CirComtList");
                        if (comtList != null && comtList.size() > 0) {
                            datas.get(cposition).setCommentsList(comtList);
                        }
                    } else if (TextUtils.equals(tag, "CirdelTag")) {
                        int position = bundle.getInt("CirDelPosition");
                        datas.remove(position);
                    }
                    if (adapter == null) {
                        adapter = new CircleAdapter(CircleAct.this, datas);
                        lv_cir.getRefreshableView().setAdapter(adapter);
                    } else {
                        adapter.notifyChange(datas);
                    }
                }

            } else {
                page = 1;
                getAllCircleTalk(page, rows);
            }

        }
    }

}
