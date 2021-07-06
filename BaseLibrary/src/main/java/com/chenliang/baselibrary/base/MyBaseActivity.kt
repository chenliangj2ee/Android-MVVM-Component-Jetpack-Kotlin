package com.chenliang.baselibrary.base

import android.os.Bundle
import android.view.MotionEvent
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chenliang.baselibrary.annotation.layoutId
import com.chenliang.baselibrary.net.MyHttpEvent
import java.lang.Exception

abstract class MyBaseActivity<BINDING : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: BINDING
    lateinit var httpEvent: MyHttpEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        httpEvent = MyHttpEvent(this)
        binding = DataBindingUtil.setContentView(this, layoutId())

        var onCreateStart = System.currentTimeMillis()
        initCreate()
        var onCreateEnd = System.currentTimeMillis()
        if (onCreateEnd - onCreateStart > 200) {
            throw Exception("${this::class.simpleName} initCreate耗时太长，请优化...")
        }
    }

    abstract fun layoutId(): Int
    abstract fun initCreate()

    override fun onResume() {
        super.onResume()
        httpEvent.onResume()
    }

    override fun onPause() {
        super.onPause()
        httpEvent.onPause()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
        httpEvent.onDestroy()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        httpEvent.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}