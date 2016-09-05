package com.hyphenate.easeui.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class EaseUserUtils {
    public static DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.userhead) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.userhead) // 设置图片Uri为空或是错误的时候显示的图片
//            .showImageOnFail(R.drawable.userhead) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象



    static EaseUserProfileProvider userProvider;
    
    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }
    
    /**
     * 根据username获取相应user
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
            return userProvider.getUser(username);
        
        return null;
    }
    
    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	EaseUser user = getUserInfo(username);
        if(user != null && user.getAvatar() != null){
//            int avatarResId = Integer.parseInt(user.getAvatar());
//            if (TextUtils.equals(user.getAvatar().substring(0,3),"http")){
                ImageLoader.getInstance().displayImage(user.getAvatar(),imageView,options);
//            }else {
//                ImageLoader.getInstance().displayImage("http://139.196.154.208:8033/zhetengba/"+user.getAvatar(),imageView,options);
//            }
//            try {
//
////                Glide.with(context).load(avatarResId).into(imageView);
//            } catch (Exception e) {
//                //正常的string路径
//                Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(imageView);
//        }
        }else{
            imageView.setImageResource(R.drawable.userhead);
//            ImageLoader.getInstance().displayImage("",imageView,options);
//            Glide.with(context).load(R.drawable.userhead).into(imageView);
        }
    }
    
    /**
     * 设置用户昵称
     */
    public static void setUserNick(String username,TextView textView){
        if(textView != null){
        	EaseUser user = getUserInfo(username);
        	if(user != null && user.getNick() != null&&user.getNick().length()!=32){
        		textView.setText(user.getNick());
        	}else{
//        		textView.setText(username);
                textView.setText("");
        	}
        }
    }
    
}
