package com.boyuanitsm.zhetengba.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.PhoneInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.hyphenate.chat.EMClient;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by gxy on 2015/11/24.
 */
public class AddressBookAdapter extends BaseAdapter implements SectionIndexer{
    private LayoutInflater inflater=null;
    private List<PhoneInfo> list=null;
    BitmapUtils bitmapUtils=null;

    private Context context;
    private ProgressDialog progressDialog;
    public AddressBookAdapter(Context context){
        inflater=LayoutInflater.from(context);
    }

    public AddressBookAdapter(List<PhoneInfo> list, Context context){
        this.list=list;
        this.context=context;
        inflater=LayoutInflater.from(context);
        bitmapUtils=new BitmapUtils(context);
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     * @param list
     */
    public void updateListView(List<PhoneInfo> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        final PhoneInfo mContent = list.get(position);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.addressbook_item,null);
            holder=new ViewHolder();
            holder.iv_head= (ImageView) convertView.findViewById(R.id.iv_head);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_phonenumber= (TextView) convertView.findViewById(R.id.tv_phonenumber);
            holder.btn_invate= (Button) convertView.findViewById(R.id.btn_invate);
            holder.tv_aregister= (TextView) convertView.findViewById(R.id.tv_aregister);
            holder.tv_zm= (TextView) convertView.findViewById(R.id.tv_zm);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        if(list.size()!=0){
            //根据position获取分类的首字母的char ascii值
            int section = getSectionForPosition(position);
            //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if(position == getPositionForSection(section)){
                holder.tv_zm.setVisibility(View.VISIBLE);
                holder.tv_zm.setText(mContent.getSortLetters());
            }else{
                holder.tv_zm.setVisibility(View.GONE);
            }
            holder.tv_name.setText(list.get(position).getName().toString());
            holder.tv_phonenumber.setText(list.get(position).getPhoneNumber().toString());
         /*   if(list.get(position).getImage()==null){
                holder.tv_name.setText(list.get(position).getName().toString());
                holder.tv_phonenumber.setText(list.get(position).getPhoneNumber().toString());
            }else {
             //   holder.iv_head.setImageBitmap(list.get(position).getImage());
                holder.tv_name.setText(list.get(position).getName().toString());
                holder.tv_phonenumber.setText(list.get(position).getPhoneNumber().toString());
            }*/

            holder.btn_invate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findUserByPhone(list.get(position).getPhoneNumber().toString());
                }
            });

        }

        return convertView;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    private String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    static class ViewHolder{
        public ImageView iv_head;
        public TextView tv_name;
        public TextView tv_phonenumber;
        public Button btn_invate;
        public TextView tv_aregister;
        public TextView tv_zm;
    }

    /**
     * 查找用户
     * @param phone
     */
    private void findUserByPhone(String phone){
        RequestManager.getMessManager().findUserByPhone(phone, new ResultCallback<ResultBean<UserInfo>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(context,errorMsg);
            }

            @Override
            public void onResponse(ResultBean<UserInfo> response) {
                UserInfo userInfo=response.getData();
                if(userInfo!=null){
                  addContact(userInfo.gethUsername(),null);
                }else{
                   MyToastUtils.showShortToast(context,"此好友还没有注册，赶紧去邀请他注册吧");
                }
            }
        });
    }

    /**
     *  添加contact
     * @param
     */
    public void addContact(final String hUserName,final String reason){
        if(EMClient.getInstance().getCurrentUser().equals(hUserName)){
            new MyAlertDialog(context).builder().setTitle("提示").setMsg(context.getResources().
                    getString(R.string.not_add_myself)).setNegativeButton("确定", null).show();
            return;
        }

        if(DemoHelper.getInstance().getContactList().containsKey(hUserName)){
            //提示已在好友列表中(在黑名单列表里)，无需添加
            if(EMClient.getInstance().contactManager().getBlackListUsernames().contains(hUserName)){
                new MyAlertDialog(context).builder().setTitle("提示").setMsg(context.getResources().
                        getString(R.string.user_already_in_contactlist)).setNegativeButton("确定", null).show();
                return;
            }
            new MyAlertDialog(context).builder().setTitle("提示").setMsg(context.getResources().
                    getString(R.string.This_user_is_already_your_friend)).setNegativeButton("确定", null).show();
            return;
        }

        progressDialog = new ProgressDialog(context);
        String stri = context.getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {
                try {
                    //demo写死了个reason，实际应该让用户手动填入
                    String s="";
                    if(TextUtils.isEmpty(reason)){
                        s = context.getResources().getString(R.string.Add_a_friend);
                    }else{
                        s=reason;
                    }
                    EMClient.getInstance().contactManager().addContact(hUserName, s);
                    ((Activity)context).runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = context.getResources().getString(R.string.send_successful);
                            Toast.makeText(context, s1, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final Exception e) {
                    ((Activity)context). runOnUiThread(new Runnable() {
                       public void run() {
                           progressDialog.dismiss();
                           String s2 = context.getResources().getString(R.string.Request_add_buddy_failure);
                           Toast.makeText(context, s2 + e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
                }
            }
        }).start();
    }
}
