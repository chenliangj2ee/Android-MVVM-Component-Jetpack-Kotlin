package com.chenliang.mvvmc

import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.MyBaseApplication
import com.chenliang.baselibrary.exception.MyException
import com.chenliang.baselibrary.utils.anrCheck
import com.chenliang.mvvmc.act.MainActivity
import com.chenliang.processor.app.MyRoutePath

class MyApplication : MyBaseApplication() {

    override fun onCreate() {
        anrCheck {
            super.onCreate()
            BaseInit.registerApi(ApiService::class.java)
            BaseInit.initMyRoute(MyRoutePath)

            MyException.open(this, MainActivity::class.java)
                .addPackage("com.chenliang")
        }
    }
}