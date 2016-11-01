package com.boyuanitsm.zhetengba.activity.circle;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.ConstantValue;
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
import com.boyuanitsm.zhetengba.fragment.circleFrg.ChanelItemFrg;
import com.boyuanitsm.zhetengba.fragment.circleFrg.CirFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.EmojUtils;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CanotEmojEditText;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.LoadingView;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.view.PicShowDialog;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.leaf.library.widget.MyListView;
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
public class CircleTextAct extends BaseActivity implements View.OnClickListener{
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
    private CustomImageView  iv_two_one, iv_two_two, iv_two_three, iv_two_four;
    private MyGridView iv_ch_image;
    private List<List<ImageInfo>> dataList ;
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

    private int page=1;
    private int rows=10;
    private List<CircleEntity> list;
    CircleTextAdapter adapter;
    private View headerView;
    private int position;
    @ViewInject(R.id.load_view)
    private LoadingView load_view;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_circle_text);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("");
        headerView=getLayoutInflater().inflate(R.layout.circle_headerview,null);
        assignView(headerView);
        entity=getIntent().getParcelableExtra("circleEntity");
        circleId=getIntent().getStringExtra("circleId");
         position=getIntent().getIntExtra("CirCommentPosition", 0);
        LayoutHelperUtil.freshInit(my_lv);
        my_lv.getRefreshableView().addHeaderView(headerView);
        setCircleEntity(entity);

        getCircleCommentsList(circleId, page, rows);
        load_view.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getCircleCommentsList(circleId, page, rows);
            }
        });
        my_lv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ll_answer.setVisibility(View.VISIBLE);
//                et_comment.setFocusable(true);
//                et_comment.setFocusableInTouchMode(true);
//                et_comment.requestFocus();
//                et_comment.requestFocusFromTouch();
//              view= (View) parent.getItemAtPosition(0);
//             TextView user_name= (TextView) view.findViewById(R.id.tv_user_name);
//               String  str_nam = user_name.getText().toString();
//                et_comment.setText("回复"+str_nam+"：");


            }
        });
        my_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                my_lv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page=1;
                getCircleCommentsList(circleId,page,rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getCircleCommentsList(circleId,page,rows);
            }
        });

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())){
                    btnSend.setBackgroundResource(R.drawable.main_btn_nor);
                    btnSend.setTextColor(Color.parseColor("#FFFFFF"));
                }else {
                    btnSend.setBackgroundColor(Color.parseColor("#f4f4f4"));
                    btnSend.setTextColor(Color.parseColor("#999999"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setCircleEntity(CircleEntity entity){
        if(entity!=null){
            if (!TextUtils.isEmpty(entity.getUserIcon())){
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(entity.getUserIcon()),head,optionshead);
            }
            if (!TextUtils.isEmpty(entity.getUserSex())){
                if (entity.getUserSex().equals(1+"")){
                    sex.setImageResource(R.drawable.male);
                }else if (entity.getUserSex().equals(0+"")){
                    sex.setImageResource(R.drawable.female);
                }
            }
            if (!TextUtils.isEmpty(entity.getCircleName())){
                String circleName = entity.getCircleName();
                if (entity.getCircleName().length()>6){
                    cirType.setText(circleName.substring(0, 2) + "..." + circleName.substring(circleName.length() - 2,circleName.length()));
                }else {
                    cirType.setText(entity.getCircleName());
                }
            }
            if(!TextUtils.isEmpty(entity.getUserName())){
                name.setText(entity.getUserName());
            }else {
                String str=entity.getUserId();
                name.setText(str.substring(0,3)+"***"+str.substring(str.length()-3,str.length()));
            }
            if(!TextUtils.isEmpty(entity.getCreateTime())){
                time.setText(ZtinfoUtils.timeChange(Long.parseLong(entity.getCreateTime())));
            }
            if(!TextUtils.isEmpty(entity.getTalkContent())){
                content.setText(entity.getTalkContent());
            }else {
                content.setText("");
            }
            if (!TextUtils.isEmpty(entity.getCommentCounts()+"")){
                commentNum.setText("评论"+entity.getCommentCounts());
            }
            if (!TextUtils.isEmpty(entity.getTalkImage())){
                initDate(entity);
            }
        }
    }
    private void assignView(View view) {
        llphoto= (LinearLayout) view.findViewById(R.id.llphoto);
        head= (CircleImageView) view.findViewById(R.id.iv_ch_head);//头像
        name= (TextView) view.findViewById(R.id.tv_ch_niName);//姓名
        sex= (ImageView) view.findViewById(R.id.iv_ch_gendar);//性别
        time= (TextView) view.findViewById(R.id.tv_time);//时间
        content= (TextView) view.findViewById(R.id.content);//说说内容
        cirType= (TextView) view.findViewById(R.id.tv_cir_name);//圈子类型
        commentNum= (TextView) view.findViewById(R.id.commentNum);//评论数
        iv_ch_image = (MyGridView) view.findViewById(R.id.iv_ch_image);
        iv_oneimage = (CustomImageView) view.findViewById(R.id.iv_oneimage);
        ll_two = (LinearLayout) view.findViewById(R.id.ll_two);
        iv_two_one = (CustomImageView) view.findViewById(R.id.iv_two_one);
        iv_two_two = (CustomImageView) view.findViewById(R.id.iv_two_two);
        iv_two_three = (CustomImageView) view.findViewById(R.id.iv_two_three);
        iv_two_four = (CustomImageView) view.findViewById(R.id.iv_two_four);
    }

    private void initDate(CircleEntity circleEntity) {
        dataList = new ArrayList<>();
        final List<ImageInfo> singleList=new ArrayList<>();
        //将图片地址转化成数组
        if(!TextUtils.isEmpty(circleEntity.getTalkImage())) {
            String[] urlList = ZtinfoUtils.convertStrToArray(circleEntity.getTalkImage());
            for (int i = 0; i < urlList.length; i++) {
                singleList.add(new ImageInfo(urlList[i], 1624, 914));
            }
        }
        dataList.add(singleList);
        llphoto.setVisibility(View.VISIBLE);
        if (singleList.isEmpty() || singleList.isEmpty()) {
            llphoto.setVisibility(View.GONE);
            ll_two.setVisibility(View.GONE);
            iv_oneimage.setVisibility(View.GONE);
            iv_ch_image.setVisibility(View.GONE);
        } else if (singleList.size() == 1) {
            ll_two.setVisibility(View.GONE);
            iv_ch_image.setVisibility(View.GONE);
            iv_oneimage.setVisibility(View.VISIBLE);
//            Bitmap bitmap = ImageLoader.getInstance().loadImageSync(Uitls.imageFullUrl(singleList.get(0).getUrl()),optionsImag);
//            singleList.get(0).setWidth(120);
//            singleList.get(0).setHeight(120);
//            LayoutHelperUtil.handlerOneImage(CircleTextAct.this, singleList.get(0), iv_oneimage);
ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(singleList.get(0).getUrl()),iv_oneimage,optionsImag);
//            LayoutHelperUtil.handlerOneImage(getApplicationContext(), singleList.get(0), iv_oneimage);

            iv_oneimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(CircleTextAct.this, singleList, 0);
                    dialog.show();
                }
            });
        } else if (singleList.size() == 4) {
            iv_ch_image.setVisibility(View.GONE);
            iv_oneimage.setVisibility(View.GONE);
            ll_two.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(singleList.get(0).getUrl()), iv_two_one, optionsImag);
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(singleList.get(1).getUrl()), iv_two_two, optionsImag);
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(singleList.get(2).getUrl()), iv_two_three, optionsImag);
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(singleList.get(3).getUrl()), iv_two_four, optionsImag);
            iv_two_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(CircleTextAct.this, singleList, 0);
                    dialog.show();
                }
            });

            iv_two_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(CircleTextAct.this, singleList, 1);
                    dialog.show();
                }
            });

            iv_two_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(CircleTextAct.this, singleList, 2);
                    dialog.show();
                }
            });

            iv_two_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(CircleTextAct.this, singleList, 3);
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
            PicGdAdapter adapter = new PicGdAdapter(CircleTextAct.this, singleList);
            iv_ch_image.setAdapter(adapter);

        }
    }

    @OnClick({R.id.iv_chanel_comment})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_chanel_comment:
                if (!TextUtils.isEmpty(etComment.getText().toString().trim())) {
                    btnSend.setEnabled(false);
                    btnSend.setClickable(false);
                    commentCircleTalk(circleId, null, etComment.getText().toString().trim());
                }else {
                    MyToastUtils.showShortToast(CircleTextAct.this,"请输入评论内容！");
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
    private void commentCircleTalk(final String circleTalkId ,String fatherCommentId ,String commentContent){
        RequestManager.getTalkManager().commentCircleTalk(circleTalkId, fatherCommentId, commentContent, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                btnSend.setEnabled(true);
                btnSend.setClickable(true);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                //重新获取评论列表，刷新评论数目，关闭键盘
                ZtinfoUtils.hideSoftKeyboard(CircleTextAct.this, etComment);
                etComment.setText("");
                commentNum.setText("评论" + response.getData());
                cirComtNum=response.getData();
                page=1;
                getCircleCommentsList(circleTalkId, page, rows);
                btnSend.setEnabled(true);
                btnSend.setClickable(true);
                sendBroadcast(new Intent(CirxqAct.TALKS));
                sendBroadcast(new Intent(MyPlaneAct.PLANEALLTALKS));

            }
        });
    }
    private List<CircleEntity> datas=new ArrayList<>();
    //获取评论列表
    private void getCircleCommentsList(String circleTalkId, final int page, int rows){
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
                list=response.getData().getRows();
                if (list.size() == 0) {
                    if (page == 1) {

                    } else {
                        my_lv.setHasMoreData(false);
                    }
                }
                if(page==1){
                    datas.clear();
                }
                datas.addAll(list);
//                commentNum.setText("评论"+datas.size());
                if (adapter==null) {
                    adapter = new CircleTextAdapter(CircleTextAct.this, datas);//评论列表
                    my_lv.getRefreshableView().setAdapter(adapter);
                }else {
                    adapter.notifyChange(datas);
                }
                Intent intent=new Intent(CircleAct.ALLTALKS);
                Bundle bundle=new Bundle();
                bundle.putInt("CirCommentPosition", position);
                bundle.putString("tag","CirComtTag");
                if (!TextUtils.isEmpty(cirComtNum)){
                    bundle.putInt("CirComtNum", Integer.parseInt(cirComtNum));
                }
                if (datas!=null&&datas.size()>0){
                    bundle.putParcelableArrayList("CirComtList", (ArrayList<CircleEntity>) datas);
                }
                intent.putExtras(bundle);
                sendBroadcast(intent);

            }
        });
    }

}
