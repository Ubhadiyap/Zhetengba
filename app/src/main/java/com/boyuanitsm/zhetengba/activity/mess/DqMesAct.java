package com.boyuanitsm.zhetengba.activity.mess;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.DqMesAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ActivityMess;
import com.boyuanitsm.zhetengba.db.ActivityMessDao;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenu;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuCreator;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuItem;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuListView;
import com.lidroid.xutils.view.annotation.ViewInject;

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
    private List<ActivityMess> list;
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
        notifyData();
        adapter=new DqMesAdapter(this,list);
        lvDqMes.setAdapter(adapter);
        setRight("清空", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list != null && list.size() > 0) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    ActivityMessDao.delAll();
                }
            }
        });

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
                        list.remove(position);
                        adapter.notifyDataChange(list);
//                        ActivityMessDao.delCir(list.get(position).getId());
                        updateActivityMessDao();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        lvDqMes.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
    }

    private void updateActivityMessDao() {
        ActivityMessDao.delAll();
        Collections.reverse(list);
        for (int i=0;i<list.size();i++){
            ActivityMessDao.saveCircleMess(list.get(i));
        }
    }

    private void notifyData() {
        list=new ArrayList<>();
        list = ActivityMessDao.getCircleUser();
        if (list!=null&&list.size() > 0) {
            Collections.reverse(list);
        }
        if (adapter == null) {
            adapter = new DqMesAdapter(this, list);
            lvDqMes.setAdapter(adapter);
        } else {
            adapter.notifyDataChange(list);
        }

    }
}
