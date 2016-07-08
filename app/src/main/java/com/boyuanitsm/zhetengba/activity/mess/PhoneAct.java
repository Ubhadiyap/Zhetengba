package com.boyuanitsm.zhetengba.activity.mess;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.AddressBookAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.PhoneInfo;
import com.boyuanitsm.zhetengba.utils.CharacterParserUtils;
import com.boyuanitsm.zhetengba.utils.GetPhoneNumberUtils;
import com.boyuanitsm.zhetengba.utils.PinyinComparator;
import com.boyuanitsm.zhetengba.utils.SideBarUtils;
import com.boyuanitsm.zhetengba.widget.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 手机联系人
 * Created by wangbin on 16/5/17.
 */
public class PhoneAct extends BaseActivity {
    @ViewInject(R.id.lv_addressbook)
    private ListView lv_addressbook;
    @ViewInject(R.id.sidrbar)
    private SideBarUtils sideBar;
    @ViewInject(R.id.dialog)
    private TextView dialog;
    @ViewInject(R.id.cetSearch)
    private ClearEditText cetSearch;

    private CharacterParserUtils characterParser;
    private PinyinComparator pinyinComparator;
    private AddressBookAdapter adapter;
    private List<PhoneInfo> list;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_phone);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("手机联系人");
        characterParser = CharacterParserUtils.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar.setTextView(dialog);
        sideBar.setOnTouchingLetterChangedListener(new SideBarUtils.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lv_addressbook.setSelection(position);
                }

            }
        });
        list = filledData(GetPhoneNumberUtils.getNumber(getApplicationContext()));
        Collections.sort(list, pinyinComparator);
        adapter = new AddressBookAdapter(list, this);
        lv_addressbook.setAdapter(adapter);
        lv_addressbook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone_num = list.get(position).getPhoneNumber();
            }
        });

        cetSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData(s.toString());
//                if (TextUtils.isEmpty(s.toString())) {
//                    list = filledData(GetPhoneNumberUtils.getNumber(getApplicationContext()));
//                    Collections.sort(list, pinyinComparator);
//                    adapter.updateListView(list);
//                } else {
//                    filterData(s.toString());
//                }
            }
        });

    }

    private List<PhoneInfo> filledData(List<PhoneInfo> infoList) {
        List<PhoneInfo> mSortList = new ArrayList<PhoneInfo>();

        for (int i = 0; i < infoList.size(); i++) {
            PhoneInfo sortModel = new PhoneInfo();
            sortModel.setName(infoList.get(i).getName());
            sortModel.setPhoneNumber(infoList.get(i).getPhoneNumber());
            //    sortModel.setImage(infoList.get(i).getImage());


            String pinyin = characterParser.getSelling(infoList.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
    }

    /**
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<PhoneInfo> filterDateList = new ArrayList<PhoneInfo>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = list;
        } else {
            filterDateList.clear();
            for (PhoneInfo sortModel : list) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }


    private void sendMess(String message, String number) {
        Uri smsToUri = Uri.parse("smsto:" + number);
        Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
        mIntent.putExtra("sms_body", message);
        startActivity(mIntent);
    }
}
