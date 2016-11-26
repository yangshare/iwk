package com.iwk.yang.application;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.iwk.yang.volley.MyVolley;


/**
 * Created by Yan on 2015/12/26.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化MyVolley
        MyVolley.init(getApplicationContext());
        //初始化Bootstrap
        TypefaceProvider.registerDefaultIconSets();

    }
}
