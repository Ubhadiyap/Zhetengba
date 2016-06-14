package com.boyuanitsm.zhetengba.activity.circle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mess.ContractsAct;
import com.boyuanitsm.zhetengba.activity.mine.AssignScanAct;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.adapter.CirclexqListAdapter;
import com.boyuanitsm.zhetengba.adapter.CirxqAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.MemberEntity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.MyListview;
import com.boyuanitsm.zhetengba.view.MyRecyleview;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 圈子详情界面
 * Created by bitch-1 on 2016/5/11.
 */
public class CirxqAct extends BaseActivity {
//    @ViewInject(R.id.rv_label)
    private MyRecyleview rv_label;
    @ViewInject(R.id.lv_cirxq)
    private PullToRefreshListView lv_cir;
//    @ViewInject(R.id.cir_sv)
//    private ScrollView cir_sv;
    @ViewInject(R.id.cir_fb)
    private TextView cir_fb;
    private List<Integer>list;

    private CirxqAdapter adapter;
    private String circleId;//圈子id
    private CircleEntity circleEntity;//圈子实体
    private List<MemberEntity> userList;//圈子成员集合
    private List<CircleEntity> circleEntityList;//该圈子说说列表

//    @ViewInject(R.id.head)
    private CircleImageView head;//头像
//    @ViewInject(R.id.tv_qz)
    private TextView name;//圈主名
//    @ViewInject(R.id.notice)
    private TextView notice;//公告
    private TextView qzzl;//圈子资料

    private int page=1;
    private int rows=10;


    private List<List<ImageInfo>> datalist=new ArrayList<>();
    private String[][] images=new String[][]{{
            ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL,"250","250"}
            ,{ConstantValue.IMAGEURL,"250","250"}
            ,{ConstantValue.IMAGEURL,"250","250"}
            ,{ConstantValue.IMAGEURL,"250","250"}
            ,{ConstantValue.IMAGEURL,"1280","800"}
    };
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    private View headView;
    private CirclexqListAdapter xqAdapter;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_cirxq);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("互联网创业");
        headView=getLayoutInflater().inflate(R.layout.xqhead_view,null);
        head= (CircleImageView) headView.findViewById(R.id.head);//头像
        name= (TextView) headView.findViewById(R.id.tv_qz);//圈主名
        notice= (TextView) headView.findViewById(R.id.notice);//公告
        qzzl= (TextView) headView.findViewById(R.id.tv_qzzl);//圈子资料
        rv_label= (MyRecyleview) headView.findViewById(R.id.rv_label);//圈子成员
        LayoutHelperUtil.freshInit(lv_cir);
        circleId=getIntent().getStringExtra("circleId");
//        initData();
        datalist=new ArrayList<>();
        getCircleDetail(circleId);
        getCircleMembers(circleId);
        getThisCircleTalks(circleId, page, rows);
//        list = new ArrayList<Integer>(Arrays.asList(R.mipmap.cirxq_l,R.mipmap.cirxq_lb,R.mipmap.cirxq_lbb,R.mipmap.cirxq_l,R.mipmap.cirxq_lb));
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CirxqAct.this);
        //设置横向
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rv_label.setLayoutManager(linearLayoutManager);

        cir_fb.setOnClickListener(new View.OnClickListener() {
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
                Intent intent=new Intent(CirxqAct.this,CirmationAct.class);
                intent.putExtra("circleEntity",circleEntity);
                startActivity(intent);
            }
        });

        lv_cir.getRefreshableView().addHeaderView(headView);
        lv_cir.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lv_cir.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page=1;
                getThisCircleTalks(circleId,page,rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getThisCircleTalks(circleId,page,rows);
            }
        });
//        lv_cir.setAdapter(new CirclexqListAdapter(CirxqAct.this,datalist));
    }



    private void initData() {
        datalist=new ArrayList<>();
        //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        ArrayList<ImageInfo> singleList=new ArrayList<>();
        singleList.add(new ImageInfo(images[0][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));
        datalist.add(singleList);
        //从一到9生成9条朋友圈内容，分别是1~9张图片
        for(int i=0;i<9;i++){
            ArrayList<ImageInfo> itemList=new ArrayList<>();
            for(int j=0;j<=i;j++){
                itemList.add(new ImageInfo(images[j][0],Integer.parseInt(images[j][1]),Integer.parseInt(images[j][2])));
            }
            datalist.add(itemList);
        }
    }

//    @OnClick({R.id.tv_qzzl})
//    public void OnClick(View v){
//        switch (v.getId()){
//            case R.id.tv_qzzl://圈子资料
//                Intent intent=new Intent(CirxqAct.this,CirmationAct.class);
//                intent.putExtra("circleEntity",circleEntity);
//                startActivity(intent);
//                break;
//        }
//
//    }

    //获取圈子详情
    private void getCircleDetail(String circleId){
        RequestManager.getTalkManager().myCircleDetail(circleId, new ResultCallback<ResultBean<CircleEntity>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<CircleEntity> response) {
                circleEntity=response.getData();
                setCircle(circleEntity);
            }
        });
    }

    //设置实体类
    private void setCircle(CircleEntity entity){
        if(entity!=null){
            setTopTitle(entity.getCircleName());
            if(!TextUtils.isEmpty(entity.getCircleLogo())){
                ImageLoader.getInstance().displayImage(IZtbUrl.BASE_URL+entity.getCircleLogo(),head,options);
            }
            if(!TextUtils.isEmpty(entity.getUserName())){
                name.setText("圈主：" + entity.getUserName());
            }else {
                String str=entity.getCircleOwnerId();
                name.setText("圈主："+str.substring(0,3)+"***"+str.substring(str.length()-3,str.length()));
            }
            if(!TextUtils.isEmpty(entity.getNotice())){
                notice.setText("公告："+entity.getNotice());
            }else {
                notice.setText("公告：暂无");
            }
        }
    }

    //获取圈子人员
    private void getCircleMembers(final String circleId){
        userList=new ArrayList<>();
        RequestManager.getTalkManager().myCircleMember(circleId,1,10, new ResultCallback<ResultBean<DataBean<MemberEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<DataBean<MemberEntity>> response) {
                userList=response.getData().getRows();
                adapter=new CirxqAdapter(CirxqAct.this,userList);
                rv_label.setAdapter(adapter);
                adapter.setOnItemClickListener(new CirxqAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (userList.size() <= 4) {
                            if (position == userList.size()) {
                                openActivity(AssignScanAct.class);
                            }else {
                                if (position == (userList.size()+1)) {
                                    Intent intent = new Intent(CirxqAct.this, CircleppAct.class);
                                    intent.putExtra("circleId", circleId);
                                    startActivity(intent);
                                }
                            }
                        } else {
                            if (position == 5) {
                                Intent intent = new Intent(CirxqAct.this, CircleppAct.class);
                                intent.putExtra("circleId", circleId);
                                startActivity(intent);
                            } else if (position == 4) {
                                openActivity(AssignScanAct.class);
                            }
                        }
                    }
                });
            }
        });
    }


    private List<CircleEntity> datas=new ArrayList<>();

    //获取该圈子所有说说列表
    private void getThisCircleTalks(String circleId, final int page, int rows){
        circleEntityList=new ArrayList<CircleEntity>();
        RequestManager.getTalkManager().getSingleCircleAllTalks(circleId, page, rows, new ResultCallback<ResultBean<DataBean<CircleEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_cir.onPullUpRefreshComplete();
                lv_cir.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<DataBean<CircleEntity>> response) {
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
                for (int j=0;j<datas.size();j++) {
                    List<ImageInfo> itemList=new ArrayList<>();
                    //将图片地址转化成数组
                    if(!TextUtils.isEmpty(datas.get(j).getTalkImage())) {
                        String[] urlList = ZtinfoUtils.convertStrToArray(datas.get(j).getTalkImage());
                        for (int i = 0; i < urlList.length; i++) {
                            itemList.add(new ImageInfo(Uitls.imageFullUrl(urlList[i]), 1624, 914));
                        }
                    }
                    datalist.add(itemList);
                }
                if(xqAdapter==null) {
                    xqAdapter=new CirclexqListAdapter(CirxqAct.this,datalist,datas);
                    lv_cir.getRefreshableView().setAdapter(xqAdapter);
                }else {
                    xqAdapter.notifyChange(datalist,datas);
                }
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
    }

    private MyBroadCastReceiver receiver;
    public static final String MEMBERXQ="xqmember_update";
    private class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getCircleMembers(circleId);
        }
    }

    private MyBroadCastReceiverTalk receiverTalk;
    public static final String TALKS="talk_update";
    private class MyBroadCastReceiverTalk extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            page=1;
            getThisCircleTalks(circleId,page,rows);
        }
    }

}
