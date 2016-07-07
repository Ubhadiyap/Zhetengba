package com.boyuanitsm.zhetengba.activity.circle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

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
import com.boyuanitsm.zhetengba.fragment.circleFrg.ChanelFrg;
import com.boyuanitsm.zhetengba.fragment.circleFrg.CirFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
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
    private EditText etContent;
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
        pd=new ProgressDialog(CirclefbAct.this);
        pd.setMessage("发布中...");
        pd.setCanceledOnTouchOutside(false);

        isShow=getIntent().getBooleanExtra("isShow", false);
        circleId=getIntent().getStringExtra("circleId");
        labelId=getIntent().getStringExtra("labelId");
        type=getIntent().getIntExtra(TYPE, 1);
        flag=getIntent().getIntExtra("flag",0);
        entity=new CircleEntity();
        channelTalkEntity=new ChannelTalkEntity();
        setRight("发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRightEnable(false);
                content=etContent.getText().toString().trim();
                pd.show();
                switch (type){
                    case 0:
                        if(!TextUtils.isEmpty(content)) {
                            channelTalkEntity.setLabelId(labelId);
                            channelTalkEntity.setChannelContent(content);
                            if (selecteds.size()>0) {
                                upLoadImg(selecteds);
                            }else {
                                addChannelTalk(channelTalkEntity);
                            }
                        }else {
                            MyToastUtils.showShortToast(CirclefbAct.this,"请输入频道说说内容");
                        }
                       break;
                    default:
                        if(!TextUtils.isEmpty(content)) {
                            entity.setTalkContent(content);
                            if (selecteds.size()>0) {
                                upLoadImg(selecteds);
                            }else {
                                addCircleTalk(entity, circleId);
                            }
                        }else {
                            MyToastUtils.showShortToast(CirclefbAct.this,"请输入圈子说说内容");
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
                setRightEnable(true);

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                pd.dismiss();
                setRightEnable(true);
                finish();
                sendBroadcast(new Intent(CirxqAct.TALKS));
                sendBroadcast(new Intent(CirFrg.ALLTALKS));
            }
        });
    }

    //发布频道说说
    private void addChannelTalk(ChannelTalkEntity channelTalkEntity){
        RequestManager.getTalkManager().addChannelTalk(channelTalkEntity, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                setRightEnable(true);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                Intent intent=new Intent(ChanelFrg.MYLABELS);
                Bundle bundle=new Bundle();
                bundle.putInt("flag", flag);
                intent.putExtras(bundle);
                sendBroadcast(intent);
                pd.dismiss();
                setRightEnable(true);
                finish();
            }
        });
    }

    //上传图片
    private void upLoadImg(List<ImageBean> selecteds){
        Map<String, FileBody> fileMaps = new HashMap<String,FileBody>();
        for (int i = 0; i < selecteds.size(); i++) {
            FileBody fb = new FileBody(new File(selecteds.get(i).getPath()));
            fileMaps.put(i+"", fb);
        }
        strList=new ArrayList<>();
        RequestManager.getTalkManager().upLoadImg(fileMaps, new ResultCallback<ResultBean<ImgBean<String>>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<ImgBean<String>> response) {
                strList=response.getData().getBigImgPaths();
                imgStr=new StringBuilder();
                if (strList.size()>0){
                    if (strList.size()==1){
                        imgStr.append(strList.get(0));
                    }else {
                        for (int i=0;i<strList.size();i++){
                            imgStr.append(strList.get(i));
                            imgStr.append(",");
                        }
                    }
                }
                switch (type){
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
