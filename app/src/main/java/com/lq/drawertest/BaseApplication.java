package com.lq.drawertest;

import android.app.Application;

import java.util.HashMap;

public class BaseApplication extends Application {
    private static HashMap<String,Object> map = new HashMap<>();
    //饿汉模式
    private static BaseApplication instance = new BaseApplication();
    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static HashMap getMap(){
        return map;
    }
}
