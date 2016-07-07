package com.boyuanitsm.zhetengba.activity.circle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.AppManager;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mess.ScanQrcodeAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.view.CommonView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 圈子资料界面
 * Created by bitch-1 on 2016/5/11.
 */
public class CirmationAct extends BaseActivity {
    @ViewInject(R.id.notice)
    private EditText notice;//公告
    @ViewInject(R.id.com_ewm)
//    private CommonView com_ewm;//二维码
//    @ViewInject(R.id.com_jb)
    private CommonView com_jb;//举报
    @ViewInject(R.id.tv_tc)
    private CommonView tv_tc;//退出按钮

    private CircleEntity circleEntity;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_cirmation);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("圈子资料");
        circleEntity=getIntent().getParcelableExtra("circleEntity");
        if(circleEntity!=null){
            if (!TextUtils.isEmpty(circleEntity.getCircleOwnerId())){
                if (circleEntity.getCircleOwnerId().equals(UserInfoDao.getUser().getId())){
                    notice.setEnabled(true);
                    setRight("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(notice.getText().toString().trim())){
                                addNotice(circleEntity.getId(),notice.getText().toString().trim());
                            }else {
                                MyToastUtils.showShortToast(CirmationAct.this,"请输入公告内容！");
                            }
                        }
                    });
                }else {
                    setRight("",null);
                    notice.setEnabled(false);
                }
            }
            if(!TextUtils.isEmpty(circleEntity.getNotice())) {
                notice.setText(circleEntity.getNotice());
            }else {
                notice.setText("");
            }
            notice.requestFocus(notice.getText().toString().trim().length());
        }
    }

    @OnClick({R.id.com_ewm,R.id.tv_tc})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.com_ewm://圈子二维码
                Intent intent=new Intent(this,CircleEr.class);
                Bundle bundle=new Bundle();
                bundle.putString("circleId", circleEntity.getId());
                intent.putExtras(bundle);
                startActivity(intent);
//                openActivity(CircleEr.class);
                break;
//            case R.id.com_jb://圈子举报
//
//                break;
            case R.id.tv_tc://圈子退出
            exitCircle(circleEntity.getId());
//                Toast.makeText(getApplicationContext(),"hah",Toast.LENGTH_SHORT).show();

                break;

        }
    }

    /**
     * 发布公告
     * @param circleId
     * @param notice
     */
    private void addNotice(String circleId ,String notice ){
        RequestManager.getTalkManager().addNotice(circleId, notice, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                finish();//发布公告成功，详情界面公告与圈子管理界面刷新
                sendBroadcast(new Intent(CirxqAct.DETAIL));
                sendBroadcast(new Intent(CircleglAct.INTENTFLAG));
            }
        });
    }

    /**
     * 成员退出圈子（群主退出，圈也注销）
     * @param circleId
     */
    private void exitCircle(String circleId){
        RequestManager.getTalkManager().removeCircle(circleId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                if (!TextUtils.isEmpty(circleEntity.getCircleOwnerId())) {
                    if (circleEntity.getCircleOwnerId().equals(UserInfoDao.getUser().getId())) {
                        AppManager.getAppManager().finishActivity(CirxqAct.class);
                        MyToastUtils.showShortToast(CirmationAct.this,"圈子已经解散！");
                    }
                }
                finish();
                sendBroadcast(new Intent(CircleglAct.INTENTFLAG));
            }
        });
    }
}
