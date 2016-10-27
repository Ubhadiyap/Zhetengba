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
package com.boyuanitsm.zhetengba.chat.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.PersonalAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ChatUserBean;
import com.boyuanitsm.zhetengba.bean.GroupBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.chat.frg.ChatFragment;
import com.boyuanitsm.zhetengba.db.ChatUserDao;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.fragment.MessFrg;
import com.boyuanitsm.zhetengba.fragment.calendarFrg.SimpleFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.MySelfSheetDialog;
import com.boyuanitsm.zhetengba.view.SafeDialog;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.EaseAlertDialog.AlertDialogUser;
import com.hyphenate.easeui.widget.EaseExpandGridView;
import com.hyphenate.easeui.widget.EaseSwitchButton;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

public class GroupDetailsActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "GroupDetailsActivity";
	private static final int REQUEST_CODE_ADD_USER = 0;
	private static final int REQUEST_CODE_EXIT = 1;
	private static final int REQUEST_CODE_EXIT_DELETE = 2;
	private static final int REQUEST_CODE_EDIT_GROUPNAME = 5;


	private EaseExpandGridView userGridview;
	private TextView tvTitle;
	private String groupId;
	private ProgressBar loadingPB;
	private Button exitBtn;
	private Button deleteBtn;
	private EMGroup group;
	private GridAdapter adapter;
	private SafeDialog progressDialog;
	private TextView tvTime;

	private RelativeLayout rl_switch_block_groupmsg;

	public static GroupDetailsActivity instance;

	String st = "";
	// 清空所有聊天记录
	private RelativeLayout clearAllHistory;
	private RelativeLayout blacklistLayout;
	private RelativeLayout changeGroupNameLayout;
    private RelativeLayout idLayout;
    private TextView idText;
	private EaseSwitchButton switchButton;
    private GroupChangeListener groupChangeListener;
    private RelativeLayout searchLayout;

	private boolean type;//true 为自建群, false 为活动群

	@Override
	public void setLayout() {
		// 获取传过来的groupid
		groupId = getIntent().getStringExtra("groupId");
		group = EMClient.getInstance().groupManager().getGroup(groupId);
		// we are not supposed to show the group if we don't find the group
		if(group == null){
			finish();
			return;
		}

		setContentView(R.layout.em_activity_group_details);
	}

	@Override
	public void init(Bundle savedInstanceState) {

		instance = this;
		st = getResources().getString(R.string.people);
		tvTime= (TextView) findViewById(R.id.tvTime);
		clearAllHistory = (RelativeLayout) findViewById(R.id.clear_all_history);
		tvTitle= (TextView) findViewById(R.id.tvTitle);
		userGridview = (EaseExpandGridView) findViewById(R.id.gridview);
		loadingPB = (ProgressBar) findViewById(R.id.progressBar);
		exitBtn = (Button) findViewById(R.id.btn_exit_grp);
		deleteBtn = (Button) findViewById(R.id.btn_exitdel_grp);
		blacklistLayout = (RelativeLayout) findViewById(R.id.rl_blacklist);
		changeGroupNameLayout = (RelativeLayout) findViewById(R.id.rl_change_group_name);
		idLayout = (RelativeLayout) findViewById(R.id.rl_group_id);
//		idLayout.setVisibility(View.VISIBLE);
		idText = (TextView) findViewById(R.id.tv_group_id_value);

		rl_switch_block_groupmsg = (RelativeLayout) findViewById(R.id.rl_switch_block_groupmsg);
		switchButton = (EaseSwitchButton) findViewById(R.id.switch_btn);
		searchLayout = (RelativeLayout) findViewById(R.id.rl_search);

		tvTitle.setText("对话管理");
		idText.setText(groupId);
		if (group.getOwner() == null || "".equals(group.getOwner())
				|| !group.getOwner().equals(EMClient.getInstance().getCurrentUser())) {
			exitBtn.setVisibility(View.GONE);
			deleteBtn.setVisibility(View.GONE);
			blacklistLayout.setVisibility(View.GONE);
			changeGroupNameLayout.setVisibility(View.GONE);
		}
		// 如果自己是群主，显示解散按钮
		if (EMClient.getInstance().getCurrentUser().equals(group.getOwner())) {
			exitBtn.setVisibility(View.GONE);
			deleteBtn.setVisibility(View.VISIBLE);
			rl_switch_block_groupmsg.setVisibility(View.GONE);
		}

		groupChangeListener = new GroupChangeListener();
		EMClient.getInstance().groupManager().addGroupChangeListener(groupChangeListener);

		((TextView) findViewById(R.id.group_name)).setText(group.getGroupName());
//		+ "(" + group.getAffiliationsCount() + st
		//获取群成员列表
		findGroupInfo();//查找群信息

		// 设置OnTouchListener
		userGridview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if (adapter.isInDeleteMode) {
							adapter.isInDeleteMode = false;
							adapter.notifyDataSetChanged();
							return true;
						}
						break;
					default:
						break;
				}
				return false;
			}
		});

		clearAllHistory.setOnClickListener(this);
		blacklistLayout.setOnClickListener(this);

		rl_switch_block_groupmsg.setOnClickListener(this);
		searchLayout.setOnClickListener(this);
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String st1 = getResources().getString(R.string.being_added);
		String st2 = getResources().getString(R.string.is_quit_the_group_chat);
		String st3 = getResources().getString(R.string.chatting_is_dissolution);
		String st4 = getResources().getString(R.string.are_empty_group_of_news);
		String st5 = getResources().getString(R.string.is_modify_the_group_name);
		final String st6 = getResources().getString(R.string.Modify_the_group_name_successful);
		final String st7 = getResources().getString(R.string.change_the_group_name_failed_please);

		if (resultCode == RESULT_OK) {
			if (progressDialog == null) {
				progressDialog = new SafeDialog(GroupDetailsActivity.this);
				progressDialog.setMessage(st1);
				progressDialog.setCanceledOnTouchOutside(false);
			}
			switch (requestCode) {
			case REQUEST_CODE_ADD_USER:// 添加群成员
				final String[] newmembers = data.getStringArrayExtra("newmembers");
				progressDialog.setMessage(st1);
				progressDialog.show();
				addMembersToGroup(newmembers);
				break;
			case REQUEST_CODE_EXIT: // 退出群
				progressDialog.setMessage(st2);
				progressDialog.show();
				exitGrop();
				break;
			case REQUEST_CODE_EXIT_DELETE: // 解散群
				progressDialog.setMessage(st3);
				progressDialog.show();
				deleteGrop();
				break;

			case REQUEST_CODE_EDIT_GROUPNAME: //修改群名称
				final String returnData = data.getStringExtra("data");
				if(!TextUtils.isEmpty(returnData)){
					progressDialog.setMessage(st5);
					progressDialog.show();
					RequestManager.getMessManager().updateGName(groupId, returnData, new ResultCallback<ResultBean<String>>() {
						@Override
						public void onError(int status, String errorMsg) {
							progressDialog.dismiss();
							Toast.makeText(getApplicationContext(), st7, Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onResponse(ResultBean<String> response) {
							((TextView) findViewById(R.id.group_name)).setText(returnData);
							progressDialog.dismiss();
							updateGroup();
							Toast.makeText(getApplicationContext(), st6, Toast.LENGTH_SHORT).show();
							Intent intent=new Intent(ChatFragment.UPDATE_GROUP_NAME);
							intent.putExtra("chat",2);
							intent.putExtra("groupName",returnData);
                            sendBroadcast(intent);

//							new Thread(new Runnable() {
//								@Override
//								public void run() {
//									try {
//										EMClient.getInstance().groupManager().changeGroupName(groupId, returnData);
//									} catch (HyphenateException e) {
//										e.printStackTrace();
//									}
//								}
//							}).start();


						}
					});



//					new Thread(new Runnable() {
//						public void run() {
//							try {
//								EMClient.getInstance().groupManager().changeGroupName(groupId, returnData);
//								runOnUiThread(new Runnable() {
//									public void run() {
//										((TextView) findViewById(R.id.group_name)).setText(returnData );
//										progressDialog.dismiss();
//										Toast.makeText(getApplicationContext(), st6, Toast.LENGTH_SHORT).show();
//									}
//								});
//
//							} catch (HyphenateException e) {
//								e.printStackTrace();
//								runOnUiThread(new Runnable() {
//									public void run() {
//										progressDialog.dismiss();
//										Toast.makeText(getApplicationContext(), st7, Toast.LENGTH_SHORT).show();
//									}
//								});
//							}
//						}
//					}).start();
				}
				break;
			default:
				break;
			}
		}
	}

    protected void addUserToBlackList(final String username) {
        final SafeDialog pd = new SafeDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(getString(R.string.Are_moving_to_blacklist));
        pd.show();
        new Thread(new Runnable() {
        	public void run() {
        		try {
        			EMClient.getInstance().groupManager().blockUser(groupId, username);
        			runOnUiThread(new Runnable() {
        				public void run() {
        				    refreshMembers();
        				    pd.dismiss();
        					Toast.makeText(getApplicationContext(), R.string.Move_into_blacklist_success, Toast.LENGTH_SHORT).show();
        				}
        			});
        		} catch (HyphenateException e) {
        			runOnUiThread(new Runnable() {
        				public void run() {
        				    pd.dismiss();
        					Toast.makeText(getApplicationContext(), R.string.failed_to_move_into, Toast.LENGTH_SHORT).show();
        				}
        			});
        		}
        	}
        }).start();
    }

	private void refreshMembers(){
	    adapter.clear();

		List<String> members = new ArrayList<String>();
        members.addAll(group.getMembers());
        adapter.addAll(members);

        adapter.notifyDataSetChanged();
	}

	/**
	 * 点击退出群组按钮
	 *
	 * @param view
	 */
	public void exitGroup(View view) {
		final MySelfSheetDialog dialog=new MySelfSheetDialog(this);
		dialog.builder().setTitle("退出后，将不再接收此群聊信息").addSheetItem("退出", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
			@Override
			public void onClick(int which) {
				if (progressDialog == null) {
					progressDialog = new SafeDialog(GroupDetailsActivity.this);
					progressDialog.setCanceledOnTouchOutside(false);
				}
				progressDialog.setMessage("正在退出群聊");
				progressDialog.show();
				exitGrop();

			}
		}).show();
//		startActivityForResult(new Intent(this, ExitGroupDialog.class), REQUEST_CODE_EXIT);

	}

	/**
	 * 点击解散群组按钮
	 *
	 * @param view
	 */
	public void exitDeleteGroup(View view) {
		final MySelfSheetDialog dialog=new MySelfSheetDialog(this);
		dialog.builder().setTitle("退出后，此群将被解散").addSheetItem("退出", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
			@Override
			public void onClick(int which) {
				if (progressDialog == null) {
					progressDialog = new SafeDialog(GroupDetailsActivity.this);
					progressDialog.setCanceledOnTouchOutside(false);
				}
				progressDialog.setMessage("正在解散群聊...");
				progressDialog.show();
				removeGroupPerson(groupId, EMClient.getInstance().getCurrentUser());
//				deleteGrop();

			}
		}).show();
//		startActivityForResult(new Intent(this, ExitGroupDialog.class).putExtra("deleteToast", getString(R.string.dissolution_group_hint)),
//				REQUEST_CODE_EXIT_DELETE);

	}

	/**
	 * 清空群聊天记录
	 */
	private void clearGroupHistory() {

		EMConversation conversation = EMClient.getInstance().chatManager().getConversation(group.getGroupId(), EMConversationType.GroupChat);
		if (conversation != null) {
			conversation.clearAllMessages();
		}
		Toast.makeText(this, R.string.messages_are_empty, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 退出群组
	 *
	 * @param
	 */
	private void exitGrop() {
//		String st1 = getResources().getString(R.string.Exit_the_group_chat_failure);
		RequestManager.getMessManager().exitGroup(groupId, new ResultCallback<ResultBean<String>>() {
			@Override
			public void onError(int status, String errorMsg) {
				MyToastUtils.showShortToast(getApplicationContext(),errorMsg);
			}

			@Override
			public void onResponse(ResultBean<String> response) {
				sendBroadcast(new Intent(SimpleFrg.DATA_CHANGE_KEY));
				sendBroadcast(new Intent(MessFrg.UPDATE_CONTRACT));
				sendBroadcast(new Intent(MyGroupAct.MYGROUP));
				progressDialog.dismiss();
				setResult(RESULT_OK);
				finish();
				if(ChatActivity.activityInstance != null)
					ChatActivity.activityInstance.finish();
			}
		});
//		new Thread(new Runnable() {
//			public void run() {
//				try {
//					EMClient.getInstance().groupManager().leaveGroup(groupId);
//					runOnUiThread(new Runnable() {
//						public void run() {
//							progressDialog.dismiss();
//							setResult(RESULT_OK);
//							finish();
//							if(ChatActivity.activityInstance != null)
//							    ChatActivity.activityInstance.finish();
//						}
//					});
//				} catch (final Exception e) {
//					runOnUiThread(new Runnable() {
//						public void run() {
//							progressDialog.dismiss();
//							Toast.makeText(getApplicationContext(), getResources().getString(R.string.Exit_the_group_chat_failure) + " " + e.getMessage(),Toast.LENGTH_SHORT).show();
//						}
//					});
//				}
//			}
//		}).start();
	}

	/**
	 * 解散群组
	 *
	 * @param
	 */
	private void deleteGrop() {
		final String st5 = getResources().getString(R.string.Dissolve_group_chat_tofail);
		RequestManager.getMessManager().removeGroupPerson(groupId, UserInfoDao.getUser().getId(), new ResultCallback() {
			@Override
			public void onError(int status, String errorMsg) {
				MyToastUtils.showShortToast(GroupDetailsActivity.this,st5);
			}

			@Override
			public void onResponse(Object response) {
				sendBroadcast(new Intent(SimpleFrg.DATA_CHANGE_KEY));
				sendBroadcast(new Intent(MessFrg.UPDATE_CONTRACT));
				sendBroadcast(new Intent(MyGroupAct.MYGROUP));
				progressDialog.dismiss();
				setResult(RESULT_OK);
				finish();
				if(ChatActivity.activityInstance != null)
					ChatActivity.activityInstance.finish();
			}
		});
//		new Thread(new Runnable() {
//			public void run() {
//				try {
//					EMClient.getInstance().groupManager().destroyGroup(groupId);
//					runOnUiThread(new Runnable() {
//						public void run() {
//							sendBroadcast(new Intent(SimpleFrg.DATA_CHANGE_KEY));
//							sendBroadcast(new Intent(MessFrg.UPDATE_CONTRACT));
//							sendBroadcast(new Intent(MyGroupAct.MYGROUP));
//							progressDialog.dismiss();
//							setResult(RESULT_OK);
//							finish();
//							if(ChatActivity.activityInstance != null)
//							    ChatActivity.activityInstance.finish();
//						}
//					});
//				} catch (final Exception e) {
//					runOnUiThread(new Runnable() {
//						public void run() {
//							progressDialog.dismiss();
//							Toast.makeText(getApplicationContext(), st5 + e.getMessage(), Toast.LENGTH_SHORT).show();
//						}
//					});
//				}
//			}
//		}).start();
	}

	/**
	 * 增加群成员
	 *
	 * @param newmembers
	 */
	private void addMembersToGroup(final String[] newmembers) {
		final String st6 = getResources().getString(R.string.Add_group_members_fail);
		RequestManager.getMessManager().addGroupMember(groupId, getPersonIds(newmembers), new ResultCallback<ResultBean<String>>() {
			@Override
			public void onError(int status, String errorMsg) {
				runOnUiThread(new Runnable() {
					public void run() {
						progressDialog.dismiss();
						Toast.makeText(getApplicationContext(), st6 , Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onResponse(ResultBean<String> response) {
				progressDialog.dismiss();
				updateGroup();
				refreshMembers();


//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						runOnUiThread(new Runnable() {
//							public void run(){
//								refreshMembers();
////						((TextView) findViewById(R.id.group_name)).setText(group.getGroupName());
//								progressDialog.dismiss();
//							}
//						});
//					}
//				}).start();

			}
		});


//		new Thread(new Runnable() {
//
//			public void run() {
//				try {
//					// 创建者调用add方法
//					if (EMClient.getInstance().getCurrentUser().equals(group.getOwner())) {
//						EMClient.getInstance().groupManager().addUsersToGroup(groupId, newmembers);
//					} else {
//						// 一般成员调用invite方法
//						EMClient.getInstance().groupManager().inviteUser(groupId, newmembers, null);
//					}
//					runOnUiThread(new Runnable() {
//						public void run() {
//						    refreshMembers();
//							((TextView) findViewById(R.id.group_name)).setText(group.getGroupName());
//							progressDialog.dismiss();
//						}
//					});
//				} catch (final Exception e) {
//					runOnUiThread(new Runnable() {
//						public void run() {
//							progressDialog.dismiss();
//							Toast.makeText(getApplicationContext(), st6 + e.getMessage(), Toast.LENGTH_SHORT).show();
//						}
//					});
//				}
//			}
//		}).start();
	}

	private String getPersonIds(String[] members){
		String personsId="";
		if(members!=null&&members.length>0){
			for(String member:members){
				personsId=personsId+member+",";;
			}
			return personsId.substring(0,personsId.length()-1);
		}
		return personsId;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_switch_block_groupmsg: // 屏蔽或取消屏蔽群组
			toggleBlockGroup();
			break;

		case R.id.clear_all_history: // 清空聊天记录
			String st9 = getResources().getString(R.string.sure_to_empty_this);
			new EaseAlertDialog(GroupDetailsActivity.this, null, st9, null, new AlertDialogUser() {

                @Override
                public void onResult(boolean confirmed, Bundle bundle) {
                    if(confirmed){
                        clearGroupHistory();
                    }
                }
            }, true).show();

			break;

//		case R.id.rl_blacklist: // 黑名单列表
//			startActivity(new Intent(GroupDetailsActivity.this, GroupBlacklistActivity.class).putExtra("groupId", groupId));
//			break;
            /*修改群名称*/
		case R.id.rl_change_group_name:
			startActivityForResult(new Intent(this, EditActivity.class).putExtra("data", group.getGroupName()), REQUEST_CODE_EDIT_GROUPNAME);
			break;
//		case R.id.rl_search:
//            startActivity(new Intent(this, GroupSearchMessageActivity.class).putExtra("groupId", groupId));
//
//            break;
		default:
			break;
		}

	}

	private void toggleBlockGroup() {
		if(!switchButton.isSwitchOpen()){
			EMLog.d(TAG, "change to unblock group msg");
			if (progressDialog == null) {
		        progressDialog = new SafeDialog(GroupDetailsActivity.this);
		        progressDialog.setCanceledOnTouchOutside(false);
		    }
			progressDialog.setMessage(getString(R.string.Is_unblock));
			progressDialog.show();
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                EMClient.getInstance().groupManager().unblockGroupMessage(groupId);
		                runOnUiThread(new Runnable() {
		                    public void run() {
		                    	switchButton.openSwitch();
		                        progressDialog.dismiss();
		                    }
		                });
		            } catch (Exception e) {
		                e.printStackTrace();
		                runOnUiThread(new Runnable() {
		                    public void run() {
		                        progressDialog.dismiss();
		                        Toast.makeText(getApplicationContext(), R.string.remove_group_of,Toast.LENGTH_SHORT).show();
		                    }
		                });

		            }
		        }
		    }).start();

		} else {
			String st8 = getResources().getString(R.string.group_is_blocked);
			final String st9 = getResources().getString(R.string.group_of_shielding);
			EMLog.d(TAG, "change to block group msg");
			if (progressDialog == null) {
		        progressDialog = new SafeDialog(GroupDetailsActivity.this);
		        progressDialog.setCanceledOnTouchOutside(false);
		    }
			progressDialog.setMessage(st8);
			progressDialog.show();
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                EMClient.getInstance().groupManager().blockGroupMessage(groupId);
		                runOnUiThread(new Runnable() {
		                    public void run() {
		                    	switchButton.closeSwitch();
		                        progressDialog.dismiss();
		                    }
		                });
		            } catch (Exception e) {
		                e.printStackTrace();
		                runOnUiThread(new Runnable() {
		                    public void run() {
		                        progressDialog.dismiss();
		                        Toast.makeText(getApplicationContext(), st9, Toast.LENGTH_SHORT).show();
		                    }
		                });
		            }

		        }
		    }).start();
		}
	}

	/**
	 * 群组成员gridadapter
	 *
	 * @author admin_new
	 *
	 */
	private class GridAdapter extends ArrayAdapter<String> {

		private int res;
		public boolean isInDeleteMode;
		private List<String> objects;

		public GridAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			this.objects = objects;
			res = textViewResourceId;
			isInDeleteMode = false;
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
		    ViewHolder holder = null;
			if (convertView == null) {
			    holder = new ViewHolder();
				convertView = LayoutInflater.from(getContext()).inflate(res, null);
				holder.imageView = (CircleImageView) convertView.findViewById(R.id.iv_avatar);
				holder.textView = (TextView) convertView.findViewById(R.id.tv_name);
				holder.badgeDeleteView = (ImageView) convertView.findViewById(R.id.badge_delete);
				convertView.setTag(holder);
			}else{
			    holder = (ViewHolder) convertView.getTag();
			}
			final LinearLayout button = (LinearLayout) convertView.findViewById(R.id.button_avatar);
			// 最后一个item，减人按钮
			if (position == getCount() - 1) {
			    holder.textView.setText("");
				// 设置成删除按钮
			    holder.imageView.setImageResource(R.mipmap.group_jian);
//				button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.smiley_minus_btn, 0, 0);
				// 如果不是创建者或者没有相应权限，不提供加减人按钮
				if (!group.getOwner().equals(EMClient.getInstance().getCurrentUser())) {
					// if current user is not group admin, hide add/remove btn
					convertView.setVisibility(View.INVISIBLE);
				} else { // 显示删除按钮
					if (isInDeleteMode) {
						// 正处于删除模式下，隐藏删除按钮
						convertView.setVisibility(View.INVISIBLE);
					} else {
						// 正常模式
						convertView.setVisibility(View.VISIBLE);
						convertView.findViewById(R.id.badge_delete).setVisibility(View.INVISIBLE);
					}
					final String st10 = getResources().getString(R.string.The_delete_button_is_clicked);
					button.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							EMLog.d(TAG, st10);
							isInDeleteMode = true;
							notifyDataSetChanged();
						}
					});
				}
			} else if (position == getCount() - 2) { // 添加群组成员按钮
			    holder.textView.setText("");
			    holder.imageView.setImageResource(R.mipmap.group_add);
//				button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.smiley_add_btn, 0, 0);
				// 如果不是创建者或者没有相应权限
				if (!group.isAllowInvites() && !group.getOwner().equals(EMClient.getInstance().getCurrentUser())) {
					// if current user is not group admin, hide add/remove btn
					convertView.setVisibility(View.INVISIBLE);
				} else {
					// 正处于删除模式下,隐藏添加按钮
					if (isInDeleteMode) {
						convertView.setVisibility(View.INVISIBLE);
					} else {
						convertView.setVisibility(View.VISIBLE);
						convertView.findViewById(R.id.badge_delete).setVisibility(View.INVISIBLE);
					}
					final String st11 = getResources().getString(R.string.Add_a_button_was_clicked);
					button.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							EMLog.d(TAG, st11);
							if(type==false){
								MyToastUtils.showShortToast(getApplicationContext(),"此群为活动群，不能添加成员");
								return;
							}
							// 进入选人页面
							startActivityForResult(
									(new Intent(GroupDetailsActivity.this, GroupPickContactsActivity.class).putExtra("groupId", groupId)),
									REQUEST_CODE_ADD_USER);
						}
					});
				}
			} else { // 普通item，显示群组成员
				final String username = getItem(position);
				convertView.setVisibility(View.VISIBLE);
				button.setVisibility(View.VISIBLE);
//				Drawable avatar = getResources().getDrawable(R.drawable.default_avatar);
//				avatar.setBounds(0, 0, referenceWidth, referenceHeight);
//				button.setCompoundDrawables(null, avatar, null, null);
				EaseUserUtils.setUserNick(username, holder.textView);
				EaseUserUtils.setUserAvatar(getContext(), username, holder.imageView);
				if (isInDeleteMode) {
					// 如果是删除模式下，显示减人图标
					convertView.findViewById(R.id.badge_delete).setVisibility(View.VISIBLE);
				} else {
					convertView.findViewById(R.id.badge_delete).setVisibility(View.INVISIBLE);
				}
				final String st12 = getResources().getString(R.string.not_delete_myself);
				final String st13 = getResources().getString(R.string.Are_removed);
				final String st14 = getResources().getString(R.string.Delete_failed);
				final String st15 = getResources().getString(R.string.confirm_the_members);
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (isInDeleteMode) {
							// 如果是删除自己，return
							if (EMClient.getInstance().getCurrentUser().equals(username)) {
							    new EaseAlertDialog(GroupDetailsActivity.this, st12).show();
								return;
							}
							if (!NetUtils.hasNetwork(getApplicationContext())) {
								Toast.makeText(getApplicationContext(), getString(R.string.network_unavailable),Toast.LENGTH_SHORT).show();
								return;
							}
							EMLog.d("group", "remove user from group:" + username);
							deleteMembersFromGroup(username);
						} else {
							// 正常情况下点击user，可以进入用户详情或者聊天页面等等
							// startActivity(new
							// Intent(GroupDetailsActivity.this,
							// ChatActivity.class).putExtra("userId",
							// user.getUsername()));
							if(!EMClient.getInstance().getCurrentUser().equals(username)) {
								Intent intent = new Intent(GroupDetailsActivity.this, PersonalAct.class);
								intent.putExtra("userId", username);
								intent.putExtra("chat_type",2);
								if (!TextUtils.isEmpty(((TextView) findViewById(R.id.group_name)).getText())){
									intent.putExtra("groupName",((TextView) findViewById(R.id.group_name)).getText());
								}
								startActivity(intent);
							}

						}
					}

					/**
					 * 删除群成员
					 *
					 * @param username
					 */
					protected void deleteMembersFromGroup(final String username) {
						final SafeDialog deleteDialog = new SafeDialog(GroupDetailsActivity.this);
						deleteDialog.setMessage(st13);
						deleteDialog.setCanceledOnTouchOutside(false);
						deleteDialog.show();
						//移除
						RequestManager.getMessManager().removeGroupPerson(groupId, username, new ResultCallback<String>() {
							@Override
							public void onError(int status, String errorMsg) {
								deleteDialog.dismiss();
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(getApplicationContext(), st14 ,Toast.LENGTH_SHORT).show();
									}
								});
							}

							@Override
							public void onResponse(String response) {
								isInDeleteMode = false;
								deleteDialog.dismiss();
								updateGroup();
								refreshMembers();
//								new Thread(new Runnable() {
//									@Override
//									public void run() {
//
//										runOnUiThread(new Runnable() {
//
//											@Override
//											public void run() {
//												deleteDialog.dismiss();
//												refreshMembers();
////												((TextView) findViewById(R.id.group_name)).setText(group.getGroupName() );
//											}
//										});
//									}
//								}).start();



							}
						});
//						new Thread(new Runnable() {
//
//							@Override
//							public void run() {
//
//								try {
//									// 删除被选中的成员
//								    EMClient.getInstance().groupManager().removeUserFromGroup(groupId, username);
//									isInDeleteMode = false;
//									runOnUiThread(new Runnable() {
//
//										@Override
//										public void run() {
//											deleteDialog.dismiss();
//											refreshMembers();
//											((TextView) findViewById(R.id.group_name)).setText(group.getGroupName() );
//										}
//									});
//								} catch (final Exception e) {
//									deleteDialog.dismiss();
//									runOnUiThread(new Runnable() {
//										public void run() {
//											Toast.makeText(getApplicationContext(), st14 + e.getMessage(),Toast.LENGTH_SHORT).show();
//										}
//									});
//								}
//
//							}
//						}).start();
					}
				});

				button.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View v) {
					    if(EMClient.getInstance().getCurrentUser().equals(username))
					        return true;
						if (group.getOwner().equals(EMClient.getInstance().getCurrentUser())) {
							new EaseAlertDialog(GroupDetailsActivity.this, null, st15, null, new AlertDialogUser() {

                                @Override
                                public void onResult(boolean confirmed, Bundle bundle) {
                                    if(confirmed){
                                        addUserToBlackList(username);
                                    }
                                }
                            }, true).show();

						}
						return false;
					}
				});
			}
			return convertView;
		}

		@Override
		public int getCount() {
			return super.getCount() + 2;
		}
	}

	protected void updateGroup() {
		new Thread(new Runnable() {
			public void run() {
				try {
				    EMClient.getInstance().groupManager().getGroupFromServer(groupId);

					runOnUiThread(new Runnable() {
						public void run() {
							((TextView) findViewById(R.id.group_name)).setText(group.getGroupName());
							loadingPB.setVisibility(View.INVISIBLE);
							refreshMembers();
							if (EMClient.getInstance().getCurrentUser().equals(group.getOwner())) {
								// 显示解散按钮
								exitBtn.setVisibility(View.GONE);
								deleteBtn.setVisibility(View.VISIBLE);
							} else {
								// 显示退出按钮
								exitBtn.setVisibility(View.VISIBLE);
								deleteBtn.setVisibility(View.GONE);
							}

							// update block
							EMLog.d(TAG, "group msg is blocked:" + group.isMsgBlocked());
							if (group.isMsgBlocked()) {
								switchButton.closeSwitch();
							} else {
							    switchButton.openSwitch();
							}
						}
					});

				} catch (Exception e) {
					runOnUiThread(new Runnable() {
						public void run() {
							loadingPB.setVisibility(View.INVISIBLE);
						}
					});
				}
			}
		}).start();
	}

	public void back(View view) {
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		instance = null;
	}

	private static class ViewHolder{
	    CircleImageView imageView;
	    TextView textView;
	    ImageView badgeDeleteView;
	}

    private class GroupChangeListener implements EMGroupChangeListener {

		@Override
		public void onInvitationReceived(String groupId, String groupName,
				String inviter, String reason) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onApplicationReceived(String groupId, String groupName,
				String applyer, String reason) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onApplicationAccept(String groupId, String groupName,
				String accepter) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onApplicationDeclined(String groupId, String groupName,
				String decliner, String reason) {

		}

//		@Override
//		public void onInvitationAccepted(String s, String s1, String s2) {
//
//		}

		@Override
		public void onInvitationAccepted(String groupId, String inviter,
				String reason) {
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					refreshMembers();
				}

        	});

		}

		@Override
		public void onInvitationDeclined(String groupId, String invitee,
				String reason) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onUserRemoved(String groupId, String groupName) {
			finish();

		}

//		@Override
//		public void onGroupDestroyed(String s, String s1) {
//
//		}

		@Override
		public void onGroupDestroyed(String groupId, String groupName) {
			finish();

		}

        @Override
        public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
            // TODO Auto-generated method stub

        }

    }

	/**
	 * 获取群成员列表
	 * @param groupId
	 */
	private void getGroupMembers(String groupId){
		RequestManager.getMessManager().getGroupMember(groupId, new ResultCallback<ResultBean<List<UserInfo>>>() {
			@Override
			public void onError(int status, String errorMsg) {
				MyToastUtils.showShortToast(getApplicationContext(),errorMsg);
			}

			@Override
			public void onResponse(ResultBean<List<UserInfo>> response) {
				List<UserInfo> datas=response.getData();
				if(datas!=null&&datas.size()>0){

					List<ChatUserBean> chatList=new ArrayList<ChatUserBean>();
					for (UserInfo friendsBean : datas){
						if(friendsBean!=null) {
							ChatUserBean chatUserBean = new ChatUserBean();
							chatUserBean.setUserId(friendsBean.getId());
							chatUserBean.setIcon(Uitls.imageFullUrl(friendsBean.getIcon()));
							EaseUser easeUser = DemoHelper.getInstance().getContactList().get(friendsBean.getId());
							if (easeUser!=null){
								chatUserBean.setNick(easeUser.getNick());
							}else {
								chatUserBean.setNick(friendsBean.getPetName());
							}
//							if (TextUtils.isEmpty(friendsBean.getPetName())) {
//								chatUserBean.setNick(friendsBean.getUsername());
//							} else {
//								chatUserBean.setNick(friendsBean.getPetName());
//							}
							chatList.add(chatUserBean);
						}
					}
					if(chatList.size()>0)
					ChatUserDao.saveUserList(chatList);
//					List<EaseUser> uList = new ArrayList<EaseUser>();
//					for (UserInfo friendsBean : datas) {
//						EaseUser easeUser = new EaseUser(friendsBean.getId());
//						if (!TextUtils.isEmpty(friendsBean.getPetName())) {
//							easeUser.setNick(friendsBean.getPetName());
//							easeUser.setInitialLetter(CharacterParserUtils.getInstance().getSelling(friendsBean.getPetName()).substring(0,1));
//						}
//						else {
//							easeUser.setNick(friendsBean.getUsername());
//							easeUser.setInitialLetter("#");
//						}
//						easeUser.setAvatar(IZtbUrl.BASE_URL + friendsBean.getIcon());
//						uList.add(easeUser);
//					}
//					if(uList!=null&&uList.size()>0)
//					DemoHelper.getInstance().updateContactList(uList);
				}


				List<String> members = new ArrayList<String>();
				members.addAll(group.getMembers());

				adapter = new GridAdapter(GroupDetailsActivity.this, R.layout.em_grid, members);
				userGridview.setAdapter(adapter);

				// 保证每次进详情看到的都是最新的group
				updateGroup();

			}
		});
	}

	/**
	 * 移除群成员及解散群聊
	 * @param groupId
	 * @param personId
	 */
	private void removeGroupPerson(final String groupId,String personId){
		final String st5 = getResources().getString(R.string.Dissolve_group_chat_tofail);
        RequestManager.getMessManager().removeGroupPerson(groupId, personId, new ResultCallback<ResultBean<String>>() {
			@Override
			public void onError(int status, String errorMsg) {
				runOnUiThread(new Runnable() {
					public void run() {
						progressDialog.dismiss();
						Toast.makeText(getApplicationContext(), st5, Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onResponse(ResultBean<String> response) {
				progressDialog.dismiss();
				sendBroadcast(new Intent(SimpleFrg.DATA_CHANGE_KEY));
				sendBroadcast(new Intent(MessFrg.UPDATE_CONTRACT));
				runOnUiThread(new Runnable() {
					public void run() {

						setResult(RESULT_OK);
						finish();
						if (ChatActivity.activityInstance != null)
							ChatActivity.activityInstance.finish();
					}
				});
			}
		});
	}

	/**
	 * 获取群组详情
	 */
	private void findGroupInfo(){
		RequestManager.getMessManager().findGroupInfo(groupId, new ResultCallback<ResultBean<GroupBean>>() {
			@Override
			public void onError(int status, String errorMsg) {

			}

			@Override
			public void onResponse(ResultBean<GroupBean> response) {
				//获取群成员列表

              GroupBean groupBean=response.getData();
				if(groupBean!=null){
					tvTime.setText("还剩"+groupBean.getReminderDays()+"天");
					type=groupBean.isType();
					MyLogUtils.info("是否自建群："+groupBean.isType());
				}
				if(type==true) {
					changeGroupNameLayout.setOnClickListener(GroupDetailsActivity.this);
				}
				getGroupMembers(groupId);
			}
		});
	}
}
