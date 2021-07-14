package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseFragment
import com.chenliang.mvvmc.databinding.FragmentToolbarNoBinding
import kotlinx.android.synthetic.main.fragment_toolbar_no.*
import kotlinx.android.synthetic.main.fragment_toolbar_no.view.*

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.mvvmc.demo
 * @author: chenliang
 * @date: 2021/07/14
 */
@My(myToolbarTitle = "自带标题的Fragment", myRefresh = true)
class FragmentToolbarRefreshDemo : MyBaseFragment<FragmentToolbarNoBinding, DefaultViewModel>() {
    override fun initOnCreateView() {
    }


}