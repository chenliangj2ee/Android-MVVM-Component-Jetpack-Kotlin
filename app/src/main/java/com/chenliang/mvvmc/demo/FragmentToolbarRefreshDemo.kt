package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseFragment
import com.chenliang.mvvmc.databinding.FragmentToolbarNoBinding

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.mvvmc.demo
 * @author: chenliang
 * @date: 2021/07/14
 */
@MyClass(myToolbarTitle = "自带标题的Fragment", myRefresh = true)
class FragmentToolbarRefreshDemo : MyBaseFragment<FragmentToolbarNoBinding, DefaultViewModel>() {
    override fun initOnCreateView() {
    }

    override fun refresh() {
        super.refresh()
    }

}