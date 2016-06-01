package com.boyuanitsm.zhetengba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 圈子个人主页里面viewpage适配器
 * Created by bitch-1 on 2016/5/16.
 */
public class PpagevpAdapter extends FragmentPagerAdapter{
    private List<Fragment> list;
    private final String[] titles={"档期","圈子动态"};
    public PpagevpAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles[position];
//    }
}
