package com.boyuanitsm.zhetengba.activity.circle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
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
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;

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

    private TextView iv_dailognew;//dialog上面小红点
//    private int type;
    private boolean flag;

    @Override
    public void setLayout() {
        setContentView(R.layout.cir_frg);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子");
        getAllCircleTalk(page, rows);
        lv_cir = (PullToRefreshListView) findViewById(R.id.lv_cir);
        llnoList = (LinearLayout) findViewById(R.id.noList);
        ivAnim = (ImageView) findViewById(R.id.ivAnim);
        noMsg = (TextView) findViewById(R.id.noMsg);
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
//        lv_cir.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
//                    ImageLoader.getInstance().resume();
//                } else {
//                    ImageLoader.getInstance().pause();
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
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
                if (datas.size()>0){
//                    if (adapter != null) {
//                        adapter.notifyChange(datalist, circleEntityList);
//                    }
                }else {
                    llnoList.setVisibility(View.VISIBLE);
                    ivAnim.setImageResource(R.mipmap.planeno);
                    noMsg.setText("加载失败");
                }

            }

            @Override
            public void onResponse(ResultBean<DataBean<CircleEntity>> response) {
                lv_cir.onPullUpRefreshComplete();
                lv_cir.onPullDownRefreshComplete();
                circleEntityList = response.getData().getRows();
                if (circleEntityList.size() == 0) {
                    if (page == 1) {
                        llnoList.setVisibility(View.VISIBLE);
                        ivAnim.setImageResource(R.mipmap.planeno);
                        noMsg.setText("暂无内容");
                    } else {
                        lv_cir.setHasMoreData(false);
                    }
//                    return;
                } else {
                    llnoList.setVisibility(View.GONE);
                }
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(circleEntityList);
                for (int j = 0; j < datas.size(); j++) {
                    final List<ImageInfo> itemList = new ArrayList<>();
                    //将图片地址转化成数组
                    if (!TextUtils.isEmpty(datas.get(j).getTalkImage())) {
                        final String[] urlList = ZtinfoUtils.convertStrToArray(datas.get(j).getTalkImage());
                        for (int i = 0; i < urlList.length; i++) {
                            itemList.add(new ImageInfo(urlList[i], 120, 120));
                        }

                    }
                    datalist.add(itemList);
                }
                if (adapter == null) {
                    adapter = new CircleAdapter(CircleAct.this, datalist, datas);
                    lv_cir.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datalist, datas);
                }
            }
        });
    }

    @OnClick({R.id.rl_more,R.id.iv_serch})
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
        }
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
                int cposition = bundle.getInt("CirCommentPosition");
                int cNum = bundle.getInt("CirComtNum");
                datas.get(cposition).setCommentCounts(cNum);
                if (adapter == null) {
                    adapter = new CircleAdapter(CircleAct.this, datalist, datas);
                    lv_cir.getRefreshableView().setAdapter(adapter);
                } else {
                    adapter.notifyChange(datalist, datas);
                }
            } else {
                page = 1;
                getAllCircleTalk(page, rows);
            }
        }
    }

}
