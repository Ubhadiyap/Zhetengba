package com.boyuanitsm.zhetengba.chat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.FriendsBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 黑名单
 * Created by xiaoke on 2016/8/9.
 */
public class HeiAdapter extends BaseAdapter {
    private Context context;
    private List<FriendsBean>list;

    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public HeiAdapter(Context context,List<FriendsBean> list){
        this.context=context;
        this.list = list;
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
        final ViewHolder holder;
        if(convertView==null){
            convertView =View.inflate(context, R.layout.item_hei_contact,null);
            holder=new ViewHolder();
            holder.iv_hand= (CircleImageView) convertView.findViewById(R.id.iv_hand);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_sex= (ImageView) convertView.findViewById(R.id.iv_sex);
            holder.tv_hf= (TextView) convertView.findViewById(R.id.tv_hf);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        if(list.size()>0){
        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getIcon()), holder.iv_hand, optionsImag);//用户头像
        if(list.get(position).getPetName()!=null&& !TextUtils.isEmpty(list.get(position).getPetName())){
            holder.tv_name.setText(list.get(position).getPetName());
        }
        if(list.get(position).getSex()!=null&&!TextUtils.isEmpty(list.get(position).getSex())){
            if("1".equals(list.get(position).getSex())){
                holder.iv_sex.setImageResource(R.mipmap.bmale);
            }else{
                holder.iv_sex.setImageResource(R.mipmap.gfemale);
            }

        }

        holder.tv_hf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //恢复好友
                removeBlack(list.get(position).getId());
            }
        });


        }

        return convertView;
    }


    class ViewHolder {
        private CircleImageView iv_hand;//头像
        private TextView tv_name;//姓名
        private ImageView iv_sex;//性别
        private TextView tv_hf;//恢复按钮



    }

    /**
     * 恢复黑名单
     */
    private void removeBlack(String friendId) {
        RequestManager.getScheduleManager().removeBlack(friendId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                MyToastUtils.showShortToast(context,"恢复好友成功");

            }
        });

    }
}
