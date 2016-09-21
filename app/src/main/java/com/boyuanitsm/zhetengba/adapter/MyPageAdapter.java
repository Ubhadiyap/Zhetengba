package com.boyuanitsm.zhetengba.adapter;

/**
 * Created by xiaoke on 2016/6/3.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.H5Web;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/***
 * viewpageradapter
 */
public class MyPageAdapter extends PagerAdapter {
    private List<LabelBannerInfo> list=new ArrayList<>();
    private Context context;
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.banner_load_fail)
            .showImageOnLoading(R.mipmap.banner_loading)
            .showImageOnFail(R.mipmap.banner_load_fail).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    public MyPageAdapter(Context context, List<LabelBannerInfo> list){
        this.list=list;
        this.context=context;
    }
    public void upData(List<LabelBannerInfo> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        //            return  bannerInfoList==null?0:bannerInfoList.size();
        return list==null?0:list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        View view = View.inflate(context, R.layout.item_loop_viewpager_act, null);
        ImageView iv_iamge = (ImageView) view.findViewById(R.id.iv_item_image);
        //加载图片地址
//            iv_iamge.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.test_banner));
        //            ImageLoader.getInstance().displayImage(
        //                    UrlManager.getPicFullUrl(bannerInfoList.get(position).getBannerPic()), iv_iamge,
        //                    optionsImag);
//        if (list!=null&&list.size()>0){
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getIcon()),iv_iamge,optionsImag);
//        }
        //UrlManager,网络地址管理类

        ((ViewPager) container).addView(view);

        iv_iamge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(list.get(position).getDictCode()!=null&&!TextUtils.isEmpty(list.get(position).getDictCode())){
                    Intent intent=new Intent(context, H5Web.class);
                    intent.putExtra("url",list.get(position).getDictCode());
                    intent.putExtra("topTitle", "活动");
                    context.startActivity(intent);}

            }
        });
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

}