package com.boyuanitsm.zhetengba.activity.circle;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ChaTextAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.utils.ScreenTools;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.NineGridlayout;
import com.leaf.library.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道正文
 * Created by xiaoke on 2016/5/11.
 */
public class ChanelTextAct extends BaseActivity {
    private MyListView my_lv;
    private ScrollView sl_chanel;
    private NineGridlayout ng_chanel;
    private CustomImageView ng_one_image;
    private List<ArrayList<ImageInfo>> dataList=new ArrayList<>();
    private String[][] images=new String[][]{{
            ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL,"250","250"}
            ,{ConstantValue.IMAGEURL,"250","250"}
            ,{ConstantValue.IMAGEURL,"250","250"}
            ,{ConstantValue.IMAGEURL,"250","250"}
            ,{ConstantValue.IMAGEURL,"1280","800"}
    };
    @Override
    public void setLayout() {
        setContentView(R.layout.act_chanel_text);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("频道正文");
         my_lv = (MyListView) findViewById(R.id.my_lv);
       sl_chanel = (ScrollView) findViewById(R.id.sl_chanel);
        ng_chanel = (NineGridlayout) findViewById(R.id.ng_chanel);
        ng_one_image = (CustomImageView) findViewById(R.id.ng_one_image);
        initDate();
        ChaTextAdapter adapter=new ChaTextAdapter(getApplicationContext());
        my_lv.setAdapter(adapter);
        sl_chanel.smoothScrollTo(0, 0);
    }

    private void initDate() {
        dataList=new ArrayList<>();
        //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        ArrayList<ImageInfo> singleList=new ArrayList<>();
        singleList.add(new ImageInfo(images[8][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));
        dataList.add(singleList);
        if (singleList.isEmpty() || singleList.isEmpty()) {
            ng_chanel.setVisibility(View.GONE);
            ng_one_image.setVisibility(View.GONE);
        } else if (singleList.size() == 1) {
            ng_chanel.setVisibility(View.GONE);
            ng_one_image.setVisibility(View.VISIBLE);
            handlerOneImage(singleList.get(0));
        } else {
            ng_chanel.setVisibility(View.VISIBLE);
            ng_one_image.setVisibility(View.GONE);
            ng_chanel.setImagesData(singleList);
        }
    }

    /***
     * 根据图片宽高自动设置布局
     * @param image
     */
    private void handlerOneImage(ImageInfo image) {
        int totalWidth;
        int imageWidth;
        int imageHeight;
        ScreenTools screentools = ScreenTools.instance(getApplicationContext());
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
        ViewGroup.LayoutParams layoutparams = ng_one_image.getLayoutParams();
        layoutparams.height = imageHeight;
        layoutparams.width = imageWidth;
        ng_one_image.setLayoutParams(layoutparams);
        ng_one_image.setClickable(true);
        ng_one_image.setScaleType(ImageView.ScaleType.FIT_XY);
        ng_one_image.setImageUrl(image.getUrl());

    }
}
