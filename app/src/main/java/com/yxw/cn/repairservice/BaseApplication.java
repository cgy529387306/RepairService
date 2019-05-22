package com.yxw.cn.repairservice;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;

import com.yxw.cn.repairservice.service.InitService;


public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    private Handler mDelivery;              //用于在主线程执行的调度器
    @Override
    public void onCreate() {
        super.onCreate();
        mDelivery = new Handler(Looper.getMainLooper());
        mInstance = this;
        InitService.startActionInit(this);
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}

