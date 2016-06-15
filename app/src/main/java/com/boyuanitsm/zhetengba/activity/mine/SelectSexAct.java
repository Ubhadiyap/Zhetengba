package com.boyuanitsm.zhetengba.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
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
    private String sex;//0女 1男
    private UserInfo user;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_selectsex);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("性别");
        user=getIntent().getParcelableExtra("user");
        if(user!=null&& !TextUtils.isEmpty(user.getSex())){
            sex=user.getSex();
            if(sex.equals("0")){
                rgGirl.setChecked(true);
            }else
                rgBoy.setChecked(true);
        }
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rgBoy:
                        user.setSex("1");
                        changeUserInfo();
                        break;
                    case R.id.rgGirl:
                        user.setSex("0");
                        changeUserInfo();
                        break;
                }

            }
        });

    }

    /**
     * 修改个人资料
     */
    private void changeUserInfo() {
        RequestManager.getUserManager().modifyUserInfo(user, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                UserInfoDao.updateUser(user);
                Intent intent = new Intent();
                intent.putExtra("Modify", user.getSex());
                setResult(PersonalmesAct.SEXMODIFY_BAKC, intent);
                finish();

            }
        });

  }


}




