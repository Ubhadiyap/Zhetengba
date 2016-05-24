package com.boyuanitsm.zhetengba.activity.circle;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ChaTextAdapter;
import com.boyuanitsm.zhetengba.adapter.CirxqAdapter;
import com.boyuanitsm.zhetengba.adapter.PicGdAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.view.PicShowDialog;
import com.leaf.library.widget.MyListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道正文
 * Created by xiaoke on 2016/5/11.
 */
public class ChanelTextAct extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.et_comment)//评论编辑框
    private EditText et_comment;
    private MyListView my_lv;
    private ScrollView sl_chanel;
    private LinearLayout ll_two;
    private CustomImageView ng_one_image, iv_two_one, iv_two_two, iv_two_three, iv_two_four;
    private MyGridView iv_ch_image;
    private List<ArrayList<ImageInfo>> dataList = new ArrayList<>();
    private String[][] images = new String[][]{{
            ConstantValue.IMAGEURL, "1624", "914"}
            , {ConstantValue.IMAGEURL, "1624", "914"}
            , {ConstantValue.IMAGEURL, "1624", "914"}
            , {ConstantValue.IMAGEURL, "1624", "914"}
            , {ConstantValue.IMAGEURL, "250", "250"}
            , {ConstantValue.IMAGEURL, "250", "250"}
            , {ConstantValue.IMAGEURL, "250", "250"}
            , {ConstantValue.IMAGEURL, "250", "250"}
            , {ConstantValue.IMAGEURL, "1280", "800"}
    };
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.zanwutupian)
            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    @Override
    public void setLayout() {
        setContentView(R.layout.act_chanel_text);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("频道正文");
        assignView();
        initDate();
        ChaTextAdapter adapter = new ChaTextAdapter(this);
        my_lv.setAdapter(adapter);
        sl_chanel.smoothScrollTo(0, 0);

        my_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ll_answer.setVisibility(View.VISIBLE);
//                et_comment.setFocusable(true);
//                et_comment.setFocusableInTouchMode(true);
//                et_comment.requestFocus();
//                et_comment.requestFocusFromTouch();
//              view= (View) parent.getItemAtPosition(0);
//             TextView user_name= (TextView) view.findViewById(R.id.tv_user_name);
//               String  str_nam = user_name.getText().toString();
//                et_comment.setText("回复"+str_nam+"：");


            }
        });
    }

    private void assignView() {
        my_lv = (MyListView) findViewById(R.id.my_lv);
        sl_chanel = (ScrollView) findViewById(R.id.sl_chanel);
        iv_ch_image = (MyGridView) findViewById(R.id.iv_ch_image);
        ng_one_image = (CustomImageView) findViewById(R.id.ng_one_image);
        ll_two = (LinearLayout) findViewById(R.id.ll_two);
        iv_two_one = (CustomImageView) findViewById(R.id.iv_two_one);
        iv_two_two = (CustomImageView) findViewById(R.id.iv_two_two);
        iv_two_three = (CustomImageView) findViewById(R.id.iv_two_three);
        iv_two_four = (CustomImageView) findViewById(R.id.iv_two_four);
    }

    private void initDate() {
        dataList = new ArrayList<>();
        //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        final ArrayList<ImageInfo> singleList = new ArrayList<>();
        singleList.add(new ImageInfo(images[8][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));
        dataList.add(singleList);
        singleList.add(new ImageInfo(images[8][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));
        dataList.add(singleList);
        singleList.add(new ImageInfo(images[8][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));
        dataList.add(singleList);
        if (singleList.isEmpty() || singleList.isEmpty()) {
            ll_two.setVisibility(View.GONE);
            ng_one_image.setVisibility(View.GONE);
            iv_ch_image.setVisibility(View.GONE);
        } else if (singleList.size() == 1) {
            ll_two.setVisibility(View.GONE);
            iv_ch_image.setVisibility(View.GONE);
            ng_one_image.setVisibility(View.VISIBLE);


            LayoutHelperUtil.handlerOneImage(getApplicationContext(), singleList.get(0), ng_one_image);

            ng_one_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, singleList, 0);
                    dialog.show();
                }
            });
        } else if (singleList.size() == 4) {
            iv_ch_image.setVisibility(View.GONE);
            ng_one_image.setVisibility(View.GONE);
            ll_two.setVisibility(View.VISIBLE);
//            viewHolder.iv_two_four.setImageUrl(itemList.get(3).getUrl());
            ImageLoader.getInstance().displayImage(singleList.get(0).getUrl(), iv_two_one, optionsImag);
            ImageLoader.getInstance().displayImage(singleList.get(1).getUrl(), iv_two_two, optionsImag);
            ImageLoader.getInstance().displayImage(singleList.get(2).getUrl(), iv_two_three, optionsImag);
            ImageLoader.getInstance().displayImage(singleList.get(3).getUrl(), iv_two_four, optionsImag);
            iv_two_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, singleList, 0);
                    dialog.show();
                }
            });

            iv_two_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, singleList, 1);
                    dialog.show();
                }
            });

            iv_two_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, singleList, 2);
                    dialog.show();
                }
            });

            iv_two_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PicShowDialog dialog = new PicShowDialog(ChanelTextAct.this, singleList, 3);
                    dialog.show();
                }
            });

        } else {
            ng_one_image.setVisibility(View.GONE);
            ll_two.setVisibility(View.GONE);
            iv_ch_image.setVisibility(View.VISIBLE);
            iv_ch_image.setNumColumns(3);
            PicGdAdapter adapter = new PicGdAdapter(ChanelTextAct.this, singleList);
            iv_ch_image.setAdapter(adapter);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

}
