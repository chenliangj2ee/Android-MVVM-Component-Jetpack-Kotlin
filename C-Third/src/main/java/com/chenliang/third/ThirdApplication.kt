package com.chenliang.third

import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.MyBaseApplication
import com.chenliang.baselibrary.utils.mylog

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.third
 * @author: chenliang
 * @date: 2021/7/12
 */
class ThirdApplication : MyBaseApplication() {
    override fun onCreate() {
        super.onCreate()
        mylog("ThirdApplication onCreate ....")
        BaseInit.registerApi(ApiService::class.java)
    }

}