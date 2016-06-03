package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子详情adapter
 * Created by bitch-1 on 2016/5/12.
 */
public class CirxqAdapter extends RecyclerView.Adapter<CirxqAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private List<Integer> list;
//    private List<UserInfo> list=new ArrayList<>();

    public CirxqAdapter(Context context, List<Integer> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;

    }
//    public CirxqAdapter(Context context, List<UserInfo> list) {
//        inflater = LayoutInflater.from(context);
//        this.list = list;
//
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        CircleImageView mCir;
    }

    /**
     * itemClick接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_gl_cirxq, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mCir = (CircleImageView) view.findViewById(R.id.civ_hand);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        if(list!=null&&list.size()>0) {
            if (list.size() <= 4) {
                if (i == list.size()) {
                    viewHolder.mCir.setImageResource(R.mipmap.cirxq_add);
                    if (mOnItemClickListener != null) {
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnItemClickListener.onItemClick(viewHolder.itemView, i);
                            }
                        });
                    }
                } else {
//                    ImageLoader.getInstance().displayImage(IZtbUrl.BASE_URL + list.get(i).getIcon(), viewHolder.mCir);
                viewHolder.mCir.setImageResource(list.get(i));
                }
            } else {
                switch (i) {
                    case 0:
//                        ImageLoader.getInstance().displayImage(IZtbUrl.BASE_URL + list.get(0).getIcon(), viewHolder.mCir);
                    viewHolder.mCir.setImageResource(list.get(0));
                        break;
                    case 1:
//                        ImageLoader.getInstance().displayImage(IZtbUrl.BASE_URL + list.get(1).getIcon(), viewHolder.mCir);
                    viewHolder.mCir.setImageResource(list.get(1));
                        break;
                    case 2:
//                        ImageLoader.getInstance().displayImage(IZtbUrl.BASE_URL + list.get(2).getIcon(), viewHolder.mCir);
                    viewHolder.mCir.setImageResource(list.get(2));
                        break;
                    case 3:
//                        ImageLoader.getInstance().displayImage(IZtbUrl.BASE_URL + list.get(3).getIcon(), viewHolder.mCir);
                    viewHolder.mCir.setImageResource(list.get(3));
                        break;
                    case 4:
                        viewHolder.mCir.setImageResource(R.mipmap.cirxq_add);
                        if (mOnItemClickListener != null) {
                            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOnItemClickListener.onItemClick(viewHolder.itemView, i);
                                }
                            });
                        }
                        break;
                    case 5:
                        viewHolder.mCir.setImageResource(R.mipmap.cirxq_more);
                        if (mOnItemClickListener != null) {
                            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOnItemClickListener.onItemClick(viewHolder.itemView, i);
                                }
                            });
                        }
                        break;
                }

//            if (i == (list.size()-1)) {
//                viewHolder.mCir.setImageResource(R.mipmap.cirxq_add);
//            }
//           viewHolder.mCir.setImageResource(list.get(i));
            }
        }
//        if (i==4){
//            viewHolder.mCir.setText("更多...");
//            //如果设置了回调，则设置点击事件
//            if (mOnItemClickListener!=null){
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mOnItemClickListener.onItemClick(viewHolder.itemView,i);
//                    }
//                });
//            }
//        }else{
//            viewHolder.mCir.setImageResource(list.get(i));
//        }

    }


    @Override
    public int getItemCount() {
        return list.size() <=4 ? list.size() + 1 : 6;
    }
}
