package com.chenliang.component_a

import com.chenliang.baselibrary.net.initAPI

var Any.API: ApiService
    set(value) {}
    get() = MyApiFactory.api!!
object MyApiFactory {
    var base = "http://api.alpha.xiaoliuyisheng.cn/app/doctora/"
    var api: ApiService? = null

    init {
        api = initAPI(base, ApiService::class.java)
    }

}
