package com.boyuanitsm.zhetengba.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.AppManager;
import com.boyuanitsm.zhetengba.Constant;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.LoginAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.chat.act.ChatActivity;
import com.boyuanitsm.zhetengba.chat.db.InviteMessgeDao;
import com.boyuanitsm.zhetengba.chat.db.UserDao;
import com.boyuanitsm.zhetengba.chat.domain.InviteMessage;
import com.boyuanitsm.zhetengba.fragment.MessFrg;
import com.boyuanitsm.zhetengba.fragment.MineFrg;
import com.boyuanitsm.zhetengba.fragment.calendarFrg.CalendarFrg;
import com.boyuanitsm.zhetengba.fragment.circleFrg.CircleFrg;
import com.boyuanitsm.zhetengba.view.MyRadioButton;
import com.boyuanitsm.zhetengba.view.PlaneDialog;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.EMLog;

import java.util.List;

/***
 * 设置首页信息
 */
public class MainAct extends BaseActivity {

    private FragmentManager fragmentManager;
    private CalendarFrg calendarFrg;
    private CircleFrg circleFrg;
    private MessFrg messFrg;
    private  PlaneDialog planeDialog;
    private MyRadioButton rb_mes;
//    // 未读消息textview
    private TextView unreadLabel;
    private final static int[] icons = {R.drawable.menu_ticket_b, R.drawable.menu_chat_b, R.drawable.menu_loop_b, R.drawable.menu_me_b};

    protected static final String TAG = "MainActivity";
    // 当前fragment的index
    private int currentTabIndex;
    // 账号在别处登录
    public boolean isConflict = false;
    // 账号被移除
    private boolean isCurrentAccountRemoved = false;
    private MineFrg mineFrg;

    /**
     * 检查当前用户是否被删除
     */
    public boolean getCurrentAccountRemoved() {
        return isCurrentAccountRemoved;
    }

    /**
     * 按两次退出键时间小于2秒退出
     */
    private final static long WAITTIME = 2000;
    private long touchTime = 0;


    private InviteMessgeDao inviteMessgeDao;
    private UserDao userDao;
    @Override
    public void setLayout() {

    }

    @Override
    public void init(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
            // 防止被移除后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            DemoHelper.getInstance().logout(false,null);
            finish();
            startActivity(new Intent(this, LoginAct.class));
            return;
        } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            // 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            finish();
            startActivity(new Intent(this, LoginAct.class));
            return;
        }
        setContentView(R.layout.act_main_layout);
        AppManager.getAppManager().addActivity(this);
        rb_mes= (MyRadioButton) findViewById(R.id.rb_mes);
        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        //获取frg的管理器
        fragmentManager = getSupportFragmentManager();
        //获取事物
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //默认展示“简约”界面
        defaultShow(transaction);
        //设置RadioButton的点击事件
        RadioGroup rg_button = (RadioGroup) findViewById(R.id.rg_button);
        RadioButton rb_cal = (RadioButton) findViewById(R.id.rb_cal);
        ImageView iv_plane = (ImageView) findViewById(R.id.iv_plane);
        planeDialog=new PlaneDialog(this);
        planeDialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);
        rb_cal.setChecked(true);
        iv_plane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planeDialog.show();
            }
        });
        rg_button.setOnCheckedChangeListener(new OnRadiGrroupCheckedChangeListener());

        inviteMessgeDao = new InviteMessgeDao(this);
        userDao = new UserDao(this);
        //注册local广播接收者，用于接收demohelper中发出的群组联系人的变动通知
        registerBroadcastReceiver();
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
    }



    /***
     * 首页默认加载节约界面
     */
    private void defaultShow(FragmentTransaction transaction) {
        hideFragment(transaction);
        if (calendarFrg == null) {
            calendarFrg = new CalendarFrg();
            transaction.add(R.id.fl_main, calendarFrg);
        } else {
            transaction.show(calendarFrg);
        }
        transaction.commit();

    }

    /***
     * 隐藏所有页面
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (calendarFrg != null) {
            transaction.hide(calendarFrg);
        }
        if (messFrg != null) {
            transaction.hide(messFrg);
        }
        if (circleFrg != null) {
            transaction.hide(circleFrg);
        }
        if (mineFrg!=null){
            transaction.hide(mineFrg);
        }

    }

    /***
     * radiobutton点击事件
     */

    private class OnRadiGrroupCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            hideFragment(transaction);
            switch (group.getCheckedRadioButtonId()) {
                case R.id.rb_cal://点击档期显示：简约/档期界面
                    currentTabIndex=0;
                    if (calendarFrg == null) {
                        calendarFrg = new CalendarFrg();
                        transaction.add(R.id.fl_main, calendarFrg);
                    } else {
                        transaction.show(calendarFrg);
                    }
                    break;
                case R.id.rb_mes://点击显示：消息界面
                    currentTabIndex=1;
                    if (messFrg == null) {
                        messFrg = new MessFrg();
                        transaction.add(R.id.fl_main, messFrg);
                    } else {
                        transaction.show(messFrg);
                    }
                    break;
                case R.id.rb_cir://点击显示：圈子界面
                    currentTabIndex=2;
                    if (circleFrg == null) {
                        circleFrg = new CircleFrg();
                        transaction.add(R.id.fl_main, circleFrg);
                    } else {
                        transaction.show(circleFrg);
                    }
                    break;
                case R.id.rb_my://点击显示：我的界面
                    currentTabIndex=3;
                    if (mineFrg==null) {
                        mineFrg = new MineFrg();
                        transaction.add(R.id.fl_main,mineFrg);
                    }else{
                        transaction.show(mineFrg);
                    }
                    break;
            }
            transaction.commit();
        }
    }


    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // 提示新消息
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // 刷新bottom bar消息未读数
                updateUnreadLabel();
                if (currentTabIndex == 1) {
                    // 当前页面如果为聊天历史页面，刷新此页面
                    if (messFrg != null) {
                        messFrg.refresh();
                    }
                }
            }
        });
    }



    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLable();
                if (currentTabIndex == 1) {
                    // 当前页面如果为聊天历史页面，刷新此页面
                    if (messFrg != null) {
                        messFrg.refresh();
                    }
                }
//                else if (currentTabIndex == 1) {
//                    if(contactListFragment != null) {
//                        contactListFragment.refresh();
//                    }
//                }
                String action = intent.getAction();
                //刷新群列表
//                if(action.equals(Constant.ACTION_GROUP_CHANAGED)){
//                    if (EaseCommonUtils.getTopActivity(MainAct.this).equals(GroupsActivity.class.getName())) {
//                        GroupsActivity.instance.onResume();
//                    }
//                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }



    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {}
        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainAct.this, ChatActivity.activityInstance.getToChatUsername() + st10,Toast.LENGTH_SHORT)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
        }
        @Override
        public void onContactInvited(String username, String reason) {}
        @Override
        public void onContactAgreed(String username) {}
        @Override
        public void onContactRefused(String username) {}
    }

    private void unregisterBroadcastReceiver(){
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (conflictBuilder != null) {
            conflictBuilder.create().dismiss();
            conflictBuilder = null;
        }
        unregisterBroadcastReceiver();

        try {
            unregisterReceiver(internalDebugReceiver);
        } catch (Exception e) {
        }

    }
    /**
     * 刷新未读消息数
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 刷新申请与通知消息数
     */
    public void updateUnreadAddressLable() {
        runOnUiThread(new Runnable() {
            public void run() {
                int count = getUnreadAddressCountTotal();
                if (count > 0) {
//					unreadAddressLable.setText(String.valueOf(count));
//                    unreadAddressLable.setVisibility(View.VISIBLE);
                } else {
//                    unreadAddressLable.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    /**
     * 获取未读申请与通知消息
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        for(EMConversation conversation:EMClient.getInstance().chatManager().getAllConversations().values()){
            if(conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount=chatroomUnreadMsgCount+conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal-chatroomUnreadMsgCount;
    }

    /**
     * 保存提示新消息
     *
     * @param msg
     */
//    private void notifyNewIviteMessage(InviteMessage msg) {
//        saveInviteMsg(msg);
//        // 提示有新消息
//        DemoHelper.getInstance().getNotifier().viberateAndPlayTone(null);
//
//        // 刷新bottom bar消息未读数
//        updateUnreadAddressLable();
//        // 刷新好友页面ui
//        if (currentTabIndex == 1)
//            contactListFragment.refresh();
//    }

    /**
     * 保存邀请等msg
     *
     * @param msg
     */
    private void saveInviteMsg(InviteMessage msg) {
        // 保存msg
        inviteMessgeDao.saveMessage(msg);
        //保存未读数，这里没有精确计算
        inviteMessgeDao.saveUnreadMessageCount(1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
        }

        // unregister this event listener when this activity enters the
        // background
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }
    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);

        super.onStop();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }

    //返回退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private android.app.AlertDialog.Builder conflictBuilder;
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    private BroadcastReceiver internalDebugReceiver;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;
    /**
     * 显示帐号在别处登录dialog
     */
    private void showConflictDialog() {
        isConflictDialogShow = true;
        DemoHelper.getInstance().logout(false,null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainAct.this.isFinishing()) {
            // clear up global variables
            try {
                if (conflictBuilder == null)
                    conflictBuilder = new android.app.AlertDialog.Builder(MainAct.this);
                conflictBuilder.setTitle(st);
                conflictBuilder.setMessage(R.string.connect_conflict);
                conflictBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conflictBuilder = null;
                        finish();
                        Intent intent = new Intent(MainAct.this, LoginAct.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                conflictBuilder.setCancelable(false);
                conflictBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color conflictBuilder error" + e.getMessage());
            }

        }

    }


    /**
     * 帐号被移除的dialog
     */
    private void showAccountRemovedDialog() {
        isAccountRemovedDialogShow = true;
        DemoHelper.getInstance().logout(false,null);
        String st5 = getResources().getString(R.string.Remove_the_notification);
        if (!MainAct.this.isFinishing()) {
            // clear up global variables
            try {
                if (accountRemovedBuilder == null)
                    accountRemovedBuilder = new android.app.AlertDialog.Builder(MainAct.this);
                accountRemovedBuilder.setTitle(st5);
                accountRemovedBuilder.setMessage(R.string.em_user_remove);
                accountRemovedBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        accountRemovedBuilder = null;
                        finish();
                        startActivity(new Intent(MainAct.this, LoginAct.class));
                    }
                });
                accountRemovedBuilder.setCancelable(false);
                accountRemovedBuilder.create().show();
                isCurrentAccountRemoved = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color userRemovedBuilder error" + e.getMessage());
            }

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
    }
}
