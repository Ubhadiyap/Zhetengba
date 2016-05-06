package com.boyuanitsm.zhetengba.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;

/**
 * 简约界面条目点击弹出“活动详情”对话框
 * Created by xiaoke on 2016/4/28.
 */
public class CustomDialog extends Dialog {


    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public static class Builder{

        private Context context;
        private String message;
        private String title;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private int resid;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * set the Dialog image frome resource
         * @param resid
         */

        public void setResid(int resid) {
            this.resid = resid;
        }

        /**
         * Set the Dialog message from resource
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }
//
//        /**
//         * Set the positive button resource and it's listener
//         */
//        public Builder setPositiveButton(int positiveButtonText,
//                                         OnClickListener listener) {
//            this.positiveButtonText = (String) context
//                    .getText(positiveButtonText);
//            this.positiveButtonClickListener = listener;
//            return this;
//        }
//
//        public Builder setPositiveButton(String positiveButtonText,
//                                         OnClickListener listener) {
//            this.positiveButtonText = positiveButtonText;
//            this.positiveButtonClickListener = listener;
//            return this;
//        }
//
//        public Builder setNegativeButton(int negativeButtonText,
//                                         OnClickListener listener) {
//            this.negativeButtonText = (String) context
//                    .getText(negativeButtonText);
//            this.negativeButtonClickListener = listener;
//            return this;
//        }
//
//        public Builder setNegativeButton(String negativeButtonText,
//                                         OnClickListener listener) {
//            this.negativeButtonText = negativeButtonText;
//            this.negativeButtonClickListener = listener;
//            return this;
//        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.act_frag_act_dialog, null);
            dialog.addContentView(layout, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
            dialog.setContentView(layout);
            ImageView iv_cancel = (ImageView) layout.findViewById(R.id.iv_cancel);
            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return dialog;
        }
    }

    }

