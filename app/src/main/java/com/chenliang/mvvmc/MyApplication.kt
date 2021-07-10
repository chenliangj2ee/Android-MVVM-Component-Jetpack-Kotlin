package com.chenliang.mvvmc

import android.app.Application
import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.utils.anrCheck

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        anrCheck {
            BaseInit.init(this)
            BaseInit.registerApi(
                ApiService::class.java,
                com.chenliang.account.ApiService::class.java
            )
        }


    }
}