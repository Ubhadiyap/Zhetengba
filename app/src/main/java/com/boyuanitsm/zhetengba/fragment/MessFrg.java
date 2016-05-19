package com.boyuanitsm.zhetengba.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.Constant;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mess.AddFriendsAct;
import com.boyuanitsm.zhetengba.activity.mess.ContractsAct;
import com.boyuanitsm.zhetengba.activity.mess.CreateGroupAct;
import com.boyuanitsm.zhetengba.activity.mess.DqMesAct;
import com.boyuanitsm.zhetengba.activity.mess.ScanQrcodeAct;
import com.boyuanitsm.zhetengba.chat.act.ChatActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;

/**
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
    protected void setUpView() {
        super.setUpView();
        // 注册上下文菜单
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {//档期消息
                    Intent dqIntent = new Intent(getContext(), DqMesAct.class);
                    getContext().startActivity(dqIntent);
                } else {
                    EMConversation conversation = conversationListView.getItem(position - 1);
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

                        }
                        // it's single chat
                        intent.putExtra(Constant.EXTRA_USER_ID, username);
                        startActivity(intent);
                    }

                }


            }
        });
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }

    /**
     * 待解决：对话框布局有出入
     * 选择对话框，选择好友/全部
     */
    private void addPop() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow = new PopupWindow(layoutParams.width, layoutParams.height);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.act_pop_mess, null);
        LinearLayout ll_sao = (LinearLayout) v.findViewById(R.id.ll_sao);
        LinearLayout ll_qun = (LinearLayout) v.findViewById(R.id.ll_qun);
        LinearLayout ll_add_friend = (LinearLayout) v.findViewById(R.id.ll_add_friend);

        mPopupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_stroke));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setContentView(v);
        mPopupWindow.showAsDropDown(rlAdd, -150, 10);
        ll_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), ScanQrcodeAct.class));
                mPopupWindow.dismiss();
            }
        });
        ll_qun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), CreateGroupAct.class));
                mPopupWindow.dismiss();
            }
        });
        ll_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), AddFriendsAct.class));
                mPopupWindow.dismiss();
            }
        });

    }

}
