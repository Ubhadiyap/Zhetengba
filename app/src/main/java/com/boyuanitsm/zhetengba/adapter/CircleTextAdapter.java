package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.hyphenate.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 圈子正文列表adapter
 * Created by xiaoke on 2016/5/12.
 */
public class CircleTextAdapter extends BaseAdapter {
    private Context context;
    private List<CircleEntity> list;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
//    private String[] str=new String[]{"小明","小红","小红","小红","小红","小红","小红"};
    public CircleTextAdapter(Context context){
        this.context=context;
    }
    public CircleTextAdapter(Context context, List<CircleEntity> list){
        this.context=context;
        this.list=list;
    }
    public void notifyChange(List<CircleEntity> list){
        this.list=list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ChaHolder chaHolder = null;
        if (convertView!=null&&convertView.getTag()!=null){
            chaHolder= (ChaHolder) convertView.getTag();
        }else {
            chaHolder=new ChaHolder();
            convertView=View.inflate(context,R.layout.item_chane_text,null);
            chaHolder.head= (CircleImageView) convertView.findViewById(R.id.head);
            chaHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_user_name);
            chaHolder.time= (TextView) convertView.findViewById(R.id.time);
            chaHolder.content= (TextView) convertView.findViewById(R.id.tv_comment_text);
            convertView.setTag(chaHolder);
        }
        if(list!=null&&list.size()>0) {
            if (!TextUtils.isEmpty(list.get(position).getUserIcon())){
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getUserIcon()),chaHolder.head,options);
            }
            if(!TextUtils.isEmpty(list.get(position).getUserName())){
                chaHolder.tv_name.setText(list.get(position).getUserName());
            }else if(!TextUtils.isEmpty(list.get(position).getCommentUserId())) {
                String str=list.get(position).getCommentUserId();
                chaHolder.tv_name.setText(str.substring(0, 3) + "***" + str.substring(str.length() - 3, str.length()));
            }
            if(!TextUtils.isEmpty(list.get(position).getCommentTime())){
                chaHolder.time.setText(ZtinfoUtils.timeToDate(Long.parseLong(list.get(position).getCommentTime())));
            }
            if(!TextUtils.isEmpty(list.get(position).getCommentContent())){
                chaHolder.content.setText(list.get(position).getCommentContent());
            }
        }
        return convertView;
    }
   static class ChaHolder{
       private CircleImageView head;
       private TextView tv_name;
       private TextView time;
       private TextView content;
    }
}
