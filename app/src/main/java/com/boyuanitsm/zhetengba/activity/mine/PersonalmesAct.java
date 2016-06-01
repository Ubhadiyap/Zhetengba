package com.boyuanitsm.zhetengba.activity.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.utils.MyBitmapUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.view.CommonView;
import com.boyuanitsm.zhetengba.view.MySelfSheetDialog;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人资料界面
 * Created by bitch-1 on 2016/5/3.
 */
public class PersonalmesAct extends BaseActivity {
    @ViewInject(R.id.rl_headIcon)
    private RelativeLayout headIcon;
    @ViewInject(R.id.cv_userName)
    private CommonView cvUserName;
    @ViewInject(R.id.cv_sex)
    private CommonView cvSex;
    @ViewInject(R.id.cv_phoneNum)
    private CommonView cvPhoneNum;
    @ViewInject(R.id.cv_email)
    private CommonView cvEmail;
    @ViewInject(R.id.cv_companyName)
    private CommonView cvCompanyName;
    @ViewInject(R.id.cv_companyAdd)
    private CommonView cvCompanyAdd;
    @ViewInject(R.id.cv_companyTel)
    private CommonView cvCompanyTel;
    @ViewInject(R.id.cv_business)
    private CommonView cvBusiness;
    @ViewInject(R.id.cv_homeTown)
    private CommonView cvHomeTown;

    private String photoSavePath;
    private String photoSaveName;
    Uri imageUri = null;
    public static final int PHOTOZOOM = 0;
    public static final int PHOTOTAKE = 1;
    public static final int IMAGE_COMPLETE = 2; // 结果

    private MyReceiver myReceiver;


    @Override
    public void setLayout() {
        setContentView(R.layout.act_personalmes);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("个人资料");
        UserInfo userInfo = UserInfoDao.getUser();
        if (userInfo != null) {
            if ((TextUtils.isEmpty(userInfo.getName()))) {
                cvUserName.setNotesText(userInfo.getName());}

            if ((TextUtils.isEmpty(userInfo.getSex()))) {
                    cvSex.setNotesText(userInfo.getSex());
                }
            if ((TextUtils.isEmpty(userInfo.getPhone()))) {
                cvPhoneNum.setNotesText(userInfo.getPhone());
            }
            if ((TextUtils.isEmpty(userInfo.getEmail()))) {
                cvEmail.setNotesText(userInfo.getEmail());
            }

            if ((TextUtils.isEmpty(userInfo.getCompanyName()))) {
                cvCompanyName.setNotesText(userInfo.getCompanyName());
            }

            if ((TextUtils.isEmpty(userInfo.getCompanyAddr()))) {
                cvCompanyAdd.setNotesText(userInfo.getCompanyAddr());
            }
            if ((TextUtils.isEmpty(userInfo.getCompanyPhone()))) {
                cvCompanyTel.setNotesText(userInfo.getCompanyPhone());
            }
            if ((TextUtils.isEmpty(userInfo.getJob()))) {
                cvBusiness.setNotesText(userInfo.getJob());
            }
            if ((TextUtils.isEmpty(userInfo.getHomeTown()))) {
                cvHomeTown.setNotesText(userInfo.getHomeTown());
            }

            }

        }

        @OnClick({R.id.rl_headIcon, R.id.cv_userName, R.id.cv_sex, R.id.cv_phoneNum, R.id.cv_email, R.id.cv_companyName, R.id.cv_companyAdd, R.id.cv_companyTel, R.id.cv_business, R.id.cv_homeTown})
        public void todo (View view){
            Intent intent = null;
            switch (view.getId()) {
                case R.id.rl_headIcon:
                    headIconDialog();
                    break;
                case R.id.cv_userName://昵称
                    intent = new Intent(this, EditAct.class);
                    intent.putExtra(EditAct.USER_TYPE, 1);
                    startActivity(intent);
                    break;
                case R.id.cv_sex://性别

                    break;
                case R.id.cv_phoneNum://手机号码
                    intent = new Intent(this, EditAct.class);
                    intent.putExtra(EditAct.USER_TYPE, 2);
                    startActivity(intent);
                    break;
                case R.id.cv_email://邮箱
                    intent = new Intent(this, EditAct.class);
                    intent.putExtra(EditAct.USER_TYPE, 3);
                    startActivity(intent);
                    break;
                case R.id.cv_companyName://公司名称
                    intent = new Intent(this, EditAct.class);
                    intent.putExtra(EditAct.USER_TYPE, 4);
                    startActivity(intent);
                    break;
                case R.id.cv_companyAdd://公司地址
                    intent = new Intent(this, EditAct.class);
                    intent.putExtra(EditAct.USER_TYPE, 5);
                    startActivity(intent);
                    break;
                case R.id.cv_companyTel://公司电话
                    intent = new Intent(this, EditAct.class);
                    intent.putExtra(EditAct.USER_TYPE, 6);
                    startActivity(intent);
                    break;
                case R.id.cv_business://职务
                    intent = new Intent(this, EditAct.class);
                    intent.putExtra(EditAct.USER_TYPE, 7);
                    startActivity(intent);
                    break;
                case R.id.cv_homeTown://故乡
                    break;
            }
        }

    private void headIconDialog() {
        MySelfSheetDialog dialog = new MySelfSheetDialog(PersonalmesAct.this);
        dialog.builder().addSheetItem("拍照", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
//                        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(openCameraIntent, PHOTOTAKE);
                } else {
                    MyToastUtils.showShortToast(PersonalmesAct.this, "存储卡不存在");
                }

            }
        }).addSheetItem("从相册选取", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(openAlbumIntent, PHOTOZOOM);
            }
        }).show();
    }

    /**
     * 返回的Path
     */
    private String temppath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri uri = null;
        Intent intent = null;
        switch (requestCode) {
            case PHOTOZOOM:// 相册
                if (resultCode != RESULT_OK) {
                    return;
                }
                if (data == null) {
                    return;
                }
                uri = data.getData();
                Bitmap userbitmap = MyBitmapUtils.decodeUriAsBitmap(this, uri);
                File user_head = MyBitmapUtils.saveBitmap(MyBitmapUtils.zoomImg(userbitmap, 100, 100), "user_head.png");
                intent = new Intent(this, ClipActivity.class);
                intent.putExtra("path", Environment.getExternalStorageDirectory() + "/" + "user_head.png");
                startActivityForResult(intent, IMAGE_COMPLETE);
                break;
            case PHOTOTAKE:// 拍照
                if (resultCode != RESULT_OK) {
                    return;
                }
                String path = photoSavePath + photoSaveName;
                Intent intent2 = new Intent(this, ClipActivity.class);
                intent2.putExtra("path", path);
                startActivityForResult(intent2, IMAGE_COMPLETE);
                break;

            case IMAGE_COMPLETE:// 完成
                temppath = data.getStringExtra("path");
                toloadfile(temppath);
//                ivHead.setImageBitmap(getimage(temppath));

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 上传头像
     *
     * @param path
     */
    private void toloadfile(String path) {
        Map<String, FileBody> filemap = new HashMap<String, FileBody>();
        File file = new File(path);
        FileBody fileBody = new FileBody(file);
        filemap.put("file", fileBody);

    }

    public static final String USER_INFO = "com.update.user";

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        UserInfo userInfo=UserInfoDao.getUser();
            if (userInfo != null) {
                if ((TextUtils.isEmpty(userInfo.getName()))) {
                    cvUserName.setNotesText(userInfo.getName());}

                if ((TextUtils.isEmpty(userInfo.getSex()))) {
                    cvSex.setNotesText(userInfo.getSex());
                }
                if ((TextUtils.isEmpty(userInfo.getPhone()))) {
                    cvPhoneNum.setNotesText(userInfo.getPhone());
                }
                if ((TextUtils.isEmpty(userInfo.getEmail()))) {
                    cvEmail.setNotesText(userInfo.getEmail());
                }

                if ((TextUtils.isEmpty(userInfo.getCompanyName()))) {
                    cvCompanyName.setNotesText(userInfo.getCompanyName());
                }

                if ((TextUtils.isEmpty(userInfo.getCompanyAddr()))) {
                    cvCompanyAdd.setNotesText(userInfo.getCompanyAddr());
                }
                if ((TextUtils.isEmpty(userInfo.getCompanyPhone()))) {
                    cvCompanyTel.setNotesText(userInfo.getCompanyPhone());
                }
                if ((TextUtils.isEmpty(userInfo.getJob()))) {
                    cvBusiness.setNotesText(userInfo.getJob());
                }
                if ((TextUtils.isEmpty(userInfo.getHomeTown()))) {
                    cvHomeTown.setNotesText(userInfo.getHomeTown());
                }

            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (myReceiver==null) {
            myReceiver = new MyReceiver();
        }
        registerReceiver(myReceiver, new IntentFilter(USER_INFO));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver!=null){
            unregisterReceiver(myReceiver);
        }
    }
}
