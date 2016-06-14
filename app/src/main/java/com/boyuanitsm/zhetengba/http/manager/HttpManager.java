package com.boyuanitsm.zhetengba.http.manager;

import com.boyuanitsm.zhetengba.ConstantValue;
import com.boyuanitsm.zhetengba.MyApplication;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.SpUtils;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangbin on 16/6/8.
 */
public class HttpManager {
    private HttpClient client;
    private static Header[] headers;
    private HttpPost post;
    public HttpManager() {
        // 初始化client
        // 如果是wap方式联网，需要设置代理信息
        client = new DefaultHttpClient();
        // 响应超时时间为5秒
        client.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        headers = new Header[1];
        headers[0] = new BasicHeader("cookie", SpUtils.getCookie(MyApplication.getInstance()));
    }


    /**
     * 文件上传 post
     *
     * @param uri
     * @param params
     * @param fileMaps
     * @return
     */
    public String upLoadFile(String uri, Map<String, String> params,
                             Map<String, FileBody> fileMaps) {
        post = new HttpPost(uri);
        post.setHeaders(headers);
        try {
            MultipartEntity mpEntity = new MultipartEntity();
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, String> item : params.entrySet()) {
                    StringBody par = new StringBody(item.getValue());
                    mpEntity.addPart(item.getKey(), par);
                }

            }

            if (fileMaps != null && fileMaps.size() > 0) {
                for (Map.Entry<String, FileBody> entry : fileMaps.entrySet()) {
                    mpEntity.addPart(entry.getKey(), entry.getValue());
                }
            }
            post.setEntity(mpEntity);// 设置需要传递的数据
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(),
                        ConstantValue.ENCODING);
            } else {
                MyLogUtils.error("访问失败--状态码："
                        + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyLogUtils.error("访问异常：" + e.getMessage());
        }
        return null;
    }

    /**
     * 文件上传 post
     *
     * @param uri
     * @param params
     * @param fileMaps
     * @return
     */
    public String upListFile(String uri, Map<String, String> params,
                             Map<String, List<FileBody>> fileMaps) {
        post = new HttpPost(uri);

        post.setHeaders(headers);
        try {
            MultipartEntity mpEntity = new MultipartEntity();
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, String> item : params.entrySet()) {
                    StringBody par = new StringBody(item.getValue());
                    mpEntity.addPart(item.getKey(), par);
                }

            }

            if (fileMaps != null && fileMaps.size() > 0) {
                for (Map.Entry<String, List<FileBody>> entry : fileMaps.entrySet()) {
                    List<FileBody> lists= entry.getValue();
                    for(int i=0;i<lists.size();i++){
                        mpEntity.addPart(entry.getKey(), lists.get(i));
                    }

                }
            }
            post.setEntity(mpEntity);// 设置需要传递的数据
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(),
                        ConstantValue.ENCODING);
            } else {
                MyLogUtils.error("访问失败--状态码："
                        + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyLogUtils.error("访问异常：" + e.getMessage());
        }
        return null;
    }


}
