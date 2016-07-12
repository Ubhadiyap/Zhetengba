package com.boyuanitsm.zhetengba.activity.mess;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.Constant;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.widget.ClearEditText;
import com.boyuanitsm.zhetengba.widget.DialogChoseDate;
import com.hyphenate.easeui.adapter.EaseContactAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.CircleImageView;
import com.hyphenate.easeui.widget.EaseSidebar;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 建立群聊
 * Created by wangbin on 16/5/19.
 */
public class CreateGroupAct extends BaseActivity {
    private ProgressDialog progressDialog;
    @ViewInject(R.id.list)
    private ListView listView;
    @ViewInject(R.id.tvQun)
    private TextView tvQun;
    @ViewInject(R.id.cetSearch)
    private ClearEditText cetSearch;//搜索框
    private PickContactAdapter contactAdapter;
    private boolean isSignleChecked = false;

    private ArrayList<String> dayList=new ArrayList<>();


    @Override
    public void setLayout() {
        setContentView(R.layout.act_create_group);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("邀请好友");
        final String st1 = getResources().getString(R.string.Is_to_create_a_group_chat);
        final String st2 = getResources().getString(R.string.Failed_to_create_groups);

        for(int i=1;i<=30;i++){
            dayList.add(i+"天");
        }
        setRight("发送", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(CreateGroupAct.this);
                progressDialog.setMessage(st1);
                progressDialog.setCanceledOnTouchOutside(false);


                if(TextUtils.isEmpty(tvQun.getText().toString().trim())){
                    MyToastUtils.showShortToast(getApplicationContext(),"请选择群时限");
                    return;
                }
                List<String> members = getToBeAddMembers();
                if(members==null||members.size()==0){
                    MyToastUtils.showShortToast(getApplicationContext(),"请至少选择一个好友");
                    return;
                }
                createGroup(tvQun.getText().toString().trim().substring(0,1),getPersonIds(members));

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 调用sdk创建群组方法
//                        final String groupName = "测试";//设置群名称
//                        String desc = "";//群简介
//                        String[] members = getToBeAddMembers().toArray(new String[0]);
//                        try {
//                            EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
//                            option.maxUsers = 200;//设置群聊最大人数
//
//                            String reason = CreateGroupAct.this.getString(R.string.invite_join_group);
//                            reason = EMClient.getInstance().getCurrentUser() + reason + groupName;
//
//                            option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval;
////                            if(publibCheckBox.isChecked()){
////                                option.style = memberCheckbox.isChecked() ? EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval : EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
////                            }else{
////                                option.style = memberCheckbox.isChecked()? EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite: EMGroupManager.EMGroupStyle.EMGroupStylePrivateOnlyOwnerInvite;
////                            }
//                            EMClient.getInstance().groupManager().createGroup(groupName, desc, members, reason, option);
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    progressDialog.dismiss();
//                                    setResult(RESULT_OK);
//                                    finish();
//                                }
//                            });
//                        } catch (final HyphenateException e) {
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    progressDialog.dismiss();
//                                    Toast.makeText(CreateGroupAct.this, st2 + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                    }
//                }).start();
            }
        });

        // 获取好友列表
        final List<EaseUser> alluserList = new ArrayList<EaseUser>();
        for (EaseUser user : DemoHelper.getInstance().getContactList().values()) {
            if (!user.getUsername().equals(Constant.NEW_FRIENDS_USERNAME) & !user.getUsername().equals(Constant.GROUP_USERNAME) & !user.getUsername().equals(Constant.CHAT_ROOM) & !user.getUsername().equals(Constant.CHAT_ROBOT))
                alluserList.add(user);
        }
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

        contactAdapter = new PickContactAdapter(this, R.layout.em_row_contact_with_checkbox, alluserList);
        listView.setAdapter(contactAdapter);
        ((EaseSidebar) findViewById(R.id.sidebar)).setListView(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                checkBox.toggle();

            }
        });
    }


    @OnClick({R.id.tvQun})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tvQun:
                DialogChoseDate dialogChooseMonth = new DialogChoseDate(CreateGroupAct.this, dayList).builder(0);
                dialogChooseMonth.show();
                dialogChooseMonth.setOnSheetItemClickListener(new DialogChoseDate.SexClickListener() {
                    @Override
                    public void getAdress(String adress) {
                        tvQun.setText(adress);

                    }
                });
                dialogChooseMonth.show();
                break;
        }
    }

    /**
     * 创建群聊
     * @param timeLength
     * @param personIds
     */
    private void createGroup(String timeLength,String personIds){
        progressDialog.show();
        RequestManager.getMessManager().createGroup( timeLength, personIds, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                progressDialog.dismiss();
                MyToastUtils.showShortToast(getApplicationContext(),errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                progressDialog.dismiss();
                MyToastUtils.showShortToast(getApplicationContext(),response.getMessage());
                finish();

            }
        });
    }

    private String getPersonIds(List<String> members){
        String personsId="";
        if(members!=null&&members.size()>0){
            for(String member:members){
                personsId=personsId+member+",";
            }
            return personsId.substring(0,personsId.length()-1);
        }
        return personsId;
    }

    /**
     * 获取要被添加的成员
     *
     * @return
     */
    private List<String> getToBeAddMembers() {
        List<String> members = new ArrayList<String>();
        int length = contactAdapter.isCheckedArray.length;
        for (int i = 0; i < length; i++) {
            String username = contactAdapter.getItem(i).getUsername();
            if (contactAdapter.isCheckedArray[i]) {
                members.add(username);
            }
        }

        return members;
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
            CircleImageView avatarView = (CircleImageView) view.findViewById(R.id.avatar);
            TextView nameView = (TextView) view.findViewById(R.id.name);

            if (checkBox != null) {
//                if(exitingMembers != null && exitingMembers.contains(username)){
//                    checkBox.setButtonDrawable(R.drawable.em_checkbox_bg_gray_selector);
//                }else{
                checkBox.setButtonDrawable(R.drawable.em_checkbox_bg_selector);
//                }
                // checkBox.setOnCheckedChangeListener(null);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // 群组中原来的成员一直设为选中状态
//                        if (exitingMembers.contains(username)) {
//                            isChecked = true;
//                            checkBox.setChecked(true);
//                        }
                        isCheckedArray[position] = isChecked;
                        //如果是单选模式
                        if (isSignleChecked && isChecked) {
                            for (int i = 0; i < isCheckedArray.length; i++) {
                                if (i != position) {
                                    isCheckedArray[i] = false;
                                }
                            }
                            contactAdapter.notifyDataSetChanged();
                        }

                    }
                });
                // 群组中原来的成员一直设为选中状态
//                if (exitingMembers.contains(username)) {
//                    checkBox.setChecked(true);
//                    isCheckedArray[position] = true;
//                } else {
                checkBox.setChecked(isCheckedArray[position]);
//                }
            }
//			}
            return view;
        }
    }

}
