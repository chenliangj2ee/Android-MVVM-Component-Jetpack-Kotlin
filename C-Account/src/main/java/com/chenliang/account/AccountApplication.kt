package com.chenliang.account

import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.MyBaseApplication
import com.chenliang.baselibrary.utils.log

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.account
 * @author: chenliang
 * @date: 2021/7/12
 */
class AccountApplication : MyBaseApplication() {

    override fun onCreate() {
        super.onCreate()
        log("AccountApplication onCreate ....")
        BaseInit.registerApi(ApiService::class.java)
    }

    override fun initModuleApplication() {

    }
}