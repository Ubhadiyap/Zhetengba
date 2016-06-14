package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.CircleppAct;
import com.boyuanitsm.zhetengba.activity.circle.CirxqAct;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.HorizontalListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子成员list适配器
 * Created by bitch-1 on 2016/5/10.
 */
public class CirpplistAdapter extends BaseAdapter{
    private Context context;
    private boolean isshanchu;
    private String circleId;//圈子id
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();
    private List<MemberEntity> list=new ArrayList<>();

    public CirpplistAdapter(Context context,boolean isshanchu) {
        this.context = context;
        this.isshanchu=isshanchu;
    }
    public CirpplistAdapter(Context context,boolean isshanchu,List<MemberEntity> list) {
        this.context = context;
        this.isshanchu=isshanchu;
        this.list=list;
    }
    public CirpplistAdapter(Context context,boolean isshanchu,List<MemberEntity> list,String circleId) {
        this.context = context;
        this.isshanchu=isshanchu;
        this.list=list;
        this.circleId=circleId;
    }

    public void notifyChange(boolean isshanchu){
        this.isshanchu=isshanchu;
        notifyDataSetChanged();
    }
    public void notifyChange(boolean isshanchu,List<MemberEntity> list){
        this.isshanchu=isshanchu;
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder=null;
        if(convertView==null) {
            holder=new ViewHolder();
            convertView= View.inflate(context, R.layout.item_cirpp_list, null);
            holder.hlv_cirpp= (HorizontalListView) convertView.findViewById(R.id.hlv_cirpp);
            holder.ll_renshu = (LinearLayout) convertView.findViewById(R.id.ll_renshu);
            holder.ll_shanchu = (LinearLayout) convertView.findViewById(R.id.ll_shanchu);
            holder.head= (CircleImageView) convertView.findViewById(R.id.iv_hand);
            holder.name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.num= (TextView) convertView.findViewById(R.id.num);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        if(isshanchu==false){
            holder.ll_renshu.setVisibility(View.VISIBLE);
            holder.ll_shanchu.setVisibility(View.GONE);
        }else {
            holder.ll_renshu.setVisibility(View.GONE);
            holder.ll_shanchu.setVisibility(View.VISIBLE);
            holder.ll_shanchu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeMember(circleId,list.get(position).getId());
                }
            });
        }
        if(list!=null&&list.size()>0){
            if (!TextUtils.isEmpty(list.get(position).getIcon())){
                ImageLoader.getInstance().displayImage(IZtbUrl.BASE_URL+list.get(position).getIcon(),holder.head,options);
            }
            if(!TextUtils.isEmpty(list.get(position).getUsername())){
                holder.name.setText(list.get(position).getUsername());
            }
            if (!TextUtils.isEmpty(list.get(position).getSameCircleCounts()+"")){
                holder.num.setText(list.get(position).getSameCircleCounts()+"");
            }
            if (!TextUtils.isEmpty(list.get(position).getSameLabels())){
                String[] str= ZtinfoUtils.convertStrToArray(list.get(position).getSameLabels());
                holder.hlv_cirpp.setAdapter(new GvcirppAdapter(context,str));
            }
        }
        return convertView;
    }

    class ViewHolder{
        private HorizontalListView hlv_cirpp;
        private LinearLayout ll_renshu;
        private LinearLayout ll_shanchu;
        private CircleImageView head;
        private TextView name;
        private TextView num;
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
                context.sendBroadcast(new Intent(CirxqAct.MEMBERXQ));
            }
        });
    }
}
