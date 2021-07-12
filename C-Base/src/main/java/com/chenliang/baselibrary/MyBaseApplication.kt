package com.chenliang.baselibrary

import android.app.Application
import android.content.Context
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
        BaseInit.init(this)
        moduleApps.forEach {
            it.onCreate()
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        initModuleApplication()
    }

    abstract fun initModuleApplication()

    fun <T> createModuleApp(clasz: Class<T>) {
        var app: T? = null
        try {
            val classLoader: ClassLoader = this.classLoader
            if (classLoader != null) {
                val mClass = classLoader.loadClass(clasz.name)
                if (mClass != null)
                    app = mClass.newInstance() as T?
            }
            val method: Method? =
                Application::class.java.getDeclaredMethod("attach", Context::class.java)
            if (method != null) {
                method.isAccessible = true
                method.invoke(app, baseContext)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        moduleApps.add(app as Application)
    }

}