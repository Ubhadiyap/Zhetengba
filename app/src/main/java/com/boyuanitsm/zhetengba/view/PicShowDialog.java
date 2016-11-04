package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.utils.MyBitmapUtils;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.photoView.PhotoView;
import com.boyuanitsm.zhetengba.view.photoView.PhotoViewAttacher;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 主界面点击发布，弹出半透明对话框
 * Created by xiaoke on 2016/4/28.
 */
public class PicShowDialog extends Dialog {
    private Context context;
    private String[] imageInfos;
    private PhotoView pv;
    private MyViewPager vp;
    private List<View> views = new ArrayList<View>();
    private LinearLayout ll_point;
    private ViewPagerAdapter pageAdapter;
    private int position;
    private LinearLayout.LayoutParams paramsL = new LinearLayout.LayoutParams(10, 10);
    private Animation mRotateAnimation;
    private ImageView mArrowImageView;
    /**
     * 旋转动画的时间
     */
    static final int ROTATION_ANIMATION_DURATION = 3000;
    /**
     * 动画插值
     */
    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.tum)
            .showImageOnFail(R.mipmap.tum).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    //    .showImageOnLoading(R.mipmap.banner_loading)
    private String path;
    private PopupWindow popupWindow;

    public PicShowDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    public PicShowDialog(Context context, String[] imageInfos, int position) {
        this(context, R.style.Pic_Dialog);
        this.imageInfos = imageInfos;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dialog_pic);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        float pivotValue = 0.5f; // SUPPRESS CHECKSTYLE
        float toDegree = 720.0f; // SUPPRESS CHECKSTYLE
        mRotateAnimation = new RotateAnimation(0.0f, toDegree,
                Animation.RELATIVE_TO_SELF, pivotValue,
                Animation.RELATIVE_TO_SELF, pivotValue);
        mRotateAnimation.setFillAfter(true);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
        vp = (MyViewPager) findViewById(R.id.vp);
        mArrowImageView = (ImageView) findViewById(R.id.mArrowImageView);
        ll_point = (LinearLayout) findViewById(R.id.ll_point);
//        mArrowImageView.setImageResource(R.mipmap.default_ptr_rotate);
        initMyPageAdapter();
        vp.setCurrentItem(position);
        vp.setOffscreenPageLimit(0);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (views.size() != 0 && views.get(position) != null) {

                    for (int i = 0; i < views.size(); i++) {
                        if (i == position) {
                            views.get(i).setBackgroundResource(R.drawable.point_focus2);
                        } else {
                            views.get(i).setBackgroundResource(R.drawable.point_normal2);
                        }
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    /***
     * 初始化viewpager适配器
     */

    private void initMyPageAdapter() {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new ViewPagerAdapter();
            if (vp != null) {
                vp.setAdapter(pageAdapter);
            }

        } else {
            pageAdapter.notifyDataSetChanged();
        }
    }

    private void initPoint() {
        views.clear();
        ll_point.removeAllViews();
        if (imageInfos.length == 1) {
            ll_point.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < imageInfos.length; i++) {
                View view = new View(context);
                paramsL.setMargins(ZhetebaUtils.dip2px(context, 5), ZhetebaUtils.dip2px(context, 2), 0, ZhetebaUtils.dip2px(context, 5));
                view.setLayoutParams(paramsL);
                if (i == position) {
                    view.setBackgroundResource(R.drawable.point_focus2);
                } else {
                    view.setBackgroundResource(R.drawable.point_normal2);
                }

                views.add(view);
                ll_point.addView(view);
            }
        }

    }

    private class ViewPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return imageInfos.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = View.inflate(context, R.layout.item_pic_show, null);
            final PhotoView photoView = (PhotoView) view.findViewById(R.id.pic_pv);
            ImageLoader.getInstance().displayImage(Uitls.imageBigFullUrl(imageInfos[position]), photoView, optionsImag, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    mArrowImageView.setVisibility(View.VISIBLE);
                    mArrowImageView.startAnimation(mRotateAnimation);
                    MyLogUtils.info("mArrowImageView显示=====");
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, final View view, final Bitmap bitmap) {
                    mArrowImageView.clearAnimation();
                    mRotateAnimation.cancel();
                    mArrowImageView.setVisibility(View.GONE);
                    if (bitmap!=null){
                        view.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                showPopupWindow(view, bitmap);
                                return false;
                            }
                        });
                    }else {
                        MyLogUtils.info("bitmap空的=====");
                    }

                    MyLogUtils.info("mArrowImageView消失=====");
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    dismiss();
                }
            });
            ((ViewPager) container).addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }

    /**
     *
     */
    private void showPopupWindow(View parent, final Bitmap bitmap) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.dialog_gc2, null);
        // 实例化popupWindow
        popupWindow = new PopupWindow(layout, AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //s设置进出动画
        popupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        //获取xoff
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        TextView tv_jb = (TextView) layout.findViewById(R.id.tv_jb);
        tv_jb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBitmapUtils.saveImageToGallery(context,bitmap);
                popupWindow.dismiss();
            }
        });
    }

    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }


}

