package com.boyuanitsm.zhetengba.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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
    private int type;
    private String codeUrl;

    @Override
    public void setLayout() {
        setContentView(R.layout.dialog_share);
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        Display defaultDisplay = getWindowManager().getDefaultDisplay();
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.width = (int) (defaultDisplay.getWidth() * 1.0);
//        params.height=(int)(defaultDisplay.getHeight()*1.0);
//        getWindow().setGravity(Gravity.BOTTOM);
        type=getIntent().getExtras().getInt("type");
        if(type==1){
            codeUrl="http://172.16.6.253:8082/share_1 ";//活动分享链接
        }
        if(type==2){
            codeUrl="http://172.16.6.253:8082/share_2 ";//档期分享链接
        }
//        if(type==3){
//            codeUrl="http://172.16.6.253:8082/share_3   ";//下载链接
//        }
        if(type==4){
            codeUrl="http://www.baidu.com";//圈子动态链接暂时用百度链接
            //占时用到这个的有从圈子frg里面子圈子，子频道，有从首页点击头像圈子动态frg分享
        }
//        if(type==5){
//            codeUrl="http://www.baidu.com";//分享二维码暂时用百度代替
//        }

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        //设置窗口的大小及透明度
        layoutParams.width = WindowManager.LayoutParams.FILL_PARENT;
        layoutParams.height = layoutParams.FILL_PARENT;
        window.setAttributes(layoutParams);
    }

    @OnClick({R.id.ll_qqshare,R.id.ll_weiboshare,R.id.ll_weixinshare,R.id.ll_cancel_share})
    public void OnClick(View v){
        final UMImage image = new UMImage(ShareDialogAct.this,
                BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        switch (v.getId()){
            case R.id.ll_qqshare://qq分享
               new ShareAction(this)
                .setPlatform(SHARE_MEDIA.QQ)
                    .setCallback(umShareListener)
                    .withText("折腾吧")
                    .withTargetUrl(codeUrl)
                    .withMedia(image)
                       .withTitle("折腾吧")
                    .share();
                break;
            case R.id.ll_weiboshare:
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .setCallback(umShareListener)
                        .withText("折腾吧"+codeUrl)
                        .withMedia(image)
                        .share();
                break;
            case R.id.ll_weixinshare:
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withText("折腾吧")
                        .withTargetUrl(codeUrl)
                        .withMedia(image)
                        .withTitle("折腾吧")
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
            finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            MyToastUtils.showShortToast(getApplicationContext(), "分享失败");
            finish();
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
