package com.boyuanitsm.zhetengba.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.adapter.ImageAdapter;
import com.boyuanitsm.zhetengba.view.CustomDialog;

import java.lang.ref.WeakReference;

/**
 * 简约界面
 * Created by xiaoke on 2016/4/24.
 */
public class SimpleFrg extends Fragment {
    private ListView lv_act;
    private ListView lv_calen;
    private View viewHeader_act;
    private ActAdapter adapter;
    private final static String tag="SimpleFragment";
    private ViewPager viewPager;
    private ImageHandler handler= new ImageHandler(new WeakReference<SimpleFrg>(this));
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //设置活动点击布局
        View view=inflater.inflate(R.layout.act_fragment, container, false);
        //设置活动listview的headerview
        viewHeader_act=getLayoutInflater(savedInstanceState).inflate(R.layout.item_viewpager_act,null);

        lv_act = (ListView) view.findViewById(R.id.lv_act);

        //设置活动listview的headerview：item_viewpager_act.xml
        lv_act.addHeaderView(viewHeader_act);

        adapter=new ActAdapter(getActivity());
        lv_act.setAdapter(adapter);
        lv_act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog();

            }
        });
        Log.i(tag,"onCreateView()");
        viewPager = (ViewPager) view.findViewById(R.id.vp_loop_act);
        viewPager.setAdapter(new ImageAdapter(getContext()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);//默认在中间，使用户看不到边界
        //开始轮播效果
        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
        return view ;

    }

    /***
     * 显示dialog
     * @param
     */
    private void showDialog() {
        CustomDialog.Builder builder= new CustomDialog.Builder(getContext());
        builder.setPositiveButton("你们两个是同事", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("共参加过2次活动", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();


    }

    private static class ImageHandler extends android.os.Handler{
        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE  = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT   = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT  = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED  = 4;

        //轮播间隔时间
        protected static final long MSG_DELAY = 3000;
        private int currentItem=0;
        private WeakReference<SimpleFrg> weakReference;
        protected ImageHandler(WeakReference<SimpleFrg> wk){
            weakReference=wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SimpleFrg simpleFrg = weakReference.get();
            if (simpleFrg ==null){
                return;
            }
            if (simpleFrg.handler.hasMessages(MSG_UPDATE_IMAGE)){
                simpleFrg.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what){
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
