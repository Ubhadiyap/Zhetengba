//package com.boyuanitsm.zhetengba.utils;
//
//import android.annotation.SuppressLint;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.widget.RemoteViews;
//
//import com.beyonditsm.parking.R;
//import com.beyonditsm.parking.entity.ResultBean;
//import com.beyonditsm.parking.entity.UserBean;
//import com.beyonditsm.parking.entity.VersionBean;
//import com.beyonditsm.parking.https.CallBack;
//import com.beyonditsm.parking.https.IParkingUrl;
//import com.beyonditsm.parking.https.engine.RequestManager;
//import com.beyonditsm.parking.widget.MyAlertDialog;
//import com.lidroid.xutils.HttpUtils;
//import com.lidroid.xutils.exception.HttpException;
//import com.lidroid.xutils.http.HttpHandler;
//import com.lidroid.xutils.http.ResponseInfo;
//import com.lidroid.xutils.http.callback.RequestCallBack;
//
//import java.io.File;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Util
// *
// * @author wangbin
// */
//public class GeneralUtils {
//
//    private static GeneralUtils generalUtils;
//
//    public static GeneralUtils getInstance() {
//        if (generalUtils == null) {
//            generalUtils = new GeneralUtils();
//        }
//        return generalUtils;
//    }
//
//    // 要判断是否包含特殊字符的目标字符串
//    public static boolean compileExChar(Context context,String str) {
//        String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//        Pattern pattern = Pattern.compile(limitEx);
//        Matcher m = pattern.matcher(str);
//        if (m.find()) {
//            MyToastUtils.showShortToast(context, "不允许输入特殊符号!");
//            return false;
//        }
//        return true;
//    }
//    // 要判断密码是否符合规则--由数字和26个英文字母组成的字符串
//    public static boolean isTruePaw(Context context,String str) {
//        String limitEx = "^[A-Za-z0-9]+$";
//        Pattern pattern = Pattern.compile(limitEx);
//        Matcher m = pattern.matcher(str);
//        if (!m.find()) {
//            MyToastUtils.showShortToast(context, "只能是数字或英文字符！");
//            return false;
//        }
//        return true;
//    }
//    /**
//     * 检查版本更新
//     */
//	public void toVersion(final Context context,final int type){
//		VersionBean vb=new VersionBean();
//		vb.setSource(1);
//		vb.setApp_type(type);
//		RequestManager.getUserInstance().toVersion(vb, new CallBack() {
//
//			@Override
//			public void onSuccess(String result) {
//				ResultBean<VersionBean> rd=(ResultBean<VersionBean>) GsonUtils.jsonToRb(result, VersionBean.class);
//				VersionBean vb=rd.getObject();
//				int nowVersion=ParkingUtils.getAppVer(context);
//				int newVersion=Integer.valueOf(vb.getVersion_code());
//				if (newVersion>nowVersion) {
//					showIsDownLoad(context, IParkingUrl.BASE_IMAGE_URL+vb.getVersion_url());
//				} else {
//					if(type==1){
//					MyToastUtils.showShortToast(context, "当前已是最新版本");
//					}
//				}
//			}
//            @Override
//            public void onError(String error) {
//                MyToastUtils.showShortToast(context,error);
//            }
//
//            @Override
//            public void onEmpty(String result) {
//                MyToastUtils.showShortToast(context,"您已经是最新版本！");
//            }
//		});
//	}
//
//    private NotificationManager mNotificationManager = null;
//    private Notification mNotification;
//    private String fileName = "/sdcard/parking.apk";
//    private int result, fileSize, downLoadFileSize;
//
//    /**
//     * 是否下载
//     */
//	private void showIsDownLoad(final Context context,final String path) {
//		MyAlertDialog dialog = new MyAlertDialog(context).builder();
//		dialog.setTitle("提示").setMsg("检测到最新版本，是否现在下载？",true).setCancelable(false)
//				.setPositiveButton("确定", new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						File file = new File(fileName);
//						if (file.exists()) {
//							file.delete();
//						}
//						downLoad(context,path);
//					}
//				},true).setNegativeButton("取消", null, null, true).show();
//	}
//
//    /**
//     * 下载
//     *
//     * @param path
//     */
//	@SuppressWarnings({ "unused", "rawtypes" })
//	private void downLoad(final Context context,String path) {
//		HttpUtils http = new HttpUtils();
//		HttpHandler handler = http.download(path, fileName, true, true,
//				new RequestCallBack<File>() {
//					@Override
//					public void onLoading(long total, long current,
//							boolean isUploading) {
//						fileSize = (int) total;
//						downLoadFileSize = (int) current;
//						result = (int) (current * 100 / total);
//						myhandler.sendEmptyMessage(1);
//					}
//
//					@Override
//					public void onStart() {
//						Message message=myhandler.obtainMessage();
//						message.obj=context;
//						message.what=0;
//						myhandler.sendMessage(message);
//					}
//
//					@SuppressLint("SdCardPath")
//					@Override
//					public void onSuccess(ResponseInfo<File> arg0) {
//						Intent intent = new Intent(Intent.ACTION_VIEW);
//						intent.setDataAndType(Uri.fromFile(new File(fileName)),
//								"application/vnd.android.package-archive");
//						context.startActivity(intent);
//					}
//
//					@Override
//					public void onFailure(HttpException arg0, String arg1) {
//						MyToastUtils.showShortToast(context, "下载失败");
//					}
//				});
//	}
//
//	private void showNotification(Context context) {
//		mNotificationManager = (NotificationManager) context
//				.getSystemService(Context.NOTIFICATION_SERVICE);
//		Intent intent = new Intent();
//		PendingIntent pIntent = PendingIntent
//				.getActivity(context, 0, intent, 0);
//		mNotification = new Notification();
//		mNotification.icon = R.mipmap.logo;
//		mNotification.tickerText = "开始下载";
//		mNotification.contentView = new RemoteViews(context.getPackageName(),
//				R.layout.content_view);// 通知栏中进度布局
//		mNotification.contentIntent = pIntent;
//	}
//
//	private Handler myhandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what) {
//			case 0:
//				Context context=(Context) msg.obj;
//				showNotification(context);
//				break;
//			case 1:
//				mNotification.contentView.setTextViewText(
//						R.id.content_view_text1, "进度" + result + "%");
//				mNotification.contentView.setProgressBar(
//						R.id.content_view_progress, fileSize, downLoadFileSize,
//						false);
//				mNotificationManager.notify(0, mNotification);
//				break;
//			}
//		}
//	};
//}
