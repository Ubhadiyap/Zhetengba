package com.boyuanitsm.zhetengba.activity.publish;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.MyPlaneAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshBase;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子--我的发布界面
 * Created by xiaoke on 2016/5/6.
 */
public class MyPlaneAct extends BaseActivity {
    private PullToRefreshListView lv_my_plane;
    private List<List<ImageInfo>> datalist;
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

    private List<CircleEntity> list;
    private int page=1;
    private int rows=10;
    private MyPlaneAdapter adapter;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_my_plane);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的发布");
        lv_my_plane= (PullToRefreshListView) findViewById(R.id.lv_my_plane);
        //初始化下拉刷新
        LayoutHelperUtil.freshInit(lv_my_plane);
        getMyTalks(page,rows);
//        datalist=new ArrayList<>();
//        initData();
//        MyPlaneAdapter adapter=new MyPlaneAdapter(MyPlaneAct.this,datalist);
//       lv_my_plane.getRefreshableView().setAdapter(adapter);
        lv_my_plane.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lv_my_plane.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
                page=1;
                getMyTalks(page,rows);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getMyTalks(page,rows);
            }
        });
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

    private List<CircleEntity> datas=new ArrayList<>();
    /**
     * 我的发布
     * @param page
     * @param rows
     */
    private void getMyTalks(final int page, int rows){
        list=new ArrayList<>();
        datalist=new ArrayList<>();

        RequestManager.getTalkManager().myTalksOut(page, rows, new ResultCallback<ResultBean<DataBean<CircleEntity>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                lv_my_plane.onPullUpRefreshComplete();
                lv_my_plane.onPullDownRefreshComplete();
            }

            @Override
            public void onResponse(ResultBean<DataBean<CircleEntity>> response) {
                lv_my_plane.onPullUpRefreshComplete();
                lv_my_plane.onPullDownRefreshComplete();
                list=response.getData().getRows();
                if (list.size()==0){
                    if (page==1){

                    }else {
                        lv_my_plane.setHasMoreData(false);
                    }
                    return;
                }
                if (page==1){
                    datas.clear();
                }
                datas.addAll(list);
                for (int j=0;j<datas.size();j++) {
                    List<ImageInfo> itemList=new ArrayList<>();
                    //将图片地址转化成数组
                    if(!TextUtils.isEmpty(datas.get(j).getTalkImage())) {
                        String[] urlList = ZtinfoUtils.convertStrToArray(datas.get(j).getTalkImage());
                        for (int i = 0; i < urlList.length; i++) {
                            itemList.add(new ImageInfo(Uitls.imageFullUrl(urlList[i]), 1624, 914));
                        }
                    }
                    datalist.add(itemList);
                }
                if (adapter==null){
                    adapter=new MyPlaneAdapter(MyPlaneAct.this,datalist,datas);
                    lv_my_plane.getRefreshableView().setAdapter(adapter);
                }else {
                    adapter.notifyChange(datalist,datas);
                }

            }
        });
    }
}
