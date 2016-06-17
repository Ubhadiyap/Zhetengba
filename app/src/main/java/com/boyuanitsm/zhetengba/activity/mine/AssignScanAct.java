package com.boyuanitsm.zhetengba.activity.mine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.hyphenate.easeui.adapter.EaseContactAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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



    @Override
    public void setLayout() {
        setContentView(R.layout.act_who_can);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String str3= bundle.getString("can");
        String str4=bundle.getString("canUserIds");//谁能看
        String str5=bundle.getString("noCanUserIds");//谁不能看
        String canflag=bundle.getString("canFlag");//判断点击进入的标志
        if (!TextUtils.isEmpty(str4)){
            if (canflag.equals("canFlag")||canflag.equals("CalcanFlag")){
                strUserIds = ZtinfoUtils.convertStrToArray(str4);
            }

        }else if (!TextUtils.isEmpty(str5)){
            if (canflag.equals("noCanFlag")||canflag.equals("CalnocanFlag")){
                strUserIds = ZtinfoUtils.convertStrToArray(str5);
            }
        }

        setTopTitle("联系人");
        setRight("发送", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Bundle bundle3=new Bundle();//谁能看
                String userIds;
                if (idList.size()==1){
                    userIds=idList.get(0);
                }else {
                    userIds=idList.get(0);
                    for (int i=1;i<idList.size();i++){
                        userIds=userIds+","+idList.get(i);
                    }
                }
                bundle3.putString("bundleIds",userIds);
                intent.putExtra("bundle3", bundle3);
                if (str3.equals("hu_can")){
                    setResult(1, intent);
                }else if (str3.equals("hu_no_can")){
                    setResult(2,intent);
                }else if (str3.equals("cal_hu_can")){
                    setResult(3,intent);
                }else if (str3.equals("cal_hu_no_can")){
                    setResult(4,intent);
                }
                finish();
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

        contactAdapter = new PickContactAdapter(this, R.layout.em_who_scan_contact_with_checkbox, alluserList,strUserIds);
        listView.setAdapter(contactAdapter);
//        ((EaseSidebar) findViewById(R.id.sidebar)).setListView(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                String strUserId=alluserList.get(position).getUsername();
                checkBox.toggle();
                if (checkBox.isChecked()){
                        idList.add(strUserId);
                }else {
                    idList.remove(strUserId);
                }

            }
        });

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
        private String[] strIds;
        private List<EaseUser> userList;

        public PickContactAdapter(Context context, int resource, List<EaseUser> users, String[] strUserIds) {
            super(context, resource, users);
            isCheckedArray = new boolean[users.size()];
            strIds=strUserIds;
            userList=users;
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
            if (strIds!=null){
                for (int i=0;i<strIds.length;i++){
                    if (strIds[i].equals(userList.get(position).getUsername())){
                        isCheckedArray[position]=true;
                        checkBox.setChecked(isCheckedArray[position]);
                    }
                }
            }


            if (checkBox != null) {
                checkBox.setButtonDrawable(R.drawable.em_checkbox_bg_selector);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
                checkBox.setChecked(isCheckedArray[position]);
            }
            return view;
        }
    }

}
