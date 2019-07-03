package com.yxw.cn.repairservice;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.yxw.cn.repairservice.service.InitService;
import com.yxw.cn.repairservice.timetask.SimpleTimerTask;
import com.yxw.cn.repairservice.timetask.SimpleTimerTaskHandler;
import com.yxw.cn.repairservice.util.MyTaskUtil;


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
        Log.d("InitService","start InitService");
        InitService.startActionInit(this);
        doTimeTask();
    }



    private void doTimeTask(){
        SimpleTimerTask loopTask = new SimpleTimerTask(1000) {
            @Override
            public void run() {
                MyTaskUtil.refreshToken();
            }
        };
        SimpleTimerTaskHandler handler = SimpleTimerTaskHandler.getInstance();
        handler.sendTask(0, loopTask);
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

