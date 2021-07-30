package com.chenliang.baselibrary.base

import com.chenliang.annotation.MyRoute
import com.chenliang.baselibrary.databinding.BaseFragmentDefaultBinding

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.base
 * @author: chenliang
 * @date: 2021/07/27
 */
@MyRoute(path="base/defautFragment")
class MyDefaultFragment : MyBaseFragment<BaseFragmentDefaultBinding, DefaultViewModel>() {
    override fun initOnCreateView() {
    }
}