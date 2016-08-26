package com.boyuanitsm.zhetengba.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.PersonalAct;
import com.boyuanitsm.zhetengba.activity.mess.NewFriendsMsgActivity;
import com.boyuanitsm.zhetengba.bean.DataBean;
import com.boyuanitsm.zhetengba.bean.FriendsBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.chat.act.HeiAct;
import com.boyuanitsm.zhetengba.chat.act.MyGroupAct;
import com.boyuanitsm.zhetengba.chat.db.InviteMessgeDao;
import com.boyuanitsm.zhetengba.chat.db.UserDao;
import com.boyuanitsm.zhetengba.db.ChatUserDao;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.CharacterParserUtils;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.view.CommonView;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.util.EMLog;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 联系人Fragment
 * Created by wangbin on 16/5/13.
 */
public class ContractsFrg extends EaseContactListFragment {
    private static final String TAG = "contract";
    private View loadingView;
    private InviteMessgeDao inviteMessgeDao;
    private ContactSyncListener contactSyncListener;
    private BlackListSyncListener blackListSyncListener;
    private ContactInfoSyncListener contactInfoSyncListener;

    private TextView tvUnReadMsg;
    private UserDao userDao;
    @Override
    protected void initView() {
        super.initView();
        userDao=new UserDao(getContext());
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.em_contacts_header, null);
        HeaderItemClickListener clickListener = new HeaderItemClickListener();
        CommonView commNf= (CommonView) headerView.findViewById(R.id.cvNewF);
        CommonView commGroup= (CommonView) headerView.findViewById(R.id.cvMyQl);
        CommonView commHei= (CommonView) headerView.findViewById(R.id.cvHei);
        commNf.setArrowGone();
        commGroup.setArrowGone();
        commHei.setArrowGone();
        commNf.setOnClickListener(clickListener);
        commGroup.setOnClickListener(clickListener);
        commHei.setOnClickListener(clickListener);
        tvUnReadMsg= (TextView) headerView.findViewById(R.id.tvUnReadMsg);
        //添加headerview
        listView.addHeaderView(headerView);
        //添加正在加载数据提示的loading view
        loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.em_layout_loading_data, null);
        contentContainer.addView(loadingView);
        //注册上下文菜单
        registerForContextMenu(listView);
    }

    @Override
    public void refresh() {
        RequestManager.getMessManager().getFriends("-1", "-1", new ResultCallback<ResultBean<DataBean<FriendsBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
                if (m instanceof Hashtable<?, ?>) {
                    m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
                }
                setContactsMap(m);
                getContactList();
                contactListLayout.refresh();
                if (inviteMessgeDao == null) {
                    inviteMessgeDao = new InviteMessgeDao(getActivity());
                }
                if (inviteMessgeDao.getUnreadMessagesCount() > 0) {
                    tvUnReadMsg.setVisibility(View.VISIBLE);
                } else {
                    tvUnReadMsg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onResponse(ResultBean<DataBean<FriendsBean>> response) {
                DataBean<FriendsBean> dataBean = response.getData();
                if (dataBean != null) {
                    List<FriendsBean> list = dataBean.getRows();
                    MyLogUtils.info(list.toString());
                    if (list != null && list.size() > 0) {
                        List<EaseUser> uList = new ArrayList<EaseUser>();
                        for (FriendsBean friendsBean : list) {
                            if(friendsBean!=null) {
                                EaseUser easeUser = new EaseUser(friendsBean.getId());
                                if (!TextUtils.isEmpty(friendsBean.getPetName())) {
                                    easeUser.setNick(friendsBean.getPetName());
                                    easeUser.setInitialLetter(CharacterParserUtils.getInstance().getSelling(friendsBean.getPetName()).substring(0, 1).toLowerCase());
                                } else {
                                    easeUser.setNick(friendsBean.getUsername());
                                    easeUser.setInitialLetter("#");
                                }

                                easeUser.setAvatar(IZtbUrl.BASE_URL + friendsBean.getIcon());
//                                        easeUser.setAvatar("http://172.16.6.253:8089/zhetengba/userIcon/90017a421ee84e0db5c6d53e55c03c50.png");
                                uList.add(easeUser);

                            }
                        }
                        DemoHelper.getInstance().updateContactList(uList);
                        ChatUserDao.updateUsers(uList);
//                        userDao.saveContactList(uList);
                    }
                }

                Map<String, EaseUser> m = userDao.getContactList();
//                Map<String, EaseUser> m=DemoHelper.
                if (m instanceof Hashtable<?, ?>) {
                    m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
                }
                setContactsMap(m);
                getContactList();
                contactListLayout.refresh();
                if (inviteMessgeDao == null) {
                    inviteMessgeDao = new InviteMessgeDao(getActivity());
                }
                if (inviteMessgeDao.getUnreadMessagesCount() > 0) {
                    tvUnReadMsg.setVisibility(View.VISIBLE);
                } else {
                    tvUnReadMsg.setVisibility(View.GONE);
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUpView() {
        //设置联系人数据
        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        super.setUpView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String username = ((EaseUser) listView.getItemAtPosition(position)).getUsername();
                // demo中直接进入聊天页面，实际一般是进入用户详情页
//                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", username));
                Intent intent = new Intent(getActivity(), PersonalAct.class);
                intent.putExtra("userId", username);
                startActivity(intent);
            }
        });




        contactSyncListener = new ContactSyncListener();
        DemoHelper.getInstance().addSyncContactListener(contactSyncListener);

        blackListSyncListener = new BlackListSyncListener();
        DemoHelper.getInstance().addSyncBlackListListener(blackListSyncListener);

        contactInfoSyncListener = new ContactInfoSyncListener();
        DemoHelper.getInstance().getUserProfileManager().addSyncContactInfoListener(contactInfoSyncListener);

        if (DemoHelper.getInstance().isContactsSyncedWithServer()) {
            loadingView.setVisibility(View.GONE);
        } else if (DemoHelper.getInstance().isSyncingContactsWithServer()) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    class ContactSyncListener implements DemoHelper.DataSyncListener {
        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contact list sync success:" + success);
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (success) {
                                    loadingView.setVisibility(View.GONE);
                                    refresh();
                                } else {
                                    String s1 = getResources().getString(R.string.get_failed_please_check);
                                    Toast.makeText(getActivity(), s1, Toast.LENGTH_SHORT).show();
                                    loadingView.setVisibility(View.GONE);
                                }
                            }

                        });
                    }
                });
            }

        }
    }

    class BlackListSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(boolean success) {
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                });
            }

        }

    };

    class ContactInfoSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contactinfo list sync success:" + success);
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        loadingView.setVisibility(View.GONE);
                        if (success) {
                            refresh();
                        }
                    }
                });
            }
        }

    }

    protected class HeaderItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cvNewF:
                    // 进入申请与通知页面
                    startActivity(new Intent(getActivity(), NewFriendsMsgActivity.class));
                    break;
                case R.id.cvMyQl:
                    // 我的群聊
                    startActivity(new Intent(getActivity(), MyGroupAct.class));
                    break;
                case R.id.cvHei:
                    //进入黑名单
                    startActivity(new Intent(getActivity(),HeiAct.class));
                default:
                    break;
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if(receiver==null){
            receiver=new ContractBroadCast();
            getActivity().registerReceiver(receiver,new IntentFilter(UPDATE_CONTRACT));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            getActivity().unregisterReceiver(receiver);
        }
    }

    private ContractBroadCast receiver;
    public static final String UPDATE_CONTRACT="com.update.contract";
    class ContractBroadCast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
           refresh();
        }
    }

}
