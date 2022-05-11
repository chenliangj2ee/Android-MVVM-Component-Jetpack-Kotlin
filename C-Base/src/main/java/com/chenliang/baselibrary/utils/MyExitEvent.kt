package com.chenliang.baselibrary.utils

import android.app.Activity
import gorden.rxbus2.RxBus
import gorden.rxbus2.Subscribe

/**
 * 退出
 */
class MyExitEvent() {

    var act:Activity?=null

    @Subscribe(code = BusCode.EXIT)
    fun exit() {
        if(act==null)
            return
        act?.finish()
    }

    fun register(activity: Activity) {
        act=activity
        RxBus.get().register(this)
    }

    fun unRegister() {
        RxBus.get().unRegister(this)
    }
}