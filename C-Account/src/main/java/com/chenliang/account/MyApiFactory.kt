package com.chenliang.account

import com.chenliang.baselibrary.net.initAPI

var Any.API: ApiService
    private set(value) {}
    get() = MyApiFactory.api!!

object MyApiFactory {
    var base = "http://api.alpha.xiaoliuyisheng.cn/app/doctor/"
    var api: ApiService? = null

    init {
        api = initAPI(base, ApiService::class.java)
    }

}
