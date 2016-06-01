package com.boyuanitsm.zhetengba.activity.circle;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mess.ContractsAct;
import com.boyuanitsm.zhetengba.activity.mine.AssignScanAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 建立圈子界面
 * Created by bitch-1 on 2016/5/9.
 */
public class CreatCirAct extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.tv_ask)//邀请好友
    private TextView tv_ask;
    @ViewInject(R.id.tv_creat)//创建圈子
    private TextView tv_creat;
    @ViewInject(R.id.et_cir_name)//建立圈子名称
    private EditText et_cir_name;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_creatcir);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("建立圈子");

    }
    @OnClick({R.id.tv_ask,R.id.tv_creat})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_ask:
                openActivity(AssignScanAct.class);
                break;
            case R.id.tv_creat://创建圈子，圈子管理增加一条，跳转至圈子管理
                if (et_cir_name.getText().toString().equals("")){
                    MyToastUtils.showShortToast(this,"名称不能为空");
                }else {
                    openActivity(CircleglAct.class);
                }

                break;
        }
    }
}
