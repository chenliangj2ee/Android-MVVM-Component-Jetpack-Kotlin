package com.chenliang.baselibrary.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.chenliang.baselibrary.annotation.layoutId
import java.lang.Exception

abstract class MyBaseFragment<T : ViewDataBinding> : Fragment() {
    lateinit var binding: T
    lateinit var rootView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("MyActivityManager",this.javaClass.name);
        var layoutId = layoutId(this)
        if (layoutId > 0) {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        }
        rootView = binding.root;
        var onCreateStart = System.currentTimeMillis()
        initOnCreateView()
        var onCreateEnd = System.currentTimeMillis()
        if (onCreateEnd - onCreateStart > 200) {
            throw Exception("${this::class.simpleName} initOnCreateView耗时太长，请优化...")
        }
        return rootView
    }

    abstract fun initOnCreateView()


    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}