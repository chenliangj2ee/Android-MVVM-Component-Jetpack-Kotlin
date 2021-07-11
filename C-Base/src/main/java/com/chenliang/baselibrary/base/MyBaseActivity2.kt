package com.chenliang.baselibrary.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.chenliang.baselibrary.utils.initVM

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.base
 * @author: chenliang
 * @date: 2021/07/11
 */
abstract class MyBaseActivity2<BINDING : ViewDataBinding, VM : ViewModel> :
    MyBaseActivity<BINDING>() {
    lateinit var mVM: VM
    override fun onCreate(savedInstanceState: Bundle?) {
//        VM::class.java.name
//        通过路径获取class
//        mVM =initVM(VM::class.java)
        super.onCreate(savedInstanceState)
    }


}