package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;

import java.util.Calendar;

/**
 * 月份选择适配器
 * Created by Administrator on 2016/5/9.
 */
public class MonthSelectAdp extends RecyclerView.Adapter<MonthSelectAdp.ViewHolder> {

    private static  int j = 1;
    private final LayoutInflater inflater;
    private final int month;
    private Context context;
    public MonthSelectAdp(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        //获取到当前月份
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        month = calendar.get(Calendar.MONTH)+1;
        Log.i("TAG","----------------"+month);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        TextView month;
        TextView m;
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
        View view = inflater.inflate(R.layout.selectmonth_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.month = (TextView) view.findViewById(R.id.tv_month);
        viewHolder.m = (TextView) view.findViewById(R.id.month);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        if (j<=month){
            viewHolder.month.setText(j+"");
            if (j==month){
                viewHolder.month.setTextSize(16);
                viewHolder.m.setTextSize(16);
                viewHolder.month.setTextColor(context.getResources().getColor(R.color.selected_color));
                viewHolder.m.setTextColor(context.getResources().getColor(R.color.selected_color));
            }
            j++;
            MyLogUtils.info("j=" + j);
        }
        if (mOnItemClickListener!=null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(viewHolder.itemView,i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return month;
    }

}
