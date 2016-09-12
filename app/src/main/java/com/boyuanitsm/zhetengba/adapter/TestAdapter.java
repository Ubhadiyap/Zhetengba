package com.boyuanitsm.zhetengba.adapter;

import android.app.ActionBar;
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
import com.boyuanitsm.zhetengba.activity.circle.CircleTextAct;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.CircleInfo;
import com.boyuanitsm.zhetengba.bean.ImageInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.ScheduleInfo;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.LayoutHelperUtil;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.CustomImageView;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.view.PicShowDialog;
import com.boyuanitsm.zhetengba.view.ScheduDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试个人主页listviewAdapter
 * Created by xiaoke on 2016/5/26.
 */
public class TestAdapter extends BaseAdapter {
    private Context context;
    private List<List<ImageInfo>> dateList;
    private List<ScheduleInfo> scheduleEntity = new ArrayList<>();
    private List<UserInfo> userInfoList = new ArrayList<>();
    private List<CircleEntity> circleTalkEntityList = new ArrayList<>();
    private int clickPos = -1;
    private String circleId;//说说id
    private List<SimpleInfo> simpleInfos;
    private PopupWindow popupWindow;
    private boolean flag;
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.tum)
            .showImageOnFail(R.mipmap.tum).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    private DisplayImageOptions optionsImagh = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.userhead)
            .showImageOnFail(R.mipmap.userhead).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public TestAdapter(Context context, List<UserInfo> userEntity, List<ScheduleInfo> scheduleEntity, List<CircleEntity> circleTalkEntity, int i, List<List<ImageInfo>> datalist) {
        this.context = context;
        this.circleTalkEntityList = circleTalkEntity;
        this.userInfoList = userEntity;
        this.scheduleEntity = scheduleEntity;
        this.clickPos = i;
        this.dateList = datalist;
    }

    public void updata(List<UserInfo> userEntity, List<ScheduleInfo> scheduleEntity, List<CircleEntity> circleTalkEntity, int i, List<List<ImageInfo>> datalist) {
        this.circleTalkEntityList = circleTalkEntity;
        this.userInfoList = userEntity;
        this.scheduleEntity = scheduleEntity;
        this.clickPos = i;
        this.dateList = datalist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (clickPos == 0) {
            return scheduleEntity == null ? 0 : scheduleEntity.size();
        } else if (clickPos==1){
            return circleTalkEntityList == null ? 0 : circleTalkEntityList.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (clickPos == 0) {
            return scheduleEntity.get(position);
        } else {
            return circleTalkEntityList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView != null && convertView.getTag() != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.test_item_cal, null);
            viewHolder.ivChHead = (ImageView) convertView.findViewById(R.id.iv_ch_head);
            viewHolder.tvChNiName = (TextView) convertView.findViewById(R.id.tv_ch_niName);
            viewHolder.ivChGendar = (ImageView) convertView.findViewById(R.id.iv_ch_gendar);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.ll_like = (LinearLayout) convertView.findViewById(R.id.like);
            viewHolder.ll_share = (LinearLayout) convertView.findViewById(R.id.ll_share);
            viewHolder.ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
            viewHolder.iv_ch_image = (MyGridView) convertView.findViewById(R.id.iv_ch_image);
            viewHolder.iv_oneimage = (CustomImageView) convertView.findViewById(R.id.iv_oneimage);
            viewHolder.tv_cir_name = (TextView) convertView.findViewById(R.id.tv_cir_name);
            viewHolder.ll_two = (LinearLayout) convertView.findViewById(R.id.ll_two);
            viewHolder.iv_two_one = (ImageView) convertView.findViewById(R.id.iv_two_one);
            viewHolder.iv_two_two = (ImageView) convertView.findViewById(R.id.iv_two_two);
            viewHolder.iv_two_three = (ImageView) convertView.findViewById(R.id.iv_two_three);
            viewHolder.iv_two_four = (ImageView) convertView.findViewById(R.id.iv_two_four);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.znum2 = (TextView) convertView.findViewById(R.id.znum2);
            viewHolder.cnum2 = (TextView) convertView.findViewById(R.id.cnum2);
            viewHolder.cnumText = (TextView) convertView.findViewById(R.id.cnumText);
            viewHolder.znumText = (TextView) convertView.findViewById(R.id.znumText);
            viewHolder.iv_icon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_Name = (TextView) convertView.findViewById(R.id.tv_Name);
            viewHolder.iv_gen = (ImageView) convertView.findViewById(R.id.iv_gen);
            viewHolder.tv_time_cal = (TextView) convertView.findViewById(R.id.tv_time_cal);
            viewHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            viewHolder.ll_guanzhu = (LinearLayout) convertView.findViewById(R.id.ll_guanzhu);
            viewHolder.ll_yue = (LinearLayout) convertView.findViewById(R.id.ll_yue);
            viewHolder.ll_cal_guanzhu_del = (LinearLayout) convertView.findViewById(R.id.ll_guanzhu_del);
            viewHolder.ll_cal_share = (LinearLayout) convertView.findViewById(R.id.ll_yue_share);
            viewHolder.iv_bag = (ImageView) convertView.findViewById(R.id.iv_bag);
            viewHolder.rl_main = (LinearLayout) convertView.findViewById(R.id.rl_main);
            viewHolder.rl_dq = (RelativeLayout) convertView.findViewById(R.id.rl_dq);
            viewHolder.ll_qz = (LinearLayout) convertView.findViewById(R.id.ll_qz);
            convertView.setTag(viewHolder);
        }

        if (clickPos == 0) {
            viewHolder.rl_dq.setVisibility(View.VISIBLE);
            viewHolder.ll_qz.setVisibility(View.GONE);
            if (!UserInfoDao.getUser().getId().equals(scheduleEntity.get(position).getCreatePersonId())) {
                viewHolder.ll_guanzhu.setVisibility(View.GONE);//关注暂时注掉
                viewHolder.ll_yue.setVisibility(View.VISIBLE);
                viewHolder.ll_cal_guanzhu_del.setVisibility(View.GONE);
                viewHolder.ll_cal_share.setVisibility(View.GONE);
                if (scheduleEntity.get(position).isAgreeAbout()) {
                    viewHolder.ll_yue.setEnabled(false);
                    viewHolder.iv_bag.setEnabled(false);
                    viewHolder.iv_bag.setImageResource(R.mipmap.yue);
                } else {
                    if (scheduleEntity.get(position).getDictName().equals("闲来无事")) {
                        viewHolder.iv_bag.setImageResource(R.mipmap.lv);
                    } else if (scheduleEntity.get(position).getDictName().equals("百无聊懒")) {
                        viewHolder.iv_bag.setImageResource(R.mipmap.huang);
                    } else if (scheduleEntity.get(position).getDictName().equals("闲的要死")) {
                        viewHolder.iv_bag.setImageResource(R.mipmap.hong);
                    } else if (scheduleEntity.get(position).getDictName().equals("无聊至极")) {
                        viewHolder.iv_bag.setImageResource(R.mipmap.lan);
                    }
                    viewHolder.ll_yue.setEnabled(true);
                    viewHolder.iv_bag.setEnabled(true);
                    viewHolder.iv_bag.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewHolder.iv_bag.setEnabled(false);
                            simpleInfos = new ArrayList<SimpleInfo>();
                            RequestManager.getScheduleManager().findMatchingActivities(scheduleEntity.get(position).getScheduleId(), new ResultCallback<ResultBean<List<SimpleInfo>>>() {
                                @Override
                                public void onError(int status, String errorMsg) {
                                    viewHolder.iv_bag.setEnabled(true);
                                }

                                @Override
                                public void onResponse(ResultBean<List<SimpleInfo>> response) {
                                    simpleInfos = response.getData();
                                    ScheduDialog dialog = new ScheduDialog(context, simpleInfos, scheduleEntity.get(position).getScheduleId(),position);
                                    dialog.show();
                                    viewHolder.iv_bag.setEnabled(true);
                                }
                            });
                        }
                    });
                }

            } else {
                viewHolder.ll_guanzhu.setVisibility(View.GONE);
                viewHolder.ll_yue.setVisibility(View.GONE);
                viewHolder.ll_cal_guanzhu_del.setVisibility(View.VISIBLE);
                viewHolder.ll_cal_share.setVisibility(View.VISIBLE);
                viewHolder.iv_bag.setImageResource(R.mipmap.bmore);
                viewHolder.iv_bag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupWindow(viewHolder.rl_main, position);
                    }
                });
            }
            ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(scheduleEntity.get(position).getUserIcon()), viewHolder.iv_icon, optionsImag);//用户头像；
            if (!TextUtils.isEmpty(userInfoList.get(0).getPetName())) {
                viewHolder.tv_Name.setText(userInfoList.get(0).getPetName());//用户昵称
            } else {
                viewHolder.tv_Name.setText("暂无昵称");
            }
            if (!TextUtils.isEmpty(scheduleEntity.get(position).getUserSex())) {
                if (scheduleEntity.get(position).getUserSex() == "1") {
                    viewHolder.iv_gen.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.male));//用户性别
                } else {
                    viewHolder.iv_gen.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.female));
                }
            }
            String strStart = ZhetebaUtils.timeToDate(Long.parseLong(scheduleEntity.get(position).getStartTime()));
            String strEnd = ZhetebaUtils.timeToDate(Long.parseLong(scheduleEntity.get(position).getEndTime()));
            if (strStart.substring(1, 6).equals(strEnd.substring(1, 6))) {
                String strTime = strStart + "—" + strEnd.substring(6);
                viewHolder.tv_time_cal.setText(strTime);//活动时间；
            } else {
                viewHolder.tv_time_cal.setText(strStart + "—" + strEnd);//活动时间；
            }

            if (!TextUtils.isEmpty(scheduleEntity.get(position).getDictName())) {
                viewHolder.tv_state.setText(scheduleEntity.get(position).getDictName());//标签名称
                if (scheduleEntity.get(position).getDictName().equals("闲来无事")) {
                    viewHolder.tv_state.setBackgroundResource(R.drawable.rdbt_xl_check);
                } else if (scheduleEntity.get(position).getDictName().equals("百无聊懒")) {
                    viewHolder.tv_state.setBackgroundResource(R.drawable.rdbt_bw_check);
                } else if (scheduleEntity.get(position).getDictName().equals("闲的要死")) {
                    viewHolder.tv_state.setBackgroundResource(R.drawable.rdbt_wl_check);
                } else if (scheduleEntity.get(position).getDictName().equals("无聊至极")) {
                    viewHolder.tv_state.setBackgroundResource(R.drawable.rdbt_ys_check);
                }
            }

        } else if (clickPos == 1) {
            viewHolder.rl_dq.setVisibility(View.GONE);
            viewHolder.ll_qz.setVisibility(View.VISIBLE);
           final List<ImageInfo> itemList= dateList.get(position);
            if (itemList == null) {
                viewHolder.iv_ch_image.setVisibility(View.GONE);
                viewHolder.iv_oneimage.setVisibility(View.GONE);
                viewHolder.ll_two.setVisibility(View.GONE);
            } else if (itemList.size() == 1) {
                viewHolder.iv_ch_image.setVisibility(View.GONE);
                viewHolder.ll_two.setVisibility(View.GONE);
                viewHolder.iv_oneimage.setVisibility(View.VISIBLE);
                LayoutHelperUtil.handlerOneImage(context, itemList.get(0), viewHolder.iv_oneimage);
                viewHolder.iv_oneimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(context, itemList, 0);
                        dialog.show();
                    }
                });
            } else if (itemList.size() == 4) {
                viewHolder.iv_ch_image.setVisibility(View.GONE);
                viewHolder.iv_oneimage.setVisibility(View.GONE);
                viewHolder.ll_two.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(itemList.get(0).getUrl()), viewHolder.iv_two_one, optionsImag);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(itemList.get(1).getUrl()), viewHolder.iv_two_two, optionsImag);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(itemList.get(2).getUrl()), viewHolder.iv_two_three, optionsImag);
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(itemList.get(3).getUrl()), viewHolder.iv_two_four, optionsImag);
                viewHolder.iv_two_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(context, itemList, 0);
                        dialog.show();
                    }
                });

                viewHolder.iv_two_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(context, itemList, 1);
                        dialog.show();
                    }
                });

                viewHolder.iv_two_three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(context, itemList, 2);
                        dialog.show();
                    }
                });

                viewHolder.iv_two_four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicShowDialog dialog = new PicShowDialog(context, itemList, 3);
                        dialog.show();
                    }
                });

            } else {
                viewHolder.iv_oneimage.setVisibility(View.GONE);
                viewHolder.ll_two.setVisibility(View.GONE);
                viewHolder.iv_ch_image.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ZhetebaUtils.dip2px(context, 255), ActionBar.LayoutParams.WRAP_CONTENT);
                viewHolder.iv_ch_image.setLayoutParams(params);
                viewHolder.iv_ch_image.setNumColumns(3);
                PicGdAdapter adapter = new PicGdAdapter(context, itemList, position);
                viewHolder.iv_ch_image.setAdapter(adapter);

            }

            if (circleTalkEntityList != null) {
                //圈子个人动态都不可点击
                viewHolder.ll_like.setEnabled(false);
                viewHolder.ll_share.setEnabled(false);
                viewHolder.ll_comment.setEnabled(false);
                viewHolder.tv_content.setEnabled(false);
                viewHolder.tv_cir_name.setEnabled(false);
                if (!TextUtils.isEmpty(circleTalkEntityList.get(position).getCreateTime())) {
                    viewHolder.tvTime.setText(ZtinfoUtils.timeChange(Long.parseLong(circleTalkEntityList.get(position).getCreateTime())));
                }
                if (!TextUtils.isEmpty(circleTalkEntityList.get(position).getTalkContent())) {
                    viewHolder.tv_content.setVisibility(View.VISIBLE);
                    viewHolder.tv_content.setText(circleTalkEntityList.get(position).getTalkContent());
                } else {
                    viewHolder.tv_content.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(circleTalkEntityList.get(position).getCircleName())) {
                    viewHolder.tv_cir_name.setText(circleTalkEntityList.get(position).getCircleName());
                }
                if (!TextUtils.isEmpty(circleTalkEntityList.get(position).getLikedCounts() + "")) {
                    if (circleTalkEntityList.get(position).getLikedCounts() == 0) {
                        viewHolder.znum2.setVisibility(View.GONE);
                        viewHolder.znumText.setVisibility(View.GONE);

                    } else {
                        viewHolder.znumText.setVisibility(View.VISIBLE);
                        viewHolder.znum2.setVisibility(View.VISIBLE);
                        viewHolder.znum2.setText(circleTalkEntityList.get(position).getLikedCounts() + "");

                    }
                }

                if (!TextUtils.isEmpty(circleTalkEntityList.get(position).getCommentCounts() + "")) {
                    if (circleTalkEntityList.get(position).getCommentCounts() == 0) {
                        viewHolder.cnum2.setVisibility(View.GONE);
                        viewHolder.cnumText.setVisibility(View.GONE);
                    } else {
                        viewHolder.cnumText.setVisibility(View.VISIBLE);
                        viewHolder.cnum2.setVisibility(View.VISIBLE);
                        viewHolder.cnum2.setText(circleTalkEntityList.get(position).getCommentCounts() + "");
                    }
                }
                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(circleTalkEntityList.get(position).getUserIcon()), viewHolder.ivChHead, optionsImagh);
                if (!TextUtils.isEmpty(circleTalkEntityList.get(position).getUserSex())) {
                    if (circleTalkEntityList.get(position).getUserSex().equals(0 + "")) {
                        viewHolder.ivChGendar.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.female));
                    } else if (circleTalkEntityList.get(position).getUserSex().equals(1 + "")) {
                        viewHolder.ivChGendar.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.male));
                    }
                }

                if (!TextUtils.isEmpty(userInfoList.get(0).getPetName())) {
                    viewHolder.tvChNiName.setText(userInfoList.get(0).getPetName());
                } else {
                    viewHolder.tvChNiName.setText("暂无昵称");
                }
            }

        }

        viewHolder.ll_yue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.ll_yue.setClickable(false);
                simpleInfos = new ArrayList<SimpleInfo>();
                RequestManager.getScheduleManager().findMatchingActivities(scheduleEntity.get(position).getScheduleId(), new ResultCallback<ResultBean<List<SimpleInfo>>>() {
                    @Override
                    public void onError(int status, String errorMsg) {
                        viewHolder.ll_yue.setClickable(true);
                    }

                    @Override
                    public void onResponse(ResultBean<List<SimpleInfo>> response) {
                        viewHolder.ll_yue.setClickable(true);
                        simpleInfos = response.getData();
                        ScheduDialog dialog = new ScheduDialog(context, simpleInfos, scheduleEntity.get(position).getScheduleId(),position);
                        dialog.show();
                    }
                });

            }
        });
        viewHolder.ll_cal_guanzhu_del.setOnClickListener(new View.OnClickListener() {
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
        viewHolder.ll_cal_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启分享界面
                Intent intent = new Intent();
                intent.setClass(context, ShareDialogAct.class);
                intent.putExtra("type", 2);
                intent.putExtra("id", scheduleEntity.get(position).getScheduleId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private CircleImageView iv_icon;//头像
        private TextView tv_Name;//昵称
        private ImageView iv_gen;//性别
        private TextView tv_time_cal;//活动日期
        private TextView tv_state;//状态
//        private LinearLayout ll_cal_guanzhu;//关注数量
//        private ImageView iv_cal_guanzhu;//关注图标
//        private TextView tv_cal_text_guanzhu;//关注文本
//        private TextView tv_gzcal_num;//关注数量设置
//        private LinearLayout ll_yh;//约会
//        private ImageView iv_cal_yh;//参加头像
//        private TextView tv_cal_yh;//参加数量
        private LinearLayout ll_guanzhu;//关注
        private LinearLayout ll_yue;//约TA
//        private RelativeLayout ll_name;//个人昵称
        private LinearLayout ll_cal_guanzhu_del;
        private LinearLayout ll_cal_share;
        private ImageView iv_bag;//档期列表右边的
        private LinearLayout rl_main;//item大布局
        private ImageView ivChHead;
        private TextView tvChNiName;
        private ImageView ivChGendar;
        private TextView tvTime;
        private MyGridView iv_ch_image;
        private CustomImageView iv_oneimage;
        private TextView tv_cir_name;
        private LinearLayout ll_two;
        private ImageView iv_two_one, iv_two_two, iv_two_three, iv_two_four;
        private LinearLayout ll_like;
        private LinearLayout ll_share;
        private LinearLayout ll_comment;
        private TextView tv_content;
//        private TextView znum;
//        private TextView cnum;
//        private ImageView zimg;
//        private LinearLayout llphoto;
        private TextView znum2;
        private TextView cnum2;
        private TextView znumText;
        private TextView cnumText;
//        private ImageView iv_more;
        private RelativeLayout rl_dq;
        private LinearLayout ll_qz;
    }

    /***
     * 删除档期
     *
     * @param id
     * @param position
     */
    private void removeSchuldel(String id, final int position) {
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
        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        //获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(parent, xpos, -25);

        LinearLayout iv_shanc = (LinearLayout) layout.findViewById(R.id.iv_shanc);
        LinearLayout iv_fenxiang = (LinearLayout) layout.findViewById(R.id.iv_fenxiang);

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
                Intent intent = new Intent();
                intent.putExtra("type", 2);
                intent.putExtra("id", scheduleEntity.get(position).getScheduleId());
                intent.setClass(context, ShareDialogAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }
}
