package com.boyuanitsm.zhetengba.activity.mine;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.ShareDialogAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 分享二维码界面
 * Created by bitch-1 on 2016/5/3.
 */
public class ShareqrcodeAct extends BaseActivity {
    @ViewInject(R.id.iverm)
    private ImageView iverm;
    @ViewInject(R.id.tv_shareer)
    private TextView tv_shareer;

    private Bitmap bitmap;
    String erUrl = "http://m.myjinzhu.com/#/tab/home?redirctUrl=/register/";
    @Override
    public void setLayout() {
        setContentView(R.layout.act_shareqrcode);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("分享二维码");
        bitmap= ZhetebaUtils.createQRImage(erUrl);
        iverm.setScaleType(ImageView.ScaleType.FIT_XY);
        iverm.setImageBitmap(bitmap);
    }


    @OnClick({R.id.tv_shareer})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_shareer:
                openActivity(ShareDialogAct.class);
                break;
        }
    }
}
