package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.view.HorizontalListView;

/**
 * 管理圈子列表适配器
 * Created by xiaoke on 2016/5/11.
 */
public class GlCircleAdapter extends BaseAdapter {
    private Context context;
    public GlCircleAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CirViewHolder cirViewHolder;
        if (convertView!=null&&convertView.getTag()!=null){
            cirViewHolder= (CirViewHolder) convertView.getTag();
        }else {
            cirViewHolder=new CirViewHolder();
            convertView= View.inflate(context, R.layout.item_gl_circle, null);
//            cirViewHolder.gv_cir = (GridView) convertView.findViewById(R.id.gv_cir);
            cirViewHolder. hlv_glcir= (HorizontalListView) convertView.findViewById(R.id.hlv_glcir);
            convertView.setTag(cirViewHolder);
        }
        GDcirAdapter gDcirAdapter=new GDcirAdapter(context);
//        cirViewHolder.gv_cir.setAdapter(gDcirAdapter);
         cirViewHolder.hlv_glcir.setAdapter(gDcirAdapter);

        return convertView;
    }
    class CirViewHolder{
        private HorizontalListView hlv_glcir;
    }
}
