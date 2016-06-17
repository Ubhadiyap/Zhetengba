package com.boyuanitsm.zhetengba.activity.circle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ChaTextAdapter;
import com.boyuanitsm.zhetengba.adapter.CirxqAdapter;
import com.boyuanitsm.zhetengba.adapter.PicGdAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.fragment.circleFrg.ChaChildFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.CustomImageView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道正文
 * Created by xiaoke on 2016/5/11.
 */
public class ChanelTextAct extends BaseActivity implements View.OnClickListener{
//    @ViewInject(R.id.ll_comment)//评论
//    private LinearLayout ll_comment;
    @ViewInject(R.id.et_comment)
    private EditText etComment;//评论内容
    @ViewInject(R.id.my_lv)
    private PullToRefreshListView my_lv;
//    private ScrollView sl_chanel;
    private LinearLayout ll_two;
    private LinearLayout llphoto;
    private CustomImageView ng_one_image, iv_two_one, iv_two_two, iv_two_three, iv_two_four;
    private MyGridView iv_ch_image;
    private List<List<ImageInfo>> dataList;
    private String[][] images = new String[][]{{
            ConstantValue.IMAGEURL, "1624", "914"}
            , {ConstantValue.IMAGEURL, "1624", "914"}
            , {ConstantValue.IMAGEURL, "1624", "914"}
            , {ConstantValue.IMAGEURL, "1624", "914"}
            , {ConstantValue.IMAGEURL, "250", "250"}
            , {ConstantValue.IMAGEURL, "250", "250"}
            , {ConstantValue.IMAGEURL, "250", "250"}
            , {ConstantValue.IMAGEURL, "250", "250"}
            , {ConstantValue.IMAGEURL, "1280", "800"}
    };
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    private String channelId;//频道说说id
    private ChannelTalkEntity channelTalkEntity;//频道说说实体
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
//    @ViewInject(R.id.commentNum)
    private TextView commentNum;//评论数

    private View headView;
    private int page=1;
    private int rows=10;
    private List<ChannelTalkEntity> list;

    ChaTextAdapter adapter;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_chanel_text);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("频道正文");
        headView=getLayoutInflater().inflate(R.layout.hannel_headerview,null);
        assignView(headView);

        channelTalkEntity=getIntent().getParcelableExtra("channelEntity");
        channelId=getIntent().getStringExtra("channelId");
        LayoutHelperUtil.freshInit(my_lv);
        my_lv.getRefreshableView().addHeaderView(headView);
        setChannel(channelTalkEntity);
//        initDate();
        getCircleCommentsList(channelId, page, rows);
//        adapter = new ChaTextAdapter(this);
//        my_lv.setAdapter(adapter);
//        sl_chanel.smoothScrollTo(0, 0);

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
                getCircleCommentsList(channelId,page,rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getCircleCommentsList(channelId,page,rows);
            }
        });
    }

    private void setChannel(ChannelTalkEntity entity){
        if(entity!=null){
            if (!TextUtils.isEmpty(entity.getUserIcon())){
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(entity.getUserIcon()),head,optionsImag);
            }
            if(!TextUtils.isEmpty(entity.getUserName())){
                name.setText(entity.getUserName());
            }else {
                String str=entity.getCreatePersonId();
                name.setText(str.substring(0,3)+"***"+str.substring(str.length()-3,str.length()));
            }
            if(!TextUtils.isEmpty(entity.getCreateTiem())){
                time.setText(ZtinfoUtils.timeToDate(Long.parseLong(entity.getCreateTiem())));
            }
            if(!TextUtils.isEmpty(entity.getChannelContent())){
                content.setText(entity.getChannelContent());
            }
            if(!TextUtils.isEmpty(entity.getCommentCounts()+"")){
                commentNum.setText("评论"+entity.getCommentCounts());
            }
            if(!TextUtils.isEmpty(entity.getChannelImage())){
                initDate(entity);
            }
        }
    }

    private void assignView(View view) {
//        my_lv = (PullToRefreshListView) findViewById(R.id.my_lv);
//        sl_chanel = (ScrollView) findViewById(R.id.sl_chanel);
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
    }

    private void initDate(ChannelTalkEntity channelTalkEntity) {
        dataList = new ArrayList<>();
        final List<ImageInfo> singleList=new ArrayList<>();
            //将图片地址转化成数组
        if(!TextUtils.isEmpty(channelTalkEntity.getChannelImage())) {
            String[] urlList = ZtinfoUtils.convertStrToArray(channelTalkEntity.getChannelImage());
            for (int i = 0; i < urlList.length; i++) {
                singleList.add(new ImageInfo(Uitls.imageFullUrl(urlList[i]), 1624, 914));
            }
        }
        dataList.add(singleList);
        //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
//        final ArrayList<ImageInfo> singleList = new ArrayList<>();
//        singleList.add(new ImageInfo(images[8][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));
//        dataList.add(singleList);
//        singleList.add(new ImageInfo(images[8][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));
//        dataList.add(singleList);
//        singleList.add(new ImageInfo(images[8][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));
//        dataList.add(singleList);
        llphoto.setVisibility(View.VISIBLE);
        if (singleList.isEmpty() || singleList.isEmpty()) {
            llphoto.setVisibility(View.GONE);
            ll_two.setVisibility(View.GONE);
            ng_one_image.setVisibility(View.GONE);
            iv_ch_image.setVisibility(View.GONE);
        } else if (singleList.size() == 1) {
            ll_two.setVisibility(View.GONE);
            iv_ch_image.setVisibility(View.GONE);
            ng_one_image.setVisibility(View.VISIBLE);


            LayoutHelperUtil.handlerOneImage(getApplicationContext(), singleList.get(0), ng_one_image);

            ng_one_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, singleList, 0);
                    dialog.show();
                }
            });
        } else if (singleList.size() == 4) {
            iv_ch_image.setVisibility(View.GONE);
            ng_one_image.setVisibility(View.GONE);
            ll_two.setVisibility(View.VISIBLE);
//            viewHolder.iv_two_four.setImageUrl(itemList.get(3).getUrl());
            ImageLoader.getInstance().displayImage(singleList.get(0).getUrl(), iv_two_one, optionsImag);
            ImageLoader.getInstance().displayImage(singleList.get(1).getUrl(), iv_two_two, optionsImag);
            ImageLoader.getInstance().displayImage(singleList.get(2).getUrl(), iv_two_three, optionsImag);
            ImageLoader.getInstance().displayImage(singleList.get(3).getUrl(), iv_two_four, optionsImag);
            iv_two_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, singleList, 0);
                    dialog.show();
                }
            });

            iv_two_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, singleList, 1);
                    dialog.show();
                }
            });

            iv_two_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, singleList, 2);
                    dialog.show();
                }
            });

            iv_two_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, singleList, 3);
                    dialog.show();
                }
            });

        } else {
            ng_one_image.setVisibility(View.GONE);
            ll_two.setVisibility(View.GONE);
            iv_ch_image.setVisibility(View.VISIBLE);
            iv_ch_image.setNumColumns(3);
            PicGdAdapter adapter = new PicGdAdapter(ChanelTextAct.this, singleList);
            iv_ch_image.setAdapter(adapter);

        }
    }

    @OnClick({R.id.ll_comment,R.id.iv_chanel_comment})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_comment:
//                openActivity(CommentAct.class);//打开评论列表
                break;
            case R.id.iv_chanel_comment:
//                openActivity(CommentAct.class);
                commentChannelTalk(channelId,null,etComment.getText().toString().trim());
                break;
        }
    }

    /**
     * 频道说说评论
     * @param channelTalkId
     * @param fatherCommentId
     * @param commentContent
     */
    private void commentChannelTalk(final String channelTalkId  ,String fatherCommentId ,String commentContent){
        RequestManager.getTalkManager().commentChannelTalk(channelTalkId, fatherCommentId, commentContent, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                //重新获取评论列表，刷新评论数目，关闭键盘
                ZtinfoUtils.hideSoftKeyboard(ChanelTextAct.this, etComment);
                etComment.setText("");
                getCircleCommentsList(channelTalkId, page, rows);
//                commentNum.setText("评论"+"");
            }
        });
    }

    private List<ChannelTalkEntity> datas=new ArrayList<>();
    //获取评论列表
    private void getCircleCommentsList(String channelTalkId, final int page, int rows){
        dataList = new ArrayList<>();
        RequestManager.getTalkManager().getChannelCommentsList(channelTalkId, page, rows, new ResultCallback<ResultBean<DataBean<ChannelTalkEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                my_lv.onPullUpRefreshComplete();
                my_lv.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<DataBean<ChannelTalkEntity>> response) {
                my_lv.onPullUpRefreshComplete();
                my_lv.onPullDownRefreshComplete();
                list=response.getData().getRows();
                commentNum.setText("评论"+list.size());
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
                if (adapter==null) {
                    adapter = new ChaTextAdapter(ChanelTextAct.this, datas);
                    my_lv.getRefreshableView().setAdapter(adapter);
                }else {
                    adapter.notifyChange(datas);
                }
                sendBroadcast(new Intent(ChaChildFrg.CHANNELTALKS));
            }
        });
    }


}
