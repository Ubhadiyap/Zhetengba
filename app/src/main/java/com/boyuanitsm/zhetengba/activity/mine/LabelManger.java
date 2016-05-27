package com.boyuanitsm.zhetengba.activity.mine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.LabelGVadapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签管理
 * Created by xiaoke on 2016/5/27.
 */
public class LabelManger extends BaseActivity {
    @ViewInject(R.id.gv1)
    private GridView gv1;
    @ViewInject(R.id.gv2)
    private GridView gv2;
    private List<String> list=new ArrayList<>();
    private String[] allLabelNum= {"待解救","努加班","幸福i","外貌协","文艺青","月光族","技术宅","上班族","白领","码农","供房i","静待缘","心如止","失恋g"};
    private List<String> mylist=new ArrayList<>();
    private LabelGVadapter labelGVadapter,myadapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    myadapter.notifyDataSetChanged();//通知gridview更新数据
                    gv1.setAdapter(myadapter);
                    break;
            }
        }
    };
    private Thread myThread=new Thread(){
        @Override
        public void run() {
            updata();
            handler.sendEmptyMessage(0);
        }
    };
    @Override
    public void setLayout() {
        setContentView(R.layout.act_labelmana2);
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        添加标签到全部标签
        for (int i=0;i<allLabelNum.length;i++){
            list.add(allLabelNum[i]);
        }
       labelGVadapter=new LabelGVadapter(LabelManger.this,list);
            gv2.setAdapter(labelGVadapter);
        gv2.setSelector(new ColorDrawable(Color.TRANSPARENT));
        updata();


    }

    private void updata() {
        gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) gv2.getItemAtPosition(position);
                for (int i=0;i<mylist.size();i++)
                mylist.add(str);
                MyToastUtils.showShortToast(LabelManger.this, str);
                labelGVadapter.setSelection(position);
                labelGVadapter.notifyDataSetChanged();

                    myadapter.update(mylist);


            }
        });
        if(myadapter==null){
            myadapter=new LabelGVadapter(LabelManger.this,mylist);
        }
        gv1.setAdapter(myadapter);
        gv1.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mylist.remove(position);
                myadapter.notifyDataSetChanged();
                gv1.setAdapter(myadapter);
            }
        });
    }
}
