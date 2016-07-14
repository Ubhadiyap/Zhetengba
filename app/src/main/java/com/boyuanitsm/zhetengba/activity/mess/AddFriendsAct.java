package com.boyuanitsm.zhetengba.activity.mess;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 添加好友
 * Created by wangbin on 16/5/16.
 */
public class AddFriendsAct extends BaseActivity {
    private int READ_CONTACTS = 111;
    @ViewInject(R.id.cetSearch)
    private ClearEditText cetSearch;
    private String codeUrl="http://172.16.6.253:8082/share_3" ;



    @Override
    public void setLayout() {
        setContentView(R.layout.actt_add_friends);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("添加好友");
        cetSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString()) && s.length() == 11) {
                    if (TextUtils.equals(UserInfoDao.getUser().getUsername(),s.toString())){
                        MyToastUtils.showShortToast(AddFriendsAct.this,"您输入的是自己手机号");
                    }else{
                        findUserByPhone(s.toString());
                    }
                }
            }
        });
    }

    @OnClick({R.id.rlPhone, R.id.ivWx, R.id.ivQQ})
    public void onClick(View v) {
        final UMImage image = new UMImage(AddFriendsAct.this,
               BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        switch (v.getId()) {
            case R.id.rlPhone://手机联系人
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                                READ_CONTACTS);
                    } else {
                        openActivity(PhoneAct.class);
                    }
                } else {
                    openActivity(PhoneAct.class);
                }

                break;
            case R.id.ivWx:
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withText("我在使用折腾吧，你也一起加入吧！")
                        .withTargetUrl(codeUrl)
                        .withMedia(image)
                        .withTitle("折腾吧")
                        .share();

                break;
            case R.id.ivQQ:
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withText("我在使用折腾吧，你也一起加入吧！")
                        .withTargetUrl(codeUrl)
                        .withMedia(image)
                        .withTitle("折腾吧")
                        .share();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                openActivity(PhoneAct.class);
            } else {
                // Permission Denied

            }
        }
    }

    /**
     * 查找用户
     * @param phone
     */
    private void findUserByPhone(String phone){
        RequestManager.getMessManager().findUserByPhone(phone, new ResultCallback<ResultBean<UserInfo>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
            }

            @Override
            public void onResponse(ResultBean<UserInfo> response) {
                UserInfo userInfo = response.getData();
                if (!TextUtils.isEmpty(userInfo.getId())) {
                    Intent intent = new Intent(AddFriendsAct.this, PerpageAct.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", userInfo.getId());
//                  bundle.putBoolean("friend",userInfo.isFriend());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    MyToastUtils.showShortToast(AddFriendsAct.this,"无此用户");
                }
            }
        });
    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            MyToastUtils.showShortToast(getApplicationContext(), "分享成功");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            MyToastUtils.showShortToast(getApplicationContext(), "分享失败");

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            MyToastUtils.showShortToast(getApplicationContext(), "分享取消");
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
