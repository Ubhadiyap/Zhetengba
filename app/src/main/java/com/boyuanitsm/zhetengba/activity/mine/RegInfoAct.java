package com.boyuanitsm.zhetengba.activity.mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.MainAct;
import com.boyuanitsm.zhetengba.activity.mess.PerpageAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.IconFilePath;
import com.boyuanitsm.zhetengba.bean.LabelBannerInfo;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.bean.UserInterestInfo;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.db.LabelInterestDao;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.fragment.MineFrg;
import com.boyuanitsm.zhetengba.fragment.circleFrg.ChanelFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyBitmapUtils;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.view.MySelfSheetDialog;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注册信息界面面
 * Created by bitch-1 on 2016/5/9.
 */
public class RegInfoAct extends BaseActivity {
    @ViewInject(R.id.et_pickname)
    private EditText et_pickname;
    @ViewInject(R.id.boy_rd)
    private RadioButton boy_rd;
    @ViewInject(R.id.gvxq)
    private MyGridView gvqy;
    @ViewInject(R.id.rg_sex)
    private RadioGroup rg_sex;
    @ViewInject(R.id.iv_rgificon)
    private CircleImageView iv_icon;

    private UserInfo user;
    private List<LabelBannerInfo> list = new ArrayList<LabelBannerInfo>();
    private List<LabelBannerInfo> idlist;//存储选中的兴趣标签
    private String lableid;


    private XqgvAdapter xqgvAdapter;//兴趣标签适配器
    private Map<Integer, String> datamap;//用来封装适配器里面选中和取消相中后的标签
    private String sex = "1";//性别选择默认为男
    private String pickname;//昵称

    private String username;
    private String pwd;
    private String yzm;
//    private UserInfo userInfo;


    private String photoSavePath;
    private String photoSaveName;

    private ProgressDialog pd;
    Uri imageUri = null;
    public static final int PHOTOZOOM = 0;
    public static final int PHOTOTAKE = 1;
    public static final int IMAGE_COMPLETE = 2; // 结果


    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ellipse) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ellipse) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ellipse) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    @Override
    public void setLayout() {
        setContentView(R.layout.act_reginfo);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("注册信息");
        datamap = new HashMap<>();
        pd=new ProgressDialog(RegInfoAct.this);
//        userInfo=new UserInfo();
        user = UserInfoDao.getUser();
        MyLogUtils.info("测试user" + user);
        idlist = new ArrayList<>();
        getIntrestLabel("0");//获得全部兴趣标签
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.girl_rd:
                        sex = "0";
                        break;
                    case R.id.boy_rd:
                        sex = "1";
                        break;
                }

            }
        });

    }

    /**
     * 个人兴趣标签/全部标签
     *
     * @param dictType
     * @return
     */
    private void getIntrestLabel(String dictType) {
        RequestManager.getScheduleManager().getIntrestLabelList(dictType, new ResultCallback<ResultBean<List<LabelBannerInfo>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<List<LabelBannerInfo>> response) {
                list = response.getData();
                xqgvAdapter = new XqgvAdapter();
                gvqy.setAdapter(xqgvAdapter);
            }
        });
    }


    @OnClick({R.id.wancheng, R.id.iv_rgificon})
    public void OnClick(View view) {
        pickname = et_pickname.getText().toString().trim();//昵称
        if (!TextUtils.isEmpty(pickname) && pickname != null) {
            user.setPetName(pickname);
        }
        user.setSex(sex);
        switch (view.getId()) {
            case R.id.iv_rgificon://点击图像
                headIconDialog();
                break;

            case R.id.wancheng://完成
                if (TextUtils.isEmpty(pickname)) {
                    MyToastUtils.showShortToast(RegInfoAct.this, "请输入昵称");
                    et_pickname.requestFocus();
                    return;
                }
                if(idlist.size()==0){
                    MyToastUtils.showShortToast(RegInfoAct.this,"至少选择一个兴趣标签！");
                    return;
                }

                if (idlist.size() == 1) {
                    lableid = idlist.get(0).getId();
//                        doPerfect(user,lableid);
                }
                if (idlist.size() > 1) {
                    lableid = idlist.get(0).getId();
                    for (int i = 1; i < idlist.size(); i++) {
                        lableid = lableid + "," + idlist.get(i).getId();
                    }

                }
                pd.setMessage("完善中....");
                pd.show();
                doPerfect(user, lableid,idlist);
//                addInterestLabel(lableid,idlist);

                break;


        }

    }

    /**
     * 完善个人信息
     *  @param user
     * @param labelIds
     * @param idlist
     */

    private void doPerfect(final UserInfo user, final String labelIds, final List<LabelBannerInfo> idlist) {
        RequestManager.getUserManager().perfect(user, labelIds, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                pd.dismiss();
                if (idlist.size()>0){
                    for (int i=0;i<idlist.size();i++){
                        UserInterestInfo userInterestInfo=new UserInterestInfo();
                        userInterestInfo.setInterestId(idlist.get(i).getId());
                        userInterestInfo.setDictName(idlist.get(i).getDictName());
                        LabelInterestDao.saveInterestLabel(userInterestInfo);
                    }
                }
                UserInfoDao.updateUser(user);
                if (!TextUtils.isEmpty(user.getPetName()))
                    DemoHelper.getInstance().getUserProfileManager().setNickName(user.getPetName());
                DemoHelper.getInstance().getUserProfileManager().setUserAvatar(Uitls.imageFullUrl(user.getIcon()));
//                MyToastUtils.showShortToast(getApplicationContext(),"成功");
                openActivity(MainAct.class);
                finish();
            }
        });
    }
    /**
     * 添加个人兴趣标签
     * @param labelIds
     * @param mylist
     */
    private void addInterestLabel(final String labelIds, final List<UserInterestInfo> mylist){
        RequestManager.getScheduleManager().addInterestLabel(labelIds, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                LabelInterestDao.delAll();
                for (int i = 0; i < mylist.size(); i++) {
                    UserInterestInfo userInterestInfo = new UserInterestInfo();
                    userInterestInfo.setInterestId(mylist.get(i).getInterestId());
                    userInterestInfo.setDictName(mylist.get(i).getDictName());
                    LabelInterestDao.saveInterestLabel(userInterestInfo);
                }
                MyLogUtils.info(LabelInterestDao.getInterestLabel().toString());
                sendBroadcast(new Intent(ChanelFrg.MYLABELS));
                sendBroadcast(new Intent(MineFrg.USER_INFO));
                sendBroadcast(new Intent(PerpageAct.PPLABELS));
                finish();
            }
        });
    }

    /**
     * 添加图像
     */
    private void headIconDialog() {
        MySelfSheetDialog dialog = new MySelfSheetDialog(RegInfoAct.this);
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
                    MyToastUtils.showShortToast(RegInfoAct.this, "存储卡不存在");
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
                iv_icon.setImageBitmap(MyBitmapUtils.LoadBigImg(temppath, 120, 120));
                toloadfile(temppath);
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
        RequestManager.getUserManager().subHeadImg(filemap, new ResultCallback<ResultBean<IconFilePath>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<IconFilePath> response) {
                user.setIcon(response.getData().getIconFilePath());
                UserInfoDao.updateUser(user);
//                ImageLoader.getInstance().displayImage(Uitls.imageFullUrl(response.getData().getIconFilePath()), head, options);
            }
        });
    }

    /**
     * 标签
     * gridview适配器
     */
    class XqgvAdapter extends BaseAdapter {

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
            final int dex = position;
            View view = View.inflate(RegInfoAct.this, R.layout.item_gv, null);
            final CheckBox ck = (CheckBox) view.findViewById(R.id.ck_xq);
//            if (position == 0) {
//                ck.setText("吐槽");
//                ck.setChecked(true);
//                ck.setClickable(false);
//            } else {
                ck.setText(list.get(position).getDictName());
                ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            idlist.add(list.get(position));

                        } else {
                            idlist.remove(list.get(position));
                        }
                    }
                });
//            }


//            if(position==0){ck.setText("吐槽");ck.isChecked();ck.setClickable(false);}
//            else{
//            ck.setText(list.get(position).getDictName());
//            ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//             if(isChecked){
//                 idlist.add(list.get(position).getId());
//             }else {
//                 idlist.remove(list.get(position).getId());
//             }
//            }
//        });
//            }

            return view;
        }

    }


}
