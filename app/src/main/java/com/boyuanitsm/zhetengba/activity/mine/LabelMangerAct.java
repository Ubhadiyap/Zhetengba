package com.boyuanitsm.zhetengba.activity.mine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.LabelGVadapter;
import com.boyuanitsm.zhetengba.adapter.LabelGvMyadapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
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
    private List<LabelBannerInfo> list=new ArrayList<LabelBannerInfo>();
    private List<LabelBannerInfo> mylist=new ArrayList<>();
    private LabelGVadapter labelGVadapter;
    private LabelGvMyadapter myadapter;
    private String labelids;//接口传入参数
    @Override
    public void setLayout() {
        setContentView(R.layout.act_labelmana2);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("标签管理");
        setRight("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyToastUtils.showShortToast(LabelMangerAct.this, "标签添加完成");
//                if (mylist==null){
//                    MyToastUtils.showShortToast(LabelMangerAct.this,"至少选择一个兴趣标签");
//                }else if (mylist.size()==1){
//                    labelids=mylist.get(0).getId();
//                    addInterestLabel(labelids);
//                }else if (mylist.size()>1){
//                    labelids=mylist.get(0).getId();
//                    for (int i=1;i<mylist.size();i++){
//                        labelids=labelids+","+mylist.get(i).getId();
//                    }
//                    addInterestLabel(labelids);
//                }
                MyLogUtils.degug("选择的所有标签："+mylist.toString());
                addInterestLabel(mylist.get(0).getId());

            }
        });
//        添加标签到全部标签
        getIntrestLabel("0");
        updata();
//        MyLogUtils.info(list.get(2).getDictName());
//        MyToastUtils.showShortToast(this,list.get(2).getDictName());

    }

    private void updata() {
        gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LabelBannerInfo str = (LabelBannerInfo) gv2.getItemAtPosition(position);
                if (mylist.size()>0){
                    for (int i=0;i<mylist.size();i++){
                        if (str.getDictName().equals(mylist.get(i).getDictName())){
                            MyToastUtils.showShortToast(LabelMangerAct.this,str+"标签已添加");
                            return ;
                        }
                    }
                    mylist.add(str);
                    labelGVadapter.setSelection(position,0);
                    labelGVadapter.notifyDataSetChanged();
                    myadapter.update(mylist);
                }else {
                    mylist.add(str);
                }
                    labelGVadapter.setSelection(position,0);
                    labelGVadapter.notifyDataSetChanged();
                    myadapter.update(mylist);

            }
        });
        if(myadapter==null){
            myadapter=new LabelGvMyadapter(LabelMangerAct.this,mylist);
        }
        gv1.setAdapter(myadapter);
        gv1.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LabelBannerInfo str = (LabelBannerInfo) gv1.getItemAtPosition(position);
                labelGVadapter.setBackGround(labelposition(str), 1);
                labelGVadapter.notifyDataSetChanged();
                mylist.remove(position);
                myadapter.notifyDataSetChanged();
                gv1.setAdapter(myadapter);
            }
        });
    }
    //刷新全部标签对应标签背景
    private int labelposition(LabelBannerInfo str){
        int position=-1;
        for (int i=0;i<list.size();i++){
            if (str.equals(list.get(i))){
                return position=i;
            }
        }
        return position;
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
                labelGVadapter=new LabelGVadapter(LabelMangerAct.this,list);
                gv2.setAdapter(labelGVadapter);
                gv2.setSelector(new ColorDrawable(Color.TRANSPARENT));
                MyLogUtils.info(list.get(5).getDictName());
            }
        });
    }

    /**
     * 添加个人兴趣标签
     * @param labelIds
     */
    private void addInterestLabel(String labelIds){
        MyLogUtils.degug("个人兴趣："+labelIds);
        RequestManager.getScheduleManager().addInterestLabel(labelIds, new ResultCallback() {
            @Override
            public void onError(int status, String errorMsg) {
                MyLogUtils.degug("错误："+errorMsg);
            }

            @Override
            public void onResponse(Object response) {
                MyLogUtils.degug("成功了");
                //finish();
            }
        });
    }

}
