package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.ChanelTextAct;
import com.boyuanitsm.zhetengba.activity.circle.CircleTextAct;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.utils.EmojUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.MyListview;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 频道正文列表adapter
 * Created by xiaoke on 2016/5/12.
 */
public class ChaTextAdapter extends BaseAdapter {
    private Context context;
    private List<ChannelTalkEntity> list;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    private int cusPos = -1;

    public ChaTextAdapter(Context context){
        this.context=context;
    }
    public ChaTextAdapter(Context context,List<ChannelTalkEntity> list){
        this.context=context;
        this.list=list;
    }
    public void notifyChange(List<ChannelTalkEntity> list){
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
        ChaHolder chaHolder = null;
        final List<ChannelTalkEntity> childCommentsList = list.get(position).getChildCommentsList();
        if (convertView!=null&&convertView.getTag()!=null){
            chaHolder= (ChaHolder) convertView.getTag();
        }else {
            chaHolder=new ChaHolder();
            convertView=View.inflate(context,R.layout.item_chane_text,null);
            chaHolder.head= (ImageView) convertView.findViewById(R.id.head);
            chaHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_user_name);
            chaHolder.time= (TextView) convertView.findViewById(R.id.time);
            chaHolder.content= (TextView) convertView.findViewById(R.id.tv_comment_text);
            chaHolder.lv_hf = (MyListview) convertView.findViewById(R.id.lv_hf);
            convertView.setTag(chaHolder);
        }
        if(list!=null&&list.size()>0) {
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getUserIcon()),chaHolder.head,options);
            if (!TextUtils.isEmpty(list.get(position).getPetName())){
                chaHolder.tv_name.setText(list.get(position).getPetName());
            } else if (!TextUtils.isEmpty(list.get(position).getUserName())){
                String str=list.get(position).getUserName();
                chaHolder.tv_name.setText(str.substring(0,3)+"***"+str.substring(str.length()-3,str.length()));
            }else if(!TextUtils.isEmpty(list.get(position).getCommentUserId())) {
                String str=list.get(position).getCommentUserId();
                chaHolder.tv_name.setText(str.substring(0, 3) + "***" + str.substring(str.length() - 3, str.length()));
            }
            if(!TextUtils.isEmpty(list.get(position).getCommentTime())){
                chaHolder.time.setText(ZtinfoUtils.timeChange(Long.parseLong(list.get(position).getCommentTime())));
            }else {
                chaHolder.time.setText("");
            }
            if(!TextUtils.isEmpty(list.get(position).getCommentContent())){
                chaHolder.content.setText(EmojUtils.decoder(list.get(position).getCommentContent()));
            }else {
                chaHolder.content.setText("");
            }
            chaHolder.lv_hf.setSelector(new ColorDrawable(Color.TRANSPARENT));
//            if (adapter == null) {
            ChanelHfAdapter  adapter = new ChanelHfAdapter(context, childCommentsList);
            chaHolder.lv_hf.setAdapter(adapter);
//            } else {
//                adapter.update(childCommentsList);
//            }
            chaHolder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.equals(UserInfoDao.getUser().getId(), list.get(position).getCommentUserId())) {
                        Intent intent = new Intent(ChanelTextAct.CHANELTEXT);
                        intent.putExtra("petName", list.get(position).getPetName());
                        intent.putExtra("fatherId", list.get(position).getId());
                        intent.putExtra("comId", list.get(position).getCommentUserId());
                        intent.putExtra("clickPos", cusPos);
                        context.sendBroadcast(intent);
                    }
                }
            });
            chaHolder.lv_hf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (childCommentsList != null) {
                        if (!TextUtils.equals(UserInfoDao.getUser().getId(), childCommentsList.get(position).getCommentUserId())) {
                            Intent intent = new Intent(ChanelTextAct.CHANELTEXT);
                            intent.putExtra("petName", childCommentsList.get(position).getPetName());
                            intent.putExtra("fatherId", childCommentsList.get(position).getFatherCommentId());
                            intent.putExtra("comId", childCommentsList.get(position).getCommentUserId());
                            intent.putExtra("clickPos", cusPos);
                            context.sendBroadcast(intent);
                        }
                    }
                }
            });
        }
        return convertView;
    }
   static class ChaHolder{
       private ImageView head;
       private TextView tv_name;
       private TextView time;
       private TextView content;
       private MyListview lv_hf;

   }
}
