package com.boyuanitsm.zhetengba.activity.mess;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.DqMesAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ActivityMess;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.ActivityMessDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenu;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuCreator;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuItem;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 档期消息
 * Created by wangbin on 16/5/13.
 */
public class DqMesAct extends BaseActivity {
    @ViewInject(R.id.lvDqMes)
    private SwipeMenuListView lvDqMes;
    private DqMesAdapter adapter;//档期消息适配器
    private List<ActivityMess> list;//极光接收的集合；
    private List<ActivityMess> agreeList;//后台返回的同意拒绝操作list
    private List<ActivityMess> datas=new ArrayList<>();
    private int page=-1;
    private int rows=-1;
    private String type=0+"";
    @Override
    public void setLayout() {
        setContentView(R.layout.act_dq_mes);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("档期消息");
//        list= ActivityMessDao.getCircleUser();
//        MyLogUtils.info(list + "档期消息信息");
//        if (list!=null&&list.size() > 0) {
//            Collections.reverse(list);
//        }
//        notifyData();
        getDqMess(type, page, rows);
        adapter=new DqMesAdapter(this,list);
        lvDqMes.setAdapter(adapter);
//        setRight("清空", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (list != null && list.size() > 0) {
//                    list.clear();
//                    adapter.notifyDataSetChanged();
//                    ActivityMessDao.delAll();
//                }
//            }
//        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()) {
                    case 0:
                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                getApplicationContext());
                        deleteItem.setBackground(R.color.plan_green);
                        deleteItem.setWidth(ZhetebaUtils.dip2px(DqMesAct.this, 80));
                        deleteItem.setTitle("删除");
                        deleteItem.setTitleSize(14);
                        deleteItem.setTitleColor(Color.parseColor("#ffffff"));
                        menu.addMenuItem(deleteItem);
                        break;
                }

            }
        };
        lvDqMes.setMenuCreator(creator);
        lvDqMes.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
//                        MyToastUtils.showShortToast(MsgAct.this, position + "");
                        // delete
                        if (!TextUtils.isEmpty(list.get(position).getMsgType())){
                            datas=ActivityMessDao.getCircleUser();
                          for (int i=0;i<datas.size();i++){
                              //删除之前，判断，是否在数据库，如果在，则从数据库中删除，并调删除接口。
                              if (TextUtils.equals(datas.get(i).getId(),list.get(position).getId())){
                                  ActivityMessDao.deleteMes(list.get(position).getCreateTime());
                              }
                          }
                            deleteMsg(list.get(position).getId());
                        }else {
                            ActivityMessDao.deleteMes(list.get(position).getCreateTime());
                        }

                        list.remove(position);
                        adapter.notifyDataChange(list);
//                        updateActivityMessDao();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        lvDqMes.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

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
//        list=new ArrayList<>();
//        list = ActivityMessDao.getCircleUser();
//        if (list!=null&&list.size() > 0) {
//            Collections.reverse(list);
//        }
//        if (adapter == null) {
//            adapter = new DqMesAdapter(this, list);
//            lvDqMes.setAdapter(adapter);
//        } else {
//            adapter.notifyDataChange(list);
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
        RequestManager.getScheduleManager().findMyInviteMsg(type, page, rows, new ResultCallback<ResultBean<DataBean<ActivityMess>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<DataBean<ActivityMess>> response) {
                agreeList=response.getData().getRows();
                MyLogUtils.info(agreeList.toString()+"返回集合");
                list=new ArrayList<>();
                list = ActivityMessDao.getCircleUser();
                if (list!=null&&list.size() > 0) {
                    Collections.reverse(list);//时间排一下序
                    if (agreeList!=null&&agreeList.size()>0){
                        for (int i=0;i<agreeList.size();i++){
                            list.add(agreeList.get(i));
                            //只有操作的数据才存放数据库，返回的数据均未作处理。
//                            ActivityMessDao.saveCircleMess(agreeList.get(i));
                        }
                    }
                }else if (agreeList!=null&&agreeList.size()>0){
                    list=agreeList;
                }
                if (adapter == null) {
                    adapter = new DqMesAdapter(DqMesAct.this, list);
                    lvDqMes.setAdapter(adapter);
                } else {
                    adapter.notifyDataChange(list);
                }
            }
        });
    }
}
