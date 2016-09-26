package com.boyuanitsm.zhetengba.view;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 自定义不可输入emoj图片
 * Created by xiaoke on 2016/7/12.
 */
public class CanotEmojEditText extends EditText {

    public CanotEmojEditText(Context context) {
        super(context);
    }

    public CanotEmojEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanotEmojEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}