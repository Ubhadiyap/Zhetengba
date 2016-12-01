package com.boyuanitsm.zhetengba.activity.mess;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.SquareAct;
import com.boyuanitsm.zhetengba.adapter.SqMessAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ActivityMess;
import com.boyuanitsm.zhetengba.bean.SquareInfo;
import com.boyuanitsm.zhetengba.db.ActivityMessDao;
import com.boyuanitsm.zhetengba.db.SquareMessDao;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.LoadingView;
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
 * Created by xiaoke on 2016/11/29.
 */
public class SquareMessAct extends BaseActivity {
    @ViewInject(R.id.lvsqMes)
    private SwipeMenuListView listView;
    @ViewInject(R.id.load_view)
    private LoadingView load_view;
    private SqMessAdapter adapter;
    private List<SquareInfo> list=new ArrayList<>();
    @Override
    public void setLayout() {
        setContentView(R.layout.act_sq_mess);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("吐槽消息");
        SharedPreferences sharedPreferences = getSharedPreferences("sqa_cir", Activity.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        list = SquareMessDao.getCircleUser();
        if (list==null){
            load_view.noContent();
        }else {
            load_view.loadComplete();
            SortClass sort = new SortClass();
            Collections.sort(list, sort);
            if (adapter==null){
                adapter=new SqMessAdapter(SquareMessAct.this,list);
                listView.setAdapter(adapter);
            }else {
                adapter.notifyDataChange(list);
            }
        }
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()) {
                    case 0:
                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                getApplicationContext());
                        deleteItem.setBackground(R.color.delete_red);
                        deleteItem.setWidth(ZhetebaUtils.dip2px(SquareMessAct.this, 80));
                        deleteItem.setTitle("删除");
                        deleteItem.setTitleSize(14);
                        deleteItem.setTitleColor(Color.WHITE);
                        menu.addMenuItem(deleteItem);
                        break;
                }

            }

        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                            ActivityMessDao.deleteMes(list.get(position).getCreateTime());
                            list.remove(position);
                        if (adapter==null){
                            adapter=new SqMessAdapter(SquareMessAct.this,list);
                            listView.setAdapter(adapter);
                        }else {
                            adapter.notifyDataChange(list);
                        }
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

    }
    /**
     * 时间降序
     * 排序
     */
    public class SortClass implements Comparator {
        public int compare(Object arg0, Object arg1) {
            SquareInfo user0 = (SquareInfo) arg0;
            SquareInfo user1 = (SquareInfo) arg1;
            int flag = user1.getCreateTime().compareTo(user0.getCreateTime());//升序直接将user0,user1互换
            return flag;
        }
    }
}
