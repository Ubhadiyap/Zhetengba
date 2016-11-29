package com.boyuanitsm.zhetengba.activity.circle;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.AppManager;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.ShareDialogAct;
import com.boyuanitsm.zhetengba.activity.mine.AssignScanAct;
import com.boyuanitsm.zhetengba.adapter.CirclexqListAdapter;
import com.boyuanitsm.zhetengba.adapter.CirxqAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.MemberEntity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.LoadingView;
import com.boyuanitsm.zhetengba.view.MyRecyleview;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子详情界面
 * Created by bitch-1 on 2016/5/11.
 */
public class CirxqAct extends BaseActivity {
    private MyRecyleview rv_label;
    @ViewInject(R.id.lv_cirxq)
    private PullToRefreshListView lv_cir;
    @ViewInject(R.id.cir_fb)
    private TextView cir_fb;
    @ViewInject(R.id.iv_fa)
    private ImageView iv_fa;
    private List<Integer>list;
    private CirxqAdapter adapter;
    private String circleId;//圈子id
    private CircleEntity circleEntity;//圈子实体
    private List<MemberEntity> userList;//圈子成员集合
    private List<CircleEntity> circleEntityList;//该圈子说说列表
    private String personIds;//存储邀请用户id
    private int IsInCircle=3;//从收索圈子进来后的判断条件看是否在圈子里面 0不在，1在；
    private int type;//类型
    private CircleImageView head;//头像
    private TextView name;//圈主名
    private TextView notice;//公告
    private TextView qzzl;//圈子资料
    private TextView rl_jiaru;
    private Intent intenttype;
    private int page=1;
    private int rows=10;
    private List<List<ImageInfo>> datalist;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    private View headView;
    private CirclexqListAdapter xqAdapter;
    private ProgressDialog progressDialog;
    private boolean isQuanzhu=false;
    private boolean isFabu=false;
    private LinearLayout ll_member;
    @ViewInject(R.id.ll_comment)
    private LinearLayout ll_comment;
    @ViewInject(R.id.et_comment)
    private EditText et_comment;
    @ViewInject(R.id.iv_chanel_comment)
    private Button bt_send;
    @ViewInject(R.id.load_view)
    private LoadingView load_view;
    @ViewInject(R.id.rl_share)
    private RelativeLayout rl_share;//分享按钮
    private int cusPos;
    private String cirId;
    private String cirname;//用来分享的时候分享的theme
    private String petName;//昵称
    private boolean flag;
    private String commentedUserId;
    private String fatherCommentId;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_cirxq);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("");
//        progressDialog=new ProgressDialog(CirxqAct.this);
//        progressDialog.setMessage("数据加载中...");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        headView=getLayoutInflater().inflate(R.layout.xq_head,null);
        head= (CircleImageView) headView.findViewById(R.id.head);//头像
        name= (TextView) headView.findViewById(R.id.tv_qz);//圈主名
        notice= (TextView) headView.findViewById(R.id.notice);//公告
        qzzl= (TextView) headView.findViewById(R.id.tv_qzzl);//圈子资料
        rv_label= (MyRecyleview) headView.findViewById(R.id.rv_label);//圈子成员
        rl_jiaru=(TextView) headView.findViewById(R.id.rl_jiaru);//加入圈子 默认是隐藏的
        ll_member= (LinearLayout) headView.findViewById(R.id.ll_member);
        lv_cir.getRefreshableView().addHeaderView(headView);
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
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
//                    bt_send.setEnabled(false);
//                    bt_send.setClickable(false);
//                    commentCircleTalk(null,cirId, null, et_comment.getText().toString().trim());
//                } else {
//                    MyToastUtils.showShortToast(getApplicationContext(), "请输入评论内容！");
//                }
                if (flag){
                    if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
                        bt_send.setEnabled(false);
                        commentCircleTalk(commentedUserId,cirId, fatherCommentId, et_comment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(getApplicationContext(), "请输入回复内容！");
                    }
                }else {
                    if (!TextUtils.isEmpty(et_comment.getText().toString().trim())) {
                        bt_send.setEnabled(false);
                        commentCircleTalk(null,cirId, null, et_comment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(getApplicationContext(), "请输入评论内容！");
                    }
                }
            }
        });

        lv_cir.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isFabu){
                    if (scrollState == SCROLL_STATE_FLING || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
//                        iv_fa.setVisibility(View.GONE);
                        ll_comment.setVisibility(View.GONE);
                        ZtinfoUtils.hideSoftKeyboard(getApplicationContext(), et_comment);
                    } else {
//                        iv_fa.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CirxqAct.this);
        //设置横向
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_label.setLayoutManager(linearLayoutManager);
        intenttype=getIntent();
        type=intenttype.getExtras().getInt("type");//3扫码
        circleId=intenttype.getExtras().getString("circleId");
        difftype();//不同情况下的圈子详情的不同情况
        iv_fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CirxqAct.this, CirclefbAct.class);
                intent.putExtra("isShow", false);
                intent.putExtra("circleId", circleId);
                intent.putExtra(CirclefbAct.TYPE,1);
                startActivity(intent);
            }
        });
        //圈子资料
        qzzl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CirxqAct.this, CirmationAct.class);
                intent.putExtra("circleEntity", circleEntity);
                startActivity(intent);
            }
        });

        load_view.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                difftype();
            }
        });
    }

    /**
     * 不同情况下进入到圈子详情的不同情况
     */
    private void difftype() {
        if(type==0){
            //从收索里面进来(需要判断是否在圈子里面的)
            IsInCircle=intenttype.getExtras().getInt("isincircle");
            if(IsInCircle==0){
                //不在圈子里面
                rl_share.setVisibility(View.GONE);
                iv_fa.setVisibility(View.GONE);
                isFabu=false;
                rl_jiaru.setVisibility(View.VISIBLE);//申请加入按钮可见
//                qzzl.setEnabled(false);//圈子资料不可点击
                getCircleDetail(circleId);
                getCircleMembers(circleId,0);
                xqAdapter=new CirclexqListAdapter(CirxqAct.this,datas);
                lv_cir.getRefreshableView().setAdapter(xqAdapter);
                isFresh(false);
                rl_jiaru.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rl_jiaru.setEnabled(false);
                        joInCircle(circleId);
                    }
                });
            }else if(IsInCircle==1){
                //在圈子里面
                rl_share.setVisibility(View.VISIBLE);
                iv_fa.setVisibility(View.VISIBLE);
                isFabu=true;
                rl_jiaru.setVisibility(View.GONE);
//                qzzl.setEnabled(true);
                getCircleDetail(circleId);
                getCircleMembers(circleId, 1);
                getThisCircleTalks(circleId, page, rows);
                isFresh(true);
            }

        } else if(type==1){
            //从圈子管理进来,或者从子圈子frg进来（已经在圈子里面的）
            rl_share.setVisibility(View.VISIBLE);
            iv_fa.setVisibility(View.VISIBLE);
            isFabu=true;
//            qzzl.setEnabled(true);
            getCircleDetail(circleId);
            getCircleMembers(circleId, 1);
            getThisCircleTalks(circleId, page, rows);
            isFresh(true);
        } else if (type==3){
            getCircleDetail(circleId);//获取圈子详情，不在圈子里显示立即加入按钮
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
                bt_send.setClickable(true);
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
//                }
                }
//
//                CircleEntity entity = new CircleEntity();
//                entity.setPetName(UserInfoDao.getUser().getPetName());
//                entity.setCommentContent(commentContent);
//                if (datas.get(cusPos).getCommentsList() == null) {
//                    List<CircleEntity> list = new ArrayList<CircleEntity>();
//                    datas.get(cusPos).setCommentsList(list);
//                    datas.get(cusPos).getCommentsList().add(entity);
//                } else {
//                    datas.get(cusPos).getCommentsList().add(entity);
//                }
//                datas.get(cusPos).setCommentsList(datas.get(cusPos).getCommentsList());
//                if (!TextUtils.isEmpty(datas.get(cusPos).getCommentCounts() + "")) {
//                    datas.get(cusPos).setCommentCounts(datas.get(cusPos).getCommentCounts() + 1);
//                } else {
//                    datas.get(cusPos).setCommentCounts(1);
//                }
                if (xqAdapter == null) {
                    xqAdapter = new CirclexqListAdapter(CirxqAct.this,datas);
                    lv_cir.getRefreshableView().setAdapter(xqAdapter);
                } else {
                    xqAdapter.notifyChange( datas);
                }
                MyToastUtils.showShortToast(getApplicationContext(), response.getMessage());
                bt_send.setEnabled(true);
                bt_send.setClickable(true);
                ll_comment.setVisibility(View.GONE);

            }
        });
    }

    /**当申请加入圈子按钮可见时点击掉接口
     * @param circleId
     */
    private void joInCircle(String circleId) {
        RequestManager.getTalkManager().sendRequestJoinCircle(circleId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                rl_jiaru.setEnabled(true);
                MyToastUtils.showShortToast(CirxqAct.this, errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                rl_jiaru.setEnabled(true);
                MyToastUtils.showShortToast(CirxqAct.this, "申请成功，等待圈主响应");
                sendBroadcast(new Intent(SerchCirAct.CIRSERCH));
                finish();


            }
        });
    }

    /**此方法是判断如果不在圈子里下拉时不刷新圈子说说
     * 在圈子里面下拉刷新圈子说说
     *
     * @param isin
     */
    private void isFresh( boolean isin) {
        if(isin==false) {
            //不在圈子里面看不到圈子说说，所以下拉时不刷新说说
            lv_cir.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    lv_cir.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                    lv_cir.onPullUpRefreshComplete();
                    lv_cir.onPullDownRefreshComplete();
                    iv_fa.setVisibility(View.GONE);
                    isFabu=false;
                    lv_cir.setScrollLoadEnabled(false);
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    lv_cir.onPullUpRefreshComplete();
                    lv_cir.onPullDownRefreshComplete();
                    iv_fa.setVisibility(View.GONE);
                    isFabu=false;
                }
            });
        }else {
            //在圈子里面
            lv_cir.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    lv_cir.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                    page=1;
                    getCircleMembers(circleId, 1);
                    getThisCircleTalks(circleId, page, rows);
                    iv_fa.setVisibility(View.VISIBLE);
                    isFabu=true;
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    page++;
                    getCircleMembers(circleId, 1);
                    getThisCircleTalks(circleId,page,rows);
                }
            });

        }
    }


    //获取圈子详情
    private void getCircleDetail(String circleId){
        circleEntity=new CircleEntity();
        RequestManager.getTalkManager().myCircleDetail(circleId, new ResultCallback<ResultBean<CircleEntity>>() {
            @Override
            public void onError(int status, String errorMsg) {
                load_view.loadError();

            }

            @Override
            public void onResponse(ResultBean<CircleEntity> response) {
//                if (progressDialog.isShowing()){
//                    progressDialog.dismiss();
//                }
                load_view.loadComplete();
                circleEntity = response.getData();
                if (circleEntity != null) {
                    cirname = circleEntity.getCircleName().toString();
                    setCircle(circleEntity);
                }
            }
        });
    }

    //设置实体类
    private void setCircle(CircleEntity entity){
        if(entity!=null){
            setTopTitle(entity.getCircleName());
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(entity.getCircleLogo()),head,options);
            if(!TextUtils.isEmpty(entity.getUserName())){
                name.setText("圈主：" + entity.getUserName());
            }else if (!TextUtils.isEmpty(entity.getCircleOwnerId())){
                String str=entity.getCircleOwnerId();
                name.setText("圈主："+str.substring(0,3)+"***"+str.substring(str.length()-3,str.length()));
            }else {
                name.setText("圈主：");
            }
            if(!TextUtils.isEmpty(entity.getNotice())){
                notice.setText(entity.getNotice());
            }else {
                notice.setText("暂无公告");
            }
            if (!TextUtils.isEmpty(entity.getAddress())){
                ll_member.setVisibility(View.GONE);
            }else {
                ll_member.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(entity.getCircleOwnerId())){
                if(entity.getCircleOwnerId().equals(UserInfoDao.getUser().getId())){
                    isQuanzhu=true;
                }else {
                    isQuanzhu=false;
                }
            }
            if (type==3) {
                if (!TextUtils.isEmpty(entity.getIsInCircle() + "")) {//0不在圈子
                    if (entity.getIsInCircle() == 0) {
                        IsInCircle = 0;
                    } else if (entity.getIsInCircle() == 1) {
                        IsInCircle = 1;
                    }
                }

                if(IsInCircle==0){
                    //不在圈子里面
                    iv_fa.setVisibility(View.GONE);
                    isFabu=false;
                    rl_jiaru.setVisibility(View.VISIBLE);//申请加入按钮可见
//                    qzzl.setEnabled(false);
                    getCircleMembers(circleId, 0);
                    xqAdapter=new CirclexqListAdapter(CirxqAct.this,datas);
                    lv_cir.getRefreshableView().setAdapter(xqAdapter);
                    isFresh(false);
                    rl_jiaru.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            joInCircle(circleId);
                        }
                    });
                }else if(IsInCircle==1){
                    //在圈子里面
//                    qzzl.setEnabled(true);
                    iv_fa.setVisibility(View.VISIBLE);
                    isFabu=true;
                    rl_jiaru.setVisibility(View.GONE);
                    getCircleMembers(circleId, 1);
                    getThisCircleTalks(circleId, page, rows);
                    isFresh(true);
                }
            }
        }
    }

    //获取圈子人员
    private void getCircleMembers(final String circleId, final int isInCircle){
        userList=new ArrayList<>();
        RequestManager.getTalkManager().myCircleMember(circleId, 1, 10, new ResultCallback<ResultBean<DataBean<MemberEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<DataBean<MemberEntity>> response) {
//                if (progressDialog.isShowing()){
//                    progressDialog.dismiss();
//                }
                userList = response.getData().getRows();
                adapter = new CirxqAdapter(CirxqAct.this, userList, isInCircle);
                rv_label.setAdapter(adapter);
                if (isInCircle != 0) {
                    isFabu = true;
                    iv_fa.setVisibility(View.VISIBLE);
                    adapter.setOnItemClickListener(new CirxqAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
//                            if (isInCircle == 0) {
//                                MyToastUtils.showShortToast(CirxqAct.this, "未在圈子里，无法查看，请申请加入！");
//                                return;
//                            }
                            if (userList.size() <= 4) {
                                if (position == userList.size()) {
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    String str3 = "circleFriend";
                                    bundle.putString("can", str3);
                                    bundle.putString("circleId", circleId);
                                    intent.putExtras(bundle);
                                    intent.setClass(CirxqAct.this, AssignScanAct.class);
                                    startActivityForResult(intent, 6);
                                } else {
                                    if (position == (userList.size() + 1)) {
                                        Intent intent = new Intent(CirxqAct.this, CircleppAct.class);
                                        intent.putExtra("circleId", circleId);
                                        intent.putExtra("isQuanzhu", isQuanzhu);
                                        startActivity(intent);
                                    }
                                }
                            } else {
                                if (position == 5) {
                                    Intent intent = new Intent(CirxqAct.this, CircleppAct.class);
                                    intent.putExtra("circleId", circleId);
                                    intent.putExtra("isQuanzhu", isQuanzhu);
                                    startActivity(intent);
                                } else if (position == 4) {
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    String str3 = "circleFriend";
                                    bundle.putString("can", str3);
                                    bundle.putString("circleId", circleId);
                                    intent.putExtras(bundle);
                                    intent.setClass(CirxqAct.this, AssignScanAct.class);
                                    startActivityForResult(intent, 6);
                                }
                            }
                        }
                    });
                }
            }
        });

    }

    @OnClick({R.id.rl_share})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_share:
                Intent intent=new Intent();
                intent.setClass(CirxqAct.this, ShareDialogAct.class);
                intent.putExtra("type", 5);
                intent.putExtra("cirname", cirname);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(!TextUtils.isEmpty(cirname)){
                    intent.putExtra("cirname", cirname);
                }else {
                    intent.putExtra("cirname", "折腾吧");
                }
                startActivity(intent);
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null&&resultCode==6){
            Bundle bundle=data.getBundleExtra("bundle3");
            personIds= bundle.getString("bundleIds");
            if (!TextUtils.isEmpty(personIds)){
                inviteFriendToCircle(circleId,personIds);
            }
        }
    }

    private List<CircleEntity> datas=new ArrayList<>();

    //获取该圈子所有说说列表
    private void getThisCircleTalks(String circleId, final int page, int rows){
        circleEntityList=new ArrayList<CircleEntity>();
        datalist = new ArrayList<>();
        RequestManager.getTalkManager().getSingleCircleAllTalks(circleId, page, rows, new ResultCallback<ResultBean<DataBean<CircleEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_cir.onPullUpRefreshComplete();
                lv_cir.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<DataBean<CircleEntity>> response) {
//                if (progressDialog.isShowing()){
//                    progressDialog.dismiss();
//                }
                lv_cir.onPullUpRefreshComplete();
                lv_cir.onPullDownRefreshComplete();
                circleEntityList = response.getData().getRows();
                if (circleEntityList.size() == 0) {
                    if (page == 1) {

                    } else {
                        lv_cir.setHasMoreData(false);
                    }
                }
                if(page==1){
                    datas.clear();
                }
                datas.addAll(circleEntityList);
                if(xqAdapter==null) {
                    xqAdapter=new CirclexqListAdapter(CirxqAct.this,datas);
                    lv_cir.getRefreshableView().setAdapter(xqAdapter);
                }else {
                    xqAdapter.notifyChange(datas);
                }
                xqAdapter.setOnItemClickListener(new CirclexqListAdapter.OnItemClickListener2() {
                    @Override
                    public void onItemClick(View view, String id, int position) {
                        cirId = id;
                        lv_cir.getRefreshableView().setSelection(position);
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


    /**
     * 邀请好友加入圈子接口
     * @param circleId
     * @param personIds
     */
    private void inviteFriendToCircle(String circleId,String personIds){
        RequestManager.getTalkManager().inviteFriendToCircle(circleId, personIds, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (receiver==null){
            receiver=new MyBroadCastReceiver();
            registerReceiver(receiver,new IntentFilter(MEMBERXQ));
        }
        if (receiverTalk==null){
            receiverTalk=new MyBroadCastReceiverTalk();
            registerReceiver(receiverTalk,new IntentFilter(TALKS));
        }
        if (receiverDetail==null){
            receiverDetail=new MyBroadCastReceiverDetail();
            registerReceiver(receiverDetail,new IntentFilter(DETAIL));
        }
        if (receiverFinish==null){
            receiverFinish=new MyBroadCastReceiverFinish();
            registerReceiver(receiverFinish,new IntentFilter(FINISH));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
        if(receiverTalk!=null){
            unregisterReceiver(receiverTalk);
            receiverTalk=null;
        }
        if(receiverDetail!=null){
            unregisterReceiver(receiverDetail);
            receiverDetail=null;
        }
        if (receiverFinish!=null){
            unregisterReceiver(receiverFinish);
            receiverFinish=null;
        }
    }

    private MyBroadCastReceiver receiver;
    public static final String MEMBERXQ="xqmember_update";
    private class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getCircleMembers(circleId, IsInCircle);
        }
    }

    private MyBroadCastReceiverTalk receiverTalk;
    public static final String TALKS="talk_update";
    private class MyBroadCastReceiverTalk extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String cir_hf = intent.getStringExtra("cir_hf");
            petName = intent.getStringExtra("petName");
            String fatherId = intent.getStringExtra("fatherId");
            String comId = intent.getStringExtra("comId");
            cirId=intent.getStringExtra("circleId");
            cusPos=intent.getIntExtra("clickPos", cusPos);
            if (TextUtils.equals("cir_hf",cir_hf)) {
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
                }else {
                    et_comment.setHint("说点什么吧...");
                    flag=false;
                }
                lv_cir.getRefreshableView().setSelection(cusPos);
                et_comment.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                return;
            }
            page=1;
            getThisCircleTalks(circleId, page, rows);
        }
    }

    private MyBroadCastReceiverDetail receiverDetail;
    public static final String DETAIL="detail_update";
    private class MyBroadCastReceiverDetail extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getCircleDetail(circleId);
        }
    }

    private MyBroadCastReceiverFinish receiverFinish;
    public static final String FINISH="finish_update";
    private class MyBroadCastReceiverFinish extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }
}

