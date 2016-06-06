package com.boyuanitsm.zhetengba.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
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
    private int TYPE;//1 昵称 2 手机号  3邮箱  4公司名称  5公司地址  6公司电话 7职务 9故乡
    public static String USER_TYPE = "type";
    private UserInfo user;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_edit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        TYPE = getIntent().getIntExtra(USER_TYPE, 0);
        user= UserInfoDao.getUser();
        setTopPos(TYPE);
        setRight("提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserinfo();
                saveUser(user);
                MyLogUtils.degug("gaga"+user);


//                String content = cetEditInfo.getText().toString().trim();
//                if (TextUtils.isEmpty(content)) {
//                    MyToastUtils.showShortDebugToast(getApplicationContext(),"请输入完成后再提交");
//                    return;
//                }
//                switch (TYPE){
//                    case 1:
//
//                        break;
//                    case 2:
//                        boolean isMobileNO = ZtinfoUtils.isMobileNO(content);
//                        if (!isMobileNO){
//                            MyToastUtils.showShortDebugToast(getApplicationContext(),"请输入正确的手机号吗");
//                            return;
//                        }
//                        break;
//                    case 3:
//                        boolean isEmail = ZtinfoUtils.isEmail(content);
//                        if (!isEmail){
//                            MyToastUtils.showShortDebugToast(getApplicationContext(), "请输入正确的邮箱号");
//                            return;
//                        }
//                        break;
//                    case 4:
//
//                        break;
//                    case 5:
//
//                        break;
//                    case 6:
//
//                        break;
//                    case 7:
//
//                        break;
//                    case 8:
//                        break;
//                }
//                finish();
            }
        });
    }

    /**
     * 保存用户信息
     * @param userInfo
     */
    private void saveUser(final UserInfo userInfo) {
        if(TYPE==8){

        }else {
            RequestManager.getUserManager().modifyUserInfo(userInfo, new ResultCallback<ResultBean<String>>() {
                @Override
                public void onError(int status, String errorMsg) {

                }

                @Override
                public void onResponse(ResultBean<String> response) {
                    UserInfoDao.updateUser(userInfo);
                    sendBroadcast(new Intent(PersonalmesAct.USER_INFO));
                    MyToastUtils.showShortToast(getApplicationContext(), "修改信息成功");
                    finish();

                }
            });
        }

    }

    /**
     * 保存用户到一个实体中
     * @return
     */
    private void saveUserinfo() {
        String content = cetEditInfo.getText().toString().trim();
        switch (TYPE){
            case 1:
                if(user!=null){
                if(!TextUtils.isEmpty(content)){
                    user.setPetName(content);
                }else user.setPetName("");}
                break;
            case 2:
                boolean isMobileNO = ZtinfoUtils.isMobileNO(content);
                if (!isMobileNO){
                    MyToastUtils.showShortDebugToast(getApplicationContext(),"请输入正确的手机号吗");
                    return;
                }else {
                    if(!TextUtils.isEmpty(content)){
                        user.setPhone(content);
                    }else {user.setPhone("");}
                }
                break;
            case 3:
                boolean isEmail = ZtinfoUtils.isEmail(content);
                if (!isEmail){
                    MyToastUtils.showShortDebugToast(getApplicationContext(), "请输入正确的邮箱号");
                    return;
                }else {
                    if(!(TextUtils.isEmpty(content))){
                        user.setEmail(content);
                    }else {user.setEmail("");}
                }
                break;
            case 4:
                if(!(TextUtils.isEmpty(content))){
                    user.setCompanyName(content);
                }else user.setCompanyName("");
                break;
            case 5:
                if(!(TextUtils.isEmpty(content))){
                    user.setCompanyAddr(content);
                }else user.setCompanyAddr("");
                break;
            case 6:
                if(!(TextUtils.isEmpty(content))){
                    user.setCompanyPhone(content);
            }else user.setCompanyPhone("");

                break;
            case 7:
                if(!(TextUtils.isEmpty(content))){
                    user.setJob(content);
                }else user.setJob("");

                break;
            case 8:
                break;
            case 9:
                if(!(TextUtils.isEmpty(content))){
                    user.setHomeTown(content);
                }else user.setHomeTown("");

                break;
        }


    }

    private void setTopPos(int position) {
        switch (position){
            case 1:
                setTopTitle("昵称");
                if(user!=null){
                    if(!(TextUtils.isEmpty(user.getPetName()))){
                        cetEditInfo.setText(user.getPetName());
                    }
                }
                cetEditInfo.setHint("请输入昵称");
                break;
            case 2:
                setTopTitle("手机号码");
                if(user!=null){
                    if(!(TextUtils.isEmpty(user.getPhone()))){
                        cetEditInfo.setText(user.getPhone());
                    }
                }
                cetEditInfo.setHint("请输入手机号码");
                cetEditInfo.setInputType(InputType.TYPE_CLASS_NUMBER);
                cetEditInfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                break;
            case 3:
                setTopTitle("邮箱");
                if(user!=null){
                    if(!(TextUtils.isEmpty(user.getEmail()))){
                        cetEditInfo.setText(user.getEmail());
                    }
                }
                cetEditInfo.setHint("请输入邮箱");
                break;
            case 4:
                setTopTitle("公司名称");
                if(user!=null){
                    if(!(TextUtils.isEmpty(user.getCompanyName()))){
                        cetEditInfo.setText(user.getCompanyName());
                    }
                }
                cetEditInfo.setHint("请输入公司名称");
                break;
            case 5:
                setTopTitle("公司地址");
                if(user!=null){
                    if(!(TextUtils.isEmpty(user.getCompanyAddr()))){
                        cetEditInfo.setText(user.getCompanyAddr());
                    }
                }
                cetEditInfo.setHint("请输入公司地址");
                break;
            case 6:
                setTopTitle("公司电话");
                if(user!=null){
                    if(!(TextUtils.isEmpty(user.getCompanyPhone()))){
                        cetEditInfo.setText(user.getCompanyPhone());
                    }
                }
                cetEditInfo.setHint("请输入公司电话");
                break;
            case 7:
                setTopTitle("职务");
                if(user!=null){
                    if(!(TextUtils.isEmpty(user.getJob()))){
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
                if(user!=null){
                    if(!TextUtils.isEmpty(user.getHomeTown())){
                        cetEditInfo.setText(user.getHomeTown());
                    }
                }
                cetEditInfo.setHint("请输入故乡地址");
        }
    }
}
