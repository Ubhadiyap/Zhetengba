package com.boyuanitsm.zhetengba.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;



import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.publish.ContractedAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;


import com.boyuanitsm.zhetengba.bean.SuggestionInfoBean;
import com.boyuanitsm.zhetengba.view.scan.Intents;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**发档期选择地址时候收索
 * Created by bitch-1 on 2016/11/29.
 */
public class SerchcityAct extends BaseActivity {
    @ViewInject(R.id.tv_city)
    private TextView tv_city;//城市
    @ViewInject(R.id.et_sh)
    private EditText et_sh;//收索框
    @ViewInject(R.id.serch_plv)
    private ListView serch_plv;
    private SuggestionSearch mSuggestionSearch;
    private List<SuggestionResult.SuggestionInfo>infos;//收索结果
    private ResultAdapter adapter;
    private String cityName;
    private String cityCode;
    private String locationCity;//定位城市
    @Override

    public void setLayout() {
        setContentView(R.layout.act_serchcity);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        infos=new ArrayList<SuggestionResult.SuggestionInfo>();
        //初识化在线建议查询
        initPoiSearch();
        //实例化SharedPreferences对象（第一步）
        SharedPreferences sharedPreferences = getSharedPreferences("ztb_City",
                Activity.MODE_PRIVATE);
        locationCity=sharedPreferences.getString("city_location","");
        cityCode=sharedPreferences.getString("cityCode","");
        tv_city.setText(locationCity);
        et_sh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString() != null && !"".equals(s.toString())) {
                    mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).city(tv_city.getText().toString().trim()).keyword(s.toString()));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(et_sh.getText().toString())){
                    if(infos!=null){
                    infos.clear();
                    adapter.notify(infos);}
                    serch_plv.setVisibility(View.GONE);//这里做了个欺骗因为这里快速清空输入框没达到效果
                }

            }
        });
        serch_plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSuggestionSearch.destroy();
//                SuggestionResult.SuggestionInfo suggestionInfo= (SuggestionResult.SuggestionInfo) parent.getItemAtPosition(position);
                SuggestionResult.SuggestionInfo info= (SuggestionResult.SuggestionInfo) parent.getItemAtPosition(position);
                SuggestionInfoBean suggestionInfo=new SuggestionInfoBean();
                suggestionInfo.setKey(info.key);
                suggestionInfo.setCity(info.city);
                suggestionInfo.setDistrict(info.district);
                suggestionInfo.setPt(info.pt);
                suggestionInfo.setUid(info.uid);
                Intent intent=new Intent();
                intent.setAction(ContractedAct.UPDATA_ET);
                intent.putExtra("suggestionInfo", suggestionInfo);
                intent.putExtra("cityCode",cityCode);
                sendBroadcast(intent);
                finish();

            }
        });

    }


    //初识化检索
    private void initPoiSearch() {
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);

    }

    @OnClick({R.id.rl_citychang})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.rl_citychang:
                Intent intent=new Intent(SerchcityAct.this,CityAct.class);
                intent.putExtra("Citytype", 1);
                startActivityForResult(intent, 0);
//                openActivity(CityAct.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0){
            if (data!=null){
                cityName = data.getStringExtra("CityName");
                cityCode = data.getStringExtra("CityCode");
                tv_city.setText(cityName+"市");
            }

        }

    }

    /**
     * 收索出来的适配器
     */
    private class ResultAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<SuggestionResult.SuggestionInfo> results = new ArrayList<SuggestionResult.SuggestionInfo>();

        public ResultAdapter(Context context, List<SuggestionResult.SuggestionInfo> results) {
            inflater = LayoutInflater.from(context);
            this.results = results;
        }
        public void notify(List<SuggestionResult.SuggestionInfo> results ) {
            this.results = results;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object getItem(int position) {
            return results.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (!TextUtils.isEmpty(infos.get(position).city) || !TextUtils.isEmpty(infos.get(position).district))
                viewHolder.name.setText(
                        infos.get(position).key + "(" + infos.get(position).city + infos.get(position).district + ")");
            else
                viewHolder.name.setText(infos.get(position).key);

            return convertView;
        }

        class ViewHolder {
            TextView name;
        }

    }

    private OnGetSuggestionResultListener listener =new OnGetSuggestionResultListener(){

        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
                return;
                //未找到相关结果
            }else {
                serch_plv.setVisibility(View.VISIBLE);
                infos.clear();
                List<SuggestionResult.SuggestionInfo> suggestionInfos = suggestionResult.getAllSuggestions();
                for (int i = 0; i < suggestionInfos.size(); i++) {
                    if (!TextUtils.isEmpty(suggestionInfos.get(i).key) && suggestionInfos.get(i).pt != null) {
                        infos.add(suggestionInfos.get(i));//筛选结果，只有关键字和经纬度同时获取到的才显示在列表中
                    }
                }
                if(infos!=null){
                    adapter=new ResultAdapter(SerchcityAct.this,infos);
                    serch_plv.setAdapter(adapter);
                }

            }

            }
        };

    }

