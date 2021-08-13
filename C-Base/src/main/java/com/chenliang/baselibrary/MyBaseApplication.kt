package com.chenliang.baselibrary

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import java.lang.reflect.Method


/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary
 * @author: chenliang
 * @date: 2021/7/12
 */
abstract class MyBaseApplication : Application() {
    var moduleApps = ArrayList<Application>()
    override fun onCreate() {
        super.onCreate()
        moduleApps.forEach { it.onCreate() }
    }

    fun initEnvironmentModel(){
        if (BuildConfig.isDev)
            BaseInit.openDev()
        if (BuildConfig.isTest)
            BaseInit.openTest()
        if (BuildConfig.isRelease)
            BaseInit.openRelease()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        initModuleApplication()
    }


    private fun initModuleApplication() {
        if (BaseInit.con != null)
            return
        BaseInit.init(this)
        initEnvironmentModel()
        var info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        if (info.metaData == null)
            return
        var apps = info.metaData.keySet()
        apps.forEach {
            try {
                var cla = Class.forName(it.toString())
                var app = cla.newInstance()
                if (app is Application && cla.name != this::class.java.name) {
                    initModuleAppAttach(app)
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initModuleAppAttach(app: Application) {
        val method: Method? =
            Application::class.java.getDeclaredMethod("attach", Context::class.java)
        if (method != null) {
            method.isAccessible = true
            method.invoke(app, baseContext)
            moduleApps.add(app)
        }
    }
}