package com.boyuanitsm.zhetengba.activity.circle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.photo.PicSelectActivity;
import com.boyuanitsm.zhetengba.adapter.CirfbAdapter;
import com.boyuanitsm.zhetengba.adapter.GvPhotoAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ImageBean;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子发布界面
 * Created by bitch-1 on 2016/5/9.
 */
public class CirclefbAct extends BaseActivity {
    @ViewInject(R.id.gv_qzfb)//圈子发布界面gridview
    private MyGridView gv_qzfb;

    @ViewInject(R.id.my_gv)
    private com.leaf.library.widget.MyGridView gvPhoto;//添加图片gridview

    /**
     * 照片List
     */
    private List<ImageBean> selecteds = new ArrayList<ImageBean>();
    private GvPhotoAdapter adapter;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_circlefb);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("Alic");
        setRight("发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final CirfbAdapter adapter1=new CirfbAdapter(CirclefbAct.this);
        gv_qzfb.setAdapter(adapter1);
        gv_qzfb.setSelector(R.color.transparent);
        gv_qzfb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              adapter1.clearSelection(position);
                //关键是这一句，激情了，它可以让listview改动过的数据重新加载一遍，以达到你想要的效果
                adapter1.notifyDataSetChanged();
            }
        });
//        gv_qzfb.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        gv_qzfb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                adapter1.setSeclection(position);
//                adapter1.notifyDataSetChanged();
//            }
//        });
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
//                intent.putExtra(PicSelectActivity.IMAGES, (Serializable) selecteds);
//                sendBroadcast(intent);
//                tvSelect.setVisibility(View.GONE);
            }
        }

        /**继续添加照片 */
        if (requestCode == GvPhotoAdapter.ADD_PHOTO_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                List<ImageBean> addSelect = (List<ImageBean>) data.getSerializableExtra(PicSelectActivity.IMAGES);
                selecteds.addAll(addSelect);
                adapter.notifyDataSetChanged();
//                intent.putExtra(PicSelectActivity.IMAGES, (Serializable) selecteds);
//                sendBroadcast(intent);
            }
        }
        /**预览删除照片*/
        if (requestCode == GvPhotoAdapter.PHOTO_CODE0 && resultCode == RESULT_OK) {
            if (data != null) {
                if (selecteds.size() > 0) {
                    selecteds = (List<ImageBean>) data.getSerializableExtra("M_LIST");
                    adapter.notifyDataChange(selecteds);
//                    intent.putExtra(PicSelectActivity.IMAGES, (Serializable) selecteds);
//                    sendBroadcast(intent);
                }
            }
        }

    }

}
