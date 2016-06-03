package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 性别选择界面
 * Created by Yang on 2015/11/27 0027.
 */
public class SelectSexAct extends BaseActivity {
    @ViewInject(R.id.rgBoy)
    private RadioButton rgBoy;
    @ViewInject(R.id.rgGirl)
    private RadioButton rgGirl;
    @ViewInject(R.id.rgSex)
    private RadioGroup rgSex;

    public final static String SEX = "sex";
    private int sex;//2女 1男
//    private MyBaseInfoBean baseInfoBean;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_selectsex);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("性别");
//        baseInfoBean = (MyBaseInfoBean) getIntent().getSerializableExtra("MyBaseInfoBean");
//        if (!"".equals(baseInfoBean) && baseInfoBean != null) {
//            sex = baseInfoBean.getSex() == 2 ? 2 : 1;
//        }
//        if (sex == 2) {
//            rgGirl.setChecked(true);
//        } else {
//            rgBoy.setChecked(true);
//        }
//        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rgBoy:
//                        baseInfoBean.setSex(1);
//                        changeUserInfo();
//                        break;
//                    case R.id.rgGirl:
//                        baseInfoBean.setSex(2);
//                        changeUserInfo();
//                        break;
//                }
//
//            }
//        });

    }

    /**
     * 修改个人资料
     */
    private void changeUserInfo() {
//        RequestManager.getUserInstance().changeUserBaseInfo(baseInfoBean, new CallBack() {
//            @Override
//            public void onSuccess(String result) {
//                ResultBean resultBean = (ResultBean) GsonUtils.jsonToRb(result, ResultBean.class);
//                Intent intent = new Intent();
//                intent.putExtra("Modify", baseInfoBean.getSex());
//                setResult(MineAct.SEXMODIFY_BAKC, intent);
//                finish();
//            }
//
//            @Override
//            public void onError(String error) {
//                MyToastUtils.showShortToast(SelectSexAct.this, error);
//            }
//
//            @Override
//            public void onEmpty(String result) {
//
//            }
//        });
  }
}
