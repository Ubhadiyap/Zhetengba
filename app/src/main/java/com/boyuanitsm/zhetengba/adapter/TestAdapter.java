package com.boyuanitsm.zhetengba.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.bean.CircleInfo;

import java.util.List;

/**
 * 测试
 * Created by xiaoke on 2016/5/26.
 */
public class TestAdapter extends BaseAdapter {
    public static final int STATE1=1;
    public static final int STATE2=2;
    public static final int SATE3=3;
    private Context context;
    //    public CircleMessAdatper(Context context){
//        this.context=context;
//    }
    public TestAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder1 holder1=null;
        if (position==0){
            convertView = View.inflate(context, R.layout.test_item_cal,null);
        }else  if (convertView!=null&&convertView.getTag()!=null){
                    convertView.setTag(holder1);
        }else {
                    holder1=new Holder1();
                    convertView = View.inflate(context, R.layout.test_item_mess,null);
                    holder1.tv_dangqi= (TextView) convertView.findViewById(R.id.tv_dangqi);
                    holder1.tv_dongtai= (TextView) convertView.findViewById(R.id.tv_dongtai);
                    convertView.setTag(holder1);
        }

        return convertView;
    }
    class Holder1 {
        private TextView tv_dangqi,tv_dongtai;

    }

}
