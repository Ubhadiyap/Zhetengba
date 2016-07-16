package com.boyuanitsm.zhetengba.activity.mine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.Constant;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.widget.ClearEditText;
import com.hyphenate.easeui.adapter.EaseContactAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 指定谁能看,不能看
 * Created by wangbin on 16/5/19.
 */
public class AssignScanAct extends BaseActivity {
    private ProgressDialog progressDialog;
    @ViewInject(R.id.list)
    private ListView listView;
    private List<String> idList=new ArrayList<>();//存取用户名list
    private PickContactAdapter contactAdapter;
    private boolean isSignleChecked = false;
    private String[] strUserIds;//c存取已经选择的用户id
    private int change=1;
    @ViewInject(R.id.cetSearch)
    private ClearEditText cetSearch;//搜索
    public static final String CANTYPE="cantype";
    private int type;//0 能看 1不能看
    private String title;//右上方文字
    List<EaseUser> alluserList;
    private String etContent;

    private Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1==0){
                alluserList.clear();
                Collection<EaseUser> values = DemoHelper.getInstance().getContactList().values();
                for (EaseUser user:values){
                    if (user.getNick().contains(etContent)){
                        alluserList.add(user);
                    }
                }
                sortList(alluserList);
            }
        }
    };
    @Override
    public void setLayout() {
        setContentView(R.layout.act_who_can);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        type=bundle.getInt(CANTYPE,-1);
        switch (type){
            case 0:
                title="确认";
                break;
            case 1:
                title="完成";
                break;
            default:
                title="发送";
                break;
        }
        final String str3 = bundle.getString("can");
        String str4 = bundle.getString("canUserIds");//谁能看
        MyLogUtils.info(str4+"返回的谁能看");
        String str5 = bundle.getString("noCanUserIds");//谁不能看
        MyLogUtils.info(str5+"返回的谁不能看");
        String canflag = bundle.getString("canFlag");//判断点击进入的标志
        if (!TextUtils.isEmpty(str4)) {
            if (canflag.equals("canFlag") || canflag.equals("CalcanFlag")) {
                strUserIds = ZtinfoUtils.convertStrToArray(str4);
                for (int i=0;i<strUserIds.length;i++){
                    idList.add(strUserIds[i]);
                }
                MyLogUtils.info(idList.toString()+"返回谁能看集合");
            }

        } else if (!TextUtils.isEmpty(str5)) {
            if (canflag.equals("noCanFlag") || canflag.equals("CalnocanFlag")) {
                strUserIds = ZtinfoUtils.convertStrToArray(str5);
                for (int i=0;i<strUserIds.length;i++){
                    idList.add(strUserIds[i]);
                }
                MyLogUtils.info(idList.toString()+"返回谁不能看集合");
            }
        }

        setTopTitle("联系人");
        setRight(title, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(str3);
                finish();
            }
        });

        // 获取好友列表
        alluserList = new ArrayList<EaseUser>();
        for (EaseUser user : DemoHelper.getInstance().getContactList().values()) {
//            if (!user.getUsername().equals(Constant.NEW_FRIENDS_USERNAME) & !user.getUsername().equals(Constant.GROUP_USERNAME) & !user.getUsername().equals(Constant.CHAT_ROOM) & !user.getUsername().equals(Constant.CHAT_ROBOT))
                alluserList.add(user);
        }


        sortList(alluserList);
        cetSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etContent=s.toString().trim();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.arg1=0;
                        myHandler.sendMessage(message);
                    }
                }).start();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void sortList(final List<EaseUser> alluserList){
        // 对list进行排序
        Collections.sort(alluserList, new Comparator<EaseUser>() {
            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
                if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
                    return lhs.getNick().compareTo(rhs.getNick());
                } else {
                    if ("#".equals(lhs.getInitialLetter())) {
                        return 1;
                    } else if ("#".equals(rhs.getInitialLetter())) {
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }

            }
        });

        contactAdapter = new PickContactAdapter(this, R.layout.em_who_scan_contact_with_checkbox, alluserList);
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                String strUserId = alluserList.get(position).getUsername();
                checkBox.toggle();
                if (checkBox.isChecked()) {
                    change = 2;
                }else {
                    change=0;
                    if (idList.contains(strUserId)){
                        idList.remove(strUserId);
                    }
                }

            }
        });
    }
    private void save(String str3) {
        Intent intent = new Intent();
        Bundle bundle3 = new Bundle();//谁能看
        String userIds = null;
        if (change==2){
            idList=getToBeAddMembers();
        }
        if (idList.size() != 0) {
            if (idList.size() == 1) {
                userIds = idList.get(0);
            } else if (idList.size()>1){
                userIds = idList.get(0);
                for (int i = 1; i < idList.size(); i++) {
                    userIds = userIds + "," + idList.get(i);
                }
            }
        }
        MyLogUtils.info(userIds + "指定谁看的用户id");
        bundle3.putString("bundleIds", userIds);
        intent.putExtra("bundle3", bundle3);
        if (str3.equals("hu_can")) {
            setResult(1, intent);
        } else if (str3.equals("hu_no_can")) {
            setResult(2, intent);
        } else if (str3.equals("cal_hu_can")) {
            setResult(3, intent);
        } else if (str3.equals("cal_hu_no_can")) {
            setResult(4, intent);
        }else if (str3.equals("circle")){
            setResult(5,intent);
        }else if (str3.equals("circleFriend")){
            setResult(6,intent);
        }
        //发送广播给前一个界面，当前type=0是能看，让前一界面不能看的设置为不可点击
    }


    /**
     * 获取要被添加的成员
     *
     * @return
     */
    private List<String> getToBeAddMembers() {
//        List<String> members = new ArrayList<String>();
        int length = contactAdapter.isCheckedArray.length;
        for (int i = 0; i < length; i++) {
            String username = contactAdapter.getItem(i).getUsername();
            if (contactAdapter.isCheckedArray[i]&&!idList.contains(username)) {
                idList.add(username);
            }
        }

        return idList;
    }


    /**
     * adapter
     */
    private class PickContactAdapter extends EaseContactAdapter {

        private boolean[] isCheckedArray;

        public PickContactAdapter(Context context, int resource, List<EaseUser> users) {
            super(context, resource, users);
            isCheckedArray = new boolean[users.size()];
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
//			if (position > 0) {
            final String username = getItem(position).getUsername();
            // 选择框checkbox
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            ImageView avatarView = (ImageView) view.findViewById(R.id.avatar);
            TextView nameView = (TextView) view.findViewById(R.id.name);
            if (checkBox!=null){
                if (idList!=null&&idList.contains(username)){
                    checkBox.setButtonDrawable(R.drawable.em_checkbox_bg_selector);
                    checkBox.setChecked(true);
                }else {
                    checkBox.setButtonDrawable(R.drawable.em_checkbox_bg_selector);
                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isCheckedArray[position] = isChecked;

                    }
                });
                contactAdapter.notifyDataSetChanged();
            }
            return view;
        }
    }

}
