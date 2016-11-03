/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.boyuanitsm.zhetengba.activity.mess;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.NewFriendsMsgAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.CircleInfo;
import com.boyuanitsm.zhetengba.chat.db.InviteMessgeDao;
import com.boyuanitsm.zhetengba.chat.domain.InviteMessage;
import com.boyuanitsm.zhetengba.db.ActivityMessDao;
import com.boyuanitsm.zhetengba.fragment.MessFrg;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenu;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuCreator;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuItem;
import com.boyuanitsm.zhetengba.view.swipemenulistview.SwipeMenuListView;
import com.google.android.gms.plus.model.people.Person;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 申请与通知
 *
 */
public class NewFriendsMsgActivity extends BaseActivity {
	@ViewInject(R.id.list)
	private SwipeMenuListView listView;
	private NewFriendsMsgAdapter adapter;

	@Override
	public void setLayout() {
		setContentView(R.layout.em_activity_new_friends_msg);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		setTopTitle("新的好友");
		final InviteMessgeDao dao = new InviteMessgeDao(this);
		final List<InviteMessage> msgs = dao.getMessagesList();
		if (msgs!=null&&msgs.size()>0){
			SortClass sort = new SortClass();
			Collections.sort(msgs, sort);
		}
		//设置adapter
		adapter = new NewFriendsMsgAdapter(this, 1, msgs);
		listView.setAdapter(adapter);
		dao.saveUnreadMessageCount(0);
		sendBroadcast(new Intent(MessFrg.UPDATE_CONTRACT));
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				switch (menu.getViewType()) {
					case 0:
						SwipeMenuItem deleteItem = new SwipeMenuItem(
								getApplicationContext());
						deleteItem.setBackground(R.color.delete_red);
						deleteItem.setWidth(ZhetebaUtils.dip2px(NewFriendsMsgActivity.this, 80));
						deleteItem.setTitle("删除");
						deleteItem.setTitleSize(14);
						deleteItem.setTitleColor(Color.WHITE);
						menu.addMenuItem(deleteItem);
						break;
				}

			}

		};
		listView.setMenuCreator(creator);
		listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
					case 0:
						String from = msgs.get(position).getFrom();
						if (!TextUtils.isEmpty(from)) {
							adapter.remove(msgs.get(position));
							dao.deleteMessage(from);
							//设置adapter

						}
						break;
				}
				// false : close the menu; true : not close the menu
				return false;
			}
		});
		listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
	}
	/**
	 * 时间降序
	 * 排序
	 */
	public class SortClass implements Comparator {
		public int compare(Object arg0, Object arg1) {
			InviteMessage user0 = (InviteMessage) arg0;
			InviteMessage user1 = (InviteMessage) arg1;
			int flag = (user1.getTime()+"").compareTo(user0.getTime()+"");//升序直接将user0,user1互换
			return flag;
		}
	}

	
}
