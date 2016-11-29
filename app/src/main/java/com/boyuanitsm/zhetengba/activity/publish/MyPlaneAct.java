package com.boyuanitsm.zhetengba.activity.publish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.adapter.MyPlaneAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.LoadingView;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子--我的发布界面
 * Created by xiaoke on 2016/5/6.
 */
public class MyPlaneAct extends BaseActivity {
    @ViewInject(R.id.load_view)
    private LoadingView load_view;
    private PullToRefreshListView lv_my_plane;
    private List<List<ImageInfo>> datalist;
    @ViewInject(R.id.ll_comment)
    private LinearLayout ll_comment;
    @ViewInject(R.id.et_comment)
    private EditText et_comment;
    @ViewInject(R.id.iv_chanel_comment)
    private Button bt_send;
    private List<CircleEntity> list;
    private int page=1;
    private int rows=10;
    private MyPlaneAdapter adapter;
    private int cusPos;
    private String cirId;
    private boolean flag;
    private String commentedUserId;
    private String fatherCommentId;
    private int clickPos=-1;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_my_plane);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的发布");
        lv_my_plane= (PullToRefreshListView) findViewById(R.id.lv_my_plane);
        //初始化下拉刷新
        LayoutHelperUtil.freshInit(lv_my_plane);
        getMyTalks(page, rows, "");
        lv_my_plane.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_IDLE) {
                    ImageLoader.getInstance().resume();
                } else {
                    ImageLoader.getInstance().pause();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        lv_my_plane.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lv_my_plane.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page = 1;
                getMyTalks(page, rows, "");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getMyTalks(page, rows, "");
            }
        });
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
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
                        bt_send.setEnabled(false);
                        commentCircleTalk(commentedUserId, cirId, fatherCommentId, et_comment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(MyPlaneAct.this, "请输入回复内容！");
                    }
                } else {
                    if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
                        bt_send.setEnabled(false);
                        commentCircleTalk(null, cirId, null, et_comment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(MyPlaneAct.this, "请输入评论内容！");
                    }
                }
//                if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
//                    bt_send.setEnabled(false);
//                    bt_send.setClickable(false);
//                    commentCircleTalk(null,cirId, null, et_comment.getText().toString().trim());
//                } else {
//                    MyToastUtils.showShortToast(getApplicationContext(), "请输入评论内容！");
//                }

            }
        });
        load_view.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getMyTalks(page, rows, "");
            }
        });
    }

    private List<CircleEntity> datas=new ArrayList<>();
    /**
     * 我的发布
     * @param page
     * @param rows
     */
    private void getMyTalks(final int page, int rows,String friend){
        list=new ArrayList<>();
        datalist=new ArrayList<>();

        RequestManager.getTalkManager().myTalksOut(page, rows,friend, new ResultCallback<ResultBean<DataBean<CircleEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                load_view.loadError();
                lv_my_plane.onPullUpRefreshComplete();
                lv_my_plane.onPullDownRefreshComplete();

            }

            @Override
            public void onResponse(ResultBean<DataBean<CircleEntity>> response) {
                load_view.loadComplete();
                lv_my_plane.onPullUpRefreshComplete();
                lv_my_plane.onPullDownRefreshComplete();
                list=response.getData().getRows();
                if (list.size()==0){
                    if (page==1){
                        load_view.noContent();
                    }else {
                        lv_my_plane.setHasMoreData(false);
                    }
                    return;
                }
                if (page==1){
                    datas.clear();
                }
                datas.addAll(list);
                if (adapter==null){
                    adapter=new MyPlaneAdapter(MyPlaneAct.this,datas);
                    lv_my_plane.getRefreshableView().setAdapter(adapter);
                }else {
                    adapter.notifyChange(datas);
                }
                adapter.setOnItemClickListener(new MyPlaneAdapter.OnItemClickListener2() {
                    @Override
                    public void onItemClick(View view, String id, int position) {
                        cirId = id;
                        lv_my_plane.getRefreshableView().setSelection(position);
                        cusPos = position;
                        ll_comment.setVisibility(View.VISIBLE);
                        et_comment.setHint("说点什么吧...");
                        et_comment.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        if (receiverTalk == null) {
            receiverTalk = new MyBroadCastReceiverTalk();
            registerReceiver(receiverTalk, new IntentFilter(PLANEALLTALKS));
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
    public static final String PLANEALLTALKS = "alltalk_update_myplane";


    private class MyBroadCastReceiverTalk extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String petName = intent.getStringExtra("petName");
            String fatherId = intent.getStringExtra("fatherId");
            String comId = intent.getStringExtra("comId");
            clickPos=intent.getIntExtra("clickPos",clickPos);
            if (!TextUtils.isEmpty(comId)) {
                commentedUserId = comId;
            }
            if (!TextUtils.isEmpty(fatherId)) {
                fatherCommentId = fatherId;
                if (!TextUtils.isEmpty(petName)) {
                    et_comment.setHint("回复" + petName + ":");
                }
                flag = true;
            }else {
                et_comment.setHint("说点什么吧...");
                flag=false;
            }
            ll_comment.setVisibility(View.VISIBLE);
            et_comment.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                page = 1;
//                getMyTalks(page, rows,"");
        }
    }
    /**
     * 圈子说说评论
     * @param circleTalkId
     * @param fatherCommentId
     * @param commentContent
     */
    private void commentCircleTalk(String comtedId,final String circleTalkId ,String fatherCommentId , final String commentContent){
        RequestManager.getTalkManager().commentCircleTalk(comtedId,circleTalkId, fatherCommentId, commentContent, new ResultCallback<ResultBean<CircleEntity>>() {
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
                if (flag){
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
                }else {
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
                }
                if (adapter == null) {
                    adapter = new MyPlaneAdapter(MyPlaneAct.this, datas);
                    lv_my_plane.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }
                MyToastUtils.showShortToast(getApplicationContext(), response.getMessage());
                bt_send.setEnabled(true);
                ll_comment.setVisibility(View.GONE);

            }
        });
    }
}
