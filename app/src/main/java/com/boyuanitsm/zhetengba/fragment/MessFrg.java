package com.boyuanitsm.zhetengba.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.Constant;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.MainAct;
import com.boyuanitsm.zhetengba.activity.mess.AddFriendsAct;
import com.boyuanitsm.zhetengba.activity.mess.ContractsAct;
import com.boyuanitsm.zhetengba.activity.mess.CreateGroupAct;
import com.boyuanitsm.zhetengba.activity.mess.DqMesAct;
import com.boyuanitsm.zhetengba.activity.mess.ScanQrcodeAct;
import com.boyuanitsm.zhetengba.bean.ActivityMess;
import com.boyuanitsm.zhetengba.chat.act.ChatActivity;
import com.boyuanitsm.zhetengba.chat.db.InviteMessgeDao;
import com.boyuanitsm.zhetengba.db.ActivityMessDao;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 消息页面
 * Created by xiaoke on 2016/4/29.
 */
public class MessFrg extends EaseConversationListFragment implements View.OnClickListener {
    private TextView errorText;
    private PopupWindow mPopupWindow;

    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
        rlAdd.setOnClickListener(this);
        rlContract.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlAdd://新增
                addPop();
                break;
            case R.id.rlContract://联系人
                Intent intent = new Intent(getContext(), ContractsAct.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(receiver==null){
            receiver=new UpdateBroadCastReceiver();
            getContext().registerReceiver(receiver,new IntentFilter(UPDATE_CONTRACT));
        }
//        if (receiverpoint==null){
//            receiverpoint=new UpdatePointBroadCastReceiver();
//            getContext().registerReceiver(receiverpoint,new IntentFilter(UPDATE_POINT));
//        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            getContext().unregisterReceiver(receiver);
        }
//        if(receiverpoint!=null){
//            getContext().unregisterReceiver(receiverpoint);
//        }
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        // 注册上下文菜单
        List<ActivityMess> list = ActivityMessDao.getCircleUser();

//        tvmessage.setText("nihao");
        if (list != null && list.size() > 0) {
            Collections.reverse(list);
            tvmessage.setText(list.get(0).getMessage());
            tvUnReaNum.setVisibility(View.VISIBLE);
//            tvUnReaNum.setText("1");
        }else {
            tvmessage.setText("暂无新消息");
            tvUnReaNum.setVisibility(View.GONE);
        }

        registerForContextMenu(conversationListView);
       rlDq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvUnReaNum.setVisibility(View.GONE);
                    Intent dqIntent = new Intent(getContext(), DqMesAct.class);
                    getContext().startActivity(dqIntent);
                }
            });

            conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    MyToastUtils.showShortToast(getContext(),"点击了");
                    EMConversation conversation = conversationListView.getItem(position);
                    String username = conversation.getUserName();
                    if (username.equals(EMClient.getInstance().getCurrentUser()))
                        Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                    else {
                        // 进入聊天页面
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        if (conversation.isGroup()) {
                            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                                // it's group chat
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                            } else {
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                            }
                            // it's single chat

                        }
                        intent.putExtra(Constant.EXTRA_USER_ID, username);
                        startActivity(intent);

                    }


                }
            });
        }

        @Override
        protected void onConnectionDisconnected () {
            super.onConnectionDisconnected();
            if (NetUtils.hasNetwork(getActivity())) {
                errorText.setText(R.string.can_not_connect_chat_server_connection);
            } else {
                errorText.setText(R.string.the_current_network);
            }
        }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        if (tobeDeleteCons == null) {
            return true;
        }
        try {
            // 删除此会话
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.getUserName(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();
        // 更新消息未读数
        ((MainAct)getActivity()).updateUnreadLabel();
        return true;
    }


    /**
     * 待解决：对话框布局有出入
     * 选择对话框，选择好友/全部
     */
    private void addPop() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.act_pop_mess, null);
        mPopupWindow = new PopupWindow(v,layoutParams.width, layoutParams.height);
        LinearLayout ll_sao = (LinearLayout) v.findViewById(R.id.ll_sao);
        LinearLayout ll_qun = (LinearLayout) v.findViewById(R.id.ll_qunavatar);
        LinearLayout ll_add_friend = (LinearLayout) v.findViewById(R.id.ll_add_friend);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        mPopupWindow.setFocusable(true);
        //获取xoff
        WindowManager manager = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        int xpos = manager.getDefaultDisplay().getWidth() / 2 - mPopupWindow.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        mPopupWindow.showAsDropDown(rlAdd, xpos, 0);
        ll_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //扫一扫
                getActivity().startActivity(new Intent(getContext(), ScanQrcodeAct.class));
                mPopupWindow.dismiss();
            }
        });
        ll_qun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建群组
                getActivity().startActivity(new Intent(getContext(), CreateGroupAct.class));
                mPopupWindow.dismiss();
            }
        });
        ll_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加好友
                getActivity().startActivity(new Intent(getContext(), AddFriendsAct.class));
                mPopupWindow.dismiss();
            }
        });

    }


    public static final String UPDATE_CONTRACT="com.update.contract";
    private UpdateBroadCastReceiver receiver;

    class UpdateBroadCastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

            int chat_receiver = intent.getIntExtra("chat_receiver", 5);
            if (chat_receiver==3){
                tvUnReaNum.setVisibility(View.VISIBLE);
            }
            MyLogUtils.degug("接收到广播");
            InviteMessgeDao inviteMessgeDao=new InviteMessgeDao(mActivity);
            if (inviteMessgeDao.getUnreadMessagesCount()>0){
                iv_new_red.setVisibility(View.VISIBLE);
            }else {
                iv_new_red.setVisibility(View.GONE);
            }



            // 获取所有会话，包括陌生人
            Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
            // 过滤掉messages size为0的conversation
            /**
             * 如果在排序过程中有新消息收到，lastMsgTime会发生变化
             * 影响排序过程，Collection.sort会产生异常
             * 保证Conversation在Sort过程中最后一条消息的时间不变
             * 避免并发问题
             */
            List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
            synchronized (conversations) {
                for (EMConversation conversation : conversations.values()) {
                    if (conversation.getAllMessages().size() != 0) {
                        //if(conversation.getType() != EMConversationType.ChatRoom){
                        sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                        //}
                    }
                }
            }
            for (Pair<Long, EMConversation> sortItem : sortList) {
                EMConversation em=sortItem.second;
                if(em.getType() == EMConversation.EMConversationType.GroupChat){
                    if(EMClient.getInstance().groupManager().getGroup(em.getUserName())==null){
//                    // 删除此会话
                        EMClient.getInstance().chatManager().deleteConversation(em.getUserName(),true);
                        InviteMessgeDao imsDao = new InviteMessgeDao(getActivity());
                        imsDao.deleteMessage(em.getUserName());
                    }
                }
            }


             refresh();
        }
    }
//    public static final String UPDATE_POINT="com.update.point";
//    private UpdatePointBroadCastReceiver receiverpoint;
//
//    class UpdatePointBroadCastReceiver extends BroadcastReceiver{
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            MyLogUtils.degug("接收到广播");
//            InviteMessgeDao inviteMessgeDao=new InviteMessgeDao(mActivity);
//            if (inviteMessgeDao.getUnreadMessagesCount()>0){
//                iv_new_red.setVisibility(View.VISIBLE);
//            }else {
//                iv_new_red.setVisibility(View.GONE);
//            }
//        }
//    }

}


