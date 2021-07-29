package com.chenliang.baselibrary.base

import com.alibaba.android.arouter.facade.annotation.Route
import com.chenliang.baselibrary.annotation.MyRoute
import com.chenliang.baselibrary.databinding.BaseFragmentDefaultBinding

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.base
 * @author: chenliang
 * @date: 2021/07/27
 */
@MyRoute
class MyDefaultFragment : MyBaseFragment<BaseFragmentDefaultBinding, DefaultViewModel>() {
    override fun initOnCreateView() {
    }
}