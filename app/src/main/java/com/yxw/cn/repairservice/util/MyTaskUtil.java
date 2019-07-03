package com.yxw.cn.repairservice.util;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;

import java.util.HashMap;
import java.util.Map;

public class MyTaskUtil {

    private static final String TAG = "MyTimeTask";

    public static void refreshToken(){
        Log.d(TAG,"start refreshToken");
        if (CurrentUser.getInstance().isLogin() && Helper.isNotEmpty(CurrentUser.getInstance().getRefreshToken())){
            Map<String, Object> map = new HashMap<>();
            map.put("refreshToken", CurrentUser.getInstance().getRefreshToken());
            OkGo.<ResponseData<String>>post(UrlConstant.REFRESH_TOKEN)
                    .upJson(new Gson().toJson(map))
                    .execute(new JsonCallback<ResponseData<String>>() {
                                 @Override
                                 public void onSuccess(ResponseData<String> response) {
                                     if (response!=null){
                                         if (response.isSuccess()) {
                                             Log.d("refreshToken","myToken:"+response.getData());
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

    public static void refreshLocation(BDLocation bdLocation){
        if (CurrentUser.getInstance().isLogin()){
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("userId", CurrentUser.getInstance().getUserId());
            requestMap.put("locationLat", bdLocation.getLatitude());
            requestMap.put("locationLng", bdLocation.getLongitude());
            OkGo.<ResponseData<Object>>post(UrlConstant.UPDATE_LOCATION)
                    .upJson(new Gson().toJson(requestMap))
                    .execute(new JsonCallback<ResponseData<Object>>() {
                                 @Override
                                 public void onSuccess(ResponseData<Object> response) {
                                     if (response!=null && response.isSuccess()){
                                         Log.d("refreshLocation","locationLat:"+bdLocation.getLatitude()+",locationLng:"+bdLocation.getLongitude());
                                     }
                                 }
                             }
                    );
        }
    }

    public static void setVersion(){
        Log.d("setVersion","start setVersion");
        OkGo.<ResponseData<Object>>post(UrlConstant.UPDATE_VERSION+ AppHelper.getCurrentVersionName())
                .execute(new JsonCallback<ResponseData<Object>>() {
                             @Override
                             public void onSuccess(ResponseData<Object> response) {
                                 if (response!=null && response.isSuccess()){
                                     Log.d("setVersion","version:"+AppHelper.getCurrentVersionName());
                                 }
                             }
                         }
                );
    }
}
