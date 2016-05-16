package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

import java.util.List;

/**
 * 标签recyclerview适配器
 * Created by Administrator on 2016/5/9.
 */
public class RecycleviewAdp extends RecyclerView.Adapter<RecycleviewAdp.ViewHolder> {

    private final LayoutInflater inflater;
    private List<String> list;

    public RecycleviewAdp(Context context,List<String> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        TextView mTxt;
    }

    /**
     * itemClick接口回调
     */
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.label_griditem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mTxt = (TextView) view.findViewById(R.id.tv_label);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        if (i==3){
            viewHolder.mTxt.setText("更多...");
            //如果设置了回调，则设置点击事件
            if (mOnItemClickListener!=null){
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(viewHolder.itemView,i);
                    }
                });
            }
        }else{
            viewHolder.mTxt.setText(list.get(i));
        }

    }


    @Override
    public int getItemCount() {
        return 4;
    }
}
