package com.chenliang.mvvmc

import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.BuildConfig
import com.chenliang.baselibrary.MyBaseApplication
import com.chenliang.baselibrary.exception.AstException
import com.chenliang.baselibrary.utils.anrCheck
import com.chenliang.mvvmc.act.MainActivity
import com.chenliang.processorapp.MyRoutePath

class MyApplication : MyBaseApplication() {

    override fun onCreate() {
        anrCheck {
            super.onCreate()
            BaseInit.registerApi(ApiService::class.java)
            BaseInit.initMyRoute(MyRoutePath)

            AstException.open(this, MainActivity::class.java)
                .addPackage("com.chenliang")
        }
    }
}