package com.chenliang.mvvmc.demo

import android.view.View
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.Callback
import com.chenliang.baselibrary.utils.toast
import com.chenliang.mvvmc.databinding.ActivityEventCallbackBinding
import gorden.rxbus2.RxBus

class EventCallBackActivity : MyBaseActivity<ActivityEventCallbackBinding, DefaultViewModel>() {
    override fun initCreate() {

    }


    fun sendEventAction(v: View) {
        toast("消息发出")
        RxBus.get().send(100, Callback("ddd") {
            it.getStringExtra("message")?.let { toast(it) }
        })
    }

}