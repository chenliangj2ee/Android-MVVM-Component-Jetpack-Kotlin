package com.chenliang.baselibrary.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

/**
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.base
 * @author: chenliang
 * @date: 2021/7/11
 * com.chenliang.baselibrary.base.MyBaseActivity<com.chenliang.mvvmc.databinding.ActivitySplashBinding, com.chenliang.baselibrary.base.DefaultViewModel>
 */
public class JavaClass {
    public static <T> T createByName(String name) {
        try {
            return (T) Class.forName(name).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getLayoutIdByBinging(Context context, String bingingName) {
        bingingName = bingingName.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
        String [] bingingNames=bingingName.split("\\.");
        String name=bingingNames[bingingNames.length-1].replace("_binding","");
        int layoutId=context.getResources().getIdentifier(name, "layout", context.getPackageName());
        return layoutId;
    }
}
