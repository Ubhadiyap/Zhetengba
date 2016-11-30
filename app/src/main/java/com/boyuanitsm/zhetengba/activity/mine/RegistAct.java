package com.boyuanitsm.zhetengba.activity.mine;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.AppManager;
import com.boyuanitsm.zhetengba.MyApplication;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserBean;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.chat.db.DemoDBManager;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ShUtils;
import com.boyuanitsm.zhetengba.utils.SpUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 注册界面
 * Created by bitch-1 on 2016/4/28.
 */
public class RegistAct extends BaseActivity {
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.et_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.et_cpwd)
    private EditText et_cpwd;
    @ViewInject(R.id.et_yzm)
    private EditText et_yzm;
    @ViewInject(R.id.tv_code)
    private TextView tv_code;
    @ViewInject(R.id.register_cb)
    private CheckBox register_cb;
    @ViewInject(R.id.tv_zc)
    private TextView tv_zc;
    @ViewInject(R.id.et_aqm)
    private EditText et_aqm;
    @ViewInject(R.id.iv_aqm)
    private ImageView iv_aqm;
    @ViewInject(R.id.et_yqphone)
    private EditText et_yqphone;

    private String phone, yzm, pwd, cpwd, aqm, yqphone, imgcatchurl, zifu;//手机号，验证码，确认的密码,安全码，邀请人手机号,图片验证码，字符
    private int i = 60;
    private Timer timer;
    private MyTimerTask myTask;

    private static final String TAG = "RegAct";
    private ProgressDialog pd;

    private Bitmap bitmap;
    private int type;

    private boolean ispress;//是否可以点击默认为false;真比较可以点击，假表示不能点击

    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.yzmjiazai)
            .showImageOnFail(R.mipmap.yzmjiazai).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    @Override
    public void setLayout() {
        setContentView(R.layout.act_regist);

    }

    @Override
    public void init(Bundle savedInstanceState) {
//        getImaCatch();//获取图像验证码
        setTopTitle("注册");
       String phoneNum= getIntent().getStringExtra("phoneNum");
        if (!TextUtils.isEmpty(phoneNum)){
            et_phone.setText(phoneNum);
        }
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });
        pd.setMessage("注册中...");
//
        register_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tv_zc.setEnabled(true);
                    tv_zc.setBackgroundResource(R.drawable.com_dybtn_select);
                } else {
                    tv_zc.setBackgroundResource(R.drawable.com_dybtn_hui);
                    tv_zc.setEnabled(false);
                }
            }
        });

//        et_phone.addTextChangedListener(textWatcher);
//        et_yzm.addTextChangedListener(textWatcher);
//        et_pwd.addTextChangedListener(textWatcher);
//        et_cpwd.addTextChangedListener(textWatcher);

    }

    /**
     * 获取图像验证码
     */
    private void getImaCatch() {
        new Thread() {
            @Override
            public void run() {
                type = 1;
                super.run();
                try {
                    URL url = new URL(IZtbUrl.FINDIMGCAPTCHA_URL);
                    //打开URL对应的资源输入流
                    HttpURLConnection conn = null;
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.connect();
                    String cookie = conn.getHeaderField("set-cookie");
                    InputStream in = conn.getInputStream();
                    if (!TextUtils.isEmpty(cookie)){
                        SpUtils.setCooike(MyApplication.getInstance(), cookie);
                    }
                    //从InputStream流中解析出图片
                    bitmap = BitmapFactory.decodeStream(in);
                    //  imageview.setImageBitmap(bitmap);
                    //发送消息，通知UI组件显示图片\
                    Message msg = Message.obtain();
                    msg.what = 0x9527;
//                    handleryzm.sendMessage(msg);
                    handler.sendMessage(msg);
                    //关闭输入流
                    in.close();
                } catch (Exception e) {
                    iv_aqm.setImageResource(R.mipmap.yzmjiazai);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            phone = et_phone.getText().toString().trim();
            yzm = et_yzm.getText().toString().trim();
            pwd = et_pwd.getText().toString().trim();
            cpwd = et_cpwd.getText().toString().trim();
            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(yzm) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(cpwd)) {
                tv_zc.setEnabled(true);
                tv_zc.setBackgroundResource(R.drawable.com_dybtn_select);
            } else {
                tv_zc.setBackgroundResource(R.drawable.com_dybtn_hui);
                tv_zc.setEnabled(false);
            }

        }
    };


    @OnClick({R.id.tv_code, R.id.tv_zc, R.id.tv_xy, R.id.iv_aqm})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_code://发送验证码
                phone = et_phone.getText().toString().trim();
//                aqm = et_aqm.getText().toString().toLowerCase();
                if (TextUtils.isEmpty(phone)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入手机号");
                    return;
                }
                if (phone.length() != 11) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入11的手机号");
                    et_phone.requestFocus();
                    et_phone.setSelection(et_phone.length());
                    return;
                }
                if (!ZhetebaUtils.checkCellPhone(phone)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号码");
                    return;
                }
//                if (TextUtils.isEmpty(aqm)) {
//                    MyToastUtils.showShortToast(getApplicationContext(), "请输入安全码");
//                    et_aqm.requestFocus();
//                    return;
//                }
//                if(!aqm.equals(zifu)){
//                    MyToastUtils.showShortToast(getApplicationContext(), "安全码不正确");
//                    et_aqm.requestFocus();
//                    return;
//                }

                sendSms(phone, "true");


                break;
            case R.id.tv_xy://注册协议
                openActivity(WebAct.class);
                break;


            case R.id.tv_zc://注册
                if (isValidate()) {
                    pd.show();
//                    MyToastUtils.showShortToast(getApplicationContext(), "注册成功");
                    toRegister(phone, yzm, pwd, yqphone);
                }

                break;
            case R.id.iv_aqm:
                getImaCatch();//获取图像验证码
                break;

        }


    }

    /**
     * 是否可用
     *
     * @return
     */
    private boolean isValidate() {
        phone = et_phone.getText().toString().trim();
        yzm = et_yzm.getText().toString().trim();
        pwd = et_pwd.getText().toString();//.trim();
        cpwd = et_cpwd.getText().toString();//.trim();
//        aqm = et_aqm.getText().toString().toLowerCase();
        yqphone = et_yqphone.getText().toString().trim();
//        yqphone=et_yqphone.getText().toString();
//        if (TextUtils.isEmpty(aqm)) {
//            MyToastUtils.showShortToast(getApplicationContext(), "请输入安全码");
//            et_aqm.requestFocus();
//            return false;
//        }
//        if (!aqm.equals(zifu)) {
//            MyToastUtils.showShortToast(getApplicationContext(), "安全码不正确");
//            et_aqm.requestFocus();
//            return false;
//        }

        if (!TextUtils.isEmpty(yqphone) && yqphone != null && yqphone.length() != 11) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入11位手机号码");
            et_yqphone.requestFocus();
            return false;
        }
//        if(yqphone.length()!=11){
//            MyToastUtils.showShortToast(getApplicationContext(), "请输入11位的手机号");
//            et_yqphone.requestFocus();
//            et_yqphone.setSelection(et_yqphone.length());
//            return false;
//        }
        if (!TextUtils.isEmpty(yqphone) && yqphone != null && !ZhetebaUtils.checkCellPhone(yqphone)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号码");
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入手机号");
            et_phone.requestFocus();
            return false;
        }
        if (phone.length() != 11) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入11位的手机号");
            et_phone.requestFocus();
            et_phone.setSelection(et_phone.length());
            return false;
        }
        if (!ZhetebaUtils.checkCellPhone(phone)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号码");
            return false;
        }

        if (TextUtils.isEmpty(yzm)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入验证码");
            et_yzm.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入密码");
            et_pwd.requestFocus();
            return false;
        }
        if (pwd.length() < 4) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入至少4位密码");
            et_pwd.requestFocus();
            et_pwd.setSelection(pwd.length());
            return false;
        }
        if (pwd.length() > 24) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入不超过24位密码");
            et_pwd.requestFocus();
            et_pwd.setSelection(pwd.length());
            return false;
        }
//        if (TextUtils.isEmpty(cpwd)) {
//            MyToastUtils.showShortToast(getApplicationContext(), "请确认密码");
//            et_cpwd.requestFocus();
//            return false;
//        }
//
//        if (!(cpwd.equals(pwd))) {
//            MyToastUtils.showShortToast(getApplicationContext(), "确认密码输入错误");
//            et_cpwd.requestFocus();
//            return false;
//        }

//        if(!ZhetebaUtils.checkPwd(pwd)){
//            MyToastUtils.showShortToast(getApplicationContext(), "请输入4-24位字母和数字");
//            return false;
//        }
        if (!register_cb.isChecked()) {
            MyToastUtils.showShortToast(getApplicationContext(), "请勾选条例");
            return false;
        }

        return true;
    }


    /**
     * 倒计时
     *
     * @author wangbin
     */
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            type = 2;
            handler.sendEmptyMessage(i--);
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (type == 1) {
//                if (msg.what == 0x9527) {
//                    //显示从网上下载的图片
//                    iv_aqm.setImageBitmap(bitmap);
//                }
//            }
//            if (type == 2) {
                if (msg.what == 0 || msg.what < 0) {
                    tv_code.setEnabled(true);
                    tv_code.setText("重新发送");
                    timer.cancel();
                    myTask.cancel();
                } else {
                    tv_code.setText(msg.what + "秒");
                }
//            }
        }

    };


    private boolean progressShow;

    /**
     * 注册
     *
     * @param username
     * @param captcha
     * @param password
     */

    public void toRegister(final String username, String captcha, String password, String referralCode) {
        RequestManager.getUserManager().register(username, captcha, password, referralCode, new ResultCallback<ResultBean<UserBean>>() {

            @Override
            public void onError(int status, String errorMsg) {
                pd.dismiss();
                MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
            }

            @Override

            public void onResponse(ResultBean<UserBean> response) {
                UserBean userBean = response.getData();
                login(userBean);
            }
        });

    }

    /**
     * 发送验证码接口
     *
     * @param phoneNumber
     * @param isRegister
     */
    public void sendSms(String phoneNumber, String isRegister) {
        tv_code.setEnabled(false);
        RequestManager.getUserManager().getSms2(phoneNumber, isRegister, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                tv_code.setEnabled(true);
                MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                i = 60;
                timer = new Timer();
                myTask = new MyTimerTask();
                timer.schedule(myTask, 0, 1000);
                MyToastUtils.showShortToast(getApplicationContext(), "验证码发送成功");

            }
        });

    }


    private String currentUsername;
    private String currentPassword;

    /**
     * 登录环信
     *
     * @param
     */
    public void login(final UserBean userBean) {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        currentUsername = userBean.getUser().gethUsername();
        currentPassword = userBean.getUser().gethPassword();
        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        progressShow = true;

        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(currentUsername);

        final long start = System.currentTimeMillis();
        // 调用sdk登陆方法登陆聊天服务器
        Log.d(TAG, "EMClient.getInstance().login");
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");

                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
                        MyApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }
                //异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                DemoHelper.getInstance().getUserProfileManager().setNickName(userBean.getUser().getUsername());
                DemoHelper.getInstance().getUserProfileManager().setUserAvatar(Uitls.imageFullUrl(userBean.getUser().getIcon()));
                UserInfoDao.saveUser(userBean.getUser());

                if (!RegistAct.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
                openActivity(RegInfoAct.class);
                AppManager.getAppManager().finishActivity(LoginAct.class);
                // 进入主页面
//                Intent intent = new Intent(RegistAct.this,
//                        MainAct.class);
//                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}
