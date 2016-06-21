package com.boyuanitsm.zhetengba.activity.mess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.hyphenate.chat.EMClient;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 加好友发送消息验证；圈友主页，加好友按钮
 * Created by xiaoke on 2016/5/20.
 */
public class MessVerifyAct extends BaseActivity {
    @ViewInject(R.id.feedback_et)
    private EditText etReason;
    private String userName;
    private String userId;
    private String hUserName;
    private ProgressDialog progressDialog;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_friend_verfy);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        userId=bundle.getString("userId");
        MyLogUtils.info(userId+"用户id");
        userName=bundle.getString("userName");
        setTopTitle(userName);
        hUserName=userId;
    }
    //调用添加好友接口

    @OnClick({R.id.tvSend})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tvSend://发送请求
                addContact(etReason.getText().toString().trim());
                break;
        }
    }

    /**
     *  添加contact
     * @param
     */
    public void addContact(final String reason){
        if(EMClient.getInstance().getCurrentUser().equals(hUserName)){
            new MyAlertDialog(this).builder().setTitle("提示").setMsg(getResources().
                    getString(R.string.not_add_myself)).setNegativeButton("确定", null).show();
            finish();
            return;
        }

        if(DemoHelper.getInstance().getContactList().containsKey(hUserName)){
            //提示已在好友列表中(在黑名单列表里)，无需添加
            if(EMClient.getInstance().contactManager().getBlackListUsernames().contains(hUserName)){
                new MyAlertDialog(this).builder().setTitle("提示").setMsg(getResources().
                        getString(R.string.user_already_in_contactlist)).setNegativeButton("确定", null).show();
                finish();
                return;
            }
            new MyAlertDialog(this).builder().setTitle("提示").setMsg(getResources().
                    getString(R.string.This_user_is_already_your_friend)).setNegativeButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }).show();
            return;
        }

        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        finish();

        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo写死了个reason，实际应该让用户手动填入
                    String s="";
                    if(TextUtils.isEmpty(reason)){
                        s = getResources().getString(R.string.Add_a_friend);
                    }else{
                        s=reason;
                    }
                    EMClient.getInstance().contactManager().addContact(hUserName, s);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(R.string.send_successful);
                            Toast.makeText(getApplicationContext(), s1,Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
