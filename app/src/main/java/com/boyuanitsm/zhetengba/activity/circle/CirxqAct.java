package com.boyuanitsm.zhetengba.activity.circle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.AppManager;
import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
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
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.MyRecyleview;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
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
    private String personIds;//存储邀请用户id
    private int IsInCircle;//从收索圈子进来后的判断条件看是否在圈子里面 0不在，1在；
    private int type;//类型
    //    @ViewInject(R.id.head)
    private CircleImageView head;//头像
    //    @ViewInject(R.id.tv_qz)
    private TextView name;//圈主名
    //    @ViewInject(R.id.notice)
    private TextView notice;//公告
    private TextView qzzl;//圈子资料

    private RelativeLayout rl_jiaru;

    private int page=1;
    private int rows=10;


    private List<List<ImageInfo>> datalist;
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

    private boolean isQuanzhu=false;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_cirxq);
        AppManager.getAppManager().addActivity(this);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("");
        headView=getLayoutInflater().inflate(R.layout.xqhead_view,null);
        head= (CircleImageView) headView.findViewById(R.id.head);//头像
        name= (TextView) headView.findViewById(R.id.tv_qz);//圈主名
        notice= (TextView) headView.findViewById(R.id.notice);//公告
        qzzl= (TextView) headView.findViewById(R.id.tv_qzzl);//圈子资料
        rv_label= (MyRecyleview) headView.findViewById(R.id.rv_label);//圈子成员
        rl_jiaru=(RelativeLayout) headView.findViewById(R.id.rl_jiaru);//加入圈子 默认是隐藏的
        lv_cir.getRefreshableView().addHeaderView(headView);
        LayoutHelperUtil.freshInit(lv_cir);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CirxqAct.this);
        //设置横向
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_label.setLayoutManager(linearLayoutManager);
        Intent intent=getIntent();

        type=intent.getExtras().getInt("type");
        circleId=intent.getExtras().getString("circleId");
        if(type==0){
            //从收索里面进来
            IsInCircle=intent.getExtras().getInt("isincircle");
            if(IsInCircle==0){
                //不在圈子里面
                rl_jiaru.setVisibility(View.VISIBLE);
                getCircleDetail(circleId);
                getCircleMembers(circleId);
                xqAdapter=new CirclexqListAdapter(CirxqAct.this,datalist,datas);
                lv_cir.getRefreshableView().setAdapter(xqAdapter);
                isFresh(false);
            }else if(IsInCircle==1){
                //在圈子里面
                rl_jiaru.setVisibility(View.GONE);
                getCircleDetail(circleId);
                getCircleMembers(circleId);
                getThisCircleTalks(circleId, page, rows);
                isFresh(true);
            }

        }
        if(type==1){
            //从圈子管理进来
            getCircleDetail(circleId);
            getCircleMembers(circleId);
            getThisCircleTalks(circleId, page, rows);
            isFresh(true);
        }

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
                Intent intent = new Intent(CirxqAct.this, CirmationAct.class);
                intent.putExtra("circleEntity", circleEntity);
                startActivity(intent);
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
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    lv_cir.onPullUpRefreshComplete();
                    lv_cir.onPullDownRefreshComplete();
                }
            });
        }else {
            //在圈子里面
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

        }
    }


    //获取圈子详情
    private void getCircleDetail(String circleId){
        circleEntity=new CircleEntity();
        RequestManager.getTalkManager().myCircleDetail(circleId, new ResultCallback<ResultBean<CircleEntity>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<CircleEntity> response) {
                circleEntity=response.getData();
                if (circleEntity!=null) {
                    setCircle(circleEntity);
                }
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
            if (!TextUtils.isEmpty(entity.getCircleOwnerId())){
                if(entity.getCircleOwnerId().equals(UserInfoDao.getUser().getId())){
                    isQuanzhu=true;
                }else {
                    isQuanzhu=false;
                }
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
                                Intent  intent = new Intent();
                                Bundle bundle=new Bundle();
                                String str3="circleFriend";
                                bundle.putString("can", str3);
                                intent.putExtras(bundle);
                                intent.setClass(CirxqAct.this, AssignScanAct.class);
                                startActivityForResult(intent, 6);
                            }else {
                                if (position == (userList.size()+1)) {
                                    Intent intent = new Intent(CirxqAct.this, CircleppAct.class);
                                    intent.putExtra("circleId", circleId);
                                    intent.putExtra("isQuanzhu",isQuanzhu);
                                    startActivity(intent);
                                }
                            }
                        } else {
                            if (position == 5) {
                                Intent intent = new Intent(CirxqAct.this, CircleppAct.class);
                                intent.putExtra("circleId", circleId);
                                intent.putExtra("isQuanzhu",isQuanzhu);
                                startActivity(intent);
                            } else if (position == 4) {
                                Intent  intent = new Intent();
                                Bundle bundle=new Bundle();
                                String str3="circleFriend";
                                bundle.putString("can", str3);
                                intent.putExtras(bundle);
                                intent.setClass(CirxqAct.this, AssignScanAct.class);
                                startActivityForResult(intent, 6);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null&&resultCode==6){
            Bundle bundle=data.getBundleExtra("bundle3");
            personIds= bundle.getString("bundleIds");
            inviteFriendToCircle(circleId,personIds);
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
                            itemList.add(new ImageInfo(urlList[i], 1624, 914));
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

