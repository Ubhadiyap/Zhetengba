package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.boyuanitsm.zhetengba.bean.UserInterestInfo;
import com.boyuanitsm.zhetengba.fragment.circleFrg.ChanelItemFrg;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoke on 2016/8/1.
 */
public class ChanelPageAdapter extends FragmentStatePagerAdapter {
    private List<UserInterestInfo> list=new ArrayList<>();
    private Context context;
    private int currentPos;
    public ChanelPageAdapter(FragmentManager fm, Context context, List<UserInterestInfo> titleList, int currentPos) {
        super(fm);
        this.context=context;
        this.list=titleList;
        this.currentPos=currentPos;
    }
    public void  updata(Context context,List<UserInterestInfo> list){
        this.context=context;
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Fragment getItem(int i) {
        ChanelItemFrg chanelfrg = new ChanelItemFrg();
        Bundle bundle = new Bundle();
        bundle.putString(ChanelItemFrg.TITLE_LIST, list.get(i).getInterestId());
        bundle.putInt(ChanelItemFrg.CURRENT_POS,currentPos);
        chanelfrg.setArguments(bundle);
        return chanelfrg;
    }

}
