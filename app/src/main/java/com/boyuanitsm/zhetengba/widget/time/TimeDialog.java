package com.boyuanitsm.zhetengba.widget.time;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.boyuanitsm.zhetengba.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择器
 * 
 * @author yang
 * 
 */
@SuppressLint("ViewConstructor")
public class TimeDialog implements OnClickListener {
	public enum Type {
		ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN
	}// 四种选择模式，年月日时分，年月日，时分，月日时分

	private View rootView; // 总的布局
	WheelTime wheelTime;
	private Button btnSubmit;
	private static final String TAG_SUBMIT = "submit";
	private OnTimeSelectListener timeSelectListener;
	private Display display;
	private Dialog dialog;
	private Context context;
	private Type type;

	public TimeDialog(Context context, Type type) {
		this.context = context;
		this.type = type;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public TimeDialog builder() {
		LayoutInflater mLayoutInflater = LayoutInflater.from(context);
		rootView = mLayoutInflater.inflate(R.layout.pw_time, null);
		rootView.setMinimumWidth(display.getWidth());
		// -----确定按钮
		btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);
		btnSubmit.setTag(TAG_SUBMIT);
		btnSubmit.setOnClickListener(this);
		// ----时间转轮
		final View timepickerview = rootView.findViewById(R.id.timepicker);
		ScreenInfo screenInfo = new ScreenInfo((Activity) context);
		wheelTime = new WheelTime(timepickerview, type);

		wheelTime.screenheight = screenInfo.getHeight();

		//默认选中当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTime.setPicker(year, month, day, hours, minute);
		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(rootView);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);
		
		return this;
	}
	

	public void show() {
		dialog.show();
	}

	/**
	 * 设置可以选择的时间范围
	 * 
	 * @param START_YEAR
	 * @param END_YEAR
	 */
	public void setRange(int START_YEAR, int END_YEAR) {
		WheelTime.setSTART_YEAR(START_YEAR);
		WheelTime.setEND_YEAR(END_YEAR);
	}

	/**
	 * 设置选中时间
	 * 
	 * @param date
	 */
	public void setTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date == null)
			calendar.setTimeInMillis(System.currentTimeMillis());
		else
			calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTime.setPicker(year, month, day, hours, minute);
	}


	/**
	 * 设置是否循环滚动
	 * 
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic) {
		wheelTime.setCyclic(cyclic);
	}

	@Override
	public void onClick(View v) {
		String tag = (String) v.getTag();
		if (tag.equals(TAG_SUBMIT)) {
			if (timeSelectListener != null) {
				try {
					Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
					timeSelectListener.onTimeSelect(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			dialog.dismiss();
			return;
		}
	}

	public interface OnTimeSelectListener {
		public void onTimeSelect(Date date);
	}

	public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
		this.timeSelectListener = timeSelectListener;
	}

}
