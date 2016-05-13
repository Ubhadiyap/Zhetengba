package com.boyuanitsm.zhetengba.chat.act;

import android.content.Intent;
import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.MainAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.chat.frg.ChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;

/**
 * 聊天页面，需要fragment的使用{@link #}
 *
 */
public class ChatActivity extends BaseActivity {
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    public String toChatUsername;

    @Override
    public void setLayout() {
        setContentView(R.layout.em_activity_chat);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        activityInstance = this;
        //聊天人或群id
        toChatUsername = getIntent().getExtras().getString("userId");
        //可以直接new EaseChatFratFragment使用
        chatFragment = new ChatFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainAct.class);
            startActivity(intent);
        }
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }
}
