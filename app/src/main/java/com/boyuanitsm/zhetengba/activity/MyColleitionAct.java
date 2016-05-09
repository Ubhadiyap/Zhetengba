package com.boyuanitsm.zhetengba.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.adapter.ActAdapter;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的收藏
 * Created by Administrator on 2016/5/9.
 */
public class MyColleitionAct extends BaseActivity implements ActAdapter.IUpdateZan{
    @ViewInject(R.id.lv_myCollection)
    private ListView lvMyCollection;
    private ActAdapter adapter;
    private boolean isComment,isComment2;
    private int gznum=0;//默认关注人数0
    private int jionum=0;//默认参加人数0；

    @Override
    public void setLayout() {
        setContentView(R.layout.act_mycollection);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的收藏");
        adapter = new ActAdapter(MyColleitionAct.this, this);
        lvMyCollection.setAdapter(adapter);
    }

    @Override
    public void registGuanZhu(int position) {
        //得到你屏幕上第一个显示的item
        int firstVisiblePosition = lvMyCollection.getFirstVisiblePosition();
        //得到你屏幕上最后一个显示的item
        int lastVisiblePosition = lvMyCollection.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            //得到你点击的item的view
            View view = lvMyCollection.getChildAt(position - firstVisiblePosition);
            if (view.getTag() instanceof ActAdapter.Holder) {
                //拿到view的Tag,强转成CommentAdapter的ViewHolder
                ActAdapter.Holder holder = (ActAdapter.Holder) view.getTag();
                if (!isComment2) {// 这里我用了一个变量来控制，点第一次时，点赞成功，点赞控件显示蓝色的图标，
                    // 点击第二次时，取消点赞，点赞控件显示灰色的图标。
                    isComment2 = true;
                    holder.iv_simple_guanzhu.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.collect_b));
                    gznum++;
                    holder.tv_guanzhu_num.setText(gznum+"");
                    holder.tv_text_guanzhu.setText("已关注");
                } else {
                    isComment2 = false;
                    holder.iv_simple_guanzhu.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.collect));
                    gznum--;
                    holder.tv_guanzhu_num.setText(gznum+"");
                    holder.tv_text_guanzhu.setText("关注");
                }
            }
        }
    }

    @Override
    public void registJoin(int position) {
//得到你屏幕上第一个显示的item
        int firstVisiblePosition = lvMyCollection.getFirstVisiblePosition();
        //得到你屏幕上最后一个显示的item
        int lastVisiblePosition = lvMyCollection.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            //得到你点击的item的view
            View view = lvMyCollection.getChildAt(position - firstVisiblePosition);
            if (view.getTag() instanceof ActAdapter.Holder) {
                //拿到view的Tag,强转成CommentAdapter的ViewHolder
                ActAdapter.Holder holder = (ActAdapter.Holder) view.getTag();
                if (!isComment) {// 这里我用了一个变量来控制，点第一次时，点赞成功，点赞控件显示蓝色的图标，
                    // 点击第二次时，取消点赞，点赞控件显示灰色的图标。
                    isComment = true;
                    holder.iv_join.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.cancel));
                    holder.tv_text_jion.setText("取消参加");
                    jionum++;
                    holder.tv_join_num.setText(jionum+"");
                    holder.tv_join_num.setTextColor(Color.RED);
                } else {
                    isComment = false;
                    holder.iv_join.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.add));
                    holder.tv_text_jion.setText("参加");
                    jionum--;
                    holder.tv_join_num.setText(jionum+"");
                    holder.tv_join_num.setTextColor(Color.parseColor("#999999"));
                }
            }
        }
    }
}
