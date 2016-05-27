package com.boyuanitsm.zhetengba.activity.mess;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 添加好友
 * Created by wangbin on 16/5/16.
 */
public class AddFriendsAct extends BaseActivity {
    private int READ_CONTACTS = 111;

    @Override
    public void setLayout() {
        setContentView(R.layout.actt_add_friends);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("添加好友");
    }

    @OnClick({R.id.rlPhone, R.id.ivWx, R.id.ivQQ})
    public void onClick(View v) {
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

                break;
            case R.id.ivQQ:

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

}
