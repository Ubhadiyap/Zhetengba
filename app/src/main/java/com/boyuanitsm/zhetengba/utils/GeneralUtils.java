package com.boyuanitsm.zhetengba.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.VersionDataEntity;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util
 *
 * @author wangbin
 */
public class GeneralUtils {

    private static GeneralUtils generalUtils;

    public static GeneralUtils getInstance() {
        if (generalUtils == null) {
            generalUtils = new GeneralUtils();
        }
        return generalUtils;
    }

    // 要判断是否包含特殊字符的目标字符串
    public static boolean compileExChar(Context context,String str) {
        String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            MyToastUtils.showShortToast(context, "不允许输入特殊符号!");
            return false;
        }
        return true;
    }
    // 要判断密码是否符合规则--由数字和26个英文字母组成的字符串
    public static boolean isTruePaw(Context context,String str) {
        String limitEx = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        if (!m.find()) {
            MyToastUtils.showShortToast(context, "只能是数字或英文字符！");
            return false;
        }
        return true;
    }
    /**
     * 检查版本更新
     */
	public void toVersion(final Context context,int version,final int type){
		MyLogUtils.degug("version:"+version);
		RequestManager.getUserManager().findNewApp(version, "ANDROID", new ResultCallback<ResultBean<VersionDataEntity>>() {
			@Override
			public void onError(int status, String errorMsg) {
				MyToastUtils.showShortToast(context,"更新失败，请检查网络！");
			}

			@Override
			public void onResponse(ResultBean<VersionDataEntity> response) {
				VersionDataEntity versionData = response.getData();
				if (!versionData.isIsNeedUpdrage()) {
					if (type == 0)
						MyToastUtils.showShortToast(context, "当前已是最新版本！");

				} else {
					String path = IZtbUrl.BASE_URL+versionData.getVersion().getPackagePath();
					String updrageLog =versionData.getVersion().getUpdrageLog();//新版本更新内容
					String remark = versionData.getVersion().getRemark();//新版本大小
					showIsDownLoad(context, path);
				}
			}
		});
	}
	/*
  * 从服务器中下载APK
  */
	protected void downLoadApk(final Context context,final String url) {
		final ProgressDialog pd;    //进度条对话框
		pd = new  ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMax(100);
		pd.setProgress(0);
		pd.setMessage("正在下载更新");
		pd.show();
		new Thread(){
			@Override
			public void run() {
				try {
					File file = getFileFromServer(url, pd);
					sleep(500);
					installApk(context,file);
					pd.dismiss(); //结束掉进度条对话框
				} catch (Exception e) {
					Message msg = new Message();
					msg.what = DOWN_ERROR;
					pd.dismiss();
					msg.obj=context;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}}.start();
	}

	private final int DOWN_ERROR=1;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {

				case DOWN_ERROR:
					Context context= (Context) msg.obj;
					//下载apk失败
					Toast.makeText(context, "下载新版本失败",Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};

	//安装apk
	protected void installApk(Context context,File file) {
		Intent intent = new Intent();
		//执行动作
		intent.setAction(Intent.ACTION_VIEW);
		//安装完成后可选择打开或者完成
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//执行的数据类型
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");//编者按：此处Android应为android，否则造成安装不了
		context.startActivity(intent);
	}

	/**
	 * 是否下载
	 */
	private void showIsDownLoad(final Context context,final String path) {
		MyAlertDialog dialog = new MyAlertDialog(context).builder();
		dialog.setTitle("提示").setMsg("检测到最新版本，是否现在下载？").setCancelable(false)
				.setPositiveButton("立即更新", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						downLoadApk(context, path);
					}
				}).setNegativeButton("稍后再说", null).show();
	}
	public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
		//如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			//获取到文件的大小
			pd.setMax(100);
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(), "zhetengba.apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			long total = 0;
			while ((len = bis.read(buffer)) != -1) {
				total += len;
				int prop = (int) (total * 100 / conn.getContentLength());
				//获取当前下载量
				pd.setProgress(prop);
				fos.write(buffer, 0, len);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}
}
