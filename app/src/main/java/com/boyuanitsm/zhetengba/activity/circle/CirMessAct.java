package com.boyuanitsm.zhetengba.activity.circle;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.boyuanitsm.zhetengba.IsShow;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.publish.MyPlaneAct;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.adapter.CircleMessAdatper;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleInfo;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.CircleMessDao;
import com.boyuanitsm.zhetengba.db.CircleNewMessDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenu;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuCreator;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuItem;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 圈子--圈子消息
 * Created by xiaoke on 2016/5/6.
 */
public class CirMessAct extends BaseActivity {
    @ViewInject(R.id.lv_cir_mess)//下拉刷新
    private SwipeMenuListView lv_cir_mess;
    private List<CircleInfo> list = new ArrayList<CircleInfo>();
    private List<CircleInfo> datas=new ArrayList<>();
    private List<CircleInfo> agreeList;
    private CircleMessAdatper adapter;
    private String type=1+"";
    private int page=-1;
    private int rows=-1;
    private ProgressDialog progressDialog;


    @Override
    public void setLayout() {
        setContentView(R.layout.act_circle_mess);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子消息");
        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("数据加载中...");
        progressDialog.show();
        setRight("我的发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转我发布界面；
                //直接跳转发布界面；
                openActivity(MyPlaneAct.class);
            }
        });
        getDqMess(type, page, rows);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()) {
                    case 0:
                        SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                        deleteItem.setBackground(R.color.delete_red);
                        deleteItem.setWidth(ZhetebaUtils.dip2px(CirMessAct.this, 80));
                        deleteItem.setTitle("删除");
                        deleteItem.setTitleSize(14);
                        deleteItem.setTitleColor(Color.WHITE);
                        menu.addMenuItem(deleteItem);
                        break;
                }

            }
        };
        lv_cir_mess.setMenuCreator(creator);
        lv_cir_mess.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        if (!TextUtils.isEmpty(list.get(position).getMsgType())) {
                            datas = CircleMessDao.getCircleUser();
                            if (datas != null && datas.size() > 0) {
                                for (int i = 0; i < datas.size(); i++) {
                                    //删除之前，判断，是否在数据库，如果在，则从数据库中删除，并调删除接口。
                                    if (TextUtils.equals(datas.get(i).getId(), list.get(position).getId())) {
                                        CircleMessDao.delCir(list.get(position).getCreateTime());
                                    }
                                }
                            }
                            deleteMsg(list.get(position).getId());
                        } else {
                            CircleMessDao.delCir(list.get(position).getCreateTime());
                        }
                        list.remove(position);
                        adapter.updata(list);
//                        updateActivityMessDao();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        lv_cir_mess.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        lv_cir_mess.smoothOpenMenu(0);
        if (CircleNewMessDao.getUser()!=null){
            CircleNewMessDao.deleteUser();
        }
    }

    /**
     * 删除消息接口
     * @param id
     */
    private void deleteMsg(String id) {
        RequestManager.getScheduleManager().deleteMsg(id, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {

            }
        });
    }

//    private void notifyData() {
//        list = new ArrayList<>();
//        list = CircleMessDao.getCircleUser();
//        getDqMess(type,page,rows);
//        if (list != null && list.size() > 0) {
//            Collections.reverse(list);
//        }
//    }
    /**
     * 调用档期消息，需要操作的接口
     * type,0档期，1是圈子
     * @param type
     * @param page
     * @param rows
     */
    private void getDqMess(String type, final int page,int rows){
        agreeList=new ArrayList<>();
        RequestManager.getScheduleManager().findMyInviteMsg(type, page, rows, new ResultCallback<ResultBean<DataBean<CircleInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                progressDialog.dismiss();
                MyToastUtils.showShortToast(getApplicationContext(),"请求失败，请检查网络！");
            }

            @Override
            public void onResponse(ResultBean<DataBean<CircleInfo>> response) {
                progressDialog.dismiss();
                agreeList=response.getData().getRows();
                MyLogUtils.info(agreeList.toString() + "返回集合");
                list=new ArrayList<>();
                list = CircleMessDao.getCircleUser();
                if (list!=null&&list.size() > 0) {
//                    Collections.reverse(list);
                    if (agreeList!=null&&agreeList.size()>0){
                        for (int i=0;i<agreeList.size();i++){
                            list.add(agreeList.get(i));
                        }
                    }
                    SortClass sort = new SortClass();
                    Collections.sort(list, sort);
                }else if (agreeList!=null&&agreeList.size()>0){
                    list=agreeList;
                }
                if (adapter == null) {
                    adapter = new CircleMessAdatper(CirMessAct.this, list);
                    lv_cir_mess.setAdapter(adapter);
                } else {
                    adapter.updata(list);
                }
            }
        });
    }
    /**
     * 时间降序
     * 排序
     */
    public class SortClass implements Comparator {
        public int compare(Object arg0, Object arg1) {
            CircleInfo user0 = (CircleInfo) arg0;
            CircleInfo user1 = (CircleInfo) arg1;
            int flag = user1.getCreateTime().compareTo(user0.getCreateTime());//升序直接将user0,user1互换
            return flag;
        }
    }

    private MyBroadCastReceiver receiver;
    public static final String REDGONG = "receiver_myreceiver";
    private class MyBroadCastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            getDqMess(type, page, rows);
            CircleMessDao.dellAll();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IsShow.setA("2");//是一个全局的变量在receiver中会用到来做判断
        if(receiver==null){
            receiver=new MyBroadCastReceiver();
            registerReceiver(receiver,new IntentFilter(REDGONG));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IsShow.setA("1");
        if(receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }
}
