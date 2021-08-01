package com.chenliang.mvvmc.demo

import android.view.View
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.sendSelf
import com.chenliang.baselibrary.utils.toast
import com.chenliang.mvvmc.databinding.ActivityEventCallbackBinding

class EventCallBackActivity : MyBaseActivity<ActivityEventCallbackBinding, DefaultViewModel>() {
    override fun initCreate() {

    }


    fun sendEventAction(v: View) {
        toast("消息发出")
        "来自send的数据".sendSelf(100) {
            toast(it.getStringExtra("message")!!)
        }
    }

}