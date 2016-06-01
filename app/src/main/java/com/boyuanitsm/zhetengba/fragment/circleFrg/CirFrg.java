package com.boyuanitsm.zhetengba.fragment.circleFrg;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CircleAdapter;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 子界面-圈子界面
 * Created by xiaoke on 2016/5/2.
 */
public class CirFrg extends Fragment {
    private int[] icons = {R.drawable.test_banner, R.drawable.test_chanel, R.drawable.test_chanel, R.drawable.test_chanel};
    private List<List<ImageInfo>> datalist=new ArrayList<>();
    private String[][] images=new String[][]{{
            ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL,"1624","914"}
            ,{ConstantValue.IMAGEURL2,"1624","914"}
            ,{ConstantValue.IMAGEURL3,"1624","914"}
            ,{ConstantValue.IMAGEURL4,"250","250"}
            ,{ConstantValue.IMAGEURL5,"250","250"}
            ,{ConstantValue.IMAGEURL4,"250","250"}
            ,{ConstantValue.IMAGEURL3,"250","250"}
            ,{ConstantValue.IMAGEURL5,"1280","800"}
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cir_frg, null);
        PullToRefreshListView lv_cir = (PullToRefreshListView) view.findViewById(R.id.lv_cir);
        initData();
        CircleAdapter adapter=new CircleAdapter(getContext(),datalist);
        LayoutHelperUtil.freshInit(lv_cir);
        lv_cir.getRefreshableView().setAdapter(adapter);
        return view;
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
