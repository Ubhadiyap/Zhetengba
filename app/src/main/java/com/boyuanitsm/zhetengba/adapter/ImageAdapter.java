package com.boyuanitsm.zhetengba.adapter;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.boyuanitsm.zhetengba.R;

/**
 * 首页图片轮播适配器
 * Created by xiaoke on 2016/4/26.
 */
public class ImageAdapter extends PagerAdapter {
    private Context context;
    private Integer[] imageList={R.drawable.test_banner, R.drawable.test_banner, R.drawable.test_banner};
    public ImageAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view= View.inflate(context,R.layout.item_loop_viewpager_act,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_item_image);
        imageView.setBackgroundResource(imageList[position]);
        container.addView(view);
        return view;
    }
}
