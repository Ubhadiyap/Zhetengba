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
import com.boyuanitsm.zhetengba.adapter.CalAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;

/**
 * 档期界面
 * Created by xiaoke on 2016/4/24.
 */
public class CalFrg extends Fragment {
    private View view;
    private View viewHeader_calen;
    private ListView lv_calen;
    private ImageView vp_loop_calen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calendar_frag, null);
        //塞入item_loop_viewpager_calen，到viewpager   :view1
        viewHeader_calen = getLayoutInflater(savedInstanceState).inflate(R.layout.item_loop_viewpager_calen, null);
        lv_calen = (ListView) view.findViewById(R.id.lv_calen);
        CalAdapter adapter = new CalAdapter(getActivity());
        //设置listview头部headview
        lv_calen.addHeaderView(viewHeader_calen);
        vp_loop_calen = (ImageView) view.findViewById(R.id.vp_loop_calen);
        ImageView iv_item_image = (ImageView) view.findViewById(R.id.iv_item_image);
        iv_item_image.setImageResource(R.drawable.test_banner);
        lv_calen.setAdapter(adapter);

        return view;
    }

}
