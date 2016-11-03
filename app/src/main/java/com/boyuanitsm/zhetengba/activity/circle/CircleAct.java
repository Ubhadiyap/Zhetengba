package com.boyuanitsm.zhetengba.activity.circle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
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
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.LoadingView;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

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
    private  TextView noMsg;
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
    private int cusPos;
    private String cirId;
    @Override
    public void setLayout() {
        setContentView(R.layout.cir_frg);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子");
        getAllCircleTalk(page, rows);
        lv_cir = (PullToRefreshListView) findViewById(R.id.lv_cir);
//        llnoList = (LinearLayout) findViewById(R.id.noList);
//        ivAnim = (ImageView) findViewById(R.id.ivAnim);
//        noMsg = (TextView) findViewById(R.id.noMsg);
//        initData();
//        datalist=new ArrayList<>();
        if (CircleNewMessDao.getUser()!=null){
            NewCircleMess newCircleMess = CircleNewMessDao.getUser();
            if (newCircleMess.isMess()==false){
                iv_xnew.setVisibility(View.VISIBLE);
            }else {
                iv_xnew.setVisibility(View.GONE);
            }
        }
        LayoutHelperUtil.freshInit(lv_cir);
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
                if (datas.size()>0){
//                    if (adapter != null) {
//                        adapter.notifyChange(datalist, circleEntityList);
//                    }
                }else {

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
//                        llnoList.setVisibility(View.VISIBLE);
//                        ivAnim.setImageResource(R.mipmap.planeno);
//                        noMsg.setText("暂无内容");
                        load_view.noContent();
                    } else {
                        lv_cir.setHasMoreData(false);
                    }
//                    return;
                } else {
//                    llnoList.setVisibility(View.GONE);
                    load_view.loadComplete();
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(circleEntityList);
//                for (int j = 0; j < datas.size(); j++) {
//                    final List<ImageInfo> itemList = new ArrayList<>();
//                    //将图片地址转化成数组
//                    if (!TextUtils.isEmpty(datas.get(j).getTalkImage())) {
//                        final String[] urlList = ZtinfoUtils.convertStrToArray(datas.get(j).getTalkImage());
//                        for (int i = 0; i < urlList.length; i++) {
//                            itemList.add(new ImageInfo(urlList[i], 120, 120));
//                        }
//                    }
//                    datalist.add(itemList);
//                }
                if (adapter == null) {
                    adapter = new CircleAdapter(CircleAct.this, datas);
                    lv_cir.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange( datas);
                }
                adapter.setOnItemClickListener(new CircleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, String id, int position) {
                        cirId = id;
                        lv_cir.getRefreshableView().setSelection(position);
                        cusPos = position;
                        ll_comment.setVisibility(View.VISIBLE);
                        et_comment.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });
            }
        });
    }


    @OnClick({R.id.rl_more,R.id.iv_serch,R.id.iv_chanel_comment})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_more:
                if (CircleNewMessDao.getUser()!=null){
                    NewCircleMess newCircleMess = CircleNewMessDao.getUser();
                    if (newCircleMess.isMess()==false){
                        iv_xnew.setVisibility(View.GONE);
                        newCircleMess.setIsMess(true);
                        newCircleMess.setIsMain(true);
                        newCircleMess.setIsCircle(true);
                        CircleNewMessDao.updateMess(newCircleMess);
                    }else {
                        iv_xnew.setVisibility(View.GONE);
                    }
                }else {
                    iv_xnew.setVisibility(View.GONE);
                }
                showPopupWindow(rl_more);
                break;
            case R.id.iv_serch:
                openActivity(SerchCirAct.class);
                break;
            case R.id.iv_chanel_comment:
                if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
                    bt_send.setEnabled(false);
                    bt_send.setClickable(false);
                    commentCircleTalk(cirId, null, et_comment.getText().toString().trim());
                } else {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入评论内容！");
                }
                break;
        }
    }

    /**
     * 圈子说说评论
     * @param circleTalkId
     * @param fatherCommentId
     * @param commentContent
     */
    private void commentCircleTalk(final String circleTalkId ,String fatherCommentId , final String commentContent){
        RequestManager.getTalkManager().commentCircleTalk(circleTalkId, fatherCommentId, commentContent, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                bt_send.setEnabled(true);
                bt_send.setClickable(true);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                //重新获取评论列表，刷新评论数目，关闭键盘
                ZtinfoUtils.hideSoftKeyboard(getApplicationContext(), et_comment);
                et_comment.setText("");
                //封装数据
                CircleEntity entity = new CircleEntity();
                entity.setPetName(UserInfoDao.getUser().getPetName());
                entity.setCommentContent(commentContent);
                if (datas.get(cusPos).getCommentsList() == null) {
                    List<CircleEntity> list = new ArrayList<CircleEntity>();
                    datas.get(cusPos).setCommentsList(list);
                    datas.get(cusPos).getCommentsList().add(entity);
                }else {
                    datas.get(cusPos).getCommentsList().add(entity);
                }
                datas.get(cusPos).setCommentsList(datas.get(cusPos).getCommentsList());
                if (!TextUtils.isEmpty(datas.get(cusPos).getCommentCounts()+"")){
                    datas.get(cusPos).setCommentCounts(datas.get(cusPos).getCommentCounts() + 1);
                }else {
                    datas.get(cusPos).setCommentCounts(1);
                }
                if (adapter == null) {
                    adapter = new CircleAdapter(CircleAct.this, datas);
                    lv_cir.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }
                MyToastUtils.showShortToast(getApplicationContext(), response.getMessage());
                bt_send.setEnabled(true);
                bt_send.setClickable(true);
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
        iv_dailognew= (TextView) layout.findViewById(R.id.iv_dailognew);
        if(CircleNewMessDao.getUser()!=null){
            NewCircleMess newCircleMess = CircleNewMessDao.getUser();
                iv_dailognew.setVisibility(View.VISIBLE);
        }else{
            iv_dailognew.setVisibility(View.GONE);
        }

        // 实例化popupWindow
        final PopupWindow popupWindow = new PopupWindow(layout, AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager)getSystemService(WINDOW_SERVICE);

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
                if (CircleNewMessDao.getUser()!=null){
                    NewCircleMess circleMess = CircleNewMessDao.getUser();
                    if (circleMess.isMess()==false) {
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
            if(CircleNewMessDao.getUser()!=null){
                if (CircleNewMessDao.getUser().isMess()==false){
                    iv_xnew.setVisibility(View.VISIBLE);
                }else {
                    iv_xnew.setVisibility(View.GONE);
                }
            }else {
                iv_xnew.setVisibility(View.GONE);
            }
            Bundle bundle = intent.getExtras();
            if (bundle != null && datas.size() > 0) {
                String tag=bundle.getString("tag");
                if (!TextUtils.isEmpty(tag)){
                    if (TextUtils.equals(tag,"CirComtTag")){
                        int cposition = bundle.getInt("CirCommentPosition");
                        int cNum = bundle.getInt("CirComtNum");
                        datas.get(cposition).setCommentCounts(cNum);
                        ArrayList<CircleEntity> comtList = bundle.getParcelableArrayList("CirComtList");
                        if (comtList!=null&&comtList.size()>0){
                            datas.get(cposition).setCommentsList(comtList);
                        }
                    }else if (TextUtils.equals(tag,"CirdelTag")){
                        int position= bundle.getInt("CirDelPosition");
                        datas.remove(position);
                        datalist.remove(position);
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
