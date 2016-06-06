package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.CollectionBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;

import java.util.List;

/**
 * 我的收藏列表
 * Created by bitch-1 on 2016/6/3.
 */
public class CollectAdapter extends BaseAdapter {
    private Context context;
    private List<CollectionBean>list;

    public CollectAdapter(Context context) {
        this.context = context;
        this.list=list;
    }


    @Override
    public int getCount() {

        return 3;


    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context,R.layout.item_collect,null);
        LinearLayout ll_shouc= (LinearLayout) view.findViewById(R.id.ll_shouc);
        ll_shouc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCollection(list.get(position).getCollectionId());
            }
        });


        return view;

    }



    public void removeCollection(String collectionId){
        RequestManager.getScheduleManager().removeCollection(collectionId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {

            }
        });
    }

}
