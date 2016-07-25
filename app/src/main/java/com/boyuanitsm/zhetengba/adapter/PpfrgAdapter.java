package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.ShareDialogAct;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.ScheduleInfo;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.boyuanitsm.zhetengba.view.ScheduDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人主页 档期frg 适配器
 * Created by bitch-1 on 2016/5/16.
 */
public class PpfrgAdapter extends BaseAdapter {
    private Context context;
    private List<ScheduleInfo> scheduleEntity = new ArrayList<>();
    private List<UserInfo> userInfoList=new ArrayList<>();
    private List<SimpleInfo> simpleInfos;
    private int index;
    private PopupWindow popupWindow;
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    public PpfrgAdapter(Context context, List<ScheduleInfo> scheduleEntity, List<UserInfo> userInfoList) {
        this.context = context;
        this.scheduleEntity=scheduleEntity;
        this.userInfoList=userInfoList;
    }

    @Override
    public int getCount() {
        return scheduleEntity==null?0:scheduleEntity.size();
    }

    @Override
    public Object getItem(int position) {
        return scheduleEntity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CalHolder calHolder;
        index=position;
        if (convertView!=null&&convertView.getTag()!=null){
            calHolder= (CalHolder) convertView.getTag();
        }else {
            convertView=View.inflate(context,R.layout.item_list_ppfrg,null);
            calHolder=new CalHolder();
            calHolder.iv_icon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            calHolder.tv_Name = (TextView) convertView.findViewById(R.id.tv_Name);
            calHolder.iv_gen = (ImageView) convertView.findViewById(R.id.iv_gen);
            calHolder.tv_time_cal = (TextView) convertView.findViewById(R.id.tv_time_cal);
            calHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            calHolder.ll_cal_guanzhu = (LinearLayout) convertView.findViewById(R.id.ll_cal_guanzhu);
            calHolder.iv_cal_guanzhu = (ImageView) convertView.findViewById(R.id.iv_cal_guanzhu);
            calHolder.tv_cal_text_guanzhu = (TextView) convertView.findViewById(R.id.tv_cal_text_guanzhu);
            calHolder.tv_gzcal_num = (TextView) convertView.findViewById(R.id.tv_gzcal_num);
            calHolder.ll_yh = (LinearLayout) convertView.findViewById(R.id.ll_yh);
            calHolder.iv_cal_yh = (ImageView) convertView.findViewById(R.id.iv_cal_yh);
            calHolder.tv_cal_yh = (TextView) convertView.findViewById(R.id.tv_cal_yh);
            calHolder.ll_guanzhu = (LinearLayout) convertView.findViewById(R.id.ll_guanzhu);
            calHolder.ll_yue = (LinearLayout) convertView.findViewById(R.id.ll_yue);
            calHolder.ll_name = (RelativeLayout) convertView.findViewById(R.id.ll_name);
            calHolder.ll_cal_guanzhu_del=(LinearLayout)convertView.findViewById(R.id.ll_guanzhu_del);
            calHolder.ll_cal_share=(LinearLayout)convertView.findViewById(R.id.ll_yue_share);
            calHolder.iv_bag= (ImageView) convertView.findViewById(R.id.iv_bag);
            calHolder.rl_main= (LinearLayout) convertView.findViewById(R.id.rl_main);
            convertView.setTag(calHolder);
        }
        if (!UserInfoDao.getUser().getId().equals(scheduleEntity.get(position).getCreatePersonId())){
            calHolder.ll_guanzhu.setVisibility(View.GONE);//关注暂时注掉
            calHolder.ll_yue.setVisibility(View.VISIBLE);
            calHolder.ll_cal_guanzhu_del.setVisibility(View.GONE);
            calHolder.ll_cal_share.setVisibility(View.GONE);
            if (scheduleEntity.get(position).isAgreeAbout()){
                calHolder.iv_cal_yh.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.finger_b));
                calHolder.tv_cal_yh.setText("邀约成功");
                calHolder.ll_yue.setEnabled(false);
                calHolder.iv_bag.setEnabled(false);
                calHolder.iv_bag.setImageResource(R.mipmap.yue);
            }else{
                if (scheduleEntity.get(position).getDictName().equals("闲来无事")){
                    calHolder.iv_bag.setImageResource(R.mipmap.lv);
                }else if (scheduleEntity.get(position).getDictName().equals("百无聊懒")){
                    calHolder.iv_bag.setImageResource(R.mipmap.huang);
                }else if (scheduleEntity.get(position).getDictName().equals("闲的要死")){
                    calHolder.iv_bag.setImageResource(R.mipmap.hong);
                }else if (scheduleEntity.get(position).getDictName().equals("无聊至极")){
                    calHolder.iv_bag.setImageResource(R.mipmap.lan);
                }

                calHolder.iv_cal_yh.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.finger));
                calHolder.tv_cal_yh.setText("约Ta");
                calHolder.ll_yue.setEnabled(true);
                calHolder.iv_bag.setEnabled(true);
                calHolder.iv_bag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        calHolder.iv_bag.setEnabled(false);
                        simpleInfos=new ArrayList<SimpleInfo>();
                        RequestManager.getScheduleManager().findMatchingActivities(scheduleEntity.get(position).getScheduleId(), new ResultCallback<ResultBean<List<SimpleInfo>>>() {
                            @Override
                            public void onError(int status, String errorMsg) {
                                calHolder.iv_bag.setClickable(true);
                            }

                            @Override
                            public void onResponse(ResultBean<List<SimpleInfo>> response) {
                                simpleInfos = response.getData();
                                ScheduDialog dialog = new ScheduDialog(context, simpleInfos, scheduleEntity.get(position).getScheduleId());
                                dialog.show();
                                calHolder.iv_bag.setClickable(true);
//                            calHolder.iv_cal_yh.setBackground(context.getResources().getDrawable(R.drawable.finger_b, null));
                            }
                        });
                    }
                });
            }

        }else {
            calHolder.ll_guanzhu.setVisibility(View.GONE);
            calHolder.ll_yue.setVisibility(View.GONE);
            calHolder.ll_cal_guanzhu_del.setVisibility(View.VISIBLE);
            calHolder.ll_cal_share.setVisibility(View.VISIBLE);
            calHolder.iv_bag.setImageResource(R.mipmap.bmore);
            calHolder.iv_bag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupWindow(calHolder.rl_main,position);
                }
            });
        }
        ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(scheduleEntity.get(position).getUserIcon()),calHolder.iv_icon,optionsImag);//用户头像；
//        calHolder.iv_icon.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.test_user));
        if (!TextUtils.isEmpty(userInfoList.get(0).getPetName())){
            calHolder.tv_Name.setText(userInfoList.get(0).getPetName());//用户昵称
        }else {
            calHolder.tv_Name.setText("暂无昵称");
        }
        if (!TextUtils.isEmpty(scheduleEntity.get(position).getUserSex())){
            if (scheduleEntity.get(position).getUserSex()=="1"){
                calHolder.iv_gen.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.male));//用户性别
            }else {
                calHolder.iv_gen.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.female));
            }
        }
        String strStart = ZhetebaUtils.timeToDate(Long.parseLong(scheduleEntity.get(position).getStartTime()));
        String strEnd = ZhetebaUtils.timeToDate(Long.parseLong(scheduleEntity.get(position).getEndTime()));
        if (strStart.substring(1,6).equals(strEnd.substring(1,6))){
            String strTime=strStart+"—"+strEnd.substring(6);
            calHolder.tv_time_cal.setText(strTime);//活动时间；
        }else {
            calHolder.tv_time_cal.setText(strStart + "—" + strEnd);//活动时间；
        }

//        calHolder.tv_time_cal.setText(ZhetebaUtils.timeToDate(Long.parseLong(scheduleEntity.get(position).getStartTime()))+ "—" + ZhetebaUtils.timeToDate(Long.parseLong(scheduleEntity.get(position).getEndTime())));
//        if (!TextUtils.isEmpty(scheduleEntity.get(position).getDictName())){
//            calHolder.tv_state.setText(scheduleEntity.get(position).getDictName());//标签名称
//        }
        if (!TextUtils.isEmpty(scheduleEntity.get(position).getDictName())) {
            calHolder.tv_state.setText(scheduleEntity.get(position).getDictName());//标签名称
            if (scheduleEntity.get(position).getDictName().equals("闲来无事")){
                calHolder.tv_state.setBackgroundResource(R.drawable.rdbt_xl_check);
            }else if (scheduleEntity.get(position).getDictName().equals("百无聊懒")){
                calHolder.tv_state.setBackgroundResource(R.drawable.rdbt_bw_check);
            }else if (scheduleEntity.get(position).getDictName().equals("闲的要死")){
                calHolder.tv_state.setBackgroundResource(R.drawable.rdbt_wl_check);
            }else if (scheduleEntity.get(position).getDictName().equals("无聊至极")){
                calHolder.tv_state.setBackgroundResource(R.drawable.rdbt_ys_check);
            }
        }

//        if (list.get(position).)是否关注
//        calHolder.iv_cal_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect));
//        calHolder.tv_cal_text_guanzhu.setText("关注");
//        if (scheduleEntity.get(position).getFollowNum()!=0){
//            calHolder.tv_gzcal_num.setVisibility(View.VISIBLE);
//            calHolder.tv_gzcal_num.setText(scheduleEntity.get(position).getFollowNum()+"");
//        }else {
//            calHolder.tv_gzcal_num.setVisibility(View.GONE);
//        }


        calHolder.ll_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calHolder.iv_cal_guanzhu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collect_b));
            }
        });
//        calHolder.ll_yue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calHolder.iv_cal_yh.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.finger_b));
//            }
//        });
        calHolder.ll_yue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calHolder.ll_yue.setClickable(false);
                simpleInfos=new ArrayList<SimpleInfo>();
                RequestManager.getScheduleManager().findMatchingActivities(scheduleEntity.get(position).getScheduleId(), new ResultCallback<ResultBean<List<SimpleInfo>>>() {
                    @Override
                    public void onError(int status, String errorMsg) {
                        calHolder.ll_yue.setClickable(true);
                    }

                    @Override
                    public void onResponse(ResultBean<List<SimpleInfo>> response) {
                        calHolder.ll_yue.setClickable(true);
                        simpleInfos= response.getData();
                        ScheduDialog dialog=new ScheduDialog(context,simpleInfos,scheduleEntity.get(position).getScheduleId());
                        dialog.show();
//                            calHolder.iv_cal_yh.setBackground(context.getResources().getDrawable(R.drawable.finger_b, null));
                    }
                });

            }
        });
        calHolder.ll_cal_guanzhu_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAlertDialog(context).builder().setTitle("提示").setMsg("确认删除此条档期？").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //调用删除此活动接口,刷新数据；
                        removeSchuldel(scheduleEntity.get(position).getScheduleId(), position);
                    }
                }).setNegativeButton("取消", null).show();

            }
        });
        calHolder.ll_cal_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启分享界面
                Intent intent=new Intent();
                intent.setClass(context,ShareDialogAct.class);
                intent.putExtra("type", 2);
                intent.putExtra("id",scheduleEntity.get(position).getScheduleId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    public static class CalHolder {
        public CircleImageView iv_icon;//头像
        public TextView tv_Name;//昵称
        public ImageView iv_gen;//性别
        public TextView tv_time_cal;//活动日期
        public TextView tv_state;//状态
        public LinearLayout ll_cal_guanzhu;//关注数量
        public ImageView iv_cal_guanzhu;//关注图标
        public TextView tv_cal_text_guanzhu;//关注文本
        public TextView tv_gzcal_num;//关注数量设置
        public LinearLayout ll_yh;//约会
        public ImageView iv_cal_yh;//参加头像
        public TextView tv_cal_yh;//参加数量
        public int cal_gznum = 0;//默认关注人数0
        public LinearLayout ll_guanzhu;//关注
        public LinearLayout ll_yue;//约TA
        private RelativeLayout ll_name;//个人昵称
        private LinearLayout ll_cal_guanzhu_del;
        private LinearLayout ll_cal_share;
        private ImageView iv_bag;//档期列表右边的
        private LinearLayout rl_main;//item大布局
    }
    /***
     * 删除档期
     * @param id
     * @param position
     */
    private void removeSchuldel(String id, final int position){
        RequestManager.getScheduleManager().removeSchuldel(id, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                MyToastUtils.showShortToast(context, "删除档期成功！");
                scheduleEntity.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    private void showPopupWindow(View parent, final int position) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.popuwindowsxx_dialog, null);

        // 实例化popupWindow
        popupWindow = new PopupWindow(layout, AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager)context. getSystemService(context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        //获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(parent, xpos, -25);

        LinearLayout iv_shanc= (LinearLayout) layout.findViewById(R.id.iv_shanc);
        LinearLayout iv_fenxiang= (LinearLayout) layout.findViewById(R.id.iv_fenxiang);

        iv_shanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAlertDialog(context).builder().setTitle("提示").setMsg("确认删除此条档期？").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //调用删除此活动接口,刷新数据；
                        removeSchuldel(scheduleEntity.get(position).getScheduleId(), position);
                    }
                }).setNegativeButton("取消", null).show();
            }
        });

        iv_fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("type",2);
                intent.putExtra("id",scheduleEntity.get(position).getScheduleId());
                intent.setClass(context,ShareDialogAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }
}
