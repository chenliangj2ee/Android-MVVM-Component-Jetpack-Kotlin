package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.My
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
@My(myRefresh = true)
class FragmentNoToolbarRefreshDemo : MyBaseFragment<FragmentToolbarNoBinding, DefaultViewModel>() {
    override fun initOnCreateView() {

    }
}