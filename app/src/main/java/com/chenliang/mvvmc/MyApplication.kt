package com.chenliang.mvvmc

import android.app.Application
import com.chenliang.baselibrary.BaseInit

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * 注册接口API
         */
        BaseInit.init(this)
        BaseInit.registerApi(
            ApiService::class.java,
            com.chenliang.account.ApiService::class.java
        )

    }
}