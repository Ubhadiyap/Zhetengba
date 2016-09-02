package com.boyuanitsm.zhetengba.activity.mess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.PersonalAct;
import com.boyuanitsm.zhetengba.activity.circle.CirxqAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ErEntity;
import com.boyuanitsm.zhetengba.utils.GsonUtils;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.boyuanitsm.zhetengba.view.scan.CameraManager;
import com.boyuanitsm.zhetengba.view.scan.CaptureActivityHandler;
import com.boyuanitsm.zhetengba.view.scan.InactivityTimer;
import com.boyuanitsm.zhetengba.view.scan.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Vector;

/**
 * 扫描二维码界面
 * Created by Yang on 2016/2/17 0017.
 */
@SuppressLint("InlinedApi")
public class ScanQrcodeAct extends BaseActivity implements Callback {
    @ViewInject(R.id.rlFlash)
    private RelativeLayout flashBtn;//闪光灯点击区域
    @ViewInject(R.id.iv_flash)
    private ImageView ivFlash; //闪光灯图片

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private boolean isOpen = true;
    private Camera camera;
    private Parameters parameter;
    private String photo_path;

    private int SCAN_TYPE = 0;//0下车扫码 1寻车扫码

    @Override
    public void setLayout() {
        setContentView(R.layout.act_scan);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("扫一扫");

        // 初始化 CameraManager
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

    }

    @OnClick(R.id.rlFlash)
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.rlFlash:
                camera = CameraManager.get().getCamera();
                if (camera == null) {
                    Toast.makeText(ScanQrcodeAct.this, "未获得相机权限，请打开相机权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                parameter = camera.getParameters();
                if (isOpen == true) {
                    parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(parameter);
                    ivFlash.setBackgroundResource(R.mipmap.light_off);
                    isOpen = false;
                } else {
                    parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameter);
                    ivFlash.setBackgroundResource(R.mipmap.light);
                    isOpen = true;
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data == null) {
                return;
            }
//			String[] proj = { MediaStore.Images.Media.DATA };
//			// 获取选中图片的路径
//			Cursor cursor = getContentResolver().query(data.getData(), proj, null, null, null);
//			if (cursor.moveToFirst()) {
//
//				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//				photo_path = cursor.getString(column_index);
//				if (photo_path == null) {
//					photo_path = ScanImageUtil.getPath(getApplicationContext(), data.getData());
//				}
//			}
//
//			cursor.close();
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					Result result = ScanImageUtil.scaning(photo_path);
//					if (result == null) {
//						Looper.prepare();
//						Toast.makeText(getApplicationContext(), "图片格式有误", Toast.LENGTH_SHORT).show();
//						Looper.loop();
//					} else {
//						// 数据返回
//						String recode = recode(result.toString());
//						Intent intent = new Intent();
//						intent.putExtra("code", recode);
//						setResult(0, intent);
//						finish();
//					}
//				}
//			}).start();

        }
    }

    /**
     * 中文乱码
     * <p>
     * 暂时解决大部分的中文乱码 但是还有部分的乱码无法解决 .
     *
     * @return
     */
    private String recode(String str) {
        String formart = "";
        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder().canEncode(str);
            if (ISO) {
                formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
            } else {
                formart = str;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return formart;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
        // 恢复活动监控器
        inactivityTimer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.onPause();
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
        CameraManager.get().stopPreview();
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    /**
     * txtResult.setText(obj.getBarcodeFormat().toString() + ":" + obj.getText());
     * <p>
     * obj.getText()这个就是二维码扫描出来之后的URL地址。
     */
    public void handleDecode(Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();
//        viewfinderView.drawResultBitmap(barcode);
        playBeepSoundAndVibrate();
        if (obj.getText().startsWith("{") && obj.getText().endsWith("}")) {
            ErEntity erEntity = GsonUtils.gsonToBean(obj.getText(), ErEntity.class);
//        MyToastUtils.showShortToast(getApplicationContext(), "扫描成功"+obj.getText());
//        scanResult(obj.getText());
            if (erEntity != null) {

                if (erEntity.getType() == 0) {//圈子
                    Intent intent = new Intent(ScanQrcodeAct.this, CirxqAct.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 3);
                    bundle.putString("circleId", erEntity.getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (erEntity.getType() == 1) {//个人主页
                    Intent intent = new Intent(ScanQrcodeAct.this, PersonalAct.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", erEntity.getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                finish();
            } else {
                showErrorDialog();
//                MyToastUtils.showShortToast(ScanQrcodeAct.this, "请扫描折腾吧提供的二维码！");

            }
        } else {
            showErrorDialog();
//            MyToastUtils.showShortToast(ScanQrcodeAct.this,"请扫描折腾吧提供的二维码！");
//            restartCamera();
        }
        closeCamera();
        viewfinderView.setVisibility(View.GONE);

    }

    private void showErrorDialog() {
        final MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.builder().setTitle("提示").setMsg("请扫描折腾吧提供的二维码！").setCancelable(false).
                setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartCamera();
            }
        }).show();
    }


    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    /**
     * 重新启动扫码
     */
    void restartCamera() {

        viewfinderView.setVisibility(View.VISIBLE);

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        initCamera(surfaceHolder);

        // 恢复活动监控器
        inactivityTimer.onResume();
    }

    void closeCamera() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();

        // 关闭摄像头
        CameraManager.get().closeDriver();
    }


}