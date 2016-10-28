package com.boyuanitsm.zhetengba.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * Created by wangbin on 16/4/1.
 */
public class ZhetebaUtils {

    public static final String TAG = "NaviSDkDemo";
    public static double pi = 3.1415926535897932384626;
//
//    //判断本地是否有过搜索字段
//    public static boolean isRepeat(SearchEntity entity){
//        List<SearchEntity> searchList = SearchDao.getSearchList();
//        for (int i=0;i<searchList.size();i++){
//            if((searchList.get(i).getContent().equals(entity.getContent()))
//                    &&(searchList.get(i).getCountry().equals(entity.getCountry()))
//                    &&(searchList.get(i).getType()==entity.getType())){
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * 手机号码验证,11位，不知道详细的手机号码段，只是验证开头必须是1和位数
     */
    public static boolean checkCellPhone(String cellPhoneNr) {
//          String reg="^[1][\\d]{10}";只判断第一位
        String reg = "^[1][34578][\\d]{9}";
        return startCheck(reg, cellPhoneNr);
    }

    public static boolean startCheck(String reg, String string) {
        boolean tem = false;

        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(string);

        tem = matcher.matches();
        return tem;
    }

    /**
     * 密码验证
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd) {
//        String reg="^[0-9A-Za-z]{4,24}$";
        String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,24}$";//字母+数字组合
        return startCheck(reg, pwd);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
    /**
     * 获取当前时间转化成秒
     *
     * @return
     */
    public static int getCurrentTime2ss() {
        Date date=new Date();
        long scond=date.getTime()/1000;
        return  new Long(scond).intValue();
    }

    /**
     * 与当前时间对比
     *
     * @param context
     * @param datess
     * @return
     */
    public static String compareTime(Context context, Long datess) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Long nowss = new Date().getTime();
        Long times = datess - nowss;
        if (times >= 0) {
            return format.format(new Date(datess));
        } else {
            MyToastUtils.showShortToast(context, "时间不得小于当前时间，请重新选择！");
            return "";
        }
    }
    /**
     * 时间对比
     * @param context
     * @param datess
     * @return
     */
//    public static String compareTimeOther(Context context, Long datess,Long nowss) {
//
////        if (nowss==0){
////            nowss=new Date().getTime();
////            Long times = datess - nowss;
////            if (times > 0) {
////                return format.format(new Date(datess));
////            } else {
////                MyToastUtils.showShortToast(context, "时间不得小于当前时间，请重新选择！");
////                return format.format(new Date(nowss));
////            }
////        }else if (datess!=0){
////            Long times = datess - nowss;
////            if (times > 0) {
////                return format.format(new Date(nowss));
////            } else{
////                MyToastUtils.showShortToast(context, "开始时间不得小于结束时间，请重新选择！");
////                return format.format(new Date(nowss));
////            }
////        }else if (datess>0){
////           Long times=datess-nowss;
////            if (times>0){
////                return format.format(new Date(nowss));
////            }else {
////                nowss=new Date().getTime();
////                MyToastUtils.showShortToast(context,"开始时间不得大于结束时间，请重新选择！");
////                return format.format(new Date(nowss));
////            }
////        }else {
////            return format.format(new Date(nowss));
////        }
//
//    }

    /**
     * 时间戳转化成时间
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String timeToDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日HH:mm");
        return format.format(new Date(time));

    }

    /**
     * 时间戳转化成时间
     * @param time
     * @return
     */
    public static String timeToDater(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(time));

    }

    /**
     * 格式化时间格式
     *
     * @param date
     * @return
     */
    public static String formatTime(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(date));
    }

    /**
     * 时间转成时间戳
     *
     * @return
     */
    public static long dateToTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            return 0;
        }
        return date.getTime();
    }


    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 获取新增一个月
     */
    public static List<String> getMonthList() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dateList = new ArrayList<>();
        Date date = new Date();
        dateList.add(formatter.format(date));
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.add(Calendar.MONTH, 1);
        dateList.add(formatter.format(calendar.getTime()));
        return dateList;
    }

    /**
     * 获取最近7/30天时间集合
     *
     * @param
     */
    public static List<String> getDateLists(int day) {
        List<String> lists = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();//取时间,今天
        Calendar calendar = new GregorianCalendar();
        for (int i = 0; i < day; i++) {
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime();   //这个时间就是日期往后推一天的结果
            String daytime = format.format(date);
            calendar.clear();
            lists.add(daytime);
        }
        return lists;
    }

    /**
     * 根据两个时间获取两张中间时间
     */
    public static List<String> getTimeList(String starTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        try {
            Date date1 = sdf.parse(starTime);
            Date date2 = sdf.parse(endTime);
            start.setTime(date1);
            end.setTime(date2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<String> result = new ArrayList<String>();
        start.add(Calendar.DAY_OF_YEAR, 1);
        while (start.before(end)) {
            result.add(sdf.format(start.getTime()));
            start.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 获得手机唯一IMEI
     */
    public static String getPhoneIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = telephonyManager.getDeviceId();
        return IMEI;
    }

    /**
     * 获取手机屏幕宽
     *
     * @param activity
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // float density = metrics.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        // int densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        return metrics.widthPixels;
    }

    /**
     * @param activity
     */
    public static int getPhoneDensityDpi(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        return densityDpi;
    }

    /**
     * 获取手机屏幕高
     *
     * @param activity
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;

    }

    /**
     * 获取屏幕密度 (单位 px)
     */
    public static Point getScreenMeture(Context context) {
        Display mDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        return new Point(mDisplay.getWidth(), mDisplay.getHeight());
    }

    /**
     * 获取手机状态栏的高度
     */
    public static int getStatusHeight(Context context) {
        // Rect frame = new Rect();
        // activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        // return frame.top;

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取当前屏幕旋转角度
     *
     * @param activity
     * @return 0表示是竖屏; 90表示是左横屏; 180表示是反向竖屏; 270表示是右横屏
     */
    public static int getScreenRotationOnPhone(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        // final Display display = ((WindowManager)
        // context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                return 0;

            case Surface.ROTATION_90:
                return 90;

            case Surface.ROTATION_180:
                return 180;

            case Surface.ROTATION_270:
                return -90;
        }
        return 0;
    }

    /**
     * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
     *
     * @param context
     * @return
     */
    public static int isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return 0;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return 1;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            String extraInfo = netWorkInfo.getExtraInfo();
                            if ("cmwap".equalsIgnoreCase(extraInfo) || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
                                return 2;
                            }
                            return 3;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue 像素值
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 生成0-9的随机数
     *
     * @param count 生成几位数（6位）
     * @return random 随机数
     */

    public static String getRandomNumber(int count) {
        String random = "";
        for (int i = 0; i < count; i++) {
            String str = String.valueOf((int) (Math.random() * 10 - 1));
            random = random + str;
        }
        return random;
    }

    /***
     * 获取MAC地址
     *
     * @return
     */
//    public static String getMacAddress(Context context) {
//        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        if (wifiInfo.getMacAddress() != null) {
//            return wifiInfo.getMacAddress();
//        } else {
//            return "";
//        }
//    }

    /**
     * 获取运行时间
     *
     * @return 运行时间(单位/s)
     */
    public static long getRunTimes() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        if (ut == 0) {
            ut = 1;
        }
        return ut;
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    public static String getVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /**
     * sdcard是否可读写
     */
    public static boolean IsCanUseSdCard() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * sim卡是否可读
     *
     * @param context
     * @return
     */
    public static boolean isCanUseSim(Context context) {
        try {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public void hideSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = ((Activity) context).getWindow().getDecorView();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 根据某个固定的View隐藏软键盘
     *
     * @param context
     * @param v
     */
    public void hideSoftKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * 作用：获取当前设备的内核数目
     *
     * @return
     */
    public static int getAvailableProcessorsNum() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 从下载文件的url中截取文件名
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        if (path == null) {
            return null;
        }
        String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
        if (fileName == null || "".equals(fileName.trim())) {
            fileName = UUID.randomUUID().toString() + ".tmp"; // 默认名
        }
        return fileName;
    }

    /**
     * 加载本地小图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            // return BitmapFactory.decodeStream(fis); //把流转化为Bitmap图片
            return BitmapFactory.decodeStream(fis, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DisplayMetrics getWindowDisplay(Context context) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        // float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        // int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        // float xdpi = dm.xdpi;
        // float ydpi = dm.ydpi;

        return dm;
    }

    /**
     * 获取手机串号，需要权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 获取手机系统的版本号
     *
     * @return
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取应用的版本号,默认值为1.0
     *
     * @return 当前应用的版本号
     */
    public static String getAppVersion(Context context) {
        String version = "1.0";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;

    }

    /**
     * 获取版本
     *
     * @param context
     * @return
     */
    public static int getAppVer(Context context) {
        int version = 1;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;

    }

    /**
     * 调用系统的分享控件
     *
     * @param activity
     * @param content
     */
    public static void shareContent(Context activity, String content) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        activity.startActivity(intent);
    }

    /**
     * 判断应用是否安装
     *
     * @param context
     * @param packName
     * @return
     */
    public static boolean appInstalled(Context context, String packName) {
        PackageInfo packageInfo;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(packName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }

        return packageInfo == null ? false : true;
    }

    /**
     * 判断某个服务是不是活着
     *
     * @param mContext
     * @param serviceName
     * @return
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {

        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(50);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * @param context 上下文
     *                要校验Activity的名称
     * @return true Activity还运行，false
     */
    public static boolean isRunningActivity(Context context, String actName) {
        // ActivityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (runningTasks != null && runningTasks.size() > 0) {
            for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
                String name = runningTaskInfo.topActivity.getClassName();
                if (actName.equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    public static Map<String, String> mapHelper(Map<String, String> map) {
        Map<String, String> newMap = new HashMap<>();
        for (Map.Entry<String, String> nMap : map.entrySet()) {
            String str = nMap.getKey();
            newMap.put("str", nMap.getValue());
        }
        return newMap;

    }

    private static int QR_WIDTH = 400;
    private static int QR_HEIGHT = 400;

    // 要转换的地址或字符串,可以是中文
    public static Bitmap createQRImage(String url) {
        Bitmap bitmap = null;
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 查询缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getFilesDir().getAbsoluteFile());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getFilesDir().getAbsoluteFile());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 获取文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getFilesDir().getAbsoluteFile());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getFilesDir().getAbsoluteFile());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 判断是否为直辖市
     * @param str
     * @return
     */
    public static boolean isCity(String str){
        boolean flag=false;
        String[] citys={"上海市","北京市","天津市","重庆市"};
        for (int i=0;i<citys.length;i++){
            if (TextUtils.equals(str,citys[i])){
                flag=true;
            }
        }
        return flag;
    }
    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }
    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }
}
