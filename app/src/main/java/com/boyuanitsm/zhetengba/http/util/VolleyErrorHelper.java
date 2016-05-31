package com.boyuanitsm.zhetengba.http.util;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Volley失败帮助类
 * @author wangbin
 *
 */
public class VolleyErrorHelper {
	 /**
     * Returns appropriate message which is to be displayed to the user 
     * against the specified error object.
     * 
     * @param error
     * @return
     */
  public static String getMessage(Object error) {
      if(error==null){
          return "连接服务器失败~！";
      }
      if (error instanceof TimeoutError) {
          return "连接服务器失败~！";
      }
      else if (isServerProblem(error)) {
          return handleServerError(error);
      }
      else if (isNetworkProblem(error)) {
          return "无网络连接~！";
      }
      return "网络异常,请稍后再试~！";
  }
  
  /**
  * Determines whether the error is related to network
  * @param error
  * @return
  */
  private static boolean isNetworkProblem(Object error) {
      return (error instanceof NetworkError) || (error instanceof NoConnectionError);
  }
  /**
  * Determines whether the error is related to server
  * @param error
  * @return
  */
  private static boolean isServerProblem(Object error) {
      return (error instanceof ServerError) || (error instanceof AuthFailureError);
  }
  /**
  * Handles the server error, tries to determine whether to show a stock message or to 
  * show a message retrieved from the server.
  * 
  * @param err
  * @return
  */
  private static String handleServerError(Object err) {
      VolleyError error = (VolleyError) err;
  
      NetworkResponse response = error.networkResponse;
  
      if (response != null) {
          switch (response.statusCode) {
//            case 404:
            case 422:
            case 401:
                try {
                    // server might return error like this { "error": "Some error occured" }
                    // Use "Gson" to parse the result
                    HashMap<String, String> result = new Gson().fromJson(new String(response.data),
                            new TypeToken<Map<String, String>>() {
                            }.getType());

                    if (result != null && result.containsKey("error")) {
                        return result.get("error");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // invalid request
                return error.getMessage();

            default:
                return "连接服务器失败~！";
            }
      }
        return "网络异常,请稍后再试~！";
  }
}
