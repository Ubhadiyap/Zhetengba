package com.boyuanitsm.zhetengba.chat.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.Constant;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.chat.adapter.GroupAdapter;
import com.boyuanitsm.zhetengba.widget.ClearEditText;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * 我的群聊
 * Created by wangbin on 16/5/16.
 */
public class MyGroupAct extends BaseActivity {

    private ListView groupListView;
    protected List<EMGroup> grouplist;
    private GroupAdapter groupAdapter;
    private InputMethodManager inputMethodManager;

    private ClearEditText cetSearch;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_mygroup);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("群聊列表");
        cetSearch= (ClearEditText) findViewById(R.id.cetSearch);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        grouplist = EMClient.getInstance().groupManager().getAllGroups();
        groupListView = (ListView) findViewById(R.id.list);
        groupAdapter = new GroupAdapter(this, 1, grouplist);
        groupListView.setAdapter(groupAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                            handler.sendEmptyMessage(0);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(1);
                        }
                    }
                }.start();
            }
        });
        //搜索
        cetSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                groupAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 进入群聊
                Intent intent = new Intent(MyGroupAct.this, ChatActivity.class);
                // it is group chat
                intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                intent.putExtra("userId", groupAdapter.getItem(position).getGroupId());
                startActivityForResult(intent, 0);
            }

        });
        groupListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    if (getCurrentFocus() != null)
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
    }

    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            swipeRefreshLayout.setRefreshing(false);
            switch (msg.what) {
                case 0:
                    refresh();
                    break;
                case 1:
                    Toast.makeText(MyGroupAct.this, R.string.Failed_to_get_group_chat_information, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        refresh();
        super.onResume();
    }

    private void refresh(){
        grouplist = EMClient.getInstance().groupManager().getAllGroups();
        groupAdapter = new GroupAdapter(this, 1, grouplist);
        groupListView.setAdapter(groupAdapter);
        groupAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
