package com.chenliang.mvvmc

import android.app.Application
import com.chenliang.baselibrary.BaseInit

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * 注册接口API
         */
        BaseInit.registerApi(
            this,
            ApiService::class.java,
            com.chenliang.component_a.ApiService::class.java,
            com.chenliang.component_b.ApiService::class.java
        )

    }
}