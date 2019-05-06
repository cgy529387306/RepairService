package com.yxw.cn.repairservice.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yxw.cn.repairservice.gson.NullStringToEmptyAdapterFactory;

public class GsonUtil {

    private static Gson gson;

    public static Gson getIntance() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
        }
        return gson;
    }
}
