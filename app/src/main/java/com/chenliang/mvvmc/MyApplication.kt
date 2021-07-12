package com.chenliang.mvvmc

import com.chenliang.account.AccountApplication
import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.MyBaseApplication
import com.chenliang.baselibrary.utils.anrCheck
import com.chenliang.third.ThirdApplication

class MyApplication : MyBaseApplication() {

    /**
     * 初始化Module模块Application
     */
    override fun initModuleApplication() {
        createModuleApp(AccountApplication::class.java)
        createModuleApp(ThirdApplication::class.java)
    }

    override fun onCreate() {
        anrCheck {
            super.onCreate()
            BaseInit.registerApi(ApiService::class.java)
        }
    }
}