package com.boyuanitsm.zhetengba.activity.circle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 圈子二维码
 * Created by xiaoke on 2016/5/24.
 */
public class CircleEr extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.tv_share)//分享二维码
    private TextView tv_share;
    @ViewInject(R.id.iv_erm)
    private ImageView iv_erm;
    private Bitmap bitmap;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_shareqrcode2);
    }

    @Override
    public void init(Bundle savedInstanceState) {
            setTopTitle("圈子二维码");
        Bundle bundle=getIntent().getExtras();
        String circleId= bundle.getString("circleId");
        if (!TextUtils.isEmpty(circleId)){
            bitmap= ZhetebaUtils.createQRImage(circleId);
        }
        iv_erm.setScaleType(ImageView.ScaleType.FIT_XY);
        iv_erm.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_share:
//              openActivity(ShareDialogAct.class);
        }
    }
}
