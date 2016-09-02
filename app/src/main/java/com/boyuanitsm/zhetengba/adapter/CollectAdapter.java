package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.mine.MyColleitionAct;
import com.boyuanitsm.zhetengba.bean.CollectionBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.fragment.calendarFrg.SimpleFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 我的收藏列表
 * Created by bitch-1 on 2016/6/3.
 */
public class CollectAdapter extends BaseAdapter {
    private Context context;
    private List<CollectionBean> list;
    private int dex;
    private boolean flag = true;//true参加


    // 图片缓存 默认 等
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead) //设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.userhead) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .build(); // 创建配置过得DisplayImageOption对象


    public CollectAdapter(Context context) {
        this.context = context;
    }

    public CollectAdapter(Context context, List<CollectionBean> list) {
        this.context = context;
        this.list = list;
    }

    public void notify(List<CollectionBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return list.size();


    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_collect, null);
            holder = new ViewHolder();
            holder.iv_headphoto = (ImageView) convertView.findViewById(R.id.iv_headphoto);
            holder.tv_niName = (TextView) convertView.findViewById(R.id.tv_niName);
            holder.tv_hdtheme = (TextView) convertView.findViewById(R.id.tv_hdtheme);
            holder.tv_loaction = (TextView) convertView.findViewById(R.id.tv_loaction);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_guanzhu = (TextView) convertView.findViewById(R.id.tv_guanzhu);
            holder.tv_guanzhu_num = (TextView) convertView.findViewById(R.id.tv_guanzhu_num);
            holder.tv_join_tal_num = (TextView) convertView.findViewById(R.id.tv_join_tal_num);
            holder.tv_join_num = (TextView) convertView.findViewById(R.id.tv_join_num);
            holder.ll_shouc = (LinearLayout) convertView.findViewById(R.id.ll_shouc);
            holder.iv_gender = (ImageView) convertView.findViewById(R.id.iv_gender);
            holder.iv_join = (ImageView) convertView.findViewById(R.id.iv_join);
            holder.tv_text_jion = (TextView) convertView.findViewById(R.id.tv_text_jion);
            holder.iv_actdetial = (CircleImageView) convertView.findViewById(R.id.iv_actdetial);
            holder.ll_join = (LinearLayout) convertView.findViewById(R.id.ll_join);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (list != null) {
            //昵称
            if (!TextUtils.isEmpty(list.get(position).getUserNm())) {
                holder.tv_niName.setText(list.get(position).getUserNm());
            }
            //主题
            if (!TextUtils.isEmpty(list.get(position).getActivityTheme())) {
                holder.tv_hdtheme.setText(list.get(position).getActivityTheme());
            }
            //地址
            if (!TextUtils.isEmpty(list.get(position).getActivitySite())) {
                holder.tv_loaction.setText(list.get(position).getActivitySite());
            }
            //时间
            if (!TextUtils.isEmpty(list.get(position).getStartTime()) && !TextUtils.isEmpty(list.get(position).getEndTime()))
                ;
            {
                holder.tv_date.setText(ZhetebaUtils.timeToDate(Long.parseLong(list.get(position).getStartTime())) + "—" + ZhetebaUtils.timeToDate(Long.parseLong(list.get(position).getEndTime())));
            }
            //关注数
            if (list.get(position).getFollowNum() != null) {
                holder.tv_guanzhu_num.setText(list.get(position).getFollowNum() + "");
            }
            if (list.get(position).isFollow()) {
                holder.tv_guanzhu.setText("已关注");
            }
            //已经响应人数
            if (list.get(position).getMemberNum() != null) {
                holder.tv_join_num.setText(list.get(position).getMemberNum() + "");
            }
            //邀请总人数
            if (list.get(position).getInviteNumber() != null) {
                holder.tv_join_tal_num.setText("/" + list.get(position).getInviteNumber());
            }

            //性别
            if (!TextUtils.isEmpty(list.get(position).getUserSex())) {
                if (list.get(position).getUserSex() == "男") {
                    holder.iv_gender.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.male));//男
                } else {
                    holder.iv_gender.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.female));//女
                }

            }
            //是已经响应了还是没响应
            if (list.get(position).isJoining()) {
                //已经响应了
                holder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cancel));//参加icon
                holder.tv_text_jion.setText("取消响应");
                holder.tv_join_num.setTextColor(Color.parseColor("#fd3838"));
            } else {
                //没有响应
                holder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.add));//参加icon
                holder.tv_text_jion.setText("响应");
                holder.tv_join_num.setTextColor(Color.parseColor("#999999"));
            }
            //用户图片
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getUserIcon()), holder.iv_headphoto, options);
            //右边标签图片
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(list.get(position).getIcon()), holder.iv_actdetial, options);
            //关注添加监听取消关注并删除本地显示
            holder.ll_shouc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MyAlertDialog(context).builder().setTitle("提示").setMsg("确认取消关注").setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dex = position;
                            removeCollection(list.get(position).getId());
                        }
                    }).setNegativeButton("取消", null).show();

                }
            });
            if (list.get(position).getMemberNum() == list.get(position).getInviteNumber()) {
                if (!list.get(position).isJoining()){
                    holder.ll_join.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyToastUtils.showShortToast(context, "参加人数已满,请参加其他活动！");
                        }
                    });
                }else {
                    holder.ll_join.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.ll_join.setEnabled(false);
                            if (list.get(position).isJoining()) {
                                final MyAlertDialog dialog = new MyAlertDialog(context);
                                dialog.builder().setTitle("提示").setMsg("确认取消参加活动？").setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        stateCancelChange(position, holder);
                                    }
                                }).setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        holder.ll_join.setEnabled(true);
                                    }
                                }).show();
                            } else {
                                stateJionChange(position, holder);
                            }


                        }
                    });
                }
            } else {
                holder.ll_join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.ll_join.setEnabled(false);
                        if (list.get(position).isJoining()) {
                            final MyAlertDialog dialog = new MyAlertDialog(context);
                            dialog.builder().setTitle("提示").setMsg("确认取消参加活动？").setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    stateCancelChange(position, holder);
                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.ll_join.setEnabled(true);
                                }
                            }).show();
                        } else {
                            stateJionChange(position, holder);
                        }


                    }
                });
            }

//            //参加活动
//            holder.ll_join.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(list.get(position).isJoin()){
//                        new MyAlertDialog(context).builder().setTitle("提示").setMsg("确认取消参加").setPositiveButton("确认", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //掉取消参加活动的接口还没出来
//                                stateCancelChange(position,holder);
//                                MyToastUtils.showShortToast(context,"取消参加活动成功");
//                            }
//                        }).setNegativeButton("取消",null).show();
//                    }else {
//                        //参加活动
//                       stateChange(position, holder);
//                    }
//                }
//
//            });

        }

        return convertView;

    }


    class ViewHolder {
        private ImageView iv_headphoto;//用户图像
        private TextView tv_niName;//昵称
        private TextView tv_hdtheme;//活动
        private TextView tv_loaction;//活动地址
        private TextView tv_date;//活动时间
        private TextView tv_guanzhu;
        private TextView tv_guanzhu_num;//关注数
        private TextView tv_join_tal_num;//邀请总人数
        private TextView tv_join_num;//已参加人数
        private LinearLayout ll_shouc;//取消关注那列
        private ImageView iv_gender;//性别
        private ImageView iv_join;//参加头像
        private TextView tv_text_jion;//参加/取消参加
        private CircleImageView iv_actdetial;//活动标签
        private LinearLayout ll_join;//参加活动


    }


    public void removeCollection(String collectionId) {
        RequestManager.getScheduleManager().removeCollection(collectionId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                MyToastUtils.showShortToast(context,response.getMessage());
                list.remove(dex);
                notifyDataSetChanged();
                context.sendBroadcast(new Intent(SimpleFrg.DATA_CHANGE_KEY));
                if (list.size()==0){
                    context.sendBroadcast(new Intent(MyColleitionAct.COLLECTION));
                }
            }
        });
    }


    /**
     * 取消参加或响应活动接口
     *
     * @param
     */

    private void stateCancelChange(final int position, final ViewHolder holder) {
        RequestManager.getScheduleManager().cancelActivity(list.get(position).getId(), new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                if (TextUtils.equals(response.getData(),0+"")){
                    holder.ll_join.setEnabled(true);
                    list.remove(position);
                    notifyDataSetChanged();
                    MyToastUtils.showShortToast(context,response.getMessage());
                    return;
                }
                delGroup(list.get(position).getId());
                holder.ll_join.setEnabled(true);
                holder.iv_join.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.add));
                holder.tv_text_jion.setText("参加");
                holder.tv_join_num.setTextColor(Color.parseColor("#999999"));
                int i = list.get(position).getMemberNum();
                i = i - 1;
                holder.tv_join_num.setText(i + "");
                list.get(position).setJoining(false);
                list.get(position).setMemberNum(i);
                MyToastUtils.showShortToast(context,response.getMessage());
                context.sendBroadcast(new Intent(SimpleFrg.DATA_CHANGE_KEY));

            }
        });
    }

    /**
     * 退出群聊
     * @param activityId
     */
    private void delGroup(String activityId) {

        RequestManager.getScheduleManager().deleGroup(activityId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {

            }
        });
    }

    /**
     * 参加或响应活动接口
     *
     * @param
     */

    private void stateJionChange(final int position, final ViewHolder viewHolder) {
        RequestManager.getScheduleManager().getRespondActivity(list.get(position).getId(), new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                viewHolder.ll_join.setEnabled(true);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                addGroup(list.get(position).getId());
                viewHolder.ll_join.setEnabled(true);
                int i = list.get(position).getMemberNum();
                i = i + 1;
                list.get(position).setMemberNum(i);
                list.get(position).setJoining(true);
                MyToastUtils.showShortToast(context,response.getMessage());
                context.sendBroadcast(new Intent(SimpleFrg.DATA_CHANGE_KEY));
                notifyDataSetChanged();
            }
        });
    }

    /**
     * 参加成功后，调用添加群组
     *
     * @param actId
     */
    private void addGroup(String actId) {
        RequestManager.getScheduleManager().addHXGroup(actId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {

            }
        });
    }

}
