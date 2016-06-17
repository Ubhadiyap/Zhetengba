package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.boyuanitsm.zhetengba.fragment.circleFrg.ChaChildFrg;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道--标签下--viewpager
 * viewpagerAdapter
 * Created by xiaoke on 2016/5/3.
 */
public class ChaPagerAdapter extends FragmentPagerAdapter {
    private List<ChaChildFrg> fragmentList;
    private FragmentManager fm;
    private Context context;
    public ChaPagerAdapter(FragmentManager fm, ArrayList<ChaChildFrg> fragmentList) {
        super(fm);
        this.fm=fm;
        this.fragmentList=fragmentList;
    }

    public void setFragments(ArrayList<ChaChildFrg> fragments) {
        if(this.fragmentList != null){
            FragmentTransaction ft = fm.beginTransaction();
            for(Fragment f:this.fragmentList){
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            ft=null;
            fm.executePendingTransactions();
        }
        this.fragmentList = fragments;
        notifyDataSetChanged();
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
