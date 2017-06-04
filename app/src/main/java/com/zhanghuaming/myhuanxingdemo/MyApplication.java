package com.zhanghuaming.myhuanxingdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by zhang on 2017/6/4.
 */

public class MyApplication extends Application {
    private Context applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

//初始化

        EMClient.getInstance().init(applicationContext, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);





    }



}
