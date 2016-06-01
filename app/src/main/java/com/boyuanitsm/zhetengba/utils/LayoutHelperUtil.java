package com.boyuanitsm.zhetengba.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

/**
 * 布局常用方法
 * Created by xiaoke on 2016/5/22.
 */
public class LayoutHelperUtil {

    /***
     * 根据图片宽高自动设置布局
     *
     * @param image
     */
    public static void handlerOneImage(Context context ,ImageInfo image,CustomImageView imageView) {
        int totalWidth;
        int imageWidth;
        int imageHeight;
        ScreenTools screentools = ScreenTools.instance(context);
        totalWidth = screentools.getScreenWidth() - screentools.dip2px(80);
        imageWidth = screentools.dip2px(image.getWidth());
        imageHeight = screentools.dip2px(image.getHeight());
        if (image.getWidth() <= image.getHeight()) {
            if (imageHeight > totalWidth) {
                imageHeight = totalWidth;
                imageWidth = (imageHeight * image.getWidth()) / image.getHeight();
            }
        } else {
            if (imageWidth > totalWidth) {
                imageWidth = totalWidth;
                imageHeight = (imageWidth * image.getHeight()) / image.getWidth();
            }
        }
        ViewGroup.LayoutParams layoutparams = imageView.getLayoutParams();
        layoutparams.height = imageHeight;
        layoutparams.width = imageWidth;
        imageView.setLayoutParams(layoutparams);
        imageView.setClickable(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageUrl(image.getUrl());

    }
    public static void freshInit(PullToRefreshListView listView){
        listView.setPullRefreshEnabled(true);//下拉刷新
        listView.setScrollLoadEnabled(true);//滑动加载
        listView.setPullLoadEnabled(false);//上拉刷新
        listView.setHasMoreData(true);//是否有更多数据
        listView.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        listView.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        listView.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
        listView.getRefreshableView().setDivider(null);
        listView.getRefreshableView().setDividerHeight(1);
    }

}
