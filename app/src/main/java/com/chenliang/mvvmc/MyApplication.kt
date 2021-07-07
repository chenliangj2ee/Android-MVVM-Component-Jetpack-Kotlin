package com.chenliang.mvvmc

import android.app.Application
import com.chenliang.baselibrary.BaseInit

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        BaseInit.registerApi(this, ApiService::class.java )
    }
}