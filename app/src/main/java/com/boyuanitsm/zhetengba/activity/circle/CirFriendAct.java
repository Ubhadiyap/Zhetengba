package com.boyuanitsm.zhetengba.activity.circle;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CirFriGvAdapter;
import com.boyuanitsm.zhetengba.adapter.CirFriGvSecAdapter;
import com.boyuanitsm.zhetengba.adapter.CirListViewAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子，圈友主页
 * Created by xiaoke on 2016/5/12.
 */
public class CirFriendAct extends BaseActivity {
    private GridView cir_fri_gv;
    private GridView cir_fri_com;
    private PullToRefreshListView cir_fri_plv;
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
        setContentView(R.layout.act_cir_friend);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈友主页");//塞入返回的昵称
        cir_fri_plv = (PullToRefreshListView) findViewById(R.id.cir_fri_plv);
        View v=getLayoutInflater().inflate(R.layout.act_cir_friend_head,null);
        cir_fri_plv.setPullRefreshEnabled(true);//下拉刷新
        cir_fri_plv.setScrollLoadEnabled(true);//滑动加载
        cir_fri_plv.setPullLoadEnabled(false);//上拉刷新
        cir_fri_plv.setHasMoreData(true);//是否有更多数据
        cir_fri_plv.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        cir_fri_plv.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        cir_fri_plv.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
        cir_fri_plv.getRefreshableView().setDivider(null);
        cir_fri_plv.getRefreshableView().addHeaderView(v);
        cir_fri_gv = (GridView) v.findViewById(R.id.cir_fri_gv);
        cir_fri_com = (GridView)v.findViewById(R.id.cir_fri_com);
        CirFriGvAdapter adapter=new CirFriGvAdapter(getApplicationContext());
        CirFriGvSecAdapter adapter1=new CirFriGvSecAdapter(getApplicationContext());
        cir_fri_gv.setAdapter(adapter);
        cir_fri_com.setAdapter(adapter1);
        initData();
        CirListViewAdapter lvadapter=new CirListViewAdapter(this,datalist);
        cir_fri_plv.getRefreshableView().setAdapter(lvadapter);
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
