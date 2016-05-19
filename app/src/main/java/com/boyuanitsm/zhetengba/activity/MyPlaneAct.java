package com.boyuanitsm.zhetengba.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.adapter.MyPlaneAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子--我的发布界面
 * Created by xiaoke on 2016/5/6.
 */
public class MyPlaneAct extends BaseActivity {
    private PullToRefreshListView lv_my_plane;
    private List<List<ImageInfo>> datalist=new ArrayList<>();
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
        setContentView(R.layout.act_my_plane);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的发布");
        lv_my_plane= (PullToRefreshListView) findViewById(R.id.lv_my_plane);
        lv_my_plane.setPullRefreshEnabled(true);//下拉刷新
        lv_my_plane.setScrollLoadEnabled(true);//滑动加载
        lv_my_plane.setPullLoadEnabled(false);//上拉刷新
        lv_my_plane.setHasMoreData(true);//是否有更多数据
        lv_my_plane.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        lv_my_plane.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        lv_my_plane.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
        lv_my_plane.getRefreshableView().setDivider(new ColorDrawable(Color.parseColor("#e1e1e1")));
        lv_my_plane.getRefreshableView().setDividerHeight(1);
        initData();
        MyPlaneAdapter adapter=new MyPlaneAdapter(MyPlaneAct.this,datalist);
       lv_my_plane.getRefreshableView().setAdapter(adapter);
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
