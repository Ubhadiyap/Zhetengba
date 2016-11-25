package com.boyuanitsm.zhetengba.adapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.CityAct;
import com.boyuanitsm.zhetengba.bean.CityBean;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.fragment.calendarFrg.SimpleFrg;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.view.MyGridView;
import com.boyuanitsm.zhetengba.view.PinnedHeaderListView;

import java.util.Arrays;
import java.util.List;

/**
 * 选择城市适配器
 */
public class CityListAdapter extends BaseAdapter implements SectionIndexer,
        PinnedHeaderListView.PinnedHeaderAdapter, OnScrollListener {
    private int mLocationPosition = -1;
    private List<CityBean> mDatas;
    // 首字母集
    private List<String> mFriendsSections;
    private List<Integer> mFriendsPositions;
    private LayoutInflater inflater;
    private CityAct context;
//    private List<CityBean> historys;
    private List<CityBean> hotList;
    private ProgressDialog dialog;
    private UserInfo user;

    public CityListAdapter(CityAct context, List<CityBean> datas, List<String> friendsSections,
                           List<Integer> friendsPositions,  List<CityBean> hotList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDatas = datas;
        mFriendsSections = friendsSections;
        mFriendsPositions = friendsPositions;
        this.hotList = hotList;
        user= UserInfoDao.getUser();
        dialog = new ProgressDialog(context);
        dialog.setMessage("重新定位中，请稍后...");
        dialog.setCancelable(false);
    }

    public void notifyData(List<CityBean> datas, List<String> friendsSections,
                           List<Integer> friendsPositions,  List<CityBean> hotList) {
        mDatas = datas;
        mFriendsSections = friendsSections;
        mFriendsPositions = friendsPositions;
        this.hotList = hotList;
        dialog.dismiss();
//        GlobalParams.bdPosition = 0;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        int section = getSectionForPosition(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item, null);
        }
        LinearLayout mHeaderParent = (LinearLayout) convertView
                .findViewById(R.id.friends_item_header_parent);
        TextView mHeaderText = (TextView) convertView
                .findViewById(R.id.friends_item_header_text);
        if (getPositionForSection(section) == position) {
            mHeaderParent.setVisibility(View.VISIBLE);
            mHeaderText.setText(mFriendsSections.get(section));
        } else {
            mHeaderParent.setVisibility(View.GONE);
        }

        RelativeLayout rlGps = (RelativeLayout) convertView.findViewById(R.id.rlGps);
        MyGridView mvHistory = (MyGridView) convertView.findViewById(R.id.mvHistory);
        MyGridView mvHot = (MyGridView) convertView.findViewById(R.id.mvHot);
        TextView textView = (TextView) convertView
                .findViewById(R.id.friends_item);
        LinearLayout linearLayout= (LinearLayout) convertView.findViewById(R.id.ll_friends_item);
        TextView tvGps = (TextView) convertView.findViewById(R.id.tvGps);
//        TextView tvLocation = (TextView) convertView.findViewById(R.id.tvLocAgain);
        if ("当前定位城市".equals(mDatas.get(position).getPinyi())) {
            rlGps.setVisibility(View.VISIBLE);
            mvHistory.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            mvHot.setVisibility(View.GONE);
            tvGps.setText(mDatas.get(position).getName());
            tvGps.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    EventBus.getDefault().post(
//                            new CityEvent(mDatas.get(position)));
//                    context.finish();
                }
            });
//            tvLocation.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.show();
////                    GlobalParams.bdPosition = 1;
////                    context.sendBroadcast(new Intent(MainActivity.EXIT_RECEIVER));
//                }
//            });
        } else if ("历史".equals(mDatas.get(position).getPinyi())) {
//            mvHistory.setVisibility(View.VISIBLE);
//            textView.setVisibility(View.GONE);
//            rlGps.setVisibility(View.GONE);
//            mvHot.setVisibility(View.GONE);
//            if (historys != null && historys.size() > 0) {
//                mvHistory.setAdapter(new GvCityAdapter(context, historys));
//                mvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        CityDao.addData(historys.get(position));
//                        EventBus.getDefault().post(
//                                new CityEvent(historys.get(position)));
//                        context.finish();
//                        context.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
//                    }
//                });
//            }
        } else if (("热门".equals(mDatas.get(position).getPinyi()))) {
            mvHistory.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            rlGps.setVisibility(View.GONE);
            mvHot.setVisibility(View.VISIBLE);
            if (hotList != null && hotList.size() > 0) {
                mvHot.setAdapter(new GvCityAdapter(context, hotList));
                mvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        CityDao.addData(hotList.get(position));
//                        EventBus.getDefault().post(
//                                new CityEvent(hotList.get(position)));
//                        context.finish();
//                        context.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                        user.setCity(hotList.get(position).getCityid());
                        modifyUser(user);//选择收索出来的城市后把它传给后台
                        context.finish();
                    }
                });
            }
        } else {
            mvHistory.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            rlGps.setVisibility(View.GONE);
            mvHot.setVisibility(View.GONE);
            textView.setText(mDatas.get(position).getName());
            textView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
//                    CityDao.addData(mDatas.get(position));
//                    EventBus.getDefault().post(
//                            new CityEvent(mDatas.get(position)));
//                    context.finish();
//                    context.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    user.setCity(mDatas.get(position).getCityid());
                    modifyUser(user);//选择收索出来的城市后把它传给后台
                    context.finish();

                }
            });
        }

        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        if (view instanceof PinnedHeaderListView) {
            ((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
        }
    }

    @Override
    public int getPinnedHeaderState(int position) {
        int realPosition = position;
        if (realPosition < 0
                || (mLocationPosition != -1 && mLocationPosition == realPosition)) {
            return PINNED_HEADER_GONE;
        }
        mLocationPosition = -1;
        int section = getSectionForPosition(realPosition);
        int nextSectionPosition = getPositionForSection(section + 1);
        if (nextSectionPosition != -1
                && realPosition == nextSectionPosition - 1) {
            return PINNED_HEADER_PUSHED_UP;
        }
        return PINNED_HEADER_VISIBLE;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {
        // TODO Auto-generated method stub
        int realPosition = position;
        int section = getSectionForPosition(realPosition);
        String title = (String) getSections()[section];
        ((TextView) header.findViewById(R.id.friends_list_header_text))
                .setText(title);
    }

    @Override
    public Object[] getSections() {
        // TODO Auto-generated method stub
        return mFriendsSections.toArray();
    }

    @Override
    public int getPositionForSection(int section) {
        if (section < 0 || section >= mFriendsSections.size()) {
            return -1;
        }
        return mFriendsPositions.get(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        // TODO Auto-generated method stub
        if (position < 0 || position >= getCount()) {
            return -1;
        }
        int index = Arrays.binarySearch(mFriendsPositions.toArray(), position);
        return index >= 0 ? index : -index - 2;
    }


    /**
     * 修改个人资料
     * @param user
     */
    private void modifyUser(final UserInfo user) {
        RequestManager.getUserManager().modifyUserInfo(user, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<String> response) {
                UserInfoDao.updateUser(user);//成功后更新数据并通知变化首页左上角城市
                context.sendBroadcast(new Intent(SimpleFrg.UPDATA_CITY_NORES));
                MyLogUtils.info(UserInfoDao.getUser().getCity());
            }
        });
    }


}
