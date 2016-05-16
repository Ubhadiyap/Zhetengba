package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 修改资料界面
 * Created by Administrator on 2016/5/13.
 */
public class EditAct extends BaseActivity{
    @ViewInject(R.id.cet_editInfo)
    private ClearEditText cetEditInfo;
    private int TYPE;//1 昵称 2 手机号  3邮箱  4公司名称  5公司地址  6公司电话 7职务
    public static String USER_TYPE = "type";
    @Override
    public void setLayout() {
        setContentView(R.layout.act_edit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        TYPE = getIntent().getIntExtra(USER_TYPE, 0);
        setTopPos(TYPE);
        setRight("提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = cetEditInfo.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    MyToastUtils.showShortDebugToast(getApplicationContext(),"请输入完成后再提交");
                    return;
                }
                switch (TYPE){
                    case 1:

                        break;
                    case 2:
                        boolean isMobileNO = ZtinfoUtils.isMobileNO(content);
                        if (!isMobileNO){
                            MyToastUtils.showShortDebugToast(getApplicationContext(),"请输入正确的手机号吗");
                            return;
                        }
                        break;
                    case 3:
                        boolean isEmail = ZtinfoUtils.isEmail(content);
                        if (!isEmail){
                            MyToastUtils.showShortDebugToast(getApplicationContext(),"请输入正确的邮箱号");
                            return;
                        }
                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                }
                finish();
            }
        });
    }

    private void setTopPos(int position) {
        switch (position){
            case 1:
                setTopTitle("昵称");
                cetEditInfo.setHint("请输入昵称");
                break;
            case 2:
                setTopTitle("手机号码");
                cetEditInfo.setHint("请输入手机号码");
                cetEditInfo.setInputType(InputType.TYPE_CLASS_NUMBER);
                cetEditInfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                break;
            case 3:
                setTopTitle("邮箱");
                cetEditInfo.setHint("请输入邮箱");
                break;
            case 4:
                setTopTitle("公司名称");
                cetEditInfo.setHint("请输入公司名称");
                break;
            case 5:
                setTopTitle("公司地址");
                cetEditInfo.setHint("请输入公司地址");
                break;
            case 6:
                setTopTitle("公司电话");
                cetEditInfo.setHint("请输入公司电话");
                break;
            case 7:
                setTopTitle("职务");
                cetEditInfo.setHint("请输入职务");
                break;
        }
    }
}
