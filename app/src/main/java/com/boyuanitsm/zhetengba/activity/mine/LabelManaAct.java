package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.GridAdapter;
import com.boyuanitsm.zhetengba.adapter.LabelAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.view.flowlayout.FlowLayout;
import com.boyuanitsm.zhetengba.view.flowlayout.LabelFlowLayout;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 标签管理
 * Created by Administrator on 2016/5/10.
 */
public class LabelManaAct extends BaseActivity {
    @ViewInject(R.id.gv)
    private GridView gv;
    @ViewInject(R.id.lfv_label)
    private LabelFlowLayout lfvLabel;
    @ViewInject(R.id.lfv_myLabel)
    private LabelFlowLayout lflMyLabel;
    private List<String> labelList = new ArrayList<>();
    private String labelNum[]  = {"吃货","呆萌","正经","神经质","反差萌"};
    private String allLabelNum[] = {"单身待解救","努力加班","幸福ing","外貌协会","文艺青年","月光族","技术宅","上班族","白领","码农","供房ing","静待缘分","心如止水","失恋ing"};
    private GridAdapter adapter;
    //    private GridAdapter adapter;
    private boolean isFirstClick = false;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_labelmana);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("标签管理");
        labelList.add("吃货");
        labelList.add("神经病");
        labelList.add("仗义");
        labelList.add("反差萌");
        labelList.add("努力奋斗中");
        LabelFlowview();
        recyclerView();
    }

    private void recyclerView() {
//        adapter = new GridAdapter(this,labelList);
//        gv.setAdapter(adapter);
//        lfvLabel.setAdapter(new LabelAdapter<List<String>>(labelList) {
//
//            @Override
//            public View getView(FlowLayout parent, int position, List<String> list) {
//                return null;
//            }
//        });
    }

    private void LabelFlowview() {

        lfvLabel.setAdapter(new LabelAdapter<String>(allLabelNum) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) LayoutInflater.from(LabelManaAct.this).inflate(R.layout.label_item,lfvLabel,false);
                textView.setText(s);
                return textView;
            }
        });
        //单选
//        lfvLabel.setOnTagClickListener(new LabelFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                Toast.makeText(LabelManaAct.this, labelNum[position], Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
        //多选
        lfvLabel.setOnSelectListener(new LabelFlowLayout.OnSelectListener() {

            private int pos;

            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                Iterator<Integer> it = selectPosSet.iterator();
                while (it.hasNext()){
                    pos = it.next();
                    MyLogUtils.info("Set集合中的值：" + pos);
                }
                if (!isFirstClick){
                    isFirstClick = true;
                    MyLogUtils.info("选择了。。。");
                    adapter.addLabel(allLabelNum[pos]);
                }else{
                    isFirstClick = false;
                    MyLogUtils.info("取消选择...");
                    adapter.removLabel(pos);
                }
//                adapter.addLabel(allLabelNum[position]);
//                MyLogUtils.info("xxxxxxxx"+allLabelNum[position]+"list的size"+labelList.size());
            }
        });
    }

}
