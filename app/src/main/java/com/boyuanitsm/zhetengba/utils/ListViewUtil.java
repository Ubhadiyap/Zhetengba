package com.boyuanitsm.zhetengba.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by admin on 2016/5/25.
 */
public class ListViewUtil {


    /**
     * 根据listView的条目数量设置listview的大小
     * @param listView
     */
    public static int MeasureListView(ListView listView){


        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0 ;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        int lvHeight = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = lvHeight;
        listView.setLayoutParams(params);
        return lvHeight ;
    }

}
