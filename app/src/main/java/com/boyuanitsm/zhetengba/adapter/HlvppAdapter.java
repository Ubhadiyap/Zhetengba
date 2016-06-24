package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.CircleglAct;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人主页界面她的圈子下面的 水平listview适配器
 * Created by bitch-1 on 2016/5/16.
 */
public class HlvppAdapter extends BaseAdapter {
    private Context context;
    private List<CircleEntity> circleEntityList=new ArrayList<>();
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    public HlvppAdapter(Context context, List<CircleEntity> circleEntity) {
        this.context = context;
        this.circleEntityList=circleEntity;
    }

    @Override
    public int getCount() {
        if (circleEntityList!=null){
            return circleEntityList.size()>5?5:circleEntityList.size();
        }else {
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return circleEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context, R.layout.item_hlvpp,null);
        CircleImageView cir_pagehand= (CircleImageView) view.findViewById(R.id.cir_pagehand);
        TextView tv_more= (TextView) view.findViewById(R.id.tv_more);
        tv_more.setText(circleEntityList.get(position).getCircleName());
        LinearLayout ll_quanzi= (LinearLayout) view.findViewById(R.id.ll_quanzi);
        if(position==4){
            cir_pagehand.setImageResource(R.mipmap.cirxq_more);
            tv_more.setText("更多");
            ll_quanzi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, CircleglAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }else {
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(circleEntityList.get(position).getCircleLogo()),cir_pagehand,optionsImag);

        }
        return view;
    }
}
