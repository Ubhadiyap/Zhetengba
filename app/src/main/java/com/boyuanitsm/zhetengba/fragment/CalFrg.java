package com.boyuanitsm.zhetengba.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.adapter.CalAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;

/**
 * 档期界面
 * Created by xiaoke on 2016/4/24.
 */
public class CalFrg extends Fragment implements CalAdapter.IUpdateYh {
    private View view;
    private View viewHeader_calen;
    private ListView lv_calen;
    private ImageView vp_loop_calen;
    private boolean isComment3,isComment4;
    private int calgznum=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calendar_frag, null);
        //塞入item_loop_viewpager_calen，到viewpager   :view1
        viewHeader_calen = getLayoutInflater(savedInstanceState).inflate(R.layout.item_loop_viewpager_calen, null);
        lv_calen = (ListView) view.findViewById(R.id.lv_calen);
        CalAdapter adapter = new CalAdapter(getActivity(),this);
        //设置listview头部headview
        lv_calen.addHeaderView(viewHeader_calen);
        vp_loop_calen = (ImageView) view.findViewById(R.id.vp_loop_calen);
        ImageView iv_item_image = (ImageView) view.findViewById(R.id.iv_item_image);
        iv_item_image.setImageResource(R.drawable.test_banner);
        lv_calen.setAdapter(adapter);

        return view;
    }

    @Override
    public void registCalGuanZhu(int position) {
        //得到你屏幕上第一个显示的item
        int firstVisiblePosition = lv_calen.getFirstVisiblePosition();
        //得到你屏幕上最后一个显示的item
        int lastVisiblePosition = lv_calen.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            //得到你点击的item的view
            View view = lv_calen.getChildAt(position - firstVisiblePosition);
            if (view.getTag() instanceof CalAdapter.CalHolder) {
                //拿到view的Tag,强转成CommentAdapter的ViewHolder
                CalAdapter.CalHolder holder = (CalAdapter.CalHolder) view.getTag();
                if (!isComment3) {// 这里我用了一个变量来控制，点第一次时，点赞成功，点赞控件显示蓝色的图标，
                    // 点击第二次时，取消点赞，点赞控件显示灰色的图标。
                    isComment3 = true;
                    holder.iv_cal_guanzhu.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.collect_b));
                    calgznum++;
                    holder.tv_gzcal_num.setText(calgznum+"");
                    holder.tv_cal_text_guanzhu.setText("已关注");
                } else {
                    isComment3 = false;
                    holder.iv_cal_guanzhu.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.collect));
                    calgznum--;
                    holder.tv_gzcal_num.setText(calgznum+"");
                    holder.tv_cal_text_guanzhu.setText("关注");
                }
            }
        }
    }

    @Override
    public void registCalYh(int position) {

        //得到你屏幕上第一个显示的item
        int firstVisiblePosition = lv_calen.getFirstVisiblePosition();
        //得到你屏幕上最后一个显示的item
        int lastVisiblePosition = lv_calen.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            //得到你点击的item的view
            View view = lv_calen.getChildAt(position - firstVisiblePosition);
            if (view.getTag() instanceof CalAdapter.CalHolder) {
                //拿到view的Tag,强转成CommentAdapter的ViewHolder
                CalAdapter.CalHolder holder = (CalAdapter.CalHolder) view.getTag();
                if (!isComment4) {// 这里我用了一个变量来控制，点第一次时，点赞成功，点赞控件显示蓝色的图标，
                    // 点击第二次时，取消点赞，点赞控件显示灰色的图标。
                    isComment4 = true;
                    holder.iv_cal_yh.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.finger_b));
                    holder.tv_cal_yh.setText("已约");
                } else {
                    isComment4 = false;
                    holder.iv_cal_yh.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.finger));
                    holder.tv_cal_yh.setText("约Ta");
                }
            }
        }
    }
}
