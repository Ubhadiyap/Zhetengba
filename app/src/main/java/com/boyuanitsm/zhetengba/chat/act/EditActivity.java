package com.boyuanitsm.zhetengba.chat.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;

public class EditActivity extends BaseActivity {
	private EditText editText;


	@Override
	public void setLayout() {
		setContentView(R.layout.em_activity_edit);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		editText = (EditText) findViewById(R.id.edittext);
		String title = getIntent().getStringExtra("title");
		String data = getIntent().getStringExtra("data");
		if(title != null)
			setTopTitle(title);
		if(data != null)
			editText.setText(data);
		editText.setSelection(editText.length());
		setRight("保存", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(editText.getText().toString().trim())){
					MyToastUtils.showShortToast(getApplicationContext(),"名称不能为空！");
					return;
				}
				setResult(RESULT_OK,new Intent().putExtra("data", editText.getText().toString().trim()));
				finish();
			}
		});
	}


	
//	public void save(View view){
//		setResult(RESULT_OK,new Intent().putExtra("data", editText.getText().toString()));
//		finish();
//	}
}
