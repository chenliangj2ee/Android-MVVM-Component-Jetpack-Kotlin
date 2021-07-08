package com.chenliang.component_b

import com.chenliang.baselibrary.net.initAPI

var Any.API: ApiService
    set(value) {}
    get() = MyApiFactory.api!!

object MyApiFactory {
    var base = "http://api.alpha.xiaoliuyisheng.cn/app/doctorb/"
    var api: ApiService? = null

    init {
        api = initAPI(base, ApiService::class.java)
    }

}
