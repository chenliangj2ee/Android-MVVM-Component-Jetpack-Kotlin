package com.chenliang.baselibrary.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chenliang.baselibrary.annotation.layoutId
import java.lang.Exception

abstract class MyBaseActivity<Binding : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var layoutId = layoutId(this)
        if (layoutId > 0) {
            binding = DataBindingUtil.setContentView<Binding>(this, layoutId)
        }
        var onCreateStart = System.currentTimeMillis()
        initCreate()
        var onCreateEnd = System.currentTimeMillis()
        if (onCreateEnd - onCreateStart > 200) {
            throw Exception("${this::class.simpleName } initCreate耗时太长，请优化...")
        }
    }

    override fun onResume() {
        super.onResume()
    }

    abstract fun initCreate()
    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}