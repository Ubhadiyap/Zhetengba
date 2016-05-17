package com.boyuanitsm.zhetengba.activity.circle;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.adapter.CirxqAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.view.MyListview;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 圈子详情界面
 * Created by bitch-1 on 2016/5/11.
 */
public class CirxqAct extends BaseActivity {
    @ViewInject(R.id.rv_label)
    private RecyclerView rv_label;
    @ViewInject(R.id.lv_cirxq)
    private MyListview lv_cir;
    @ViewInject(R.id.cir_sv)
    private ScrollView cir_sv;
    @ViewInject(R.id.cir_fb)
    private TextView cir_fb;
    private List<Integer>list;

    private CirxqAdapter adapter;


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
        setContentView(R.layout.act_cirxq);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("互联网创业");
        initData();
        list = new ArrayList<Integer>(Arrays.asList(R.mipmap.cirxq_l,R.mipmap.cirxq_lb,R.mipmap.cirxq_lbb,R.mipmap.cirxq_l,R.mipmap.cirxq_lb));
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        //设置横向
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rv_label.setLayoutManager(linearLayoutManager);
        cir_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(CirclefbAct.class);
            }
        });
        adapter=new CirxqAdapter(getApplicationContext(),list);
        rv_label.setAdapter(adapter);
        adapter.setOnItemClickListener(new CirxqAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 5) {
//                    MyToastUtils.showShortToast(getApplicationContext(),"hah");
                    openActivity(CircleppAct.class);
                }else
                MyToastUtils.showShortToast(getApplicationContext(),"gaga");
            }
        });

        //listview设置适配器
        cir_sv.smoothScrollTo(0, 0);
        lv_cir.setAdapter(new CircleAdapter(getApplicationContext(),datalist));}



    private void initData() {
        datalist=new ArrayList<>();
        //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        ArrayList<ImageInfo> singleList=new ArrayList<>();
        singleList.add(new ImageInfo(images[0][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));
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
