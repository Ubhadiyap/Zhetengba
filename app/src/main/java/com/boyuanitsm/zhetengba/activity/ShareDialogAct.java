package com.boyuanitsm.zhetengba.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by bitch-1 on 2016/5/23.
 */
public class ShareDialogAct extends BaseActivity {

    @Override
    public void setLayout() {
        setContentView(R.layout.dialog_share);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (defaultDisplay.getWidth() * 1.0);
        getWindow().setGravity(Gravity.BOTTOM);

    }

    @OnClick({R.id.ll_qqshare,R.id.ll_weiboshare,R.id.ll_weixinshare,R.id.ll_cancel_share})
    public void OnClick(View v){
        final UMImage image = new UMImage(ShareDialogAct.this,
                BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
        switch (v.getId()){
            case R.id.ll_qqshare://qq分享
               new ShareAction(this)
                .setPlatform(SHARE_MEDIA.QQ)
                    .setCallback(umShareListener)
                    .withText("hello umeng video")
                    .withTargetUrl("http://www.baidu.com")
                    .withMedia(image)
                    .share();
                break;
            case R.id.ll_weiboshare:
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .setCallback(umShareListener)
                        .withText("hello umeng video")
                        .withMedia(image)
                        .share();
                break;
            case R.id.ll_weixinshare:
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withText("hello umeng video")
                        .withTargetUrl("http://www.baidu.com")
                        .withMedia(image)
                        .share();
                break;

            case R.id.ll_cancel_share:
                finish();
                break;



        }



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
