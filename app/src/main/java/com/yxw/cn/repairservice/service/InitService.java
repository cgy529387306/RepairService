package com.yxw.cn.repairservice.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yxw.cn.repairservice.BaseApplication;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.LocationService;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.crash.CrashHandler;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.util.Helper;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class InitService extends IntentService {

    private static final String ACTION_INIT = "com.yxw.cn.repairservice.action.Init";

    private MyTimeTask mTimeTask;

    public LocationService mLocationService;

    public Gson mGson = new Gson();

    public static final int REFRESH_TIME = 1*60*60*1000;
    /**
     * Instantiates a new Init service.
     */
    public InitService() {
        super("InitService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     * @param context the context
     * @see IntentService
     */

// TODO: Customize helper method
    public static void startActionInit(Context context) {
        try {
            Intent intent = new Intent(context, InitService.class);
            intent.setAction(ACTION_INIT);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                loadInit();
            }
        }
    }

    /**
     * 初始化操作数据
     */
    private void loadInit() {
        MultiDex.install(BaseApplication.getInstance());
        mLocationService = new LocationService(BaseApplication.getInstance());
        SDKInitializer.initialize(BaseApplication.getInstance());
        SDKInitializer.setCoordType(CoordType.BD09LL);

        OkGo.getInstance().init(BaseApplication.getInstance());
        //Builder
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(BaseApplication.getInstance())));
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        OkGo.getInstance().setOkHttpClient(builder.build());
        Logger.addLogAdapter(new AndroidLogAdapter());
        CrashHandler.getInstance().init(BaseApplication.getInstance());
        if (getApplicationInfo().packageName.equals(AppUtil.getCurProcessName(BaseApplication.getInstance())) ||
                "io.rong.push".equals(AppUtil.getCurProcessName(BaseApplication.getInstance()))) {

        }
        setTimerTask();
    }

    public void refreshToken(){
        Logger.d("myToken:refreshToken()");
        if (CurrentUser.getInstance().isLogin() && Helper.isNotEmpty(CurrentUser.getInstance().getRefreshToken())){
            Map<String, Object> map = new HashMap<>();
            map.put("refreshToken", CurrentUser.getInstance().getRefreshToken());
            OkGo.<ResponseData<String>>post(UrlConstant.REFRESH_TOKEN)
                    .upJson(mGson.toJson(map))
                    .execute(new JsonCallback<ResponseData<String>>() {
                                 @Override
                                 public void onSuccess(ResponseData<String> response) {
                                     if (response!=null){
                                         if (response.isSuccess()) {
                                             Logger.d("myToken:"+response.getData());
                                             HttpHeaders headers = new HttpHeaders();
                                             CurrentUser.getInstance().setToken(response.getData());
                                             headers.put("Authorization", "Bearer "+ response.getData());
                                             OkGo.getInstance().addCommonHeaders(headers);
                                         }
                                     }
                                 }
                             }
                    );
        }
    }

    private void setTimerTask(){
        mTimeTask = new MyTimeTask(REFRESH_TIME, new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
            }
        });
        mTimeTask.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //在此执行定时操作
                    refreshToken();
                    break;
                default:
                    break;
            }
        }
    };



    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.darker_gray);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}