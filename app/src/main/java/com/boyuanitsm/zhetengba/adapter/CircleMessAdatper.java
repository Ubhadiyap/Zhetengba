package com.boyuanitsm.zhetengba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.circle.CircleglAct;
import com.boyuanitsm.zhetengba.bean.CircleInfo;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;

import java.util.List;

/**
 * 圈子消息，同意，拒绝，回复，赞几种类型
 * Created by xiaoke on 2016/5/9.
 */
public class CircleMessAdatper extends BaseAdapter {
    public static final int STATE1=1;
    public static final int STATE2=2;
    public static final int SATE3=3;
    private List<CircleInfo> circleInfoList;
    private Context context;
    private PopupWindow mPopupWindow;
//    public CircleMessAdatper(Context context){
//        this.context=context;
//    }
    public CircleMessAdatper(Context context,List<CircleInfo> list){
        this.context=context;
        this.circleInfoList=list;
    }
    @Override
    public int getItemViewType(int position) {
        //复写返回类型 list.get(position).type

        return circleInfoList.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return circleInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //tiem_mess_one两种布局
        int type=getItemViewType(position);
        Holder1 holder1=null;
        Holder2 holder2=null;
        Holder3 holder3=null;
        if (convertView!=null&&convertView.getTag()!=null){
            switch (type){
                case STATE1:
                    convertView.setTag(holder1);
                    break;
                case STATE2:
                    convertView.setTag(holder2);
                    break;
                case SATE3:
                    convertView.setTag(holder3);
                    break;
            }
        }else {
            switch (type){
                case STATE1://回复，赞布局
                    holder1=new Holder1();
                    convertView = View.inflate(context, R.layout.item_mess,null);

                  holder1.tv_huifu= (TextView) convertView.findViewById(R.id.tv_huifu);
                    holder1.ll_reply= (LinearLayout) convertView.findViewById(R.id.ll_reply);
                    if (circleInfoList.get(position).getState()==1){
                        holder1.tv_huifu.setText("回复“我”：");
                        holder1.ll_reply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }else if (circleInfoList.get(position).getState()==2){
                        holder1.tv_huifu.setText("赞了“我”：");
                    }else {
                        holder1.tv_huifu.setText("分享了：");
                    }
                    convertView.setTag(holder1);
                    break;
                case STATE2://邀请，拒绝，接受布局
                    holder2=new Holder2();
                    convertView = View.inflate(context, R.layout.item_mess_one,null);
                   holder2.tv_qingqiu= (TextView) convertView.findViewById(R.id.tv_qingqiu);
                  holder2.tv_beizhu= (TextView) convertView.findViewById(R.id.tv_beizhu);

                    if (circleInfoList.get(position).getState()==1){
                        holder2.tv_qingqiu.setText("请求加入娱乐圈");
                        holder2.tv_beizhu.setText("备注：你好，我是李宇春");
                    }else if (circleInfoList.get(position).getState()==2){
                        holder2.tv_qingqiu.setText("邀请你加入买菜圈");
                        holder2.tv_beizhu.setText("备注：一起去买菜");
                    }
                    convertView.setTag(holder2);
                    break;
                case SATE3://同意，拒绝提示布局
                    holder3=new Holder3();
                    convertView = View.inflate(context, R.layout.item_mess_two, null);
                    holder3.tv_shenqing = (TextView) convertView.findViewById(R.id.tv_shenqing);

                    if (circleInfoList.get(position).getState()==1){
                        holder3.tv_shenqing.setText("同意了你的请求，欢迎加入吃饭圈");
                    }else if (circleInfoList.get(position).getState()==2){
                        holder3.tv_shenqing.setText("拒绝了你的请求，不参加吃饭圈");
                    }else {
                        holder3.tv_shenqing.setText("分享了你的吃饭圈子");
                    }
                    convertView.setTag(holder3);
                    break;
            }
        }

        return convertView;
    }
    class Holder1 {
        private TextView tv_huifu;
        private LinearLayout ll_reply;
    }
    class Holder2{
        private TextView tv_qingqiu;
        private TextView tv_beizhu;
    }
    class Holder3{
        private TextView tv_shenqing;
    }
    /**
     * 待解决：对话框布局有出入
     * 选择对话框，选择好友/全部
     */
    private void selectPop() {
        mPopupWindow = new PopupWindow(200, 200);
        View v = LayoutInflater.from(context).inflate(R.layout.act_pop_cir_mes, null);
        TextView tv_friend = (TextView) v.findViewById(R.id.tv_friend);
        TextView tv_all = (TextView) v.findViewById(R.id.tv_all);


        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_circle_stroke));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setContentView(v);
//        mPopupWindow.showAsDropDown(ll_friend, 20, 20);
//        tv_friend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyToastUtils.showShortToast(getContext(), "点击了好友");
//                mPopupWindow.dismiss();
//            }
//        });
//        tv_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyToastUtils.showShortToast(getContext(), "点击了全部");
//                mPopupWindow.dismiss();
//            }
//        });

    }
}
