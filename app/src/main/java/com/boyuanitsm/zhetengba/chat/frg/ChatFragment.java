package com.boyuanitsm.zhetengba.chat.frg;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.Constant;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.MainAct;
import com.boyuanitsm.zhetengba.activity.PersonalAct;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.chat.act.ContextMenuActivity;
import com.boyuanitsm.zhetengba.chat.act.ForwardMessageActivity;
import com.boyuanitsm.zhetengba.chat.act.GroupDetailsActivity;
import com.boyuanitsm.zhetengba.chat.domain.EmojiconExampleGroupData;
import com.boyuanitsm.zhetengba.chat.domain.RobotUser;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.utils.SpUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentListener;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

public class ChatFragment extends EaseChatFragment implements EaseChatFragmentListener {

    //避免和基类定义的常量可能发生的冲突，常量从11开始定义
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;

    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    public static final String FIRE_ON = "fire_on";
    public static final String FIRE_CLOSE = "fire_close";
    /**
     * 是否为环信小助手
     */
    private boolean isRobot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            Map<String, RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
            if (robotMap != null && robotMap.containsKey(toChatUsername)) {
                isRobot = true;
            }
        }
        super.setUpView();
        // 设置标题栏点击事件
        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainAct.class);
                    startActivity(intent);
                }
                getActivity().finish();
            }
        });
        ((EaseEmojiconMenu) inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconExampleGroupData.getData());

        inputMenu.setSwitchIsOn(SpUtils.getIsReadDestory(getContext()));//设置阅后即焚烧
        inputMenu.setSetSwitch(new EaseChatInputMenu.SetSwitch() {
            @Override
            public void getIsSwitch(boolean isSwitch) {
                SpUtils.setRead(getContext(), isSwitch);
            }
        });


    }

    @Override
    protected void registerExtendMenuItem() {
        //demo这里不覆盖基类已经注册的item,item点击listener沿用基类的
        super.registerExtendMenuItem();
        //增加扩展item
//        inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
//        inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);
//        if(chatType == Constant.CHATTYPE_SINGLE){
//            inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, ITEM_VOICE_CALL, extendMenuItemClickListener);
//            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, ITEM_VIDEO_CALL, extendMenuItemClickListener);
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // 复制消息
                    clipboard.setText(((EMTextMessageBody) contextMenuMessage.getBody()).getMessage());
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // 删除消息
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    messageList.refresh();
                    break;

                case ContextMenuActivity.RESULT_CODE_FORWARD: // 转发消息
                    Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
                    intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
                    startActivity(intent);

                    break;

                default:
                    break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //发送选中的视频
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //发送选中的文件
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (isRobot) {
            //设置消息扩展属性
            message.setAttribute("em_robot_message", isRobot);
        }
        // 通过扩展字段标识为阅后即焚消息（参数可以自定义，但要求与ios保持一致）
        if (SpUtils.getIsReadDestory(getContext()))
            message.setAttribute("fire", FIRE_ON);
        else
            message.setAttribute("fire", FIRE_CLOSE);
//        message.setAttribute("userName",UserInfoDao.getUser().getId());
        message.setAttribute("nick", UserInfoDao.getUser().getPetName());
        message.setAttribute("icon", Uitls.imageFullUrl(UserInfoDao.getUser().getIcon()));

    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        //设置自定义listview item提供者
        return new CustomChatRowProvider();
    }


    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            startActivityForResult(
                    (new Intent(getActivity(), GroupDetailsActivity.class).putExtra("groupId", toChatUsername)),
                    REQUEST_CODE_GROUP_DETAIL);
        }
//        else if(chatType == Constant.CHATTYPE_CHATROOM){
//        	startActivityForResult(new Intent(getActivity(), ChatRoomDetailsActivity.class).putExtra("roomId", toChatUsername), REQUEST_CODE_GROUP_DETAIL);
//        }
    }

    /**
     * 点击头像
     *
     * @param username
     */
    @Override
    public void onAvatarClick(String username) {
        if (!username.equals(UserInfoDao.getUser().getId())) {
            //头像点击事件
            Intent intent = new Intent(getActivity(), PersonalAct.class);
            intent.putExtra("userId", username);
            if (chatType == Constant.CHATTYPE_SINGLE) {
                intent.putExtra("chat_type", 1);
            }
            startActivity(intent);
        }

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        //消息框长按
        startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message", message),
                REQUEST_CODE_CONTEXT_MENU);
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO: //视频
//            Intent intent = new Intent(getActivity(), ImageGridActivity.class);
//            startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                break;
            case ITEM_FILE: //一般文件
                //demo这里是通过系统api选择文件，实际app中最好是做成qq那种选择发送文件
                selectFileFromLocal();
                break;
            case ITEM_VOICE_CALL: //音频通话
//            startVoiceCall();
                break;
            case ITEM_VIDEO_CALL: //视频通话
//            startVideoCall();
                break;

            default:
                break;
        }
        //不覆盖已有的点击事件
        return false;
    }

    /**
     * 选择文件
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //19以后这个api不可用，demo这里简单处理成图库选择图片
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    /**
     * 拨打语音电话
     */
//    protected void startVoiceCall() {
//        if (!EMClient.getInstance().isConnected()) {
//            Toast.makeText(getActivity(), R.string.not_connect_to_server, 0).show();
//        } else {
//            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
//                    .putExtra("isComingCall", false));
//            // voiceCallBtn.setEnabled(false);
//            inputMenu.hideExtendMenuContainer();
//        }
//    }

    /**
     * 拨打视频电话
     */
//    protected void startVideoCall() {
//        if (!EMClient.getInstance().isConnected())
//            Toast.makeText(getActivity(), R.string.not_connect_to_server, 0).show();
//        else {
//            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
//                    .putExtra("isComingCall", false));
//            // videoCallBtn.setEnabled(false);
//            inputMenu.hideExtendMenuContainer();
//        }
//    }

    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //音、视频通话发送、接收共4种
            return 4;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //语音通话类型
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    //视频通话
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // 语音通话,  视频通话
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                        message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
//                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
                }
            }
            return null;
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            getActivity().unregisterReceiver(receiver);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if(receiver==null){
            receiver=new UpdateGReceiver();
            getActivity().registerReceiver(receiver,new IntentFilter(UPDATE_GROUP_NAME));
        }
    }

    private UpdateGReceiver receiver;
    public static final String UPDATE_GROUP_NAME="com.update.groupname";
    class UpdateGReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int chat = intent.getIntExtra("chat", 0);
            if (chat==1){
                //单聊
                String nickName = intent.getStringExtra("nickName");
                titleBar.setTitle(nickName);
            }else {
                titleBar.setTitle(intent.getStringExtra("groupName"));
            }
        }
    }

}
