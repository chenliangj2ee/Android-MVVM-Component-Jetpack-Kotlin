package com.chenliang.mvvmc

import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.MyBaseApplication
import com.chenliang.baselibrary.utils.anrCheck

class MyApplication : MyBaseApplication() {

    override fun onCreate() {
        anrCheck {
            super.onCreate()
            BaseInit.registerApi(ApiService::class.java)
        }
    }
}