package com.boyuanitsm.zhetengba.activity.circle;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RemoteViews;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.photo.PicSelectActivity;
import com.boyuanitsm.zhetengba.adapter.CirfbAdapter;
import com.boyuanitsm.zhetengba.adapter.GvPhotoAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.bean.ImageBean;
import com.boyuanitsm.zhetengba.bean.ImgBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.fragment.circleFrg.ChanelItemFrg;
import com.boyuanitsm.zhetengba.fragment.circleFrg.CirFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyBitmapUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.view.CanotEmojEditText;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 圈子发布界面
 * Created by bitch-1 on 2016/5/9.
 */
public class CirclefbAct extends BaseActivity {
    @ViewInject(R.id.gv_qzfb)//圈子发布界面gridview
    private MyGridView gv_qzfb;
    private CircleEntity entity;
    @ViewInject(R.id.etContent)
    private CanotEmojEditText etContent;
    private String content;
    private StringBuilder imgStr;//图片地址
    private ProgressDialog pd;

    @ViewInject(R.id.my_gv)
    private com.leaf.library.widget.MyGridView gvPhoto;//添加图片gridview

    /**
     * 照片List
     */
    private List<ImageBean> selecteds = new ArrayList<ImageBean>();
    private GvPhotoAdapter adapter;

    private boolean isShow=true;
    private String circleId;
    public static String TYPE="type";
    private int type;//0 频道说说 1圈子说说
    private ChannelTalkEntity channelTalkEntity;
    private String labelId;//频道标签id
    private int flag;
    private List<String> strList;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_circlefb);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle(UserInfoDao.getUser().getPetName());
        isShow=getIntent().getBooleanExtra("isShow", false);
        circleId=getIntent().getStringExtra("circleId");
        labelId=getIntent().getStringExtra("labelId");
        type=getIntent().getIntExtra(TYPE, 1);
//        flag=getIntent().getIntExtra("flag", 0);
        entity=new CircleEntity();
        pd=new ProgressDialog(CirclefbAct.this);
        pd.setMessage("发布中...");
        pd.setCanceledOnTouchOutside(false);
        channelTalkEntity=new ChannelTalkEntity();
        setRight("发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                notifyKJ();
//                pd.show();
//                setRightEnable(false);
                content = etContent.getText().toString().trim();
                switch (type) {
                    case 0:
                        if (!TextUtils.isEmpty(content)) {
                            finish();
                            notifyTZ();
                            channelTalkEntity.setLabelId(labelId);
                            channelTalkEntity.setChannelContent(content);
                            if (selecteds.size() > 0) {
                                upLoadImg(selecteds);
                            } else {
//                                pd.show();
                                addChannelTalk(channelTalkEntity);
                            }
                        } else {
                            if (selecteds.size() > 0) {
                                finish();
                                notifyTZ();
//                                pd.show();
                                channelTalkEntity.setLabelId(labelId);
                                upLoadImg(selecteds);
                            } else {
//                                pd.dismiss();
                                MyToastUtils.showShortToast(getApplicationContext(), "频道说说内容不能为空！");
                                return;
                            }

                        }
                        break;
                    default:
                        if (!TextUtils.isEmpty(content)) {
                            finish();
                            notifyTZ();
                            entity.setTalkContent(content);
                            if (selecteds.size() > 0) {
                                upLoadImg(selecteds);
                            } else {
//                                pd.show();
                                addCircleTalk(entity, circleId);
                            }
                        } else {
                            if (selecteds.size() > 0) {
//                                pd.show();
                                finish();
                                notifyTZ();
                                entity.setTalkContent("");
                                upLoadImg(selecteds);
                            } else {
//                                pd.dismiss();
                                MyToastUtils.showShortToast(getApplicationContext(), "圈子说说内容不能空！");
                                return;
                            }
                        }
                        break;
                }

            }
        });
        if(isShow) {
            final CirfbAdapter adapter1 = new CirfbAdapter(CirclefbAct.this);
            gv_qzfb.setAdapter(adapter1);
            gv_qzfb.setSelector(new ColorDrawable(Color.TRANSPARENT));
            gv_qzfb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    adapter1.clearSelection(position);
                    //关键是这一句，激情了，它可以让listview改动过的数据重新加载一遍，以达到你想要的效果
                    adapter1.notifyDataSetChanged();
                    String text = adapter1.getItem(position).toString();
                    MyToastUtils.showShortToast(CirclefbAct.this, text);
                }
            });
        }
        adapter = new GvPhotoAdapter(selecteds, 9, CirclefbAct.this);
        gvPhoto.setAdapter(adapter);

    }

    private void notifyTZ() {
        Bitmap btm = BitmapFactory.decodeResource(getResources(),
                R.drawable.logo);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                getApplicationContext())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("发布中...");
        mBuilder.setTicker("发布中...");//第一次提示消息的时候显示在通知栏上
        mBuilder.setLargeIcon(btm);
                mBuilder.setAutoCancel(true);//自己维护通知的消失
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Intent intent = new Intent(CreditSecondFrag.IMAGE);
        /**第一次添加照片 */
        if (requestCode == 0x123 && resultCode == RESULT_OK) {
            if (data != null) {
                gvPhoto.setVisibility(View.VISIBLE);
                selecteds = (List<ImageBean>) data.getSerializableExtra(PicSelectActivity.IMAGES);
                if (adapter == null) {
                    adapter = new GvPhotoAdapter(selecteds, 3, CirclefbAct.this);
                    gvPhoto.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        }

        /**继续添加照片 */
        if (requestCode == GvPhotoAdapter.ADD_PHOTO_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                List<ImageBean> addSelect = (List<ImageBean>) data.getSerializableExtra(PicSelectActivity.IMAGES);
                selecteds.addAll(addSelect);
                adapter.notifyDataSetChanged();
            }
        }
        /**预览删除照片*/
        if (requestCode == GvPhotoAdapter.PHOTO_CODE0 && resultCode == RESULT_OK) {
            if (data != null) {
                if (selecteds.size() > 0) {
                    selecteds = (List<ImageBean>) data.getSerializableExtra("M_LIST");
                    adapter.notifyDataChange(selecteds);
                }
            }
        }

    }

    /**
     * 发布圈子说说
     * @param circleEntity
     * @param circleId
     */
    private void addCircleTalk(CircleEntity circleEntity,String circleId){
        RequestManager.getTalkManager().addCircleTalk(circleEntity, circleId, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
//                setRightEnable(true);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                mNotificationManager.cancel(0);
                Bitmap btm = BitmapFactory.decodeResource(getResources(),
                        R.drawable.logo);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        getApplicationContext())
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("发布失败");
                mBuilder.setTicker("发布失败");//第一次提示消息的时候显示在通知栏上
                mBuilder.setLargeIcon(btm);
                mBuilder.setAutoCancel(true);//自己维护通知的消失
                mNotificationManager.notify(0, mBuilder.build());
                MyToastUtils.showShortToast(getApplicationContext(), "发布失败，请检查网络！");
//                mNotificationManager.cancel(0);

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                mNotificationManager.cancel(0);
                Bitmap btm = BitmapFactory.decodeResource(getResources(),
                        R.drawable.logo);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        getApplicationContext())
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("发布成功");
                mBuilder.setTicker("发布成功");//第一次提示消息的时候显示在通知栏上
                mBuilder.setLargeIcon(btm);
                mBuilder.setAutoCancel(true);//自己维护通知的消失
                mNotificationManager.notify(0, mBuilder.build());
                mNotificationManager.cancel(0);
//                pd.dismiss();
//                setRightEnable(true);
//                finish();
                sendBroadcast(new Intent(CirxqAct.TALKS));
                sendBroadcast(new Intent(CircleAct.ALLTALKS));
            }
        });
    }

    //发布频道说说
    private void addChannelTalk(final ChannelTalkEntity channelTalkEntity){
        RequestManager.getTalkManager().addChannelTalk(channelTalkEntity, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
//                pd.dismiss();
//                setRightEnable(true);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                mNotificationManager.cancel(0);
                Bitmap btm = BitmapFactory.decodeResource(getResources(),
                        R.drawable.logo);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        getApplicationContext())
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("发布失败");
                mBuilder.setTicker("发布失败");//第一次提示消息的时候显示在通知栏上
                mBuilder.setLargeIcon(btm);
                mBuilder.setAutoCancel(true);//自己维护通知的消失
                mNotificationManager.notify(0, mBuilder.build());
//                mNotificationManager.cancel(0);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                mNotificationManager.cancel(0);
                Bitmap btm = BitmapFactory.decodeResource(getResources(),
                        R.drawable.logo);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        getApplicationContext())
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("发布成功");
                mBuilder.setTicker("发布成功");//第一次提示消息的时候显示在通知栏上
                mBuilder.setLargeIcon(btm);
                mBuilder.setAutoCancel(true);//自己维护通知的消失
                mNotificationManager.notify(0, mBuilder.build());
                mNotificationManager.cancel(0);
//                Intent intent=new Intent(SquareAct.TALK_LIST);
//                Bundle bundle=new Bundle();
//                bundle.putParcelable("channelTalkEntity",channelTalkEntity);
//                intent.putExtras(bundle);
                getApplicationContext().sendBroadcast(new Intent(SquareAct.TALK_LIST));

//                pd.dismiss();
//                setRightEnable(true);
//                finish();
            }
        });
    }

    //上传图片
    private void upLoadImg(final List<ImageBean> selecteds){
        pd.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=handler.obtainMessage();
                message.what=0;
                handler.sendMessage(message);
            }
        }).start();

    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Map<String, FileBody> fileMaps = new HashMap<String,FileBody>();
                    for (int i = 0; i < selecteds.size(); i++) {
                        Bitmap bitmap= MyBitmapUtils.getSmallBitmap(selecteds.get(i).getPath());
                        if (bitmap==null){
                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    mNotificationManager.cancel(0);
                            MyToastUtils.showShortToast(CirclefbAct.this, "选择图片有误，请重新发布！");
                            return;
                        }
                        File file = MyBitmapUtils.saveBitmap(bitmap, selecteds.get(i).path);
                        FileBody fb = new FileBody(file);
                        fileMaps.put(i+"", fb);
                    }
                    toUpLoadImage(fileMaps);
                    break;
            }
            super.handleMessage(msg);
//            Map<String, FileBody> fileMaps= (Map<String, FileBody>) msg.obj;
        }
    };

    private void toUpLoadImage(Map<String, FileBody> fileMaps){
        RequestManager.getTalkManager().upLoadImg(fileMaps, new ResultCallback<ResultBean<ImgBean<String>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                pd.dismiss();
            }

            @Override
            public void onResponse(ResultBean<ImgBean<String>> response) {
                strList = response.getData().getBigImgPaths();
                imgStr = new StringBuilder();
                if (strList.size() > 0) {
                    if (strList.size() == 1) {
                        imgStr.append(strList.get(0));
                    } else {
                        for (int i = 0; i < strList.size(); i++) {
                            imgStr.append(strList.get(i));
                            imgStr.append(",");
                        }
                        imgStr.deleteCharAt(imgStr.length() - 1);
                    }
                }
                switch (type) {
                    case 0:
                        channelTalkEntity.setChannelImage(imgStr.toString());
                        addChannelTalk(channelTalkEntity);
                        break;
                    default:
                        entity.setTalkImage(imgStr.toString());
                        addCircleTalk(entity, circleId);
                        break;
                }
            }
        });
    }



}
