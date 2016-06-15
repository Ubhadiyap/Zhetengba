package com.boyuanitsm.zhetengba.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.LabelGVadapter;
import com.boyuanitsm.zhetengba.adapter.LabelGvMyadapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;
import com.boyuanitsm.zhetengba.db.LabelInterestDao;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.fragment.circleFrg.ChanelFrg;
import com.boyuanitsm.zhetengba.fragment.MineFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签管理
 * Created by xiaoke on 2016/5/27.
 */
public class LabelMangerAct extends BaseActivity {
    @ViewInject(R.id.gv1)
    private GridView gv1;
    @ViewInject(R.id.gv2)
    private GridView gv2;
    @ViewInject(R.id.tvlabel)
    private TextView tvlabel;
    @ViewInject(R.id.ll_all_label)
    private LinearLayout ll_all_label;
    private List<LabelBannerInfo> list=new ArrayList<LabelBannerInfo>();
    private List<UserInterestInfo> mylist=new ArrayList<>();
    private UserInterestInfo userInterestInfo;
    private LabelBannerInfo labelBannerInfo;
    private LabelGVadapter labelGVadapter;
    private LabelGvMyadapter myadapter;
    private String labelids;//接口传入参数
    private String userId;//用户Id
    private LabelInterestDao labelInterestDao;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_labelmana2);
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        labelInterestDao=new LabelInterestDao(LabelMangerAct.this);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle!=null){
         userId= bundle.getString("userId");
            if (UserInfoDao.getUser().getId().equals(userId)){
                instalLabelData();
            }else {
                otherInterestLabel(userId);
            }
        }else {
            instalLabelData();
        }

    }

    /**
     * 查询他人兴趣标签数据初始化
     * @param userId
     */
    private void otherInterestLabel(String userId) {
        setTopTitle("兴趣标签");
        tvlabel.setText("他的标签");
        ll_all_label.setVisibility(View.GONE);
        getOtherInterestLabel(userId);
    }

    /**
     * 用户本人点击标签管理
     */
    private void instalLabelData() {
        setTopTitle("标签管理");
        tvlabel.setText("我的标签");
        setRight("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mylist == null) {
                    MyToastUtils.showShortToast(LabelMangerAct.this, "至少选择一个兴趣标签");
                } else if (mylist.size() == 1) {
                    labelids = mylist.get(0).getInterestId();
                    addInterestLabel(labelids);
                } else if (mylist.size() > 1) {
                    labelids = mylist.get(0).getInterestId();
                    for (int i = 1; i < mylist.size(); i++) {
                        labelids = labelids + "," + mylist.get(i).getInterestId();
                    }

//                    LabelInterestDao labelInfoDao=new LabelInterestDao(LabelMangerAct.this);
//                    labelInfoDao.save(mylist);
//                    for (int i=0;i<mylist.size();i++){
//                        labelInterestDao.save(mylist.get(i));
//                    }
                    addInterestLabel(labelids);
                }

            }
        });
        ll_all_label.setVisibility(View.VISIBLE);
//        添加标签到全部标签
        getIntrestLabel("0");
        getMyInterestLabel();
        updata();
    }

    private void updata() {
        gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LabelBannerInfo str = list.get(position);
                userInterestInfo = new UserInterestInfo(LabelMangerAct.this);
                userInterestInfo.setInterestId(str.getId());
                userInterestInfo.setDictName(str.getDictName());
//                    labelInterestDao.save(userInterestInfo);
                LabelInterestDao.saveInterestLabel(userInterestInfo);
                mylist.add(userInterestInfo);
                myadapter.update(mylist);
                list.remove(position);
                labelGVadapter.update(list);

            }
        });
        gv1.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInterestInfo str = mylist.get(position);
                labelInterestDao.delete(str.getId());
                mylist.remove(position);
                myadapter.update(mylist);
                gv1.setAdapter(myadapter);
                labelBannerInfo = new LabelBannerInfo(LabelMangerAct.this);
                labelBannerInfo.setDictName(str.getDictName());
                labelBannerInfo.setId(str.getId());
                list.add(labelBannerInfo);
                labelGVadapter.update(list);
            }
        });
    }


    /**
     * 个人兴趣标签/全部标签
     * @param dictType
     * @return
     */
    private void getIntrestLabel(String dictType){
        RequestManager.getScheduleManager().getIntrestLabelList(dictType, new ResultCallback<ResultBean<List<LabelBannerInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<List<LabelBannerInfo>> response) {
                list = response.getData();
                labelGVadapter = new LabelGVadapter(LabelMangerAct.this, list);
                gv2.setAdapter(labelGVadapter);
                gv2.setSelector(new ColorDrawable(Color.TRANSPARENT));
            }
        });
    }

    /**
     * 添加个人兴趣标签
     * @param labelIds
     */
    private void addInterestLabel(String labelIds){
        RequestManager.getScheduleManager().addInterestLabel(labelIds, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                sendBroadcast(new Intent(ChanelFrg.MYLABELS));
                sendBroadcast(new Intent(MineFrg.USER_INFO));
                finish();
            }
        });
    }

    /**
     * 获取兴趣标签
     */
    private void getMyInterestLabel(){
        RequestManager.getScheduleManager().findMyLabelListMoreByUserId(new ResultCallback<ResultBean<List<UserInterestInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<List<UserInterestInfo>> response) {
                mylist = response.getData();
                myadapter = new LabelGvMyadapter(LabelMangerAct.this, mylist);
                gv1.setAdapter(myadapter);
            }
        });
    }

    /**
     * 查询他人兴趣标签
     * @param userId
     */
    private void getOtherInterestLabel(String userId){
        RequestManager.getScheduleManager().findOtherListMoreByUserId(userId, new ResultCallback<ResultBean<List<UserInterestInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<List<UserInterestInfo>> response) {
                mylist = response.getData();
                myadapter = new LabelGvMyadapter(LabelMangerAct.this, mylist);
                gv1.setAdapter(myadapter);
            }
        });
    }


}
