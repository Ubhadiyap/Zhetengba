package com.boyuanitsm.zhetengba.activity.circle;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.MyApplication;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.publish.MyPlaneAct;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.adapter.CircleTextAdapter;
import com.boyuanitsm.zhetengba.adapter.PicGdAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.CircleNewMessDao;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
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
public class CircleTextAct extends BaseActivity implements View.OnClickListener {
    //    @ViewInject(R.id.ll_cir_comment)//评论
    private LinearLayout ll_cir_comment;
    @ViewInject(R.id.my_lv)
    private PullToRefreshListView my_lv;
    @ViewInject(R.id.iv_chanel_comment)
    private Button btnSend;
    //    private ScrollView sl_chanel;
    private LinearLayout ll_two;
    //    @ViewInject(R.id.llphoto)
    private LinearLayout llphoto;
    private CustomImageView iv_oneimage;
    private CustomImageView iv_two_one, iv_two_two, iv_two_three, iv_two_four;
    private MyGridView iv_ch_image;
    private List<List<ImageInfo>> dataList;
    private String cirComtNum;
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

    @ViewInject(R.id.et_comment)
    private EditText etComment;
    private String circleId;//说说id
    private CircleEntity entity;//说说实体
    private CircleEntity circleEntity;//评论成功返回的实体。
    //    @ViewInject(R.id.iv_ch_head)
    private CircleImageView head;//头像
    //    @ViewInject(R.id.tv_ch_niName)
    private TextView name;//姓名
    //    @ViewInject(R.id.iv_ch_gendar)
    private ImageView sex;//性别
    //    @ViewInject(R.id.tv_time)
    private TextView time;//时间
    //    @ViewInject(R.id.content)
    private TextView content;//说说内容
    //    @ViewInject(R.id.tv_cir_name)
    private TextView cirType;//圈子类型
    //    @ViewInject(R.id.commentNum)
    private TextView commentNum;//评论数

    private int page = 1;
    private int rows = 10;
    private List<CircleEntity> list;
    CircleTextAdapter adapter;
    private View headerView;
    private int position;
    @ViewInject(R.id.load_view)
    private LoadingView load_view;
    private boolean flag;
    private String fatherCommentId, commentedUserId;
    private int clickPos = -1;//评论点击位置
    private int comtPos = -1;//回复点击位置。
    private TextView cnum2, cnumText, znum2, znumText;
    private LinearLayout ll_zan, ll_cmt;
    private ImageView iv_zan;
    private LinearLayout ll_head, ll_head2,ll_headbg2;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_circle_text);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子详情");
        headerView = getLayoutInflater().inflate(R.layout.circle_headerview, null);
        assignView(headerView);
//        entity = getIntent().getParcelableExtra("circleEntity");
        circleId = getIntent().getStringExtra("circleId");
        position = getIntent().getIntExtra("CirCommentPosition", 0);
        my_lv.setPullRefreshEnabled(true);//下拉刷新
        my_lv.setScrollLoadEnabled(true);//滑动加载
        my_lv.setPullLoadEnabled(false);//上拉刷新
        my_lv.setHasMoreData(true);//是否有更多数据
        my_lv.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        my_lv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        my_lv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
        my_lv.getRefreshableView().setDivider(null);
        my_lv.getRefreshableView().addHeaderView(headerView);
//        if (entity == null) {
            if (!TextUtils.isEmpty(circleId)) {
                getCircleTalk(circleId);
                getCircleCommentsList(circleId, page, rows);
            }
//        }
//        setCircleEntity(entity);
            load_view.setOnRetryListener(new LoadingView.OnRetryListener() {
                @Override
                public void OnRetry() {
                    getCircleCommentsList(circleId, page, rows);
                }
            });
            my_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    my_lv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                    page = 1;
                    getCircleCommentsList(circleId, page, rows);
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    page++;
                    getCircleCommentsList(circleId, page, rows);
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
                if (0 == entity.getLiked()) {
                    addCircleLike(circleId);
                } else {//if (1 == list.get(clickPos).getLiked())
                    removeCircleLike(circleId);
                }
            }
        });
        ll_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                etComment.setHint("说点什么吧...");
                etComment.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
//        }
    }

    private void setCircleEntity(CircleEntity entity) {
        if (entity != null) {
            if (!TextUtils.isEmpty(entity.getUserIcon())) {
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(entity.getUserIcon()), head, optionshead);
            }
            if (!TextUtils.isEmpty(entity.getUserSex())) {
                if (entity.getUserSex().equals(1 + "")) {
                    sex.setImageResource(R.drawable.male);
                } else if (entity.getUserSex().equals(0 + "")) {
                    sex.setImageResource(R.drawable.female);
                }
            }
            if (!TextUtils.isEmpty(entity.getCircleName())) {
                String circleName = entity.getCircleName();
                if (entity.getCircleName().length() > 5) {
                    cirType.setText(circleName.substring(0, 2) + "..." + circleName.substring(circleName.length() - 2, circleName.length()));
                } else {
                    cirType.setText(entity.getCircleName());
                }
            }
            if (!TextUtils.isEmpty(entity.getUserName())) {
                name.setText(entity.getUserName());
            } else {
                String str = entity.getUserId();
                name.setText(str.substring(0, 3) + "***" + str.substring(str.length() - 3, str.length()));
            }
            if (!TextUtils.isEmpty(entity.getCreateTime())) {
                time.setText(ZtinfoUtils.timeChange(Long.parseLong(entity.getCreateTime())));
            }
            if (!TextUtils.isEmpty(entity.getTalkContent())) {
                content.setText(entity.getTalkContent());
            } else {
                content.setText("");
            }
            if (!TextUtils.isEmpty(entity.getCommentCounts() + "")) {
                commentNum.setText("评论" + entity.getCommentCounts());
            }
            if (!TextUtils.isEmpty(entity.getTalkImage())) {
                initDate(entity);
            }
            if (!TextUtils.isEmpty(entity.getLiked() + "")) {
                if (0 == entity.getLiked()) {//未点赞
                    iv_zan.setImageResource(R.mipmap.zan2x);
                } else {//if (1 == list.get(clickPos).getLiked())
                    iv_zan.setImageResource(R.mipmap.zanx);
                }
            }
            if (!TextUtils.isEmpty(entity.getLikedCounts() + "")) {
                if (entity.getLikedCounts() == 0) {
                    znum2.setVisibility(View.GONE);
                    znumText.setVisibility(View.GONE);
                } else {
                    znum2.setVisibility(View.VISIBLE);
                    znumText.setVisibility(View.VISIBLE);
                    znum2.setText(entity.getLikedCounts() + "");
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
        llphoto = (LinearLayout) view.findViewById(R.id.llphoto);
        head = (CircleImageView) view.findViewById(R.id.iv_ch_head);//头像
        name = (TextView) view.findViewById(R.id.tv_ch_niName);//姓名
        sex = (ImageView) view.findViewById(R.id.iv_ch_gendar);//性别
        time = (TextView) view.findViewById(R.id.tv_time);//时间
        content = (TextView) view.findViewById(R.id.content);//说说内容
        cirType = (TextView) view.findViewById(R.id.tv_cir_name);//圈子类型
        commentNum = (TextView) view.findViewById(R.id.commentNum);//评论数
        iv_ch_image = (MyGridView) view.findViewById(R.id.iv_ch_image);
        iv_oneimage = (CustomImageView) view.findViewById(R.id.iv_oneimage);
        ll_two = (LinearLayout) view.findViewById(R.id.ll_two);
        iv_two_one = (CustomImageView) view.findViewById(R.id.iv_two_one);
        iv_two_two = (CustomImageView) view.findViewById(R.id.iv_two_two);
        iv_two_three = (CustomImageView) view.findViewById(R.id.iv_two_three);
        iv_two_four = (CustomImageView) view.findViewById(R.id.iv_two_four);
        znum2 = (TextView) view.findViewById(R.id.znum2);
        cnum2 = (TextView) view.findViewById(R.id.cnum2);
        cnumText = (TextView) view.findViewById(R.id.cnumText);
        znumText = (TextView) view.findViewById(R.id.znumText);
        ll_zan = (LinearLayout) view.findViewById(R.id.ll_zan);
        ll_cmt = (LinearLayout) view.findViewById(R.id.ll_cmt);
        iv_zan = (ImageView) view.findViewById(R.id.iv_zan);
        ll_head2 = (LinearLayout) findViewById(R.id.ll_head2);
        ll_headbg2 = (LinearLayout) view.findViewById(R.id.headbg2);
    }

    private void initDate(CircleEntity circleEntity) {
        dataList = new ArrayList<>();
        //将图片地址转化成数组
        if (!TextUtils.isEmpty(circleEntity.getTalkImage())) {
            final String[] urlList = ZtinfoUtils.convertStrToArray(circleEntity.getTalkImage());
            llphoto.setVisibility(View.VISIBLE);
            if (urlList.length == 1) {
                ll_two.setVisibility(View.GONE);
                iv_ch_image.setVisibility(View.GONE);
                iv_oneimage.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(urlList[0]), iv_oneimage, optionsImag);
                iv_oneimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(CircleTextAct.this, urlList, 0);
                        dialog.show();
                    }
                });
            } else if (urlList.length == 4) {
                iv_ch_image.setVisibility(View.GONE);
                iv_oneimage.setVisibility(View.GONE);
                ll_two.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(urlList[0]), iv_two_one, optionsImag);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(urlList[1]), iv_two_two, optionsImag);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(urlList[2]), iv_two_three, optionsImag);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(urlList[3]), iv_two_four, optionsImag);
                iv_two_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(CircleTextAct.this, urlList, 0);
                        dialog.show();
                    }
                });

                iv_two_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(CircleTextAct.this, urlList, 1);
                        dialog.show();
                    }
                });

                iv_two_three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(CircleTextAct.this, urlList, 2);
                        dialog.show();
                    }
                });

                iv_two_four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(CircleTextAct.this, urlList, 3);
                        dialog.show();
                    }
                });

            } else {
                iv_oneimage.setVisibility(View.GONE);
                ll_two.setVisibility(View.GONE);
                iv_ch_image.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ZhetebaUtils.dip2px(CircleTextAct.this, 255), ActionBar.LayoutParams.WRAP_CONTENT);
                iv_ch_image.setLayoutParams(params);
                iv_ch_image.setNumColumns(3);
                PicGdAdapter adapter = new PicGdAdapter(CircleTextAct.this, urlList);
                iv_ch_image.setAdapter(adapter);

            }
        } else {
            llphoto.setVisibility(View.GONE);
            ll_two.setVisibility(View.GONE);
            iv_oneimage.setVisibility(View.GONE);
            iv_ch_image.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_chanel_comment})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_chanel_comment:
                if (flag) {
                    if (!TextUtils.isEmpty(etComment.getText().toString().trim())) {
                        btnSend.setEnabled(false);
                        commentCircleTalk(commentedUserId, circleId, fatherCommentId, etComment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(CircleTextAct.this, "请输入回复内容！");
                    }
                } else {
                    if (!TextUtils.isEmpty(etComment.getText().toString().trim())) {
                        btnSend.setEnabled(false);
                        commentCircleTalk(null, circleId, null, etComment.getText().toString().trim());
                    } else {
                        MyToastUtils.showShortToast(CircleTextAct.this, "请输入评论内容！");
                    }
                }

                break;
//            case R.id.ll_zan:
//                ll_zan.setEnabled(false);
//                if (0 == entity.getLiked()) {
//                    addCircleLike(circleId);
//                } else {//if (1 == list.get(clickPos).getLiked())
//                    removeCircleLike(circleId);
//                }
//                break;
//            case R.id.ll_cmt:
//                flag = false;
//                etComment.setHint("说点什么吧...");
//                etComment.requestFocus();
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
    private void commentCircleTalk(String commentedUserId, final String circleTalkId, String fatherCommentId, String commentContent) {
        RequestManager.getTalkManager().commentCircleTalk(commentedUserId, circleTalkId, fatherCommentId, commentContent, new ResultCallback<ResultBean<CircleEntity>>() {
            @Override
            public void onError(int status, String errorMsg) {
                btnSend.setEnabled(true);
            }

            @Override
            public void onResponse(ResultBean<CircleEntity> response) {
                getCircleTalk(circleId);
                ll_headbg2.setVisibility(View.VISIBLE);
                ll_head2.setVisibility(View.VISIBLE);
                CircleEntity circleEntity = response.getData();//返回的评论实体
//                if (!TextUtils.isEmpty(circleEntity.getRemark())) {
//                    if (Integer.parseInt(circleEntity.getRemark()) == 0) {
//                        cnum2.setVisibility(View.GONE);
//                        cnumText.setVisibility(View.GONE);
//                    } else if (Integer.parseInt(circleEntity.getRemark()) > 0) {
//                        cnum2.setVisibility(View.VISIBLE);
//                        cnumText.setVisibility(View.VISIBLE);
//                        cnum2.setText(entity.getRemark());
//                    }
//                } else {
//                    cnum2.setVisibility(View.GONE);
//                    cnumText.setVisibility(View.GONE);
//                }
                //重新获取评论列表，刷新评论数目，关闭键盘
                ZtinfoUtils.hideSoftKeyboard(CircleTextAct.this, etComment);
                etComment.setText("");
//                commentNum.setText("评论" + circleEntity.getRemark());
//                cirComtNum = circleEntity.getRemark();
                if (flag) {
                    getCircleCommentsList(circleTalkId,1,10);
//                    if (clickPos > 0) {
//                        if (datas.get(clickPos ).getChildCommentsList() != null) {
//                            datas.get(clickPos ).getChildCommentsList().add(circleEntity);
//                        } else {
//                            List<CircleEntity> list = new ArrayList<CircleEntity>();
//                            datas.get(clickPos ).setChildCommentsList(list);
//                            datas.get(clickPos ).getChildCommentsList().add(circleEntity);
//                        }
//                    }
                } else {
                    datas.add(circleEntity);
                }
                if (adapter == null) {
                    adapter = new CircleTextAdapter(CircleTextAct.this, datas);//评论列表
                    my_lv.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }
//                page = 1;
//                getCircleCommentsList(circleTalkId, page, rows);
                btnSend.setEnabled(true);
                sendBroadcast(new Intent(CirxqAct.TALKS));
                sendBroadcast(new Intent(MyPlaneAct.PLANEALLTALKS));

            }
        });
    }

    private List<CircleEntity> datas = new ArrayList<>();

    //获取评论列表
    private void getCircleCommentsList(String circleTalkId, final int page, int rows) {
        list = new ArrayList<>();
        RequestManager.getTalkManager().getCircleCommentsList(circleTalkId, page, rows, new ResultCallback<ResultBean<DataBean<CircleEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                my_lv.onPullUpRefreshComplete();
                my_lv.onPullDownRefreshComplete();
                load_view.loadError();
            }

            @Override
            public void onResponse(ResultBean<DataBean<CircleEntity>> response) {
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
                } else {
                    ll_headbg2.setVisibility(View.VISIBLE);
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list);
                if (adapter == null) {
                    adapter = new CircleTextAdapter(CircleTextAct.this, datas);//评论列表
                    my_lv.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datas);
                }
                if (!TextUtils.isEmpty(cirComtNum)) {
                    if (Integer.parseInt(cirComtNum) < 5 && Integer.parseInt(cirComtNum) > 0) {
                        Intent intent = new Intent(CircleAct.ALLTALKS);
                        Bundle bundle = new Bundle();
                        bundle.putInt("CirCommentPosition", position);
                        bundle.putString("tag", "CirComtTag");
                        if (!TextUtils.isEmpty(cirComtNum)) {
                            bundle.putInt("CirComtNum", Integer.parseInt(cirComtNum));
                        }
                        if (datas != null && datas.size() > 0) {
                            bundle.putParcelableArrayList("CirComtList", (ArrayList<CircleEntity>) datas);
                        }
                        intent.putExtras(bundle);
                        sendBroadcast(intent);
                    }
                }


            }
        });
    }

    /**
     * 获取圈子正文
     *
     * @param talkId
     */
    private void getCircleTalk(String talkId) {
        RequestManager.getTalkManager().getCircleTalk(talkId, new ResultCallback<ResultBean<CircleEntity>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<CircleEntity> response) {
                entity = response.getData();
                setCircleEntity(entity);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (receiverTalk == null) {
            receiverTalk = new MyBroadCastReceiverTalk();
            registerReceiver(receiverTalk, new IntentFilter(CIRCLETEXT));
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
    public static final String CIRCLETEXT = "circle_text";


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
     * 圈子说说点赞
     *
     * @param circleTalkId
     */
    private void addCircleLike(String circleTalkId) {
        RequestManager.getTalkManager().addCircleLike(circleTalkId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                ll_zan.setEnabled(true);
                MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                ll_zan.setEnabled(true);
                entity.setLiked(1);
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
     * 取消圈子说说点赞
     *
     * @param circleTalkId
     */
    private void removeCircleLike(String circleTalkId) {
        RequestManager.getTalkManager().removeCircleLike(circleTalkId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                ll_zan.setEnabled(true);
                MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                ll_zan.setEnabled(true);
                entity.setLiked(0);
                if (!TextUtils.isEmpty(response.getData())) {
                    znum2.setVisibility(View.VISIBLE);
                    znumText.setVisibility(View.VISIBLE);
                    znum2.setText(Integer.parseInt(response.getData()) + "");
                }
                iv_zan.setImageResource(R.mipmap.zan2x);
            }
        });
    }
}
