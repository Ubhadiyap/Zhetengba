package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子--我的发布界面
 * Created by xiaoke on 2016/5/6.
 */
public class MyPlaneAct extends BaseActivity {
    private ListView lv_my_plane;
    private List<List<ImageInfo>> datalist=new ArrayList<>();
    private String[][] images=new String[][]{{
            "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1402/12/c1/31189058_1392186616852.jpg","1624","914"}
            ,{"file:///android_asset/img2.jpg","250","250"}
            ,{"file:///android_asset/img3.jpg","250","250"}
            ,{"file:///android_asset/img4.jpg","250","250"}
            ,{"file:///android_asset/img5.jpg","250","250"}
            ,{"file:///android_asset/img6.jpg","250","250"}
            ,{"file:///android_asset/img7.jpg","250","250"}
            ,{"file:///android_asset/img8.jpg","250","250"}
            ,{"http://img3.douban.com/view/photo/raw/public/p1708880537.jpg","1280","800"}
    };
    @Override
    public void setLayout() {
        setContentView(R.layout.act_my_plane);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的发布");
        lv_my_plane= (ListView) findViewById(R.id.lv_my_plane);
        initData();
        CircleAdapter adapter=new CircleAdapter(getApplicationContext(),datalist);
       lv_my_plane.setAdapter(adapter);
    }
    private void initData() {
        datalist=new ArrayList<>();
        //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        ArrayList<ImageInfo> singleList=new ArrayList<>();
        singleList.add(new ImageInfo(images[8][0],Integer.parseInt(images[8][1]),Integer.parseInt(images[8][2])));
        datalist.add(singleList);
        //从一到9生成9条朋友圈内容，分别是1~9张图片
        for(int i=0;i<9;i++){
            ArrayList<ImageInfo> itemList=new ArrayList<>();
            for(int j=0;j<=i;j++){
                itemList.add(new ImageInfo(images[j][0],Integer.parseInt(images[j][1]),Integer.parseInt(images[j][2])));
            }
            datalist.add(itemList);
        }
    }
}
