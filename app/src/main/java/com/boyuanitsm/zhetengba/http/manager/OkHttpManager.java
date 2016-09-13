package com.boyuanitsm.zhetengba.http.manager;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.boyuanitsm.zhetengba.MyApplication;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.param.Param;
import com.boyuanitsm.zhetengba.http.util.HttpHeaderHelper;
import com.boyuanitsm.zhetengba.utils.GsonUtils;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.SpUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangbin on 16/8/23.
 */
public class OkHttpManager {
    private static OkHttpClient mOkHttpClient;
    private Headers headers;
    private static OkHttpManager mInstance;
    private Gson mGson;
    private Handler mDelivery;

    private OkHttpManager() {
        mGson = new Gson();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        mOkHttpClient = builder.build();
        //cookie enabled
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static OkHttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpManager();
                }
            }
        }
        return mInstance;
    }


    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    /**
     * post请求  键值对
     *
     * @param url
     * @param paramMap
     * @return
     */
    public void doPost(String url, Map<String, String> paramMap, final ResultCallback callback) {
        headers = HttpHeaderHelper.getHeaders();
        MyLogUtils.info("请求地址:" + url);
        MyLogUtils.info("请求参数:" + GsonUtils.bean2Json(paramMap));
        FormBody.Builder parmBuilder = new FormBody.Builder();
        Param[] params = map2Params(paramMap);
        if (params != null && params.length > 0) {
            for (Param param : params) {
                parmBuilder.add(param.key, param.value);
            }
        }

        Request.Builder builder = new Request.Builder();
        builder.url(url).post(parmBuilder.build());
        if (headers != null) {
            MyLogUtils.info("headers内容是===="+headers);
            builder.headers(headers);
        }

        Request request = builder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(-1, "请求失败，请检查网络！", callback);
//                callback.onError(-1, "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取cookie
                String header = response.header("Set-Cookie");
                MyLogUtils.info("获取到cookie:" + header);
                if (!TextUtils.isEmpty(header))
                    SpUtils.setCooike(MyApplication.getInstance(), header);
                final String result = response.body().string();
                MyLogUtils.info("获取result：" + result);
                if (callback.mType == String.class) {

//                        callback.onResponse(result);
                    sendSuccessResultCallback(result, callback);
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int status = jsonObject.getInt("status");
                        if (status == 200) {//成功
                            Object o = mGson.fromJson(result, callback.mType);
                            sendSuccessResultCallback(o, callback);
//                                callback.onResponse(o);
                        } else if (status == 601) {
                            sendFailedStringCallback(status, result, callback);
//                                callback.onError(status, result);
                        } else {//失败
                            sendFailedStringCallback(status, jsonObject.getString("message"), callback);
//                                callback.onError(status, jsonObject.getString("message"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    private void sendFailedStringCallback(final int status, final String e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(status, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }


}
