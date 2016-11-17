package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CityBean;
import com.boyuanitsm.zhetengba.view.PinnedHeaderListView;
import com.boyuanitsm.zhetengba.view.city.BladeCityView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 城市选择界面
 * Created by bitch-1 on 2016/11/17.
 */
public class cityAct extends BaseActivity {
    @ViewInject(R.id.tv_wc)
    private TextView tv_wc;//完成按钮

    @ViewInject(R.id.et_sh)
    private EditText et_sh;//收索框

    @ViewInject(R.id.plv)//城市列表listview
    private PinnedHeaderListView mListView;

    @ViewInject(R.id.serch_plv)
    private ListView serch_plv;//收索结果listview

    @ViewInject(R.id.mLetterListView)//右边导航栏
    private BladeCityView mLetter;


    private List<String> mSections;// 首字母集
    private Map<String, List<CityBean>> mMap;// 根据首字母存放数据
    private List<Integer> mPositions; // 首字母位置集 在listview中
    private Map<String, Integer> mIndexer;// 首字母对应的位置


    private String locCityName;//当前定位城市从首页传过来的
    public static final String CITYNAME = "city_name";//定位的城市


    @Override
    public void setLayout() {
        setContentView(R.layout.act_city);

    }

    @Override
    public void init(Bundle savedInstanceState) {

        mLetter.setOnItemClickListener(new BladeCityView.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
               if(mIndexer.get(s)!=null){
                   mListView.setSelection(mIndexer.get(s));
               }
            }
        });

        mSections = new ArrayList<>();
        mMap = new HashMap<>();
        mPositions = new ArrayList<>();
        mIndexer = new HashMap<>();
//        listHistorys = CityDao.findUseData();
        locCityName = getIntent().getStringExtra(CITYNAME);
        getCityList();//获取城市列表


    }

    /**
     * 获取城市列表
     * 还没确定好是取本地的还是掉后台的
     */
    private void getCityList() {

    }
}
