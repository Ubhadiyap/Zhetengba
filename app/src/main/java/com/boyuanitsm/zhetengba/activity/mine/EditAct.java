package com.boyuanitsm.zhetengba.activity.mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.fragment.MineFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 修改资料界面
 * Created by Administrator on 2016/5/13.
 */
public class EditAct extends BaseActivity {
    @ViewInject(R.id.cet_editInfo)
    private ClearEditText cetEditInfo;
    private int TYPE;//1 昵称 2 手机号  3邮箱  4公司名称  5公司地址  6公司电话 7职务 9故乡
    public static String USER_TYPE = "type";
    private UserInfo user;
    private boolean flag=true;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_edit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        TYPE = getIntent().getIntExtra(USER_TYPE, 0);
        user = UserInfoDao.getUser();
        setTopPos(TYPE);
        setRight("提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if (saveUserinfo()==true){
                       saveUser(user);
                   }else {
                       MyToastUtils.showShortToast(EditAct.this,"修改信息失败！");
                   }

            }
        });
    }

    /**
     * 保存用户信息
     *
     * @param userInfo
     */
    private void saveUser(final UserInfo userInfo) {
        if (TYPE == 8) {

        } else {
            RequestManager.getUserManager().modifyUserInfo(userInfo, new ResultCallback<ResultBean<String>>() {
                @Override
                public void onError(int status, String errorMsg) {

                }

                @Override
                public void onResponse(ResultBean<String> response) {
                    if(TYPE==1&&!TextUtils.isEmpty(userInfo.getPetName()))
                    DemoHelper.getInstance().getUserProfileManager().setNickName(userInfo.getPetName());
                    UserInfoDao.updateUser(userInfo);
                    sendBroadcast(new Intent(PersonalmesAct.USER_INFO));
                    sendBroadcast(new Intent(MineFrg.USER_INFO));
                    MyToastUtils.showShortToast(getApplicationContext(), "修改信息成功");
                    finish();

                }
            });
        }

    }

    /**
     * 保存用户到一个实体中
     *
     * @return
     */
    private boolean saveUserinfo() {
        String content = cetEditInfo.getText().toString().trim();
        switch (TYPE) {
            case 1:
                if (user != null) {
                    if (!TextUtils.isEmpty(content)) {
                        user.setPetName(content);
                    } else {
                        user.setPetName("");
                    }
                  flag=true;
                }
                break;
            case 2:
                boolean isMobileNO = ZtinfoUtils.isMobileNO(content);
                if (!isMobileNO) {
                    MyToastUtils.showShortDebugToast(getApplicationContext(), "请输入正确的手机号吗");
                    flag=false;
                } else {
                    if (!TextUtils.isEmpty(content)) {
                        user.setPhone(content);
                    } else {
                        user.setPhone("");
                    }
                   flag=true;
                }
                break;
            case 3:
                boolean isEmail = ZtinfoUtils.isEmail(content);
                if (!isEmail) {
                    MyToastUtils.showShortDebugToast(getApplicationContext(), "请输入正确的邮箱号");
                   flag=false;
                } else {
                    if (!(TextUtils.isEmpty(content))) {
                        user.setEmail(content);
                    } else {
                        user.setEmail("");
                    }
                   flag=true;
                }
                break;
            case 4:
                if (!(TextUtils.isEmpty(content))) {
                    user.setCompanyName(content);
                } else{
                    user.setCompanyName("");
                }
                flag=true;
                break;
            case 5:
                if (!(TextUtils.isEmpty(content))) {
                    user.setCompanyAddr(content);
                } else {
                    user.setCompanyAddr("");
                }
                flag=true;
                break;
            case 6:
                if (!(TextUtils.isEmpty(content))) {
                    user.setCompanyPhone(content);
                } else {
                    user.setCompanyPhone("");
                }
                  flag=true;
                break;
            case 7:
                if (!(TextUtils.isEmpty(content))) {
                    user.setJob(content);
                } else {
                    user.setJob("");
                }
               flag=true;
                break;
            case 8:
                break;
            case 9:
                if (!(TextUtils.isEmpty(content))) {
                    user.setHomeTown(content);
                } else {
                    user.setHomeTown("");
                }
                  flag=true;
                break;
        }
        return flag;
    }

    /**
     * 跳转至输入这边来的时候
     * @param position
     */
    private void setTopPos(int position) {
        switch (position) {
            case 1:
                setTopTitle("昵称");
                if (user != null) {
                    if (!(TextUtils.isEmpty(user.getPetName()))) {
                        cetEditInfo.setText(user.getPetName());
                    }
                }
                cetEditInfo.setHint("请输入昵称");
                cetEditInfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

                break;
            case 2:
                setTopTitle("手机号码");
                if (user != null) {
                    if (!(TextUtils.isEmpty(user.getPhone()))) {
                        cetEditInfo.setText(user.getPhone());
                    }
                }
                cetEditInfo.setHint("请输入手机号码");
                cetEditInfo.setInputType(InputType.TYPE_CLASS_NUMBER);
                cetEditInfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                break;
            case 3:
                setTopTitle("邮箱");
                if (user != null) {
                    if (!(TextUtils.isEmpty(user.getEmail()))) {
                        cetEditInfo.setText(user.getEmail());
                    }
                }
                cetEditInfo.setHint("请输入邮箱");
                break;
            case 4:
                setTopTitle("公司名称");
                if (user != null) {
                    if (!(TextUtils.isEmpty(user.getCompanyName()))) {
                        cetEditInfo.setText(user.getCompanyName());
                    }
                }
                cetEditInfo.setHint("请输入公司名称");
                break;
            case 5:
                setTopTitle("公司地址");
                if (user != null) {
                    if (!(TextUtils.isEmpty(user.getCompanyAddr()))) {
                        cetEditInfo.setText(user.getCompanyAddr());
                    }
                }
                cetEditInfo.setHint("请输入公司地址");
                break;
            case 6:
                setTopTitle("公司电话");
                if (user != null) {
                    if (!(TextUtils.isEmpty(user.getCompanyPhone()))) {
                        cetEditInfo.setText(user.getCompanyPhone());
                    }
                }
                cetEditInfo.setHint("请输入公司电话");
                break;
            case 7:
                setTopTitle("职务");
                if (user != null) {
                    if (!(TextUtils.isEmpty(user.getJob()))) {
                        cetEditInfo.setText(user.getJob());
                    }
                }
                cetEditInfo.setHint("请输入职务");
                break;
            case 8://从消息里面的个人主页界面穿过来的修改备注
                setTopTitle("修改备注");
                cetEditInfo.setHint("请输入备注");
                break;

            case 9://故乡
                setTopTitle("故乡");
                if (user != null) {
                    if (!TextUtils.isEmpty(user.getHomeTown())) {
                        cetEditInfo.setText(user.getHomeTown());
                    }
                }
                cetEditInfo.setHint("请输入故乡地址");
        }
    }

    /**
     * 修改昵称
     *
     * @param newNickName
     */
    private void updateNickName(String newNickName) {
        RequestManager.getMessManager().updateNickName(newNickName, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
            }

            @Override
            public void onResponse(String response) {

            }
        });
    }

    private ProgressDialog dialog;
    private void updateRemoteNick(final String nickName) {
        dialog = ProgressDialog.show(this, getString(R.string.dl_update_nick), getString(R.string.dl_waiting));
        new Thread(new Runnable() {

            @Override
            public void run() {
                boolean updatenick = DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(nickName);
                if (EditAct.this.isFinishing()) {
                    return;
                }
                if (!updatenick) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(EditAct.this, getString(R.string.toast_updatenick_fail), Toast.LENGTH_SHORT)
                                    .show();
                            dialog.dismiss();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(EditAct.this, getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
                                    .show();
//                            tvNickName.setText(nickName);
                        }
                    });
                }
            }
        }).start();
    }
}
