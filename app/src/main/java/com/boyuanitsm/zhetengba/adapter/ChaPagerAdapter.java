package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道--标签下--viewpager
 * viewpagerAdapter
 * Created by xiaoke on 2016/5/3.
 */
public class ChaPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private Context context;
    public ChaPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(ArrayList<Fragment> fragmentList){
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentList.get(arg0);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
