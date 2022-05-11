package com.chenliang.baselibrary.utils

import android.app.Activity
import android.app.Dialog
import android.os.Handler
import android.view.MotionEvent
import com.chenliang.baselibrary.debug.BaseBeanLog
import com.chenliang.baselibrary.debug.FloatView
import gorden.rxbus2.RxBus
import gorden.rxbus2.Subscribe
import gorden.rxbus2.ThreadMode

/**
 * 监听网络请求数据，网络请求loadingDialog
 */
class MyHttpEvent(act: Activity) {

    var act = act
    var log = true
    var floatButton: FloatView = FloatView(act)


    /**
     * 接受log event
     */
    @Subscribe(code = 31415928, threadMode = ThreadMode.MAIN)
    fun eventHttpLog(bean: BaseBeanLog) {
//        log("${act::class.java.simpleName}  eventHttpLog....................")
        if (this.log)
            floatButton.addLog(bean)

    }


    /**
     * 注销event，关闭所有loading Dialog
     */
    fun onDestroy() {
//        log("MyHttpEvent ${act::class.java.simpleName} onDestroy......")
        RxBus.get().unRegister(this)
    }

    /**
     * 开始接受log
     */
    fun register() {
        RxBus.get().unRegister(this)
        RxBus.get().register(this)
        log = true
    }


    /**
     * 停止接受log
     */
    fun onPause() {
        RxBus.get().unRegister(this)
        log = false
    }
    /**
     * 停止接受log
     */
    fun unRegister() {
        RxBus.get().unRegister(this)
        log = false
    }

    /**
     * 长按Activity，显示网络调试日志
     */
    var downTime = 0L;
    var down = false;
    fun dispatchTouchEvent(event: MotionEvent?) {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            down = true
            downTime = System.currentTimeMillis()
            Handler().postDelayed(Runnable {
                if (down && System.currentTimeMillis() - downTime > 1000) {
                    this.floatButton.show(event.x, event.y)
                }
            }, 1200)

        } else if (event!!.action == MotionEvent.ACTION_UP) {
            down = false
        }
    }
}