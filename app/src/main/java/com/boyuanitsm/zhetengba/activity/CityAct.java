package com.boyuanitsm.zhetengba.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.CityListAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CityBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.DBHelper;
import com.boyuanitsm.zhetengba.db.DatabaseHelper;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.fragment.calendarFrg.SimpleFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.view.PinnedHeaderListView;
import com.boyuanitsm.zhetengba.view.city.BladeCityView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 城市选择界面
 * Created by bitch-1 on 2016/11/17.
 */
public class CityAct extends BaseActivity {
//    @ViewInject(R.id.tv_wc)
//    private TextView tv_wc;//完成按钮

    @ViewInject(R.id.et_sh)
    private EditText et_sh;//收索框

    @ViewInject(R.id.plv)//城市列表listview
    private PinnedHeaderListView mListView;

    @ViewInject(R.id.serch_plv)
    private ListView serch_plv;//收索结果listview

    @ViewInject(R.id.mLetterListView)//右边导航栏
    private BladeCityView mLetter;

    @ViewInject(R.id.tv_noresult)
    private TextView tv_noresult;//无收索内容


    private ResultListAdapter resultListAdapter;//收索适配器
    private ArrayList<CityBean> city_result;//收索城市
    private DatabaseHelper helper;//历史数据库helper


    List<CityBean> list;//从本地获取的城市列表
    private List<CityBean> listHots = new ArrayList<>();//热门城市
    private String locationCity;//定位城市名
    private static final String FORMAT = "^[a-z,A-Z].*$";
    private CityListAdapter mAdapter;
    // 首字母集
    private List<String> mSections;
    // 根据首字母存放数据
    private Map<String, List<CityBean>> mMap;
    // 首字母位置集
    private List<Integer> mPositions;
    // 首字母对应的位置
    private Map<String, Integer> mIndexer;//点击右边的首字母调到到相应的首字母位置


    private String locCityName;//当前定位城市从首页传过来的
    public static final String CITYNAME = "city_name";//定位的城市

    private UserInfo user;//根据user里面的city(是城市id)然后匹配本地数据库读出城市名称


    @Override
    public void setLayout() {
        setContentView(R.layout.act_city);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        user = UserInfoDao.getUser();
        city_result = new ArrayList<CityBean>();//收索城市接口
        helper = new DatabaseHelper(this);//超做历史记录的数据库(这里可以暂时不要)

        //收索监听
        et_sh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString() == null || "".equals(s.toString())) {
                    mLetter.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.VISIBLE);
                    serch_plv.setVisibility(View.GONE);
                    tv_noresult.setVisibility(View.GONE);
                } else {
                    city_result.clear();
                    mLetter.setVisibility(View.GONE);
                    mListView.setVisibility(View.GONE);
                    getResultCityList(s.toString());//获取收索返回来的结构
                    if (city_result.size() <= 0) {
                        tv_noresult.setVisibility(View.VISIBLE);
                        serch_plv.setVisibility(View.GONE);
                    } else {
                        tv_noresult.setVisibility(View.GONE);
                        serch_plv.setVisibility(View.VISIBLE);
                        resultListAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //收索返回来的接口
        resultListAdapter = new ResultListAdapter(this, city_result);
        serch_plv.setAdapter(resultListAdapter);
        serch_plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        city_result.get(position).getName(), Toast.LENGTH_SHORT)
//                        .show();
                user.setCity(city_result.get(position).getCityid());
                modifyUser(user);//选择收索出来的城市后把它传给后台
                finish();

            }
        });


        mLetter.setOnItemClickListener(new BladeCityView.OnItemClickListener() {

            @Override
            public void onItemClick(String s) {
                if (mIndexer.get(s) != null) {
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
        initData(list);//初始化列表


    }

    /**
     * 初始化
     *
     * @param list
     */
    private void initData(List<CityBean> list) {
        List<CityBean> listLocation = new ArrayList<>();//定位的id
        CityBean locationBean = new CityBean();
        //实例化SharedPreferences对象（第一步）
        SharedPreferences sharedPreferences = getSharedPreferences("ztb_City",
                Activity.MODE_PRIVATE);
        locationCity=sharedPreferences.getString("city_location","");
        if (TextUtils.isEmpty(locationCity)) {
            locationBean.setName("定位失败");
        } else {
            locationBean.setName(locationCity);
        }
        locationBean.setPinyi("当前定位城市");
//        locationBean.setLatitude(GlobalParams.m_latitude + "");
//        locationBean.setLongitude(GlobalParams.m_longitude + "");
        locationBean.setCityid(115 + "");
        listLocation.add(locationBean);


        listHots = new ArrayList<>();
        listHots.add(0, new CityBean("131", "北京", "0"));//城市编码，名称，拼音
        listHots.add(1, new CityBean("289", "上海", "0"));
        listHots.add(2, new CityBean("257", "广州", "0"));
        listHots.add(3, new CityBean("340", "深圳", "0"));
        listHots.add(4, new CityBean("315", "南京", "0"));
        listHots.add(5, new CityBean("179", "杭州", "0"));
        listHots.add(6, new CityBean("75", "成都", "0"));
        listHots.add(7, new CityBean("218", "武汉", "0"));
        List<CityBean> hotList = new ArrayList<>();

        if (listHots.size() > 0) {
            CityBean cbH = new CityBean();
            cbH.setPinyi("热门");
            hotList.add(cbH);
            list.addAll(0, hotList);
        }

        list.addAll(0, listLocation);//把定位城市加到listview去

        for (int i = 0; i < list.size(); i++) {
            String firstName = list.get(i).getPinyi().substring(0, 1).toString().toUpperCase();
            if (firstName.matches(FORMAT)) {
                if (mSections.contains(firstName)) {
                    mMap.get(firstName).add(list.get(i));
                } else {
                    mSections.add(firstName);
                    List<CityBean> mlist = new ArrayList<CityBean>();
                    mlist.add(list.get(i));
                    mMap.put(firstName, mlist);
                }
            }


        }
        Collections.sort(mSections);


        /**
         * 热
         */
        List<String> mHotList = new ArrayList<String>();
        if (hotList != null && hotList.size() > 0) {
            for (int i = 0; i < hotList.size(); i++) {
                if (mHotList.contains("热门")) {
                    mMap.get("热门").add(hotList.get(i));
                } else {
                    mHotList.add("热门");
                    List<CityBean> mlist = new ArrayList<CityBean>();
                    mlist.add(hotList.get(i));
                    mMap.put("热门", mlist);
                }
            }
        }

        List<String> locationList = new ArrayList<String>();
        locationList.add("当前定位城市");
        mMap.put("当前定位城市", listLocation);

        if (mHotList.size() > 0)
            mSections.addAll(0, mHotList);
        mSections.addAll(0, locationList);

        int position = 0;
        for (int i = 0; i < mSections.size(); i++) {
            mIndexer.put(mSections.get(i), position);// 存入map中，key为首字母字符串，value为首字母在listview中位置
            mPositions.add(position);// 首字母在listview中位置，存入list中
            position += mMap.get(mSections.get(i)).size();// 计算下一个首字母在listview的位置
        }

        if (mAdapter == null) {
            mAdapter = new CityListAdapter(this, list, mSections, mPositions, listHots);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyData(list, mSections, mPositions, listHots);
        }

        mListView.setOnScrollListener(mAdapter);
        mListView.setPinnedHeaderView(LayoutInflater.from(this).inflate(
                R.layout.listview_head, mListView, false));
    }


    /**
     * 收索城市
     *
     * @param keyword
     */
    private void getResultCityList(String keyword) {
        DBHelper dbHelper = new DBHelper(this);
        try {
            dbHelper.createDataBase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(
                    "select * from city where name like \"%" + keyword
                            + "%\" or pinyin like \"%" + keyword + "%\"", null);
            CityBean city;
            Log.e("info", "length = " + cursor.getCount());
            while (cursor.moveToNext()) {
                city = new CityBean(cursor.getString(3), cursor.getString(1), cursor.getString(2));
                city_result.add(city);
            }
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(city_result, comparator);
    }

    /**
     * a-z排序
     */
    @SuppressWarnings("rawtypes")
    Comparator comparator = new Comparator<CityBean>() {
        @Override
        public int compare(CityBean lhs, CityBean rhs) {
            String a = lhs.getPinyi().substring(0, 1);
            String b = rhs.getPinyi().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    /**
     * 获取城市列表
     * 还没确定好是取本地的还是掉后台的
     */
    private List<CityBean> getCityList() {
        DBHelper dbHelper = new DBHelper(this);
        list = new ArrayList<CityBean>();
        try {
            dbHelper.createDataBase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from city", null);
            CityBean city;
            while (cursor.moveToNext()) {
                city = new CityBean(cursor.getString(3), cursor.getString(1), cursor.getString(2));
                list.add(city);
            }
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(list, comparator);
        return list;
    }

    /**
     * 收索出来的适配器
     */
    private class ResultListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<CityBean> results = new ArrayList<CityBean>();

        public ResultListAdapter(Context context, ArrayList<CityBean> results) {
            inflater = LayoutInflater.from(context);
            this.results = results;
        }

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(results.get(position).getName());
            return convertView;
        }

        class ViewHolder {
            TextView name;
        }
    }


    /**
     * 修改个人资料
     *
     * @param user
     */
    private void modifyUser(final UserInfo user) {
        RequestManager.getUserManager().modifyUserInfo(user, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                UserInfoDao.updateUser(user);//成功后更新数据并通知变化首页左上角城市
                sendBroadcast(new Intent(SimpleFrg.UPDATA_CITY_RES));
            }
        });
    }

}
