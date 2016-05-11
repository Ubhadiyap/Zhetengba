package com.boyuanitsm.zhetengba.fragment.calendarFrg;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.adapter.ImageAdapter;
import com.boyuanitsm.zhetengba.base.BaseFragment;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.refresh.PullToRefreshListView;

import java.lang.ref.WeakReference;

/**
 * 简约界面
 * Created by xiaoke on 2016/4/24.
 */
public class SimpleFrg extends BaseFragment {
    private PullToRefreshListView lv_act;
    private View view;
    private ListView lv_calen;
    private View viewHeader_act;
    private ActAdapter adapter;
    private ViewPager viewPager;
    private ImageHandler handler = new ImageHandler(new WeakReference<SimpleFrg>(this));


    @Override
    public View initView(LayoutInflater inflater) {
       view = inflater.inflate(R.layout.act_frag, null, false);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewHeader_act = getLayoutInflater(savedInstanceState).inflate(R.layout.item_viewpager_act, null);
        lv_act = (PullToRefreshListView) view.findViewById(R.id.lv_act);
        //设置简约listview的headerview：item_viewpager_act.xml
        lv_act.setPullRefreshEnabled(true);//下拉刷新
        lv_act.setScrollLoadEnabled(true);//滑动加载
        lv_act.setPullLoadEnabled(false);//上拉刷新
        lv_act.setHasMoreData(true);//是否有更多数据
        lv_act.getRefreshableView().setVerticalScrollBarEnabled(false);//设置右侧滑动
        lv_act.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        lv_act.setLastUpdatedLabel(ZtinfoUtils.getCurrentTime());
        lv_act.getRefreshableView().setDivider(null);
        //刷新初始化
        lv_act.getRefreshableView().addHeaderView(viewHeader_act);
        adapter = new ActAdapter(mActivity);
        lv_act.getRefreshableView().setAdapter(adapter);
        viewPager = (ViewPager) view.findViewById(R.id.vp_loop_act);
        viewPager.setAdapter(new ImageAdapter(mActivity));
        viewPager.setOnPageChangeListener(new PagerChangeListener());
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);//默认在中间，使用户看不到边界
        //开始轮播效果
        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
    }

    /***
     * viewpager监听事件
     */
    private class PagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, position, 0));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                    break;
                default:
                    break;
            }
        }
    }


    private static class ImageHandler extends android.os.Handler {
        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED = 4;

        //轮播间隔时间
        protected static final long MSG_DELAY = 3000;
        private int currentItem = 0;
        private WeakReference<SimpleFrg> weakReference;

        protected ImageHandler(WeakReference<SimpleFrg> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SimpleFrg simpleFrg = weakReference.get();
            if (simpleFrg == null) {
                return;
            }
            if (simpleFrg.handler.hasMessages(MSG_UPDATE_IMAGE)) {
                simpleFrg.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    simpleFrg.viewPager.setCurrentItem(currentItem);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    simpleFrg.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;

            }
        }
    }
}
