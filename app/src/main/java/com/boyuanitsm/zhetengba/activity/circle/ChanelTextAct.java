package com.boyuanitsm.zhetengba.activity.circle;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.publish.MyPlaneAct;
import com.boyuanitsm.zhetengba.adapter.ChaTextAdapter;
import com.boyuanitsm.zhetengba.adapter.CircleTextAdapter;
import com.boyuanitsm.zhetengba.adapter.PicGdAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.LoadingView;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.view.PicShowDialog;
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
 * 频道正文
 * Created by xiaoke on 2016/5/11.
 */
public class ChanelTextAct extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.et_comment)
    private EditText etComment;//评论内容
    @ViewInject(R.id.my_lv)
    private PullToRefreshListView my_lv;
    @ViewInject(R.id.iv_chanel_comment)
    private Button btnSend;
    private LinearLayout ll_two;
    private LinearLayout llphoto;
    private CustomImageView ng_one_image, iv_two_one, iv_two_two, iv_two_three, iv_two_four;
    private MyGridView iv_ch_image;
    private List<List<ImageInfo>> dataList;
    private String comNum;
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.tum)
            .showImageOnLoading(R.mipmap.tum)
            .showImageOnFail(R.mipmap.tum).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    private DisplayImageOptions optionshead = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    private String channelId;//频道说说id
    private ChannelTalkEntity channelTalkEntity;//频道说说实体
    private CircleImageView head;//头像
    private TextView name;//姓名
    private ImageView sex;//性别
    private TextView time;//时间
    private TextView content;//说说内容
    private TextView commentNum;//评论数
    private View headView;
    private int page=1;
    private int rows=10;
    private List<ChannelTalkEntity> list;
    private int position;
    ChaTextAdapter adapter;
    @ViewInject(R.id.load_view)
    private LoadingView load_view;

    private TextView cnum2, cnumText, znum2, znumText;
    private LinearLayout ll_zan, ll_cmt;
    private ImageView iv_zan;
    private LinearLayout ll_headbg2, ll_head2;
    private int clickPos = -1;//评论点击位置
    private String fatherCommentId,commentedUserId;
    private boolean flag;
    private String chanelComtNum;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_chanel_text);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("");
        headView=getLayoutInflater().inflate(R.layout.hannel_headerview,null);
        assignView(headView);
//        channelTalkEntity=getIntent().getParcelableExtra("channelEntity");
        channelId=getIntent().getStringExtra("channelId");
        position= getIntent().getIntExtra("CommentPosition", 0);
        my_lv.setPullRefreshEnabled(true);//下拉刷新
        my_lv.setScrollLoadEnabled(true);//滑动加载
        my_lv.setPullLoadEnabled(false);//上拉刷新
        my_lv.setHasMoreData(true);//是否有更多数据
        my_lv.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        my_lv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        my_lv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
        my_lv.getRefreshableView().setDivider(null);
        my_lv.getRefreshableView().addHeaderView(headView);
        if (!TextUtils.isEmpty(channelId)) {
            getChanelTalk(channelId);
            getCircleCommentsList(channelId, page, rows);
        }
        load_view.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getCircleCommentsList(channelId, page, rows);
            }
        });

        my_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                my_lv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page = 1;
                getCircleCommentsList(channelId, page, rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getCircleCommentsList(channelId, page, rows);
            }
        });

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    btnSend.setBackgroundResource(R.drawable.main_btn_nor);
                    btnSend.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    btnSend.setBackgroundColor(Color.parseColor("#f4f4f4"));
                    btnSend.setTextColor(Color.parseColor("#999999"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ll_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_zan.setEnabled(false);
                if (0 == channelTalkEntity.getLiked()) {
                    addChannelLike(channelId);
                } else {//if (1 == list.get(clickPos).getLiked())
                    removeChannelLike(channelId);
                }
            }
        });
        ll_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=false;
                etComment.setHint("说点什么吧...");
                etComment.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }



    private void setChannel(ChannelTalkEntity entity){
        if(entity!=null){
            if (!TextUtils.isEmpty(entity.getUserIcon())){
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(entity.getUserIcon()), head, optionshead);
            }
            if(!TextUtils.isEmpty(entity.getUserName())){
                name.setText(entity.getUserName());
            }else {
                String str=entity.getCreatePersonId();
                name.setText(str.substring(0,3)+"***"+str.substring(str.length()-3,str.length()));
            }
            if(!TextUtils.isEmpty(entity.getUserSex())){
                if (entity.getUserSex().equals(1+"")){
                    sex.setImageResource(R.drawable.male);
                }else if (entity.getUserSex().equals(0+"")){
                    sex.setImageResource(R.drawable.female);
                }
            }
            if(!TextUtils.isEmpty(entity.getCreateTiem())){
                time.setText(ZtinfoUtils.timeChange(Long.parseLong(entity.getCreateTiem())));
            }
            if(!TextUtils.isEmpty(entity.getChannelContent())){
                content.setVisibility(View.VISIBLE);
                content.setText(entity.getChannelContent());
            }else {
                content.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(entity.getCommentCounts()+"")){
                commentNum.setText("评论"+entity.getCommentCounts());
            }
            if(!TextUtils.isEmpty(entity.getChannelImage())){
                initDate(entity);
            }
            if (!TextUtils.isEmpty(entity.getLiked() + "")) {
                if (0 == entity.getLiked()) {//未点赞
                    iv_zan.setImageResource(R.mipmap.zan2x);
                } else {//if (1 == list.get(clickPos).getLiked())
                    iv_zan.setImageResource(R.mipmap.zanx);
                }
            }
            if (!TextUtils.isEmpty(entity.getLikeCounts() + "")) {
                if (entity.getLikeCounts() == 0) {
                    znum2.setVisibility(View.GONE);
                    znumText.setVisibility(View.GONE);
                } else {
                    znum2.setVisibility(View.VISIBLE);
                    znumText.setVisibility(View.VISIBLE);
                    znum2.setText(entity.getLikeCounts() + "");
                }
            }
            if (!TextUtils.isEmpty(entity.getCommentCounts() + "")) {
                if (entity.getCommentCounts() == 0) {
                    cnum2.setVisibility(View.GONE);
                    cnumText.setVisibility(View.GONE);
                } else if (entity.getCommentCounts() > 0) {
                    cnum2.setVisibility(View.VISIBLE);
                    cnumText.setVisibility(View.VISIBLE);
                    cnum2.setText(entity.getCommentCounts() + "");
                }
            } else {
                cnum2.setVisibility(View.GONE);
                cnumText.setVisibility(View.GONE);
            }
        }
    }

    private void assignView(View view) {
        head= (CircleImageView) view.findViewById(R.id.iv_ch_head);//头像
        name= (TextView) view.findViewById(R.id.tv_ch_niName);//姓名
        sex= (ImageView) view.findViewById(R.id.iv_ch_gendar);//性别
        time= (TextView) view.findViewById(R.id.tv_time);//时间
        content= (TextView) view.findViewById(R.id.content);//说说内容
        commentNum= (TextView) view.findViewById(R.id.commentNum);//评论数
        iv_ch_image = (MyGridView) view.findViewById(R.id.iv_ch_image);
        ng_one_image = (CustomImageView) view.findViewById(R.id.ng_one_image);
        ll_two = (LinearLayout) view.findViewById(R.id.ll_two);
        iv_two_one = (CustomImageView) view.findViewById(R.id.iv_two_one);
        iv_two_two = (CustomImageView) view.findViewById(R.id.iv_two_two);
        iv_two_three = (CustomImageView) view.findViewById(R.id.iv_two_three);
        iv_two_four = (CustomImageView) view.findViewById(R.id.iv_two_four);
        llphoto= (LinearLayout) view.findViewById(R.id.llphoto);
        znum2 = (TextView) view.findViewById(R.id.znum2);
        cnum2 = (TextView) view.findViewById(R.id.cnum2);
        cnumText = (TextView) view.findViewById(R.id.cnumText);
        znumText = (TextView) view.findViewById(R.id.znumText);
        ll_zan = (LinearLayout) view.findViewById(R.id.ll_zan);
        ll_cmt = (LinearLayout) view.findViewById(R.id.ll_cmt);
        iv_zan = (ImageView) view.findViewById(R.id.iv_zan);
        ll_head2 = (LinearLayout) view.findViewById(R.id.ll_head2);
        ll_headbg2 = (LinearLayout) view.findViewById(R.id.headbg2);
    }

    private void initDate(ChannelTalkEntity channelTalkEntity) {
        dataList = new ArrayList<>();
//        final List<ImageInfo> singleList=new ArrayList<>();
            //将图片地址转化成数组
        if(!TextUtils.isEmpty(channelTalkEntity.getChannelImage())) {
            llphoto.setVisibility(View.VISIBLE);
            final String[] urlList = ZtinfoUtils.convertStrToArray(channelTalkEntity.getChannelImage());
            if (urlList.length== 1) {
                ll_two.setVisibility(View.GONE);
                iv_ch_image.setVisibility(View.GONE);
                ng_one_image.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(urlList[0]), ng_one_image, optionsImag);
                ng_one_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, urlList, 0);
                        dialog.show();
                    }
                });
            } else if (urlList.length == 4) {
                iv_ch_image.setVisibility(View.GONE);
                ng_one_image.setVisibility(View.GONE);
                ll_two.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(urlList[0]), iv_two_one, optionsImag);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(urlList[1]), iv_two_two, optionsImag);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(urlList[2]), iv_two_three, optionsImag);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(urlList[3]), iv_two_four, optionsImag);
                iv_two_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, urlList, 0);
                        dialog.show();
                    }
                });

                iv_two_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, urlList, 1);
                        dialog.show();
                    }
                });

                iv_two_three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, urlList, 2);
                        dialog.show();
                    }
                });

                iv_two_four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, urlList, 3);
                        dialog.show();
                    }
                });

            } else {
                ng_one_image.setVisibility(View.GONE);
                ll_two.setVisibility(View.GONE);
                iv_ch_image.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ZhetebaUtils.dip2px(ChanelTextAct.this, 255), ActionBar.LayoutParams.WRAP_CONTENT);
                iv_ch_image.setLayoutParams(params);
                iv_ch_image.setNumColumns(3);
                PicGdAdapter adapter = new PicGdAdapter(ChanelTextAct.this, urlList);
                iv_ch_image.setAdapter(adapter);
            }
        }else {
            llphoto.setVisibility(View.GONE);
            ll_two.setVisibility(View.GONE);
            ng_one_image.setVisibility(View.GONE);
            iv_ch_image.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ll_comment,R.id.iv_chanel_comment})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_comment:
                break;
            case R.id.iv_chanel_comment:
                if (flag) {
                    if (!TextUtils.isEmpty(etComment.getText().toString().trim())) {
                        btnSend.setEnabled(false);
                        commentChannelTalk(channelId, fatherCommentId, commentedUserId, etComment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(getApplicationContext(), "请输入回复内容！");
                    }
                } else {
                    if (!TextUtils.isEmpty(etComment.getText().toString().trim())) {
                        btnSend.setEnabled(false);
                        commentChannelTalk(channelId, null, null, etComment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(getApplicationContext(), "请输入评论内容！");
                    }
                }
//                if (!TextUtils.isEmpty(etComment.getText().toString().trim())) {
//                    btnSend.setEnabled(false);
//                    commentChannelTalk(channelId, null,null, etComment.getText().toString().trim());
//                }else {
//                    MyToastUtils.showShortToast(ChanelTextAct.this,"请输入评论内容！");
//                }
                break;
//            case R.id.ll_zan:
//                ll_zan.setEnabled(false);
//                if (0 == channelTalkEntity.getLiked()) {
//                    addChannelLike(channelId);
//                } else {//if (1 == list.get(clickPos).getLiked())
//                    removeChannelLike(channelId);
//                }
//                break;
//            case R.id.ll_cmt:
//                flag=false;
//                etComment.setHint("说点什么吧...");
//                etComment.requestFocus();
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                break;
        }
    }

    /**
     * 频道说说评论
     * @param channelTalkId
     * @param fatherCommentId
     * @param commentContent
     */
    private void commentChannelTalk(final String channelTalkId  ,String fatherCommentId ,String comentedId,String commentContent){
        RequestManager.getTalkManager().commentChannelTalk(channelTalkId, fatherCommentId, comentedId, commentContent, new ResultCallback<ResultBean<ChannelTalkEntity>>() {
            @Override
            public void onError(int status, String errorMsg) {
                btnSend.setEnabled(true);
            }

            @Override
            public void onResponse(ResultBean<ChannelTalkEntity> response) {
                //重新获取评论列表，刷新评论数目，关闭键盘
//                ZtinfoUtils.hideSoftKeyboard(ChanelTextAct.this, etComment);
//                etComment.setText("");
//                commentNum.setText("评论" + response.getData());
//                comNum=response.getData();
//                page=1;
//                getCircleCommentsList(channelTalkId, page, rows);
//                btnSend.setEnabled(true);
                getChanelTalk(channelId);
                ll_headbg2.setVisibility(View.VISIBLE);
                channelTalkEntity = response.getData();//返回的评论实体
//                if (!TextUtils.isEmpty(channelTalkEntity.getRemark() + "")) {
//                    if (Integer.parseInt(channelTalkEntity.getRemark()) == 0) {
//                        cnum2.setVisibility(View.GONE);
//                        cnumText.setVisibility(View.GONE);
//                    } else if (Integer.parseInt(channelTalkEntity.getRemark()) > 0) {
//                        cnum2.setVisibility(View.VISIBLE);
//                        cnumText.setVisibility(View.VISIBLE);
//                        cnum2.setText(channelTalkEntity.getRemark());
//                    }
//                } else {
//                    cnum2.setVisibility(View.GONE);
//                    cnumText.setVisibility(View.GONE);
//                }
                //重新获取评论列表，刷新评论数目，关闭键盘
                ZtinfoUtils.hideSoftKeyboard(ChanelTextAct.this, etComment);
                etComment.setText("");
//                commentNum.setText("评论" + circleEntity.getRemark());
//                chanelComtNum = channelTalkEntity.getRemark();
                if (flag) {
                    getCircleCommentsList(channelTalkId,1,10);
//                    if (clickPos > 0) {
//                        if (datas.get(clickPos-1).getChildCommentsList() != null) {
//                            datas.get(clickPos-1).getChildCommentsList().add(channelTalkEntity);
//                        } else {
//                            List<ChannelTalkEntity> list = new ArrayList<ChannelTalkEntity>();
//                            datas.get(clickPos-1).setChildCommentsList(list);
//                            datas.get(clickPos-1).getChildCommentsList().add(channelTalkEntity);
//                        }
//                    }
                } else {
                    datas.add(channelTalkEntity);
                }
                if (adapter == null) {
                    adapter = new ChaTextAdapter(ChanelTextAct.this, datas);//评论列表
                    my_lv.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }
//                page = 1;
//                getCircleCommentsList(circleTalkId, page, rows);
                btnSend.setEnabled(true);
                sendBroadcast(new Intent(SquareAct.TALK_LIST));

            }
        });
    }

    private List<ChannelTalkEntity> datas=new ArrayList<>();
    //获取评论列表
    private void getCircleCommentsList(String channelTalkId, final int page, int rows){
        list = new ArrayList<>();
        RequestManager.getTalkManager().getChannelCommentsList(channelTalkId, page, rows, new ResultCallback<ResultBean<DataBean<ChannelTalkEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                my_lv.onPullUpRefreshComplete();
                my_lv.onPullDownRefreshComplete();
                load_view.loadError();
            }

            @Override
            public void onResponse(ResultBean<DataBean<ChannelTalkEntity>> response) {
                my_lv.onPullUpRefreshComplete();
                my_lv.onPullDownRefreshComplete();
                load_view.loadComplete();
                list = response.getData().getRows();
                if (list.size() == 0) {
                    if (page == 1) {
                        ll_headbg2.setVisibility(View.GONE);

                    } else {
                        my_lv.setHasMoreData(false);
                    }
                }else {
                    ll_headbg2.setVisibility(View.VISIBLE);
                }

                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
//                commentNum.setText("评论" + datas.size());
                if (adapter == null) {
                    adapter = new ChaTextAdapter(ChanelTextAct.this, datas);
                    my_lv.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }
                if (!TextUtils.isEmpty(comNum)) {
                    if (Integer.parseInt(comNum) < 5&&Integer.parseInt(comNum)>0) {
                        Intent intent = new Intent(SquareAct.TALK_LIST);
                        Bundle bundle = new Bundle();
                        bundle.putInt("ComtPosition", position);
                        bundle.putString("tag", "comTag");
                        if (!TextUtils.isEmpty(comNum)) {
                            bundle.putInt("ComtNum", Integer.parseInt(comNum));
                        }
                        if (datas != null && datas.size() > 0) {
                            bundle.putParcelableArrayList("ComtList", (ArrayList<ChannelTalkEntity>) datas);
                        }
                        intent.putExtras(bundle);
                        sendBroadcast(intent);
                    }
                }
            }
        });
    }
    /**
     * 获取单个吐槽说说
     * @param channelId
     */
    private void getChanelTalk(String channelId) {
        RequestManager.getTalkManager().getChanelTalk(channelId, new ResultCallback<ResultBean<ChannelTalkEntity>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<ChannelTalkEntity> response) {
                channelTalkEntity=response.getData();
                setChannel(channelTalkEntity);

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        if (receiverTalk == null) {
            receiverTalk = new MyBroadCastReceiverTalk();
            registerReceiver(receiverTalk, new IntentFilter(CHANELTEXT));
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
    public static final String CHANELTEXT = "chanel_text";


    private class MyBroadCastReceiverTalk extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String petName = intent.getStringExtra("petName");
            String fatherId = intent.getStringExtra("fatherId");
            String comId = intent.getStringExtra("comId");
            clickPos = intent.getIntExtra("clickPos", clickPos);
            if (!TextUtils.isEmpty(comId)) {
                commentedUserId = comId;
            }
            if (!TextUtils.isEmpty(fatherId)) {
                fatherCommentId = fatherId;
                if (!TextUtils.isEmpty(petName)) {
                    etComment.setHint("回复" + petName + ":");
                }
                flag = true;
            } else {
                etComment.setHint("说点什么吧...");
                flag = false;
            }
            etComment.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 点赞
     *
     * @param channelId
     */
    private void addChannelLike(String channelId) {
        RequestManager.getTalkManager().addChannelLike(channelId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                ll_zan.setEnabled(true);
                MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                ll_zan.setEnabled(true);
                channelTalkEntity.setLiked(1);
                if (!TextUtils.isEmpty(response.getData())) {
                    znum2.setVisibility(View.VISIBLE);
                    znumText.setVisibility(View.VISIBLE);
                    znum2.setText(Integer.parseInt(response.getData()) + "");
                }
                iv_zan.setImageResource(R.mipmap.zanx);
            }
        });

    }

    /**
     * 取消点赞
     *
     * @param channelId
     */
    private void removeChannelLike(String channelId) {
        RequestManager.getTalkManager().removeChannelLike(channelId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                ll_zan.setEnabled(true);
                MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                ll_zan.setEnabled(true);
                channelTalkEntity.setLiked(0);
                if (!TextUtils.isEmpty(response.getData())) {
                    znum2.setVisibility(View.VISIBLE);
                    znumText.setVisibility(View.VISIBLE);
                    znum2.setText(Integer.parseInt(response.getData()) + "");
                }
                iv_zan.setImageResource(R.mipmap.zanx);
            }
        });
    }
}
