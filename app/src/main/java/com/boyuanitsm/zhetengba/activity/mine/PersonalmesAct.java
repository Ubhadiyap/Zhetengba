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
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
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

    public static final int SEXMODIFY_GO = 200;//选择性别
    public static final int SEXMODIFY_BAKC = 201;//性别 resultcode 201

    private MyReceiver myReceiver;
    private UserInfo user;


    @Override
    public void setLayout() {
        setContentView(R.layout.act_personalmes);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("个人资料");
        user = UserInfoDao.getUser();
        MyLogUtils.degug("user"+user);
        if (user != null) {
            if (!(TextUtils.isEmpty(user.getPetName()))) {
                MyLogUtils.degug("hah"+user);
                MyLogUtils.degug(user.getPetName());
                cvUserName.setNotesText(user.getPetName());}

            if (!(TextUtils.isEmpty(user.getSex()))) {
                    cvSex.setNotesText(user.getSex());
                }
            if (!(TextUtils.isEmpty(user.getPhone()))) {
                cvPhoneNum.setNotesText(user.getPhone());
            }
            if (!(TextUtils.isEmpty(user.getEmail()))) {
                cvEmail.setNotesText(user.getEmail());
            }

            if (!(TextUtils.isEmpty(user.getCompanyName()))) {
                cvCompanyName.setNotesText(user.getCompanyName());
            }

            if (!(TextUtils.isEmpty(user.getCompanyAddr()))) {
                cvCompanyAdd.setNotesText(user.getCompanyAddr());
            }
            if (!(TextUtils.isEmpty(user.getCompanyPhone()))) {
                cvCompanyTel.setNotesText(user.getCompanyPhone());
            }
            if (!(TextUtils.isEmpty(user.getJob()))) {
                cvBusiness.setNotesText(user.getJob());
            }
            if (!(TextUtils.isEmpty(user.getHomeTown()))) {
                cvHomeTown.setNotesText(user.getHomeTown());
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
//                    openActivity(SelectSexAct.class);
                    Intent intent1=new Intent(PersonalmesAct.this,SelectSexAct.class);
                    intent1.putExtra("user",user);
                    startActivityForResult(intent1,SEXMODIFY_GO);
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

            case SEXMODIFY_GO://修改性别返回
                if (resultCode == SEXMODIFY_BAKC) {
                    if (data != null) {
                        String sex=data.getStringExtra("Modify");
                        if(sex.equals("女")){
                            cvSex.setNotesText("女");
                            user.setSex("女");
                        }
                        if(sex.equals("男")){
                            cvSex.setNotesText("男");
                            user.setSex("男");
                        }

                    }
                }

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
        UserInfo user=UserInfoDao.getUser();
            MyLogUtils.degug(user.getPetName());
            if (user != null) {
                if (user.getPetName()!=null) {
                    cvUserName.setNotesText(user.getPetName());}

//                if (user.getSex()!=null){
//                    cvSex.setNotesText(user.getSex());
//                }
                if (user.getPhone()!=null) {
                    cvPhoneNum.setNotesText(user.getPhone());
                }
                if (user.getEmail()!=null) {
                    cvEmail.setNotesText(user.getEmail());
                }

                if (user.getCompanyName()!=null) {
                    cvCompanyName.setNotesText(user.getCompanyName());
                }

                if (user.getCompanyAddr()!=null) {
                    cvCompanyAdd.setNotesText(user.getCompanyAddr());
                }
                if (user.getCompanyPhone()!=null) {
                    cvCompanyTel.setNotesText(user.getCompanyPhone());
                }
                if (user.getJob()!=null) {
                    cvBusiness.setNotesText(user.getJob());
                }
                if (user.getHomeTown()!=null) {
                    cvHomeTown.setNotesText(user.getHomeTown());
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
