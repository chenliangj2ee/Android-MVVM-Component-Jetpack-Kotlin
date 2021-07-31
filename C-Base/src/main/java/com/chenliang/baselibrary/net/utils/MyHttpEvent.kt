package com.chenliang.baselibrary.net.utils

import android.app.Activity
import android.app.Dialog
import android.os.Handler
import android.view.MotionEvent
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.net.log.BaseBeanLog
import com.chenliang.baselibrary.net.log.FloatView
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
    var dialogs = HashMap<String, Dialog>()


    init {
        RxBus.get().unRegister(this)
        RxBus.get().register(this)
    }

    /**
     * 接受展示loading event
     */
    @Subscribe(code = 31415926, threadMode = ThreadMode.MAIN)
    fun eventShowLoading(id: String) {
//        log("${act::class.java.simpleName}  eventShowLoading....................")
        var dialog = Dialog(act)
        dialog.setContentView(R.layout.base_loading)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show()
        dialogs[id] = dialog

    }

    /**
     * 接受关闭loading event
     */
    @Subscribe(code = 31415927, threadMode = ThreadMode.MAIN)
    fun eventCloseLoading(id: String) {
//        log("${act::class.java.simpleName}  eventCloseLoading....................")
        dialogs[id]?.dismiss()
        dialogs.remove(id)
    }


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
        for (d in dialogs) {
            d.value.dismiss()
        }
    }

    /**
     * 开始接受log
     */
    fun onResume() {
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