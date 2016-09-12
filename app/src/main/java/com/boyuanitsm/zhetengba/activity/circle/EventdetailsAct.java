package com.boyuanitsm.zhetengba.activity.circle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZtinfoUtils;
import com.boyuanitsm.zhetengba.view.CanotEmojEditText;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 活动详情
 */
public class EventdetailsAct extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.feedback_et)
    private CanotEmojEditText feedback_et;
    private  String str;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_eventdetails);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("活动详情");
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle!=null){
            str=bundle.getString("backTheme");
            feedback_et.setText(str);
            feedback_et.setSelection(str.length());
        }

    }
    @OnClick({R.id.tv_ok})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_ok:
                str=feedback_et.getText().toString().trim();
                if (TextUtils.isEmpty(str)){
                    MyToastUtils.showShortToast(this,"输入内容不能为空");
                    return;
                }else {
                    Intent intent=new Intent();
                    Bundle bundle2=new Bundle();
                    bundle2.putString("detailsTheme",str);
                    intent.putExtra("bundle2", bundle2);
                    setResult(0, intent);
                    ZtinfoUtils.hideSoftKeyboard(EventdetailsAct.this);
                    finish();
                }

                break;
        }
    }
}
