package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.ContractedAct;
import com.boyuanitsm.zhetengba.activity.publish.ScheduleAct;

import java.util.List;

/**
 * Created by xiaoke on 2016/6/27.
 */
public class ScheduDialog extends Dialog {
    private Context context;
    public ScheduDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    public ScheduDialog(Context context) {
       this(context, R.style.Plane_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.act_schule_layout);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        ListView lv_theme = (ListView) findViewById(R.id.lv_theme);
    }
}
