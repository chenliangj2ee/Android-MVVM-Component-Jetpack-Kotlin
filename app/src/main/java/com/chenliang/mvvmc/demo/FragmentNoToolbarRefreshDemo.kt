package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.annotation.MyField
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseFragment
import com.chenliang.baselibrary.utils.mylog
import com.chenliang.mvvmc.databinding.FragmentToolbarNoBinding

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.mvvmc.demo
 * @author: chenliang
 * @date: 2021/07/14
 */
@MyClass(myRefresh = true)
class FragmentNoToolbarRefreshDemo : MyBaseFragment<FragmentToolbarNoBinding, DefaultViewModel>() {

    @MyField
    lateinit var type:String
    override fun initOnCreateView() {
        mylog("fragment传参：type----------------$type")
    }
}