package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.boyuanitsm.zhetengba.AppManager;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.CircleppAct;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.view.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子成员list适配器
 * Created by bitch-1 on 2016/5/10.
 */
public class CirpplistAdapter extends BaseAdapter{
    private Context context;
    private boolean isshanchu;
    private List<UserInfo> list=new ArrayList<>();

    public CirpplistAdapter(Context context,boolean isshanchu) {
        this.context = context;
        this.isshanchu=isshanchu;
    }
    public CirpplistAdapter(Context context,boolean isshanchu,List<UserInfo> list) {
        this.context = context;
        this.isshanchu=isshanchu;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size()>0?list.size():1;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context, R.layout.item_cirpp_list,null);
        LinearLayout ll_renshu= (LinearLayout) view.findViewById(R.id.ll_renshu);
        LinearLayout ll_shanchu= (LinearLayout) view.findViewById(R.id.ll_shanchu);
        if(isshanchu==false){
            ll_renshu.setVisibility(View.VISIBLE);
            ll_shanchu.setVisibility(View.GONE);
        }else {
            ll_renshu.setVisibility(View.GONE);
            ll_shanchu.setVisibility(View.VISIBLE);
            ll_shanchu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeMember("1","1");
                }
            });
        }
        HorizontalListView hlv_cirpp= (HorizontalListView) view.findViewById(R.id.hlv_cirpp);
        hlv_cirpp.setAdapter(new GvcirppAdapter(context));
        return view;
    }

    /**
     * 圈主删除成员
     * @param circleId 圈子id
     * @param memberId 成员id
     */
    private void removeMember(String circleId,String memberId){
        RequestManager.getTalkManager().removeMember(circleId, memberId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
               context.sendBroadcast(new Intent(CircleppAct.MEMBER));
            }
        });
    }
}
